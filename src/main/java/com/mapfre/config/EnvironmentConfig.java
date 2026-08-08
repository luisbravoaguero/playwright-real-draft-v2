package com.mapfre.config;

import com.mapfre.exceptions.FrameworkException;

public final class EnvironmentConfig {
    private EnvironmentConfig() {}

    public static String getAppUrl() {
        String env = ConfigManager.getEnv();
        String key = "app.url." + env;

        String url = ConfigManager.getGlobal(key);
        if (url == null || url.isBlank()) {
            throw new FrameworkException("Falta la URL de la aplicación para el entorno. Se esperaba un key.: " + key);
        }
        return url;
    }
}
