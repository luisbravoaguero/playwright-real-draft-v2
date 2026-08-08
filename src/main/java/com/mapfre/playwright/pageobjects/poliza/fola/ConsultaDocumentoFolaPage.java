package com.mapfre.playwright.pageobjects.poliza.fola;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ConsultaDocumentoFolaPage extends BasePage {
    private Locator title;
    private Locator fechaInicioInput;
    private Locator fechaFinInput;
    private Locator filtrarButton;
    private Locator resultadoNoEncontradoMensaje;
    private Locator filasResultados;

    public ConsultaDocumentoFolaPage(Page page) {
        super(page);
        this.title= page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Consultar Documentos"));
        this.fechaInicioInput = page.locator("oim-datepicker[formcontrolname='fechaInicial']").locator("input");
        this.fechaFinInput = page.locator("oim-datepicker[formcontrolname='fechaFinal']").locator("input");
        this.filtrarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Filtrar "));
        this.resultadoNoEncontradoMensaje= page.getByText("No hay resultados para los filtros escogidos");
        this.filasResultados = page.locator("polizas-fola-documento-item");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO CONSULTA DOCUMENTOS DE FOLA DEBE SER VISIBLE");
        ElementAsserts.assertVisible(filtrarButton, "El botón Buscar debe ser visible");
    }

    public void fillFormDocumentosFola(String fechaInicio, String fechaFin) {
        fillReadonlyDate(fechaInicioInput, fechaInicio);
        fillReadonlyDate(fechaFinInput, fechaFin);
        clickAndSync(filtrarButton);
    }
    public void processAndAssertSuccessBusqueda(long timeoutMs, long quietMs) {
        filtrarButton.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
        scrollToTop();
        var result = com.mapfre.utils.waits.FirstAppearanceRace.waitForFirst(
                page,
                filtrarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoNoEncontradoMensaje,
                filasResultados.first(),
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == com.mapfre.utils.waits.FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoNoEncontradoMensaje.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == com.mapfre.utils.waits.FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs + " ms");
        }
    }
}
