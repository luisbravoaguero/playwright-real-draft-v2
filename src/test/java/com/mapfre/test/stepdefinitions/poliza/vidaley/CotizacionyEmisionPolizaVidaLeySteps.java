package com.mapfre.test.stepdefinitions.poliza.vidaley;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import com.mapfre.playwright.pageobjects.poliza.vidaley.*;
import com.mapfre.test.hooks.PageProvider;

public class CotizacionyEmisionPolizaVidaLeySteps {

    private final ResultadosCotizacionVidaLeyPage resultadosCotizacionVidaLeyPage;
    private final EmitirPolizaVidaLeyPage emitirPolizaPage;
    private final ResultadoEmisionPolizaVidaLeyPage resultadoEmisionPage;

    // Constructor
    public CotizacionyEmisionPolizaVidaLeySteps(PageProvider pageProvider) {

        this.resultadosCotizacionVidaLeyPage = new ResultadosCotizacionVidaLeyPage(pageProvider.get());
        this.emitirPolizaPage = new EmitirPolizaVidaLeyPage(pageProvider.get());
        this.resultadoEmisionPage = new ResultadoEmisionPolizaVidaLeyPage(pageProvider.get());

    }

    @And("en la pagina Cotizacion poliza de vida ley se genera el numero de cotizacion y se inicia la emision de la poliza")
    public void enLaPaginaCotizacionPolizaDeVidaLeySeGeneraElNumeroDeCotizacionYSeIniciaLaEmisionDeLaPoliza() {

        resultadosCotizacionVidaLeyPage.aceptarCotizacion();
        resultadosCotizacionVidaLeyPage.extraerYLoggearNumeroCotizacion();
        resultadosCotizacionVidaLeyPage.emitirPoliza();
    }

    @And("en la pagina Emitir poliza de vida ley se confirman los datos del contratante para continuar con la emision")
    public void enLaPaginaEmitirPolizaDeVidaLeySeConfirmanLosDatosDelContratanteParaContinuarConLaEmision() {

        emitirPolizaPage.assertEnPaginaEmitirPolizaVidaLey();
        emitirPolizaPage.clickSiguiente();
        emitirPolizaPage.confirmarGuardarYContinuar();

    }

    @And("en la pagina Emitir poliza de vida ley se validan los datos de la cobertura y asegurados")
    public void enLaPaginaEmitirPolizaDeVidaLeySeValidanLosDatosDeLaCoberturaYAsegurados() {
        emitirPolizaPage.assertEnPasoEmision();
        emitirPolizaPage.clickEmitirPoliza();

    }

    @Then("se muestra el resultado de poliza emitida con el numero de poliza generado")
    public void seMuestraElResultadoDePolizaEmitidaConElNumeroDePolizaGenerado() {

        resultadoEmisionPage.assertPolizaEmitida();
        resultadoEmisionPage.obtenerNumeroPoliza();

    }
}
