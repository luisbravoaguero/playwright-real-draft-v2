package com.mapfre.test.stepdefinitions.poliza.fola;

import com.mapfre.playwright.pageobjects.poliza.fola.EmisionPolizaFolaPage;
import com.mapfre.playwright.pageobjects.poliza.fola.ResumenCotizacionFolaPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class EmisionFolaSteps {
    private final ResumenCotizacionFolaPage resumenCotizacionFolaPage;
    private final EmisionPolizaFolaPage emisionPolizaFolaPage;

    public EmisionFolaSteps(PageProvider pageProvider) {
        this.resumenCotizacionFolaPage = new ResumenCotizacionFolaPage(pageProvider.get());
        this.emisionPolizaFolaPage =  new EmisionPolizaFolaPage(pageProvider.get());
    }
    @And("en la pagina Resumen Cotizacion Fola selecciona el boton de emitir poliza")
    public void enLaPaginaResumenCotizacionFolaSeleccionaElBotonDeEmitirPoliza() {
        resumenCotizacionFolaPage.assertLoaded();
        resumenCotizacionFolaPage.clickEmitirPoliza();
    }
    @And("en la pagina de Emision de Poliza Fola selecciona el boton Descargar Formato y adjunta el formato completado")
    public void enLaPaginaDeEmisionDePolizaFolaSeleccionaElBotonDescargarFormatoYAdjuntaElFormatoCompletado() {
    emisionPolizaFolaPage.assertLoaded();
    emisionPolizaFolaPage.descargarFormatoYAdjuntarFormatoCompletado();
    }

    @And("en la pagina de Emision de Poliza Fola ingresa el telefono {string},telefono movil {string},nombre de representante {string},cargo representante {string}")
    public void enLaPaginaDeEmisionDePolizaFolaIngresaElTelefonoTelefonoMovilNombreDeRepresentanteCargoRepresentante(String telefono, String telefonoMovil, String nombreRepresentante, String cargoRepresentante) {
    emisionPolizaFolaPage.ingresarDatosDeContactoYRepresentante(telefono, telefonoMovil, nombreRepresentante, cargoRepresentante);
    }


    @And("en la pagina de Emision de Poliza Fola selecciona el boton Emision")
    public void enLaPaginaDeEmisionDePolizaFolaSeleccionaElBotonEmision() {
    emisionPolizaFolaPage.clickEmisionFola();
    emisionPolizaFolaPage.processAndAssertPolizaEmitida(30_000);
    }

    @Then("el sistema muestra en la pagina Documentos Fola el numero de poliza emitida")
    public void elSistemaMuestraEnLaPaginaDocumentosFolaElNumeroDePolizaEmitida() {
    emisionPolizaFolaPage.obtenerNumeroDePoliza();
    }
}
