package com.mapfre.utils;

import com.mapfre.playwright.driver.DriverManager;
import java.util.Base64;

public final class ScreenshotUtils {
    private ScreenshotUtils() {}

    public static String screenshotBase64() {
        byte[] bytes = DriverManager.page().screenshot();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
