package com.mapfre.playwright.pageobjects.varios.bondaracademy.playground.extraComponents;

import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class BondaracademyPlaygroundExtraComponents extends BasePage {
    private final Locator pdfDownloadLink;
    public BondaracademyPlaygroundExtraComponents(Page page) {
        super(page);
        this.pdfDownloadLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("PDF Download"));
    }

    public void clickpdfDownloadLink() {
        this.pdfDownloadLink.click();
    }
}
