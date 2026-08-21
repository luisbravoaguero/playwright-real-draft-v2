package com.mapfre.test.pageobjects.technical;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.playwright.tabs.ScenarioTabs;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public final class WindowPage extends BasePage {
    public static final String URL = "https://playground.bondaracademy.com/pages/modal-overlays/window";
    private static final String HOMEPAGE_TAB = "bondar-homepage";

    private final ScenarioTabs tabs;
    private final Locator openHomepage;

    public WindowPage(Page page, ScenarioTabs tabs) {
        super(page);
        this.tabs = tabs;
        openHomepage = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Open homepage in a new tab").setExact(true));
    }

    public void open() {
        page.navigate(URL);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        ElementAsserts.assertVisible(
                openHomepage,
                "El botón para abrir el homepage en una pestaña nueva debe estar visible");
    }

    public PlaygroundHomePage openHomepageInNewTab() {
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
}
