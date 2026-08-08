package com.mapfre.playwright.pageobjects.inspeccionAutos;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.regex.Pattern;

public class InspeccionAutosProgramacionesPage extends BasePage {
    private final Locator breadcrumbsTitle;
    private final Locator programacionesLink;
    private final Locator numeroSolicitudInput;
    private final Locator estadoSelect;
    private final Locator resultList;
    private final Locator resultadoNoEncontradoMensaje;
    private final Locator filtrarButton;
    private final Locator programacionesTitle;
    private final Locator programacionesBreadcrumbsLink;
    private final Locator programacionesH1H2H3Title;
    private final Locator modalErrorGeneral;
    private final Locator detalleSolicitudBreadcrumbs;
    private final Locator paginadorSiguienteButton;
    public InspeccionAutosProgramacionesPage(Page page) {
        super(page);
        this.breadcrumbsTitle = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Inspección de Autos >"));
        this.programacionesLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Programaciones")).nth(1);
        this.numeroSolicitudInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nro. Solicitud"));
        this.estadoSelect = page.locator("label:has-text('Estado')").locator("..").locator("select");
        this.resultList = page.locator("inspec-schedules-list div.g-box");
        this.resultadoNoEncontradoMensaje = page.getByText("No hay resultados para los filtros escogidos");
        this.filtrarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("FILTRAR"));
        this.programacionesTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Programaciones"));
        this.programacionesBreadcrumbsLink = page.getByRole(AriaRole.LISTITEM).filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Programaciones$")));
        this.programacionesH1H2H3Title = page.locator("h1, h2, h3").filter(new Locator.FilterOptions().setHasText("Programaciones")).first();
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.detalleSolicitudBreadcrumbs = page.getByRole(AriaRole.LISTITEM).filter(new Locator.FilterOptions().setHasText("Detalle Solicitud"));
        this.paginadorSiguienteButton = page.locator("button[aria-label='Siguiente página']").first();
    }

    public void assertLoadedProgramaciones() {
        ElementAsserts.assertVisible(programacionesBreadcrumbsLink, "El titulo de breadcrumbs Programaciones DEBE SER VISIBLE");
        ElementAsserts.assertVisible(programacionesH1H2H3Title, "El titulo Programaciones DEBE SER VISIBLE");
        waitRandomBetween(3500);
        log.info("[INSPECCION AUTOS][BUSCQUEDA PROGRAMACIONES] Página Programaciones de Inspección de Autos cargada correctamente.");
    }

    public void fillFormBuscarProgramacionInspeccion(String numeroNuevaSolicitud) {
        pressSequentiallyAndBlurSync(numeroSolicitudInput, numeroNuevaSolicitud);
        selectAndSync(estadoSelect, "PROGRAMADA");
        log.info("[INSPECCION AUTOS][BUSCQUEDA PROGRAMACIONES] Formulario de búsqueda de programación de inspección llenado con Nro. Solicitud: {} y Estado: PROGRAMADA", numeroNuevaSolicitud);
    }

    public void processAndAssertSuccessBuscarProgramacionInspeccion(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(filtrarButton, "El boton filtrar DEBE SER VISIBLE para procesar la busqueda de solicitud de inspeccion");
        ElementAsserts.assertEnabled(filtrarButton, "El boton filtrar DEBE SER HABILITADO para procesar la busqueda de solicitud de inspeccion");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                filtrarButton::click,               // action (coloca null si la accion click fue realizad)
                resultadoNoEncontradoMensaje,
                resultList.first(),
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );
        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][BUSCQUEDA PROGRAMACIONES] Busqueda de programación de inspección procesada exitosamente.");
    }

    public void encontrarNumeroSolicitudEnResultados(String numeroSolicitud) {
        int pagina = 1;

        while (true) {
            esperarResultadosVisibles();

            int totalResultados = resultList.count();
            log.info("[INSPECCION AUTOS][BUSQUEDA PROGRAMACIONES] Revisando página {} con {} resultados.", pagina, totalResultados);

            for (int i = 0; i < totalResultados; i++) {
                String textoCard = resultList.nth(i).innerText();

                if (textoCard != null && textoCard.contains(numeroSolicitud)) {
                    log.info("[INSPECCION AUTOS][BUSQUEDA PROGRAMACIONES] Se encontró el numero de solicitud {} en la página {}, fila {}.", numeroSolicitud, pagina, i + 1);

                    return;
                }
            }

            if (!paginadorSiguienteButton.isEnabled()) {
                throw new FrameworkException("[INSPECCION AUTOS][BUSQUEDA PROGRAMACIONES] No se encontró el numero de solicitud " + numeroSolicitud + " después de revisar todas las páginas.");
            }

            String primerResultadoAntes = totalResultados > 0 ? resultList.first().innerText() : "";

            log.info("[INSPECCION AUTOS][BUSQUEDA PROGRAMACIONES] El numero de solicitud {} no está en la página {}. Avanzando a la siguiente.", numeroSolicitud, pagina);

            clickAndSync(paginadorSiguienteButton);
            esperarCambioDeResultados(primerResultadoAntes);

            pagina++;
        }
    }

    private void esperarResultadosVisibles() {
        resultList.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    private void esperarCambioDeResultados(String primerResultadoAntes) {
        int intentos = 20;

        for (int i = 0; i < intentos; i++) {
            if (resultList.count() > 0) {
                String textoActual = resultList.first().innerText();
                if (textoActual != null && !textoActual.equals(primerResultadoAntes)) {
                    return;
                }
            }
            page.waitForTimeout(500);
        }
        throw new FrameworkException("[INSPECCION AUTOS][BUSQUEDA PROGRAMACIONES] La lista de resultados no cambió después de avanzar de página.");
    }

    public void clickVerProgramacionInspeccionButton(String numeroNuevaSolicitud) {
        boolean found = false;
        for (int i = 0; i < resultList.count(); i++) {
            Locator solicitudItem = resultList.nth(i);
            Locator numeroSolicitudLocator = solicitudItem.getByText("Nro. " + numeroNuevaSolicitud);
            if (numeroSolicitudLocator.isVisible()) {
                Locator verProgramacionLink = solicitudItem.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("VER PROGRAMACIÓN"));
                clickAndSync(verProgramacionLink);
                log.info("Solicitud de inspeccion con numeroNuevaSolicitud = {} encontrada y se hizo click en el enlace Programar inspeccion", numeroNuevaSolicitud);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new FrameworkException("No se encuentró el numero de solicitud de inspeccion " + numeroNuevaSolicitud + " en los resultados de busqueda.");
        }
    }

    public void processAndAssertSuccessclickVerProgramacionInspeccionButton(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                detalleSolicitudBreadcrumbs,
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );
        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
    }
}
