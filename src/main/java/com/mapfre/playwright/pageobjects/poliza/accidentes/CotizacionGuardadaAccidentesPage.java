package com.mapfre.playwright.pageobjects.poliza.accidentes;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.regex.Pattern;

public class CotizacionGuardadaAccidentesPage extends BasePage {

    // ===== Validadores de página =====
    private final Locator tituloCotizacionGuardada;
    private final Locator textoNumeroCotizacion;
    private final Locator seccionRiesgosCotizados;

    // ===== Acciones =====
    private final Locator botonEmitirPoliza;

    public CotizacionGuardadaAccidentesPage(Page page) {
        super(page);

        this.tituloCotizacionGuardada = page.getByText(
                Pattern.compile("Cotización guardada de accidentes", Pattern.CASE_INSENSITIVE) );

        this.textoNumeroCotizacion = page.locator(
                "text=/Nro\\.\\s*Cotización:\\s*\\d+/");

        this.seccionRiesgosCotizados = page.getByText(
                Pattern.compile("Riesgos cotizados", Pattern.CASE_INSENSITIVE));

        this.botonEmitirPoliza = page.locator("a.g-button")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("Emitir Póliza", Pattern.CASE_INSENSITIVE)));

    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(
                tituloCotizacionGuardada,
                "[ACCIDENTES][COTIZACION_GUARDADA] TITULO Cotización guardada de accidentes DEBE SER VISIBLE");

        ElementAsserts.assertVisible(
                textoNumeroCotizacion,
                "[ACCIDENTES][COTIZACION_GUARDADA] NRO Cotización DEBE SER VISIBLE");

        ElementAsserts.assertVisible(
                seccionRiesgosCotizados,
                "[ACCIDENTES][COTIZACION_GUARDADA] SECCION Riesgos cotizados DEBE SER VISIBLE");

        log.info("[ACCIDENTES][COTIZACION_GUARDADA] Página visible: Cotización guardada de accidentes");
    }

    public String obtenerNumeroCotizacion() {
        try {
            ElementAsserts.assertVisible(
                    textoNumeroCotizacion,
                    "[ACCIDENTES][COTIZACION_GUARDADA] NRO Cotización DEBE SER VISIBLE PARA EXTRAERLO");

            String texto = textoNumeroCotizacion.textContent().trim();
            String numeroCotizacion = texto.replaceAll("\\D+", "");

            if (numeroCotizacion.isEmpty()) {
                throw new AssertExceptions(
                        "[ACCIDENTES][COTIZACION_GUARDADA][ERROR] No se pudo extraer el número de cotización."
                );
            }

            log.info("[ACCIDENTES][COTIZACION_GUARDADA] Número de cotización generado: {}", numeroCotizacion);

            return numeroCotizacion;

        } catch (AssertExceptions e) {
            throw e;

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][COTIZACION_GUARDADA][ERROR] No se pudo obtener el número de cotización.",
                    e
            );
        }
    }



    public void clickEmitirPoliza() {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        esperarHastaQueAlMenosNFilled(5, 30_000, 500);
        try {
            obtenerNumeroCotizacion();
            ElementAsserts.assertVisible(
                    botonEmitirPoliza,
                    "[ACCIDENTES][COTIZACION_GUARDADA] BOTON Emitir Póliza DEBE SER VISIBLE ANTES DEL CLICK");

            log.info("[ACCIDENTES][COTIZACION_GUARDADA] Botón visible: Emitir Póliza");
            clickAndSync(botonEmitirPoliza);
            log.info("[ACCIDENTES][COTIZACION_GUARDADA] Click realizado correctamente en el botón: Emitir Póliza");

        } catch (AssertExceptions e) {
            throw e;

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][COTIZACION_GUARDADA][ERROR] No se pudo hacer click en el botón Emitir Póliza. " +
                            "Validar si la página cargó correctamente o si el botón está disponible.",
                    e
            );
        }
    }

    public void esperarHastaQueAlMenosNFilled(int minimoLlenos, double timeoutMs, double quietMs) {
        Page.WaitForFunctionOptions options = new Page.WaitForFunctionOptions();
        options.setTimeout(timeoutMs);
        options.setPollingInterval(quietMs);

        page.waitForFunction(
                "(minimo) => {" +
                        "  const elementos = Array.from(document.querySelectorAll('div.item-dato'));" +
                        "  const llenos = elementos.filter(el => el.textContent && el.textContent.trim().length > 0).length;" +
                        "  return llenos >= minimo;" +
                        "}",
                minimoLlenos,
                options
        );

        log.info("Al menos {} elementos div.item-dato tienen contenido.", minimoLlenos);
    }
}