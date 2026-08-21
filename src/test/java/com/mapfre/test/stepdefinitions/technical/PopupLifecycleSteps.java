package com.mapfre.test.stepdefinitions.technical;

import com.mapfre.playwright.tabs.ScenarioTabs;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.pageobjects.technical.PopupDetailPage;
import com.mapfre.test.pageobjects.technical.PopupResultsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public final class PopupLifecycleSteps {
    private final PopupResultsPage resultsPage;
    private PopupDetailPage detailPage;
    private String scenarioId;

    public PopupLifecycleSteps(PageProvider pageProvider, ScenarioTabs tabs) {
        resultsPage = new PopupResultsPage(pageProvider.get(), tabs);
    }

    @Given("una página de resultados aislada para el escenario {string}")
    public void loadScenarioResults(String id) {
        scenarioId = id;
        resultsPage.loadFixture(id);
    }

    @When("abre el detalle en una nueva pestaña")
    public void openDetailInNewTab() {
        detailPage = resultsPage.openDetail(scenarioId);
    }

    @Then("el detalle pertenece únicamente al escenario actual")
    public void assertScenarioDetail() {
        detailPage.assertLoadedFor(scenarioId);
        Assert.assertEquals(resultsPage.openPopupCount(), 1, "Debe existir un popup registrado");
        Assert.assertEquals(resultsPage.contextPageCount(), 2, "El contexto debe contener dos páginas");
    }

    @When("cierra la pestaña de detalle")
    public void closeDetailTab() {
        resultsPage.closeDetail();
    }

    @Then("regresa a la pestaña original sin contaminación")
    public void assertOriginalTabIsIsolated() {
        Assert.assertTrue(detailPage.isClosed(), "El popup debe estar cerrado");
        resultsPage.assertOriginalRemainsOpenFor(scenarioId);
        Assert.assertEquals(resultsPage.openPopupCount(), 0, "No deben quedar popups registrados");
        Assert.assertEquals(resultsPage.contextPageCount(), 1, "Sólo debe permanecer la página original");
    }
}
