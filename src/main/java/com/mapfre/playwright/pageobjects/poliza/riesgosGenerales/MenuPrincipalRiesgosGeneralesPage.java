package com.mapfre.playwright.pageobjects.poliza.riesgosGenerales;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class MenuPrincipalRiesgosGeneralesPage extends BasePage {
    private final Locator title;
    private final Locator consultaDocumentoRiesgosGeneralesButton;
    private final Locator cotizarPolizaRiesgosButton;
    public MenuPrincipalRiesgosGeneralesPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("¿Qué quieres hacer?"));
        this.consultaDocumentoRiesgosGeneralesButton = page.getByText("Consultar documentos de");
        this.cotizarPolizaRiesgosButton = page.getByText("Cotizar póliza Riesgos");
    }
    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
    }
    public void clickConsultaDocumentoRiesgosGenealesButton() {
        clickAndSync(consultaDocumentoRiesgosGeneralesButton);
    }
    public void clickCotizarPolizaRiesgosButton() {
        clickAndSync(cotizarPolizaRiesgosButton);
    }
}
