package com.mapfre.playwright.pageobjects.poliza.vidaley;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Permite:
 * - Buscar actividades por código
 * - Seleccionar una actividad válida
 * - Confirmar la selección
 */
public class ActividadModalPage extends BasePage {

    /* ===== Locators ===== */
    // Input de búsqueda de actividad
    private final Locator txtBuscarActividad;

    // Lista de actividades
    private final Locator listaActividades;

    // Botón OK para confirmar la selección
    private final Locator btnOk;

    /* ===== Constructor ===== */

    public ActividadModalPage(Page page) {
        super(page);

        this.txtBuscarActividad =
                page.locator("input[name='nNombSubAct']");

        // Locator base del contenedor seleccionable
        this.listaActividades =
                page.locator("ul.g-list");

        this.btnOk =
                page.getByRole(
                        AriaRole.BUTTON,
                        new Page.GetByRoleOptions().setName("OK")
                );
    }

    /* ===== Acciones ===== */

    /** Busca y selecciona una actividad dentro del modal.**/
    public void buscarYSeleccionarActividad(String codigoActividad) {

        log.info(
                "[VIDA_LEY][ACTIVIDAD] Buscando y seleccionando actividad con código {}",
                codigoActividad
        );

        int maxIntentos = 3;

        for (int intento = 1; intento <= maxIntentos; intento++) {

            log.info(
                    "[VIDA_LEY][ACTIVIDAD] Intento {} de {}",
                    intento,
                    maxIntentos
            );

            try {
                // Esperar input y escribir código
                txtBuscarActividad.waitFor();
                txtBuscarActividad.fill("");
                txtBuscarActividad.fill(codigoActividad);
                txtBuscarActividad.press("Tab");

                // Esperar que Angular termine validaciones async
                page.waitForCondition(() ->
                        page.locator(".ng-pending").count() == 0
                );

                // Aplicar filtro dinámico sobre el locator base
                Locator actividadSeleccionable =
                        listaActividades
                                .filter(new Locator.FilterOptions()
                                        .setHasText(codigoActividad))
                                .first();

                actividadSeleccionable.waitFor(
                        new Locator.WaitForOptions().setTimeout(20_000)
                );

                // Click REAL sobre el <ul>
                actividadSeleccionable.click();

                // esperar habilitación del botón OK
                btnOk.waitFor();
                page.waitForCondition(btnOk::isEnabled);

                // Confirmar selección
                btnOk.click();

                UiSync.waitForAppIdle(page);

                log.info(
                        "[VIDA_LEY][ACTIVIDAD] Actividad {} seleccionada correctamente",
                        codigoActividad
                );
                return;

            } catch (Exception e) {

                log.warn(
                        "[VIDA_LEY][ACTIVIDAD] No se pudo seleccionar la actividad en el intento {}",
                        intento
                );

                page.waitForTimeout(2000);
            }
        }

        throw new AssertionError(
                "No se pudo seleccionar la actividad con código " + codigoActividad +
                        ". El modal de actividades se mostró, pero la opción no pudo ser seleccionada."
        );
    }
}