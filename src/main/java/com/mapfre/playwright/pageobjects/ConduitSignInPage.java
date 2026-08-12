package com.mapfre.playwright.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.mapfre.asserts.ElementAsserts;

public class ConduitSignInPage extends BasePage {

    private final Locator signInHeading;
    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator signInButton;

    public ConduitSignInPage(Page page) {
        super(page);
        this.signInHeading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Sign in"));
        this.emailInput = page.getByPlaceholder("cophenage");//Email
        this.passwordInput = page.getByPlaceholder("Password");
        this.signInButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in"));
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(signInHeading, "El encabezado 'Sign in' debe ser visible");
    }

    public void fillEmail(String email) {
        fillAndSync(emailInput, email);
    }

    public void fillPassword(String password) {
        fillAndSync(passwordInput, password);
    }

    public void clickSignInButton() {
        clickAndSync(signInButton);
    }

    public void signIn(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        clickSignInButton();
    }
}

