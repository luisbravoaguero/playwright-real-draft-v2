package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.asserts.ElementAsserts;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.regex.Pattern;

public class DeclaracionDetalleDeRiesgoPage extends BasePage {
    private final Locator titleInformacionDeclaracionLabel;
    private final Locator datosObraInput;
    private final Locator generarButton;
    private final Locator resultadoModalMensajeError;
    //private final Locator modalInfoGenerarDeclaracion;
    private final Locator modal;
    private final Locator modalGenerarButton;
    private final Locator successModal;
    private final Locator successModalButton;
    private final Locator numeroConstanciaLabel;
    private final Locator numeroConstanciaVidaLeyLabel;
    public DeclaracionDetalleDeRiesgoPage(Page page) {
        super(page);
        this.titleInformacionDeclaracionLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Información de Declaración").setLevel(2));
        this.datosObraInput = page.locator("oim-text-area[name='nDatosObra'] textarea");
        this.generarButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Generar"));
        //this.modalInfoGenerarDeclaracion = page.locator("div.swal2-popup.swal2-icon-info[role='dialog']");
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.modal = page.getByRole(AriaRole.DIALOG, new Page.GetByRoleOptions().setName("GENERAR DECLARACIÓN"));
        this.modalGenerarButton = modal.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("GENERAR"));
        this.successModal = page.locator("div.swal2-popup.swal2-icon-success");
        this.successModalButton = successModal.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("OK"));
        this.numeroConstanciaLabel = page.locator("b").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Nro\\. MP/")));
        this.numeroConstanciaVidaLeyLabel = page.locator("b").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Nro\\. VLEY/")));
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(titleInformacionDeclaracionLabel, "TITULO Información de Declaración DEBE SER VISIBLE");
    }
    public void fillDatosObra() {
        //si desea este dato puede ser parametrizado en gherkins, por ahora se coloca un valor en duro para la prueba
        fillAndSync(datosObraInput, "MAPFRE");
        log.info("[DECLARACION][GENERAR_CONSTANCIA] Datos de obra llenados correctamente con exito");
    }

    public void clickGenerarButton() {
        clickAndSync(generarButton);
    }
    public void processAndAssertSuccessProcesarBotonGenerar(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                modal,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[DECLARACION][GENERAR_CONSTANCIA] Modal de confirmación para generar constancia generado con exito");

    }

    public void processAndAssertSuccessProcesarBotonGenerarconExito(long timeoutMs, long quietMs) {
        modal.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        modalGenerarButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                modalGenerarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                successModal,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[DECLARACION][GENERAR_CONSTANCIA] Proceso de constancia procesada con exito");
    }

    public void processAndAssertSuccessProcesarDeclaracionPantallaFinal(long timeoutMs, long quietMs) {
        successModal.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        successModalButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                successModalButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                numeroConstanciaLabel,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[DECLARACION][GENERAR_CONSTANCIA] Proceso resumen de la constancia generado con exito");

    }

    public void processAndAssertSuccessProcesarDeclaracionVidaLeyPantallaFinal(long timeoutMs, long quietMs) {
        successModal.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        successModalButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                successModalButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                numeroConstanciaVidaLeyLabel,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INCLUSION][GENERAR_CONSTANCIA] Proceso resumen de la constancia generado Vida Ley con exito");
    }
}
