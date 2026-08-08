package com.mapfre.reporting;

public interface ArtifactSink {
    void onCheckpoint(String name, String base64Png);
}
