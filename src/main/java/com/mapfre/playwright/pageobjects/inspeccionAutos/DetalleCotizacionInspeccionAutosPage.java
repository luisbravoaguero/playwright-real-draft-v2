package com.mapfre.playwright.pageobjects.inspeccionAutos;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class DetalleCotizacionInspeccionAutosPage extends BasePage {
    private final Locator detalleCotizacionTitle;
    private final Locator modalErrorGeneral;
    private final Locator solicitarInspeccionButton;
    private final Locator modalInfoSolicitarInspeccion;
    private final Locator modalInfoSolicitarInspeccionAceptarButton;
    private final Locator titleNuevaSolicitudnspeccion;

    public DetalleCotizacionInspeccionAutosPage(Page page) {
        super(page);
        this.detalleCotizacionTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Detalle cotización"));
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.solicitarInspeccionButton = page.getByText("SOLICITAR INSPECCIÓN");
        this.modalInfoSolicitarInspeccion = page.locator("div.swal2-popup.swal2-icon-info[role='dialog']");
        this.modalInfoSolicitarInspeccionAceptarButton = modalInfoSolicitarInspeccion.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("ACEPTAR"));
        this.titleNuevaSolicitudnspeccion = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Nueva Solicitud de Inspección"));

    }

    public void assertLoadedDetalleCotizacionTitle() {
        ElementAsserts.assertVisible(detalleCotizacionTitle, "TITULO Detalle cotización DEBE SER VISIBLE");
    }

    public void processAndAssertSuccessSolicitarInspeccion(long timeoutMs, long quietMs) {
        solicitarInspeccionButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                solicitarInspeccionButton::click,// action (coloca null si la accion click fue realizada)
                modalErrorGeneral,// locator de error
                modalInfoSolicitarInspeccion,// locator de éxito
                null,// coloca null si el mensaje del error esta en modalErrorGeneral
                timeoutMs,
                quietMs,// quietMs (stability)
                150// pollMs
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
        log.info("[INSPECCION AUTOS][DETALLE COTIZACION] Se muestra modal de información después de solicitar inspección: {}", result.outcome());
    }

    public void processAndAssertSuccessSolicitarInspeccionModalAceptar(long timeoutMs, long quietMs) {
        modalInfoSolicitarInspeccionAceptarButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                modalInfoSolicitarInspeccionAceptarButton::click,// action (coloca null si la accion click fue realizada)
                modalErrorGeneral,// locator de error
                titleNuevaSolicitudnspeccion,// locator de éxito
                null,// coloca null si el mensaje del error esta en modalErrorGeneral
                timeoutMs,
                quietMs,// quietMs (stability)
                150// pollMs
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
        log.info("[INSPECCION AUTOS][DETALLE COTIZACION] Se muestra página de Nueva Solicitud de Inspección después de aceptar modal: {}", result.outcome());
    }
}
