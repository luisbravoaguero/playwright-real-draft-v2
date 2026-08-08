package com.mapfre.playwright.pageobjects.poliza.decesos.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class ResumenCotizacionDecesosPage extends BasePage {

    private final Locator title;
    private final Locator ventanaErrorTitulo;
    private final Locator ventanaErrorDescripcion;
    private final Locator emitirPolizaButton;

    private final Locator nroCotizacion;
    private final Locator modalErrorGeneral;
    private final Locator tituloEmision;
    public ResumenCotizacionDecesosPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotización Guardada"));
        this.ventanaErrorTitulo = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Error"));
        this.ventanaErrorDescripcion = page.getByText(Pattern.compile("Error en"));
        this.nroCotizacion = page.locator("div.g-text-right-sm.g-myd-title:has-text('Nro. Cotización:') b");
        //this.emitirPolizaButton = page.getByText("EMITIR");
        this.emitirPolizaButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("EMITIR"));
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.tituloEmision = page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING, new Page.GetByRoleOptions().setName("Emision"));
    }

    public void processAndAssertSuccess(long timeoutMs, long quietMs){
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                ventanaErrorDescripcion,
                title,
                ventanaErrorTitulo,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + ventanaErrorDescripcion.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Cotización Guardada DEBE SER VISIBLE");
        String titulo = title.innerText();
        //System.out.println("Titulo capturado: " + titulo);
        ElementAsserts.assertVisible(nroCotizacion, "El número de cotización no apareció.");
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        String numero = nroCotizacion.innerText().trim();
        //System.out.println("Número de cotización capturado: " + numero);
        esperarHastaQueAlMenosNFilled(10);
    }

    public void esperarHastaQueAlMenosNFilled(int minimoLlenos) {
        page.waitForFunction(
                "(minimo) => {" +
                        "  const elementos = Array.from(document.querySelectorAll('div.item-dato'));" +
                        "  const llenos = elementos.filter(el => el.textContent && el.textContent.trim().length > 0).length;" +
                        "  return llenos >= minimo;" +
                        "}",
                minimoLlenos
        );

        log.info("Al menos {} elementos div.item-dato tienen contenido.", minimoLlenos);
    }
    public void clickEmitirPoliza() {
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        ElementAsserts.assertVisible(emitirPolizaButton, "BOTON Emitir poliza DEBE SER VISIBLE");
        //emitirPolizaButton.scrollIntoViewIfNeeded();
        clickAndSync(emitirPolizaButton);
        log.info("[DECESOS][RESUMEN] Botón Emitir póliza clickeado correctamente");
    }

    public void processAndAssertSuccessEmitirButton(long timeoutMs, long quietMs){
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                tituloEmision,
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
        log.info("[DECESOS][RESUMEN] Botón Emitir póliza procesado correctamente, se ha detectado la pantalla de emisión.");
    }
}
