package com.mapfre.test.stepdefinitions.poliza.auto;

import com.mapfre.playwright.pageobjects.inspeccionAutos.InspeccionAutosSolicitudesPage;
import com.mapfre.playwright.pageobjects.poliza.auto.emsion.EmisionAutoUsadoRealizadaResumenPage;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import io.cucumber.java.en.Then;

public class EmisionAutoUsadoRealizadaResumenSteps {
    private final EmisionAutoUsadoRealizadaResumenPage emisionAutoUsadoRealizadaResumenPage;
    private final ScenarioContext scenarioContext;
    public EmisionAutoUsadoRealizadaResumenSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.emisionAutoUsadoRealizadaResumenPage = new EmisionAutoUsadoRealizadaResumenPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @Then("en la pagina Poliza Emitida de Autos se muestra el resumen de la poliza auto usado")
    public void enLaPaginaPolizaEmitidaDeAutosSeMuestraElResumenDeLaPolizaAutoUsado() {
        emisionAutoUsadoRealizadaResumenPage.processAndAssertSuccessOKModalInfoEmisionPolizaButton(40_000, 500);
        emisionAutoUsadoRealizadaResumenPage.assertLoadedResumenPolizaAutoUsado();
    }
}
