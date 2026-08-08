package com.mapfre.test.stepdefinitions.poliza.camposanto;

import com.mapfre.playwright.pageobjects.poliza.camposanto.emision.EmisionCotizadorCampoSantoPage;
import com.mapfre.playwright.pageobjects.poliza.camposanto.cotizacion.*;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class EmisionCampoSantoNecesidadFuturaSteps {

    private final EmisionCotizadorCampoSantoPage emisionCotizadorCampoSantoPage;
    private final BandejaCamposantoPage bandejaCamposantoPage;
    private String nroCotizacionEmision;

    public EmisionCampoSantoNecesidadFuturaSteps(PageProvider pageProvider) {
        this.emisionCotizadorCampoSantoPage = new EmisionCotizadorCampoSantoPage(pageProvider.get());
        this.bandejaCamposantoPage = new BandejaCamposantoPage(pageProvider.get());

    }

    @And("en la pagina Cotizador de Camposanto seleccionar la opcion Ir a Bandeja")
    public void seleccionarIrABandeja() {

        nroCotizacionEmision = emisionCotizadorCampoSantoPage.obtenerNumeroCotizacionGenerado();
        emisionCotizadorCampoSantoPage.clickIrABandeja();

    }

    @And("en la pagina Bandeja de Camposanto seleccionamos y emitimos la poliza cotizada")
    public void enLaPaginaBandejaDeCamposantoSeleccionamosYEmitimosLaPolizaCotizada() {
        bandejaCamposantoPage.assertLoaded();
        bandejaCamposantoPage.emitirCotizacionPorNumero(nroCotizacionEmision);

    }

    @And("en la pagina Cotizador de Camposanto se ingresa los datos en check de documentos, datos del tomador, datos beneficiarios, datos adicionales")
    public void enLaPaginaCotizadorDeCamposantoSeIngresaLosDatosEnCheckDeDocumentosDatosDelTomadorDatosBeneficiariosDatosAdicionalesLuegoSeEmiteLaPoliza() {
        emisionCotizadorCampoSantoPage.assertLoadedRepositorioDocumentos();
        emisionCotizadorCampoSantoPage.cargarDniTitular();
        emisionCotizadorCampoSantoPage.TabDatosDelTomador();
        emisionCotizadorCampoSantoPage.TabDatosBeneficiarios();
        emisionCotizadorCampoSantoPage.tabDatosAdicionales();


    }

    @Then("se emite la poliza correctamente")
    public void seEmiteLaPolizaCorrectamente() {
        emisionCotizadorCampoSantoPage.emitirPoliza();
    }
}
