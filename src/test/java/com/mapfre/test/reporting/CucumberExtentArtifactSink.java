package com.mapfre.test.reporting;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.mapfre.reporting.ArtifactSink;
import com.mapfre.reporting.ExtentReportManager;
import io.cucumber.java.Scenario;
import java.util.Base64;

public class CucumberExtentArtifactSink implements ArtifactSink {

    private final Scenario scenario;

    public CucumberExtentArtifactSink(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public void onCheckpoint(String name, String base64Png) {
        // Attach to Cucumber report
        scenario.attach(Base64.getDecoder().decode(base64Png), "image/png", "checkpoint-" + safe(name));

        // Attach to Extent report INSIDE the current step node (if present)
        ExtentReportManager.current().info(
                "Checkpoint: " + name,
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Png).build()
        );
    }

    private String safe(String name) {
        if (name == null) return "checkpoint";
        return name.replaceAll("[^a-zA-Z0-9-_]+", "_");
    }
}