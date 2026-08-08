package com.mapfre.playwright.pageobjects.poliza.camposanto.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CotizacionCampoSantoNecesidadFuturaPage extends BasePage {

    private final Locator title;
    private final Locator necesidadFuturaButton;
    private final Locator necesidadFuturaContadoButton;
    private final Locator camposantoProductoSelect;
    private final Locator tipoContratoSelect;
    private final Locator modalidadSelect;
    private final Locator productoSelect;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombreInput;
    private final Locator apellidoPaternoInput;
    private final Locator apellidoMaternoInput;
    private final Locator diaSelect;
    private final Locator mesSelect;
    private final Locator anioSelect;
    private final Locator estadoCivilSelect;
    private final Locator telefonoCasaInput;
    private final Locator telefonoMovilInput;
    private final Locator correoElectronicoInput;
    private final Locator departamentoSelect;
    private final Locator provinciaSelect;
    private final Locator distritoSelect;
    private final Locator direccionInput;
    private final Locator generarCotizacionButton;
    private final Locator modalGuardarCotizacionRoot;
    private final Locator modalGuardarCotizacionIcon;
    private final Locator modalGuardarCotizacionContainer;
    private final Locator modalGuardarCotizacionOKButton;
    private final Locator modalGuardarCotizacionErrorRoot;
    private final Locator modalNuevaCotizacionCreadaSuccess;
    private final Locator titleCotizadorDeCamposanto;
    public CotizacionCampoSantoNecesidadFuturaPage(com.microsoft.playwright.Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotizador de Camposanto"));
        this.necesidadFuturaButton = page.getByText("Necesidad Futura");
        this.necesidadFuturaContadoButton = page.getByText("CONTADO");
        this.camposantoProductoSelect = page.locator("oim-select[name='nCamposanto'] select");
        this.tipoContratoSelect = page.locator("oim-select[name='nTipoContrato'] select");
        this.modalidadSelect = page.locator("oim-select[name='nModalidad'] select");
        this.productoSelect = page.locator("oim-select[name='nProducto'] select");
        this.tipoDocumentoSelect = page.locator("oim-select[name='documentType'] select");
        this.numeroDocumentoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de documento"));
        this.nombreInput = page.locator("oim-input[name='Nombre'] input");
        this.apellidoPaternoInput = page.locator("oim-input[name='ApellidoPaterno'] input");
        this.apellidoMaternoInput = page.locator("oim-input[name='ApellidoMaterno'] input");
        this.diaSelect = page.locator("oim-select[name='day'] select");
        this.mesSelect = page.locator("oim-select[name='month'] select");
        this.anioSelect = page.locator("oim-select[name='year'] select");
        this.estadoCivilSelect = page.locator("oim-select[name='civilState'] select");
        this.telefonoCasaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono Casa"));
        this.telefonoMovilInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono móvil"));
        this.correoElectronicoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Correo electrónico"));
        this.departamentoSelect = page.locator("oim-select[label='Departamento'] select");
        this.provinciaSelect = page.locator("oim-select[label='Provincia'] select");
        this.distritoSelect =  page.locator("oim-select[label='Distrito'] select");
        this.direccionInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Dirección"));
        this.generarCotizacionButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("GENERAR COTIZACIÓN"));
        this.modalGuardarCotizacionRoot = page.locator("div.swal2-popup.swal2-modal[role='dialog']");
        this.modalGuardarCotizacionIcon = modalGuardarCotizacionRoot.locator("div.swal2-icon.swal2-warning.swal2-icon-show");
        this.modalGuardarCotizacionContainer = modalGuardarCotizacionRoot.locator("#swal2-html-container");
        //this.modalGuardarCotizacionOKButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK"));
        this.modalGuardarCotizacionOKButton = modalGuardarCotizacionRoot.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("OK"));
        this.modalGuardarCotizacionErrorRoot = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.modalNuevaCotizacionCreadaSuccess = page.locator("div.swal2-popup.swal2-modal.swal2-icon-success[role='dialog']");
        this.titleCotizadorDeCamposanto = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotizador de Camposanto"));

    }
    public void assertLoadedCotizacionCampoSantoNecesidadFuturaPage() {
        ElementAsserts.assertVisible(title, "TITULO Cotizador de Camposanto DEBE SER VISIBLE");
    }

    public void clickNecesidadFuturaButton() {
        clickAndSync(necesidadFuturaButton);
    }

    public void clickNecesidadFuturaContadoButton() {
        clickAndSync(necesidadFuturaContadoButton);
    }

    public void fillFormDatosProducto(String camposantoProducto, String tipoContrato, String modalidad, String producto) {
        selectAndSync(camposantoProductoSelect,camposantoProducto);
        selectAndSync(tipoContratoSelect,tipoContrato);
        selectAndSync(modalidadSelect,modalidad);
        selectAndSync(productoSelect,producto);
    }

    public void fillFormDatosCliente(String tipoDoumento, String numeroDocumento, String nombre, String apPaterno, String apMaterno, String fechaNacimiento, String estadoCivil, String telefonoCasa, String telefonoMovil, String correoElectronico, String departamento, String provincia, String distrito, String direccion) {
        selectAndSync(tipoDocumentoSelect,tipoDoumento);
        fillAndBlurSync(numeroDocumentoInput, numeroDocumento);
        fillInputIfEnabledAndSync(nombreInput, nombre);
        fillInputIfEnabledAndSync(apellidoPaternoInput, apPaterno);
        fillInputIfEnabledAndSync(apellidoMaternoInput, apMaterno);
        String[] fechaNacimientoParts = fechaNacimiento.split("/");
        if (fechaNacimientoParts.length == 3) {
            String dia = fechaNacimientoParts[0];
            String mes = fechaNacimientoParts[1];
            String anio = fechaNacimientoParts[2];
            selectOptionIfEnabledAndSync(diaSelect, dia);
            selectOptionIfEnabledAndSync(mesSelect, mes);
            selectOptionIfEnabledAndSync(anioSelect, anio);
        } else {
            System.out.println("Formato de fecha de nacimiento inválido: " + fechaNacimiento);
        }
        selectOptionIfEnabledAndSync(estadoCivilSelect, estadoCivil);
        fillInputIfEmptyAndSync(telefonoCasaInput, telefonoCasa);
        fillInputIfEmptyAndSync(telefonoMovilInput, telefonoMovil);
        fillInputIfEmptyAndSync(correoElectronicoInput, correoElectronico);
        selectOptionIfEnabledAndSync(departamentoSelect, departamento);
        selectOptionIfEnabledAndSync(provinciaSelect, provincia);
        selectOptionIfEnabledAndSync(distritoSelect, distrito);
        fillInputIfEmptyAndSync(direccionInput, direccion);
        clickAndSync(generarCotizacionButton);
    }

    // Esperar a que el modal de guardar cotización aparezca
    public void processAndAssertCotizacionSuccess(long timeoutMs, long quietMs){
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                modalGuardarCotizacionErrorRoot,
                modalGuardarCotizacionIcon,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + modalGuardarCotizacionErrorRoot.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");

        }
            if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
                ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                        + " ms (después de" + result.elapsedMs() + " ms).");
            }
            // SUCCESS: continue
        ElementAsserts.assertVisible(modalGuardarCotizacionOKButton, "Botón OK del modal de cotización guardada debe ser visible");
        clickAndSync(modalGuardarCotizacionOKButton);
        }

    public void processAndAssertCotizacionPaso2Success(long timeoutMs, long quietMs){
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                modalGuardarCotizacionErrorRoot,
                modalNuevaCotizacionCreadaSuccess,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + modalGuardarCotizacionErrorRoot.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");

        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        if(modalNuevaCotizacionCreadaSuccess.textContent().contains("NUEVA COTIZACION CREADA")){
            ElementAsserts.assertVisible(modalGuardarCotizacionOKButton, "Botón OK del modal de cotización guardada debe ser visible");
            clickAndSync(modalGuardarCotizacionOKButton);
        }else{
            ElementAsserts.assertUIMessage("El modal de éxito no contiene el mensaje esperado. Texto actual: " + modalNuevaCotizacionCreadaSuccess.textContent());
        }

    }
    public void processAndAssertCotizacionPaso3Success(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                modalGuardarCotizacionErrorRoot,
                titleCotizadorDeCamposanto,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + modalGuardarCotizacionErrorRoot.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");

        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
    }
}
