package com.mapfre.playwright.driver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.mapfre.config.ConfigManager;

import java.util.List;

public final class BrowserOptions {
    private BrowserOptions() {}
    /**
     * Configura las opciones de lanzamiento del binario del navegador.
     *
     * @return LaunchOptions configuradas para el hilo actual.
     */
    public static BrowserType.LaunchOptions launchOptions() {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(ConfigManager.isHeadless());

        if (ConfigManager.isMaximize()) {
            options.setArgs(List.of("--start-maximized"));
        }

        options.setArgs(mergeArgs(options.args, List.of(
                "--no-sandbox",
                "--disable-dev-shm-usage"
        )));
        return options;
    }

     //Configura las opciones del contexto del navegador (Sesión).
     //El contexto es lo que garantiza el aislamiento entre pruebas en paralelo.
    public static Browser.NewContextOptions contextOptions() {

        Browser.NewContextOptions options = new Browser.NewContextOptions();

        //if (ConfigManager.isMaximize()) {
            //options.setViewportSize(null);
        //} else {
            options.setViewportSize(1920, 1080);
        //}

        /*
         * Puntos de extensión (Puedes descomentar según necesites):
         *
         * options.setIgnoreHTTPSErrors(true);
         * options.setAcceptDownloads(true);
         * options.setLocale("es-ES");
         * options.setTimezoneId("America/Lima");
         */
        options.setAcceptDownloads(true);

        return options;
    }

    private static List<String> mergeArgs(List<String> current, List<String> extra) {
        if (current == null || current.isEmpty()) return extra;
        java.util.ArrayList<String> merged = new java.util.ArrayList<>(current);
        for (String a : extra) if (!merged.contains(a)) merged.add(a);
        return merged;
    }
}
