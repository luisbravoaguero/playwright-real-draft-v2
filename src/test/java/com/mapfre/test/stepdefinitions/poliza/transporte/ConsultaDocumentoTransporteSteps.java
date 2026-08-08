package com.mapfre.test.stepdefinitions.poliza.transporte;

import com.mapfre.playwright.pageobjects.poliza.transporte.ConsultaDocumentoTransportePage;
import com.mapfre.playwright.pageobjects.poliza.transporte.MenuPrincipalTransportePage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ConsultaDocumentoTransporteSteps {
    private final MenuPrincipalTransportePage menuPrincipalTransportePage;
    private final ConsultaDocumentoTransportePage consultaDocumentoTransportePage;

    public ConsultaDocumentoTransporteSteps(PageProvider pageProvider) {
        this.menuPrincipalTransportePage = new MenuPrincipalTransportePage(pageProvider.get());
        this.consultaDocumentoTransportePage = new ConsultaDocumentoTransportePage(pageProvider.get());
    }

    @And("en la pagina Transporte selecciona la opcion Consulta Documentos de Transporte")
    public void enLaPaginaTransporteSeleccionaLaOpcionConsultaDocumentosDeTransporte() {
        menuPrincipalTransportePage.assertLoaded();
        menuPrincipalTransportePage.clickConsultaDocumentoButton();
    }

    @And("en la pagina Documentos Transporte completamos el formulario fecha de inicion {string} y fecha fin {string}")
    public void enLaPaginaDocumentosTransporteCompletamosElFormularioFechaDeInicionYFechaFin(String fecha_inicio, String fecha_fin) {
        consultaDocumentoTransportePage.fillFormDocumentosTransporte(fecha_inicio,fecha_fin);
        consultaDocumentoTransportePage.processAndAssertSuccess(30_000,500);
    }

    @Then("el sistema muestra los documentos Transporte")
    public void elSistemaMuestraLosDocumentosTransporte() {
        consultaDocumentoTransportePage.validateDateRange();
    }
}
