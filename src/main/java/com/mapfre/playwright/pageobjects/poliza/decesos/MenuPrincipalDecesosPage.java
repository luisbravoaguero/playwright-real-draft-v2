package com.mapfre.playwright.pageobjects.poliza.decesos;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class MenuPrincipalDecesosPage extends BasePage {

    private final Locator title;
    private final Locator cotizarDecesosButton;
    private final Locator consultarCotizacionesDecesosButton;

    public MenuPrincipalDecesosPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("¿Qué quieres hacer?"));
        this.cotizarDecesosButton = page.getByText("COTIZAR/EMITIR PÓLIZA DE DECESOS");
        this.consultarCotizacionesDecesosButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("CONSULTAR COTIZACIONES DE"));

    }
    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
    }

    public void clickCotizarDecesosButton() {
        clickAndSync(cotizarDecesosButton);
    }
    public void clickConsultarCotizacionesDecesosButton() {clickAndSync(consultarCotizacionesDecesosButton);}
}
