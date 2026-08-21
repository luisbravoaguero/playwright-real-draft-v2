package com.mapfre.playwright.pageobjects.technical;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.config.PlaygroundConfig;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.playwright.tabs.ScenarioTabs;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public final class WindowPage extends BasePage {
    private static final String HOMEPAGE_TAB = "pages/iot-dashboard";

    private final ScenarioTabs tabs;
    private final Locator openHomepage;
    private final Locator modalOverLayLink;
    private final Locator windowSubLink;

    public WindowPage(Page page, ScenarioTabs tabs) {
        super(page);
        this.tabs = tabs;
        openHomepage = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("OPEN HOMEPAGE IN A NEW TAB"));
        this.modalOverLayLink = page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Modal & Overlays"));
        this.windowSubLink = page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Window"));
    }

    public void open() {
        page.navigate(PlaygroundConfig.getPlaygroundUrl());
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        ElementAsserts.assertVisible(
                modalOverLayLink,
                "El link para abrir el menu Modal & OverLays en una pestaña nueva debe estar visible");
    }

    public PlaygroundHomePage openHomepageInNewTab() {
        openContainerToGetNewTabButton();
        Page popup = tabs.open(HOMEPAGE_TAB, page, openHomepage::click);
        return new PlaygroundHomePage(popup);
    }

    public void closeHomepage() {
        tabs.close(HOMEPAGE_TAB);
    }

    public void assertStillOpen() {
        ElementAsserts.assertVisible(
                openHomepage,
                "La pestaña Window original debe continuar abierta después de cerrar el popup");
    }

    public int contextPageCount() {
        return page.context().pages().size();
    }

    public int ownedPopupCount() {
        return tabs.openCount();
    }

    public void openContainerToGetNewTabButton() {
        modalOverLayLink.click();
        windowSubLink.click();
        waitRandomBetween(1000);
    }
}
