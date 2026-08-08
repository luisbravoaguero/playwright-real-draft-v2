package com.mapfre.test.stepdefinitions.poliza.riegosgenerales;

import com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.MenuPrincipalRiesgosGeneralesPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;

public class MenuRiesgosGeneralesSteps {
    private final MenuPrincipalRiesgosGeneralesPage menuPrincipalRiesgosGeneralesPage;

    public MenuRiesgosGeneralesSteps(PageProvider pageProvider) {
        this.menuPrincipalRiesgosGeneralesPage = new MenuPrincipalRiesgosGeneralesPage(pageProvider.get());
    }
    @And("en la pagina Riesgos Generales selecciona la opcion Cotizar poliza riesgos generales")
    public void enLaPaginaRiesgosGeneralesSeleccionaLaOpcionCotizarPolizaRiesgosGenerales() {
        menuPrincipalRiesgosGeneralesPage.clickCotizarPolizaRiesgosButton();
    }
}
