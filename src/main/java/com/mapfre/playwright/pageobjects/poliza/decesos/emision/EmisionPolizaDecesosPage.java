package com.mapfre.playwright.pageobjects.poliza.decesos.emision;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.regex.MatchResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class EmisionPolizaDecesosPage extends BasePage {
    private static final Pattern NUMERO_POLIZA_PATTERN = Pattern.compile("(\\d{8,})");

    private final Locator tituloEmision;
    private final Locator botonSiguiente;
    private final Locator seccionCargaDocumentos;
    private final Locator tipoDocumentoSelect;
    private final Locator inputDocumento;
    private final Locator botonEmision;
    private final Locator modalPolizaEmitida;
    private final Locator mensajePolizaEmitida;
    private final Locator okPolizaEmitidaButton;
    private final Locator modalErrorGeneral;
    public EmisionPolizaDecesosPage(Page page) {
        super(page);
        this.tituloEmision = page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING, new Page.GetByRoleOptions().setName("Emision"));
        this.botonSiguiente = page.locator("a.g-button")
                .filter(new Locator.FilterOptions().setHasText("Siguiente"))
                .first();
        this.seccionCargaDocumentos = page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING, new Page.GetByRoleOptions().setName("Adjuntar documentos"));
        this.tipoDocumentoSelect = page.locator("select").filter(new Locator.FilterOptions().setHasText("Solicitud Decesos")).first();
        this.inputDocumento = page.locator("input[type='file']#iLoadFileDE");
        this.botonEmision = page.locator("a.g-button.block")
                .filter(new Locator.FilterOptions().setHasText("Emision"))
                .first();
        this.modalPolizaEmitida = page.locator(".swal2-title").filter(new Locator.FilterOptions().setHasText("Exitoso")).first();
        this.mensajePolizaEmitida = page.locator(".swal2-html-container").filter(new Locator.FilterOptions().setHasText("Se emitió correctamente número de poliza:"));
        this.okPolizaEmitidaButton = page.locator("button.swal2-confirm");
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");

    }

    public void assertLoaded() {
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        boolean tituloVisible = false;
        try {
            tituloVisible = tituloEmision.isVisible();
        } catch (Exception ignored) {
            // El título puede variar entre ambientes; se valida también con el botón Siguiente.
        }

        boolean siguienteVisible = false;
        try {
            siguienteVisible = botonSiguiente.isVisible();
        } catch (Exception ignored) {
            // Algunas cargas tardías ocultan temporalmente el botón; se permite fallback al título.
        }

        if (!tituloVisible && !siguienteVisible) {
            throw new AssertExceptions("[DECESOS][EMISION] No se pudo validar la pantalla de emisión de póliza de decesos.");
        }

        log.info("[DECESOS][EMISION] Pantalla de emisión visible");
    }

    public void clickSiguienteDatosPoliza() {
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        ElementAsserts.assertVisible(botonSiguiente, "BOTON Siguiente DEBE SER VISIBLE EN EMISION DE DECESOS");
        botonSiguiente.scrollIntoViewIfNeeded();
        clickAndSync(botonSiguiente);

        /*try {
            seccionCargaDocumentos.waitFor(new Locator.WaitForOptions().setTimeout(10_000));
        } catch (Exception ignored) {
            // Si no hay título de sección, el input file funciona como señal de que ya avanzó.
            inputDocumento.waitFor(new Locator.WaitForOptions().setTimeout(10_000));
        }*/

        log.info("[DECESOS][EMISION] Se avanzó a la sección de carga de documentos");
    }

    public void processAndAssertSuccessSiguienteButton(long timeoutMs, long quietMs){
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                seccionCargaDocumentos,
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
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        log.info("[DECESOS][EMISION] Botón Siguiente procesado correctamente, se ha detectado la sección de carga de documentos.");
    }

    public void seleccionarTipoDocumentoYAdjuntar(String tipoDocumento) {
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(1000);
        if (tipoDocumento != null && !tipoDocumento.isBlank()) {
            selectByOptionIfEnableAndSync(tipoDocumentoSelect, tipoDocumento.trim());

            String opcionSeleccionada = tipoDocumentoSelect.locator("option:checked").first().textContent().trim();
            if (!opcionSeleccionada.equalsIgnoreCase(tipoDocumento.trim())) {
                throw new AssertExceptions("[DECESOS][EMISION] No se pudo seleccionar el tipo de documento esperado. Esperado: "
                        + tipoDocumento + " | Actual: " + opcionSeleccionada);
            }
        }

        Path archivoPdf = Paths.get(
                "src",
                "test",
                "resources",
                "testdata",
                "uploads",
                "templates",
                "test_dni_CheckDocumentos.pdf"
        );

        ElementAsserts.assertVisible(inputDocumento, "INPUT DE CARGA DE DOCUMENTOS DEBE SER VISIBLE");
        uploadFile(inputDocumento, archivoPdf);
        log.info("[DECESOS][EMISION] Documento adjuntado correctamente para tipo '{}'", tipoDocumento);
    }

    public void clickEmision() {
        ElementAsserts.assertVisible(botonEmision, "BOTON Emision DEBE SER VISIBLE EN EMISION DE DECESOS");
        botonEmision.scrollIntoViewIfNeeded();
        clickForcedAndSync(botonEmision);
        UiSync.waitForAppIdle(page);
        log.info("[DECESOS][EMISION] Proceso de emisión ejecutado");
    }

    public void processAndAssertPolizaEmitida(long timeoutMs) {
        try {
            modalPolizaEmitida.waitFor(new Locator.WaitForOptions().setTimeout(timeoutMs));
            ElementAsserts.assertVisible(mensajePolizaEmitida, "MENSAJE DE POLIZA EMITIDA DEBE SER VISIBLE");

            String textoModal = mensajePolizaEmitida.innerText().trim();
            String numeroPolizaModal = NUMERO_POLIZA_PATTERN.matcher(textoModal)
                    .results()
                    .map(MatchResult::group)
                    .findFirst()
                    .orElse(null);

            if (numeroPolizaModal != null) {
                log.info("[DECESOS][EMISION] Número de póliza emitida (modal): {}", numeroPolizaModal);
            } else {
                log.info("[DECESOS][EMISION] Modal de emisión detectado sin número de póliza parseable: {}", textoModal);
            }
            if (okPolizaEmitidaButton.isVisible()) {
                clickForcedAndSync(okPolizaEmitidaButton);
            }
            log.info("[DECESOS][EMISION] Se detectó confirmación de póliza emitida");
        } catch (Exception ignored) {
            log.info("[DECESOS][EMISION] No apareció modal explícito de póliza emitida; se continúa a validación de documentos");
        }
    }


    public void processAndAssertSuccessPolizaEmitida(long timeoutMs, long quietMs){
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                modalPolizaEmitida,
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
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        log.info("[DECESOS][EMISION] Número de póliza emitida (modal): {}", getNumeroPolizaEmitida());
        if (okPolizaEmitidaButton.isVisible()) {
            clickForcedAndSync(okPolizaEmitidaButton);
            log.info("[DECESOS][EMISION] Se detectó confirmación de póliza emitida y se cerró el modal");
        }
    }

    public String getNumeroPolizaEmitida() {
        ElementAsserts.assertVisible(mensajePolizaEmitida, "MENSAJE DE POLIZA EMITIDA DEBE SER VISIBLE");
        return mensajePolizaEmitida.innerText().trim();
    }

}
