package com.mapfre.playwright.pageobjects.poliza.transporte;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class MenuPrincipalTransportePage extends BasePage {
    private final Locator title;
    private final Locator consultaDocumentoButton;
    private final Locator emitirPolizaTransporteButton;
    public MenuPrincipalTransportePage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("¿Qué quieres hacer?"));
        this.consultaDocumentoButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Consultar documentos de"));
        this.emitirPolizaTransporteButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Emitir póliza de transporte"));
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
    }
    public void clickConsultaDocumentoButton() {
        clickAndSync(consultaDocumentoButton);
    }
    public void clickEmitirPolizaTransporteButton() {
        clickAndSync(emitirPolizaTransporteButton);
    }

}
