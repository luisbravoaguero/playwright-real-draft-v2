package com.mapfre.test.stepdefinitions.inspeccionAutos;

import com.mapfre.playwright.pageobjects.inspeccionAutos.InspeccionDeAutosPage;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InspeccionDeAutosSteps {
    private final InspeccionDeAutosPage inspeccionDeAutosPage;
    private final ScenarioContext scenarioContext;
    public InspeccionDeAutosSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.inspeccionDeAutosPage = new InspeccionDeAutosPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @And("en la pagina Inspeccion de Autos selecciona la opcion Cotizaciones")
    public void enLaPaginaInspeccionDeAutosSeleccionaLaOpcionCotizaciones() {
        inspeccionDeAutosPage.assertLoaded();
        inspeccionDeAutosPage.clickCotizacionesMenuLink();
    }

    @When("en la pagina Inspeccion de Autos selecciona la opcion Solicitudes")
    public void enLaPaginaInspeccionDeAutosSeleccionaLaOpcionSolicitudes() {
        inspeccionDeAutosPage.assertLoaded();
        inspeccionDeAutosPage.clickSolicitudesMenuLink();
    }

    @When("en la pagina Inspeccion de Autos selecciona la opcion Programaciones")
    public void enLaPaginaInspeccionDeAutosSeleccionaLaOpcionProgramaciones() {
        inspeccionDeAutosPage.assertLoaded();
        inspeccionDeAutosPage.clickProgramacionesMenuLink();
    }

    @Then("se muestra la pagina Inspeccion de Autos")
    public void seMuestraLaPaginaInspeccionDeAutos() {
        inspeccionDeAutosPage.assertLoaded();
    }
}
