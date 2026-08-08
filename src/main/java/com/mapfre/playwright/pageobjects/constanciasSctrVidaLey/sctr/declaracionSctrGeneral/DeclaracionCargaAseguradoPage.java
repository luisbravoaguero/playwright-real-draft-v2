package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.asserts.ElementAsserts;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.regex.Pattern;


public class DeclaracionCargaAseguradoPage extends BasePage {
    private final Locator titleDetallePolizasLabel;
    private final Locator titleDeclaracionLabel;
    private final Locator policyCards;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombreCompletoInput;
    private final Locator fechaNacimientoInput;
    private final Locator sueldoInput;
    private final Locator cargaIndividualButton;
    private final Locator siguienteButton;
    private final Locator aceptarModalLinkButton;
    private final Locator informacionDeclaracionLabel;
    private final Locator resultadoModalMensajeError;
    private final Locator ocupacionInput;
    private final Locator tipoRiesgoSelect;
    private final Locator lugarExposicionInput;
    private final Locator sexoSelect;
    private final Locator procesarButton;
    private final Locator numeroAseguradosCargadosLabel;
    private final Locator titleInformacionDeclaracionLabel;
    private final Locator siguienteGButton;
    public DeclaracionCargaAseguradoPage(Page page) {
        super(page);
        this.titleDetallePolizasLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Detalles de Pólizas").setLevel(2));
        this.titleDeclaracionLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Declaración").setLevel(1));
        this.titleInformacionDeclaracionLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Información de Declaración").setLevel(2));
        this.policyCards = page.locator("div.g-box").filter(new Locator.FilterOptions().setHasText("Póliza"));
        this.tipoDocumentoSelect = page.locator("oim-select[label='Tipo de documento'] select");
        this.numeroDocumentoInput = page.locator("oim-input[label='Nro. Documento'] input");
        this.nombreCompletoInput = page.locator("oim-input[label='Nombre Completo'] input");
        this.ocupacionInput = page.locator("oim-input[label='Ocupación'] input");
        this.tipoRiesgoSelect = page.locator("oim-select[label='Tipo de riesgo'] select");
        this.lugarExposicionInput = page.locator("oim-autocomplete[label='Lugar de Exposicion'] input");
        this.sexoSelect = page.locator("oim-select[label='Sexo'] select");
        this.fechaNacimientoInput = page.locator("oim-datepicker[label='Fecha Nacimiento']").locator("input");
        this.sueldoInput = page.locator("oim-input[label='Sueldo'] input");
        this.cargaIndividualButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Cargar individual")));
        this.siguienteButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Siguiente")));
        this.aceptarModalLinkButton = page.locator("a.g-button.g-button--second-design").filter(new Locator.FilterOptions().setHasText("Aceptar"));
        this.informacionDeclaracionLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Información de Declaración").setLevel(2));
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.procesarButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Procesar"));
        this.numeroAseguradosCargadosLabel = page.locator(".gH2 b");
        this.siguienteGButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Siguiente"));
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(titleDetallePolizasLabel, "TITULO Detalles de Pólizas DEBE SER VISIBLE");
    }
    public void assertDeclaracionLoaded() {
        ElementAsserts.assertVisible(titleDeclaracionLabel, "TITULO Declaracion DEBE SER VISIBLE");
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
                log.info("[DECLARACION][ASEGURADO] Póliza de Pensión completada - Trabajadores: 1, Monto: 3000");
            }
            if (headerText.contains("Salud")) {
                fillPolicy(card, "1", "3000");
                log.info("[DECLARACION][ASEGURADO] Póliza de Salud completada - Trabajadores: 1, Monto: 3000");
            }
        }
    }

    public void fillAseguradosCargaIndividual() {
        clickAndSync(cargaIndividualButton);
        //datos en duro para la carga individual, se pueden parametrizar si se desea
        selectAndSync(tipoDocumentoSelect, "DNI");
        fillAndSync(numeroDocumentoInput, "94646546");
        fillAndSync(nombreCompletoInput, "JUAN PEREZ ROJAS");
        fillReadonlyDate(fechaNacimientoInput, "01/01/1990");
        fillAndSync(sueldoInput, "3000");
        //clickAndSync(siguienteButton);
        log.info("[DECLARACION][ASEGURADO] Asegurado cargado con exito");

    }

    public void fillAseguradosVidaLeyCargaIndividual() {
        clickAndSync(cargaIndividualButton);
        //datos en duro para la carga individual, se pueden parametrizar si se desea
        selectAndSync(tipoDocumentoSelect, "DNI");
        fillAndSync(numeroDocumentoInput, "94646582");
        fillAndSync(nombreCompletoInput, "JUAN PEREZ ROJAS");
        fillReadonlyDate(fechaNacimientoInput, "01/01/1990");
        fillAndSync(ocupacionInput, "INGENIERO");
        fillAndSync(sueldoInput, "3000");
        selectAndSync(tipoRiesgoSelect,"EMPLEADOS");
        selectFromMatAutocompleteFailFast(lugarExposicionInput,"010101","Lugar de Exposición");
        assertAutocompleteSelected(lugarExposicionInput,"010101","Lugar de Exposición");
        selectAndSync(sexoSelect,"MASCULINO");
        //clickAndSync(siguienteButton);
        log.info("[DECLARACION][ASEGURADO] Formulario de la carga individual Vida Ley con exito");

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

    public void processAndAssertSuccessListaDeObservaciones(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizada)
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
        log.info("[DECLARACION][OBSERVACIONES] Lista de observaciones procesada con exito");

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

    public void clickAceptarListaObservacionesButton() {
        //clickAndSync(aceptarModalLinkButton);
    }

    public void processAndAssertSuccessBotonGenerar(long timeoutMs, long quietMs) {
        aceptarModalLinkButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarModalLinkButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                informacionDeclaracionLabel,
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
        log.info("[DECLARACION - INCLUSION - CONSTANCIA MANUAL][GENERAR_CONSTANCIA] Constancia generada con exito");

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

    public void processAndAssertSuccessBotonSiguiente(long timeoutMs, long quietMs) {
        siguienteGButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteGButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                titleInformacionDeclaracionLabel,
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
    }
}
