package com.mapfre.playwright.pageobjects.poliza.transporte;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ResultadoEmisionTransportePage extends BasePage {
    private final Locator title;
    private final Locator numeroPoliza;
    public ResultadoEmisionTransportePage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Póliza emitida"));
        this.numeroPoliza = page.getByText("Nro. Póliza:");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Póliza emitida DEBE SER VISIBLE");
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
    }

    public void validarVisibilidadNumeroPoliza() {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        esperarHastaQueAlMenosNFilled(10, 60_000, 500);
        ElementAsserts.assertVisible(numeroPoliza, "El número de póliza no apareció.");
        log.info("[TRANSPORTE][EMISION][RESULTADO] Número de póliza visible: {}", numeroPoliza.innerText().trim());
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
