package com.mapfre.test.stepdefinitions.poliza.fola;

import com.mapfre.playwright.pageobjects.poliza.fola.ConsultaDocumentoFolaPage;
import com.mapfre.playwright.pageobjects.poliza.fola.MenuPrincipalFolaPage;
import com.mapfre.playwright.pageobjects.poliza.fola.ResumenCotizacionFolaPage;
import com.mapfre.test.hooks.PageProvider;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ConsultaDocumentoFolaSteps {
    private final MenuPrincipalFolaPage menuPrincipalFolaPage;
    private final ConsultaDocumentoFolaPage consultaDocumentoFolaPage;
    private final ResumenCotizacionFolaPage resumenCotizacionFolaPage;

    public ConsultaDocumentoFolaSteps(PageProvider pageProvider) {
        this.menuPrincipalFolaPage = new MenuPrincipalFolaPage(pageProvider.get());
        this.consultaDocumentoFolaPage= new ConsultaDocumentoFolaPage(pageProvider.get());
        this.resumenCotizacionFolaPage = new ResumenCotizacionFolaPage(pageProvider.get());
    }

    @And("en la pagina FOLA selecciona la opcion Consulta Documentos de FOLA")
    public void enLaPaginaFOLASeleccionaLaOpcionConsultaDocumentosDeFOLA() {
        menuPrincipalFolaPage.assertLoaded();
        menuPrincipalFolaPage.clickConsultaDocumentoFolaButton();

    }

    @And("en la pagina Documentos FOLA completamos el formulario fecha de inicion {string} y fecha fin {string}")
    public void enLaPaginaDocumentosFOLACompletamosElFormularioFechaDeInicionYFechaFin(String fechaInicio, String fechaFin) {
        consultaDocumentoFolaPage.assertLoaded();
        consultaDocumentoFolaPage.fillFormDocumentosFola(fechaInicio,fechaFin);
    }

    @Then("el sistema muestra los documentos de FOLA")
    public void elSistemaMuestraLosDocumentosDeFOLA() {
        consultaDocumentoFolaPage.processAndAssertSuccessBusqueda(60_000,500);
    }

    @Then("el sistema muestra la pagina Resumen Cotizacion Fola con el resumen de la cotizacion")
    public void elSistemaMuestraLaPaginaResumenCotizacionFolaConElResumenDeLaCotizacion() {
        resumenCotizacionFolaPage.processAndAssertSuccess(120_000,500);
        resumenCotizacionFolaPage.assertLoaded();
    }
}
