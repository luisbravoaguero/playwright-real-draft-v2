package com.mapfre.reporting;

public final class ReportLogger {
    private ReportLogger() {}

    public static void info(String msg) {
        ExtentReportManager.test().info(msg);
    }

    public static void pass(String msg) {
        ExtentReportManager.test().pass(msg);
    }

    public static void fail(String msg) {
        ExtentReportManager.test().fail(msg);
    }
}
