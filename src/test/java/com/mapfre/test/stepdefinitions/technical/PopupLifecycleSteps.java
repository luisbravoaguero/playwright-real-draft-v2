package com.mapfre.test.stepdefinitions.technical;

import com.aventstack.extentreports.ExtentReports;
import com.mapfre.playwright.tabs.ScenarioTabs;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.pageobjects.technical.PlaygroundHomePage;
import com.mapfre.test.pageobjects.technical.WindowPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public final class PopupLifecycleSteps {
    private final WindowPage windowPage;
    private PlaygroundHomePage homepage;
    private String statusBefore;
    private String statusAfter;

    public PopupLifecycleSteps(PageProvider pageProvider, ScenarioTabs tabs) {
        windowPage = new WindowPage(pageProvider.get(), tabs);
    }

    @Given("el escenario {string} está en la página Window de Bondar Academy")
    public void openBondarWindowPage(String scenarioId) {
        windowPage.open();
    }

    @When("hace clic en Open homepage in a new tab")
    public void openHomepageInNewTab() {
        homepage = windowPage.openHomepageInNewTab();
    }

    @Then("el homepage se abre en una nueva pestaña del mismo escenario")
    public void assertHomepagePopup() {
        homepage.assertLoaded();
        Assert.assertEquals(windowPage.ownedPopupCount(), 1, "Debe existir un popup registrado");
        Assert.assertEquals(windowPage.contextPageCount(), 2, "El contexto debe contener dos páginas");
    }

    @Then("{string} es visible en el homepage")
    public void assertElementVisible(String elementName) {
        if ("Roller Shades".equals(elementName)) {
            homepage.assertRollerShadesVisible();
        }
    }

    @Then("obtiene el estado de {string} antes de hacer clic")
    public void getStatusBefore(String elementName) {
        if ("Roller Shades".equals(elementName)) {
            statusBefore = homepage.getRollerShadesStatus();
            System.out.println("Estado de Roller Shades antes: " + statusBefore);
        }
        ExtentEvidence.shot("El texto buscado es presente");
    }

    @When("hace clic en el elemento i.nb-roller-shades")
    public void clickRollerShadesButton() {
        homepage.clickRollerShadesButton();
    }

    @Then("obtiene el estado de {string} después de hacer clic")
    public void getStatusAfter(String elementName) {
        if ("Roller Shades".equals(elementName)) {
            statusAfter = homepage.getRollerShadesStatus();
            System.out.println("Estado de Roller Shades después: " + statusAfter);
        }
    }

    @Then("el estado de {string} debe ser OFF")
    public void assertStatusIsOff(String elementName) {
        if ("Roller Shades".equals(elementName)) {
            homepage.assertRollerShadesStatusIsOff();
        }
    }

    @When("cierra la nueva pestaña")
    public void closeHomepageTab() {
        windowPage.closeHomepage();
    }

    @Then("la pestaña Window original permanece abierta y aislada")
    public void assertOriginalWindowTab() {
        Assert.assertTrue(homepage.isClosed(), "La nueva pestaña debe estar cerrada");
        windowPage.assertStillOpen();
        Assert.assertEquals(windowPage.ownedPopupCount(), 0, "No deben quedar popups registrados");
        Assert.assertEquals(windowPage.contextPageCount(), 1, "Sólo debe permanecer la pestaña original");
    }
}
