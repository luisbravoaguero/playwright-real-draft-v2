package com.mapfre.test.stepdefinitions;

import com.mapfre.playwright.pageobjects.HomePage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.When;

public class HomeSteps {
    private final HomePage homePage;
    public HomeSteps(PageProvider pageProvider) {
        this.homePage = new HomePage(pageProvider.get());
    }

    @When("desde la pagina Home selecciona el modulo Polizas")
    public void desdeLaPaginaHomeSeleccionaElModuloPolizas() {
        homePage.assertLoaded();
        homePage.clickPolizaLink();
    }

    @When("desde la pagina Home selecciona el modulo Constancias SCTR y VL")
    public void desdeLaPaginaHomeSeleccionaElModuloConstanciasSCTRYVL() {
        homePage.assertLoaded();
        homePage.clickConstanciaSctrVLLink();
    }

    @When("el usuario redirecciona a la pagina Home")
    public void elUsuarioRedireccionaALaPaginaHome() {
        homePage.clickHomePageButton();
    }

    @When("desde la pagina Home selecciona el modulo Inspeccion de Autos")
    public void desdeLaPaginaHomeSeleccionaElModuloInspeccionDeAutos() {
        homePage.assertLoaded();
        homePage.clickInspeccionAutosLink();
    }
}
