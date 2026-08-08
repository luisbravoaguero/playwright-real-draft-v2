package com.mapfre.playwright.pageobjects.poliza.sctr.emision;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DownloadVsErrorRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.regex.Pattern;

public class ResumenEmisionSctrPeriodoCortoPage extends BasePage {
    private final Locator polizaProcesadaConExitoTitle;
    private final Locator polizaInformacionCard;
    private final Locator descargarTodoButton;
    private final Locator ventanaErrorTitulo;
    private final Locator ventanaErrorDescripcion;
    public ResumenEmisionSctrPeriodoCortoPage(Page page) {
        super(page);
        this.polizaProcesadaConExitoTitle = page.locator("h2").filter(new Locator.FilterOptions().setHasText(Pattern.compile("La póliza ha sido emitida con éxito", Pattern.CASE_INSENSITIVE)));
        this.polizaInformacionCard = page.locator("div.g-box ul.g-list.second-design");
        this.descargarTodoButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Descargar todo", Pattern.CASE_INSENSITIVE)));
        this.ventanaErrorTitulo = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ERROR"));
        this.ventanaErrorDescripcion = page.getByText("Ocurrió un error inesperado.");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(polizaProcesadaConExitoTitle, "TITULO Su solicitud fue procesada con éxito DEBE SER VISIBLE");
        sync();
        waitRandomBetween(3000);
        waitForNetworkIdle();
        polizaInformacionCard.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(40_000));
        log.info("[EMISION][RESUMEN EMISION SCTR PERIODO CORTO] Página Resumen Emisión SCTR Periodo Corto cargada correctamente.");
    }

    public void validarVisibilidadYContenidoNumeroPoliza() {
        ElementAsserts.assertVisible(polizaInformacionCard.first(), "LA CARD DE INFORMACION DE LA POLIZA DEBE SER VISIBLE");
        int cardCount = polizaInformacionCard.count();

        for (int i = 0; i < cardCount; i++) {
            Locator card = polizaInformacionCard.nth(i);
            String label = card.locator("li .item-label").innerText();
            String nro = card.locator("li.cnt-item .item-dato").innerText();
            log.info("[EMISION][RESUMEN EMISION SCTR PERIODO CORTO] Documento: {} | Numbero: {}", label.trim(), nro.trim());
        }

        log.info("[EMISION][RESUMEN EMISION SCTR PERIODO CORTO] Se validó la visibilidad y contenido de la información de la póliza correctamente.");
    }

    public void valdiarDescargaTodosDocumentosSctrPensionSaludPeriodoCorto() {
        ElementAsserts.assertVisible(descargarTodoButton,"El boton 'descargar todo' constancia DEBE SER VISIBLE");
        DownloadVsErrorRace.Result r = DownloadVsErrorRace.race(
                page,
                descargarTodoButton::click,
                ventanaErrorTitulo,
                ventanaErrorDescripcion,
                120_000, // timeoutMs
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
            throw new FrameworkException("Ni el error modal ni la finalización de la descarga ocurrieron dentro del tiempo de espera. Recibo Pension Descargar");
        }

        // DOWNLOAD_SUCCESS -> continue
        log.info("[EMISION][RESUMEN EMISION SCTR PERIODO CORTO] Descarga de todos los documentos exitoso. nombre_archivo={} url={}", r.filename(), r.url());
    }
}
