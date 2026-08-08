package com.mapfre.test.stepdefinitions.poliza.vidaley;

import com.mapfre.playwright.pageobjects.poliza.vidaley.PolizaVidaLeyPage;
import com.mapfre.playwright.pageobjects.poliza.vidaley.consultaDocumento.DocumentosVidaLeyPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ConsultaDocumentosVidaLeySteps {
    private final PolizaVidaLeyPage polizaVidaLeyPage;
    private final DocumentosVidaLeyPage documentosVidaLeyPage;

    public ConsultaDocumentosVidaLeySteps(PageProvider pageProvider) {
        this.polizaVidaLeyPage = new PolizaVidaLeyPage(pageProvider.get());
        this.documentosVidaLeyPage = new DocumentosVidaLeyPage(pageProvider.get());
    }

    @And("en la pagina Vida Ley selecciona la opcion Bandeja de documentos")
    public void enLaPaginaVidaLeySeleccionaLaOpcionBandejaDeDocumentos() {
        polizaVidaLeyPage.assertLoaded();
        polizaVidaLeyPage.clickBandejaDeDocumentosButton();
    }

    @And("en la pagina Documentos de Vida Ley completamos el formulario estado de la poliza {string}, tipo de documento {string}, numero de documento {string}, numero de solicitud {string}, fecha de inicion {string} y fecha fin {string}")
    public void enLaPaginaDocumentosDeVidaLeyCompletamosElFormularioEstadoDeLaPolizaTipoDeDocumentoNumeroDeDocumentoNumeroDeSolicitudFechaDeInicionYFechaFin(String estado_poliza, String tipo_documento, String numero_documento, String numero_solicitud, String fecha_inicio, String fecha_fin) {
        documentosVidaLeyPage.assertLoaded();
        documentosVidaLeyPage.fillFormDocumentoVidaLey(estado_poliza, tipo_documento, numero_documento, numero_solicitud, fecha_inicio, fecha_fin);
        documentosVidaLeyPage.clickFiltrarButton();
        documentosVidaLeyPage.processAndAssertSuccess(30_000,500);
    }

    @Then("el sistema muestra los documentos Vida Ley")
    public void elSistemaMuestraLosDocumentosVidaLey() {
        documentosVidaLeyPage.validateDateRange();
    }
}
