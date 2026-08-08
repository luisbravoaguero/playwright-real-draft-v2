package com.mapfre.utils.waits; // paquete del proyecto

import com.microsoft.playwright.Locator; // import para Playwright Locator
import com.microsoft.playwright.Page; // import para Playwright Page
import java.util.Arrays; // utilidad para trabajar con arrays

/**
 * Clase especializada para esperar la primera aparición estable entre un error y
 * varios posibles elementos de éxito.
 * Comentarios: cada línea está documentada para aclarar propósito y funcionamiento.
 */
public final class FirstAppearanceRaceMultiSuccess {

    // Constructor privado para evitar instanciación (clase utilitaria)
    private FirstAppearanceRaceMultiSuccess() {}

    // Resultado posible de la espera: ERROR, SUCCESS o TIMEOUT
    public enum Outcome { ERROR, SUCCESS, TIMEOUT }

    // Registro que contiene el resultado completo junto con el índice del success que ganó.
    // successIndex: índice (0..n-1) del successRoots que se estabilizó primero, -1 si no aplica.
    public record Result(Outcome outcome, String errorMessage, long elapsedMs, int successIndex) {}

    /**
     * Espera hasta que aparezca y se estabilice primero o bien `errorRoot` o cualquiera de los
     * locators en `successRoots`.
     *
     * Parámetros esperados (contracto):
     * - page: instancia Playwright Page (usada para sleep/polling y operaciones locator).
     * - action: Runnable opcional que dispara el cambio (por ejemplo un click). Puede ser null.
     * - errorRoot: locator que representa la UI de error (puede ser null, en cuyo caso nunca aparecerá error).
     * - successRoots: array de locators que representan alternativas de éxito; el índice en el array
     *   se usará para identificar cuál success ocurrió (orden significativo).
     * - errorMessageLocator: locator opcional para extraer texto de error; si es null se lee del errorRoot.
     * - timeoutMs: tiempo máximo en milisegundos a esperar antes de TIMEOUT.
     * - quietMs: ventana de estabilidad en ms (elemento debe permanecer visible ese tiempo para considerarse estable).
     * - pollMs: intervalo de sondeo en ms (se respeta un mínimo para evitar busy-waiting).
     *
     * Devuelve un `Result` con `successIndex` >= 0 cuando outcome == SUCCESS indicando cuál success ganó.
     */
    public static Result waitForFirst(
            Page page,
            Runnable action,
            Locator errorRoot,
            Locator[] successRoots,
            Locator errorMessageLocator,
            long timeoutMs,
            long quietMs,
            long pollMs
    ) {
        // Si no hay successRoots válidos, delegamos en la implementación clásica (si existe)
        if (successRoots == null || successRoots.length == 0) {
            // Llamamos a FirstAppearanceRace existente para mantener compatibilidad.
            var r = FirstAppearanceRace.waitForFirst(page, action, errorRoot, null, errorMessageLocator, timeoutMs, quietMs, pollMs);
            int idx = -1; // no hay índice de success
            // Mapear el Outcome del otro record al Outcome local (mismos nombres)
            Outcome mapped = Outcome.valueOf(r.outcome().name());
            return new Result(mapped, r.errorMessage(), r.elapsedMs(), idx);
        }

        // Ejecutar la acción disparadora si fue proporcionada
        if (action != null) action.run();

        long start = System.nanoTime(); // tiempo de inicio en nanos
        long deadline = start + msToNanos(timeoutMs); // instante límite

        long errorSeenAt = -1; // timestamp de cuándo el error empezó a verse continuamente
        long[] successSeenAt = new long[successRoots.length]; // timestamps para cada success
        Arrays.fill(successSeenAt, -1L); // inicializar a -1 (no visto)

        // Bucle de sondeo hasta agotar el deadline
        while (System.nanoTime() < deadline) {
            long now = System.nanoTime();

            // Comprobar visibilidad de error (seguro)
            boolean errVisible = safeVisible(errorRoot);
            if (errVisible) {
                if (errorSeenAt < 0) errorSeenAt = now; // marca inicio de visibilidad continua
            } else {
                errorSeenAt = -1; // resetea si dejó de ser visible
            }

            // Actualizar visibilidad/tiempos para cada success
            for (int i = 0; i < successRoots.length; i++) {
                Locator s = successRoots[i]; // locator i-ésimo
                boolean vis = safeVisible(s); // comprobación segura de visibilidad
                if (vis) {
                    if (successSeenAt[i] < 0) successSeenAt[i] = now; // marca inicio si nuevo
                } else {
                    successSeenAt[i] = -1; // resetea si dejó de ser visible
                }
            }

            // Determinar si hay alguno success estable y cuál es el primero
            boolean anyStableSuccess = false; // indicador si existe al menos un success estable
            long earliestStableSuccessSeenAt = Long.MAX_VALUE; // timestamp más temprano entre success estables
            int earliestStableSuccessIndex = -1; // índice del earliest
            for (int i = 0; i < successRoots.length; i++) {
                long seen = successSeenAt[i]; // tiempo de inicio de visibilidad continua del success i
                if (seen >= 0) {
                    boolean stable = (quietMs <= 0) || ((now - seen) >= msToNanos(quietMs)); // si cumplió quiet window
                    if (stable) {
                        anyStableSuccess = true; // hay al menos uno estable
                        if (seen < earliestStableSuccessSeenAt) {
                            earliestStableSuccessSeenAt = seen; // actualiza earliest
                            earliestStableSuccessIndex = i; // guarda índice
                        }
                    }
                }
            }

            // Determinar si error es estable
            boolean errStable = errVisible && (quietMs <= 0 || (now - errorSeenAt) >= msToNanos(quietMs));

            // Si ambos son estables, desempatar por timestamp de aparición
            if (errStable && anyStableSuccess) {
                boolean errorWasFirst = errorSeenAt <= earliestStableSuccessSeenAt; // desempate
                if (errorWasFirst) {
                    return errorResult(errorRoot, errorMessageLocator, start, -1); // error, no successIndex
                } else {
                    return new Result(Outcome.SUCCESS, null, elapsedMs(start), earliestStableSuccessIndex);
                }
            }

            // Si sólo uno es estable devolvemos inmediatamente
            if (errStable) return errorResult(errorRoot, errorMessageLocator, start, -1);
            if (anyStableSuccess) return new Result(Outcome.SUCCESS, null, elapsedMs(start), earliestStableSuccessIndex);

            // Espera ligera para no saturar CPU (mínimo 50ms)
            page.waitForTimeout(Math.max(50, pollMs));
        }

        // Si se agota el tiempo devolvemos TIMEOUT con successIndex=-1
        return new Result(Outcome.TIMEOUT, null, elapsedMs(start), -1);
    }

    // Construye un Result de ERROR intentando leer un texto de mensaje (si existe) y añadiendo index
    private static Result errorResult(Locator errorRoot, Locator msgLocator, long start, int successIndex) {
        String msg = safeText(msgLocator != null ? msgLocator : errorRoot);
        return new Result(Outcome.ERROR, msg, elapsedMs(start), successIndex);
    }

    // Wrapper seguro para isVisible(): captura excepciones y devuelve false si locator es null o falla
    private static boolean safeVisible(Locator locator) {
        try { return locator != null && locator.isVisible(); } catch (Exception e) { return false; }
    }

    // Wrapper seguro para innerText(): captura excepciones y devuelve null en fallo
    private static String safeText(Locator locator) {
        try { return locator == null ? null : locator.innerText(); } catch (Exception e) { return null; }
    }

    // Calcula milisegundos transcurridos desde `startNanos`
    private static long elapsedMs(long startNanos) { return (System.nanoTime() - startNanos) / 1_000_000L; }

    // Convierte ms a nanos
    private static long msToNanos(long ms) { return ms * 1_000_000L; }
}
