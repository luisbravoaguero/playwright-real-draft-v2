package com.mapfre.playwright.pageobjects.poliza.fola;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MenuPrincipalFolaPage extends BasePage {
    private Locator title;
    private Locator consultaDocumentoFolaButton;
    private Locator cotizarFolaButton;

        public MenuPrincipalFolaPage(Page page) {
            super(page);
            this.title = page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING, new Page.GetByRoleOptions().setName("¿Qué quieres hacer?"));
            this.consultaDocumentoFolaButton = page.getByText("Consulta de documentos");
            this.cotizarFolaButton= page.getByText("Cotizar póliza");
        }
    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
    }
    public void clickConsultaDocumentoFolaButton() {
        clickAndSync(consultaDocumentoFolaButton);
    }
    public void clickCotizarFolaButton() {
        clickAndSync(cotizarFolaButton);
    }

}
