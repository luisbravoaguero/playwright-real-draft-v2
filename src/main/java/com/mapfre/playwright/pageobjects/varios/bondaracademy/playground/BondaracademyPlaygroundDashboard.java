package com.mapfre.playwright.pageobjects.varios.bondaracademy.playground;

import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class BondaracademyPlaygroundDashboard extends BasePage {
    private final Locator extraComponentsLink;
    public BondaracademyPlaygroundDashboard(Page page) {
        super(page);
        this.extraComponentsLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Extra Components"));
    }

    public void clickExtraComponentsLink() {clickAndSync(extraComponentsLink);}
}
