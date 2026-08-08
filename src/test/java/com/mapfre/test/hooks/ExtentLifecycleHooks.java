package com.mapfre.test.hooks;

import com.mapfre.reporting.ExtentReportManager;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;

public class ExtentLifecycleHooks {

    @BeforeAll
    public static void beforeAll() {
        // Ensure reporter is initialized even if no scenario creates a test early
        ExtentReportManager.init();
    }

    @AfterAll
    public static void afterAll() {
        // This is what actually writes target/extent-reports/index.html
        ExtentReportManager.flush();
    }
}