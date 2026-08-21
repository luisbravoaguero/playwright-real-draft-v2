package com.mapfre.test.pageobjects.technical;

import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import java.net.URI;

public final class PlaygroundHomePage extends BasePage {
    public PlaygroundHomePage(Page page) {
        super(page);
    }

    public void assertLoaded() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.locator("body").waitFor();

        URI destination = URI.create(page.url());
        if (!"playground.bondaracademy.com".equals(destination.getHost())) {
            throw new FrameworkException(
                    "El popup abrió un host inesperado: " + page.url());
        }
        String path = destination.getPath();
        if (path != null && !path.isBlank() && !"/".equals(path)) {
            throw new FrameworkException(
                    "El popup no abrió el homepage esperado: " + page.url());
        }
    }

    public boolean isClosed() {
        return page.isClosed();
    }

    public String url() {
        return page.url();
    }
}
