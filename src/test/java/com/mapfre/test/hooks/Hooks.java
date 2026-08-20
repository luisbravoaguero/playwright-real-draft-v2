package com.mapfre.test.hooks;

import com.mapfre.playwright.driver.DriverFactory;
import com.mapfre.playwright.driver.DriverManager;
import com.mapfre.reporting.ArtifactSinks;
import com.mapfre.reporting.ExtentReportManager;
import com.mapfre.reporting.ReportLogger;
import com.mapfre.test.reporting.CucumberExtentArtifactSink;
import io.cucumber.java.*;
import io.cucumber.java.Scenario;

public class Hooks {

    private final ScenarioContext scenarioContext;
    private final ScenarioTabs scenarioTabs;

    public Hooks(ScenarioContext scenarioContext, ScenarioTabs scenarioTabs) {
        this.scenarioContext = scenarioContext;
        this.scenarioTabs = scenarioTabs;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        // Sink for Checkpoint.capture(...)
        ArtifactSinks.set(new CucumberExtentArtifactSink(scenario));

        // Driver
        DriverFactory.init();

        // Ensure Extent test exists for this thread (plugin may have created it already)
        try {
            ExtentReportManager.test();
        } catch (IllegalStateException e) {
            ExtentReportManager.createTest(scenario.getName());
        }

        ReportLogger.info("Scenario started: " + scenario.getName());
        scenarioContext.put("scenarioName", scenario.getName());
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        // Cucumber report screenshot after each step (as you requested)
        try {
            String base64 = com.mapfre.utils.ScreenshotUtils.screenshotBase64();
            byte[] screenshotBytes = java.util.Base64.getDecoder().decode(base64);
            scenario.attach(screenshotBytes, "image/png", "Step Screenshot");
        } catch (Exception e) {
            ReportLogger.fail("No se pudo capturar la pantalla en el paso: " + e.getMessage());
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                // Keep Cucumber failure screenshot if you want
                try {
                    String base64 = com.mapfre.utils.ScreenshotUtils.screenshotBase64();
                    scenario.attach(java.util.Base64.getDecoder().decode(base64), "image/png", "failure-screenshot");
                } catch (Exception ignored) {}
                ReportLogger.fail("Scenario failed: " + scenario.getName());
            } else {
                ReportLogger.pass("Scenario passed: " + scenario.getName());
            }
        } finally {
            try {
                // Close scenario-owned popups before the context and the Playwright runtime.
                scenarioTabs.close();
            } finally {
                DriverManager.cleanup();
                ArtifactSinks.clear();
                ExtentReportManager.clearTest();
            }
        }
    }
}
