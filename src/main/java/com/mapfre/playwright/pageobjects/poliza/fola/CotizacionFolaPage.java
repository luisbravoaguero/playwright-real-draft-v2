package com.mapfre.playwright.pageobjects.poliza.fola;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.components.material.MaterialAutocomplete;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CotizacionFolaPage extends BasePage {
    private final Locator title;
    private final Locator numeroRucInput;
    private final Locator razonSocialInput;
    private final Locator tipoFraccionamientoSelect;
    private final Locator cantidadAseguradoInput;

    private final Locator subvencionPersonaInput;
    private final Locator agregarAseguradoFolaButton;
    private final Locator cotizarFolaButton;
    private final MaterialAutocomplete actividadAutocomplete;
    private  MaterialAutocomplete.AttemptKind lastActividadAttempt;

        public CotizacionFolaPage(Page page) {
            super(page);
            this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotizador"));
            this.numeroRucInput=page.locator("oim-input[name='nNumeroDocumento'] input");
            this.razonSocialInput=page.locator("oim-input[name='nNombreCompleto'] input");;
            this.tipoFraccionamientoSelect=page.locator("oim-select[name='nFracPago'] select");
            this.actividadAutocomplete = new MaterialAutocomplete(page, page.locator("input[role='combobox']").last(), "Actividad");
            this.cantidadAseguradoInput=page.locator("input[name='nCantidad1']");
            this.subvencionPersonaInput=page.locator("input[name='nSubvencion1']");
            this.agregarAseguradoFolaButton=page.locator("a.g-button:has-text('Agregar')");
            this.cotizarFolaButton= page.locator("a.g-button:has-text(' cotizar ')");
        }

        public void assertLoadedCotizacionFola() {
            ElementAsserts.assertVisible(title, "TITULO Cotización de póliza FOLA DEBE SER VISIBLE");
        }
        public void fillFormInformacionGeneral(String numeroRuc, String razonSocial, String tipoFraccionamiento) {
            fillAndBlurSync(numeroRucInput, numeroRuc);
            fillInputIfEnabledAndSync(razonSocialInput, razonSocial);
            selectOptionIfEnabledAndSync(tipoFraccionamientoSelect, tipoFraccionamiento);

        }
        public void fillFormAsegurados(String cantidadAsegurado, String actividad, String subvencionPersona) {

            fillInputIfEnabledAndSync(cantidadAseguradoInput, cantidadAsegurado);
            lastActividadAttempt= actividadAutocomplete.selectAndValidateNotEmpty(actividad,6000,500);

            fillInputIfEnabledAndSync(subvencionPersonaInput, subvencionPersona);
        }
        public void clickAgregarAseguradoFola() {
            clickAndSync(agregarAseguradoFolaButton);
        }
        public void clickCotizarFolaButton() {
            clickAndSync(cotizarFolaButton);
        }
}
