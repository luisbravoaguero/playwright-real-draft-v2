package com.mapfre.utils.waits;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Utilidad que espera a que aparezca primero (y de forma estable) uno de dos locators:
 * un `errorRoot` o un `successRoot`.
 *
 * Estrategia (resumen):
 * - Opcionalmente ejecuta una acción que dispara el cambio (p. ej. un click).
 * - Hace polling ligero leyendo si cada locator es visible.
 * - Cuando un locator se mantiene visible durante `quietMs` milisegundos se considera "estable".
 * - Si ambos se estabilizan, se desempata el que apareció primero (por timestamp).
 * - Devuelve un `Result` con Outcome {ERROR, SUCCESS, TIMEOUT} y, para ERROR, intenta leer
 *   el texto del mensaje de error.
 */
public final class FirstAppearanceRace {

    private FirstAppearanceRace() {}

    public enum Outcome { ERROR, SUCCESS, TIMEOUT }

    /**
     * Resultado simple que devuelve el outcome, texto opcional de error y tiempo transcurrido.
     */
    public record Result(
            Outcome outcome,
            String errorMessage,
            long elapsedMs
    ) {}

    /**
     * Wait until either errorRoot or successRoot becomes visible first (and stays visible for quietMs).
     *
     * Parámetros clave:
     * - page: instancia de Playwright Page, usada sólo para sleep/polling.
     * - action: Runnable opcional que dispara la transición (click, submit). Si es null se asume
     *   que el evento ya ocurrió.
     * - errorRoot / successRoot: locators que representan estado de error y éxito respectivamente.
     * - errorMessageLocator: opcional; si se pasa, se usa para extraer el texto del error cuando
     *   se detecta Outcome.ERROR. Si es null se lee texto de errorRoot.
     * - timeoutMs: tiempo máximo a esperar (si se agota → TIMEOUT).
     * - quietMs: ventana de estabilidad: el locator debe estar visible de forma continua durante
     *   al menos quietMs para considerarse "estable". Usar 0 para aceptar visibilidad instantánea.
     * - pollMs: intervalo de sondeo (se respeta un mínimo de 50ms para evitar busy-waiting).
     *
     * Comportamiento importante:
     * - Si `action.run()` lanza una excepción, ésta se propaga (no se captura aquí).
     * - Las comprobaciones de visibilidad y lectura de texto usan helpers que atrapan excepciones
     *   (safeVisible / safeText) para evitar romper el bucle por cambios DOM inesperados.
     */
    public static Result waitForFirst(
            Page page,
            Runnable action,
            Locator errorRoot,
            Locator successRoot,
            Locator errorMessageLocator, // can be null; falls back to errorRoot text
            long timeoutMs,
            long quietMs,
            long pollMs
    ) {
        // Ejecutar la acción que dispara el comportamiento (por ejemplo, click en un botón).
        // NOTA: si `action` lanza una excepción, se propagará hacia el llamador.
        // Comentario: se ejecuta inmediatamente antes de comenzar el polling para que el
        // cambio en la UI ocurra mientras monitoreamos las apariciones de los locators.
        if (action != null) action.run();

        long start = System.nanoTime();
        long deadline = start + msToNanos(timeoutMs);

        // Timestamps (en nanos) indicando cuándo cada locator empezó a ser visible de forma
        // continua. -1 significa "no visto" actualmente.
        long errorSeenAt = -1;
        long successSeenAt = -1;

        // Bucle de polling hasta deadline
        while (System.nanoTime() < deadline) {
            long now = System.nanoTime();

            // safeVisible devuelve false en caso de excepción o si el locator es null
            boolean errVisible = safeVisible(errorRoot);
            boolean okVisible  = safeVisible(successRoot);

            // Track continuous-visibility start times
            // Si un locator pasa a visible, guardamos el tiempo de inicio;
            // si deja de ser visible, reiniciamos el timestamp a -1.
            // Comentario: esto permite detectar que un elemento se muestra de forma "continua"
            // durante la ventana de quietMs. Si aparece y desaparece rápidamente no se considera estable.
            if (errVisible) {
                if (errorSeenAt < 0) errorSeenAt = now;
            } else {
                errorSeenAt = -1;
            }

            if (okVisible) {
                if (successSeenAt < 0) successSeenAt = now;
            } else {
                successSeenAt = -1;
            }

            // Determinar si cada uno es "estable": visible y visible durante al menos quietMs.
            // Nota: si quietMs <= 0, aceptamos la visibilidad instantánea (sin ventana).
            // Comentario: 'estable' evita falsos positivos causados por parpadeos del DOM.
            boolean errStable = errVisible && (quietMs <= 0 || (now - errorSeenAt) >= msToNanos(quietMs));
            boolean okStable  = okVisible  && (quietMs <= 0 || (now - successSeenAt) >= msToNanos(quietMs));

            // Si ambos son estables, desempatar por quién empezó a ser visible primero.
            // Si los timestamps son iguales, la condición errorSeenAt <= successSeenAt favorecerá ERROR.
            // Comentario: el desempate por timestamp cubre casos donde ambos elementos aparecen casi al
            // mismo tiempo; se asume que el que apareció primero es el estado "válido".
            if (errStable && okStable) {
                boolean errorWasFirst = errorSeenAt <= successSeenAt;
                return errorWasFirst
                        ? errorResult(errorRoot, errorMessageLocator, start)
                        : new Result(Outcome.SUCCESS, null, elapsedMs(start));
            }

            // Si sólo uno es estable, devolvemos el resultado correspondiente.
            // Comentario: devolvemos inmediatamente en cuanto detectamos estabilidad de uno solo.
            if (errStable) return errorResult(errorRoot, errorMessageLocator, start);
            if (okStable)  return new Result(Outcome.SUCCESS, null, elapsedMs(start));

            // Light polling: esperamos al menos 50ms o pollMs, el que sea mayor.
            // Esto evita hacer spin y reduce carga de CPU.
            // Comentario: la espera es intencionalmente corta para reaccionar rápido, pero suficiente
            // para no saturar la CPU. Si la UI hace cambios más lentos aumentar pollMs/quietMs.
            page.waitForTimeout(Math.max(50, pollMs)); // light polling
        }

        // Si se agota el tiempo, devolvemos TIMEOUT
        // Comentario: no se asumió ni ERROR ni SUCCESS dentro del periodo de timeout.
        return new Result(Outcome.TIMEOUT, null, elapsedMs(start));
    }

    // Construye un Result de ERROR intentando leer un texto de mensaje (si existe).
    // Comentario: si se proporciona `msgLocator` se usa para extraer el texto del error, si no
    // se intenta leer directamente del `errorRoot`.
    private static Result errorResult(Locator errorRoot, Locator msgLocator, long start) {
        String msg = safeText(msgLocator != null ? msgLocator : errorRoot);
        return new Result(Outcome.ERROR, msg, elapsedMs(start));
    }

    // Helpers "seguros" que capturan excepciones de Playwright y devuelven un valor por defecto.
    // Esto evita que cambios DOM o elementos removidos rompan la espera.
    // Comentario: isVisible/innerText pueden lanzar si el locator apunta a un elemento ya removido;
    // estos wrappers garantizan que la espera continúe en esos casos.
    private static boolean safeVisible(Locator locator) {
        try { return locator.isVisible(); } catch (Exception e) { return false; }
    }

    private static String safeText(Locator locator) {
        try { return locator.innerText(); } catch (Exception e) { return null; }
    }

    private static long elapsedMs(long startNanos) {
        return (System.nanoTime() - startNanos) / 1_000_000L;
    }

    private static long msToNanos(long ms) {
        return ms * 1_000_000L;
    }
}