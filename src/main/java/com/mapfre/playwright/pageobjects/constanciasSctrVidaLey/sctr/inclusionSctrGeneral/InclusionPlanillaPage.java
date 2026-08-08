package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Path;
import java.util.regex.Pattern;

public class InclusionPlanillaPage extends BasePage {
    private final Locator title;
    private final Locator policyCards;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombreCompletoInput;
    private final Locator fechaNacimientoInput;
    private final Locator sueldoInput;
    private final Locator cargaIndividualButton;
    private final Locator siguienteButton;
    private final Locator aceptarModalLinkButton;
    private final Locator informacionInclusionLabel;
    private final Locator resultadoModalMensajeError;
    private final Locator facturadaRadioButton;
    private final Locator siguienteAngularButton;
    private final Locator aceptarModalAngularButton;
    private final Locator ocupacionInput;
    private final Locator tipoRiesgoSelect;
    private final Locator lugarExposicionInput;
    private final Locator sexoSelect;
    private final Locator procesarButton;
    private final Locator facturacionGroup;
    private final Locator facturadaRadioVidaLeyButton;
    private final Locator numeroAseguradosCargadosLabel;
    private final Locator iImportarPlanillaInput;
    private final Locator planillaCargadaExitosamenteMessage;
    private final Locator listaAseguradosContainer;
    public InclusionPlanillaPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Inclusión de planilla"));
        this.policyCards = page.locator("div.g-box").filter(new Locator.FilterOptions().setHasText("Póliza"));
        this.tipoDocumentoSelect = page.locator("oim-select[label='Tipo de documento'] select");
        this.numeroDocumentoInput = page.locator("oim-input[label='Nro. Documento'] input");
        this.nombreCompletoInput = page.locator("oim-input[label='Nombre Completo'] input");
        this.fechaNacimientoInput = page.locator("oim-datepicker[label='Fecha Nacimiento']").locator("input");
        this.sueldoInput = page.locator("oim-input[label='Sueldo'] input");
        this.cargaIndividualButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Cargar individual")));
        this.siguienteButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Siguiente")));
        this.aceptarModalLinkButton = page.locator("a.g-button.g-button--second-design").filter(new Locator.FilterOptions().setHasText("Aceptar"));
        this.informacionInclusionLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Información de Inclusión").setLevel(2));
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.facturadaRadioButton = page.locator("oim-radio[label='Facturada'] input");
        this.siguienteAngularButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Siguiente"));
        this.aceptarModalAngularButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Aceptar"));
        this.ocupacionInput = page.locator("oim-input[label='Ocupación'] input");
        this.tipoRiesgoSelect = page.locator("oim-select[label='Tipo de riesgo'] select");
        this.lugarExposicionInput = page.locator("oim-autocomplete[label='Lugar de Exposicion'] input");
        this.sexoSelect = page.locator("oim-select[label='Sexo'] select");
        this.procesarButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Procesar"));
        this.facturacionGroup = page.locator("mat-radio-group[name='nOpcionFacturacion'][role='radiogroup']");
        this.facturadaRadioVidaLeyButton = facturacionGroup.getByText("Facturada", new Locator.GetByTextOptions().setExact(true));
        this.numeroAseguradosCargadosLabel = page.locator(".gH2 b");
        this.iImportarPlanillaInput = page.locator("input#iImportarPlanilla");
        this.planillaCargadaExitosamenteMessage = page.getByText("Planilla cargada exitosamente");
        this.listaAseguradosContainer = page.locator("div.gnContentAuto-bg");

    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Inclusión de planilla DEBE SER VISIBLE");
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
    }

    public void fillFormDetallesPolizas() {
        int count = policyCards.count();
        //datos en duro para la carga individual, se pueden parametrizar si se desea
        for (int i = 0; i < count; i++) {
            Locator card = policyCards.nth(i);
            // Detect policy name from header text
            String headerText = card.getByText("Póliza").first().innerText();
            if (headerText.contains("Pensión")) {
                fillPolicy(card, "1", "3000");
                log.info("[INCLUSION][ASEGURADO] Póliza de Pensión completada - Trabajadores: 1, Monto: 3000");
            }
            if (headerText.contains("Salud")) {
                fillPolicy(card, "1", "3000");
                log.info("[INCLUSION][ASEGURADO] Póliza de Salud completada - Trabajadores: 1, Monto: 3000");
            }
        }
    }

    public void fillAseguradosCargaIndividual(String numeroDocumento) {
        clickAndSync(cargaIndividualButton);
        //datos en duro para la carga individual, se pueden parametrizar si se desea
        selectAndSync(tipoDocumentoSelect, "DNI");
        fillAndSync(numeroDocumentoInput, numeroDocumento);
        fillAndSync(nombreCompletoInput, "JUAN PEREZ ROJAS");
        fillReadonlyDate(fechaNacimientoInput, "01/01/1990");
        fillAndSync(sueldoInput, "3000");
        //clickAndSync(siguienteButton);
        log.info("[INCLUSION][ASEGURADO] Asegurado cargado con exito");

    }
    public void fillAseguradosCargaIndividualVidaLey(String numeroDocumento) {
        clickAndSync(cargaIndividualButton);
        //datos en duro para la carga individual, se pueden parametrizar si se desea
        selectAndSync(tipoDocumentoSelect, "DNI");
        fillAndSync(numeroDocumentoInput, numeroDocumento);
        fillAndSync(nombreCompletoInput, "JUAN PEREZ ROJAS");
        fillReadonlyDate(fechaNacimientoInput, "01/01/1990");
        fillAndSync(ocupacionInput, "INGENIERO");
        fillAndSync(sueldoInput, "3000");
        selectAndSync(tipoRiesgoSelect,"EMPLEADOS");
        selectFromMatAutocompleteFailFast(lugarExposicionInput,"010101","Lugar de Exposición");
        assertAutocompleteSelected(lugarExposicionInput,"010101","Lugar de Exposición");
        selectAndSync(sexoSelect,"MASCULINO");
        log.info("[INCLUSION][ASEGURADO] Formulario de la carga individual Vida Ley con exito");
    }
    public void seleccionarFacturadaSctr() {
        clickAndSync(facturadaRadioButton);
        log.info("[INCLUSION][ASEGURADO] Opción No Facturada seleccionada con exito");
    }

    protected void fillPolicy(Locator card, String trabajadores, String monto) {
        card.getByLabel(
                        "Nro. Trabajadores",
                        new Locator.GetByLabelOptions().setExact(false)
                )
                .fill(String.valueOf(trabajadores));
        card.getByLabel(
                        "Monto",
                        new Locator.GetByLabelOptions().setExact(false)
                )
                .fill(String.valueOf(monto));
    }

    public void clickSiguienteAngularButton() {
        siguienteAngularButton.click();
        log.info("[INCLUSION][OBSERVACIONES] Botón Siguiente clickeado con exito");

    }

    public void processAndAssertSuccessListaDeObservaciones(long timeoutMs, long quietMs) {
        //siguienteButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                aceptarModalLinkButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INCLUSION][OBSERVACIONES] Lista de observaciones procesada con exito");

    }

    public void clickAceptarListaObservacionesButton() {
        //aceptarModalAngularButton.click();
    }

    public void processAndAssertSuccessBotonSiguiente(long timeoutMs, long quietMs) {
        aceptarModalLinkButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarModalLinkButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                informacionInclusionLabel,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INCLUSION][ASEGURADO] Botón Siguiente procesado con exito");
    }

    public void processAndAssertSuccessBotonSiguienteVidaLey(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                informacionInclusionLabel,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INCLUSION][ASEGURADO] Botón Siguiente Vida Ley procesado con exito");
    }
    public void processAndAssertSuccessListaDeObservacionesVidaLey(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                procesarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                aceptarModalLinkButton,
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
        log.info("[DECLARACION][OBSERVACIONES] Lista de observaciones Vida Ley procesada con exito");

    }
    public void seleccionarFacturadaVidaLey() {
        clickAndSync(facturadaRadioVidaLeyButton);
        log.info("[INCLUSION][ASEGURADO] Opción No Facturada Vida Ley seleccionada con exito");
    }

    public void processAndAssertSuccessCargaDeAsegurados(long timeoutMs, long quietMs) {
        aceptarModalLinkButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarModalLinkButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                numeroAseguradosCargadosLabel,
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
        ElementAsserts.assertTextMatches(numeroAseguradosCargadosLabel, Pattern.compile(".*Se ha cargado un total de\\s+\\d+\\s+trabajador\\(es\\).*"),"Error al cargar trabajadores");
        log.info("[DECLARACION][CARGA DE ASEGURADO] Carga de asegurados cargada con exito");

    }

    public void assertLoadedImportarPlanillaMineria() {
        ElementAsserts.assertVisible(iImportarPlanillaInput, "Campo de importación de planilla DEBE SER VISIBLE");
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[INCLUSION][ASEGURADO SCTR MINERI] Seccion de importación de planilla cargada con éxito");
    }

    public void fillFormDatosDeLosAseguradosSctrMineria(Path template) {
        iImportarPlanillaInput.setInputFiles(template);
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(2000);
        ElementAsserts.assertVisible(planillaCargadaExitosamenteMessage, "Mensaje de planilla cargada exitosamente DEBE SER VISIBLE");
    }

    public void processAndAssertSuccessProcesarPlanillaAseguradoMineria(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(procesarButton, "Botón Procesar DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                procesarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                listaAseguradosContainer,
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
        log.info("[INCLUSION][CARGA DE ASEGURADO MINERIA] Se visualiza la seccion con la lista de asegurados");
    }

    public void validarAseguradosCargadosSctrMineria(String numeroDocumento) {
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(2000);
        ElementAsserts.assertVisible(listaAseguradosContainer, "Sección con la lista de asegurados DEBE SER VISIBLE");
        Locator numeroDocumentoPlanillaLabel = listaAseguradosContainer.locator("li.tbl-item").filter(new Locator.FilterOptions().setHasText(Pattern.compile(numeroDocumento.trim(), Pattern.CASE_INSENSITIVE))).first();
        ElementAsserts.assertContainsText(numeroDocumentoPlanillaLabel,numeroDocumento.trim(),"Numero de documento " + numeroDocumento + " debe estar presente en la planilla cargada");
        log.info("[DECLARACION][ASEGURADO] Planilla cargada con exito con el numero de documento: {}", numeroDocumento);
    }

    public void processAndAssertSuccessBotonSiguienteMineria(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(siguienteButton, "Botón Siguiente DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                aceptarModalAngularButton,
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
        log.info("[INCLUSION][CARGA DE ASEGURADO MINERIA] Se visualiza la seccion con la lista de asegurados");
    }

    public void processAndAssertSuccessListaDeObservacionesMineria(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(aceptarModalAngularButton, "Botón Aceptar dentro del modal Lista de Observaciones DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarModalAngularButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                informacionInclusionLabel,
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
        log.info("[INCLUSION][CARGA DE ASEGURADO MINERIA] Se procesó el modal de Lista de Observaciones con exito");
    }
}