package com.mapfre.playwright.pageobjects.poliza.sctr.emision;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DownloadVsErrorRace;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.regex.Pattern;

public class ResumenEmisionSctrPeriodoRegularPage extends BasePage {
    private final Locator polizaProcesadaConExitoTitle;
    private final Locator polizaInformacionCard;
    private final Locator ventanaErrorTitulo;
    private final Locator ventanaErrorDescripcion;
    private final Locator enviarPorCorreoButton;
    private final Locator emailModal;
    private final Locator emisorEmailInput;
    private final Locator asuntoEmailInput;
    private final Locator comentarioEmailInput;
    private final Locator enviarButton;
    private final Locator sendEmailModal;
    private final Locator closeIcon;
    private final Locator confirmationMessage;
    private final Locator cerrarButton;
    private final Locator resultadoMensajeError;
    public ResumenEmisionSctrPeriodoRegularPage(Page page) {
        super(page);
        this.polizaProcesadaConExitoTitle = page.locator("h2").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Su solicitud fue procesada con éxito", Pattern.CASE_INSENSITIVE)));
        this.polizaInformacionCard = page.locator("div.g-box ul.g-list.second-design");
        this.ventanaErrorTitulo = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ERROR"));
        this.ventanaErrorDescripcion = page.getByText("Ocurrió un error inesperado.");
        this.enviarPorCorreoButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText(Pattern.compile("ENVIAR POR CORREO", Pattern.CASE_INSENSITIVE)));
        this.emailModal = page.locator("polizas-modal-send-email");
        this.emisorEmailInput = emailModal.locator("oim-input[formcontrolname='mPara'] input");
        this.asuntoEmailInput = emailModal.locator("oim-input[formcontrolname='mAsunto'] input");
        this.comentarioEmailInput = emailModal.locator("oim-text-area[formcontrolname='mComentario'] textarea");
        this.enviarButton = page.locator("button[type='submit']").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Enviar", Pattern.CASE_INSENSITIVE)));
        this.sendEmailModal = page.locator("polizas-modal-send-email div.g-modal-send-email");
        this.closeIcon = sendEmailModal.locator("i.g-modal--close");
        this.confirmationMessage = sendEmailModal.locator("p.text-center");
        this.cerrarButton = sendEmailModal.locator("a.g-button", new Locator.LocatorOptions().setHasText("Cerrar"));
        this.resultadoMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(polizaProcesadaConExitoTitle, "TITULO Su solicitud fue procesada con éxito DEBE SER VISIBLE");
        sync();
        waitRandomBetween(3000);
        waitForNetworkIdle();
        polizaInformacionCard.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(40_000));
        log.info("[EMISION][RESUMEN EMISION SCTR PERIODO REGULAR] Página Resumen Emisión SCTR Periodo Regular cargada correctamente");
    }

    public void validarVisibilidadYContenidoNumeroPoliza() {
        ElementAsserts.assertVisible(polizaInformacionCard.first(), "LA CARD DE INFORMACION DE LA POLIZA DEBE SER VISIBLE");
        int cardCount = polizaInformacionCard.count();

        for (int i = 0; i < cardCount; i++) {
            Locator card = polizaInformacionCard.nth(i);
            String label = card.locator("li .item-label").innerText();
            String nro = card.locator("li.cnt-item .item-dato").innerText();
            log.info("[EMISION][RESUMEN EMISION SCTR PERIODO REGULAR] Documento: {} | Numbero: {}", label.trim(), nro.trim());
        }
        log.info("[EMISION][RESUMEN EMISION SCTR PERIODO REGULAR] La informacion de la poliza se muestra correctamente");
    }

    public void valdiarDescargaConstanciaSctrPensionSaludPeriodoRegular() {
        Locator constanciaCard = polizaInformacionCard.filter(new Locator.FilterOptions().setHasText(Pattern.compile("Constancia", Pattern.CASE_INSENSITIVE)));
        Locator constanciaButton = constanciaCard.locator("a.g-button");
        ElementAsserts.assertVisible(constanciaButton,"El boton descargar constancia DEBE SER VISIBLE");
        DownloadVsErrorRace.Result r = DownloadVsErrorRace.race(
                page,
                constanciaButton::click,
                ventanaErrorTitulo,
                ventanaErrorDescripcion,
                120_000, // timeoutMs
                300, // quietMs (estabilidad para evitar falsos positivos por parpadeo)
                80 // pollMs
        );

        // 3) Decide outcome
        if (r.outcome() == DownloadVsErrorRace.Outcome.ERROR_UI_FIRST) {
            // best-effort evidence + cleanup
            ElementAsserts.assertUIMessage("Apareció un error modal antes de que finalizara la descarga. Mensaje: " + r.uiErrorText());
        }

        if (r.outcome() == DownloadVsErrorRace.Outcome.DOWNLOAD_FAILED) {
            throw new FrameworkException("La descarga finalizó pero FALLÓ. error=" + r.downloadFailure() +
                    " nombre_archivo=" + r.filename() + " url=" + r.url());
        }

        if (r.outcome() == DownloadVsErrorRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Ni el error modal ni la finalización de la descarga ocurrieron dentro del tiempo de espera. Recibo Pension Descargar");
        }

        // DOWNLOAD_SUCCESS -> continue
        log.info("[EMISION][RESUMEN EMISION SCTR PERIODO REGULAR] Descarga de constancia exitosa. nombre_archivo={} url={}", r.filename(), r.url());
    }

    public void processAndAssertSuccessAbrirModalEnviarCorreoElectronico(long timeoutMs, long  quietMs) {
        sync();
        waitRandomBetween(2500);
        ElementAsserts.assertVisible(enviarPorCorreoButton, "EL BOTON ENVIAR POR CORREO DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                enviarPorCorreoButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoMensajeError,
                emailModal,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[EMISION][RESUMEN EMISION SCTR PERIODO REGULAR] Modal Enviar correo electronico abierto exitosamente.");
    }

    public void fillInformacionPolizaPorCorreoElectronico(String correoElectronico) {
        ElementAsserts.assertVisible(emailModal, "EL MODAL DE ENVIO DE CORREO ELECTRONICO DEBE SER VISIBLE");
        fillAndSync(emisorEmailInput, correoElectronico);
        fillAndBlurSync(asuntoEmailInput, "Informacion poliza SCTR Periodo Regular 701 702");
        //fillAndBlurSync(comentarioEmailInput, "Poliza SCTR Periodo Regular 701 702");
        log.info("[EMISION][RESUMEN EMISION SCTR PERIODO REGULAR] Formulario de envio de correo electronico llenado exitosamente.");
    }

    public void processAndAssertSuccessModalEnviarCorreoElectronico(long timeoutMs, long  quietMs) {
        ElementAsserts.assertVisible(sendEmailModal.first(), "EL MODAL DE ENVIO DE CORREO ELECTRONICO DEBE SER VISIBLE");
        ElementAsserts.assertVisible(enviarButton, "EL BOTON ENVIAR DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                enviarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoMensajeError,
                confirmationMessage,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[EMISION][RESUMEN EMISION SCTR PERIODO REGULAR] Modal Enviar correo electronico enviado exitosamente.");
    }

    public void assertMensajeExitoEnvioCorreoElectronico() {
        ElementAsserts.assertTextContains(confirmationMessage, "En unos momentos el documento", "El mensaje de exito de envio de correo electronico DEBE SER VISIBLE");
        log.info("[EMISION][RESUMEN EMISION SCTR PERIODO REGULAR] Mensaje de exito de envio de correo electronico verificado exitosamente.");
    }
}
