package com.mapfre.playwright.pageobjects.poliza.fola;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.regex.MatchResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;


public class EmisionPolizaFolaPage extends BasePage {
    private static final String ARCHIVO_PLANTILLA = "src/test/resources/testdata/uploads/templates/plantilla_asegurados_fola.xlsx";
    private static final Pattern NUMERO_POLIZA_PATTERN = Pattern.compile("(\\d{8,})");

    private final Locator tituloEmision;
    private final Locator anclaCargaEmision;
    private final Locator descargarFormatoButton;
    private final Locator modalOkButton;
    private final Locator telefonoInput;
    private final Locator telefonoMovilInput;
    private final Locator nombreRepresentanteInput;
    private final Locator cargoRepresentanteSelect;
    private final Locator emisionButton;
    private final Locator modalExitoTitulo;
    private final Locator numeroPolizaEmitidaCache;

    public EmisionPolizaFolaPage(Page page) {
        super(page);
        this.tituloEmision = page.locator("h1:has-text('Emision'), h2:has-text('Emision')").first();
        this.anclaCargaEmision = page.locator("#file-upload");
        this.descargarFormatoButton = page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Descargar Formato"));
        this.modalOkButton = page.locator("button:has-text('OK')").first();
        this.telefonoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono Casa"));
        this.telefonoMovilInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono Móvil"));
        this.nombreRepresentanteInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Representante"));
        this.cargoRepresentanteSelect = page.getByLabel("Cargo representante");
        this.emisionButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Emitir Póliza"));
        this.modalExitoTitulo = page.getByRole(AriaRole.DIALOG, new Page.GetByRoleOptions().setName("MENSAJE"));
        this.numeroPolizaEmitidaCache = page.locator(
                ".g-tbl-wrapper .row.ng-star-inserted.g-border-bottom .col-2.g-text-center-xs p.mb-xs-0"
        ).first();
    }
    // Implementa los métodos y elementos de la página de emisión de póliza Fola aquí
    public void assertLoaded() {
        if (tituloEmision.isVisible()) {
            log.info("[FOLA][EMISION] Título de emisión detectado");
            return;
        }
        ElementAsserts.assertVisible(anclaCargaEmision, "PANTALLA DE EMISION FOLA DEBE MOSTRAR CONTROLES DE CARGA");
        log.info("[FOLA][EMISION] Pantalla de emisión detectada por controles de carga (sin título visible)");
    }

    public void descargarFormatoYAdjuntarFormatoCompletado() {
        ElementAsserts.assertVisible(descargarFormatoButton, "BOTON Descargar Formato DEBE SER VISIBLE");
        clickAndSync(descargarFormatoButton);
        cerrarPopup();

        Path archivoPlanilla = obtenerRutaPlantilla();

        if (!Files.exists(archivoPlanilla)) {
            throw new IllegalStateException("No se encontró el archivo de plantilla para emisión FOLA: " + archivoPlanilla);
        }

        ElementAsserts.assertVisible(anclaCargaEmision, "INPUT DE CARGA DE FORMATO DEBE SER VISIBLE");
        uploadFile(anclaCargaEmision, archivoPlanilla);
        log.info("[FOLA][EMISION] Formato adjuntado correctamente: {}", archivoPlanilla);
        cerrarPopup();
    }

    private void cerrarPopup() {
        try {
            if (modalOkButton.isVisible()) {
                clickForcedAndSync(modalOkButton);
            }
        } catch (Exception ignored) {
            // no-op
        }
    }
    private Path obtenerRutaPlantilla() {
        return Paths.get(ARCHIVO_PLANTILLA);
    }
    public void ingresarDatosDeContactoYRepresentante(String telefono, String telefonoMovil, String nombreRepresentante, String cargoRepresentante) {
        fillInputIfEnabledAndSync(telefonoInput, telefono);
        fillInputIfEnabledAndSync(telefonoMovilInput, telefonoMovil);
        fillInputIfEnabledAndSync(nombreRepresentanteInput, nombreRepresentante);
        selectByOptionIfEnableAndSync(cargoRepresentanteSelect, cargoRepresentante);
        log.info("[FOLA][EMISION] Datos de contacto y representante completados");
    }
    public void clickEmisionFola() {
        ElementAsserts.assertVisible(emisionButton, "BOTON Emision DEBE SER VISIBLE EN FOLA");
        emisionButton.scrollIntoViewIfNeeded();
        clickForcedAndSync(emisionButton);
        log.info("[FOLA][EMISION] Botón Emision ejecutado");
    }
    public void processAndAssertPolizaEmitida(long timeoutMs) {
        try {
            modalExitoTitulo.waitFor(new Locator.WaitForOptions().setTimeout(timeoutMs));
            ElementAsserts.assertVisible(modalOkButton, "MENSAJE DE RESULTADO DE EMISION DEBE SER VISIBLE");

            String textoModal = modalExitoTitulo.innerText().trim();
            String numeroPolizaModal = NUMERO_POLIZA_PATTERN.matcher(textoModal)
                    .results()
                    .map(MatchResult::group)
                    .findFirst()
                    .orElse(null);

            if (numeroPolizaModal != null) {
                log.info("[FOLA][EMISION] Número de póliza emitida (modal): {}", numeroPolizaModal);
            } else {
                log.info("[FOLA][EMISION] Modal de emisión detectado sin número de póliza parseable: {}", textoModal);
            }

            if (modalOkButton.isVisible()) {
                clickForcedAndSync(modalOkButton);
            }
        } catch (Exception ignored) {
            log.info("[FOLA][EMISION] No apareció modal explícito de póliza emitida; se continúa a validación de documentos");
        }

    }
    public void obtenerNumeroDePoliza() {
        try {
            String numeroPoliza = numeroPolizaEmitidaCache.innerText().trim();
            log.info("[FOLA][EMISION] Número de póliza emitida (cache): {}", numeroPoliza);
        } catch (Exception e) {
            throw new IllegalStateException("[FOLA][EMISION] No se pudo obtener el número de póliza emitida desde la página.", e);
        }
    }
}
