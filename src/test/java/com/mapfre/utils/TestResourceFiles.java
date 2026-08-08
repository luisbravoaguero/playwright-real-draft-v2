package com.mapfre.utils;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TestResourceFiles {
    private TestResourceFiles() {}

    /**
     * Resolve a file located under src/test/resources as a Path.
     * Example: TestResourceFiles.path("testdata/uploads/planillas/sctr/planilla_ok.xlsx")
     */
    public static Path path(String resourceRelativePath) {
        URL url = TestResourceFiles.class.getClassLoader().getResource(resourceRelativePath);
        if (url == null) {
            throw new IllegalArgumentException("Test resource not found: " + resourceRelativePath);
        }
        try {
            return Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI for resource: " + resourceRelativePath, e);
        }
    }
}