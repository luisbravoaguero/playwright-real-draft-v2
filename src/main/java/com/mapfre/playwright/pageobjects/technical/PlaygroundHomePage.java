package com.mapfre.playwright.pageobjects.technical;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.config.PlaygroundConfig;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import java.net.URI;

public final class PlaygroundHomePage extends BasePage {
    private final Locator rollerShadesCard;
    private final Locator rollerShadesText;
    private final Locator rollerShadesButton;
    private final Locator statusParagraph;

    public PlaygroundHomePage(Page page) {
        super(page);
        this.rollerShadesText = page.getByText("Roller Shades", new Page.GetByTextOptions().setExact(true));
        this.rollerShadesButton = page.locator("i.nb-roller-shades");
        this.statusParagraph = rollerShadesText.locator("..").locator("..").locator(".status.paragraph-2");
        this.rollerShadesCard = null;
    }

    public void assertLoaded() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.locator("body").waitFor();

        URI destination = URI.create(page.url());
        String host = destination.getHost();
        String expectedHost = PlaygroundConfig.getPlaygroundHost();
        
        if (!expectedHost.equals(host)) {
            throw new FrameworkException(
                    "El popup abrió un host inesperado. Esperado: " + expectedHost + ", Actual: " + host + 
                    ". URL completa: " + page.url());
        }
        
        String path = destination.getPath();
        if (!"/pages/iot-dashboard".equals(path)) {
            throw new FrameworkException(
                    "El popup no abrió el homepage esperado. Esperado path: /pages/iot-dashboard, Actual: " + path +
                    ". URL completa: " + page.url());
        }
    }

    public void assertRollerShadesVisible() {
        ElementAsserts.assertVisible(
                rollerShadesText,
                "El texto 'Roller Shades' debe estar visible en el homepage");
    }

    public String getRollerShadesStatus() {
        String statusText = statusParagraph.textContent().trim().toUpperCase();
        return statusText;
    }

    public void clickRollerShadesButton() {
        rollerShadesButton.click();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public void assertRollerShadesStatusIsOff() {
        String status = getRollerShadesStatus();
        if (!"OFF".equals(status)) {
            throw new FrameworkException(
                    "El estado de Roller Shades debe ser OFF pero es: " + status);
        }
    }

    public boolean isClosed() {
        return page.isClosed();
    }

    public String url() {
        return page.url();
    }
}

