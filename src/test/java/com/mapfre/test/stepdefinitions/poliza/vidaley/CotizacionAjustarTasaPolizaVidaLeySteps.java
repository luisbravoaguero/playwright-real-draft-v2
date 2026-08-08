package com.mapfre.test.stepdefinitions.poliza.vidaley;

import com.mapfre.playwright.pageobjects.poliza.vidaley.ResultadosCotizacionVidaLeyPage;
import com.mapfre.playwright.pageobjects.poliza.vidaley.CotizarVidaLeyPage;
import com.mapfre.playwright.pageobjects.poliza.BandejaDocumentosVidaLeyPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;

public class CotizacionAjustarTasaPolizaVidaLeySteps {

    private final ResultadosCotizacionVidaLeyPage resultadosCotizacionVidaLeyPage;
    private final CotizarVidaLeyPage cotizarVidaLeyPage;
    private final BandejaDocumentosVidaLeyPage bandejaDocumentosVidaLeyPage;

    // Constructor
    public CotizacionAjustarTasaPolizaVidaLeySteps(PageProvider pageProvider) {

        this.resultadosCotizacionVidaLeyPage = new ResultadosCotizacionVidaLeyPage(pageProvider.get());
        this.cotizarVidaLeyPage = new CotizarVidaLeyPage(pageProvider.get());
        this.bandejaDocumentosVidaLeyPage = new BandejaDocumentosVidaLeyPage(pageProvider.get());

    }

    @And("en la pagina Cotizacion poliza de vida ley se solicita el reajuste de la tasa de la cotizacion")
    public void solicitarReajusteDeTasa() {
        resultadosCotizacionVidaLeyPage.solicitarReajusteDeTasa();
    }

    @And("en la pagina Vida Ley se selecciona la opcion Bandeja de documentos")
    public void seleccionarBandejaDeDocumentos() {
        cotizarVidaLeyPage.clickBandejaDeDocumentos();
    }

    @And("en la bandeja de documentos de Vida Ley se filtra y se busca la cotizacion por el estado SOLICITUD EVALUACION y se selecciona ver cotizacion")
    public void enLaBandejaDeDocumentosDeVidaLeySeFiltraYSeBuscaLaCotizacionPorElEstadoSOLICITUDEVALUACIONYSeSeleccionaVerCotizacion() {
        bandejaDocumentosVidaLeyPage.assertEnBandejaDocumentos();
        bandejaDocumentosVidaLeyPage.seleccionarSolicitudEnEvaluacionYVerCotizacion();
    }

    @And("en la pagina de evaluacion de tasa se ingresa la tasa final {string} y se acepta la solicitud")
    public void ingresarTasaFinalYAceptarSolicitud(String tasaFinal) {
        resultadosCotizacionVidaLeyPage.ingresarTasaFinalYAceptarSolicitud(tasaFinal);
    }


    @And("en la bandeja de documentos de Vida Ley se filtra y se busca la cotizacion por el estado SOLICITUD ATENDIDA y se selecciona ver cotizacion")
    public void enLaBandejaDeDocumentosDeVidaLeySeFiltraYSeBuscaLaCotizacionPorElEstadoSOLICITUDATENDIDAYSeSeleccionaVerCotizacion() {

        bandejaDocumentosVidaLeyPage.seleccionarSolicitudAtendidaVerCotizacion();

    }



}
