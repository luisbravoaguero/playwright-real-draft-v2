package com.mapfre.test.stepdefinitions.poliza.auto;

import com.mapfre.playwright.pageobjects.poliza.auto.PolizaAutoPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;

public class PolizaAutoSteps {
    private final PolizaAutoPage polizaAutoPage;
    public PolizaAutoSteps(PageProvider pageProvider) {
        this.polizaAutoPage = new PolizaAutoPage(pageProvider.get());
    }
    @And("en la pagina Auto selecciona la opcion Emitir poliza auto usado")
    public void enLaPaginaAutoSeleccionaLaOpcionEmitirPolizaAutoUsado() {
        polizaAutoPage.assertLoaded();
        polizaAutoPage.clickEmitirPolizaAutosUsadoButton();
    }
}
