package com.mapfre.test.stepdefinitions.inspeccionAutos;

import com.mapfre.playwright.pageobjects.inspeccionAutos.DetalleCotizacionInspeccionAutosPage;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class DetalleCotizacionInspeccionAutosSteps {
    private final DetalleCotizacionInspeccionAutosPage detalleCotizacionInspeccionAutosPage;
    private final ScenarioContext scenarioContext;
    public DetalleCotizacionInspeccionAutosSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.detalleCotizacionInspeccionAutosPage = new DetalleCotizacionInspeccionAutosPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @And("en la pagina Detalle Cotizacion de Autos selecciona el boton Solicitar Inspeccion para crear la solicitud de inspeccion")
    public void enLaPaginaDetalleCotizacionDeAutosSeleccionaElBotonSolicitarInspeccionParaCrearLaSolicitudDeInspeccion() {
        detalleCotizacionInspeccionAutosPage.assertLoadedDetalleCotizacionTitle();
        detalleCotizacionInspeccionAutosPage.processAndAssertSuccessSolicitarInspeccion(40_000,500);
    }

    @Then("en la pagina Nueva Solicitud de Inspeccion se muestra el paso Solicitante y Vehiculo")
    public void enLaPaginaNuevaSolicitudDeInspeccionSeMuestraElPasoSolicitanteYVehiculo() {
        detalleCotizacionInspeccionAutosPage.processAndAssertSuccessSolicitarInspeccionModalAceptar(40_000,500);

    }
}
