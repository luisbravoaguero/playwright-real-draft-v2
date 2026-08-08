package com.mapfre.test.stepdefinitions.poliza.auto;

import com.mapfre.playwright.pageobjects.poliza.auto.PolizaAutoPage;
import com.mapfre.playwright.pageobjects.poliza.auto.cotizacion.CotizacionGuardadaPage;
import com.mapfre.playwright.pageobjects.poliza.auto.emsion.CotizacionesAutoPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class CotizacionEmisionAutoSteps {
    private final PolizaAutoPage polizaAutoPage;
    private final CotizacionesAutoPage cotizacionesAutoPage;
    private final CotizacionGuardadaPage cotizacionGuardadaPage;
    private String numeroCotizacion;

    public CotizacionEmisionAutoSteps(PageProvider pageProvider) {
        this.polizaAutoPage = new PolizaAutoPage(pageProvider.get());
        this.cotizacionesAutoPage = new CotizacionesAutoPage(pageProvider.get());
        this.cotizacionGuardadaPage = new CotizacionGuardadaPage(pageProvider.get());
    }

    @And("el sistema muestra el resumen y el numero de cotizacion")
    public void elSistemaMuestraElResumenYElNumeroDeCotizacion() {
        cotizacionGuardadaPage.assertLoaded();
        numeroCotizacion = cotizacionGuardadaPage.obtenerNumeroCotizacion();
    }

    @And("en la pagina Cotizaciones Auto busca la cotizacion generada entre el rango de fecha {string} y {string}")
    public void buscarCotizacionGenerada(String fechaInicio, String fechaFin) {

        if (numeroCotizacion == null || numeroCotizacion.isEmpty()) {
            throw new RuntimeException("No existe número de cotización capturado en el flujo.");
        }
        cotizacionesAutoPage.buscarCotizacionGenerada(numeroCotizacion, fechaInicio, fechaFin);
    }

    @And("en la pagina Cotización póliza de auto se realiza la emisión de la póliza")
    public void enLaPaginaCotizaciónPólizaDeAutoSeRealizaLaEmisiónDeLaPóliza() {
        cotizacionesAutoPage.processAndAssertVerCotizacionSuccess(30_000, 500);
        cotizacionesAutoPage.assertCotizacionNumberLoaded();
        cotizacionesAutoPage.clickEmitirPolizaAuto();

    }
}