package com.mapfre.playwright.pageobjects.poliza.sctr.consultaDocumento;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.models.RegistroFechaSCTR;
import com.mapfre.models.SctrDocumento;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.playwright.support.export.SctrExportService;
import com.mapfre.utils.DateUtils;
import com.mapfre.utils.DownloadVsErrorRace;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class DocumentosSctrPage extends BasePage {
    // ====================== Logger ======================
    private static final Logger LOG = LoggerFactory.getLogger(DocumentosSctrPage.class);

    // ====================== Timeouts/esperas ======================
    private static final Duration TIMEOUT_LISTADO = Duration.ofSeconds(10);
    private static final Duration POLLING = Duration.ofMillis(500);

    private final Locator title;
    private final Locator fechaInicioInput;
    private final Locator fechaFinInput;
    private final Locator filtrarButton;
    private final Locator resultadoNoEncontradoMensaje;
    private final Locator resultadoEcnontradoConExito;
    private final Locator filasResultados;
    private final Locator nextButton;
    private final List<RegistroFechaSCTR> registrosIncorrectos = new ArrayList<>();
    private final List<RegistroFechaSCTR> registrosCorrectos = new ArrayList<>();

    // ====================== Patrones de extracción ======================
    private static final Pattern P_SCTR_LINEA =
            Pattern.compile("(?m)^(\\s*Nro\\.?\\s*Doc\\.?\\s*SCTR:\\s*\\d+).*?$");
    private static final Pattern P_SCTR_NUMERO =
            Pattern.compile("Nro\\.?\\s*Doc\\.?\\s*SCTR:\\s*(\\d+)");
    private static final Pattern P_FECHA_REGISTRO =
            Pattern.compile("Fecha\\s*Registro:\\s*([0-9]{2}/[0-9]{2}/[0-9]{4}(?:\\s+[0-9]{2}:\\d{2}:\\d{2})?)",
                    Pattern.CASE_INSENSITIVE);

    private final boolean defaultDedup = Boolean.parseBoolean(System.getProperty("sctr.dedup", "true"));
    private final Locator tipoProductoButton;
    private final Locator numeroPolizaInput;
    private final Locator estadoSelect;
    private final Locator origenSelect;
    private final Locator resultadoVerMasButton;
    private final Locator resultadoVerDetalleButton;
    private final Locator emitirSctrPeriodoRegularTitle;
    private final Locator modalResultadoDivError;
    private final Locator ventanaErrorTitulo;
    private final Locator ventanaErrorDescripcion;
    private final Locator reciboPensionDescargarButton;
    private final Locator reciboSaludDescargarButton;
    private final Locator polizaPensionDescargarButton;
    private final Locator polizaSaludDescargarButton;
    private final Locator nroSolicitudInput;
    private final Locator polizaInput;
    private final Locator frecuenciaDeclaracionSelect;
    private final Locator numeroSolicitudSctrInput;
    private final Locator buscarButton;
    private final Locator paginadorSiguienteButton;
    private final Locator resultList;
    private final Locator verDetalleButton;
    private final Locator seccionRiesgosContainer;
    private final Locator resultadoMensajeError;
    public DocumentosSctrPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Documentos SCTR"));
        this.fechaInicioInput = page.locator("oim-datepicker[name='nConsultaDesde']").locator("input");
        this.fechaFinInput = page.locator("oim-datepicker[name='nConsultaHasta']").locator("input");
        this.filtrarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Filtrar"));
        this.resultadoNoEncontradoMensaje = page.getByText("No hay resultados para los filtros escogidos");
        this.resultadoEcnontradoConExito = page.getByText(Pattern.compile("Nro\\. Doc\\. SCTR:.*"));
        this.filasResultados = page.locator("div.g-myd-result");
        this.nextButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Siguiente página"));
        this.tipoProductoButton = page.getByText("Largo");
        this.numeroPolizaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Póliza"));
        this.estadoSelect = page.locator("oim-select[name='nEstado'] select");
        this.origenSelect = page.locator("oim-select[name='nOrigen'] select");
        this.resultadoVerMasButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("VER MÁS"));
        this.resultadoVerDetalleButton = page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Ver detalle"));
        this.emitirSctrPeriodoRegularTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Emitir póliza SCTR Periodo"));
        this.modalResultadoDivError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.ventanaErrorTitulo = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ERROR"));
        this.ventanaErrorDescripcion = page.getByText("Ocurrió un error inesperado.");
        this.reciboPensionDescargarButton = page.locator("ul.g-list.second-design:has(.item-label:has-text('Recibo de Pensión')) a:has-text('DESCARGAR')");
        this.reciboSaludDescargarButton = page.locator("ul.g-list.second-design:has(.item-label:has-text('Recibo de Salud'))   a:has-text('DESCARGAR')");
        this.polizaPensionDescargarButton = page.locator("ul.g-list.second-design:has(.item-label:has-text('Póliza de Pensión')) a:has-text('DESCARGAR')");
        this.polizaSaludDescargarButton = page.locator("ul.g-list.second-design:has(.item-label:has-text('Póliza de Salud'))   a:has-text('DESCARGAR')");
        this.nroSolicitudInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nro. Solicitud"));
        this.polizaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Póliza"));
        this.frecuenciaDeclaracionSelect = page.getByLabel("Frecuencia de Declaración");
        this.numeroSolicitudSctrInput = page.locator("oim-input[name='nNumDocSCTR'] input");
        this.buscarButton = page.locator("button").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Buscar", Pattern.CASE_INSENSITIVE)));
        this.paginadorSiguienteButton = page.locator("button[aria-label='Siguiente página']").first();
        this.resultList = page.locator("div.g-myd-result");
        this.verDetalleButton = page.locator("button").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Ver detalle", Pattern.CASE_INSENSITIVE)));
        this.seccionRiesgosContainer = page.locator("div.risk-box--inputs");
        this.resultadoMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Documentos SCTR DEBE SER VISIBLE");
        ElementAsserts.assertVisible(nroSolicitudInput, "INPUT Nro. Solicitud DEBE SER VISIBLE");
        ElementAsserts.assertVisible(polizaInput, "INPUT Póliza DEBE SER VISIBLE");
        ElementAsserts.assertVisible(frecuenciaDeclaracionSelect, "SELECT Frecuencia de Declaración DEBE SER VISIBLE");
        ElementAsserts.assertVisible(estadoSelect, "SELECT Estado DEBE SER VISIBLE");
        ElementAsserts.assertVisible(origenSelect, "SELECT Origen DEBE SER VISIBLE");
    }

    public void fillFormByDate(String fecha_inicio, String fecha_fin) {
        scrollPageBy(0,10000);
        fillReadonlyDate(fechaInicioInput, fecha_inicio);
        fillReadonlyDate(fechaFinInput, fecha_fin);
    }

    public void clickFiltrarButton() {
        clickAndSync(filtrarButton);
    }
    public void clickBuscarButton() {
        clickAndSync(buscarButton);
        log.info("[DOCUMENTOS SCTR][BUSQUEDA DE DOCUMENTOS SCTR] Se hizo click en el boton Buscar");
    }

    public void processAndAssertSuccess(long timeoutMs, long quietMs) {
        scrollToTop();
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoNoEncontradoMensaje,
                resultadoEcnontradoConExito.first(),
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoNoEncontradoMensaje.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
    }

    public void validateDateRange() {
        LocalDateTime fechaA = DateUtils.parseDateTime(fechaInicioInput.inputValue().trim()+" 00:00:00");
        System.out.println("Fecha de Inicio: "+fechaInicioInput.inputValue().trim()+" 00:00:00");
        LocalDateTime fechaC = DateUtils.parseDateTime(fechaFinInput.inputValue().trim()+" 23:59:59");
        System.out.println("Fecha Fin: "+fechaFinInput.inputValue().trim()+" 23:59:59");

        //validarTodasLasFechasEnRango(fechaA, fechaC);
        while (true) {
            filasResultados.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

            for (int i = 0; i < filasResultados.count(); i++) {
                Locator filaActual = filasResultados.nth(i);
                String numDoc = filaActual.getByText(Pattern.compile("Nro\\. Doc\\. SCTR:.*")).innerText();
                String fechaTexto = filaActual.locator(".item-label:has-text('Fecha Registro:') + .item-dato").innerText().trim();

                RegistroFechaSCTR registro = new RegistroFechaSCTR(numDoc, fechaTexto);
                LocalDateTime dateB = DateUtils.parseDateTime(registro.fechaRegistro());
                boolean isInRange = !dateB.isBefore(fechaA) && !dateB.isAfter(fechaC);

                if (!isInRange) {
                    throw new AssertExceptions(
                            "¡Prueba Fallida! El registro está fuera de rango.\n" +
                                    "Doc: " + numDoc + " | Fecha encontrada: " + fechaTexto + "\n" +
                                    "Rango esperado: [" + fechaA.toLocalDate() + " - " + fechaC.toLocalDate() + "]");
                }
            }
            if (!nextButton.isEnabled()) {
                break;
            }
            clickAndSync(nextButton);
        }
        System.out.println("Validación completada: Todas las fechas en todas las páginas están dentro del rango.");
    }

    // ====================== Extracción (página actual) ======================
    /**
     * Extrae las filas visibles de la página actual (1 card = 1 fila).
     * Contenedor de card: div.pt-xs-1.gBgcGray5.h-myd-bg--white
     * Header (número):     div.col-sm-12.item-label.gH4 > b (contiene "Nro. Doc. SCTR:")
     * Fecha:               label "Fecha Registro: ..." dentro del MISMO card
     */
    public List<SctrDocumento> extractCurrentPageRows() {
        // Selecciona SOLO cards visibles que contengan el header esperado
        Locator visibleCards = page.locator(
                "div.pt-xs-1.gBgcGray5.h-myd-bg--white:has(div.col-sm-12.item-label.gH4:has-text('Nro. Doc. SCTR')):visible"
        );

        int visibles = visibleCards.count();
        List<SctrDocumento> rows = new ArrayList<>(visibles);

        for (int i = 0; i < visibles; i++) {
            Locator card = visibleCards.nth(i);

            // Texto completo del card (normalizado) para búsquedas robustas
            String cardText = normalizeForRegex(card.innerText());

            // Header con el número (línea)
            String linea = null;
            Locator header = card.locator("div.col-sm-12.item-label.gH4");
            if (header.count() > 0) {
                Locator b = header.first().locator("b:has-text('Nro. Doc. SCTR')");
                if (b.count() > 0) {
                    linea = b.first().innerText().trim();
                } else {
                    String raw = normalizeForRegex(header.first().innerText());
                    var mLine = P_SCTR_LINEA.matcher(raw);
                    if (mLine.find()) linea = mLine.group(1).trim();
                }
            }
            if (linea == null) {
                var mLine = P_SCTR_LINEA.matcher(cardText);
                if (mLine.find()) linea = mLine.group(1).trim();
            }
            if (linea == null) continue;

            // Número
            String numero = null;
            var mNum = P_SCTR_NUMERO.matcher(linea);
            if (mNum.find()) {
                numero = mNum.group(1).trim();
            } else {
                mNum = P_SCTR_NUMERO.matcher(cardText);
                if (mNum.find()) numero = mNum.group(1).trim();
            }
            if (numero == null) continue;

            // Fecha (buscar SIEMPRE en el texto completo normalizado del card)
            String fecha = "";
            var mF = P_FECHA_REGISTRO.matcher(cardText);
            if (mF.find()) {
                fecha = mF.group(1).trim();
            } else {
                // Fallback por si el DOM separa label/valor en nodos hermanos
                Locator fechaLbl = card.getByText("Fecha Registro:", new Locator.GetByTextOptions().setExact(false));
                if (fechaLbl.count() > 0) {
                    Locator value = fechaLbl.first().locator("xpath=following-sibling::*[1]");
                    if (value.count() == 0) {
                        value = fechaLbl.first().locator("xpath=../following-sibling::*[1]");
                    }
                    if (value.count() > 0) {
                        String v = normalizeForRegex(value.first().innerText());
                        var m2 = P_FECHA_REGISTRO.matcher("Fecha Registro: " + v);
                        if (m2.find()) fecha = m2.group(1).trim();
                    }
                }
            }

            // (Opcional) diagnóstico
            // LOG.debug("[SCTR] Extraído numero='{}' fecha='{}' linea='{}'", numero, fecha, linea);
            rows.add(new SctrDocumento(numero, linea, fecha));
        }

        return rows;
    }

    // ====================== Lectura con paginación + deduplicación ======================
    /** Por defecto QUITA duplicados por número (preferencia por el que tiene fecha). */
    public List<SctrDocumento> readAllDocuments() {
        return readAllDocuments(defaultDedup);
    }

    /**
     * @param dedup true = quita duplicados por numero (prefiere el que tiene fecha); false = tal cual UI.
     */
    public List<SctrDocumento> readAllDocuments(boolean dedup) {
        List<SctrDocumento> all = new ArrayList<>();

        while (true) {
            page.waitForLoadState(LoadState.NETWORKIDLE);
            all.addAll(extractCurrentPageRows());
            if (!goToNextPage()) break;
        }

        if (!dedup) {
            LOG.info("[SCTR] UI -> documentos leidos (sin deduplicar): {}", all.size());
            return all;
        }

        // Dedup por numero, manteniendo orden y prefiriendo fila con fecha
        LinkedHashMap<String, SctrDocumento> unique = new LinkedHashMap<>();
        for (SctrDocumento d : all) {
            if (d == null || d.numero() == null) continue;
            SctrDocumento existente = unique.get(d.numero());
            if (existente == null) {
                unique.put(d.numero(), d);
            } else {
                boolean existenteTieneFecha = existente.fechaRegistro() != null && !existente.fechaRegistro().isBlank();
                boolean nuevoTieneFecha = d.fechaRegistro() != null && !d.fechaRegistro().isBlank();
                if (!existenteTieneFecha && nuevoTieneFecha) {
                    unique.put(d.numero(), d);
                }
            }
        }

        // LOG.info("[SCTR] UI -> Registros SCTR leidos : {}", all.size());
        return new ArrayList<>(unique.values());
    }

    // ====================== Export helpers ======================
    /** Exporta a: target/Export/documentoSctr/DocumentoSctr_<MMdd_HHmmss>.txt y devuelve el Path. */
    public Path exportarTxt() {
        // Usa 'Export' (mayúscula) para mantener consistencia con SctrExportService por defecto
        return exportarTxt(Paths.get("target", "export", "documentoSctr"));
    }

    /** Alias para compatibilidad con el step que llama exportartxt(). */
    public Path exportartxt() {
        return exportarTxt();
    }

    /** Variante para exportar a un directorio específico. */
    public Path exportarTxt(Path outDir) {
        try {
            var docs = readAllDocuments(true); // dedup por número
            LOG.info("[SCTR] UI -> Nro de documentos SCTR leídos: {}", docs.size());

            var exporter = new SctrExportService(outDir);
            Path txt = exporter.exportTxt(docs);

            // LOG.info("[SCTR] TXT: {}", txt.toAbsolutePath());
            return txt;
        } catch (Exception e) {
            throw new IllegalStateException("No fue posible exportar Documentos SCTR a TXT en: " + outDir, e);
        }
    }

    // ====================== Paginación ======================
    /** Click en “Siguiente página” y espera a que cambie el primer número visible para no releer. */
    private boolean goToNextPage() {
        Locator next = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Siguiente página")
        );

        if (next.count() == 0) return false;
        Locator btn = next.first();
        if (!btn.isEnabled()) return false;

        String before = firstNumeroOnPage();
        btn.click();

        page.waitForFunction("(prev) => !document.body.innerText.includes(prev)", before);
        return true;
    }

    /** Devuelve el primer 'Nro. Doc. SCTR: #######' visible en pantalla, o "" si no hay. */
    private String firstNumeroOnPage() {
        Locator firstCardNumero = page.locator(
                "div.pt-xs-1.gBgcGray5.h-myd-bg--white:has(div.col-sm-12.item-label.gH4:has-text('Nro. Doc. SCTR')):visible"
        ).first().locator("b:has-text('Nro. Doc. SCTR')");
        if (firstCardNumero.count() == 0) return "";
        String t = firstCardNumero.first().innerText();
        var m = P_SCTR_NUMERO.matcher(t);
        return m.find() ? m.group(1) : "";
    }

    // ====================== Helpers ======================
    private static String extractFirst(String text, Pattern p, int group) {
        var m = p.matcher(text);
        return m.find() ? m.group(group) : null;
    }

    private static String safe(String s) {
        return s == null ? "" : s.replaceAll("[\\r\\n]+", " ").trim();
    }

    /** Normaliza texto para regex: reemplaza NBSP y saltos de línea por espacio y colapsa espacios. */
    private static String normalizeForRegex(String s) {
        if (s == null) return "";
        return s
                .replace('\u00A0', ' ')
                .replace("\r", " ")
                .replace("\n", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public void fillFormDocumentoSctr(String tipoProducto, String numeroPoliza, String estadoPoliza, String fechaInicio, String fechaFin, String origen) {
        clickAndSync(tipoProductoButton);
        fillAndSync(numeroPolizaInput,numeroPoliza);
        selectAndSync(estadoSelect,estadoPoliza);
        fillReadonlyDate(fechaInicioInput, fechaInicio);
        fillReadonlyDate(fechaFinInput, fechaFin);
        selectAndSync(origenSelect,origen);
    }

    public void fillFormDocumentoSctr(String numeroSolicitudSctr) {
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(1000);
        fillAndSync(numeroSolicitudSctrInput, numeroSolicitudSctr);
        log.info("[DOCUMENTOS SCTR][BUSQUEDA DE NUMERO DOCUMENTO SCTR] Se llenó el campo de búsqueda con el número de documento SCTR: {}", numeroSolicitudSctr);
    }

    public void clickVerDetalleButton() {
        clickAndSync(resultadoVerMasButton);
        clickAndSync(resultadoVerDetalleButton);
    }
    public void processAndAssertResultadoVerDetalleSuccess(long timeoutMs, long quietMs) {
        scrollToTop();
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                modalResultadoDivError,
                emitirSctrPeriodoRegularTitle,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + modalResultadoDivError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
    }


    public void downloadPolizaPension() {
        ElementAsserts.assertVisible(reciboPensionDescargarButton,"El boton descargar Poliza Pension visible");
        // 2) Race: UI error vs download completion
        DownloadVsErrorRace.Result r = DownloadVsErrorRace.race(
                page,
                polizaPensionDescargarButton::click,
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
            throw new FrameworkException("Ni el error modal ni la finalización de la descarga ocurrieron dentro del tiempo de espera. Poliza Pension Descargar");
        }

        // DOWNLOAD_SUCCESS -> continue
        log.info("Descarga de Póliza de Pensión completada exitosamente. Archivo: {}", r.filename());
    }
    public void downloadPolizaSalud() {
        ElementAsserts.assertVisible(polizaPensionDescargarButton,"El boton descargar Poliza Salud visible");
        DownloadVsErrorRace.Result r = DownloadVsErrorRace.race(
                page,
                polizaSaludDescargarButton::click,
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
            throw new FrameworkException("Ni el error modal ni la finalización de la descarga ocurrieron dentro del tiempo de espera. Poliza Salud Descargar");
        }

        // DOWNLOAD_SUCCESS -> continue
        log.info("Descarga de Póliza de Salud completada exitosamente. Archivo: {}", r.filename());
    }

    public void downloadReciboPension() {
        scrollPageBy(0,100000);
        ElementAsserts.assertVisible(reciboPensionDescargarButton,"El boton descargar Recibo Pension visible");
        DownloadVsErrorRace.Result r = DownloadVsErrorRace.race(
                page,
                reciboPensionDescargarButton::click,
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
        log.info("Descarga de Recibo de Pensión completada exitosamente. Archivo: {}", r.filename());
    }

    public void downloadReciboSalud() {
        ElementAsserts.assertVisible(reciboSaludDescargarButton,"El boton descargar Recibo Salud visible");
        DownloadVsErrorRace.Result r = DownloadVsErrorRace.race(
                page,
                reciboSaludDescargarButton::click,
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
            throw new FrameworkException("Ni el error modal ni la finalización de la descarga ocurrieron dentro del tiempo de espera. Recibo Salud Descargar");
        }

        // DOWNLOAD_SUCCESS -> continue
        log.info("Descarga de Recibo de Salud completada exitosamente. Archivo: {}", r.filename());
    }

    public void encontrarNumeroDocumentoSctrEnResultados(String numeroDocumentoSctr) {
        int pagina = 1;

        while (true) {
            esperarResultadosVisibles();

            int totalResultados = resultList.count();
            log.info("[DOCUMENTOS SCTR][BUSQUEDA DE DOCUMENTOS SCTR] Número de resultados en la página {}: {}", pagina, totalResultados);
            for (int i = 0; i < totalResultados; i++) {
                String textoCard = resultList.nth(i).innerText();

                if (textoCard != null && textoCard.contains(numeroDocumentoSctr)) {
                    log.info("[DOCUMENTOS SCTR][BUSQUEDA DE DOCUMENTOS SCTR] Se encontró el numero de documento SCTR {} en la página {}, fila {}.", numeroDocumentoSctr, pagina, i + 1);
                    return;
                }
            }

            if (!paginadorSiguienteButton.isEnabled()) {
                throw new FrameworkException("[DOCUMENTOS SCTR][BUSQUEDA DE DOCUMENTOS SCTR] No se encontró el numero de documento " + numeroDocumentoSctr + " después de revisar todas las páginas.");
            }

            String primerResultadoAntes = totalResultados > 0 ? resultList.first().innerText() : "";

            log.info("[DOCUMENTOS SCTR][BUSQUEDA DE DOCUMENTOS SCTR] El numero de documento {} no está en la página {}. Avanzando a la siguiente.", numeroDocumentoSctr, pagina);

            clickAndSync(paginadorSiguienteButton);
            esperarCambioDeResultados(primerResultadoAntes);

            pagina++;
        }
    }
    private void esperarResultadosVisibles() {
        resultList.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    private void esperarCambioDeResultados(String primerResultadoAntes) {
        int intentos = 20;

        for (int i = 0; i < intentos; i++) {
            if (resultList.count() > 0) {
                String textoActual = resultList.first().innerText();
                if (textoActual != null && !textoActual.equals(primerResultadoAntes)) {
                    return;
                }
            }
            page.waitForTimeout(500);
        }

        throw new FrameworkException("[DOCUMENTOS SCTR][BUSQUEDA DE DOCUMENTOS SCTR] La lista de resultados no cambió después de avanzar de página.");
    }

    public void clickVerDetalleNumeroDocumentoSctrButton(String numeroDocumentoSctr) {
        boolean found = false;
        for (int i = 0; i < resultList.count(); i++) {
            Locator solicitudItem = resultList.nth(i);
            Locator numeroSolicitudLocator = solicitudItem.getByText(Pattern.compile("Nro\\. Doc\\. SCTR:.*"+numeroDocumentoSctr+".*"));
            if (numeroSolicitudLocator.isVisible()) {
                Locator verProgramacionLink = solicitudItem.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("VER MÁS"));
                clickAndSync(verProgramacionLink);
                clickAndSync(verDetalleButton);
                log.info("[DOCUMENTOS SCTR][BUSQUEDA DE DOCUMENTOS SCTR] Numero de documento numeroDocumentoSCTR = {} encontrada y se hizo click en el enlace VER DETALLE", numeroDocumentoSctr);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new FrameworkException("[DOCUMENTOS SCTR][BUSQUEDA DE DOCUMENTOS SCTR] No se encuentró el numero de documento SCTR " + numeroDocumentoSctr + " en los resultados de busqueda.");
        }
    }

    public void processAndAssertSuccessVerDetalleNumeroDocumentoSctrButton(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoMensajeError,
                seccionRiesgosContainer.first(),
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
        log.info("[EMISION][SCTR PERIODO CORTO] Se hizo click en el boton Ver Detalle del numero de documento SCTR y se cargó correctamente la sección de riesgos.");
    }

    /*public void validarTodasLasFechasEnRango(LocalDateTime fechaA, LocalDateTime fechaC) {
        while (true) {
            filasResultados.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            for (int i = 0; i < filasResultados.count(); i++) {
                Locator filaActual = filasResultados.nth(i);
                String numDoc = filaActual.getByText(Pattern.compile("Nro\\. Doc\\. SCTR:.*")).innerText();
                String fechaTexto = filaActual.locator(".item-label:has-text('Fecha Registro:') + .item-dato").innerText().trim();
                RegistroFechaSCTR registro = new RegistroFechaSCTR(numDoc, fechaTexto);
                clasificarRegistro(registro, fechaA, fechaC);
            }
            if (!nextButton.isEnabled()) {
                break;
            }
            clickAndSync(nextButton);
        }
        reportarResultadosFinales();
    }

    private void clasificarRegistro(RegistroFechaSCTR registro, LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime dateB = DateUtils.parseDateTime(registro.fechaRegistro());
        boolean isInRange = !dateB.isBefore(startDate) && !dateB.isAfter(endDate);

        if (isInRange) {
            registrosCorrectos.add(registro);
        } else {
            registrosIncorrectos.add(registro);

        }
    }

    private void reportarResultadosFinales() {
        // Formateamos los detalles de los registros en Strings limpios
        String detallesIncorrectos = registrosIncorrectos.stream()
                .map(r -> "  - Doc: " + r.numeroDocumento() + " | Fecha: " + r.fechaRegistro())
                .collect(Collectors.joining("\n"));

        String detallesCorrectos = registrosCorrectos.stream()
                .map(r -> "  - Doc: " + r.numeroDocumento() + " | Fecha: " + r.fechaRegistro())
                .collect(Collectors.joining("\n"));

        // Construimos el mensaje completo
        String reporteCompleto = "\n--- Resumen de Validación de Registros SCTR ---\n\n" +
                "### Registros Fuera de Rango (Incorrectos): " + registrosIncorrectos.size() + "\n" +
                (registrosIncorrectos.isEmpty() ? "  Ninguno.\n" : detallesIncorrectos + "\n\n") +
                "### Registros Dentro de Rango (Correctos): " + registrosCorrectos.size() + "\n" +
                (registrosCorrectos.isEmpty() ? "  Ninguno.\n" : detallesCorrectos + "\n") +
                "--------------------------------------------------";

        // Imprimimos el reporte completo en la consola/log
        System.out.println(reporteCompleto);

        // Aserción Final: Fallar la prueba si se encontraron registros incorrectos
        if (!registrosIncorrectos.isEmpty()) {
            // Usamos una excepción limpia para fallar la prueba formalmente
            throw new AssertExceptions("¡La prueba falló! Se encontraron " + registrosIncorrectos.size() +
                    " registros fuera del rango de fechas. Revisa el reporte completo en el log.");
        }
    }

*/
}
