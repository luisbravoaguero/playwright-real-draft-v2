package com.mapfre.playwright.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.mapfre.asserts.ElementAsserts;

public class ConduitHomePage extends BasePage {

    private final Locator mainHeading;
    private final Locator signInLink;
    private final Locator signUpLink;
    private final Locator homeLink;

    public ConduitHomePage(Page page) {
        super(page);
        this.mainHeading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("conduit"));
        this.signInLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign in"));
        this.signUpLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign up"));
        this.homeLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home"));
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(mainHeading, "El encabezado principal 'conduit' debe ser visible");
    }

    public void clickSignIn() {
        clickAndSync(signInLink);
    }

    public void clickSignUp() {
        clickAndSync(signUpLink);
    }

    public void clickHome() {
        clickAndSync(homeLink);
    }
}

