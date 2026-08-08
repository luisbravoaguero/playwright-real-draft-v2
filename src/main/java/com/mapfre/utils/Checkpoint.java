package com.mapfre.utils;

import com.mapfre.config.ConfigManager;
import com.mapfre.reporting.ArtifactSinks;

public final class Checkpoint {

    private Checkpoint() {}

    public static void capture(String name) {
        ConfigManager.load();
        if (!enabled()) return;

        String base64 = ScreenshotUtils.screenshotBase64();
        ArtifactSinks.get().onCheckpoint(name, base64);
    }

    private static boolean enabled() {
        return "true".equalsIgnoreCase(ConfigManager.getGlobal("ui.screenshot.checkpoints.enabled"));
    }
}