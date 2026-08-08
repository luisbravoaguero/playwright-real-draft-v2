package com.mapfre.playwright.pageobjects.poliza.soat;

import com.mapfre.exceptions.FrameworkException;
import com.mapfre.utils.DownloadVsErrorRace;
import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import java.util.regex.Pattern;

public class DocumentoSoatPage extends BasePage {
    private final Locator title;
    private final Locator estadoSelect;
    private final Locator numeroPolizaInput;
    private final Locator filtrarButton;
    private final Locator limpiarButton;
    private final Locator accionesButton;

    // Modal de error
    private final Locator ventanaErrorTitulo;       // HEADING: "ERROR"
    private final Locator ventanaErrorDescripcion;  // Texto: "Ocurrió un error inesperado."
    private final Locator ventanaErrorOKButton;     // Botón: "Ok" / "OK"

    // Menú Acciones y sus items
    private final Locator descargarPdfItem;
    private final Locator overlayContainer;
    private final Locator resultadoNoEncontradoMensaje;
    private final Locator ventanaErrorModal;
    public DocumentoSoatPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Documentos SOAT"));
        this.numeroPolizaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de Póliza"));
        this.estadoSelect = page.getByLabel("Estado", new Page.GetByLabelOptions().setExact(true));
        this.filtrarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Filtrar"));
        this.limpiarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Limpiar"));
        this.accionesButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("Acciones")));

        // Locators del modal de error (los 3 que acordamos)
        this.ventanaErrorModal = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.ventanaErrorTitulo = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ERROR").setLevel(2));
        this.ventanaErrorDescripcion = ventanaErrorModal.locator("div.swal2-html-container");
        this.ventanaErrorOKButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("^Ok$", Pattern.CASE_INSENSITIVE))
        );

        // Overlay/menú que contiene los ítems (más preciso que apuntar al mismo item)
        this.overlayContainer = page.getByRole(AriaRole.MENU);

        // Ítem de "Descargar PDF"
        this.descargarPdfItem = page.getByRole(
                AriaRole.MENUITEM,
                new Page.GetByRoleOptions().setName(Pattern.compile("Descargar PDF", Pattern.CASE_INSENSITIVE))
        );

        this.resultadoNoEncontradoMensaje = page.getByText("No hay resultados para los filtros escogidos");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Documentos SOAT DEBE SER VISIBLE");
    }

    public void filterActivePoliza(String numero_poliza){
        numeroPolizaInput.fill(numero_poliza);
        // Si tu selectOption acepta sólo labels exactos, considera usar "Vigente" literal.
        estadoSelect.selectOption(new SelectOption().setLabel(String.valueOf(Pattern.compile("Vigente", Pattern.CASE_INSENSITIVE))));
        clickAndSync(filtrarButton);
    }
    public void processAndAssertSuccess(long timeoutMs, long quietMs){
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoNoEncontradoMensaje,
                accionesButton,
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


    public void clickAccionesButton() {
        accionesButton.click();
        ElementAsserts.assertVisible(overlayContainer,"El elemento que contiene al boton descargar PDF debe ser visible");
        ElementAsserts.assertVisible(descargarPdfItem,"El boton descargar PDF debe ser visible");
    }
    public void downloadPdfOrThrow() {
        DownloadVsErrorRace.Result r = DownloadVsErrorRace.race(
                page,
                descargarPdfItem::click,
                ventanaErrorModal,
                ventanaErrorDescripcion,
                40_000, // timeoutMs
                300, // quietMs (estabilidad para evitar falsos positivos por parpadeo)
                80 // pollMs
        );

        // 3) Decide outcome
        if (r.outcome() == DownloadVsErrorRace.Outcome.ERROR_UI_FIRST) {
            // best-effort evidence + cleanup
            ElementAsserts.assertUIMessage("Apareció un error modal antes de que finalizara la descarga. Mensaje: " + r.uiErrorText());
        }


        if (r.outcome() == DownloadVsErrorRace.Outcome.DOWNLOAD_FAILED) {
            throw new FrameworkException("La descarga finalizó pero FALLÓ. error=" + r.downloadFailure() +
                    " nombre_archivo=" + r.filename() + " url=" + r.url());
        }

        if (r.outcome() == DownloadVsErrorRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Ni el error modal ni la finalización de la descarga ocurrieron dentro del tiempo de espera.");
        }

        // DOWNLOAD_SUCCESS -> continue
    }

/*
    // DESCARGA: Inicio (con validación de modal de error)
    // Solo conocer que INICIÓ la descarga del PDF. Devuelve el objeto Download.
    //Inicia la descarga del PDF o, si aparece el modal de error, falla de inmediato.
    public Download descargarPdf_Inicia() {
        final long DEADLINE_MS = 30000; // ajusta tiempo
        final long POLL_MS = 60; // chequear cada 60ms

        // 1) Abrir el menú Acciones (no usar clickAndSync en flujos de descarga)
        accionesButton.click();

        // 2) Asegurar que el menú y el ítem estén visibles
        assertThat(overlayContainer).isVisible();
        assertThat(descargarPdfItem).isVisible();

        // 3) Preparar "carrera" entre descarga y modal
        final java.util.concurrent.atomic.AtomicReference < Download > downloadRef = new java.util.concurrent.atomic.AtomicReference < > ();

        // Registrar handler de descarga (se completa apenas el browser dispare el evento)
        final java.util.function.Consumer < Download > handler = downloadRef::set;
        page.onDownload(handler);

        try {
            // 4) Disparar el click que intenta descargar
            descargarPdfItem.click();

            // 5) Loop de polling muy corto: termina en cuanto gane descarga o modal
            final long deadline = System.currentTimeMillis() + DEADLINE_MS;
            while (System.currentTimeMillis() < deadline) {
                // ¿Ganó la descarga?
                Download dl = downloadRef.get();
                if (dl != null) {
                    System.out.printf("Descarga INICIADA: %s | %s%n", dl.suggestedFilename(), dl.url());
                    return dl;
                }

                // ¿Ganó el modal?
                try {
                    // Chequeo robusto: heading y descripción visibles
                    if (ventanaErrorTitulo.isVisible() && ventanaErrorDescripcion.isVisible()) {
                        // Evidencia
                        try {
                            java.nio.file.Path dir = java.nio.file.Paths.get("evidencias");
                            java.nio.file.Files.createDirectories(dir);
                            page.screenshot(new Page.ScreenshotOptions()
                                    .setPath(dir.resolve("error_descarga.png"))
                                    .setFullPage(true));
                        } catch (Exception ignored) {
                            // best effort
                        }

                        // Limpieza (cerrar modal)
                        cerrarModalErrorSiVisible();

                        // Fallar de inmediato (sin esperar al timeout de descarga)
                        throw new AssertionError("La descarga NO inició. Apareció modal de error: 'ERROR – Ocurrió un error inesperado.'");
                    }
                } catch (RuntimeException ignored) {
                    // Si el DOM aún no está listo, seguimos polling.
                }

                try {
                    Thread.sleep(POLL_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrumpido esperando descarga o modal", ie);
                }
            }

            // 6) Si no ganó ninguno, fallar con diagnóstico claro
            throw new AssertionError("Ni la descarga ni el modal de error aparecieron dentro de " + DEADLINE_MS + " ms.");

        } finally {
            // 7) Detach del handler para evitar listeners colgados (si tu versión soporta offDownload)
            try {
                page.offDownload(handler);
            } catch (Throwable ignored) {
                //compatible con versiones sin offDownload
            }
        }
    }
*/
    // Utilitarios de Modal de Error

    //Espera de forma NO bloqueante a que el modal de error sea visible.
    //Devuelve true si aparece dentro del timeout; false si no.

    /*public boolean esperarModalError(long timeoutMs) {
        long deadline = System.currentTimeMillis() + timeoutMs;
        long pollEveryMs = 150;

        while (System.currentTimeMillis() < deadline) {
            try {
                if (ventanaErrorTitulo.isVisible() || ventanaErrorDescripcion.isVisible()) {
                    return true;
                }
            } catch (RuntimeException ignored) {
                // best effort
            }


            try {
                Thread.sleep(pollEveryMs);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return false;
    }*/

    //Abre "Acciones", hace click en "Descargar PDF" y valida si aparece el modal de error.
    //No lanza excepción por timeout; devuelve true si apareció, false si no.
    //Si cerrarModal=true, intenta cerrar el modal con "Ok".
    /*public boolean clickDescargarYValidarModalError(long timeoutMs, boolean cerrarModal) {
        accionesButton.click();

        try {
            assertThat(descargarPdfItem).isVisible();
            descargarPdfItem.click();
        } catch (RuntimeException ignored) {
            // Si falló el click, igual chequeamos el modal
        }

        boolean visible = esperarModalError(timeoutMs);

        if (visible && cerrarModal) {
            cerrarModalErrorSiVisible();
        }
        return visible;
    }

    //Intenta cerrar el modal de error si está visible (best effort).

    public void cerrarModalErrorSiVisible() {
        try {
            if (ventanaErrorOKButton != null && ventanaErrorOKButton.isVisible()) {
                ventanaErrorOKButton.click();
            }
        } catch (RuntimeException ignored) {
            // best effort
        }
    }

    //Chequeo rápido del modal (para asserts condicionales en tests).

    public boolean hayErrorInesperado() {
        try {
            assertThat(ventanaErrorTitulo).isVisible();
            assertThat(ventanaErrorDescripcion).isVisible();
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public void cerrarVentanaError() {
        try {
            ventanaErrorOKButton.click();
        } catch (RuntimeException ignored) {}
    }
*/
}
 