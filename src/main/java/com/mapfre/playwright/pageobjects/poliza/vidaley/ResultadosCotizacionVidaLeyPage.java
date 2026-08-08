package com.mapfre.playwright.pageobjects.poliza.vidaley;

import java.util.regex.Pattern;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByTextOptions;

public class ResultadosCotizacionVidaLeyPage extends BasePage {

    // Patrón para validar el formato del título de cotización
    private static final Pattern TITULO_COTIZACION_PATTERN =
            Pattern.compile("Cotización\\s+\\d+", Pattern.CANON_EQ);

    // ================= Locators =================
    // Botón para aceptar la cotización y avanzar al flujo de emisión
    private final Locator btnAceptarCotizacion;
    // Botón para confirmar la emisión de la póliza
    private final Locator btnEmitirPoliza;
    // Título que muestra el texto "Cotización <número>"
    private final Locator tituloNumeroCotizacion;
    // Botón para solicitar reajuste de la tasa
    private final Locator btnSolicitarReajuste;
    private final Locator txtTasaFinal;
    private final Locator btnAceptarSolicitud;
    private final Locator btnOkModalEvaluacionTasa;

    // ================= Constructor =================
    public ResultadosCotizacionVidaLeyPage(Page page) {
        super(page);

        this.btnAceptarCotizacion =page.getByText("Aceptar cotización",new GetByTextOptions().setExact(true));
        this.btnEmitirPoliza =page.getByText("Emitir póliza",new GetByTextOptions().setExact(true));
        this.tituloNumeroCotizacion =page.locator("h1:has-text('Cotización')");
        this.btnSolicitarReajuste =page.getByText("Solicitar reajuste",new GetByTextOptions().setExact(true));
        this.txtTasaFinal = page.locator("input[name='nTasaSus']");
        this.btnAceptarSolicitud = page.getByText("Aceptar Solicitud");
        this.btnOkModalEvaluacionTasa = page.locator("button.swal2-confirm");
    }
    // ================= Acciones =================
    /**Acepta la cotización generada y permite continuar con el flujo de emisión*/
    public void aceptarCotizacion() {

        log.info("[VIDA_LEY][COTIZACION] Intentando aceptar la cotización");
        UiSync.waitForAppIdle(page);

        Locator btnAceptar =
                page.locator("a.g-button.block")
                        .filter(new Locator.FilterOptions().setHasText("Aceptar"));

        try {
            // Esperar que el botón exista y sea visible
            btnAceptar.waitFor();
        } catch (com.microsoft.playwright.TimeoutError e) {

            log.error(
                    "[VIDA_LEY][COTIZACION][ERROR] El botón 'Aceptar' no se encontró en pantalla. " +
                            "La cotización no llegó al estado de resultados."
            );

            throw new AssertionError(
                    "No se pudo aceptar la cotización. " +
                            "El botón 'Aceptar' no estuvo disponible. " +
                            "Revise si la cotización fue generada correctamente."
            );
        }

        btnAceptar.scrollIntoViewIfNeeded();

        log.info("[VIDA_LEY][COTIZACION] Botón 'Aceptar' visible, realizando click");
        btnAceptar.click();
        UiSync.waitForAppIdle(page);
        log.info("[VIDA_LEY][COTIZACION] Cotización aceptada correctamente");
    }

    /** número de cotización generado y se registra en el log*/
    public void extraerYLoggearNumeroCotizacion() {

        log.info("[VIDA_LEY][COTIZACION] Esperando resumen con número de cotización");

        // Espera explícitamente el H1 que contenga un número (estado final)
        Locator tituloConNumero =
                page.locator("h1")
                        .filter(new Locator.FilterOptions()
                                .setHasText(Pattern.compile("\\d+")));

        tituloConNumero.waitFor();

        String textoCompleto = tituloConNumero.textContent().trim();
        String numeroCotizacion = textoCompleto.replaceAll("\\D+", "");
        log.info(
                "[VIDA_LEY][RESULTADO_FINAL] Cotización generada exitosamente: {}",
                numeroCotizacion
        );
    }

    /**Confirma la emisión de la póliza haciendo click en el botón "Emitir póliza"*/
    public void emitirPoliza() {

        log.info("[VIDA_LEY][EMISION] Iniciando emisión de la póliza");

        // Espera que la aplicación esté estable antes de emitir
        UiSync.waitForAppIdle(page);

        // Verifica visibilidad del botón de emisión
        btnEmitirPoliza.waitFor();
        btnEmitirPoliza.scrollIntoViewIfNeeded();

        // Click final para emitir la póliza
        btnEmitirPoliza.click();

        // Espera el render de la pantalla de resultado
        UiSync.waitForAppIdle(page);

        log.info("[VIDA_LEY][EMISION] Click en 'Emitir póliza' realizado correctamente");
    }

    /**** Solicita el reajuste de la tasa y confirma el modal de evaluación. *****/
    public void solicitarReajusteDeTasa() {

        log.info("[VIDA_LEY][REAJUSTE] Intentando solicitar reajuste de tasa");
        UiSync.waitForAppIdle(page);

        try {
            btnSolicitarReajuste.waitFor();
        } catch (com.microsoft.playwright.TimeoutError e) {

            log.error(
                    "[VIDA_LEY][REAJUSTE][ERROR] El botón 'Solicitar reajuste' no se encontró en pantalla."
            );
            throw new AssertionError(
                    "No se pudo solicitar el reajuste de tasa. " +
                            "El botón 'Solicitar reajuste' no estuvo disponible."
            );
        }
        btnSolicitarReajuste.scrollIntoViewIfNeeded();
        btnSolicitarReajuste.click();
        log.info("[VIDA_LEY][REAJUSTE] Solicitud de reajuste enviada");

        // =======MODAL EVALUACIÓN DE TASA ===================
        try {
            btnOkModalEvaluacionTasa.waitFor();
            btnOkModalEvaluacionTasa.click();
            page.waitForCondition(() -> !btnOkModalEvaluacionTasa.isVisible());
                log.info("[VIDA_LEY][REAJUSTE] Modal de evaluación confirmado (OK)");

        } catch (Exception e) {
            log.warn("[VIDA_LEY][REAJUSTE] No apareció el modal de evaluación de tasa");
        }
        UiSync.waitForAppIdle(page);
    }

    /***Ingresa la tasa final en la evaluación de tasa.*/

    public void ingresarTasaFinalYAceptarSolicitud(String tasaFinal) {

        if (tasaFinal == null || tasaFinal.isBlank()) {
            throw new IllegalArgumentException("La tasa final no puede ser vacía");
        }

        log.info("[VIDA_LEY][EVALUACION_TASA] Ingresando tasa final: {}", tasaFinal);


        UiSync.waitForAppIdle(page);

        //Campo tasa final
        txtTasaFinal.waitFor();
        txtTasaFinal.scrollIntoViewIfNeeded();

        // Escribir como si fuera usuario real
        txtTasaFinal.click();
        txtTasaFinal.press("Control+A");
        txtTasaFinal.press("Backspace");
        txtTasaFinal.type(tasaFinal);
        txtTasaFinal.press("Tab");
        UiSync.waitForAppIdle(page);
        log.info("[VIDA_LEY][EVALUACION_TASA] Tasa final ingresada correctamente");

        // Aceptar solicitud
        btnAceptarSolicitud.waitFor();
        btnAceptarSolicitud.scrollIntoViewIfNeeded();
        btnAceptarSolicitud.click(new Locator.ClickOptions().setForce(true));

        log.info("[VIDA_LEY][EVALUACION_TASA] Solicitud aceptada, esperando confirmación");

        // modal: cotizacion aceptada
        try {
            btnOkModalEvaluacionTasa.waitFor();
            btnOkModalEvaluacionTasa.click();

            // esperar que el modal desaparezca
            page.waitForCondition(() -> !btnOkModalEvaluacionTasa.isVisible());

            log.info("[VIDA_LEY][EVALUACION_TASA] Modal 'Cotización aceptada' confirmado");
        } catch (Exception e) {
            log.warn("[VIDA_LEY][EVALUACION_TASA] No apareció el modal de confirmación");
        }

        // Esperar que el page que listo para el siguiente paso
        UiSync.waitForAppIdle(page);
        log.info("[VIDA_LEY][EVALUACION_TASA] Flujo de evaluación de tasa finalizado");
    }


}