package com.mapfre.test.stepdefinitions.poliza.accidentes;

import com.mapfre.playwright.pageobjects.poliza.accidentes.*;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.Then;

public class CotizacionPolizaAccidentesSteps {

    private final CotizacionGuardadaAccidentesPage cotizacionGuardadaAccidentesPage;

    public CotizacionPolizaAccidentesSteps(PageProvider pageProvider){
        this.cotizacionGuardadaAccidentesPage = new CotizacionGuardadaAccidentesPage(pageProvider.get());
    }


    @Then("en la pagina Cotizacion guardada se genera la cotizacion correctamente")
        public void enlapaginaCotizacionguardadasegeneralacotizacioncorrectamente()
    {
        cotizacionGuardadaAccidentesPage.assertLoaded();
        cotizacionGuardadaAccidentesPage.obtenerNumeroCotizacion();
    }

}





