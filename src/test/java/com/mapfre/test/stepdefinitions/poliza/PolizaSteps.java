package com.mapfre.test.stepdefinitions.poliza;

import com.mapfre.playwright.pageobjects.HomePage;
import com.mapfre.playwright.pageobjects.poliza.PolizaPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PolizaSteps {
    private final PolizaPage polizaPage;
    public PolizaSteps(PageProvider pageProvider) {
        this.polizaPage = new PolizaPage(pageProvider.get());
    }

    @And("en la pagina Polizas selecciona la opcion Accidentes")
    public void enLaPaginaPolizasSeleccionaLaOpcionAccidentes() {
        polizaPage.assertLoaded();
        polizaPage.clickAccidentesLink();
    }

    @And("en la pagina Polizas selecciona la opcion Auto")
    public void enLaPaginaPolizasSeleccionaLaOpcionAuto() {
        polizaPage.assertLoaded();
        polizaPage.clickAutoLink();
    }

    @And("en la pagina Polizas selecciona la opcion Camposanto")
    public void enLaPaginaPolizasSeleccionaLaOpcionCamposanto() {
        polizaPage.assertLoaded();
        polizaPage.clickCampoSantoLink();
    }

    @And("en la pagina Polizas selecciona la opcion Decesos")
    public void enLaPaginaPolizasSeleccionaLaOpcionDecesos() {
        polizaPage.assertLoaded();
        polizaPage.clickDecesosLink();
    }

    @And("en la pagina Polizas selecciona la opcion Sctr")
    public void enLaPaginaPolizasSeleccionaLaOpcionSctr() {
        polizaPage.assertLoaded();
        polizaPage.clickSctrLink();
    }

    @And("en la pagina Polizas selecciona la opcion Soat")
    public void enLaPaginaPolizasSeleccionaLaOpcionSoat() {
        polizaPage.assertLoaded();
        polizaPage.clickSoatLink();
    }

    @And("en la pagina Polizas selecciona la opcion Transporte")
    public void enLaPaginaPolizasSeleccionaLaOpcionTransporte() {
        polizaPage.assertLoaded();
        polizaPage.clickTransporteLink();
    }

    @And("en la pagina Polizas selecciona la opcion Vida Ley")
    public void enLaPaginaPolizasSeleccionaLaOpcionVidaLey() {
        polizaPage.assertLoaded();
        polizaPage.clickVidaLeyLink();
    }

    @And("en la pagina Polizas selecciona la opcion Riesgos Generales")
    public void enLaPaginaPolizasSeleccionaLaOpcionRiesgosGenerales() {
        polizaPage.assertLoaded();
        polizaPage.clickRiesgosGeneralesLink();
    }

    @And("en la pagina Polizas selecciona la opcion FOLA")
    public void enLaPaginaPolizasSeleccionaLaOpcionFOLA() {
        polizaPage.assertLoaded();
        polizaPage.clickFolaLink();
    }

    @And("en la pagina Polizas selecciona la opcion Vida Inversion")
    public void enLaPaginaPolizasSeleccionaLaOpcionVidaInversion() {
        polizaPage.assertLoaded();
        polizaPage.clickVidaInversionLink();
    }
}
