package com.mapfre.reporting;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConsoleStepLogger implements ConcurrentEventListener {

    private static final Logger log = LoggerFactory.getLogger(ConsoleStepLogger.class);

    // Switches via system properties (CI friendly defaults)
    private static final boolean USE_ANSI =
            Boolean.parseBoolean(System.getProperty("console.logger.ansi", "false")); // default OFF
    private static final boolean USE_EMOJI =
            Boolean.parseBoolean(System.getProperty("console.logger.emoji", "true")); // emoji usually OK
    private static final boolean LOG_SCENARIO_HEADER =
            Boolean.parseBoolean(System.getProperty("console.logger.scenarioHeader", "true"));
    // Tiempos (paralelo seguro)
    private static final Map<String, Long> STEP_START_NS = new ConcurrentHashMap<>();
    private static final Map<String, Long> SCENARIO_START_NS = new ConcurrentHashMap<>();

    // Store scenario name per thread (parallel-safe)
    private static final ThreadLocal<String> SCENARIO_NAME = new ThreadLocal<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::onCaseStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::onStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::onStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onCaseFinished);
    }

    private void onCaseStarted(TestCaseStarted event) {
        TestCase tc = event.getTestCase();

        SCENARIO_NAME.set(tc.getName());
        SCENARIO_START_NS.put(caseKey(tc), System.nanoTime());
        if (!LOG_SCENARIO_HEADER) {
            log.info("[SCENARIO] {}", tc.getName());
            return;
        }

        String title = "SCENARIO: " + tc.getName();
        String uri = String.valueOf(tc.getUri());
        String line = repeat('─', clamp(Math.max(title.length(), uri.length()) + 8, 40, 110));

        log.info(colorize(line, Ansi.CYAN));
        log.info(colorize(center(title, line.length()), Ansi.CYAN_BOLD));
        log.info(colorize(center("Location: " + uri, line.length()), Ansi.CYAN_DIM));
        log.info(colorize(line, Ansi.CYAN));
    }

    private void onStepStarted(TestStepStarted event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep step)) return;

        String keyword = safe(step.getStep().getKeyword()).trim(); // Given/When/Then/And/But
        String text = safe(step.getStep().getText());

        // Clave única por step (paralelo seguro)
        String key = stepKey(event.getTestCase(), step);
        STEP_START_NS.put(key, System.nanoTime());

        String prefix = USE_EMOJI ? "➡ " : "-> ";
        //String scenario = safe(SCENARIO_NAME.get());
        String msg = String.format("[%s] %s %s ", prefix, keyword, text);
        //String msg = String.format("[%s] %s%s %s", scenario, prefix, keyword, text);
        log.info(colorize(msg, Ansi.BLUE_BOLD));
    }

    private void onStepFinished(TestStepFinished event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep)) return;
        if (!(event.getTestStep() instanceof PickleStepTestStep step)) return;

        Result result = event.getResult();
        Status status = result.getStatus();
        Throwable error = result.getError();
        //String scenario = safe(SCENARIO_NAME.get());

        // Calcular duración del step
        String key = stepKey(event.getTestCase(), step);
        Long startNs = STEP_START_NS.remove(key);
        String elapsedStr = "-";
        if (startNs != null) {
            long elapsedNs = System.nanoTime() - startNs;
            elapsedStr = String.format("%.3f", elapsedNs / 1_000_000_000.0);
        }

        String badge = statusBadge(status);
        Ansi color = statusColor(status);
        //log.info(colorize(String.format("%s Status: %s", badge, status), color));
        //log.info(colorize(String.format("[%s]    %s Status: %s", scenario, badge, status), color));
        String line = String.format("%s Status: %s, Tiempo de Ejecución: %s s", badge, status, elapsedStr);
        log.info(colorize(line, color));

        if (error != null) {
            // Keep it concise; full stack trace is already printed by test runner if needed
            log.error(colorize(String.format("Error: %s", error.toString()), Ansi.RED_DIM));
            //log.error(colorize(String.format("[%s]    Error: %s", scenario, error.toString()), Ansi.RED_DIM));

        }
    }

    private void onCaseFinished(TestCaseFinished event) {
        TestCase tc = event.getTestCase();
        Status status = event.getResult().getStatus();

        String label = "END SCENARIO: " + status;
        String line = repeat('─', clamp(label.length() + 8, 40, 110));

        Ansi color = switch (status) {
            case PASSED -> Ansi.GREEN_BOLD;
            case FAILED -> Ansi.RED_BOLD;
            default -> Ansi.CYAN_BOLD;
        };

        log.info(colorize(line, color));
        log.info(colorize(center(label, line.length()), color));

        // Tiempo total del escenario
        Long startNs = SCENARIO_START_NS.remove(caseKey(tc));
        if (startNs != null) {
            long elapsedNs = System.nanoTime() - startNs;
            String totalStr = String.format("%.3f", elapsedNs / 1_000_000_000.0);
            log.info(colorize(center("Tiempo Total de Ejecución: " + totalStr + " s", line.length()), color));
        }


        log.info(colorize(line, color));
        log.info(""); // spacer line

        SCENARIO_NAME.remove();
    }

    // =======================
    // Helpers
    // =======================

    private static String statusBadge(Status status) {
        if (!USE_EMOJI) {
            return switch (status) {
                case PASSED -> "[OK]";
                case FAILED -> "[FAIL]";
                case SKIPPED -> "[SKIP]";
                case PENDING, UNDEFINED, AMBIGUOUS -> "[PENDING]";
                default -> "[INFO]";
            };
        }
        return switch (status) {
            case PASSED -> "✅";
            case FAILED -> "❌";
            case SKIPPED -> "⏭";
            case PENDING, UNDEFINED, AMBIGUOUS -> "⏳";
            default -> "ℹ️";
        };
    }

    private static Ansi statusColor(Status status) {
        return switch (status) {
            case PASSED -> Ansi.GREEN;
            case FAILED -> Ansi.RED_BOLD;
            case SKIPPED -> Ansi.YELLOW;
            default -> Ansi.MAGENTA;
        };
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

    private static String repeat(char ch, int n) {
        return String.valueOf(ch).repeat(Math.max(0, n));
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static String center(String text, int width) {
        if (text == null) text = "";
        if (text.length() >= width) return text;
        int pad = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, pad)) + text;
    }

    private static String colorize(String s, Ansi ansi) {
        if (!USE_ANSI) return s;
        return ansi.open + s + Ansi.RESET.open;
    }

    private enum Ansi {
        RESET("\u001B[0m"),
        BLUE_BOLD("\u001B[34;1m"),
        RED_BOLD("\u001B[31;1m"),
        GREEN("\u001B[32m"),
        GREEN_BOLD("\u001B[32;1m"),
        YELLOW("\u001B[33m"),
        MAGENTA("\u001B[35m"),
        CYAN("\u001B[36m"),
        CYAN_BOLD("\u001B[36;1m"),
        CYAN_DIM("\u001B[36;2m"),
        RED_DIM("\u001B[31;2m");

        final String open;
        Ansi(String code) { this.open = code; }
    }

    private static String stepKey(TestCase testCase, PickleStepTestStep step) {
        // Clave única estable por escenario y step (apto para paralelo)
        return testCase.getUri() + "|" + testCase.getName() + "|" + step.getId();
    }

    private static String caseKey(TestCase testCase) {
        return testCase.getUri() + "|" + testCase.getName();
    }
}