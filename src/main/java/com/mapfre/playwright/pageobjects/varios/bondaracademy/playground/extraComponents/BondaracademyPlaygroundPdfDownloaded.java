package com.mapfre.playwright.pageobjects.varios.bondaracademy.playground.extraComponents;

import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class BondaracademyPlaygroundPdfDownloaded extends BasePage {
    private final Locator downloadButton;
    public BondaracademyPlaygroundPdfDownloaded(Page page) {
        super(page);
        this.downloadButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("DOWNLOAD PDF"));
    }

    public void clickDownloadButton() {clickAndSync(downloadButton);}
}
