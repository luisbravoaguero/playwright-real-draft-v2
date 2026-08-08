package com.mapfre.playwright.pageobjects.poliza.vidaley;

import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ResultadoEmisionPolizaVidaLeyPage extends BasePage {

    // ===== Validadores únicos de pantalla =====
    private final Locator textoNumeroPoliza;

    public ResultadoEmisionPolizaVidaLeyPage(Page page) {
        super(page);

        // Resultado FINAL del negocio
        this.textoNumeroPoliza =
                page.locator("text=/PÓLIZA\\s+NRO\\s+\\d+/");
    }
    /*** Valida que la póliza fue emitida correctamente */
    public void assertPolizaEmitida() {
        //parametros de control de tiempo
        final int timeoutTotalMs = 300_000;     // tiempo de espera total 5 minutos
        final int intervaloMs   = 30_000;       // intervalo información cada 30 segundos
        final int totalMinutos  = timeoutTotalMs / 60_000;

        log.info(
                "[VIDA_LEY][RESULTADO] Emisión iniciada (proceso backend pesado). " +
                        "Tiempo máximo de espera: {} minutos",
                totalMinutos
        );

        long startTime = System.currentTimeMillis();
        int minutoActual = 0;

        while (true) {
            //Cálculo del tiempo transcurrido
            long elapsedMs = System.currentTimeMillis() - startTime;

            // Si apareció el resultado final, salimos
            if (textoNumeroPoliza.isVisible()) {

                log.info(
                        "[VIDA_LEY][RESULTADO] Resultado obtenido correctamente " +
                                "después de {} segundos",
                        elapsedMs / 1000
                );
                return;
            }

            // Timeout alcanzado
            if (elapsedMs >= timeoutTotalMs) {
                break;
            }

            // Log de progreso cada intervalo
            minutoActual = (int) (elapsedMs / 60_000) + 1;

            log.info(
                    "[VIDA_LEY][RESULTADO] Emisión en proceso... minuto {} de {}",
                    minutoActual,
                    totalMinutos
            );

            // Espera controlada
            page.waitForTimeout(intervaloMs);
        }

        //Si salimos del bucle sin resultado → fallo real
        log.error(
                "[VIDA_LEY][RESULTADO][ERROR] El proceso de emisión no finalizó " +
                        "después de {} minutos",
                totalMinutos
        );

        throw new AssertionError(
                "La emisión de la póliza no finalizó dentro del tiempo esperado (" +
                        totalMinutos + " minutos). El backend puede estar lento o encolado."
        );
    }
    /*** Obtiene el número de póliza emitida */
    public String obtenerNumeroPoliza() {

        String texto = textoNumeroPoliza.textContent().trim();
        String numeroPoliza = texto.replaceAll("\\D+", "");

        log.info("[VIDA_LEY][RESULTADO] Número de póliza emitida: {}", numeroPoliza);
        return numeroPoliza;
    }
}