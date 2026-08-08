package com.mapfre.utils.waits;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.function.Predicate;

/**
 * Espera hasta que el texto de un locator cumpla una condición (predicate).
 */
public final class ValueWait {

    private ValueWait() {}

    public record Result(boolean success, String lastValue, long elapsedMs) {}

    /**
     * Polls the text content of a locator until the predicate is satisfied.
     *
     * @param page        Playwright page (for polling sleep)
     * @param locator     element whose text to evaluate
     * @param condition   predicate applied to the trimmed text
     * @param timeoutMs   max wait time
     * @param pollMs      polling interval
     */
    public static Result waitUntilText(Page page, Locator locator, Predicate<String> condition, long timeoutMs, long pollMs) {
        long start = System.nanoTime();
        long deadline = start + timeoutMs * 1_000_000L;
        String lastValue = "";

        while (System.nanoTime() < deadline) {
            try {
                lastValue = locator.textContent().trim();
                if (condition.test(lastValue)) {
                    return new Result(true, lastValue, elapsedMs(start));
                }
            } catch (Exception e) {
                // element not ready yet, continue polling
            }
            page.waitForTimeout(Math.max(50, pollMs));
        }

        return new Result(false, lastValue, elapsedMs(start));
    }

    private static long elapsedMs(long startNanos) {
        return (System.nanoTime() - startNanos) / 1_000_000L;
    }
}
