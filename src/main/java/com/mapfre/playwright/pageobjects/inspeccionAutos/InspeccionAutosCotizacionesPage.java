package com.mapfre.playwright.pageobjects.inspeccionAutos;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.components.material.MaterialDatePicker;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DateUtils;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class InspeccionAutosCotizacionesPage extends BasePage {
    private final Locator title;
    private final Locator numeroCotizacionInput;
    private final Locator filtrarButton;
    private final Locator busquedaNoEncontrada;
    private final Locator resultadoList;
    private final Locator modalErrorGeneral;
    private final Locator detalleCotizacionTitle;
    private final MaterialDatePicker fechaDesdeDatePicker;
    private final MaterialDatePicker fechaHastaDatePicker;
    private final Locator paginadorSiguienteButton;
    public InspeccionAutosCotizacionesPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotizaciones"));
        this.numeroCotizacionInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nro. Cotización"));
        this.filtrarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Filtrar"));
        this.busquedaNoEncontrada = page.getByText("No hay resultados para los filtros escogidos");
        this.resultadoList = page.locator("inspec-estimations-list div.g-box");
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.detalleCotizacionTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Detalle cotización"));
        this.fechaDesdeDatePicker = new MaterialDatePicker(page, page.locator("oim-datepicker[label='Desde']"), "Desde");
        this.fechaHastaDatePicker = new MaterialDatePicker(page, page.locator("oim-datepicker[label='Hasta']"), "Hasta");
        //this.paginadorSiguienteButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Siguiente página")).first();
        this.paginadorSiguienteButton = page.locator("button[aria-label='Siguiente página']").first();
    }

    public void assertLoadedCotizaciones() {
        ElementAsserts.assertVisible(title, "TITULO Cotizaciones DEBE SER VISIBLE");
        waitRandomBetween(2000);
        log.info("[INSPECCION AUTOS][MODULO COTIZACIONES] La página Inspeccion Autos seccion Cotizaciones se cargó correctamente.");
    }
    public void seleccionarFechaDesde(String fecha) {
        fechaDesdeDatePicker.seleccionarFecha(fecha);
    }

    public void seleccionarFechaHasta(String fecha) {
        fechaHastaDatePicker.seleccionarFecha(fecha);
    }
    public void fillNumeroCotizacion(String numeroCotizacion, String fechaInicioCotizacion, String fechaFinCotizacion) {
        fillAndSync(numeroCotizacionInput, numeroCotizacion.trim());
        fechaDesdeDatePicker.seleccionarFecha(fechaInicioCotizacion);
        fechaHastaDatePicker.seleccionarFecha(fechaFinCotizacion);
        log.info("[INSPECCION AUTOS][BUSQUEDA COTIZACION] Se ingresó el número de cotización {}, fecha inicio cotización {} y fecha fin cotización {}", numeroCotizacion, fechaInicioCotizacion, fechaFinCotizacion);
    }
    public void fillNumeroCotizacion(String numeroCotizacion) {
        pressSequentiallyAndBlurSync(numeroCotizacionInput, numeroCotizacion.trim());
        waitRandomBetween(700);
        log.info("[INSPECCION AUTOS][BUSQUEDA COTIZACION] Se ingresó el número de cotización {}", numeroCotizacion);
    }

    public void fillRangoFechasPorDefecto() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate yesterday = tomorrow.minusMonths(1);
        String rangoFechaInicio = DateUtils.formatLocalDateToStringDdMmYyyy(yesterday);
        String rangoFechaFin = DateUtils.formatLocalDateToStringDdMmYyyy(tomorrow);
        fechaDesdeDatePicker.seleccionarFecha(rangoFechaInicio);
        waitRandomBetween(1500);
        dismissAnyOverlay();
        fechaHastaDatePicker.seleccionarFecha(rangoFechaFin);
        waitRandomBetween(1500);
        dismissAnyOverlay();
        log.info("[INSPECCION AUTOS][BUSQUEDA COTIZACION] Se ingresó el rango de fechas por defecto: fecha inicio cotización {} y fecha fin cotización {}", rangoFechaInicio, rangoFechaFin);
    }

    public void clickBotonFiltrar() {
        clickAndSync(filtrarButton);
        waitRandomBetween(2000);
        log.info("[INSPECCION AUTOS][BUSQUEDA COTIZACION] Se hizo click en el botón FILTRAR");
    }

    public void processAndAssertSuccessBusquedaNumeroCotizacion(long timeoutMs, long quietMs) {
        //ElementAsserts.assertVisible(filtrarButton, "El botón FILTRAR debe ser visible antes de hacer click.");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,// action (coloca null si la accion click fue realizada)
                busquedaNoEncontrada,// coloca el locator de error
                resultadoList.first(),// coloca el locator de éxito
                null,// coloca null si el mensaje del error esta en busquedaNoEncontrada
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
        if (result.outcome() == FirstAppearanceRace.Outcome.SUCCESS) {
            log.info("[INSPECCION AUTOS][BUSQUEDA COTIZACION] Resultado de la búsqueda por número de cotización: {} al menos un resultado apareció (después de {} ms)", result.outcome(), result.elapsedMs());
        } else {
            log.info("[INSPECCION AUTOS][BUSQUEDA COTIZACION] Resultado de la búsqueda por número de cotización, al menos un resultado apareció: {}", result.outcome());
        }
    }

    public void encontrarNumeroCotizacionEnResultados(String numeroCotizacion) {
        int pagina = 1;

        while (true) {
            esperarResultadosVisibles();

            int totalResultados = resultadoList.count();
            log.info("[INSPECCION AUTOS][BUSQUEDA COTIZACION] Revisando página {} con {} resultados.", pagina, totalResultados);

            for (int i = 0; i < totalResultados; i++) {
                String textoCard = resultadoList.nth(i).innerText();

                if (textoCard != null && textoCard.contains(numeroCotizacion)) {
                    log.info("[INSPECCION AUTOS][BUSQUEDA COTIZACION] Se encontró la cotización {} en la página {}, fila {}.", numeroCotizacion, pagina, i + 1);

                    return;
                }
            }

            if (!paginadorSiguienteButton.isEnabled()) {
                throw new FrameworkException("[INSPECCION AUTOS][BUSQUEDA COTIZACION] No se encontró la cotización " + numeroCotizacion + " después de revisar todas las páginas.");
            }

            String primerResultadoAntes = totalResultados > 0 ? resultadoList.first().innerText() : "";

            log.info("[INSPECCION AUTOS][BUSQUEDA COTIZACION] La cotización {} no está en la página {}. Avanzando a la siguiente.", numeroCotizacion, pagina);

            clickAndSync(paginadorSiguienteButton);
            esperarCambioDeResultados(primerResultadoAntes);

            pagina++;
        }
    }

    private void esperarResultadosVisibles() {
        resultadoList.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    private void esperarCambioDeResultados(String primerResultadoAntes) {
        int intentos = 20;

        for (int i = 0; i < intentos; i++) {
            if (resultadoList.count() > 0) {
                String textoActual = resultadoList.first().innerText();
                if (textoActual != null && !textoActual.equals(primerResultadoAntes)) {
                    return;
                }
            }
            page.waitForTimeout(500);
        }

        throw new FrameworkException("[INSPECCION AUTOS][BUSQUEDA COTIZACION] La lista de resultados no cambió después de avanzar de página.");
    }

    public void seleccionarVerCotizacion(String numeroCotizacion) {
        Locator targetCard = resultadoList.filter(new Locator.FilterOptions().setHasText(numeroCotizacion)).first();
        ElementAsserts.assertVisible(targetCard, "La cotización " + numeroCotizacion + " debe ser visible");
        Locator verCotizacionButton = targetCard.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName(Pattern.compile("VER COTIZACIÓN")));
        clickAndSync(verCotizacionButton);
        log.info("[INSPECCION AUTOS][BUSQUEDA COTIZACION] Se seleccionó VER COTIZACIÓN para el número de cotización {} ", numeroCotizacion);
    }

    public void processAndAssertSuccessSeleccionarVerCotizacion(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                modalErrorGeneral,
                detalleCotizacionTitle,
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
        if (result.outcome() == FirstAppearanceRace.Outcome.SUCCESS) {
            log.info("[INSPECCION AUTOS][BUSQUEDA COTIZACION] Resultado de la selección VER COTIZACIÓN: {} (después de {} ms)", result.outcome(), result.elapsedMs());
        } else {
            log.info("[INSPECCION AUTOS][BUSQUEDA COTIZACION] Resultado de la selección VER COTIZACIÓN: {}", result.outcome());
        }
    }


}
