package com.mapfre.playwright.pageobjects.poliza.vidaley;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class PolizaVidaLeyPage extends BasePage {
    private final Locator title;
    private final Locator bandejaDeDocumentosButton;

    // Constructor
    public PolizaVidaLeyPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("¿Qué quieres hacer?"));
        this.bandejaDeDocumentosButton = page.getByText("BANDEJA DE DOCUMENTOS");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
    }

    public void clickBandejaDeDocumentosButton(){
        clickAndSync(bandejaDeDocumentosButton);
    }

}
