package com.mapfre.playwright.pageobjects.poliza.auto.emsion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class EmisionAutoUsadoRealizadaResumenPage extends BasePage {
    private final Locator modalInfoGeneral;
    private final Locator OKEmisionModalInfoButton;
    private final Locator modalErrorGeneral;
    private final Locator polizaEmitidaTitle;
    private final Locator numeroPolizaLabel;
    public EmisionAutoUsadoRealizadaResumenPage(Page page) {
        super(page);
        this.modalInfoGeneral = page.locator("div.swal2-popup.swal2-icon-info[role='dialog']");
        this.OKEmisionModalInfoButton = modalInfoGeneral.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName(Pattern.compile("^ok$", Pattern.CASE_INSENSITIVE)));
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.polizaEmitidaTitle = page.locator(".g-title h2.gH1").filter(new Locator.FilterOptions().setHasText("Póliza emitida"));
        this.numeroPolizaLabel = page.locator(".g-summary-data");

    }

    public void processAndAssertSuccessOKModalInfoEmisionPolizaButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(modalInfoGeneral, "El modal de información general DEBE SER VISIBLE después de emitir la póliza de auto usado");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                OKEmisionModalInfoButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                polizaEmitidaTitle.first(),
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );
        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[EMISION AUTO USADO REALIZADA][RESUMEN] Modal de información general procesado correctamente, se hizo click en el botón OK.");
    }

    public void assertLoadedResumenPolizaAutoUsado() {
        ElementAsserts.assertVisible(polizaEmitidaTitle, "El titulo Póliza emitida DEBE SER VISIBLE en el resumen de póliza de auto usado");
        ElementAsserts.assertVisible(numeroPolizaLabel, "El numero de póliza DEBE SER VISIBLE en el resumen de póliza de auto usado");
        ElementAsserts.assertFilled(numeroPolizaLabel, "El numero de póliza DEBE ESTAR COMPLETADO en el resumen de póliza de auto usado");
        log.info("[EMISION AUTO USADO REALIZADA][RESUMEN] Página de resumen de póliza de auto usado cargada correctamente");
        log.info("[EMISION AUTO USADO REALIZADA][RESUMEN] Número de póliza emitida es {}",numeroPolizaLabel.innerText());
        waitUntilItemDatoAttributeIsNFilled(10);

    }
}
