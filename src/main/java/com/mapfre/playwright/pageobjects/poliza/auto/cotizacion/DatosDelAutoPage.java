package com.mapfre.playwright.pageobjects.poliza.auto.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.components.material.MaterialAutocomplete;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral.PerfilClientePage;
import com.mapfre.utils.waits.FirstAppearanceRaceMultiSuccess;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.regex.Pattern;

public class DatosDelAutoPage extends BasePage {

    private enum ModalesDeValidacion { MODAL_ALERTA, FORMULARIO_AUTO }

    private final Locator title;
    private final Locator numeroPlacaInput;
    private final Locator siguienteButton;
    private final Locator tipoVehiculoSelect;
    private final Locator marcaModeloInput;
    private final Locator marcaModeloCombo;
    private final MaterialAutocomplete marcaModeloAutocomplete;
    private MaterialAutocomplete.AttemptKind lastMarcaModeloAttempt;
    private final Locator versionInput;
    private final Locator anioFabricacionSelect;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombreInput;
    private final Locator apellidoPaternoInput;
    private final Locator apellidoMaternoInput;
    private final Locator diaSelect;
    private final Locator mesSelect;
    private final Locator anioSelect;
    private final Locator correoElectronicoInput;
    private final Locator sexoMasculinoCheckBox;
    private final Locator departamentoSelect;
    private final Locator provinciaSelect;
    private final Locator distritoSelect;
    private final Locator estadoVehiculoNuevoCheckBox;
    private final Locator modalAlerta;
    private final Locator formularioAuto;
    private final Locator modalError;
    private final Locator modalAlertaOKButton;
    public DatosDelAutoPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotización póliza de auto"));
        this.numeroPlacaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nro de placa"));
        this.siguienteButton = page.getByText("Siguiente");
        this.tipoVehiculoSelect = page.locator("oim-select[name='nTipoVehiculo'] select");
        this.marcaModeloInput = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Marca y Modelo"));
        this.marcaModeloCombo = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Marca y Modelo"));
        this.marcaModeloAutocomplete = new MaterialAutocomplete(page, marcaModeloCombo, "Marca y Modelo").setDebug(false);
        this.versionInput = page.locator("oim-input[name='nVersion'] input");
        this.anioFabricacionSelect = page.locator("oim-select[name='nYearFabric'] select");
        this.tipoDocumentoSelect = page.locator("oim-select[name='documentType'] select");
        this.numeroDocumentoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de documento"));
        this.nombreInput = page.locator("oim-input[name='Nombre'] input");
        this.apellidoPaternoInput = page.locator("oim-input[name='ApellidoPaterno'] input");
        this.apellidoMaternoInput = page.locator("oim-input[name='ApellidoMaterno'] input");
        this.diaSelect = page.locator("oim-select[name='day'] select");
        this.mesSelect = page.locator("oim-select[name='month'] select");
        this.anioSelect = page.locator("oim-select[name='year'] select");
        this.correoElectronicoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Correo electrónico"));
        this.sexoMasculinoCheckBox = page.locator("oim-radio").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Masculino")));
        this.estadoVehiculoNuevoCheckBox = page.locator("oim-radio").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Nuevo")));
        this.departamentoSelect = page.locator("oim-select[label='Departamento'] select");
        this.provinciaSelect = page.locator("oim-select[label='Provincia'] select");
        this.distritoSelect =  page.locator("oim-select[label='Distrito'] select");
        this.modalAlerta = page.locator("div.swal2-popup.swal2-icon-warning[role='dialog']");
        this.modalAlertaOKButton = modalAlerta.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("OK"));
        this.formularioAuto = page.locator("oim-select[name='nTipoVehiculo'] select");
        this.modalError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Cotización póliza de auto DEBE SER VISIBLE");
    }

    public void sendNumeroPlaca(String numero_placa){
        numeroPlacaInput.fill(numero_placa);
        clickAndSync(siguienteButton);
    }

    public void processAndAssertSuccessMultiplesModales(long timeoutMs, long quietMs) {
        Locator[] successLocators = new Locator[] { modalAlerta, formularioAuto };

        DatosDelAutoPage.ModalesDeValidacion[] successTypes = {
                DatosDelAutoPage.ModalesDeValidacion.MODAL_ALERTA,
                DatosDelAutoPage.ModalesDeValidacion.FORMULARIO_AUTO
        };

        var result = FirstAppearanceRaceMultiSuccess.waitForFirst(
                page,
                null,
                modalError,
                successLocators,
                null,
                timeoutMs,
                quietMs,
                150
        );

        if (result.outcome() == FirstAppearanceRaceMultiSuccess.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRaceMultiSuccess.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout: no apareció ERROR ni ninguna alternativa SUCCESS dentro de "
                    + timeoutMs + " ms (después de " + result.elapsedMs() + " ms).");
        }

        if (result.outcome() == FirstAppearanceRaceMultiSuccess.Outcome.SUCCESS) {
            DatosDelAutoPage.ModalesDeValidacion successType = successTypeFromIndex(result.successIndex(), successTypes);
            ejecutarAccionPosteriorRecibosPendientes(successType);
        }
    }

    private DatosDelAutoPage.ModalesDeValidacion successTypeFromIndex(int successIndex, DatosDelAutoPage.ModalesDeValidacion[] successTypes) {
        if (successIndex < 0 || successIndex >= successTypes.length) {
            throw new IllegalStateException("Índice de success no contemplado: " + successIndex);
        }
        return successTypes[successIndex];
    }

    private void ejecutarAccionPosteriorRecibosPendientes(DatosDelAutoPage.ModalesDeValidacion successType) {
        switch (successType) {
            case MODAL_ALERTA -> {
                log.info("[COTIZACION][DATOS DEL AUTO] Se muestra el modal 'ALERTA Los datos del catalogo son inconsitentes'");
                clickAndSync(modalAlertaOKButton);
                log.info("[COTIZACION][DATOS DEL AUTO]  Se hizo click en el boton OK del modal 'ALERTA Los datos del catalogo son inconsitentes'");
            }
            case FORMULARIO_AUTO -> {
                log.info("[COTIZACION][DATOS DEL AUTO] El formulario Datos del Auto es visible");
                clickIfEnabledAndSync(modalAlertaOKButton);
                log.info("COTIZACION][DATOS DEL AUTO] El formulario Datos del Auto es visible y se hizo click en el boton OK");
            }
        }
    }

    public void imprimirDatosDelAuto(String numeroPlaca, String numeroSerie, String numeroMotor){
        log.info("Número de placa Autogenerado: {}", numeroPlaca);
        log.info("Número de serie Autogenerado: {}", numeroSerie);
        log.info("Número de motor Autogenerado: {}", numeroMotor);
    }

    public void fillFormContrante(String tipoDocumento, String numeroDocumento, String nombre, String apPaterno, String apMaterno, String fechaNacimiento, String correoElectronico, String sexo, String departamento, String provincia, String distrito) {
        selectOptionIfEnabledAndSync(tipoDocumentoSelect, tipoDocumento);
        fillInputIfEnabledBlurAndSync(numeroDocumentoInput, numeroDocumento);

        waitAppReady();
        fillInputIfEnabledAndSync(nombreInput, nombre);
        fillInputIfEnabledAndSync(apellidoPaternoInput, apPaterno);
        fillInputIfEnabledAndSync(apellidoMaternoInput, apMaterno);

        String[] parts = fechaNacimiento.split("/");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];
        selectOptionIfEnabledAndSync(diaSelect,day);
        selectOptionIfEnabledAndSync( mesSelect, month);
        selectOptionIfEnabledAndSync( anioSelect, year);
        fillInputIfEmptyAndSync(correoElectronicoInput, correoElectronico);
        sexoMasculinoCheckBox.click();
        selectOptionIfEnabledAndSync(departamentoSelect, departamento);
        selectOptionIfEnabledAndSync(provinciaSelect, provincia);
        selectOptionIfEnabledAndSync(distritoSelect, distrito);
        scrollPageBy(0,10000);
    }
    public void clickFormContrante(){
        clickAndSync(siguienteButton);
    }

    public void fillFormBienAsegurado(String tipoVehiculo, String marcaModelo, String anioFabricacion) {

        selectAndSync(tipoVehiculoSelect, tipoVehiculo);
        //anioFabricacionSelect.selectOption(new SelectOption().setIndex(1));
        lastMarcaModeloAttempt = marcaModeloAutocomplete.selectExact(
                marcaModelo,
                6000, // timeoutMs
                500   // quietMs
        );
        log.info("Intento que funcionó en 'Marca y Modelo': " + lastMarcaModeloAttempt);
        selectAndSync(anioFabricacionSelect, anioFabricacion);
    }

    public void fillFormBienAsegurado(String tipoVehiculo, String marcaModelo, String anioFabricacion, String estadoVehiculo) {
        selectAndSync(tipoVehiculoSelect, tipoVehiculo);
        //anioFabricacionSelect.selectOption(new SelectOption().setIndex(1));
        lastMarcaModeloAttempt = marcaModeloAutocomplete.selectExact(
                marcaModelo,
                6000, // timeoutMs
                500   // quietMs
        );
        log.info("Intento que funcionó en 'Marca y Modelo': " + lastMarcaModeloAttempt);
        selectAndSync(anioFabricacionSelect, anioFabricacion);
        estadoVehiculoNuevoCheckBox.click();
    }
}