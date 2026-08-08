package com.mapfre.test.stepdefinitions.poliza.sctr;

import com.mapfre.playwright.pageobjects.poliza.sctr.consultaDocumento.DocumentosSctrPage;
import com.mapfre.playwright.pageobjects.poliza.sctr.PolizaSctrPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ConsultaDocumentoSctrSteps {
    private final PolizaSctrPage polizaSctrPage;
    private final DocumentosSctrPage documentosSctrPage;

    public ConsultaDocumentoSctrSteps(PageProvider pageProvider) {
        this.polizaSctrPage = new PolizaSctrPage(pageProvider.get());
        this.documentosSctrPage = new DocumentosSctrPage(pageProvider.get());
    }

    @And("en la pagina Sctr selecciona la opcion Ver documentos sctr")
    public void enLaPaginaSctrSeleccionaLaOpcionVerDocumentosSctr() {
        polizaSctrPage.assertLoaded();
        polizaSctrPage.clickVerDocumentosSctrButton();
    }

    @And("en la pagina Documentos SCTR ingresa la fecha de inicio {string} y fecha fin {string}")
    public void enLaPaginaDocumentosSCTRIngresaLaFechaDeInicioYFechaFin(String fecha_inicio, String fecha_fin) {
        documentosSctrPage.assertLoaded();
        documentosSctrPage.fillFormByDate(fecha_inicio, fecha_fin);
        ExtentEvidence.shot("Evidencia despues de llenar el campo fecha de inicio en el formulario");
        documentosSctrPage.clickFiltrarButton();
        ExtentEvidence.shot("Evidencia despues de hacer click en el boton Filtrar en el formulario");
        documentosSctrPage.processAndAssertSuccess(10_000,500);
    }

    @Then("el sistema muestra los documentos sctr")
    public void elSistemaMuestraLosDocumentosSctr() {
        documentosSctrPage.exportartxt();
        //documentosSctrPage.validateDateRange();
    }

    @And("en la pagina Documentos SCTR completamos el formulario tipo de producto {string}, numero de poliza {string}, estado de la poliza {string}, fecha de inicion {string}, fecha fin {string} y origen {string}")
    public void enLaPaginaDocumentosSCTRCompletamosElFormularioTipoDeProductoNumeroDePolizaEstadoDeLaPolizaFechaDeInicionFechaFinYOrigen(String tipo_producto, String numero_poliza, String estado_poliza, String fecha_inicio, String fecha_fin, String origen) {
        documentosSctrPage.assertLoaded();
        documentosSctrPage.fillFormDocumentoSctr(tipo_producto, numero_poliza, estado_poliza, fecha_inicio, fecha_fin, origen);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario");
        documentosSctrPage.clickFiltrarButton();
        ExtentEvidence.shot("Evidencia despues de hacer click en el boton Filtrar en el formulario");
        documentosSctrPage.processAndAssertSuccess(10_000,500);

    }

    @And("el la pagina Documentos SCTR seleccionamos Ver Detalle")
    public void elLaPaginaDocumentosSCTRSeleccionamosVerDetalle() {
        documentosSctrPage.clickVerDetalleButton();
        ExtentEvidence.shot("Evidencia despues de hacer click en el boton Ver Detalle en el formulario");
        documentosSctrPage.processAndAssertResultadoVerDetalleSuccess(30_000,500);
    }

    @Then("en la pagina Emitir poliza SCTR Periodo Regular se descarga el Recibo de Pension Recibo de Salud Poliza de Pension y Poliza de Salud")
    public void enLaPaginaEmitirPolizaSCTRPeriodoRegularSeDescargaElReciboDePensionReciboDeSaludPolizaDePensionYPolizaDeSalud() {
        documentosSctrPage.downloadReciboPension();
        ExtentEvidence.shot("Evidencia despues de descargar el pdf del recibo de pension");
        documentosSctrPage.downloadReciboSalud();
        ExtentEvidence.shot("Evidencia despues de descargar el pdf del recibo de salud");
        documentosSctrPage.downloadPolizaPension();
        ExtentEvidence.shot("Evidencia despues de descargar el pdf de la poliza de pension");
        documentosSctrPage.downloadPolizaSalud();
        ExtentEvidence.shot("Evidencia despues de descargar el pdf de la poliza de salud");
    }
}
