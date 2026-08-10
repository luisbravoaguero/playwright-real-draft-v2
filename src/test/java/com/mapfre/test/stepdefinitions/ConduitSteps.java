package com.mapfre.test.stepdefinitions;

import com.mapfre.playwright.pageobjects.ConduitHomePage;
import com.mapfre.playwright.pageobjects.ConduitSignInPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class ConduitSteps {
    private final ConduitHomePage conduitHomePage;
    private final ConduitSignInPage conduitSignInPage;

    public ConduitSteps(PageProvider pageProvider) {
        this.conduitHomePage = new ConduitHomePage(pageProvider.get());
        this.conduitSignInPage = new ConduitSignInPage(pageProvider.get());
    }

    @Given("el usuario accede a la pagina Conduit")
    public void elUsuarioAccedeALaPaginaConduit() {
        conduitHomePage.navigate();
        conduitHomePage.assertLoaded();
    }

    @When("desde la pagina Conduit hace clic en Sign in")
    public void desdeLaPaginaConduitHaceClicEnSignIn() {
        conduitHomePage.clickSignIn();
        conduitSignInPage.assertLoaded();
    }

    @When("en la pagina Sign in ingresa el email \"{}\" y la contraseña \"{}\"")
    public void enLaPaginaSignInIngresaEmailYContraseña(String email, String password) {
        conduitSignInPage.signIn(email, password);
    }
}

