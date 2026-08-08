package com.mapfre.playwright.pageobjects.poliza.accidentes;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.regex.Pattern;

public class EmisionPolizaAccidentesResultadoPage extends BasePage {

    // ===== Elementos =====
    private final Locator tituloPolizaEmitida;
    private final Locator condicionVigente;
    private final Locator primaTotalValor;

    public EmisionPolizaAccidentesResultadoPage(Page page) {
        super(page);

        this.tituloPolizaEmitida = page.getByText(Pattern.compile("Póliza emitida", Pattern.CASE_INSENSITIVE));
        this.condicionVigente = page.getByText(
                Pattern.compile("\\bVIGENTE\\b", Pattern.CASE_INSENSITIVE));
        this.primaTotalValor = page.locator(
                "xpath=//*[contains(text(),'Total del período')]/following::*[self::span][1]");

    }

    public String obtenerTitulo() {
        return tituloPolizaEmitida.textContent().trim();
    }

    public String obtenerCondicion() {
        return condicionVigente.textContent().trim();
    }

    public String obtenerPrimaTotal() {
        return primaTotalValor.textContent().trim();
    }

    public void mostrarResumenEmision() {
        try {

            esperarCargaCompletaEmision();

            String titulo = obtenerTitulo();
            String condicion = obtenerCondicion();
            String prima = obtenerPrimaTotal();

            log.info("========== RESULTADO EMISIÓN ==========");
            log.info("Estado           : {}", titulo);
            log.info("Condición        : {}", condicion);
            log.info("Prima Total (IGV): {}", prima);
            log.info("=======================================");

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][EMISION][ERROR] No se pudieron leer los datos de emisión.",
                    e
            );
        }
    }
    public void validarPolizaEmitida() {
        UiSync.waitForAppIdle();
        ElementAsserts.assertVisible(
                tituloPolizaEmitida,
                "[EMISION] Debe mostrarse 'Póliza emitida'"
        );

        if (!obtenerCondicion().equalsIgnoreCase("VIGENTE")) {
            throw new AssertExceptions(
                    "[EMISION][ERROR] La condición no es VIGENTE"
            );
        }

        log.info("[ACCIDENTES][EMISION] Validación OK ✅");
    }
    public void esperarCargaCompletaEmision() {

        log.info("[ACCIDENTES][EMISION] Esperando resultado de emisión de póliza");

        UiSync.waitForAppIdle(page);

        int intentos = 0;
        int maxIntentos = 50; // 40 * 3s = 120s

        while (intentos < maxIntentos) {

            try {

                // Validar que el título existe
                if (tituloPolizaEmitida.count() > 0
                        && tituloPolizaEmitida.first().isVisible()) {

                    // Validar que la prima ya cargó correctamente
                    if (primaTotalValor.count() > 0) {

                        String valor = primaTotalValor.textContent();

                        if (valor != null
                                && !valor.trim().isEmpty()
                                && !valor.trim().equals("0.00")) {

                            log.info("[ACCIDENTES][EMISION] Emisión completamente procesada ✅");
                            return;
                        }
                    }
                }

            } catch (Exception ignored) {

            }

            log.info("[ACCIDENTES][EMISION] Esperando emisión... intento {}", intentos + 1);

            page.waitForTimeout(6000);
            UiSync.waitForAppIdle(page);

            intentos++;
        }

        log.error("[ACCIDENTES][EMISION][ERROR] Timeout esperando resultado de emisión");
        log.error("[DEBUG] URL actual: {}", page.url());

        try {
            log.error("[DEBUG] Body actual:");
            log.error(page.locator("body").innerText());
        } catch (Exception ignored) {}

        throw new AssertExceptions(
                "[ACCIDENTES][EMISION][ERROR] La emisión no terminó completamente."
        );
    }
}