package com.mapfre.playwright.pageobjects.poliza.sctr;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class PolizaSctrPage extends BasePage {
    private final Locator title;
    private final Locator verDocumentosSctrButton;
    private final Locator emitirSctrPeriodoRegularButton;
    private final Locator emitirSctrPeriodoCortoButton;
    public PolizaSctrPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("¿Qué quieres hacer?"));
        this.verDocumentosSctrButton = page.getByText("Ver documentos SCTR / EMISIÓN");
        this.emitirSctrPeriodoRegularButton = page.getByText("Emitir SCTR periodo regular");
        this.emitirSctrPeriodoCortoButton = page.getByText("Emitir SCTR periodo corto");
    }

    /**
     * Verifica que la página de Póliza SCTR haya cargado correctamente
     * validando que el título y todos los campos de filtro sean visibles.
     */
    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
    }

    /**
     * Hace clic en el botón "Ver documentos SCTR / EMISIÓN"
     * para acceder a la sección de documentos SCTR.
     */
    public void clickVerDocumentosSctrButton(){ clickAndSync(verDocumentosSctrButton); }

    /**
     * Hace clic en el botón "Emitir SCTR periodo regular"
     * para acceder a la sección de emisión de SCTR en periodo regular.
     */
    public void clickEmitirSctrPeriodoRegularButton(){
        clickAndSync(emitirSctrPeriodoRegularButton);
    }

    public void clickEmitirSctrPeriodoCortoButton() {
        clickAndSync(emitirSctrPeriodoCortoButton);
    }
}
