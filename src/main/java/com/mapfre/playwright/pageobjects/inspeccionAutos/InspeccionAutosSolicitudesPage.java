package com.mapfre.playwright.pageobjects.inspeccionAutos;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class InspeccionAutosSolicitudesPage extends BasePage {
    private final Locator solicitudesH1H2H3Title;
    private final Locator numeroSolicitudInput;
    private final Locator estadoSelect;
    private final Locator filtrarButton;
    private final Locator resultadoNoEncontradoMensaje;
    private final Locator resultList;
    private final Locator paginadorSiguienteButton;
    private final Locator modalErrorGeneral;
    private final Locator detalleDeSolicitudBreadcrumbs;
    public InspeccionAutosSolicitudesPage(Page page) {
        super(page);
        this.solicitudesH1H2H3Title = page.locator("h1, h2, h3").filter(new Locator.FilterOptions().setHasText("Solicitudes")).first();
        this.numeroSolicitudInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nro. Solicitud"));
        this.estadoSelect = page.locator("label:has-text('Estado')").locator("..").locator("select");
        this.filtrarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("FILTRAR"));
        this.resultadoNoEncontradoMensaje = page.getByText("No hay resultados para los filtros escogidos");
        this.resultList = page.locator("inspec-requests-list div.g-box");
        this.paginadorSiguienteButton = page.locator("button[aria-label='Siguiente página']").first();
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.detalleDeSolicitudBreadcrumbs = page.getByRole(AriaRole.LISTITEM).filter(new Locator.FilterOptions().setHasText("Detalle Solicitud"));

    }

    public void assertLoadedSolicitudes() {
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(2000);
        ElementAsserts.assertVisible(solicitudesH1H2H3Title, "El titulo Solicitudes DEBE SER VISIBLE");
        waitRandomBetween(3500);
    }

    public void fillFormBuscarSolicitudInspeccion(String numeroNuevaSolicitud) {
        pressSequentiallyAndBlurSync(numeroSolicitudInput, numeroNuevaSolicitud);
        selectAndSync(estadoSelect, "POR PROGRAMAR");
        log.info("[INSPECCION AUTOS][SOLICITUDES] Formulario de búsqueda de solicitud de inspección llenado con Nro. Solicitud: {} y Estado: POR PROGRAMAR", numeroNuevaSolicitud);
    }

    public void processAndAssertSuccessBuscarSolicitudInspeccion(long timeoutMs, long quietMs) {
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
        log.info("[INSPECCION AUTOS][SOLICITUDES] Busqueda de solicitud de inspección procesada exitosamente.");
    }

    public void encontrarNumeroSolicitudEnResultados(String numeroSolicitud) {
        int pagina = 1;

        while (true) {
            esperarResultadosVisibles();

            int totalResultados = resultList.count();
            log.info("[INSPECCION AUTOS][BUSQUEDA SOLICITUD] Revisando página {} con {} resultados.", pagina, totalResultados);

            for (int i = 0; i < totalResultados; i++) {
                String textoCard = resultList.nth(i).innerText();

                if (textoCard != null && textoCard.contains(numeroSolicitud)) {
                    log.info("[INSPECCION AUTOS][BUSQUEDA SOLICITUD] Se encontró el numero de solicitud {} en la página {}, fila {}.", numeroSolicitud, pagina, i + 1);

                    return;
                }
            }

            if (!paginadorSiguienteButton.isEnabled()) {
                throw new FrameworkException("[INSPECCION AUTOS][BUSQUEDA SOLICITUD] No se encontró el numero de solicitud " + numeroSolicitud + " después de revisar todas las páginas.");
            }

            String primerResultadoAntes = totalResultados > 0 ? resultList.first().innerText() : "";

            log.info("[INSPECCION AUTOS][BUSQUEDA SOLICITUD] El numero de solicitud {} no está en la página {}. Avanzando a la siguiente.", numeroSolicitud, pagina);

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

        throw new FrameworkException("[INSPECCION AUTOS][BUSQUEDA SOLICITUD] La lista de resultados no cambió después de avanzar de página.");
    }

    public void clickProgramarInspeccionButton(String numeroNuevaSolicitud) {
        boolean found = false;
        for (int i = 0; i < resultList.count(); i++) {
            Locator solicitudItem = resultList.nth(i);
            Locator numeroSolicitudLocator = solicitudItem.getByText("Nro. " + numeroNuevaSolicitud);
            if (numeroSolicitudLocator.isVisible()) {
                Locator programarLink = solicitudItem.getByRole(AriaRole.BUTTON,
                        new Locator.GetByRoleOptions().setName("PROGRAMAR"));
                clickAndSync(programarLink);
                log.info("Solicitud de inspeccion con numeroNuevaSolicitud = {} encontrada y se hizo click en el enlace Programar inspeccion", numeroNuevaSolicitud);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new FrameworkException("No se encuentró el numero de solicitud de inspeccion " + numeroNuevaSolicitud + " en los resultados de busqueda.");
        }
    }

    public void processAndAssertSuccessClickProgramarInspeccionButton(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                detalleDeSolicitudBreadcrumbs,
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
        log.info("[INSPECCION AUTOS][SOLICITUDES] Click en PROGRAMAR INSPECCIÓN procesado exitosamente, se muestra el detalle de solicitud.");
    }

    public void fillFormBuscarSolicitudEnEvaluacionInspeccion(String numeroNuevaSolicitud) {
        pressSequentiallyAndBlurSync(numeroSolicitudInput, numeroNuevaSolicitud);
        selectAndSync(estadoSelect, "EN EVALUACION");
        log.info("[INSPECCION AUTOS][SOLICITUDES] Formulario de búsqueda de solicitud de inspección llenado con Nro. Solicitud: {} y Estado: EN EVALUACION", numeroNuevaSolicitud);
    }

    public void clickVerInspeccionButton(String numeroNuevaSolicitud) {
        boolean found = false;
        for (int i = 0; i < resultList.count(); i++) {
            Locator solicitudItem = resultList.nth(i);
            Locator numeroSolicitudLocator = solicitudItem.getByText("Nro. " + numeroNuevaSolicitud);
            if (numeroSolicitudLocator.isVisible()) {
                Locator programarLink = solicitudItem.getByRole(AriaRole.BUTTON,
                        new Locator.GetByRoleOptions().setName("VER INSPECCIÓN"));
                clickAndSync(programarLink);
                log.info("Solicitud de inspeccion con numeroNuevaSolicitud = {} encontrada y se hizo click en el enlace Ver Inspeccion", numeroNuevaSolicitud);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new FrameworkException("No se encuentró el numero de solicitud de inspeccion " + numeroNuevaSolicitud + " en los resultados de busqueda.");
        }
        log.info("[INSPECCION AUTOS][SOLICITUDES] Click en VER INSPECCIÓN realizado para la solicitud con numeroNuevaSolicitud = {}", numeroNuevaSolicitud);
    }

    public void processAndAssertSuccessClickVerInspeccionButton(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                solicitudesH1H2H3Title,
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
        log.info("[INSPECCION AUTOS][SOLICITUDES] Click en VER INSPECCIÓN procesado exitosamente, se muestra la información de la inspección.");
    }

    public void fillFormBuscarSolicitudTerminadaInspeccion(String numeroNuevaSolicitud) {
        pressSequentiallyAndBlurSync(numeroSolicitudInput, numeroNuevaSolicitud);
        selectAndSync(estadoSelect, "TERMINADA");
        log.info("[INSPECCION AUTOS][SOLICITUDES] Formulario de búsqueda de solicitud de inspección llenado con Nro. Solicitud: {} y Estado: TERMINADA", numeroNuevaSolicitud);
    }

    public void verficaEstadoDeInspeccionTerminada(String numeroNuevaSolicitud) {
        boolean found = false;

        for (int i = 0; i < resultList.count(); i++) {
            Locator solicitudItem = resultList.nth(i);
            Locator numeroSolicitudLocator = solicitudItem.getByText("Nro. " + numeroNuevaSolicitud);

            if (numeroSolicitudLocator.isVisible()) {
                Locator estadoSolicitudLocator = solicitudItem.getByText("TERMINADA");
                ElementAsserts.assertVisible(estadoSolicitudLocator,
                        "El estado TERMINADA debe ser visible para la solicitud de inspeccion con numero de solicitud = " + numeroNuevaSolicitud);

                log.info("Solicitud de inspeccion con numero de solicitud = {} encontrada con estado TERMINADA", numeroNuevaSolicitud);
                found = true;
                break;
            }
        }

        ElementAsserts.assertTrue(found, "Solicitud de inspeccion con numero de solicitud = " + numeroNuevaSolicitud + " no fue encontrada");
    }
}
