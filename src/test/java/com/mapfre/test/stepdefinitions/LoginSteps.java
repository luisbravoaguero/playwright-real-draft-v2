package com.mapfre.test.stepdefinitions;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.playwright.pageobjects.HomePage;
import com.mapfre.playwright.pageobjects.LoginPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.*;

public class LoginSteps {
    private final BasePage basePage;
    private final LoginPage loginPage;
    private final HomePage homePage;

    public LoginSteps(PageProvider pageProvider) {
        this.loginPage = new LoginPage(pageProvider.get());
        this.basePage = new LoginPage(pageProvider.get());
        this.homePage = new HomePage(pageProvider.get());
    }

    @Given("el usuario accede a la pagina OIM con credenciales validas")
    public void elUsuarioAccedeALaPaginaOIMConCredencialesValidas() {
        //DriverManager.page().navigate(EnvironmentConfig.getAppUrl());
        loginPage.navigate();
        loginPage.loginOim();
        homePage.assertLoaded();
    }
}
