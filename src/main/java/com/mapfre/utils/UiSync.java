package com.mapfre.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.mapfre.config.ConfigManager;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.driver.DriverManager;

public final class UiSync {

    private UiSync() {}

    //Punto de entrada predeterminado para su marco (utiliza DriverManager.page + propiedades).
    public static void waitForAppIdle() {
        Page page = DriverManager.page();
        waitForAppIdle(page);
    }

    //Utiliza el spinner XPath configurado + período de silencio para manejar múltiples ondas.
    public static void waitForAppIdle(Page page) {
        ConfigManager.load();

        String spinnerXpath = ConfigManager.getGlobal("ui.spinner.xpath");
        if (spinnerXpath == null || spinnerXpath.isBlank()) {
            // If you haven't configured it yet, fail early with a clear message
            throw new FrameworkException("Falta el key global.properties: ui.spinner.xpath");
        }

        int quietMs = parseIntOrDefault(ConfigManager.getGlobal("ui.spinner.quietMs"), 500);
        int timeoutMs = parseIntOrDefault(ConfigManager.getGlobal("ui.spinner.timeoutMs"), 30000);

        Locator spinner = page.locator("xpath=" + spinnerXpath).first();
        waitForSpinnerWavesToFinish(spinner, quietMs, timeoutMs);
    }

    //Espera hasta que el spinner se oculte y permanece oculto durante quietMs.
    //Esto gestiona "ola 1 -> ola 2 -> ola 3" usando el mismo localizador.
    public static void waitForSpinnerWavesToFinish(Locator spinner, int quietMs, int timeoutMs) {
        long deadline = System.currentTimeMillis() + timeoutMs;

        while (true) {
            long remaining = deadline - System.currentTimeMillis();
            if (remaining <= 0) {
                throw new FrameworkException("Timed out waiting for spinner waves to finish after " + timeoutMs + "ms");
            }

            // Wait for spinner to become hidden (or detached).
            spinner.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.HIDDEN)
                    .setTimeout((double) remaining));

            // Periodo de silencio: dar tiempo a que aparezca la "ola 2" después de que desaparezca la ola 1.
            // (Usamos intencionalmente la espera de Playwright, no Thread.sleep)
            spinner.page().waitForTimeout(quietMs);

            // Si vuelve a aparecer, repite el bucle.
            if (spinner.isVisible()) {
                continue;
            }

            // Permaneció oculta durante el período de inactividad -> la aplicación está "inactiva"
            return;
        }
    }

    private static int parseIntOrDefault(String value, int defaultVal) {
        try {
            if (value == null || value.isBlank()) return defaultVal;
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return defaultVal;
        }
    }
}
