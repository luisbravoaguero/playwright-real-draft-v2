package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class InclusionReporteConstanciaPage extends BasePage {
    private final Locator titleReporteConstancia;
    private final Locator numeroConstanciaLabel;
    private final Locator numeroConstanciaVidaLeyLabel;
    public InclusionReporteConstanciaPage(Page page) {
        super(page);
        this.titleReporteConstancia = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("REPORTE CONSTANCIA").setLevel(2));
        this.numeroConstanciaLabel = page.locator("b").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Nro\\. MP/")));
        this.numeroConstanciaVidaLeyLabel = page.locator("b").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Nro\\. VLEY/")));
    }
    public void assertLoaded() {
        ElementAsserts.assertVisible(titleReporteConstancia, "TITULO Reporte de Constancia DEBE SER VISIBLE");
        ElementAsserts.assertVisible(numeroConstanciaLabel, "El Numero de Constancia DEBE SER VISIBLE");
        log.info("[INCLUSION][NUMERO_DE_CONSTANCIA]: {}", numeroConstanciaLabel.innerText());
    }
    public void assertVidaLeyLoaded() {
        ElementAsserts.assertVisible(titleReporteConstancia, "TITULO Reporte de Constancia DEBE SER VISIBLE");
        ElementAsserts.assertVisible(numeroConstanciaVidaLeyLabel, "El Numero de Constancia DEBE SER VISIBLE");
        log.info("[INCLUSION][NUMERO_DE_CONSTANCIA_VIDA_LEY]: {}", numeroConstanciaVidaLeyLabel.innerText());
    }

}
