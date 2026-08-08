package com.mapfre.test.stepdefinitions.poliza.transporte;

import com.mapfre.playwright.pageobjects.poliza.transporte.MenuPrincipalTransportePage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;

public class MenuTransporteSteps {
    private final MenuPrincipalTransportePage menuPrincipalTransportePage;

    public MenuTransporteSteps(PageProvider pageProvider) {
        this.menuPrincipalTransportePage = new MenuPrincipalTransportePage(pageProvider.get());
    }
    @And("en la pagina Transporte selecciona la opcion Emitir poliza de Transporte")
    public void enLaPaginaTransporteSeleccionaLaOpcionEmitirPolizaDeTransporte() {
        menuPrincipalTransportePage.assertLoaded();
        menuPrincipalTransportePage.clickEmitirPolizaTransporteButton();
    }
}
