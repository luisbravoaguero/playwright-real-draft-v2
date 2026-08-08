package com.mapfre.playwright.pageobjects.poliza.accidentes;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static com.mapfre.utils.UiSync.waitForAppIdle;

public class EmisionPolizaAccidentesPage extends BasePage {

    // ===== Validadores de página =====
    private final Locator tituloPaginaEmision;

    // ===== Datos del contratante =====
    private final Locator comboTipoDocumento;
    private final Locator inputNumeroDocumento;

    // ===== Datos principales =====
    private final Locator inputNombres;
    private final Locator inputApellidoPaterno;
    private final Locator inputApellidoMaterno;

    // Fecha nacimiento (Día/Mes/Año) - IDs definitivos
    private final Locator comboDiaNacimiento;   // mat-input-169
    private final Locator comboMesNacimiento;   // mat-input-170
    private final Locator comboAnioNacimiento;  // mat-input-171

    // ===== Características personales =====
    private final Locator radioMasculino;
    private final Locator radioFemenino;

    // ===== Datos laborales =====
    private final Locator comboProfesion;

    // ===== Datos de contacto =====
    private final Locator inputCorreoElectronico;

    // ===== Datos de dirección =====
    private final Locator comboDepartamento;
    private final Locator comboProvincia;
    private final Locator comboDistrito;
    private final Locator comboVia;
    private final Locator inputNombreVia;

    // ===== Acción =====
    private final Locator botonSiguiente;

    // ===== Paso 2: Cargar asegurados =====
    private final Locator seccionCargarAsegurados;
    private final Locator botonImportarPlanilla;
    private final Locator botonSubirPlanilla;
    private final Locator botonEmitirPoliza;
    private final Locator nombreArchivoCargado;
    private final Locator inputArchivoPlanilla;
    private final Locator botonConfirmarEmitir;


    public EmisionPolizaAccidentesPage(Page page) {
        super(page);

        // ===== Validadores de página =====
        this.tituloPaginaEmision = page.getByText(
                Pattern.compile("Emisión póliza accidentes", Pattern.CASE_INSENSITIVE)
        );

        // ===== Datos del contratante =====
        this.comboTipoDocumento = page.getByLabel("Tipo de documento").first();
        this.inputNumeroDocumento = page.getByLabel("Número de documento").first();

        // ===== Datos principales =====
        this.inputNombres = page.getByLabel("Nombres").first();
        this.inputApellidoPaterno = page.getByLabel("Apellido Paterno").first();
        this.inputApellidoMaterno = page.getByLabel("Apellido Materno").first();

        // ✅ Fecha nacimiento (IDs confirmados por tu HTML)
        this.comboDiaNacimiento = page.locator("select:has(option:has-text('Día'))").first();
        this.comboMesNacimiento = page.locator("select:has(option:has-text('Mes'))").first();
        this.comboAnioNacimiento = page.locator("select:has(option:has-text('Año'))").first();

        // ===== Características personales =====
        this.radioMasculino = page.getByLabel("Masculino").first();
        this.radioFemenino = page.getByLabel("Femenino").first();

        // ===== Datos laborales =====
        this.comboProfesion = page.getByLabel("Profesión").first();

        // ===== Datos de contacto =====
        this.inputCorreoElectronico = page.getByLabel("Correo electrónico").first();

        // ===== Datos de dirección =====
        this.comboDepartamento = page.getByLabel("Departamento").first();
        this.comboProvincia = page.getByLabel("Provincia").first();
        this.comboDistrito = page.getByLabel("Distrito").first();

        // Si este combo de “Vía” también es select nativo, perfecto:
        this.comboVia = page.locator(
                "xpath=//*[contains(normalize-space(), 'Vía')]/following::select[1]"
        );

        this.inputNombreVia = page.getByLabel("Nombre Vía").first();

        // ===== Acción =====
        this.botonSiguiente = page.locator("a.g-button.block")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("^\\s*Siguiente\\s*$", Pattern.CASE_INSENSITIVE)))
                .first();

        // Sección visible
        this.seccionCargarAsegurados = page.getByText(
                Pattern.compile("^\\s*Cargar asegurados\\s*$", Pattern.CASE_INSENSITIVE)
        ).first();


        // IMPORTAR PLANILLA
        // Botón Importar planilla

        this.botonImportarPlanilla = page.locator("label.g-button--input-file")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("Importar planilla", Pattern.CASE_INSENSITIVE)))
                .first();


        // Botón Subir planilla
                this.botonSubirPlanilla = page.locator("button, a")
                        .filter(new Locator.FilterOptions()
                                .setHasText(Pattern.compile("^\\s*Subir planilla\\s*$", Pattern.CASE_INSENSITIVE)))
                        .first();

        // Botón Emitir póliza (confirmado)
        this.botonEmitirPoliza = page.locator("a.g-button.block")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("^\\s*Emitir póliza\\s*$", Pattern.CASE_INSENSITIVE)))
                .first();

        // Validación archivo cargado
                this.nombreArchivoCargado = page.locator("text=/.*\\.xlsx/i").first();

                this.inputArchivoPlanilla = page.locator("input[type='file']");

        this.botonConfirmarEmitir = page.locator("button.g-button")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("^\\s*Emitir\\s*$", Pattern.CASE_INSENSITIVE)))
                .last();
    }
    // ============================================================
    // Métodos públicos principales
    // ============================================================
    public void assertLoaded() {
        ElementAsserts.assertVisible(
                tituloPaginaEmision,
                "[ACCIDENTES][EMISION] TITULO Emisión póliza accidentes DEBE SER VISIBLE"
        );
        log.info("[ACCIDENTES][EMISION] Página visible: Emisión póliza accidentes");
    }

    public void completarCamposObligatoriosMinimos(String tipoDocumento, String numeroDocumento, String fechaNacimiento) {
        try {
            assertLoaded();

            // 1) Tipo documento: solo si viene con valor y el combo no tiene valor válido
            if (!esVacio(tipoDocumento) && !selectTieneValorValido(comboTipoDocumento)) {
                seleccionarOpcionSelect(comboTipoDocumento, tipoDocumento);
            }

            // 2) Número documento: solo si viene con valor y está vacío o distinto
            if (!esVacio(numeroDocumento)) {
                String actual = inputNumeroDocumento.inputValue().trim();
                if (actual.isEmpty() || !actual.equals(numeroDocumento.trim())) {
                    inputNumeroDocumento.fill(numeroDocumento);
                    inputNumeroDocumento.press("Tab"); // dispara autocompletado
                }
            }

            // 3) Espera dinámica por autocompletado (no falla si no autocompleta)
            esperarAutocompletadoDatosPrincipales();

            // 4) Fallback nombres/apellidos si quedaron vacíos
            if (inputEstaVacio(inputNombres)) inputNombres.fill("AutomatizacionNombre");
            if (inputEstaVacio(inputApellidoPaterno)) inputApellidoPaterno.fill("AutomatizacionPaterno");
            if (inputEstaVacio(inputApellidoMaterno)) inputApellidoMaterno.fill("AutomatizacionMaterno");

            // 5) Fecha nacimiento: seleccionar día/mes/año solo si no está seleccionada y si viene parámetro
            if (!esVacio(fechaNacimiento) && !fechaNacimientoSeleccionada()) {
                esperarControlesFechaNacimiento();
                seleccionarFechaNacimientoDesdeString(fechaNacimiento);
            }

            // 6) Sexo*: si ninguno está marcado, marcar Masculino por defecto
            if (!radioMasculino.isChecked() && !radioFemenino.isChecked()) {
                radioMasculino.click();
            }

            // 7) Profesión*: si no hay valor válido, seleccionar una por defecto
            if (!selectTieneValorValido(comboProfesion)) {
                seleccionarOpcionSelect(comboProfesion, "ABOGADO");
            }

            // 8) Correo*: si está vacío, completar
            if (inputEstaVacio(inputCorreoElectronico)) {
                inputCorreoElectronico.fill("automatizacion.qa@correo.com");
            }

            // 9) Dirección obligatoria: completar solo si falta
            if (!selectTieneValorValido(comboDepartamento)) seleccionarOpcionSelect(comboDepartamento, "LIMA");
            if (!selectTieneValorValido(comboProvincia)) seleccionarOpcionSelect(comboProvincia, "LIMA");
            if (!selectTieneValorValido(comboDistrito)) seleccionarOpcionSelect(comboDistrito, "COMAS");
            if (!selectTieneValorValido(comboVia)) seleccionarOpcionSelect(comboVia, "AA.HH.");
            if (inputEstaVacio(inputNombreVia)) inputNombreVia.fill("PRINCIPAL");

            log.info("[ACCIDENTES][EMISION] Campos obligatorios mínimos completados/validados correctamente");

        } catch (AssertExceptions e) {
            throw e;
        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][EMISION][ERROR] No se pudieron completar los campos obligatorios mínimos de emisión.",
                    e
            );
        }
    }

    public void clickSiguiente() {
        try {
            ElementAsserts.assertVisible(
                    botonSiguiente,
                    "[ACCIDENTES][EMISION] BOTON Siguiente DEBE SER VISIBLE ANTES DEL CLICK"
            );
            waitForAppIdle();
            botonSiguiente.scrollIntoViewIfNeeded();
            clickAndSync(botonSiguiente);
            log.info("[ACCIDENTES][EMISION] Click realizado en botón Siguiente");
            Locator botonImportar = page.getByText("Importar planilla").first();
            botonImportar.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            log.info("[ACCIDENTES][EMISION] Se navegó correctamente a 'Cargar asegurados'");

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][EMISION][ERROR] No se pudo avanzar a 'Cargar asegurados'.",
                    e
            );
        }
    }
    // ============================================================
    // Privados - Fecha nacimiento (día/mes/año por separado)
    // ============================================================

    private void seleccionarFechaNacimientoDesdeString(String fechaNacimiento) {
        String valor = fechaNacimiento == null ? "" : fechaNacimiento.trim();

        if (valor.isEmpty()) {
            throw new AssertExceptions("[ACCIDENTES][EMISION][ERROR] Fecha de nacimiento está vacía.");
        }

        String dia;
        String mes;
        String anio;

        // dd/MM/yyyy
        if (valor.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            String[] p = valor.split("/");
            dia = p[0];
            mes = p[1];
            anio = p[2];

            // ddMMyyyy
        } else if (valor.matches("^\\d{8}$")) {
            dia = valor.substring(0, 2);
            mes = valor.substring(2, 4);
            anio = valor.substring(4, 8);

        } else {
            throw new AssertExceptions(
                    "[ACCIDENTES][EMISION][ERROR] Formato inválido de fecha nacimiento: " + valor +
                            " (permitidos: dd/MM/yyyy o ddMMyyyy)"
            );
        }

        // ✅ Selección independiente
        seleccionarOpcionSelectConFallback(comboDiaNacimiento, dia);
        seleccionarOpcionSelectConFallback(comboMesNacimiento, mes);
        seleccionarOpcionSelectConFallback(comboAnioNacimiento, anio);
    }

    private void seleccionarOpcionSelectConFallback(Locator combo, String valor) {
        try {
            seleccionarOpcionSelect(combo, valor); // "07"
            return;
        } catch (Exception ignored) { }

        String sinCero = valor.replaceFirst("^0+(?!$)", ""); // "07" -> "7"
        seleccionarOpcionSelect(combo, sinCero);
    }

    private boolean fechaNacimientoSeleccionada() {
        return selectTieneValorValido(comboDiaNacimiento)
                && selectTieneValorValido(comboMesNacimiento)
                && selectTieneValorValido(comboAnioNacimiento);
    }
    // ============================================================
    // Privados - Autocompletado / validaciones
    // ============================================================

    private void esperarAutocompletadoDatosPrincipales() {
        final int timeoutMs = 3_000;
        final int intervaloMs = 250;

        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < timeoutMs) {
            boolean nombresOk = !inputNombres.inputValue().trim().isEmpty();
            boolean paternoOk = !inputApellidoPaterno.inputValue().trim().isEmpty();
            boolean maternoOk = !inputApellidoMaterno.inputValue().trim().isEmpty();

            if (nombresOk && paternoOk && maternoOk) return;
            page.waitForTimeout(intervaloMs);
        }
    }

    private boolean inputEstaVacio(Locator input) {
        return input.inputValue().trim().isEmpty();
    }

    private boolean esVacio(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean selectTieneValorValido(Locator combo) {
        try {
            String textoSeleccionado = (String) combo.evaluate(
                    "select => select.selectedOptions.length > 0 ? select.selectedOptions[0].textContent.trim() : ''"
            );

            return textoSeleccionado != null
                    && !textoSeleccionado.isBlank()
                    && !textoSeleccionado.toUpperCase().contains("DÍA")
                    && !textoSeleccionado.toUpperCase().contains("MES")
                    && !textoSeleccionado.toUpperCase().contains("AÑO")
                    && !textoSeleccionado.toUpperCase().contains("SELECCIONE")
                    && !textoSeleccionado.toUpperCase().contains("TIPO")
                    && !textoSeleccionado.equals("-");

        } catch (Exception e) {
            return false;
        }
    }
    // ============================================================
    // Privados - Utilitario para <select>
    // ============================================================
    private void seleccionarOpcionSelect(Locator combo, String opcionEsperada) {
        if (opcionEsperada == null || opcionEsperada.trim().isEmpty()) {
            throw new AssertExceptions("[ACCIDENTES][EMISION][ERROR] Se intentó seleccionar una opción vacía en un combo.");
        }

        Locator comboUnico = combo.first();

        final int timeoutMs = 10_000;
        final int intervaloMs = 250;
        long start = System.currentTimeMillis();

        // 1) Esperar a que el select tenga opciones cargadas (más de 1)
        while (System.currentTimeMillis() - start < timeoutMs) {
            int totalOpciones = comboUnico.locator("option").count();
            if (totalOpciones > 1) break;
            page.waitForTimeout(intervaloMs);
        }

        // 2) Buscar value del option por JS (trim + normalización de espacios)
        String value = (String) comboUnico.evaluate(
                "(select, target) => {" +
                        "const norm = s => (s || '').replace(/\\s+/g,' ').trim().toLowerCase();" +
                        "const t = norm(target);" +
                        "const opts = Array.from(select.options || []);" +
                        "const found = opts.find(o => norm(o.textContent) === t);" +
                        "return found ? found.value : null;" +
                        "}",
                opcionEsperada
        );

        if (value == null || value.isBlank()) {
            // 3) Diagnóstico: listar opciones disponibles
            String opcionesDisponibles = (String) comboUnico.evaluate(
                    "select => Array.from(select.options || []).map(o => (o.textContent || '').replace(/\\s+/g,' ').trim()).join(', ')"
            );

            throw new AssertExceptions(
                    "[ACCIDENTES][EMISION][ERROR] No se encontró la opción en <select>: " + opcionEsperada +
                            ". Opciones disponibles: " + opcionesDisponibles
            );
        }

        comboUnico.selectOption(value);
    }
    private void esperarControlesFechaNacimiento() {
        final int timeoutMs = 10_000;
        final int intervaloMs = 250;

        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < timeoutMs) {
            boolean dia = comboDiaNacimiento.count() > 0;
            boolean mes = comboMesNacimiento.count() > 0;
            boolean anio = comboAnioNacimiento.count() > 0;

            if (dia && mes && anio) {
                return;
            }
            page.waitForTimeout(intervaloMs);
        }

        throw new AssertExceptions(
                "[ACCIDENTES][EMISION][ERROR] No se encontraron los combos de Fecha de Nacimiento (Día/Mes/Año) dentro del tiempo esperado."
        );
    }
/*============= IMPORTAR PLANILLA ========== */
    public void assertPasoCargarAsegurados() {
        ElementAsserts.assertVisible(
                seccionCargarAsegurados,
                "[ACCIDENTES][EMISION] SECCION Cargar asegurados DEBE SER VISIBLE"
        );

        log.info("[ACCIDENTES][EMISION] Paso visible: Cargar asegurados");
    }

    public void importarPlanilla(String nombreArchivo) {
        try {
            assertPasoCargarAsegurados();

            Path ruta = Paths.get(
                    System.getProperty("user.dir"),
                    "src", "test", "resources",
                    "testdata", "uploads", "templates",
                    nombreArchivo
            );

            ElementAsserts.assertVisible(
                    botonImportarPlanilla,
                    "[ACCIDENTES][EMISION] BOTON Importar planilla DEBE SER VISIBLE"
            );

            // ✅ CLAVE: usar FileChooser porque input se crea dinámicamente
            FileChooser chooser = page.waitForFileChooser(() -> {
                botonImportarPlanilla.click();
            });

            chooser.setFiles(ruta);

            log.info("[ACCIDENTES][EMISION] Archivo cargado correctamente: {}", nombreArchivo);

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][EMISION][ERROR] No se pudo importar la planilla.",
                    e
            );
        }
    }


    public void subirPlanilla() {
        try {
            ElementAsserts.assertVisible(
                    botonSubirPlanilla,
                    "[ACCIDENTES][EMISION] BOTON Subir planilla DEBE SER VISIBLE"
            );

            clickAndSync(botonSubirPlanilla);

            // Después de subir ya debería estar listo emitir
            ElementAsserts.assertVisible(
                    botonEmitirPoliza,
                    "[ACCIDENTES][EMISION] BOTON Emitir póliza DEBE SER VISIBLE DESPUES DE SUBIR"
            );

            log.info("[ACCIDENTES][EMISION] Planilla subida correctamente");

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][EMISION][ERROR] No se pudo subir la planilla.",
                    e
            );
        }
    }

    public void cargarPlanillaYEmitir(String archivoExcel) {
        importarPlanilla(archivoExcel);
        subirPlanilla();
        clickEmitirPoliza();
        confirmarEmisionModal();
    }

    public void clickEmitirPoliza() {
        try {
            ElementAsserts.assertVisible(
                    botonEmitirPoliza,
                    "[ACCIDENTES][EMISION] BOTON Emitir póliza DEBE SER VISIBLE"
            );

            log.info("[ACCIDENTES][EMISION] Botón visible: Emitir póliza");

            clickAndSync(botonEmitirPoliza);

            log.info("[ACCIDENTES][EMISION] Click realizado correctamente en Emitir póliza");

        } catch (AssertExceptions e) {
            throw e;

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][EMISION][ERROR] No se pudo hacer click en Emitir póliza.",
                    e
            );
        }
    }
    public void confirmarEmisionModal() {
        try {

            botonConfirmarEmitir.waitFor(); // espera modal

            ElementAsserts.assertVisible(
                    botonConfirmarEmitir,
                    "[ACCIDENTES][EMISION] MODAL Emitir DEBE SER VISIBLE"
            );

            botonConfirmarEmitir.scrollIntoViewIfNeeded();

            clickAndSync(botonConfirmarEmitir);

            log.info("[ACCIDENTES][EMISION] Emisión confirmada en modal");

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][EMISION][ERROR] No se pudo confirmar la emisión en el modal.",
                    e
            );
        }
    }

}