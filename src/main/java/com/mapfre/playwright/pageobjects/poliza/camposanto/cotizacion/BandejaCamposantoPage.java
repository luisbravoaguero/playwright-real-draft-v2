package com.mapfre.playwright.pageobjects.poliza.camposanto.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BandejaCamposantoPage extends BasePage {

    private final Locator lblBandejaCamposanto;
    private final Locator tarjetasCotizacion;
    private final Locator btnSeleccionar;

    public BandejaCamposantoPage(Page page) {
        super(page);
        this.lblBandejaCamposanto = page.locator("text=Bandeja de Camposanto");
        // cards (cada cotización)
        this.tarjetasCotizacion = page.locator("text=Nro Cotización").locator("xpath=ancestor::div[contains(@class,'card')]");
        // botón seleccionar genérico
        this.btnSeleccionar = page.locator("button:has-text('Seleccione')");
    }

    // ================= VALIDACIÓN =================

    public void assertLoaded() {
        UiSync.waitForAppIdle();

        try {
            log.info("[BANDEJA CAMPOSANTO] Validando bandeja");

            ElementAsserts.assertVisible(
                    lblBandejaCamposanto,
                    "Debe mostrarse 'Bandeja de Camposanto'"
            );

            log.info("[BANDEJA CAMPOSANTO] Bandeja cargada correctamente");

        } catch (Exception e) {
            throw new AssertExceptions("Error validando Bandeja Camposanto", e);
        }
    }

    // ================= SELECCIÓN SIMPLE =================

    public void seleccionarCotizacionPorNumero(String nroCotizacion) {

        try {
            log.info("[BANDEJA] Buscando cotización: {}", nroCotizacion);

            Locator numero = page.locator("p")
                    .filter(new Locator.FilterOptions().setHasText(nroCotizacion))
                    .first();

            long timeout = System.currentTimeMillis() + 20000;

            while (System.currentTimeMillis() < timeout) {
                if (numero.count() > 0) break;
                page.waitForTimeout(1000);
            }

            numero.waitFor();
            Locator tarjeta = numero.locator("xpath=ancestor::div[contains(@class,'card')]");
            Locator btnSeleccionar = tarjeta.locator("button:has-text('Seleccione')");
            btnSeleccionar.click();
            UiSync.waitForAppIdle();
            log.info("[BANDEJA] Cotización seleccionada");

        } catch (Exception e) {
            throw new AssertExceptions("Error seleccionando cotización: " + nroCotizacion, e);
        }
    }

    public void emitirCotizacionPorNumero(String nroCotizacion) {

        try {
            log.info("[BANDEJA] Buscando cotización: {}", nroCotizacion);

            Locator numero = page.locator("p")
                    .filter(new Locator.FilterOptions().setHasText(nroCotizacion))
                    .first();

            numero.waitFor();

            //encontrar botón "SELECCIONE" cercano al número
            Locator btnSeleccione = numero.locator("xpath=following::a[contains(.,'SELECCIONE')][1]");

            btnSeleccione.waitFor();
            btnSeleccione.click();

            log.info("[BANDEJA] Combo abierto");

            Locator opcionEmitir = page.locator("li")
                    .filter(new Locator.FilterOptions().setHasText("Emitir"));

            opcionEmitir.waitFor();
            opcionEmitir.click();

            UiSync.waitForAppIdle();

            log.info("[BANDEJA] Emitir seleccionado");

        } catch (Exception e) {
            throw new AssertExceptions("Error al emitir cotización: " + nroCotizacion, e);
        }
    }
}