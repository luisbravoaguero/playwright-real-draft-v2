package com.mapfre.test.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/technical/popup_lifecycle.feature",
        glue = {
                "com.mapfre.test.stepdefinitions.technical",
                "com.mapfre.test.hooks"
        },
        plugin = {
                "pretty",
                "json:target/cucumber-reports/popup-technical.json"
        },
        monochrome = true
)
public final class PopupTechnicalRunnerTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
