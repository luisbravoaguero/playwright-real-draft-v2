package com.mapfre.playwright.pageobjects.poliza.vidaley;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;

public class DuracionDeclaracionSeguroPage extends BasePage {

    // ===== Validación de página =====
    private final Locator seccionDuracionDeclaracion;

    // ===== Campos =====
    private final Locator cboFrecuenciaDeclaracion;
    private final Locator cboDuracionCobertura;
    private final Locator txtCentroRiesgo;
    private final Locator btnCalendarioFechaInicial;
    private final Locator btnGuardarYContinuarModal;

    // Botón Siguiente (en la página)
    private final Locator btnSiguiente;


    public DuracionDeclaracionSeguroPage(Page page) {
        super(page);

        this.seccionDuracionDeclaracion =
                page.getByText("Duración y declaración del seguro");

        this.cboFrecuenciaDeclaracion =
                page.getByLabel("Frecuencia de declaración");

        this.cboDuracionCobertura =
                page.getByLabel("Duración de la cobertura");

        this.txtCentroRiesgo =
                page.getByLabel("Centro de riesgo");

        this.btnCalendarioFechaInicial =
                page.locator("mat-datepicker-toggle button").first();

        this.btnSiguiente = page.getByText(
                "Siguiente",
                new Page.GetByTextOptions().setExact(true)
        );
        this.btnGuardarYContinuarModal = page.getByText(
                "Guardar y continuar",
                new Page.GetByTextOptions().setExact(true)
        );

    }
    //======== MÉTODO QUE EL STEP ESPERA =============
    public void completarDuracionYDeclaracionSeguro(
            String frecuencia,
            String duracionCobertura,
            String centroRiesgo) {

        assertEnPaginaDuracionDeclaracionSeguro();
        seleccionarFrecuenciaDeclaracion(frecuencia);
        seleccionarFechaInicial(14);
        seleccionarDuracionCobertura(duracionCobertura);
        if (centroRiesgo != null && !centroRiesgo.isBlank()) {
            ingresarCentroRiesgo(centroRiesgo);
        }

        // Modal "Guardar y continuar" duración y declaración
        clickSiguienteYConfirmarGuardado();
    }
    /** Valida que estamos en el Paso 2 - Duración y declaración del seguro */
    public void assertEnPaginaDuracionDeclaracionSeguro() {

        log.info("[VIDA_LEY][COTIZACION] Validando Paso 2 - Duración y declaración del seguro");

        try {
            seccionDuracionDeclaracion.waitFor();
            UiSync.waitForAppIdle(page);

        } catch (com.microsoft.playwright.TimeoutError e) {

            log.error(
                    "[VIDA_LEY][COTIZACION][ERROR] No se mostró el Paso 2 - Duración y declaración del seguro"
            );

            throw new AssertionError(
                    "No se pudo validar el Paso 2 (Duración y declaración del seguro). " +
                            "El flujo no llegó al estado esperado."
            );
        }

        log.info("[VIDA_LEY][COTIZACION] Paso 2 validado correctamente");
    }

    public void seleccionarFrecuenciaDeclaracion(String frecuencia) {
        cboFrecuenciaDeclaracion.waitFor();
        cboFrecuenciaDeclaracion.selectOption(
                new SelectOption().setLabel(frecuencia)
        );
    }

    public void seleccionarDuracionCobertura(String duracionCobertura) {
        // Si viene vacío, no seleccionar nada
        if (duracionCobertura == null || duracionCobertura.isBlank()) {
            return;
        }
        cboDuracionCobertura.waitFor();
        cboDuracionCobertura.selectOption(
                new SelectOption().setLabel(duracionCobertura.trim())
        );
    }

    public void ingresarCentroRiesgo(String centroRiesgo) {
        txtCentroRiesgo.waitFor();
        txtCentroRiesgo.fill(centroRiesgo);
    }

    public void seleccionarFechaInicial(int dia) {
        // Abrir el calendario
        btnCalendarioFechaInicial.waitFor();
        btnCalendarioFechaInicial.click();

        // se Selecciona el día
        Locator diaCalendario = page.getByRole(
                AriaRole.GRIDCELL,
                new Page.GetByRoleOptions().setName(String.valueOf(dia))
        );
        diaCalendario.waitFor();
        diaCalendario.click();
    }
    public void clickSiguienteYConfirmarGuardado() {

        // Clic en btn Siguiente
        btnSiguiente.waitFor();
        btnSiguiente.click();

        // Esperar que aparezca el modal y confirmar
        btnGuardarYContinuarModal.waitFor();
        btnGuardarYContinuarModal.click();
    }
}