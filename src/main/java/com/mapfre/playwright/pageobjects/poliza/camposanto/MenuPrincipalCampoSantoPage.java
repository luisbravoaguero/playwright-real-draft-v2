package com.mapfre.playwright.pageobjects.poliza.camposanto;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class MenuPrincipalCampoSantoPage extends BasePage {
    private final Locator title;
    private final Locator cotizarContratoButton;

    public MenuPrincipalCampoSantoPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("¿Qué quieres hacer?"));
        this.cotizarContratoButton = page.getByText("COTIZAR CONTRATO");
    }
    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
    }

    public void clickCotizarContratoButton() {
        clickAndSync(cotizarContratoButton);
    }
}
