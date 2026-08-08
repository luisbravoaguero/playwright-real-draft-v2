package com.mapfre.playwright.pageobjects.poliza.vidaley.consultaDocumento;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.models.RegistroFechaAccidentes;
import com.mapfre.models.RegistroFechaVidaLey;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DateUtils;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class DocumentosVidaLeyPage extends BasePage {
    private final Locator title;
    private final Locator fechaInicioInput;
    private final Locator fechaFinInput;
    private final Locator filtrarButton;
    private final Locator resultadoNoEncontradoMensaje;
    private final Locator filasResultados;
    private final Locator nextButton;
    private final Locator estadoPolizaSelect;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator numeroSolicitudInput;
    public DocumentosVidaLeyPage(com.microsoft.playwright.Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Documentos de Vida Ley"));
        this.fechaInicioInput = page.locator("oim-datepicker[name='nFechaInicial']").locator("input");
        this.fechaFinInput = page.locator("oim-datepicker[name='nFechaFinal']").locator("input");
        this.filtrarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Filtrar"));
        this.filasResultados = page.locator("div.mb-xs-2.ng-scope");
        this.nextButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Siguiente página"));
        this.resultadoNoEncontradoMensaje = page.getByText("No hay resultados para los filtros escogidos");
        this.estadoPolizaSelect = page.locator("oim-select[name='nEstadoFilter']").locator("select");
        this.tipoDocumentoSelect = page.locator("oim-select[name='nTipoDocFilter']").locator("select");
        this.numeroDocumentoInput = page.locator("oim-input[name='nNroDocumentoFilter']").locator("input");
        this.numeroSolicitudInput = page.locator("oim-input[name='nNroCotizacion']").locator("input");

    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Documentos de Vida Ley DEBE SER VISIBLE");
    }

    public void fillFormDocumentoVidaLey(String estado_poliza, String tipo_documento, String numero_documento, String numero_solicitud, String fecha_inicio, String fecha_fin) {
        if (!isFieldOptional(estado_poliza)) {
            selectAndSync(estadoPolizaSelect, estado_poliza);
        }
        if (!isFieldOptional(tipo_documento)) {
            selectAndSync(tipoDocumentoSelect, tipo_documento);
        }
        if (!isFieldOptional(numero_documento)) {
            fillAndSync(numeroDocumentoInput, numero_documento);
        }
        if (!isFieldOptional(numero_solicitud)) {
            fillAndSync(numeroSolicitudInput, numero_solicitud);
        }
        // Campos obligatorios
        fillReadonlyDate(fechaInicioInput, fecha_inicio);
        fillReadonlyDate(fechaFinInput, fecha_fin);

    }
    public void clickFiltrarButton() {
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
                String numDoc = filaActual.locator(".item-label:has-text('Nro solicitud:') + .item-dato").innerText();
                String fechaTexto = filaActual.locator(".item-label:has-text('Fecha registro:') + .item-dato").innerText().trim();

                RegistroFechaVidaLey registro = new RegistroFechaVidaLey(numDoc, fechaTexto);
                LocalDateTime dateB = DateUtils.parseToLocalDateTime(registro.fechaRegistro());
                boolean isInRange = !dateB.isBefore(fechaA) && !dateB.isAfter(fechaC);

                if (!isInRange) {
                    throw new AssertExceptions(
                            "¡Prueba Fallida! La solicitud está fuera de rango.\n" +
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