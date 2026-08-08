package com.mapfre.test.stepdefinitions.poliza.riegosgenerales.cotizacion;

import com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion.CotizacionRiesgosGeneralesResultadoCotizacionPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.Then;

public class CotizacionRiesgosGeneralesResultadoCotizacionSteps {
    private final CotizacionRiesgosGeneralesResultadoCotizacionPage cotizacionRiesgosGeneralesResultadoCotizacionPage;

    public CotizacionRiesgosGeneralesResultadoCotizacionSteps(PageProvider pageProvider) {
        this.cotizacionRiesgosGeneralesResultadoCotizacionPage = new CotizacionRiesgosGeneralesResultadoCotizacionPage(pageProvider.get());
    }

    @Then("en la pagina Cotizador de Riesgos Generales en el paso Resultado de la cotizacion se muestra el resumen de la cotizacion del producto Hidrocarburos con cobertura Solo RC")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnElPasoResultadoDeLaCotizacionSeMuestraElResumenDeLaCotizacionDelProductoHidrocarburosConCoberturaSoloRC() {
        cotizacionRiesgosGeneralesResultadoCotizacionPage.processAndAssertSuccessClickBotonSiguiente(120_000,500);
        ExtentEvidence.shot("Evidencia despues de visualizar el numero de cotizacion");
        cotizacionRiesgosGeneralesResultadoCotizacionPage.assertLoaded();
        cotizacionRiesgosGeneralesResultadoCotizacionPage.assertLoadedHidrocarburosSoloRC();
    }

    @Then("en la pagina Cotizador de Riesgos Generales en el paso Resultado de la cotizacion se muestra el resumen de la cotizacion del producto CAR Lite")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnElPasoResultadoDeLaCotizacionSeMuestraElResumenDeLaCotizacionDelProductoCARLite() {
        cotizacionRiesgosGeneralesResultadoCotizacionPage.processAndAssertSuccessClickBotonSiguiente(120_000,500);
        ExtentEvidence.shot("Evidencia despues de visualizar el numero de cotizacion");
        cotizacionRiesgosGeneralesResultadoCotizacionPage.assertLoadedCarLite();
    }

}
