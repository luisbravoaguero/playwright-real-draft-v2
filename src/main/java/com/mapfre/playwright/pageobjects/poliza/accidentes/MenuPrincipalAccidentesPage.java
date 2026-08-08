package com.mapfre.playwright.pageobjects.poliza.accidentes;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.regex.Pattern;

public class MenuPrincipalAccidentesPage extends BasePage {
    private final Locator title;
    private final Locator consultaDocumentoButton;
    private final Locator cotizarPolizaAccidentesButton;

    public MenuPrincipalAccidentesPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("¿Qué quieres hacer?"));

        this.consultaDocumentoButton =
                page.getByText("Consultar documentos de");

        this.cotizarPolizaAccidentesButton =
                page.locator("a.g-button")
                        .filter(new Locator.FilterOptions()
                                .setHasText(Pattern.compile("Cotizar póliza ACCIDENTES", Pattern.CASE_INSENSITIVE)));

    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
    }
    public void clickConsultaDocumentoButton() {
        clickAndSync(consultaDocumentoButton);
    }
    public void clickCotizarPolizaAccidentes() {

        try {
            ElementAsserts.assertVisible(
                    cotizarPolizaAccidentesButton,
                    "[ACCIDENTES][MENU] BOTON Cotizar póliza ACCIDENTES DEBE SER VISIBLE ANTES DEL CLICK"
            );
            log.info("[ACCIDENTES][MENU] Botón visible: Cotizar póliza ACCIDENTES");
            clickAndSync(cotizarPolizaAccidentesButton);
            log.info("[ACCIDENTES][MENU] Click realizado correctamente en el botón: Cotizar póliza ACCIDENTES");

        } catch (AssertExceptions e) {
            throw e;

        } catch (Exception e) {
            throw new AssertExceptions(
                    "[ACCIDENTES][MENU][ERROR] No se pudo hacer click en el botón Cotizar póliza ACCIDENTES. " +
                            "Validar si el botón está habilitado, si existe un overlay o si la página terminó de cargar.",
                    e
            );
        }
    }
}