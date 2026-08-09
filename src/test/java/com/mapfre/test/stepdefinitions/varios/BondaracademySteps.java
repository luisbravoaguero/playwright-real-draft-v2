package com.mapfre.test.stepdefinitions.varios;

import com.mapfre.playwright.pageobjects.HomePage;
import com.mapfre.playwright.pageobjects.LoginPage;
import com.mapfre.playwright.pageobjects.varios.BondaracademyPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.*;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;

public class BondaracademySteps {
    private final LoginPage loginPage;
    private final BondaracademyPage bondaracademyPage;
    public BondaracademySteps(PageProvider pageProvider) {
        this.loginPage = new LoginPage(pageProvider.get());
        this.bondaracademyPage = new  BondaracademyPage(pageProvider.get());
    }
    @Given("I open the web varios")
    public void iOpenTheWebVarios() {
        loginPage.navigate();
    }

    @When("enter a name {string} in the dialog")
    public void enterANameInTheDialog(String name) {
        bondaracademyPage.fillName(name);
    }
}
