package com.mapfre.playwright.pageobjects.poliza;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.regex.Pattern;
import com.microsoft.playwright.options.WaitForSelectorState;

public class BandejaDocumentosVidaLeyPage extends BasePage {

    /* ================= VALIDACIÓN ================= */
    private final Locator tituloBandeja;

    // Todas las tarjetas de solicitudes
    private final Locator tarjetasSolicitudes;

    public BandejaDocumentosVidaLeyPage(Page page) {
        super(page);

        // Título de la bandeja (ancla de pantalla)
        this.tituloBandeja = page.getByRole(AriaRole.HEADING,new Page.GetByRoleOptions().setName("Documentos de Vida Ley"));

        //Base de tarjetas (contenedores de cada solicitud)
        this.tarjetasSolicitudes = page.locator("div").filter(new Locator.FilterOptions().setHasText("Nro solicitud"));
    }
    /* ================= ASSERT ================= */
    public void assertEnBandejaDocumentos()
    {
        tituloBandeja.waitFor();
        UiSync.waitForAppIdle(page);
    }

    /* ================= ACCIÓN PRINCIPAL ================= */
    public void seleccionarSolicitudEnEvaluacionYVerCotizacion() {

        log.info("[VIDA_LEY][BANDEJA] Buscando primera solicitud con estado SOLICITUD EVALUACION TASA");

        UiSync.waitForAppIdle(page);

        Pattern estadoEvaluacionTasa = Pattern.compile(
                "SOLICITUD\\s+EVALUACION\\s+TASA",
                Pattern.CASE_INSENSITIVE
        );

        Pattern textoVerCotizacion = Pattern.compile(
                "Ver\\s+Cotizaci[oó]n",
                Pattern.CASE_INSENSITIVE
        );

        Locator tarjetasSolicitudes = page.locator("polizas-vida-ley-documento-item");

        tarjetasSolicitudes.first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(15000));

        Locator tarjetasEnEvaluacion = tarjetasSolicitudes
                .filter(new Locator.FilterOptions().setHasText(estadoEvaluacionTasa));

        int totalTarjetasEnEvaluacion = 0;

        for (int intento = 1; intento <= 10; intento++) {

            UiSync.waitForAppIdle(page);

            totalTarjetasEnEvaluacion = tarjetasEnEvaluacion.count();

            log.info("[VIDA_LEY][BANDEJA] Intento " + intento
                    + " - Total solicitudes con estado SOLICITUD EVALUACION TASA: "
                    + totalTarjetasEnEvaluacion);

            if (totalTarjetasEnEvaluacion > 0) {
                break;
            }

            page.waitForTimeout(1000);
        }

        if (totalTarjetasEnEvaluacion == 0) {

            int totalTarjetas = tarjetasSolicitudes.count();
            log.info("[VIDA_LEY][BANDEJA] Total tarjetas visibles en bandeja: " + totalTarjetas);

            int limite = Math.min(totalTarjetas, 5);

            for (int i = 0; i < limite; i++) {
                String textoTarjeta = tarjetasSolicitudes.nth(i).innerText();
                log.info("[VIDA_LEY][BANDEJA] Texto tarjeta " + i + ": " + textoTarjeta);
            }

            throw new RuntimeException("No se encontró ninguna solicitud con estado SOLICITUD EVALUACION TASA");
        }

        Locator tarjetaEnEvaluacion = tarjetasEnEvaluacion.first();

        tarjetaEnEvaluacion.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));

        log.info("[VIDA_LEY][BANDEJA] Solicitud en evaluación encontrada");

        Locator botonesVerCotizacion = tarjetaEnEvaluacion
                .locator("a.g-button")
                .filter(new Locator.FilterOptions().setHasText(textoVerCotizacion));

        int totalBotones = botonesVerCotizacion.count();

        log.info("[VIDA_LEY][BANDEJA] Total botones Ver Cotización dentro de la solicitud en evaluación: "
                + totalBotones);

        if (totalBotones == 0) {
            throw new RuntimeException("La solicitud con estado SOLICITUD EVALUACION TASA no tiene botón Ver Cotización");
        }

        Locator btnVerCotizacion = botonesVerCotizacion.first();

        btnVerCotizacion.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));

        btnVerCotizacion.scrollIntoViewIfNeeded();

        btnVerCotizacion.click();

        UiSync.waitForAppIdle(page);

        log.info("[VIDA_LEY][BANDEJA] Click en 'Ver Cotización' realizado correctamente");
    }

    public void seleccionarSolicitudAtendidaVerCotizacion() {

        log.info("[VIDA_LEY][BANDEJA] Buscando solicitud con estado SOLICITUD ATENDIDA");

        UiSync.waitForAppIdle(page);

        Pattern estadoSolicitudAtendida = Pattern.compile(
                "SOLICITUD\\s+ATENDIDA",
                Pattern.CASE_INSENSITIVE
        );

        Pattern textoVerCotizacion = Pattern.compile(
                "Ver\\s+Cotizaci[oó]n",
                Pattern.CASE_INSENSITIVE
        );

        // Importante: usar la tarjeta real, no div
        Locator tarjetasSolicitudes = page.locator("polizas-vida-ley-documento-item");

        Locator tarjetaAtendida = tarjetasSolicitudes
                .filter(new Locator.FilterOptions().setHasText(estadoSolicitudAtendida))
                .first();

        tarjetaAtendida.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));

        log.info("[VIDA_LEY][BANDEJA] SOLICITUD ATENDIDA encontrada");

        // Botón "Ver Cotización" dentro de esa tarjeta específica
        Locator btnVerCotizacion = tarjetaAtendida
                .locator("a.g-button")
                .filter(new Locator.FilterOptions().setHasText(textoVerCotizacion))
                .first();

        btnVerCotizacion.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));

        btnVerCotizacion.scrollIntoViewIfNeeded();

        btnVerCotizacion.click();

        UiSync.waitForAppIdle(page);

        log.info("[VIDA_LEY][BANDEJA] Click en 'Ver Cotización' realizado correctamente");
    }
}