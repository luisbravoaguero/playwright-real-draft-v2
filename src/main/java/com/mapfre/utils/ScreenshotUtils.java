package com.mapfre.utils;

import com.mapfre.playwright.driver.DriverManager;
import com.microsoft.playwright.Page;
import java.util.Base64;
import java.util.List;

public final class ScreenshotUtils {
    private ScreenshotUtils() {}

    public static String screenshotBase64() {
        Page page = getActivePage();
        byte[] bytes = page.screenshot();
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Returns the active page to screenshot.
     * If multiple pages exist in the context (popup scenario), returns the last opened page.
     * Otherwise, returns the main page.
     * This ensures screenshots capture the correct page without breaking existing single-page scenarios.
     */
    private static Page getActivePage() {
        Page mainPage = DriverManager.page();
        
        try {
            List<Page> allPages = mainPage.context().pages();
            
            if (allPages.size() > 1) {
                // Multiple pages: return the last opened (most likely the popup)
                Page lastPage = allPages.get(allPages.size() - 1);
                if (!lastPage.isClosed()) {
                    return lastPage;
                }
            }
        } catch (Exception e) {
            // If anything fails, fall back to main page
        }
        
        return mainPage;
    }
}

