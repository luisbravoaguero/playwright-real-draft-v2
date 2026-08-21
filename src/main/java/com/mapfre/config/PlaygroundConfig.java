package com.mapfre.config;

import com.mapfre.exceptions.FrameworkException;

/**
 * Configuration for Playground (Bondar Academy) - used for technical/multi-tab testing scenarios.
 * This is separate from the main application URL to allow testing multi-tab scenarios.
 */
public final class PlaygroundConfig {
    private PlaygroundConfig() {}

    public static String getPlaygroundUrl() {
        String env = ConfigManager.getEnv();
        String key = "playground.url." + env;

        String url = ConfigManager.getGlobal(key);
        if (url == null || url.isBlank()) {
            throw new FrameworkException("Falta la URL del playground para el entorno. Se esperaba un key.: " + key);
        }
        return url;
    }

    /**
     * Extracts the host from the Playground URL for validation purposes.
     * Example: "https://www.playground.bondaracademy.com/" → "www.playground.bondaracademy.com"
     */
    public static String getPlaygroundHost() {
        String url = getPlaygroundUrl();
        try {
            java.net.URI uri = java.net.URI.create(url);
            return uri.getHost();
        } catch (Exception e) {
            throw new FrameworkException("No se pudo extraer el host de la URL del playground: " + url, e);
        }
    }
}
