package com.mapfre.test.stepdefinitions.poliza.accidentes;

import com.mapfre.playwright.pageobjects.poliza.accidentes.ConsultaDocumentoAccidentesPage;
import com.mapfre.playwright.pageobjects.poliza.accidentes.MenuPrincipalAccidentesPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ConsultaDocumentoAccidentesSteps {
    private final MenuPrincipalAccidentesPage menuPrincipalAccidentesPage;
    private final ConsultaDocumentoAccidentesPage consultaDocumentoAccidentesPage;

    public ConsultaDocumentoAccidentesSteps(PageProvider pageProvider) {
        this.menuPrincipalAccidentesPage = new MenuPrincipalAccidentesPage(pageProvider.get());
        this.consultaDocumentoAccidentesPage = new ConsultaDocumentoAccidentesPage(pageProvider.get());
    }

    @And("en la pagina Accidentes selecciona la opcion Consulta Documentos de Accidentes")
    public void enLaPaginaAccidentesSeleccionaLaOpcionConsultaDocumentosDeAccidentes() {
        menuPrincipalAccidentesPage.assertLoaded();
        menuPrincipalAccidentesPage.clickConsultaDocumentoButton();
    }

    @And("en la pagina Documentos Accidentes completamos el formulario fecha de inicion {string} y fecha fin {string}")
    public void enLaPaginaDocumentosAccidentesCompletamosElFormularioFechaDeInicionYFechaFin(String fecha_inicio, String fecha_fin) {
        consultaDocumentoAccidentesPage.fillFormDocumentosAccidentes(fecha_inicio,fecha_fin);
        consultaDocumentoAccidentesPage.processAndAssertSuccess(30_000,500);
    }

    @Then("el sistema muestra los documentos Accidentes")
    public void elSistemaMuestraLosDocumentosAccidentes() {
        consultaDocumentoAccidentesPage.validateDateRange();

    }
}
