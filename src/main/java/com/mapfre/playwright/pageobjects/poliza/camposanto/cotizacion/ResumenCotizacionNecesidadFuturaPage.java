package com.mapfre.playwright.pageobjects.poliza.camposanto.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DownloadVsErrorRace;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;


public class ResumenCotizacionNecesidadFuturaPage extends BasePage {
    private final Locator title;
    private final Locator numeroPolizaLabel;
    private final Locator enviarCotizacionAlClienteButton;
    private final Locator descargarPdfButton;
    private final Locator modalEnviarCotizacionPorCorreo;
    private final Locator modalEnviarCotizacionPorCorreoInput;
    private final Locator modalEnviarCotizacionPorCorreoEnviarButton;
    private final Locator modalEnviarCotizacionPorCorreoErrorRoot;
    private final Locator modalCotiEnviarPorCorreoSuccessRoot;
    private final Locator modalCotiEnviarPorCorreoSuccessMessage;
    private final Locator modalCorreoEnviadoOKButton;



    public ResumenCotizacionNecesidadFuturaPage(Page page) {
        super(page);

        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotizador de Camposanto"));
        this.numeroPolizaLabel = page.locator("div.row.justify-content-end");
        this.enviarCotizacionAlClienteButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("ENVIAR COTIZACIÓN A CLIENTE"));
        this.descargarPdfButton = page.locator("a:has-text('DESCARGAR PDF')");
        this.modalEnviarCotizacionPorCorreo = page.locator("div.cdk-overlay-pane");
        this.modalEnviarCotizacionPorCorreoInput = modalEnviarCotizacionPorCorreo.locator("oim-input[name='txtEmail'] input");
        this.modalEnviarCotizacionPorCorreoEnviarButton = modalEnviarCotizacionPorCorreo.locator("a:has-text('ENVIAR')");
        this.modalEnviarCotizacionPorCorreoErrorRoot = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.modalCotiEnviarPorCorreoSuccessRoot = page.locator("div.swal2-popup.swal2-modal.swal2-icon-success[role='dialog']");
        this.modalCotiEnviarPorCorreoSuccessMessage = modalCotiEnviarPorCorreoSuccessRoot.locator("#swal2-html-container");
        this.modalCorreoEnviadoOKButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK"));

    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
        ElementAsserts.assertContainsText(numeroPolizaLabel, "260", "NUMERO DE POLIZA DEBE ESTAR PRESENTE EN LA SECCION DE RESUMEN");
        String numeroPoliza = numeroPolizaLabel.textContent();
                log.info("Número de póliza: {}", numeroPoliza);

    }

    public void fillFormEnviarCotizacionPorCorreo(String correoElectronico) {
        clickAndSync(enviarCotizacionAlClienteButton);
        processAndAssertVisualizacionModalEnviarCorreoSuccess(30_000,500);
        clearAndFill(modalEnviarCotizacionPorCorreoInput, correoElectronico);
        clickAndSync(modalEnviarCotizacionPorCorreoEnviarButton);
    }

    public void processAndAssertVisualizacionModalEnviarCorreoSuccess(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                modalEnviarCotizacionPorCorreoErrorRoot,
                modalEnviarCotizacionPorCorreo,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + modalEnviarCotizacionPorCorreoErrorRoot.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");

        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
    }
    public void processAndAssertProcesarEnviarCorreoSuccess(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                modalEnviarCotizacionPorCorreoErrorRoot,
                modalCotiEnviarPorCorreoSuccessRoot,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + modalEnviarCotizacionPorCorreoErrorRoot.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");

        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        ElementAsserts.assertContainsText(modalCotiEnviarPorCorreoSuccessMessage, "Cotización enviada con éxito", "El mensaje de éxito al enviar la cotización por correo debe contener 'Cotización enviada con éxito'");
        ElementAsserts.assertVisible(modalCorreoEnviadoOKButton, "Botón OK del modal de Correo enviado debe ser visible");
        clickAndSync(modalCorreoEnviadoOKButton);
    }

    public void downloadPdfAndAssert(){
        ElementAsserts.assertVisible(descargarPdfButton,"El boton descargar PDF debe ser visible");
        // 2) Race: UI error vs download completion
        DownloadVsErrorRace.Result r = DownloadVsErrorRace.race(
                page,
                descargarPdfButton::click,
                modalEnviarCotizacionPorCorreoErrorRoot,
                modalEnviarCotizacionPorCorreoErrorRoot,
                40_000, // timeoutMs
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
            throw new FrameworkException("Ni el error modal ni la finalización de la descarga ocurrieron dentro del tiempo de espera.");
        }

        // DOWNLOAD_SUCCESS -> continue
    }
}
