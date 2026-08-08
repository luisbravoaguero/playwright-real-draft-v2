package com.mapfre.reporting;

public final class ArtifactSinks {

    private static final ThreadLocal<ArtifactSink> SINK = new ThreadLocal<>();

    private ArtifactSinks() {}

    public static void set(ArtifactSink sink) { SINK.set(sink); }

    public static ArtifactSink get() {
        ArtifactSink sink = SINK.get();
        return sink != null ? sink : (name, base64) -> {}; // no-op default
    }

    public static void clear() { SINK.remove(); }
}
