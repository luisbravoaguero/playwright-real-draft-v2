package com.mapfre.playwright.pageobjects.poliza.riesgosGenerales;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class ConsultaDocumentoRiesgosGeneralesPage extends BasePage {
    private Locator fechaInicioInput;
    private Locator fechaFinInput;
    private Locator filtrarButton;
    private Locator title;
    private Locator numeroCotizacionInput;
    private Locator resultadoNoEncontradoMensaje;
    private Locator filasResultados;

    public ConsultaDocumentoRiesgosGeneralesPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Bandeja de Riesgos generales"));
        this.numeroCotizacionInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de cotización"));
        this.fechaInicioInput = page.locator("oim-datepicker[name='nFechaInicial']").locator("input");
        this.fechaFinInput = page.locator("oim-datepicker[name='mFechaFinal']").locator("input");
        this.filtrarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Filtrar"));
        this.resultadoNoEncontradoMensaje = page.getByText("No hay resultados para los filtros escogidos");
        this.filasResultados = page.locator("div.g-box.g-overflow-hidden-xs.mb-xs-2");

    }
    public void assertLoaded(){
        ElementAsserts.assertVisible(title, "TITULO CONSULTA DOCUMENTOS DE RIESGOS GENERALES DEBE SER VISIBLE");
        ElementAsserts.assertVisible(numeroCotizacionInput,"TITULO Numero de Poliza DEBE SER VISIBLE");
    }
    public void fillFormDocumentosRiesgosGenerales(String fechaInicio, String fechaFin) {
      fillReadonlyDate(fechaInicioInput, fechaInicio);
      fillReadonlyDate(fechaFinInput, fechaFin);
      clickAndSync(filtrarButton);
    }
    public void processAndAssertSuccessBusqueda(long timeoutMs, long quietMs) {
        filtrarButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        scrollToTop();
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
        // SUCCESS: continue
    }
}
