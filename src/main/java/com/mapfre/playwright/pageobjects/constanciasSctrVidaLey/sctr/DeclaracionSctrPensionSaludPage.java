package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.util.regex.Pattern;

public class DeclaracionSctrPensionSaludPage extends BasePage {
    private final Locator title;
    private final Locator numeroPolizaInput;
    private final Locator filtrarButton;
    private final Locator resultadoNoEncontradoMensaje;
    private final Locator filasResultados;
    private final Locator seleccionarResultadoButton;
    private final Locator resultadoModalMensajeError;
    private final Locator perfilClienteHeader;
    private final Locator filasAplicaciones;
    private final Locator saludNoDeclarada;
    private final Locator panelHeaderAplicacionesButton;
    private final Locator panelFooterAplicacionesButton;
    private final Locator declararButton;
    private final Locator modalRecibosPendientes;
    private final Locator aceptarModalButton;
    private final Locator headerDeclaracion;
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
    private final Locator datosObraInput;
    private final Locator generarButton;
    private final Locator modalInfoGenerarDeclaracion;
    private final Locator modal;
    private final Locator modalGenerarButton;
    private final Locator successModal;
    private final Locator successModalButton;
    private final Locator numeroConstanciaLabel;
    public DeclaracionSctrPensionSaludPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Clientes"));
        //this.sctrLink = page.locator("a:has(div.label:text-is('SCTR'))");
        this.numeroPolizaInput = page.locator("oim-input[name='nNumPolizaFilter'] input");
        this.filtrarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Filtrar"));
        this.resultadoNoEncontradoMensaje = page.getByText("No hay resultados para los filtros escogidos");
        this.filasResultados = page.locator("div.g-myd-result");
        this.seleccionarResultadoButton = filasResultados.first().locator("a:has-text('Seleccionar')");
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.perfilClienteHeader = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Perfil Cliente"));
        this.filasAplicaciones = page.locator("div.col-md-12.g-list-sm");
        this.saludNoDeclarada = page.locator("ul:has-text('SALUD'):has-text('NO DECLARADA')").locator("li:has-text('SALUD')").locator("input[type='checkbox']").first();
        this.panelHeaderAplicacionesButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("Colectivo Asegurado")));
        this.panelFooterAplicacionesButton = page.locator("div.gnSecResultsFixed.gBgcGray5.show");
        this.declararButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Declarar"));
        this.modalRecibosPendientes = page.locator("div.mat-mdc-dialog-surface");
        this.aceptarModalButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Aceptar"));
        this.headerDeclaracion = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Declaración"));
        this.policyCards = page.locator("div.g-box").filter(new Locator.FilterOptions().setHasText("Póliza"));
        this.tipoDocumentoSelect = page.locator("oim-select[label='Tipo de documento'] select");
        this.numeroDocumentoInput = page.locator("oim-input[label='Nro. Documento'] input");
        this.nombreCompletoInput = page.locator("oim-input[label='Nombre Completo'] input");
        this.fechaNacimientoInput = page.locator("oim-datepicker[label='Fecha Nacimiento']").locator("input");
        this.sueldoInput = page.locator("oim-input[label='Sueldo'] input");
        this.cargaIndividualButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Cargar individual")));
        this.siguienteButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Siguiente")));
        this.aceptarModalLinkButton = page.locator("a.g-button.g-button--second-design").filter(new Locator.FilterOptions().setHasText("Aceptar"));
        this.informacionDeclaracionLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Información de Declaración").setLevel(2));
        this.datosObraInput = page.locator("oim-text-area[name='nDatosObra'] textarea");
        this.generarButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Generar"));
        this.modalInfoGenerarDeclaracion = page.locator("div.swal2-popup.swal2-icon-info[role='dialog']");
        this.modal = page.getByRole(AriaRole.DIALOG, new Page.GetByRoleOptions().setName("GENERAR DECLARACIÓN"));
        this.modalGenerarButton = modal.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("GENERAR"));
        this.successModal = page.locator("div.swal2-popup.swal2-icon-success");
        this.successModalButton = successModal.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("OK"));
        this.numeroConstanciaLabel = page.locator("b").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Nro\\. MP/")));
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Clientes DEBE SER VISIBLE");
    }

    public void fillFormDeclaracionSctrPensionSalud(String numero_poliza) {
        fillAndSync(numeroPolizaInput, numero_poliza);
        clickAndSync(filtrarButton);
    }

    public void processAndAssertSuccess(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoNoEncontradoMensaje,
                filasResultados.first(),
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

    public void clickSeleccionarButton() {
        filasResultados.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        clickAndSync(seleccionarResultadoButton);
    }

    public void processAndAssertSuccessPerfilCliente(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                perfilClienteHeader,
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

    public void seleccionarAplicacionConEstadoNoDeclaradaCheckBox() {
        clickAndSync(panelHeaderAplicacionesButton);
        filasAplicaciones.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        if(saludNoDeclarada.isChecked()){
            System.out.println("El checkbox de salud con estado NO DECLARADA ya está seleccionado");
        }else{
            clickAndSync(saludNoDeclarada);
            System.out.println("Se seleccionó el checkbox de salud con estado NO DECLARADA");
        }
    }

    public void seleccionarDeclararAplicacionButton() {
        clickAndSync(panelFooterAplicacionesButton);
        clickAndSync(declararButton);
    }

    public void processAndAssertSuccessRecibosPendientes(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                aceptarModalButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + aceptarModalButton.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
    }

    public void clickAceptarRecibosPendientesButton() {
        clickAndSync(aceptarModalButton);
    }

    public void processAndAssertSuccessDeclaracionButton(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                headerDeclaracion,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + headerDeclaracion.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
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
            }
            if (headerText.contains("Salud")) {
                fillPolicy(card, "1", "3000");
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
        // SUCCESS: continue
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
        // SUCCESS: continue
    }

    public void fillDatosObra(String datos_obra) {
        fillAndSync(datosObraInput, datos_obra);
    }

    public void clickGenerarButton() {
        clickAndSync(generarButton);
    }
    public void processAndAssertSuccessProcesarBotonGenerar(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                modal,
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
        // SUCCESS: continue
    }

    public void clickGenerarModalButton() {
        //clickAndSync(modalGenerarButton);
    }
    public void processAndAssertSuccessProcesarBotonGenerarconExito(long timeoutMs, long quietMs) {
        modal.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        modalGenerarButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                modalGenerarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                successModal,
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
        // SUCCESS: continue
    }

    public void processAndAssertSuccessProcesarDeclaracionPantallaFinal(long timeoutMs, long quietMs) {
        successModal.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        successModalButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                successModalButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                numeroConstanciaLabel,
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
        // SUCCESS: continue

    }
}
