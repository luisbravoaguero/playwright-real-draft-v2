package com.mapfre.test.stepdefinitions.varios;

import com.mapfre.playwright.pageobjects.LoginPage;
import com.mapfre.playwright.pageobjects.varios.BondaracademyPage;
import com.mapfre.playwright.pageobjects.varios.bondaracademy.playground.BondaracademyPlaygroundDashboard;
import com.mapfre.playwright.pageobjects.varios.bondaracademy.playground.extraComponents.BondaracademyPlaygroundExtraComponents;
import com.mapfre.playwright.pageobjects.varios.bondaracademy.playground.extraComponents.BondaracademyPlaygroundPdfDownloaded;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class VerifyPdfDownloadedSteps {
    private final BondaracademyPlaygroundDashboard bondaracademyPlaygroundDashboard;
    private final BondaracademyPlaygroundExtraComponents bondaracademyPlaygroundExtraComponents;
    private final BondaracademyPlaygroundPdfDownloaded bondaracademyPlaygroundPdfDownloaded;
    public VerifyPdfDownloadedSteps(PageProvider pageProvider) {
        this.bondaracademyPlaygroundDashboard = new BondaracademyPlaygroundDashboard(pageProvider.get());
        this.bondaracademyPlaygroundExtraComponents = new BondaracademyPlaygroundExtraComponents(pageProvider.get());
        this.bondaracademyPlaygroundPdfDownloaded = new BondaracademyPlaygroundPdfDownloaded(pageProvider.get());
    }
    @When("I click on the button DOWNLOAD PDF")
    public void iClickOnTheButtonDOWNLOADPDF() {
        bondaracademyPlaygroundDashboard.clickExtraComponentsLink();
        bondaracademyPlaygroundExtraComponents.clickpdfDownloadLink();
    }

    @And("I verify the pdf content contains {string} next to the text {string}")
    public void iVerifyThePdfContentContainsNextToTheText(String arg0, String arg1) {
        bondaracademyPlaygroundPdfDownloaded.clickDownloadButton();
    }
}
