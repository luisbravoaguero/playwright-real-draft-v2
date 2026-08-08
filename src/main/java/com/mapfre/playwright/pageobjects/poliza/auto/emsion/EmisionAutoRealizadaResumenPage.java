package com.mapfre.playwright.pageobjects.poliza.auto.emsion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class EmisionAutoRealizadaResumenPage extends BasePage {
    private final Locator title;
    private final Locator resultadoMensajeError;
    public EmisionAutoRealizadaResumenPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("resumen poliza emitida"));
        this.resultadoMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");

    }
    public void processAndAssertVerCotizacionSuccess(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                resultadoMensajeError,
                title,
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            String allVisibleText = resultadoMensajeError.innerText();
            System.out.println(allVisibleText);
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
    }
}
