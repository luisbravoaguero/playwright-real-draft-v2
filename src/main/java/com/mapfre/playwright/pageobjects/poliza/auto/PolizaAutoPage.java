package com.mapfre.playwright.pageobjects.poliza.auto;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class PolizaAutoPage extends BasePage {
    private final Locator title;
    private final Locator cotizarPolizaAutoButton;
    private final Locator emitirPolizaAutoButton;
    private final Locator emitirPolizaAutoUsadoButton;

    public PolizaAutoPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("¿Qué quieres hacer?"));
        this.cotizarPolizaAutoButton = page.getByText("Cotizar póliza de autos");
        this.emitirPolizaAutoButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Emitir póliza auto nuevo"));
        this.emitirPolizaAutoUsadoButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Emitir póliza auto usado"));
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
    }

    public void clickCotizarPolizaAutosButton(){
        clickAndSync(cotizarPolizaAutoButton);
    }

    public void clickEmitirPolizaAutosButton(){
        clickAndSync(emitirPolizaAutoButton);
    }
    public void clickEmitirPolizaAutosUsadoButton() {clickAndSync(emitirPolizaAutoUsadoButton);}
}
