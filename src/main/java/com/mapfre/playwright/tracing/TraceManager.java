package com.mapfre.playwright.tracing;

import com.mapfre.config.ConfigManager;
import com.mapfre.playwright.driver.DriverManager;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TraceManager {
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
    private static final Path TRACE_DIRECTORY = Paths.get("target", "traces");

    private TraceManager() {}

    public static void start(String scenarioName) {
        if (!ConfigManager.isTracingEnabled()) {
            return;
        }

        BrowserContext context = DriverManager.context();
        if (context == null) {
            throw new IllegalStateException("No existe un BrowserContext activo para iniciar Playwright tracing.");
        }

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)
                .setTitle(scenarioName));
    }

    public static Path stop(String scenarioName, boolean failed) {
        if (!ConfigManager.isTracingEnabled()) {
            return null;
        }

        BrowserContext context = DriverManager.context();
        if (context == null) {
            return null;
        }

        boolean shouldSave = failed || !ConfigManager.isTraceRetainOnFailureOnly();
        if (!shouldSave) {
            context.tracing().stop();
            return null;
        }

        Path tracePath = createTracePath(scenarioName);
        context.tracing().stop(new Tracing.StopOptions().setPath(tracePath));
        return tracePath;
    }

    private static Path createTracePath(String scenarioName) {
        try {
            Files.createDirectories(TRACE_DIRECTORY);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo crear el directorio de trazas: " + TRACE_DIRECTORY, e);
        }

        String safeScenarioName = scenarioName == null || scenarioName.isBlank()
                ? "scenario"
                : scenarioName
                .replaceAll("[^a-zA-Z0-9áéíóúÁÉÍÓÚñÑ._-]+", "_")
                .replaceAll("^_+|_+$", "");

        String timestamp = LocalDateTime.now().format(TIMESTAMP);
        long threadId = Thread.currentThread().getId();
        String fileName = safeScenarioName + "_" + timestamp + "_thread-" + threadId + ".zip";

        return TRACE_DIRECTORY.resolve(fileName);
    }
}