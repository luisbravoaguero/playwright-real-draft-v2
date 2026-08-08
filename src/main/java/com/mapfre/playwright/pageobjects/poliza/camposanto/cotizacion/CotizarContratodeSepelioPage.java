package com.mapfre.playwright.pageobjects.poliza.camposanto.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CotizarContratodeSepelioPage extends BasePage {

    private final Locator titulo;
    private final Locator btnNecesidadInmediata;


    public CotizarContratodeSepelioPage(Page page) {
        super(page);
        this.titulo = page.getByRole(AriaRole.HEADING).filter(new Locator.FilterOptions().setHasText("Cotizador de Camposanto"));
        this.btnNecesidadInmediata = page.locator("div.g-box--tabs").filter(new Locator.FilterOptions().setHasText("Necesidad inmediata"));
    }

    public void assertLoadedCotizadorCampoSanto() {
        try {
            log.info("[COTIZADOR CAMPOSANTO] Validando pantalla");

            ElementAsserts.assertVisible(titulo,
                    "El título 'Cotizador de Camposanto' debe ser visible");

            ElementAsserts.assertTextContains(titulo,
                    "Cotizador de Camposanto",
                    "El título no es correcto");

            log.info("[COTIZADOR CAMPOSANTO] Pantalla OK");

        } catch (Exception e) {
            log.error("[COTIZADOR CAMPOSANTO] Error validando pantalla", e);
            throw new AssertExceptions("Error validando la página Cotizador de Camposanto:", e);
        }
    }

    public void clickNecesidadInmediata() {
        btnNecesidadInmediata.waitFor();
        btnNecesidadInmediata.click();

        log.info("[COTIZADOR CAMPOSANTO] Se realiza Click opción necesidad inmediata");

        UiSync.waitForAppIdle();
    }

}