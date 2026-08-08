package com.mapfre.playwright.pageobjects.poliza.decesos.consultaCotizaciones;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.asserts.ElementAsserts;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class DocumentosDecesosPage extends BasePage{
    private final Locator title;
    private final Locator cotizadasButton;
    private final Locator gestorInput;
    private final Locator AgenteInput;
    private final Locator numeroDocumentoDNIInput;
    private final Locator numeroCotizacionInput;
    private final Locator filtrarButton;
    private final Locator fechaInicioInput;
    private final Locator fechaFinInput;
    private final Locator resultadoNoEncontradoMensaje;
    private final Locator filasResultados;
    private final Locator emitidasProvisionalButton;
    private final Locator numeroPolizaResultado;

    public DocumentosDecesosPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Documentos decesos"));
        this.cotizadasButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cotizadas"));
        this.gestorInput = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Gestor"));
        this.AgenteInput =  page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Agente"));
        this.numeroDocumentoDNIInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("DNI"));
        this.numeroCotizacionInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de Cotización"));
        this.filtrarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("FILTRAR"));
        this.fechaInicioInput = page.locator("oim-datepicker[name='mDesde']").locator("input");
        this.fechaFinInput = page.locator("oim-datepicker[name='mHasta']").locator("input");
        this.resultadoNoEncontradoMensaje = page.getByText("No hay resultados para los filtros escogidos");
        this.filasResultados = page.locator("div.g-myd-result");
        this.emitidasProvisionalButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Emitidas Provisional"));
        this.numeroPolizaResultado = page.locator("li.cnt-item:has(div.item-label:has-text('Nro de Póliza:')) div.item-dato, li.cnt-item:has(div.item-label:has-text('Nro de Poliza:')) div.item-dato").first();

    }

    /**
     * Verifica que la página de documentos de decesos haya cargado correctamente
     * validando que el título y todos los campos de filtro sean visibles.
     */
    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
        ElementAsserts.assertVisible(gestorInput, "TITULO Gestor DEBE SER VISIBLE");
        ElementAsserts.assertVisible(AgenteInput, "TITULO Agente DEBE SER VISIBLE");
        ElementAsserts.assertVisible(numeroDocumentoDNIInput, "TITULO Numero de Documento DNI DEBE SER VISIBLE");
        ElementAsserts.assertVisible(numeroCotizacionInput, "TITULO Numero de Cotizacion DEBE SER VISIBLE");
    }
    /**
     * Completa el formulario de opciones de búsqueda
     * haciendo clic en el botón de cotizadas y rellenando las fechas de inicio y fin.
     *
     * @param fechaInicio la fecha de inicio del rango de búsqueda
     * @param fechaFin la fecha de fin del rango de búsqueda
     */
    public void fillFormOpcionesDeBusqueda(String fechaInicio, String fechaFin) {
        clickAndSync(cotizadasButton);
        fillReadonlyDate(fechaInicioInput, fechaInicio);
        fillReadonlyDate(fechaFinInput, fechaFin);
        log.info("[DECESOS][FORMULARIO] Formulario de busqueda completado");
    }

    /**
     * Procesa la búsqueda de documentos de decesos y valida que sea exitosa.
     * Espera a que aparezcan los resultados o el mensaje de no encontrado,
     * verificando que el filtrado se haya realizado correctamente.
     *
     * @param timeoutMs tiempo máximo de espera en milisegundos
     * @param quietMs tiempo de estabilidad para confirmar la aparición del elemento
     */
    public void processAndAssertSuccessBusqueda(long timeoutMs, long quietMs) {
        filtrarButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        scrollToTop();
        log.info("[DECESOS][BUSQUEDA] Iniciando proceso de filtrado y validación de resultados.");
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
    public void assertNumeroPolizaEmitidaVisible() {
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(1000);
        if (emitidasProvisionalButton.isVisible()) {
            clickAndSync(emitidasProvisionalButton);
        }
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        ElementAsserts.assertVisible(filasResultados.first(), "DEBE EXISTIR AL MENOS UN RESULTADO EN DOCUMENTOS DECESOS");
        ElementAsserts.assertVisible(numeroPolizaResultado, "EL NUMERO DE POLIZA EMITIDA DEBE SER VISIBLE");
        ElementAsserts.assertFilled(numeroPolizaResultado, "EL NUMERO DE POLIZA EMITIDA DEBE ESTAR COMPLETADO");
        String numeroPoliza = numeroPolizaResultado.innerText().trim();
        log.info("[DECESOS][DOCUMENTOS] Número de póliza emitida encontrado: {}", numeroPoliza);
    }
}
