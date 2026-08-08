package com.mapfre.playwright.pageobjects.poliza.vidaInversion.emision;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ResultadoEmisionPolizaVidaInversionPage extends BasePage {
    private final Locator polizaDeVidaEmitidatitle;
    private final Locator numeroPolizaCertirentaLabel;
    public ResultadoEmisionPolizaVidaInversionPage(Page page) {
        super(page);
        this.polizaDeVidaEmitidatitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Póliza de vida").setLevel(2));
        this.numeroPolizaCertirentaLabel = page.locator("div:has(span:has-text('Nro. Póliza:')) span.g-summary-data");
    }
    public void assertLoaded() {
        ElementAsserts.assertVisible(polizaDeVidaEmitidatitle, "TITULO Póliza de vida DEBE SER VISIBLE");
    }

    public void assertNumeroPolizaVisibleAndFilled() {
        ElementAsserts.assertVisible(numeroPolizaCertirentaLabel, "EL CAMPO NUMERO DE POLIZA DEBE SER VISIBLE");
        ElementAsserts.assertFilled(numeroPolizaCertirentaLabel, "EL CAMPO NUMERO DE POLIZA NO MUESTRA EL NUMERO DE POLIZA");
        log.info("[EMISION][RESULTADO NUMERO DE POLIZA VIDA INVERSION]: {}", numeroPolizaCertirentaLabel.innerText());
    }
}
