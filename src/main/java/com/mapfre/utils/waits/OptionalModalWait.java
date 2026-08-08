package com.mapfre.utils.waits;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Clase para esperar opcionalmente a que un modal o elemento aparezca y se estabilice.
 * Si el elemento no aparece dentro del tiempo especificado, simplemente devuelve false.
 */
public final class OptionalModalWait {

    private OptionalModalWait() {}

    /**
     * Espera a que un modal opcional aparezca y se estabilice dentro del tiempo especificado.
     *
     * @param page Instancia de Playwright Page para manejar el tiempo de espera.
     * @param modalLocator Locator del modal o elemento opcional.
     * @param timeoutMs Tiempo máximo de espera en milisegundos.
     * @param quietMs Tiempo de estabilidad requerido en milisegundos.
     * @param pollMs Intervalo de sondeo en milisegundos.
     * @return True si el modal aparece y se estabiliza, false si no aparece.
     */
    public static boolean waitForOptionalModal(
            Page page,
            Locator modalLocator,
            long timeoutMs,
            long quietMs,
            long pollMs
    ) {
        long start = System.nanoTime();
        long deadline = start + msToNanos(timeoutMs);
        long seenAt = -1;

        while (System.nanoTime() < deadline) {
            long now = System.nanoTime();

            boolean isVisible = safeVisible(modalLocator);

            if (isVisible) {
                if (seenAt < 0) seenAt = now;
            } else {
                seenAt = -1;
            }

            boolean isStable = isVisible && (quietMs <= 0 || (now - seenAt) >= msToNanos(quietMs));

            if (isStable) {
                return true;
            }

            page.waitForTimeout(Math.max(50, pollMs));
        }

        return false;
    }

    private static boolean safeVisible(Locator locator) {
        try { return locator.isVisible(); } catch (Exception e) { return false; }
    }

    private static long msToNanos(long ms) {
        return ms * 1_000_000L;
    }
}
