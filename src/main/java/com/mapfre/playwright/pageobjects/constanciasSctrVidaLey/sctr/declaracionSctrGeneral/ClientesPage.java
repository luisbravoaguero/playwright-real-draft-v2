package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.asserts.ElementAsserts;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class ClientesPage extends BasePage {
    private final Locator title;
    private final Locator numeroPolizaInput;
    private final Locator filtrarButton;
    private final Locator resultadoNoEncontradoMensaje;
    private final Locator filasResultados;
    private final Locator seleccionarResultadoButton;
    private final Locator resultadoModalMensajeError;
    private final Locator perfilClienteHeader;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    public ClientesPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Clientes"));
        this.numeroPolizaInput = page.locator("oim-input[name='nNumPolizaFilter'] input");
        this.filtrarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Filtrar"));
        this.resultadoNoEncontradoMensaje = page.getByText("No hay resultados para los filtros escogidos");
        this.filasResultados = page.locator("div.g-myd-result");
        this.seleccionarResultadoButton = filasResultados.first().locator("a:has-text('Seleccionar')");
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.perfilClienteHeader = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Perfil Cliente"));
        this.tipoDocumentoSelect = page.locator("oim-select[name='nTipoDocFilter'] select");
        this.numeroDocumentoInput = page.locator("oim-input[name='nNroDocumentoFilter'] input");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Clientes DEBE SER VISIBLE");
    }

    public void fillFormDeclaracionSctrPensionSalud(String numero_poliza) {
        fillAndSync(numeroPolizaInput, numero_poliza);
        //clickAndSync(filtrarButton);
        log.info("[DECLARACION - INCLUSION - CONSTANCIA MANUAL][FORMULARIO] Formulario con número de póliza completado con exito");

    }

    public void processAndAssertSuccess(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                filtrarButton::click,               // action (coloca null si la accion click fue realizada)
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
        log.info("[DECLARACION - INCLUSION - CONSTANCIA MANUAL][BUSQUEDA] Búsqueda del numero de poliza completada con exito");
    }

    public void processAndAssertSuccessPerfilCliente(long timeoutMs, long quietMs) {
        filasResultados.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        seleccionarResultadoButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                seleccionarResultadoButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                perfilClienteHeader,
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
        log.info("[DECLARACION - INCLUSION - CONSTANCIA MANUAL][PERFIL CLIENTE] Pagina perfil cliente cargado con exito");
    }

    public void fillFormPorTipoDocumentoPensionSalud(String tipoDocumento, String numeroDocumento) {
        selectAndSync(tipoDocumentoSelect, tipoDocumento);
        fillAndSync(numeroDocumentoInput, numeroDocumento);

    }
}
