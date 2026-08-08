package com.mapfre.playwright.pageobjects.poliza.auto.emsion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CotizacionesAutoPage extends BasePage {

    private final Locator title;
    private final Locator buscarCotizacionesButton;
    private final Locator resultadoNoEncontradoMensaje;
    private final Locator resultadoNoEncontradoIncono;
    private final Locator fechaInicioInput;
    private final Locator fechaFinInput;
    private final Locator filasResultados;
    private final Locator nextButton;
    private final Locator titleNextPage;
        private final Locator nroCotizacion;
    private final Locator emitirPolizaButton;

    public CotizacionesAutoPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotizaciones auto"));
        this.buscarCotizacionesButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Buscar Cotizaciones"));
        this.resultadoNoEncontradoMensaje = page.getByText("No hay cotizaciones en la");
        this.resultadoNoEncontradoIncono = page.locator(".ico-mapfre_302_error");
        this.fechaInicioInput = page.locator("oim-datepicker[name='nConsultaDesde']").locator("input");
        this.fechaFinInput = page.locator("oim-datepicker[name='nConsultaHasta']").locator("input");
        this.filasResultados = page.locator("div.g-myd-result");
        this.nextButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Siguiente página"));
        this.titleNextPage = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotización guardada de autos"));
        this.nroCotizacion = page.locator("div.producto-documento")
                .getByText("Nro. Cotización:")
                .locator("xpath=following-sibling::span")
                .locator("b");
        this.emitirPolizaButton = page.getByText("Emitir póliza");

    }
    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Cotizaciones auto de auto DEBE SER VISIBLE");
    }

    public void fillFormCotizacionesAuto(String fecha_inicio, String fecha_fin){
        fillReadonlyDate(fechaInicioInput, fecha_inicio);
        fillReadonlyDate(fechaFinInput, fecha_fin);
        clickAndSync(buscarCotizacionesButton);
    }

    public void processAndAssertSuccess(long timeoutMs, long quietMs){
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                resultadoNoEncontradoMensaje,
                filasResultados.first(),
                resultadoNoEncontradoIncono,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoNoEncontradoMensaje.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
    }

    public void clickVerCotizacion(String numeroCotizacion) {
        while (true) {
            filasResultados.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            for (int i = 0; i < filasResultados.count(); i++) {
                Locator filaActual = filasResultados.nth(i);
                String numDoc = filaActual.locator(".item-label:has-text('Nro. cotización:') + .item-dato").innerText().trim();
                Locator verCotizacionButton = filaActual.locator(".g-button:has-text('Ver cotización')");
                if (numDoc.contains(numeroCotizacion)) {
                    clickAndSync(verCotizacionButton);
                    System.out.println("Validación completada: Se encontró el numero de cotizacion y se hizo click en el boton ver cotización '"+numDoc+"' and '"+i+"'");
                    return; // Termina inmediatamente: encontrado
                }
            }
            if (!nextButton.isEnabled()) {
                throw new AssertExceptions("Prueba Fallida! El numero de cotizacion '" + numeroCotizacion + "' no fue encontrado");
            }
            clickAndSync(nextButton);
        }

    }

    public void processAndAssertVerCotizacionSuccess(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                resultadoNoEncontradoMensaje,
                titleNextPage,
                resultadoNoEncontradoIncono,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoNoEncontradoMensaje.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
    }

    public void assertCotizacionNumberLoaded() {
        ElementAsserts.assertVisible(nroCotizacion, "El número de cotización no apareció.");
        String numero = nroCotizacion.innerText().trim();
        log.info("Número de cotización capturado: {}", numero);
    }

    public void clickEmitirPolizaAuto() {
        clickAndSync(emitirPolizaButton);
        ElementAsserts.assertVisible(emitirPolizaButton,"Boton emitir poliza visible");
    }

    public void buscarCotizacionGenerada(String numeroCotizacion, String fechaInicio, String fechaFin) {

        assertLoaded();
        fillFormCotizacionesAuto(fechaInicio, fechaFin);
        processAndAssertSuccess(15000, 500);
        clickVerCotizacion(numeroCotizacion);
        processAndAssertVerCotizacionSuccess(15000, 500);
    }

}
