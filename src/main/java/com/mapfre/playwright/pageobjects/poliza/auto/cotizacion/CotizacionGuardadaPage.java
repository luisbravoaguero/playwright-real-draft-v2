package com.mapfre.playwright.pageobjects.poliza.auto.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CotizacionGuardadaPage extends BasePage {
    private final Locator title;
    private final Locator nroCotizacion;

    public CotizacionGuardadaPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotización guardada de autos"));
        this.nroCotizacion = page.locator("div.producto-documento")
                .getByText("Nro. Cotización:")
                .locator("xpath=following-sibling::span")
                .locator("b");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Cotización guardada de autos DEBE SER VISIBLE");
        ElementAsserts.assertVisible(nroCotizacion, "El número de cotización no apareció.");
        String numero = nroCotizacion.innerText().trim();
        log.info("Número de cotización capturado: " + numero);
    }

    public String obtenerNumeroCotizacion() {
        ElementAsserts.assertVisible(nroCotizacion, "El número de cotización no apareció.");
        return nroCotizacion.innerText().trim();
    }

}
