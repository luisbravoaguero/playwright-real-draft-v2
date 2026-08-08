package com.mapfre.test.stepdefinitions.poliza.vidaInversion;

import com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.MenuPrincipalRiesgosGeneralesPage;
import com.mapfre.playwright.pageobjects.poliza.vidaInversion.MenuPrincipalVidaInversionPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;

public class MenuPrincipalVidaInversionSteps {
    private final MenuPrincipalVidaInversionPage menuPrincipalRiesgosGeneralesPage;

    public MenuPrincipalVidaInversionSteps(PageProvider pageProvider) {
        this.menuPrincipalRiesgosGeneralesPage = new MenuPrincipalVidaInversionPage(pageProvider.get());
    }
    @And("en la pagina Vida Inversion selecciona la opcion Cotizar poliza de vida inversion")
    public void enLaPaginaVidaInversionSeleccionaLaOpcionCotizarPolizaDeVidaInversion() {
        menuPrincipalRiesgosGeneralesPage.assertLoaded();
        menuPrincipalRiesgosGeneralesPage.clickCotizaPolizaVidaBButton();
    }

}
