package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.constanciaManualSctrGeneral;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class GenerarNuevaConstanciaManualModalPage extends BasePage {
    private final Locator title;
    //Modal Generar Nueva Constancia Manual
    private final Locator fechaVigenciaSelect;
    private final Locator fechaCoberturasRadioButton;
    private final Locator centroTrabajoInput;
    private final Locator cargaPlanillaInput;
    private final Locator procesarButton;
    private final Locator guardarButton;
    private final Locator cajaArchivoAdjunto;
    private final Locator archivoAdjuntoNombreLabel;
    private final Locator seccionTrabajadorLabel;

    //Modal Resultado del Procesamiento
    private final Locator resultadoModalMensajeError;
    private final Locator resultadoModalMensajeExito;

    //Modal Lista de Observaciones
    private final Locator listaObservacionesAseguradoModal;
    private final Locator aceptarButton;

    //Modal Resultado Final
    private final Locator numeroConstanciaManualLabel;
    private final Locator descargarConstanciaButton;
    public GenerarNuevaConstanciaManualModalPage(Page page) {
        super(page);
        this.title = page.locator("mat-dialog-container:has-text('Generar Nueva Constancia Manual')");
        this.fechaVigenciaSelect = page.locator("oim-select[label='Seleccione vigencia'] select");
        this.fechaCoberturasRadioButton = page.locator("oim-radio[label='Fecha de coberturas'] input[type='radio']");
        this.centroTrabajoInput = page.locator("oim-text-area[name='nCentroTrabajoProof'] textarea");
        this.cargaPlanillaInput = page.locator("input[name='nCargarPlanillaProof']");
        this.procesarButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Procesar"));
        this.guardarButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Guardar"));
        this.cajaArchivoAdjunto = page.locator("div.g-box-attachment");
        this.archivoAdjuntoNombreLabel = page.locator("label b:has-text('Adjunto')");
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.listaObservacionesAseguradoModal = page.locator("mat-dialog-container:has-text('LISTA DE OBSERVACIONES')");
        this.aceptarButton = page.locator("a.g-button:has-text('Aceptar')");
        this.seccionTrabajadorLabel = page.locator("nsctr-workers-information");
        this.resultadoModalMensajeExito = page.locator("div.swal2-popup.swal2-icon-success[role='dialog']");
        this.numeroConstanciaManualLabel = resultadoModalMensajeExito.locator(".swal2-html-container");
        this.descargarConstanciaButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("DESCARGAR CONSTANCIA"));
    }

    /**
     * Verifica que el modal de generación de nueva constancia manual haya cargado correctamente
     * validando que el título sea visible.
     */
    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Generar Nueva Constancia Manual DEBE SER VISIBLE");
    }

    /**
     * Completa el formulario de generación de nueva constancia manual
     * seleccionando la fecha de coberturas, la vigencia y el centro de trabajo.
     */
    public void fillFormGenerarNuevaConstanciaManualModal() {
        clickAndSync(fechaCoberturasRadioButton);
        fechaVigenciaSelect.selectOption(new SelectOption().setIndex(1));
        selectIndexOptionIfEnabledAndSync(fechaVigenciaSelect, 1);
        fillAndSync(centroTrabajoInput, "Centro de Trabajo Ejemplo");
        log.info("[CONSTANCIA_MANUAL][MODAL_GENERAR_CONSTANCIA] Formulario de constancia completado con exito");
    }

    /**
     * Carga el archivo de plantilla con los datos de los asegurados
     * y valida que el archivo se haya adjuntado correctamente.
     *
     * @param template la ruta del archivo de plantilla a cargar
     * @param sheetName el nombre de la hoja del archivo para validación
     */
    public void fillFormDatosDeLosAsegurados(Path template, String sheetName) {
        cargaPlanillaInput.setInputFiles(template);
        ElementAsserts.assertContainsText(cajaArchivoAdjunto,sheetName,"La planilla no se ha cargado correctamente");
        ElementAsserts.assertVisible(archivoAdjuntoNombreLabel,"El label de archivo adjunto no es visible, la planilla no se ha cargado correctamente");
        log.info("[CONSTANCIA_MANUAL][MODAL_GENERAR_CONSTANCIA] Plantilla de asegurados cargada con exito");
    }

    /**
     * Procesa el botón "Procesar" y valida que sea exitoso.
     * Espera a que aparezca la lista de observaciones o un mensaje de error,
     * verificando que el procesamiento de datos sea correcto.
     *
     * @param timeoutMs tiempo máximo de espera en milisegundos
     * @param quietMs tiempo de estabilidad para confirmar la aparición del elemento
     */
    public void processAndAssertSuccessBotonProcesar(long timeoutMs, long quietMs) {
        procesarButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                procesarButton::click,               // Realizar click en el botón procesar
                resultadoModalMensajeError,          // Elemento esperado: mensaje de error
                listaObservacionesAseguradoModal,    // Elemento esperado: lista de observaciones
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
        log.info("[CONSTANCIA_MANUAL][MODAL_GENERAR_CONSTANCIA] Asegurados procesados con exito");
    }


    /**
     * Procesa el botón "Aceptar" de la lista de observaciones y valida el resultado.
     * Espera a que aparezca la sección de trabajadores con los datos cargados,
     * verificando que se han procesado correctamente los trabajadores del formulario.
     *
     * @param timeoutMs tiempo máximo de espera en milisegundos
     * @param quietMs tiempo de estabilidad para confirmar la aparición del elemento
     */
    public void processAndAssertSuccessBotonListaObservaciones(long timeoutMs, long quietMs) {
        aceptarButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarButton::click,                // Realizar click en el botón aceptar
                resultadoModalMensajeError,          // Elemento esperado: mensaje de error
                seccionTrabajadorLabel,              // Elemento esperado: sección de trabajadores
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
        ElementAsserts.assertTextMatches(seccionTrabajadorLabel, Pattern.compile(".*Se ha cargado un total de\\s+\\d+\\s+trabajador\\(es\\).*"),"Error al cargar trabajadores");
        log.info("[CONSTANCIA_MANUAL][MODAL_OBSERVACIONES] Lista de observaciones procesadas con exito");
    }

    /**
     * Procesa el botón "Guardar" y valida que la constancia se haya generado exitosamente.
     * Espera a que aparezca el mensaje de éxito con el número de constancia manual,
     * verifica que el botón de descargar constancia esté disponible y registra el resultado.
     *
     * @param timeoutMs tiempo máximo de espera en milisegundos
     * @param quietMs tiempo de estabilidad para confirmar la aparición del elemento
     */
    public void processAndAssertSuccessBotonGuardar(long timeoutMs, long quietMs) {

        // ✅ 1. Register network listeners BEFORE any action
        page.onRequest(request ->
                log.info("[REQUEST] {} {}", request.method(), request.url())
        );

        page.onResponse(response ->
                log.info("[RESPONSE] {} {} (status: {})", response.url(), response.statusText(), response.status())
        );

        page.onRequestFailed(req ->
                log.error("[REQUEST FAILED] {} → {}", req.url(), req.failure())
        );

        page.onRequestFinished(req ->
                log.info("[REQUEST FINISHED] {}", req.url())
        );

        // ✅ 2. Wait for the button to be visible
        guardarButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        // ✅ 3. Click and wait for the backend response at the same time
        Response apiResponse = page.waitForResponse(
                resp -> resp.url().contains("/app/per/api/oimconstancias/1.0/common/manualConstancy/generate"),  // ← replace with the actual endpoint
                () -> {
                    var result = FirstAppearanceRace.waitForFirst(
                            page,
                            guardarButton::click,
                            resultadoModalMensajeError,
                            resultadoModalMensajeExito,
                            null,
                            timeoutMs,
                            quietMs,
                            150
                    );

                    if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
                        ElementAsserts.assertUIMessage(
                                "Proceso falló. UI error: " + resultadoModalMensajeError.innerText()
                                        + result.errorMessage()
                                        + " (después de " + result.elapsedMs() + " ms)"
                        );
                    }

                    if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
                        ElementAsserts.assertUIMessage(
                                "Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de "
                                        + timeoutMs + " ms (después de " + result.elapsedMs() + " ms)."
                        );
                    }
                }
        );

        // ✅ 4. Log the backend response for debugging
        log.info("[API URL] {}", apiResponse.url());
        log.info("[API STATUS] {}", apiResponse.status());
        log.info("[API BODY] {}", apiResponse.text());

        // ✅ 5. Now assert the UI result
        resultadoModalMensajeExito.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)
        );

        ElementAsserts.assertTextMatches(
                numeroConstanciaManualLabel,
                Pattern.compile("Se registró la Constancia Manual\\s+\\d+\\s+con éxito"),
                "Error al generar el numero de la constancia manual"
        );

        String messageText = numeroConstanciaManualLabel.innerText().trim();

        ElementAsserts.assertVisible(
                descargarConstanciaButton,
                "El boton descargar constancia no es visible"
        );

        log.info("[CONSTANCIA_MANUAL][MODAL_RESULTADO] Constancia generada con exito: {}", messageText);
    }

    public void processAndAssertSuccessBotonGuardarConstanciaManualVidaLey(long timeoutMs, long quietMs) {


        // ✅ Track all pending requests
        Set<String> pendingRequests = ConcurrentHashMap.newKeySet();

        page.onRequest(request -> {
            pendingRequests.add(request.url());
            log.info("[REQUEST STARTED] {} {}", request.method(), request.url());
        });

        page.onResponse(response -> {
            pendingRequests.remove(response.url());
            log.info("[RESPONSE RECEIVED] {} → {}",
                    response.url(), response.status());
        });

        page.onRequestFailed(req -> {
            pendingRequests.remove(req.url());
            log.error("[REQUEST FAILED] {} → {}", req.url(), req.failure());
        });

        page.onRequestFinished(req -> {
            pendingRequests.remove(req.url());
            log.info("[REQUEST FINISHED] {}", req.url());
        });

        guardarButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        // ✅ FirstAppearanceRace with click
        var result = FirstAppearanceRace.waitForFirst(
                page,
                guardarButton::click,
                resultadoModalMensajeError,
                resultadoModalMensajeExito,
                null,
                timeoutMs,
                quietMs,
                150
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            // ✅ Log pending requests on error
            log.error("⏳ Pending requests at ERROR ({}):", pendingRequests.size());
            pendingRequests.forEach(url ->
                    log.error("  ⏳ STILL PENDING: {}", url)
            );
            ElementAsserts.assertUIMessage(
                    "Proceso falló. UI error: " + resultadoModalMensajeError.innerText()
                            + result.errorMessage()
                            + " (después de " + result.elapsedMs() + " ms)"
            );
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            // ✅ Log pending requests on timeout
            log.error("⏳ Timeout after {} ms. Pending requests ({}):", timeoutMs, pendingRequests.size());
            pendingRequests.forEach(url ->
                    log.error("  ⏳ STILL PENDING: {}", url)
            );
            ElementAsserts.assertUIMessage(
                    "Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de "
                            + timeoutMs + " ms (después de " + result.elapsedMs() + " ms)."
            );
        }

        // Assert UI result
        resultadoModalMensajeExito.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)
        );

        ElementAsserts.assertTextMatches(
                numeroConstanciaManualLabel,
                Pattern.compile("Se registró la Constancia Manual\\s+\\d+\\s+con éxito"),
                "Error al generar el numero de la constancia manual"
        );

        String messageText = numeroConstanciaManualLabel.innerText().trim();

        ElementAsserts.assertVisible(
                descargarConstanciaButton,
                "El boton descargar constancia no es visible"
        );

        log.info("[CONSTANCIA_MANUAL][MODAL_RESULTADO] Constancia generada con exito: {}", messageText);
    }

    public void processAndAssertSuccessBotonGuardarAntiguo(long timeoutMs, long quietMs) {
        guardarButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                guardarButton::click,                // Realizar click en el botón guardar
                resultadoModalMensajeError,          // Elemento esperado: mensaje de error
                resultadoModalMensajeExito,          // Elemento esperado: mensaje de éxito
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
        resultadoModalMensajeExito.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        ElementAsserts.assertTextMatches(numeroConstanciaManualLabel, Pattern.compile("Se registró la Constancia Manual\\s+\\d+\\s+con éxito"),"Error al generar el numero de la constancia manual");
        String messageText = numeroConstanciaManualLabel.innerText().trim();
        ElementAsserts.assertVisible(descargarConstanciaButton,"El boton descargar constancia no es visible");
        log.info("[CONSTANCIA_MANUAL][MODAL_RESULTADO] Constancia generada con exito: {}", messageText);
    }
}
