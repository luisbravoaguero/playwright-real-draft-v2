package com.mapfre.test.stepdefinitions.poliza.transporte;

import com.mapfre.playwright.pageobjects.poliza.transporte.ResultadoEmisionTransportePage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.Then;

public class ResultadoEmisionTransporteSteps {
    private final ResultadoEmisionTransportePage resultadoEmisionTransportePage;

    public ResultadoEmisionTransporteSteps(PageProvider pageProvider) {
        this.resultadoEmisionTransportePage = new ResultadoEmisionTransportePage(pageProvider.get());
    }
    @Then("en la pagina Poliza emitida se muestra la poliza de Transporte")
    public void enLaPaginaPolizaEmitidaSeMuestraLaPolizaDeTransporte() {
        resultadoEmisionTransportePage.assertLoaded();
        resultadoEmisionTransportePage.validarVisibilidadNumeroPoliza();
    }


}
