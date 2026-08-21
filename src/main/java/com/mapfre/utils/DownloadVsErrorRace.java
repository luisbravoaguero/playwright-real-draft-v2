package com.mapfre.utils;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public final class DownloadVsErrorRace {

    private DownloadVsErrorRace() {}

    public enum Outcome {
        ERROR_UI_FIRST,
        DOWNLOAD_SUCCESS,
        DOWNLOAD_FAILED,
        TIMEOUT
    }

    public record Result(
            Outcome outcome,
            String uiErrorText,
            String filename,
            String url,
            String downloadFailure,
            Path tempPath,
            Download download,
            long elapsedMs
    ) {}

    public static Result race(
            Page page,
            Runnable triggerDownload,
            Locator errorTitle,
            Locator errorDescription,
            long timeoutMs,
            long quietMs,
            long pollMs
    ) {
        Objects.requireNonNull(page, "page");
        Objects.requireNonNull(triggerDownload, "triggerDownload");
        Objects.requireNonNull(errorTitle, "errorTitle");
        Objects.requireNonNull(errorDescription, "errorDescription");

        long startNanos = System.nanoTime();
        long deadlineMs = System.currentTimeMillis() + timeoutMs;

        AtomicReference<Download> downloadRef = new AtomicReference<>();

        // ✅ Playwright Java uses Consumer<Download>
        Consumer<Download> listener = downloadRef::set;
        page.onDownload(listener);

        ExecutorService exec = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "dl-wait-" + UUID.randomUUID());
            t.setDaemon(true);
            return t;
        });

        CompletableFuture<Completion> completionFuture = null;

        try {
            triggerDownload.run();

            long errorVisibleSince = -1;

            while (System.currentTimeMillis() < deadlineMs) {

                // A) UI error (must be stable for quietMs)
                boolean titleVisible = safeVisible(errorTitle);
                boolean descVisible  = safeVisible(errorDescription);

                if (titleVisible && descVisible) {
                    if (errorVisibleSince < 0) errorVisibleSince = System.currentTimeMillis();

                    if (quietMs <= 0 || (System.currentTimeMillis() - errorVisibleSince) >= quietMs) {
                        return new Result(
                                Outcome.ERROR_UI_FIRST,
                                safeText(errorDescription),
                                null, null, null, null,
                                null,
                                elapsedMs(startNanos)
                        );
                    }
                } else {
                    errorVisibleSince = -1;
                }

                // B) Once download STARTS, start waiting for completion in background
                Download d = downloadRef.get();
                if (d != null && completionFuture == null) {
                    completionFuture = CompletableFuture.supplyAsync(() -> waitDownloadCompletion(d), exec);
                }

                // C) If completion finished, decide success/fail
                if (completionFuture != null && completionFuture.isDone()) {
                    Completion c = completionFuture.join();

                    String filename = safe(() -> d.suggestedFilename());
                    String url = safe(() -> d.url());

                    Download completedDownload = downloadRef.get();

                    if (c.failure != null && !c.failure.isBlank()) {
                        return new Result(
                                Outcome.DOWNLOAD_FAILED,
                                null,
                                filename,
                                url,
                                c.failure,
                                c.tempPath,
                                completedDownload,
                                elapsedMs(startNanos)
                        );
                    }

                    return new Result(
                            Outcome.DOWNLOAD_SUCCESS,
                            null,
                            filename,
                            url,
                            null,
                            c.tempPath,
                            completedDownload,
                            elapsedMs(startNanos)
                    );
                }

                page.waitForTimeout(Math.max(50, pollMs));
            }

            return new Result(Outcome.TIMEOUT, null, null, null, null, null, null, elapsedMs(startNanos));

        } finally {
            // Some Playwright Java versions support offDownload, some might not.
            try { page.offDownload(listener); } catch (Throwable ignored) {}
            exec.shutdownNow();
        }
    }

    // ---------------- internals ----------------

    private static final class Completion {
        final Path tempPath;
        final String failure;
        Completion(Path tempPath, String failure) {
            this.tempPath = tempPath;
            this.failure = failure;
        }
    }

    private static Completion waitDownloadCompletion(Download d) {
        Path p = null;
        try { p = d.path(); } catch (Throwable ignored) {}      // blocks until finished
        String failure = null;
        try { failure = d.failure(); } catch (Throwable ignored) {}
        return new Completion(p, failure);
    }

    private static boolean safeVisible(Locator locator) {
        try { return locator.isVisible(); } catch (Throwable e) { return false; }
    }

    private static String safeText(Locator locator) {
        try { return locator.innerText(); } catch (Throwable e) { return null; }
    }

    private static String safe(UnsafeStringSupplier s) {
        try { return s.get(); } catch (Throwable e) { return null; }
    }

    @FunctionalInterface
    private interface UnsafeStringSupplier {
        String get();
    }

    private static long elapsedMs(long startNanos) {
        return (System.nanoTime() - startNanos) / 1_000_000L;
    }
}