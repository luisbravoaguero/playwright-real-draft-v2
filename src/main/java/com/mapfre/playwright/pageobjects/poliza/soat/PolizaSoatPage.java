package com.mapfre.playwright.pageobjects.poliza.soat;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class PolizaSoatPage extends BasePage {
    private final Locator title;
    private final Locator consultaDocSoatButton;
    private final Locator btnEmitirPolizaSoat;

    public PolizaSoatPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("¿Qué quieres hacer?"));
        this.consultaDocSoatButton = page.getByText("Consultar documentos de soat");
        this.btnEmitirPolizaSoat = page.getByText("Emitir póliza SOAT");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
    }

    public void clickConsultaDocSoatButton(){
        clickAndSync(consultaDocSoatButton);
    }
    public void clickEmitirPolizaSOAT(){
        clickAndSync(btnEmitirPolizaSoat);
    }
}
