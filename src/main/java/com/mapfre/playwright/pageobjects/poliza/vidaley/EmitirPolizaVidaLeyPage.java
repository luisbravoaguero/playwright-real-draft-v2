package com.mapfre.playwright.pageobjects.poliza.vidaley;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class EmitirPolizaVidaLeyPage extends BasePage {

    // ========== Locators =================
    // Título principal de la pantalla
    private final Locator tituloEmitirPoliza;

    // Botón Siguiente
    private final Locator btnSiguiente;

    // Botón Emitir
    private final Locator btnEmitirFinal;

    // Botón "Guardar y continuar" del modal
    private final Locator btnGuardarYContinuar;

    // ========== Constructor =================

    public EmitirPolizaVidaLeyPage(Page page) {
        super(page);

        this.tituloEmitirPoliza =
                page.getByRole(
                        com.microsoft.playwright.options.AriaRole.HEADING,
                        new Page.GetByRoleOptions().setName("Emitir póliza de vida ley")
                );

        this.btnSiguiente =
                page.locator("a.g-button.block")
                        .filter(new Locator.FilterOptions().setHasText("Siguiente"));

        this.btnEmitirFinal =
                page.locator("a.g-button.block")
                        .filter(new Locator.FilterOptions().setHasText("Emitir"));

        // Locator DIRECTO al botón (no al modal)
        this.btnGuardarYContinuar =
                page.locator("button")
                        .filter(new Locator.FilterOptions().setHasText("Guardar y continuar"));
    }

    // ================= Validaciones =================

    /*** Valida que estamos en la pantalla "Emitir póliza de vida ley" */
    public void assertEnPaginaEmitirPolizaVidaLey() {

        log.info("[VIDA_LEY][EMISION] Validando pantalla 'Emitir póliza de vida ley'");

        try {
            tituloEmitirPoliza.waitFor();
            UiSync.waitForAppIdle(page);

        } catch (com.microsoft.playwright.TimeoutError e) {
            log.error("[VIDA_LEY][EMISION][ERROR] No se cargó la pantalla de emisión");
            throw new AssertionError(
                    "La pantalla 'Emitir póliza de vida ley' no terminó de cargar correctamente."
            );
        }

        log.info("[VIDA_LEY][EMISION] Pantalla de emisión lista");
    }

    /*** Hace clic en "Siguiente" */
    public void clickSiguiente() {

        log.info("[VIDA_LEY][EMISION] Avanzando al Paso 2");

        try {
            UiSync.waitForAppIdle(page);
            btnSiguiente.waitFor();
            btnSiguiente.scrollIntoViewIfNeeded();
            btnSiguiente.click();
            UiSync.waitForAppIdle(page);

        } catch (com.microsoft.playwright.TimeoutError e) {
            log.error("[VIDA_LEY][EMISION][ERROR] No se pudo hacer clic en 'Siguiente'");
            throw new AssertionError(
                    "No se pudo avanzar al Paso 2 (Emisión). El botón 'Siguiente' no estuvo disponible."
            );
        }

        log.info("[VIDA_LEY][EMISION] Paso 2 alcanzado");
    }

    /**Confirma el modal haciendo clic en "Guardar y continuar"*/
    public void confirmarGuardarYContinuar() {

        log.info("[VIDA_LEY][EMISION] Confirmando 'Guardar y continuar'");

        try {
            UiSync.waitForAppIdle(page);
            btnGuardarYContinuar.waitFor();
            btnGuardarYContinuar.scrollIntoViewIfNeeded();
            btnGuardarYContinuar.click();
            UiSync.waitForAppIdle(page);

        } catch (com.microsoft.playwright.TimeoutError e) {
            log.error("[VIDA_LEY][EMISION][ERROR] No se pudo confirmar 'Guardar y continuar'");
            throw new AssertionError(
                    "No se pudo confirmar el guardado de los datos. El botón 'Guardar y continuar' no respondió."
            );
        }

        log.info("[VIDA_LEY][EMISION] Guardado confirmado correctamente");
    }

    /*** Valida que estamos en el Paso 2 - Emisión*/
    public void assertEnPasoEmision() {

        log.info("[VIDA_LEY][EMISION] Validando Paso 2 - Emisión");

        try {
            UiSync.waitForAppIdle(page);
            btnEmitirFinal.waitFor();

        } catch (com.microsoft.playwright.TimeoutError e) {
            log.error("[VIDA_LEY][EMISION][ERROR] Paso 2 no disponible");
            throw new AssertionError(
                    "No se pudo validar el Paso 2 (Emisión). El botón 'Emitir' no está disponible."
            );
        }

        log.info("[VIDA_LEY][EMISION] Paso 2 validado");
    }

    /*** clic en el botón Emitir*/
    public void clickEmitirPoliza() {

        log.info("[VIDA_LEY][EMISION] Haciendo clic en 'Emitir'");

        try {
            UiSync.waitForAppIdle(page);
            btnEmitirFinal.waitFor();
            btnEmitirFinal.scrollIntoViewIfNeeded();

            //AQUÍ SE DISPARA LA EMISIÓN REAL
            btnEmitirFinal.click();

        } catch (com.microsoft.playwright.TimeoutError e) {
            log.error("[VIDA_LEY][EMISION][ERROR] No se pudo hacer clic en 'Emitir'");
            throw new AssertionError(
                    "No se pudo emitir la póliza. El botón 'Emitir' no estuvo disponible."
            );
        }
        log.info("[VIDA_LEY][EMISION] Click en 'Emitir' ejecutado correctamente");
    }
}
