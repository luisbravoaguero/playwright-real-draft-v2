package com.mapfre.playwright.pageobjects.poliza.transporte;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.models.RegistroFechaTransporte;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DateUtils;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.time.LocalDateTime;

public class ConsultaDocumentoTransportePage extends BasePage {
    private final Locator title;
    private final Locator fechaInicioInput;
    private final Locator fechaFinInput;
    private final Locator filtrarButton;
    private final Locator filasResultados;
    private final Locator nextButton;
    private final Locator resultadoNoEncontradoMensaje;
    public ConsultaDocumentoTransportePage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Documentos transporte"));
        this.fechaInicioInput = page.locator("oim-datepicker[name='nDesdeFilter']").locator("input");
        this.fechaFinInput = page.locator("oim-datepicker[name='nHastaFilter']").locator("input");
        this.filtrarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Filtrar"));
        this.filasResultados = page.locator("div.g-myd-result");
        this.nextButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Siguiente página"));
        this.resultadoNoEncontradoMensaje = page.getByText("No hay resultados para los filtros escogidos");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Documentos transporte DEBE SER VISIBLE");
    }

    public void fillFormDocumentosTransporte(String fecha_inicio, String fecha_fin) {
        fillReadonlyDate(fechaInicioInput, fecha_inicio);
        fillReadonlyDate(fechaFinInput, fecha_fin);
        clickAndSync(filtrarButton);
    }

    public void processAndAssertSuccess(long timeoutMs, long quietMs) {
        scrollToTop();
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
                String numDoc = filaActual.locator(".item-label:has-text('Nro. documento:') + .item-dato").innerText();
                String fechaTexto = filaActual.locator(".item-label:has-text('Fecha Registro:') + .item-dato").innerText().trim();

                RegistroFechaTransporte registro = new RegistroFechaTransporte(numDoc, fechaTexto);
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

}
