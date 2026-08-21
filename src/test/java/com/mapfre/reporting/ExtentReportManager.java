package com.mapfre.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.JsonFormatter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.mapfre.config.ConfigManager;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentReportManager {
    private static ExtentReports extent;

    private static final ThreadLocal<ExtentTest> TL_TEST = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> TL_STEP = new ThreadLocal<>();

    private ExtentReportManager() {}

    private static Path outDir() {
        return Path.of(System.getProperty("extent.out.dir", "target/extent-reports"));
    }

    public static synchronized void init() {
        if (extent != null) return;

        try {
            Path dir = outDir();
            Files.createDirectories(dir);

            // 1) JSON archive (mergeable) - siempre
            JsonFormatter json = new JsonFormatter(dir.resolve("extent.json").toString());

            extent = new ExtentReports();
            extent.attachReporter(json);

            // 2) (Opcional) HTML por pod solo si se habilita (por defecto NO)
            boolean sparkEnabled = Boolean.parseBoolean(System.getProperty("extent.spark.enabled", "true"));
            if (sparkEnabled) {
                ExtentSparkReporter spark = new ExtentSparkReporter(dir.resolve("index.html").toString());
                spark.config().setTheme(Theme.STANDARD);
                spark.config().setDocumentTitle("Reporte de Automatización - MAPFRE");
                spark.config().setReportName("Pruebas Funcionales OIM");
                spark.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
                extent.attachReporter(spark);
            }

            extent.setSystemInfo("Ambiente", ConfigManager.getEnv());
            extent.setSystemInfo("Navegador", ConfigManager.getBrowser());
            extent.setSystemInfo("Usuario", System.getProperty("user.name"));

        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar ExtentReports", e);
        }
    }

    public static void createTest(String name) {
        init();
        TL_TEST.set(extent.createTest(name));
    }

    public static ExtentTest test() {
        ExtentTest currentTest = TL_TEST.get();
        if (currentTest == null) {
            throw new IllegalStateException("El test no ha sido iniciado en el ThreadLocal.");
        }
        return currentTest;
    }

    public static void setStepNode(ExtentTest node) { TL_STEP.set(node); }
    public static void clearStepNode() { TL_STEP.remove(); }

    public static ExtentTest current() {
        ExtentTest step = TL_STEP.get();
        return (step != null) ? step : test();
    }

    public static synchronized void flush() {
        if (extent != null) extent.flush();
    }

    public static void clearTest() {
        TL_STEP.remove();
        TL_TEST.remove();
    }
}