package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRaceMultiSuccess;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.util.regex.Pattern;

public class PerfilCienteInclusionPage extends BasePage{
    private final Locator title;
    private final Locator filasAplicaciones;
    private final Locator saludDeclarada;
    private final Locator panelHeaderAplicacionesButton;
    private final Locator panelFooterAplicacionesButton;
    private final Locator incluirButton;
    //private final Locator modalRecibosPendientes;
    //private final Locator aceptarModalButton;
    private final Locator headerInclusionDePlanilla;
    private final Locator resultadoModalMensajeError;
    private final Locator modalRecibosPendientes;
    private final Locator vidaLeyNoDeclarada;
    public PerfilCienteInclusionPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Perfil Cliente"));
        this.filasAplicaciones = page.locator("div.col-md-12.g-list-sm");
        this.saludDeclarada =
                page.locator("ul")
                        .filter(new Locator.FilterOptions()
                                .setHas(page.locator("span.gBgcGreen1")))
                        .filter(new Locator.FilterOptions()
                                .setHasText("SALUD"))
                        .locator("li:has-text('SALUD')")
                        .locator("input[type='checkbox']")
                        .first();
        this.panelHeaderAplicacionesButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("Colectivo Asegurado")));
        this.panelFooterAplicacionesButton = page.locator("div.gnSecResultsFixed.gBgcGray5.show");
        this.incluirButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Incluir"));
        //this.modalRecibosPendientes = page.locator("div.mat-mdc-dialog-surface");
        //this.aceptarModalButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Aceptar"));
        this.headerInclusionDePlanilla = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Inclusión de planilla"));
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.modalRecibosPendientes = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(Pattern.compile("^Recibos Pendientes de Remesar")));
        this.vidaLeyNoDeclarada = page.locator("ul")
                .filter(new Locator.FilterOptions()
                        .setHas(page.locator("span.gBgcGreen1")))
                .filter(new Locator.FilterOptions()
                        .setHasText("VIDA LEY"))
                .locator("li:has-text('VIDA LEY')")
                .locator("input[type='checkbox']")
                .first();

    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Perfil Cliente DEBE SER VISIBLE");
    }
    public void seleccionarAplicacionConEstadoDeclaradaCheckBox() {
        panelHeaderAplicacionesButton.click();
        filasAplicaciones.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        if(saludDeclarada.isChecked()){
            System.out.println("El checkbox de salud con estado DECLARADA ya está seleccionado");
        }else{
            clickAndSync(saludDeclarada);
            log.info("[INCLUSION][PERFIL_CLIENTE] Checkbox de salud con estado DECLARADA seleccionado con exito");
        }
    }

    public void seleccionarAplicacionConEstadoNoDeclaradaVidaLeyCheckBox() {
        panelHeaderAplicacionesButton.click();
        filasAplicaciones.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        if(vidaLeyNoDeclarada.isChecked()){
            System.out.println("El checkbox de salud con estado DECLARADA ya está seleccionado");
        }else{
            clickAndSync(vidaLeyNoDeclarada);
            log.info("[INCLUSION][PERFIL_CLIENTE] Checkbox de Vida Ley con estado DECLARADA seleccionado con exito");
        }
    }

    public void seleccionarIncluirAplicacionButton() {
        clickAndSync(panelFooterAplicacionesButton);
        clickAndSync(incluirButton);
        log.info("[INCLUSION][PERFIL_CLIENTE] Botón Incluir Aplicación seleccionado con exito");
    }

    public void processAndAssertSuccessRecibosPendientes(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                modalRecibosPendientes,
                headerInclusionDePlanilla, // successRoot
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + modalRecibosPendientes.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INCLUSION][MODAL_RECIBOS] Modal de Recibos Pendientes apareció con éxito");
    }

    /*
    public void processAndAssertSuccessRecibosPendientes(long timeoutMs, long quietMs) {
        // Uso del nuevo helper FirstAppearanceRaceMultiSuccess.waitForFirst:
        // Argumentos esperados (contracto) para esta llamada:
        // - page: Playwright Page usado internamente para polling.
        // - action: Runnable que dispara la transiciÃ³n (null si ya se ejecutÃ³ la acciÃ³n).
        // - errorRoot: locator que indica estado de error (resultadoModalMensajeError).
        // - successRoots: array con los locators de las alternativas de success en orden; en este caso:
        //      index 0 -> aceptarModalButton (modal con boton Aceptar)
        //      index 1 -> headerDeclaracion (cabecera Declaración)
        // - errorMessageLocator: locator para extraer mensaje de error (o null para usar errorRoot)
        // - timeoutMs: timeout en ms
        // - quietMs: ventana de estabilidad en ms
        // - pollMs: poll interval en ms (aqui usamos 150)
        var result = FirstAppearanceRaceMultiSuccess.waitForFirst(
                page,
                null,
                resultadoModalMensajeError,
                new com.microsoft.playwright.Locator[]{aceptarModalButton, headerDeclaracion},
                null,
                timeoutMs,
                quietMs,
                150
        );

        // Manejo del resultado
        if (result.outcome() == FirstAppearanceRaceMultiSuccess.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
            return;
        }

        if (result.outcome() == FirstAppearanceRaceMultiSuccess.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
            return;
        }

        // SUCCESS: actuar en función del índice del success que ocurrió
        int successIdx = result.successIndex();
        if (successIdx == 0) {
            // Apareció el modal con botón Aceptar → pulsamos y esperamos la cabecera Declaración
            clickAndSync(aceptarModalButton);
            headerDeclaracion.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeoutMs));
        } else if (successIdx == 1) {
            // Ya apareció headerDeclaracion → nada que hacer
            System.out.println("Success: headerDeclaracion already visible (successIdx=1)");
        } else {
            // Success sin índice conocido → fallback
            System.out.println("Warning: SUCCESS but unknown successIndex=" + successIdx);
        }
    }*/
}
