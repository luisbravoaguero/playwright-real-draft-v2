package com.mapfre.playwright.pageobjects.poliza.fola;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ResumenCotizacionFolaPage extends BasePage {
    private final Locator title;
    private final Locator ventanaErrorTitulo;
    private final Locator ventanaErrorDescripcion;
    private final Locator nroCotizacion;
    private final Locator emitirPolizaButton;

    public ResumenCotizacionFolaPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotización"));
        this.ventanaErrorTitulo = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Error"));
        this.ventanaErrorDescripcion = page.getByText("Error en");
        this.nroCotizacion = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotización"));
        this.emitirPolizaButton = page.getByText("Emitir Póliza");

    }

    public void processAndAssertSuccess(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                ventanaErrorDescripcion,
                title,
                ventanaErrorTitulo,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + ventanaErrorDescripcion.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }

    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Cotización DEBE SER VISIBLE");
        String titulo = title.innerText();
        System.out.println("Titulo capturado: " + titulo);
        ElementAsserts.assertVisible(nroCotizacion, "El número de cotización no apareció.");
        String numero = nroCotizacion.innerText().trim();
        System.out.println("Número de cotización capturado: " + numero);
    }
    public void clickEmitirPoliza() {
        ElementAsserts.assertVisible(emitirPolizaButton, "BOTON Emitir poliza DEBE SER VISIBLE EN FOLA");
        emitirPolizaButton.scrollIntoViewIfNeeded();
        clickAndSync(emitirPolizaButton);
        log.info("[FOLA][RESUMEN] Botón Emitir póliza clickeado correctamente");
    }

}