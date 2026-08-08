package com.mapfre.test.stepdefinitions.poliza.soat;

import com.mapfre.playwright.pageobjects.poliza.soat.DocumentoSoatPage;
import com.mapfre.playwright.pageobjects.poliza.soat.PolizaSoatPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class DescargaSoatSteps {
    private final PolizaSoatPage polizaSoatPage;
    private final DocumentoSoatPage documentoSoatPage;

    public DescargaSoatSteps(PageProvider pageProvider) {
        this.polizaSoatPage = new PolizaSoatPage(pageProvider.get());
        this.documentoSoatPage = new DocumentoSoatPage(pageProvider.get());
    }

    @And("en la pagina Soat selecciona la opcion Consulta documentos de Soat")
    public void enLaPaginaSoatSeleccionaLaOpcionConsultaDocumentosDeSoat() {
        polizaSoatPage.assertLoaded();
        polizaSoatPage.clickConsultaDocSoatButton();
    }

    @And("en la pagina Documentos SOAT ingresa el numero de poliza {string} con estado activo")
    public void enLaPaginaDocumentosSOATIngresaElNumeroDePolizaConEstadoActivo(String numero_poliza) {
        documentoSoatPage.assertLoaded();
        documentoSoatPage.filterActivePoliza(numero_poliza);
        documentoSoatPage.processAndAssertSuccess(10_000,500);
    }

    @And("en la pagina Documentos SOAT seleccionamos la opcion descargar pdf de la poliza mostrada")
    public void enLaPaginaDocumentosSOATSeleccionamosLaOpcionDescargarPdfDeLaPolizaMostrada() {
        documentoSoatPage.clickAccionesButton();
    }

    @Then("visualiza la poliza descargada en pdf")
    public void visualizaLaPolizaDescargadaEnPdf() {
        documentoSoatPage.downloadPdfOrThrow();
    }

}
