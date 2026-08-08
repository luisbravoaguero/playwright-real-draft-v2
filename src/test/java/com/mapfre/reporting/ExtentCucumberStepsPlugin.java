package com.mapfre.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.mapfre.utils.ScreenshotUtils;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

public class ExtentCucumberStepsPlugin implements ConcurrentEventListener {

    private final ThreadLocal<ExtentTest> currentScenarioTest = new ThreadLocal<>();
    private final ThreadLocal<ExtentTest> currentStepNode = new ThreadLocal<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::onTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::onTestStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onTestCaseFinished);
        publisher.registerHandlerFor(TestRunFinished.class, this::onTestRunFinished);
    }

    private ExtentTest ensureScenarioTest(String scenarioName) {
        ExtentTest cached = currentScenarioTest.get();
        if (cached != null) return cached;

        try {
            ExtentTest t = ExtentReportManager.test();
            currentScenarioTest.set(t);
            return t;
        } catch (IllegalStateException e) {
            ExtentReportManager.createTest(scenarioName);
            ExtentTest t = ExtentReportManager.test();
            currentScenarioTest.set(t);
            return t;
        }
    }

    private void onTestCaseStarted(TestCaseStarted event) {
        ensureScenarioTest(event.getTestCase().getName());
    }

    /** Creates ONE node per Gherkin step and exposes it to the thread. */
    private void onTestStepStarted(TestStepStarted event) {
        // Only handle real Gherkin steps (ignore hooks)
        if (!(event.getTestStep() instanceof PickleStepTestStep step)) return;

        ExtentTest scenarioTest = ensureScenarioTest(event.getTestCase().getName());

        String keyword = step.getStep().getKeyword() == null ? "" : step.getStep().getKeyword();
        String stepName = keyword + step.getStep().getText();

        ExtentTest node = scenarioTest.createNode(stepName);

        currentStepNode.set(node);
        ExtentReportManager.setStepNode(node); // ✅ checkpoints go inside this step
    }

    /** Default screenshot AFTER EACH STEP, attached INSIDE the step node. */
    private void onTestStepFinished(TestStepFinished event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep)) return;

        ExtentTest stepNode = currentStepNode.get();
        if (stepNode == null) return;

        Result result = event.getResult();
        Status status = result.getStatus();

        try {
            // Always attach screenshot after each step
            String base64 = ScreenshotUtils.screenshotBase64();
            var media = MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build();

            if (status == Status.PASSED) {
                stepNode.pass("Passed", media);
            } else if (status == Status.SKIPPED) {
                stepNode.skip("Skipped", media);
            } else if (status == Status.FAILED) {
                stepNode.fail(result.getError(), media);
            } else {
                stepNode.info("Status: " + status.name(), media);
            }

        } catch (Exception e) {
            // If screenshot fails, still log status
            if (status == Status.FAILED) stepNode.fail(result.getError());
            else stepNode.info("Screenshot failed: " + e.getMessage());
        } finally {
            currentStepNode.remove();
            ExtentReportManager.clearStepNode(); // ✅ important
        }
    }

    private void onTestCaseFinished(TestCaseFinished event) {
        currentScenarioTest.remove();
        currentStepNode.remove();
        ExtentReportManager.clearStepNode();
    }

    private void onTestRunFinished(TestRunFinished event) {
        ExtentReportManager.init();
        ExtentReportManager.flush();
    }
}