package com.mapfre.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class ExtentJsonMergeMain {
    public static void main(String[] args) throws Exception {
        String inDir = System.getProperty("extent.merge.input", "target/extent-archives");
        String outDir = System.getProperty("extent.merge.output", "target/extent-merged");

        Files.createDirectories(Path.of(outDir));

        ExtentSparkReporter spark = new ExtentSparkReporter(Path.of(outDir, "index.html").toString());
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);

        try (Stream<Path> s = Files.walk(Path.of(inDir))) {
            s.filter(p -> p.getFileName().toString().equalsIgnoreCase("extent.json"))
                    .forEach(p -> {
                        try {
                            extent.createDomainFromJsonArchive(p.toString());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        extent.flush();
        System.out.println("Merged extent: " + Path.of(outDir, "index.html"));
    }
}