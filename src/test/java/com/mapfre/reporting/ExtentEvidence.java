package com.mapfre.reporting;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.mapfre.utils.ScreenshotUtils;

public final class ExtentEvidence {
    private ExtentEvidence() {}

    /** Attach a screenshot as an INFO entry (works multiple times). */
    public static void shot(String title) {
        try {
            String base64 = ScreenshotUtils.screenshotBase64();
            ExtentReportManager.current().info(
                    title,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build()
            );
        } catch (Exception e) {
            // If screenshot fails, still log the text
            ExtentReportManager.current().info(title + " (screenshot failed: " + e.getMessage() + ")");
        }
    }

    /** Attach screenshot as FAIL (useful when you want to fail immediately). */
    public static void failShot(String title, Throwable error) {
        try {
            String base64 = ScreenshotUtils.screenshotBase64();
            ExtentReportManager.current().fail(
                    error,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build()
            );
        } catch (Exception e) {
            ExtentReportManager.current().fail(error);
        }
    }
}
