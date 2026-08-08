package com.mapfre.test.stepdefinitions.poliza.decesos;

import com.mapfre.playwright.pageobjects.poliza.decesos.consultaCotizaciones.DocumentosDecesosPage;
import com.mapfre.playwright.pageobjects.poliza.decesos.cotizacion.ResumenCotizacionDecesosPage;
import com.mapfre.playwright.pageobjects.poliza.decesos.emision.EmisionPolizaDecesosPage;
import com.mapfre.reporting.ExtentEvidence;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import com.mapfre.test.hooks.PageProvider;

public class EmisionDecesosSteps {

    private final ResumenCotizacionDecesosPage resumenCotizacionDecesosPage;
    private final EmisionPolizaDecesosPage emisionPolizaDecesosPage;
    private final DocumentosDecesosPage documentosDecesosPage;

    public EmisionDecesosSteps(PageProvider pageProvider) {
        this.resumenCotizacionDecesosPage = new ResumenCotizacionDecesosPage(pageProvider.get());
        this.emisionPolizaDecesosPage = new EmisionPolizaDecesosPage(pageProvider.get());
        this.documentosDecesosPage = new DocumentosDecesosPage(pageProvider.get());
    }

    @And("en la pagina Resumen Cotizacion Decesos selecciona el boton de emitir poliza")
    public void enLaPaginaResumenCotizacionDecesosSeleccionaElBotonDeEmitirPoliza() {
        resumenCotizacionDecesosPage.assertLoaded();
        resumenCotizacionDecesosPage.clickEmitirPoliza();
        resumenCotizacionDecesosPage.processAndAssertSuccessEmitirButton(30_000, 500);
    }

    @And("en la pagina de Emision de Poliza de Decesos en la seccion Datos de Poliza selecciona el boton Siguiente")
    public void enLaPaginaDeEmisionDePolizaDeDecesosEnLaSeccionDatosDePolizaSeleccionaElBotonSiguiente() {
        //emisionPolizaDecesosPage.assertLoaded();
        emisionPolizaDecesosPage.clickSiguienteDatosPoliza();
        emisionPolizaDecesosPage.processAndAssertSuccessSiguienteButton(60_000, 500);
    }

    @And("en la pagina de Emision de Poliza de Decesos en la seccion Carga de documentos selecciona el tipo de documento {string} y adjunta el documento")
    public void enLaPaginaDeEmisionDePolizaDeDecesosEnLaSeccionCargaDeDocumentosSeleccionaElTipoDeDocumentoYAdjuntaElDocumento(String tipoDocumento) {
        emisionPolizaDecesosPage.seleccionarTipoDocumentoYAdjuntar(tipoDocumento);
    }

    @And("en la pagina de Emision de Poliza de Decesos en la seccion Carga de documentos selecciona el boton Emision")
    public void enLaPaginaDeEmisionDePolizaDeDecesosEnLaSeccionCargaDeDocumentosSeleccionaElBotonEmision() {
        emisionPolizaDecesosPage.clickEmision();
        ExtentEvidence.shot("Evidencia despues de hacer click en el boton Emision");
        //emisionPolizaDecesosPage.processAndAssertPolizaEmitida(30_000);
        emisionPolizaDecesosPage.processAndAssertSuccessPolizaEmitida(40_000, 500);
    }

    @Then("el sistema muestra en la pagina Documentos Decesos el numero de poliza emitida")
    public void elSistemaMuestraEnLaPaginaDocumentosDecesosElNumeroDePolizaEmitida() {
        documentosDecesosPage.assertNumeroPolizaEmitidaVisible();
    }
}
