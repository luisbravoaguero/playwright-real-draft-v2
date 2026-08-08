package com.mapfre.test.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.mapfre.test.stepdefinitions",
                "com.mapfre.test.hooks"
        },
        tags = "@riesgosgenerales",
        plugin = {
                //"summary",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber-junit.xml",
                "com.mapfre.reporting.ExtentCucumberStepsPlugin",
                "com.mapfre.reporting.ConsoleStepLogger"
        },
        monochrome = true
)
public class RunnerCucumberTest extends AbstractTestNGCucumberTests {

    /*static {
        // Muestra lo que vienes pasando por CLI
        String filterTags = System.getProperty("cucumber.filter.tags");
        System.out.println("[DEBUG] cucumber.filter.tags = " + (filterTags == null ? "<none>" : filterTags));
    }*/

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();

        //Object[][] s = super.scenarios();
        //System.out.println("[DEBUG] Escenarios descubiertos por DataProvider = " + (s == null ? 0 : s.length));
        //return s;
    }

}
