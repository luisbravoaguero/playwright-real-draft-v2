package com.mapfre.playwright.pageobjects.poliza.accidentes;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.regex.Pattern;

public class CotizacionPolizaAccidentesPage extends BasePage {

    // ===== Validadores de página =====
    private final Locator tituloPaginaCotizacion;

    // ===== Datos del contratante =====
    private final Locator comboTipoDocumento;
    private final Locator inputNumeroDocumento;
    private final Locator inputNombres;
    private final Locator inputApellidoPaterno;
    private final Locator inputApellidoMaterno;

    // ===== Datos del riesgo =====
    private final Locator inputActividad;
    private final Locator inputNumeroAsegurados;
    private final Locator comboExposicionRiesgo;
    private final Locator comboMoneda;
    private final Locator inputFechaInicioVigencia;
    private final Locator botonCalendarioFechaInicio;

    // ===== Coberturas principales =====
    private final Locator inputMuerteAccidental;
    private final Locator inputInvalidezPermanente;
    private final Locator inputIncapacidadTemporalDiaria;
    private final Locator inputGastosCuracion;
    private final Locator inputGastosSepelio;

    // ===== Acciones de cotización =====
    private final Locator botonAgregarRiesgo;

    // ===== Riesgos agregados =====
    private final Locator seccionRiesgosAgregados;
    private final Locator filaRiesgoAgregado;
    private final Locator celdaActividadRiesgoAgregado;
    private final Locator celdaNumeroAseguradosRiesgoAgregado;
    private final Locator celdaPrimaTotalRiesgoAgregado;

    // ===== Guardar cotización =====
    private final Locator botonGuardarCotizacion;
    private final Locator botonGuardarModal;
    private final Locator resultadoMensajeError;
    private final Locator botonEmitirPoliza;
    public CotizacionPolizaAccidentesPage(Page page) {
        super(page);

        // ===== Validadores de página =====
        this.tituloPaginaCotizacion = page.getByText(
                Pattern.compile("Cotización póliza accidentes", Pattern.CASE_INSENSITIVE)
        );

        // ===== Datos del contratante =====
        this.comboTipoDocumento = page.getByLabel("Tipo de documento").first();
        this.inputNumeroDocumento = page.locator("input[name='documentNumber']");
        this.inputNombres = page.locator("input[name='Nombre']");
        this.inputApellidoPaterno = page.locator("input[name='ApellidoPaterno']");
        this.inputApellidoMaterno = page.locator("input[name='ApellidoMaterno']");

        // ===== Datos del riesgo =====
        this.inputActividad = page.locator("input[placeholder*='Busque o seleccione una actividad']").first();
        this.inputNumeroAsegurados = page.getByLabel("Número de asegurados").first();
        this.comboExposicionRiesgo = page.getByLabel("Exposición al riesgo").first();
        this.comboMoneda = page.getByLabel("Moneda").first();
        this.inputFechaInicioVigencia = page.getByLabel("Fecha de inicio de vigencia").first();

        this.botonCalendarioFechaInicio = page.locator("mat-form-field")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("Fecha de inicio de vigencia", Pattern.CASE_INSENSITIVE)))
                .locator("button[aria-label='Open calendar']")
                .first();

        // ===== Coberturas principales =====
        this.inputMuerteAccidental = page.getByLabel("Muerte accidental").first();
        this.inputInvalidezPermanente = page.getByLabel("Invalidez permanente").first();

        this.inputIncapacidadTemporalDiaria = page.locator("oim-numeric-textbox")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("INCAPACIDAD TEMPORAL", Pattern.CASE_INSENSITIVE)))
                .locator("input:not([style*='display: none'])")
                .first();


        this.inputGastosCuracion = page.locator("oim-numeric-textbox")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("GASTOS DE CURACION|GASTOS DE CURACIÓN", Pattern.CASE_INSENSITIVE)))
                .locator("input:visible")
                .first();

        this.inputGastosSepelio = page.locator("oim-numeric-textbox")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("GASTOS DE SEPELIO", Pattern.CASE_INSENSITIVE)))
                .locator("input:visible")
                .first();


        // ===== Acciones de cotización =====
        this.botonAgregarRiesgo = page.locator("a.g-button")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("Agregar riesgo", Pattern.CASE_INSENSITIVE)));

// ===== Riesgos agregados =====
        this.seccionRiesgosAgregados = page.getByText(
                Pattern.compile("Riesgos agregados", Pattern.CASE_INSENSITIVE)
        );
        this.filaRiesgoAgregado = page.locator(
                "xpath=//*[contains(normalize-space(), 'Riesgos agregados')]/following::*[contains(normalize-space(), 'AGRICULTURA')][1]" );

        this.celdaActividadRiesgoAgregado = page.locator(
                "xpath=//*[contains(normalize-space(), 'Riesgos agregados')]/following::*[normalize-space()='AGRICULTURA'][1]");

        this.celdaNumeroAseguradosRiesgoAgregado = page.locator(
                "xpath=//*[contains(normalize-space(), 'Riesgos agregados')]/following::*[normalize-space()='AGRICULTURA']/following::*[normalize-space()='1'][1]");

        this.celdaPrimaTotalRiesgoAgregado = page.locator(
                "xpath=//*[contains(normalize-space(), 'Riesgos agregados')]/following::*[contains(normalize-space(), '415') or contains(normalize-space(), '341')][1]");

        // ===== Guardar cotización =====
        this.botonGuardarCotizacion = page.locator("a.g-button")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("Guardar cotización", Pattern.CASE_INSENSITIVE)));

        this.botonGuardarModal = page.locator("button.g-button")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("^\\s*Guardar\\s*$", Pattern.CASE_INSENSITIVE)));
        this.resultadoMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.botonEmitirPoliza = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Emitir Póliza", Pattern.CASE_INSENSITIVE)));
    }

    // ============================================================
    // Métodos públicos principales
    // ============================================================

    public void assertLoaded() {
        ElementAsserts.assertVisible(
                tituloPaginaCotizacion,
                "[ACCIDENTES][COTIZACION] TITULO Cotización póliza accidentes DEBE SER VISIBLE"
        );

        log.info("[ACCIDENTES][COTIZACION] Página visible: Cotización póliza accidentes");
    }

    public void completarDatosObligatoriosContratante(
            String tipoDocumento,
            String numeroDocumento,
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno
    ) {
        try {

            seleccionarTipoDocumento(tipoDocumento);
            inputNumeroDocumento.fill(numeroDocumento);
            inputNumeroDocumento.press("Tab");

            if (datosPrincipalesAutocompletados()) {
                log.info("[ACCIDENTES][COTIZACION] Datos principales autocompletados correctamente");
            } else {
                completarDatosPrincipalesManual(nombres, apellidoPaterno, apellidoMaterno);
                log.info("[ACCIDENTES][COTIZACION] Datos principales completados manualmente");
            }

            log.info("[ACCIDENTES][COTIZACION] Datos obligatorios del contratante completados correctamente");

        } catch (AssertExceptions e) {
            throw e;

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][COTIZACION][ERROR] No se pudieron completar los datos obligatorios del contratante. " +
                            "Validar tipo documento, número documento, autocompletado o disponibilidad de campos manuales.",
                    e
            );
        }
    }

    public void completarDatosRiesgoHardcodeado() {
        try {
            seleccionarActividadAutocomplete("AGRICULTURA");
            inputNumeroAsegurados.fill("1");
            seleccionarOpcionSelect(comboExposicionRiesgo, "24 HORAS");
            seleccionarOpcionSelect(comboMoneda, "SOLES");
            seleccionarFechaInicioVigenciaSiEsNecesario("12/05/2026", "12");
            log.info("[ACCIDENTES][COTIZACION] Datos del riesgo completados correctamente");

        } catch (AssertExceptions e) {
            throw e;

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][COTIZACION][ERROR] No se pudieron completar los datos del riesgo.",
                    e
            );
        }
    }

    public void completarCoberturasPrincipalesHardcodeado() {
        try {
            inputMuerteAccidental.fill("10000");
            inputInvalidezPermanente.fill("100");

            inputIncapacidadTemporalDiaria.fill("100");
            inputGastosCuracion.fill("100");
            inputGastosSepelio.fill("100");

            log.info("[ACCIDENTES][COTIZACION] Coberturas principales completadas correctamente");

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][COTIZACION][ERROR] No se pudieron completar las coberturas principales.",
                    e
            );
        }
    }
    // ============================================================
    // Métodos privados - Datos del contratante
    // ============================================================
    private void seleccionarTipoDocumento(String tipoDocumento) {

        final int timeoutMs = 10_000;
        final int intervaloMs = 100;

        long startTime = System.currentTimeMillis();
        String valueEncontrado = null;

        while (System.currentTimeMillis() - startTime < timeoutMs) {

            Locator opciones = comboTipoDocumento.locator("option");
            int totalOpciones = opciones.count();

            for (int i = 0; i < totalOpciones; i++) {
                Locator opcion = opciones.nth(i);
                String textoOpcion = opcion.textContent().trim();

                if (textoOpcion.equalsIgnoreCase(tipoDocumento.trim())) {
                    valueEncontrado = opcion.getAttribute("value");
                    break;
                }
            }

            if (valueEncontrado != null && !valueEncontrado.isBlank()) {
                comboTipoDocumento.selectOption(valueEncontrado);
                return;
            }

            page.waitForTimeout(intervaloMs);
        }

        throw new AssertExceptions(
                "[ACCIDENTES][COTIZACION][ERROR] No se encontró la opción de tipo documento: " + tipoDocumento +
                        ". Opciones disponibles: " + obtenerOpcionesTipoDocumento()
        );
    }

    private boolean datosPrincipalesAutocompletados() {

        final int timeoutMs = 10_000;
        final int intervaloMs = 500;

        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeoutMs) {

            String nombres = inputNombres.inputValue().trim();
            String apellidoPaterno = inputApellidoPaterno.inputValue().trim();
            String apellidoMaterno = inputApellidoMaterno.inputValue().trim();

            if (!nombres.isEmpty() && !apellidoPaterno.isEmpty() && !apellidoMaterno.isEmpty()) {
                return true;
            }

            page.waitForTimeout(intervaloMs);
        }

        return false;
    }

    private void completarDatosPrincipalesManual(
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno
    ) {
        inputNombres.fill(nombres);
        inputApellidoPaterno.fill(apellidoPaterno);
        inputApellidoMaterno.fill(apellidoMaterno);
    }

    // ============================================================
    // Métodos privados - Datos del riesgo
    // ============================================================

    private void seleccionarActividadAutocomplete(String actividad) {
        try {
            inputActividad.click();
            inputActividad.fill("");
            inputActividad.pressSequentially(
                    actividad,
                    new Locator.PressSequentiallyOptions().setDelay(120)
            );

            page.waitForTimeout(1000);

            Locator opcionActividad = page.locator("mat-option")
                    .filter(new Locator.FilterOptions()
                            .setHasText(Pattern.compile(Pattern.quote(actividad), Pattern.CASE_INSENSITIVE)))
                    .first();

            if (opcionActividad.count() > 0 && opcionActividad.isVisible()) {
                opcionActividad.click();
            } else {
                /*
                 * Si no se encuentra la opción por texto, intentamos seleccionar
                 * la primera opción disponible con teclado.
                 */
                inputActividad.press("ArrowDown");
                page.waitForTimeout(300);
                inputActividad.press("Enter");
            }

            inputActividad.press("Tab");

            page.waitForTimeout(500);

            String valorActividad = inputActividad.inputValue().trim();

            if (valorActividad.isEmpty()) {
                throw new AssertExceptions(
                        "[ACCIDENTES][COTIZACION][ERROR] La actividad no quedó seleccionada. " +
                                "Es posible que el autocomplete no haya mostrado opciones o que el valor escrito no sea válido: " + actividad
                );
            }

            log.info("[ACCIDENTES][COTIZACION] Actividad seleccionada correctamente: {}", valorActividad);

        } catch (AssertExceptions e) {
            throw e;

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][COTIZACION][ERROR] No se pudo seleccionar la actividad: " + actividad,
                    e
            );
        }
    }

    private void seleccionarOpcionSelect(Locator combo, String opcionEsperada) {

        final int timeoutMs = 10_000;
        final int intervaloMs = 500;

        long startTime = System.currentTimeMillis();
        String valueEncontrado = null;

        while (System.currentTimeMillis() - startTime < timeoutMs) {

            Locator opciones = combo.locator("option");
            int totalOpciones = opciones.count();

            for (int i = 0; i < totalOpciones; i++) {
                Locator opcion = opciones.nth(i);
                String textoOpcion = opcion.textContent().trim();

                if (textoOpcion.equalsIgnoreCase(opcionEsperada.trim())) {
                    valueEncontrado = opcion.getAttribute("value");
                    break;
                }
            }

            if (valueEncontrado != null && !valueEncontrado.isBlank()) {
                combo.selectOption(valueEncontrado);
                return;
            }

            page.waitForTimeout(intervaloMs);
        }

        throw new AssertExceptions(
                "[ACCIDENTES][COTIZACION][ERROR] No se encontró la opción en combo: " + opcionEsperada
        );
    }

    private void seleccionarFechaInicioVigenciaSiEsNecesario(String fechaEsperada, String dia) {

        String fechaActual = inputFechaInicioVigencia.inputValue().trim();

        try {

            if (fechaActual.equals(fechaEsperada)) {
                log.info("[ACCIDENTES][COTIZACION] Fecha inicio de vigencia ya correcta: {}", fechaActual);

                // ✅ CRÍTICO: cerrar cualquier overlay activo
                cerrarOverlayCalendario();

                return;
            }

            botonCalendarioFechaInicio.click();

            page.evaluate(
                    "(dia) => {" +
                            "const panes = Array.from(document.querySelectorAll('.cdk-overlay-pane'))" +
                            ".filter(p => p.offsetParent !== null);" +
                            "const pane = panes[panes.length - 1];" +
                            "if (!pane) throw new Error('No se encontró overlay visible del calendario');" +
                            "const botones = Array.from(pane.querySelectorAll('button.mat-calendar-body-cell'));" +
                            "const botonDia = botones.find(b => b.innerText.trim() === dia);" +
                            "if (!botonDia) throw new Error('No se encontró el día en calendario: ' + dia);" +
                            "botonDia.click();" +
                            "}",
                    dia
            );

            // ✅ CRÍTICO: cerrar calendario SIEMPRE
            inputFechaInicioVigencia.press("Tab");
            cerrarOverlayCalendario();

            log.info("[ACCIDENTES][COTIZACION] Fecha inicio de vigencia seleccionada desde calendario");

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][COTIZACION][ERROR] No se pudo seleccionar la fecha inicio de vigencia desde calendario.",
                    e
            );
        }
    }
    // ============================================================
    // Métodos privados - Utilitarios
    // ============================================================
    private String obtenerOpcionesTipoDocumento() {
        try {
            Locator opciones = comboTipoDocumento.locator("option");
            int totalOpciones = opciones.count();

            StringBuilder opcionesDisponibles = new StringBuilder();

            for (int i = 0; i < totalOpciones; i++) {
                String texto = opciones.nth(i).textContent().trim();

                if (!texto.isEmpty()) {
                    if (!opcionesDisponibles.isEmpty()) {
                        opcionesDisponibles.append(", ");
                    }
                    opcionesDisponibles.append(texto);
                }
            }

            return opcionesDisponibles.toString();

        } catch (Exception e) {
            return "No se pudieron obtener las opciones del combo Tipo de documento.";
        }
    }

    public void clickAgregarRiesgo() {
        try {
            cerrarOverlayCalendario(); // ✅ FIX PRINCIPAL

            ElementAsserts.assertVisible(
                    botonAgregarRiesgo,
                    "[ACCIDENTES][COTIZACION] BOTON Agregar riesgo DEBE SER VISIBLE"
            );

            botonAgregarRiesgo.scrollIntoViewIfNeeded(); // ✅ viewport fix

            clickAndSync(botonAgregarRiesgo);

            validarRiesgoAgregadoCorrectamente();

            log.info("[ACCIDENTES][COTIZACION] Riesgo agregado correctamente");

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][COTIZACION][ERROR] No se pudo agregar el riesgo a la cotización.",
                    e
            );
        }
    }

    private void validarRiesgoAgregadoCorrectamente() {

        final int timeoutMs = 10_000;
        final int intervaloMs = 500;

        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeoutMs) {

            boolean actividadVisible = celdaActividadRiesgoAgregado.isVisible();

            if (actividadVisible) {
                log.info("[ACCIDENTES][COTIZACION] Riesgo agregado visible con actividad AGRICULTURA");
                return;
            }

            page.waitForTimeout(intervaloMs);
        }

        throw new AssertExceptions(
                "[ACCIDENTES][COTIZACION][ERROR] El riesgo no fue agregado correctamente. " +
                        "La tabla Riesgos agregados no muestra una fila con actividad AGRICULTURA."
        );
    }
    public void guardarCotizacion() {
        try {
            ElementAsserts.assertVisible(
                    botonGuardarCotizacion,
                    "[ACCIDENTES][COTIZACION] BOTON Guardar cotización DEBE SER VISIBLE ANTES DEL CLICK"
            );

            log.info("[ACCIDENTES][COTIZACION] Botón visible: Guardar cotización");

            clickAndSync(botonGuardarCotizacion);

            ElementAsserts.assertVisible(
                    botonGuardarModal,
                    "[ACCIDENTES][COTIZACION] BOTON Guardar DEL MODAL DEBE SER VISIBLE"
            );

            log.info("[ACCIDENTES][COTIZACION] Botón visible en modal: Guardar");
            clickAndSync(botonGuardarModal);
            log.info("[ACCIDENTES][COTIZACION] Cotización guardada correctamente");

        } catch (AssertExceptions e) {
            throw e;

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][COTIZACION][ERROR] No se pudo guardar la cotización. " +
                            "Validar botón Guardar cotización, modal de confirmación o botón Guardar del modal.",
                    e
            );
        }
    }

    public void processAndAssertSuccessClickGuardarCotizacion(long timeoutMs, long quietMs) {
        sync();
        waitRandomBetween(2500);
        log.info("[COTIZACION][ACCIDENTES] El proceso de cotizacion ha iniciado...");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoMensajeError,
                botonEmitirPoliza,
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
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[COTIZACION][ACCIDENTES] Pagina de cotización procesada correctamente, inicia la emision de la poliza ...");

    }

    private void cerrarOverlayCalendario() {

        try {
            Locator overlay = page.locator(".cdk-overlay-backdrop");

            if (overlay.count() > 0 && overlay.first().isVisible()) {
                page.keyboard().press("Escape");
                page.waitForTimeout(300);
                log.info("[ACCIDENTES][COTIZACION] Overlay calendario cerrado correctamente");
            }

        } catch (Exception e) {
            log.warn("[ACCIDENTES][COTIZACION] No se pudo validar/cerrar overlay (continuando)");
        }
    }

}