package com.mapfre.test.stepdefinitions.poliza.riegosgenerales;

import com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.ConsultaDocumentoRiesgosGeneralesPage;
import com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.MenuPrincipalRiesgosGeneralesPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ConsultaDocumentoRiesgosGeneralesSteps {
    private final MenuPrincipalRiesgosGeneralesPage menuPrincipalRiesgosGeneralesPage;
    private final ConsultaDocumentoRiesgosGeneralesPage consultaDocumentoRiesgosGeneralesPage;

    public ConsultaDocumentoRiesgosGeneralesSteps(PageProvider pageProvider) {
        this.menuPrincipalRiesgosGeneralesPage = new MenuPrincipalRiesgosGeneralesPage(pageProvider.get());
        this.consultaDocumentoRiesgosGeneralesPage= new ConsultaDocumentoRiesgosGeneralesPage(pageProvider.get());
    }

    @And("en la pagina Riesgos Generales selecciona la opcion Consulta Documentos de Riesgos Generales")
    public void enLaPaginaRiesgosGeneralesSeleccionaLaOpcionConsultaDocumentosDeRiesgosGenerales() {
        menuPrincipalRiesgosGeneralesPage.assertLoaded();
        menuPrincipalRiesgosGeneralesPage.clickConsultaDocumentoRiesgosGenealesButton();

    }

    @And("en la pagina Documentos Riesgos Generales completamos el formulario fecha de inicion {string} y fecha fin {string}")
    public void enLaPaginaDocumentosRiesgosGeneralesCompletamosElFormularioFechaDeInicionYFechaFin(String fechaInicio, String fechaFin) {
        consultaDocumentoRiesgosGeneralesPage.assertLoaded();
        consultaDocumentoRiesgosGeneralesPage.fillFormDocumentosRiesgosGenerales(fechaInicio,fechaFin);
    }

    @Then("el sistema muestra los documentos de Riesgos Generales")
    public void elSistemaMuestraLosDocumentosDeRiesgosGenerales() {
        consultaDocumentoRiesgosGeneralesPage.processAndAssertSuccessBusqueda(60_000,500);
    }
}
