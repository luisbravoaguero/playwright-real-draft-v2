package com.mapfre.playwright.pageobjects.poliza.auto.emsion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.Locator;

public class PolizaEmitidaAutoPage extends BasePage {
    private final Locator title;
    private final Locator numeroPolizaLabel;

    public PolizaEmitidaAutoPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Póliza emitida de autos"));
        this.numeroPolizaLabel = page.locator("span.g-summary-data");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Póliza emitida de autos DEBE SER VISIBLE");
        ElementAsserts.assertContainsText(numeroPolizaLabel, "301", "NUMERO DE POLIZA DEBE ESTAR PRESENTE EN LA SECCION DE RESUMEN");
    }

}
