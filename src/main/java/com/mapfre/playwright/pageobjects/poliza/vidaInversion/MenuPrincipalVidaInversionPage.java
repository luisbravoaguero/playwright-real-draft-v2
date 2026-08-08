package com.mapfre.playwright.pageobjects.poliza.vidaInversion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class MenuPrincipalVidaInversionPage extends BasePage {
    private final Locator title;
    private final Locator cotizaPolizaVidaButton;

    public MenuPrincipalVidaInversionPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("¿Qué quieres hacer?"));
        this.cotizaPolizaVidaButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cotizar Póliza de vida"));
    }
    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
    }
    public void clickCotizaPolizaVidaBButton() {
        clickAndSync(cotizaPolizaVidaButton);
    }

}
