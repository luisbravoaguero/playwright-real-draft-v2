package com.mapfre.playwright.pageobjects.poliza.vidaInversion.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CotizacionPolizaVidaInversionPage extends BasePage {
    private final Locator title;
    private final Locator oimPersonContratanteSection;
    private final Locator tipoDocumentoContratanteSelect;
    private final Locator numeroDocumentoContratanteInput;
    private final Locator nombreContratanteInput;
    private final Locator apellidoPaternoContratanteInput;
    private final Locator apellidoMaternoContratanteInput;
    private final Locator telefonoMovilContratanteInput;
    private final Locator correoElectronicoContratanteInput;
    private final Locator contranteAseguradoIgualcheckBox;
    private final Locator oimPersonAseguradoSection;
    private final Locator tipoDocumentoAseguradoSelect;
    private final Locator numeroDocumentoAseguradoInput;
    private final Locator nombreAseguradoInput;
    private final Locator apellidoPaternoAseguradoInput;
    private final Locator apellidoMaternoAseguradoInput;
    private final Locator diaNacimientoAseguradoSelect;
    private final Locator mesNacimientoAseguradoSelect;
    private final Locator anioNacimientoAseguradoSelect;
    private final Locator gestorSupervisorSelect;
    private final Locator agenteAutoCompleteInput;
    private final Locator tipoProductoSelect;
    private final Locator tipoMonedaSelect;
    private final Locator aniosDuracionSeguroSelect;
    private final Locator porcentajeDevolucionSelect;
    private final Locator primaComercialUnicaInput;
    private final Locator diferimientoPagoInput;
    private final Locator periodicidadPagoRentaSelect;
    private final Locator codigoPromocionalInput;
    private final Locator registrarButton;
    private final Locator activarSeguroButton;
    private final Locator cotizarButton;
    private final Locator checkboxesInTable;
    private final Locator resultadoModalMensajeError;
    private final Locator dataRowListCotizacion;
    private final Locator headerRowNumeroCotizacion;
    private final Locator emisionPolizaVidaTitle;
    private final Locator modalidadSelect;
    public CotizacionPolizaVidaInversionPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotización Póliza Vida"));
        this.oimPersonContratanteSection = page.locator("oim-person[name='contratante']");
        this.tipoDocumentoContratanteSelect = oimPersonContratanteSection.locator("oim-select[name='documentType'] select");
        this.numeroDocumentoContratanteInput = oimPersonContratanteSection.locator("oim-input[name='documentNumber'] input");
        this.nombreContratanteInput = oimPersonContratanteSection.locator("oim-input[name='Nombre'] input");
        this.apellidoPaternoContratanteInput = oimPersonContratanteSection.locator("oim-input[name='ApellidoPaterno'] input");
        this.apellidoMaternoContratanteInput = oimPersonContratanteSection.locator("oim-input[name='ApellidoMaterno'] input");
        this.telefonoMovilContratanteInput = page.locator("oim-input[name='Telefono2'] input");
        this.correoElectronicoContratanteInput = page.locator("oim-input[name='CorreoElectronico'] input");
        this.contranteAseguradoIgualcheckBox = page.getByText("Los datos del asegurado son");
        this.oimPersonAseguradoSection = page.locator("oim-person[name='asegurado']");
        this.tipoDocumentoAseguradoSelect = oimPersonAseguradoSection.locator("oim-select[name='documentType'] select");
        this.numeroDocumentoAseguradoInput = oimPersonAseguradoSection.locator("oim-input[name='documentNumber'] input");
        this.nombreAseguradoInput = oimPersonAseguradoSection.locator("oim-input[name='Nombre'] input");
        this.apellidoPaternoAseguradoInput = oimPersonAseguradoSection.locator("oim-input[name='ApellidoPaterno'] input");
        this.apellidoMaternoAseguradoInput = oimPersonAseguradoSection.locator("oim-input[name='ApellidoMaterno'] input");
        this.diaNacimientoAseguradoSelect = oimPersonAseguradoSection.locator("oim-select[name='day'] select");
        this.mesNacimientoAseguradoSelect = oimPersonAseguradoSection.locator("oim-select[name='month'] select");
        this.anioNacimientoAseguradoSelect = oimPersonAseguradoSection.locator("oim-select[name='year'] select");
        this.gestorSupervisorSelect = page.locator("oim-select[name='nGestorSupervisor'] select");
        this.agenteAutoCompleteInput = page.locator("oim-autocomplete[name='mAgente'] input");
        this.tipoProductoSelect = page.locator("oim-select[name='nProductoVidaInv'] select");
        this.tipoMonedaSelect = page.locator("oim-select[name='mMoneda'] select");
        this.aniosDuracionSeguroSelect = page.locator("oim-select[name='nDuracionSeguro'] select");
        this.porcentajeDevolucionSelect = page.locator("oim-select[name='nDevolucion'] select");
        this.primaComercialUnicaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Prima comercial única"));
        this.diferimientoPagoInput = page.locator("oim-input[name='nDiferimiento'] input");
        this.periodicidadPagoRentaSelect = page.locator("oim-select[name='nPeriocidad'] select");
        this.codigoPromocionalInput = page.locator("oim-input[name='nCodigoPromocion'] input");
        this.registrarButton = page.getByText("REGISTRAR");
        this.activarSeguroButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Seleccionar o deseleccionar"));
        this.cotizarButton = page.getByText("Cotizar", new Page.GetByTextOptions().setExact(true));
        this.checkboxesInTable = page.locator("ul oim-checkbox mat-checkbox input[type='checkbox']");
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.dataRowListCotizacion = page.locator("div.mb-xs-1.gnContentAuto-bg ul.g-tbl-row");
        this.headerRowNumeroCotizacion = page.getByText("Número de Cotización");
        this.emisionPolizaVidaTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Emisión póliza vida"));
        this.modalidadSelect = page.locator("oim-select[name='nProducto'] select");
    }
    public void assertLoaded() {ElementAsserts.assertVisible(title, "TITULO Cotización Póliza Vida Inversión DEBE SER VISIBLE");}

    public void fillFormularioPasoDatosPolizaSeccionDatosContratante(String tipoDocumento, String numeroDocumento, String nombre, String apellidoPaterno, String apellidoMaterno, String telefonoMovil, String correoElectronico) {
        oimPersonContratanteSection.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        selectAndSync(tipoDocumentoContratanteSelect, tipoDocumento);
        fillAndBlurSync(numeroDocumentoContratanteInput, numeroDocumento);
        fillInputIfEnabledAndSync(nombreContratanteInput, nombre);
        fillInputIfEnabledAndSync(apellidoPaternoContratanteInput, apellidoPaterno);
        fillInputIfEnabledAndSync(apellidoMaternoContratanteInput, apellidoMaterno);
        fillInputValueIfEnabledAndEmptyAndSync(telefonoMovilContratanteInput, telefonoMovil);
        fillInputValueIfEnabledAndEmptyAndSync(correoElectronicoContratanteInput, correoElectronico);
        log.info("[COTIZACION][DATOS DE LA POLIZA] Se lleno el formulario de Datos del Contratante");
    }

    public void activarOpcionDatosAseguradoIgualesContratante() {
        clickAndSync(contranteAseguradoIgualcheckBox);
        log.info("[COTIZACION][DATOS DE LA POLIZA] Se activo la opcion de que los datos del asegurado sean iguales al contratante");
    }

    public void validarDatosAseguradoIgualesContratante() {
        oimPersonAseguradoSection.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        ElementAsserts.assertLocatorValuesAreEqual(tipoDocumentoAseguradoSelect, tipoDocumentoContratanteSelect, "EL TIPO DE DOCUMENTO DEL ASEGURADO DEBE SER IGUAL AL CONTRATANTE");
        ElementAsserts.assertLocatorValuesAreEqual(numeroDocumentoContratanteInput, numeroDocumentoAseguradoInput, "EL NUMERO DE DOCUMENTO DEL ASEGURADO DEBE SER IGUAL AL CONTRATANTE");
        ElementAsserts.assertLocatorValuesAreEqual(nombreContratanteInput, nombreAseguradoInput, "EL NOMBRE DEL ASEGURADO DEBE SER IGUAL AL CONTRATANTE");
        ElementAsserts.assertLocatorValuesAreEqual(apellidoPaternoAseguradoInput, apellidoPaternoAseguradoInput, "EL APELLIDO PATERNO DEL ASEGURADO DEBE SER IGUAL AL CONTRATANTE");
        ElementAsserts.assertLocatorValuesAreEqual(apellidoMaternoAseguradoInput, apellidoMaternoAseguradoInput, "EL APELLIDO MATERNO DEL ASEGURADO DEBE SER IGUAL AL CONTRATANTE");
        ElementAsserts.assertFilled(diaNacimientoAseguradoSelect, "EL DIA DE NACIMIENTO DEL ASEGURADO DEBE ESTAR LLENO");
        ElementAsserts.assertFilled(mesNacimientoAseguradoSelect, "EL MES DE NACIMIENTO DEL ASEGURADO DEBE ESTAR LLENO");
        ElementAsserts.assertFilled(anioNacimientoAseguradoSelect, "EL AÑO DE NACIMIENTO DEL ASEGURADO DEBE ESTAR LLENO");
        log.info("[COTIZACION][DATOS DE LA POLIZA] Se valido que los datos del asegurado sean iguales al contratante");
    }

    public void fillFormularioDatosDelAsesor(String gestorSupervisor, String agente) {
        selectAndSync(gestorSupervisorSelect, gestorSupervisor);
        log.info("[COTIZACION][DATOS DE LA POLIZA] Se selecciono el Gestor Supervisor: {}", gestorSupervisor);
        selectFromMatAutocompleteFailFastIfEmpty(agenteAutoCompleteInput, agente,"AGENTE","mat-option[role='option']");
        log.info("[COTIZACION][DATOS DE LA POLIZA] Se selecciono el Agente: {}", agente);
    }

    public void fillFormularioPasoDatosPolizaSeccionCaracteristicasDelSeguro(String tipoProducto, String tipoMoneda, String aniosDuracion, String porcentajeDevolucion, String primaComercialUnica, String diferimientoPago, String periocidadPagoRenta, String codigoPromocion) {
        selectAndSync(tipoProductoSelect, tipoProducto);
        selectAndSync(tipoMonedaSelect, tipoMoneda);
        selectAndSync(aniosDuracionSeguroSelect, aniosDuracion);
        selectAndSync(porcentajeDevolucionSelect, porcentajeDevolucion);
        fillAndBlurSync(primaComercialUnicaInput, primaComercialUnica);
        fillAndBlurSync(diferimientoPagoInput, diferimientoPago);
        selectOptionIfEnabledAndSync(periodicidadPagoRentaSelect, periocidadPagoRenta);
        fillIfFieldIsNotOptional(codigoPromocionalInput, codigoPromocion);
        log.info("[COTIZACION][DATOS DE LA POLIZA] Se lleno el formulario de Caracteristicas del Seguro");
    }

    public void fillFormularioPasoDatosPolizaSeccionCaracteristicasDelSeguroCertivida(String tipoProducto, String tipoMoneda, String aniosDuracion, String modalidad, String primaComercialUnica, String codigoPromocion) {
        selectAndSync(tipoProductoSelect, tipoProducto);
        selectAndSync(tipoMonedaSelect, tipoMoneda);
        selectAndSync(aniosDuracionSeguroSelect, aniosDuracion);
        selectAndSync(modalidadSelect, modalidad);
        fillAndBlurSync(primaComercialUnicaInput, primaComercialUnica);
        fillIfFieldIsNotOptional(codigoPromocionalInput, codigoPromocion);
        log.info("[COTIZACION][DATOS DE LA POLIZA] Se lleno el formulario de Caracteristicas del Seguro para el producto Certivida");
    }

    public void processAndAssertSucessRegistrarNuevaCarateristicaDelSeguro(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                registrarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                activarSeguroButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[COTIZACION][DATOS DE LA POLIZA] Se proceso el registro de la nueva caracteristica del seguro");
    }

    public void activarCheckBoxSeleccionarTodo() {
        clickAndSync(activarSeguroButton);
        verifyCheckboxesIsChecked(checkboxesInTable,"Registrar nueva característica del seguro");
        ElementAsserts.assertVisible(cotizarButton, "EL BOTON COTIZAR DEBE SER VISIBLE DESPUES DE ACTIVAR EL CHECKBOX SELECCIONAR TODO");
        log.info("[COTIZACION][DATOS DE LA POLIZA] Se activo el checkbox de seleccionar todo para las caracteristicas del seguro");
    }
    public void processAndAssertSucessCotizacion(long timeoutMs, long quietMs){
        var result = FirstAppearanceRace.waitForFirst(
                page,
                cotizarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                headerRowNumeroCotizacion,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        int dataRowCount = dataRowListCotizacion.count();
        for(int i=0; i<dataRowCount; i++){
            Locator dataRow = dataRowListCotizacion.nth(i);
            Locator valueCell = dataRow.locator("li").nth(1);
            ElementAsserts.assertCellNotEmpty(valueCell, "LA EL NUMERO DE COTIZACION EN LA FILA "+ (i+1) +" NO DEBE ESTAR VACIA");
        }
        log.info("[COTIZACION][RESULTADO] Se proceso la cotizacion y se visualiza el numero de cotizacion");
    }
    public void activarEmisionCheckBox(){
        Locator checkboxCell = getCheckboxCellCard(1);
        Locator matCheckbox = getMatCheckboxCard(checkboxCell);
        ElementAsserts.assertVisible(matCheckbox, "El mat-checkbox dentro de la celda del checkbox DEBE SER VISIBLE");
        clickAndSync(matCheckbox);
        log.info("COTIZACION][RESULTADO] Se activo el checkbox para iniciar la emision de la poliza de vida");
    }

    public void processAndAssertSucessInicioEmision(long timeoutMs, long quietMs) {
        Locator checkboxCell = getCheckboxCellCard(1);
        Locator emitirButton = checkboxCell.getByText("Emitir", new Locator.GetByTextOptions().setExact(true));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                emitirButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                emisionPolizaVidaTitle,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[COTIZACION][RESULTADO] Se proceso el inicio de emision de la poliza de vida inversion");
    }

    public Locator getMatCheckboxCard(Locator checkboxCell) {
        return checkboxCell.locator("mat-checkbox");
    }

    public Locator getCheckboxCellCard(int rowNumber) {
        return page.locator("div.mb-xs-1.gnContentAuto-bg ul.g-tbl-row").nth(rowNumber - 1).locator("li[style*='inline-flex']");
    }


    //queeeeeeeeeeeeeeeeeeee

}
