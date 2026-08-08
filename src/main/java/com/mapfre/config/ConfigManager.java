package com.mapfre.config;

import com.mapfre.exceptions.FrameworkException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {
    private static final Properties DRIVER_PROPS = new Properties();
    private static final Properties GLOBAL_PROPS = new Properties();
    private static boolean loaded = false;

    private ConfigManager() {}

    public static synchronized void load() {
        if (loaded) return;
        loadProps("driver.properties", DRIVER_PROPS);
        loadProps("global.properties", GLOBAL_PROPS);
        loaded = true;
    }

    private static void loadProps(String resourceName, Properties target) {
        try (InputStream in = ConfigManager.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) throw new FrameworkException("Archivo de recursos faltante: " + resourceName);
            target.load(in);
        } catch (IOException e) {
            throw new FrameworkException("No se pudo cargar el recurso: " + resourceName, e);
        }
    }

    private static String overrideValue(String key) {
        // Priority: -D key > ENV KEY (KEY uppercase, dots->underscores) > null
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) return sys;

        String envKey = key.toUpperCase().replace('.', '_');
        String env = System.getenv(envKey);
        if (env != null && !env.isBlank()) return env;

        return null;
    }

    private static String get(Properties props, String key, String defaultValue) {
        load();
        String override = overrideValue(key);
        if (override != null) return override;

        String v = props.getProperty(key);
        if (v != null && !v.isBlank()) return v;

        return defaultValue;
    }

    public static String getBrowser() {
        return get(DRIVER_PROPS, "browser", "chromium").toLowerCase();
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get(DRIVER_PROPS, "headless", "true"));
    }

    public static boolean isMaximize() {
        return Boolean.parseBoolean(get(DRIVER_PROPS, "maximize", "true"));
    }

    public static double getDefaultTimeoutMs() {
        String v = get(DRIVER_PROPS, "pageLoadTimeout", "40000");
        try {
            return Double.parseDouble(v);
        } catch (NumberFormatException e) {
            throw new FrameworkException("Invalid defaultTimeoutMs: " + v, e);
        }
    }

    public static String getEnv() {
        return get(GLOBAL_PROPS, "env", "qa").toLowerCase();
    }

    public static String getProperty(String key) {
        return GLOBAL_PROPS.getProperty(key);
    }

    public static String getGlobal(String key) {
        return get(GLOBAL_PROPS, key, null);
    }
}
