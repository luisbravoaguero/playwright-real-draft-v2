package com.mapfre.playwright.pageobjects.poliza.auto.emsion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.VehicleIdGenerator;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.regex.Pattern;

public class EmisionPolizaAutoNuevoPage extends BasePage {
    private final Locator title;
    private final Locator numeroPlacaInput;
    private final Locator numeroChasisInput;
    private final Locator numeroMotorInput;
    private final Locator colorVehiculoSelect;
    private final Locator siguienteButton;
    //Datos del contratante
    private final Locator diaSelect;
    private final Locator mesSelect;
    private final Locator anioSelect;
    private final Locator nombreInput;
    private final Locator apellidoPaternoInput;
    private final Locator apellidoMaternoInput;
    private final Locator numeroDocumentoContratante;
    private final Locator profesionSelect;
    private final Locator telefonoCasaInput;
    private final Locator telefonoMovilInput;
    private final Locator correoElectronicoInput;
    private final Locator departamentoSelect;
    private final Locator provinciaSelect;
    private final Locator distritoSelect;
    private final Locator nombreViaInput;
    private final Locator tipoViaSelect;
    private final Locator tipoUsoVehiculoSelect;
    private final Locator numeroAccidentesVehiculoSelect;
    private final Locator numeroAntiguedadLicenciaSelect;
    private final Locator tipoFinanciamientoSelect;
    private final Locator emitirPolizaButton;
    private final Locator resultadoMensajeExitoPolizaEmitida;
    private final Locator resultadoOKButton;
    private final Locator modalRootPolizaEmitida;
    private final Locator modalMsgPolizaEmitida;
    private final Locator modalOKButtonPolizaEmitida;
    private final Locator summaryHeaderPolizaEmitada;
    private final Locator resultadoMensajeError;
    public EmisionPolizaAutoNuevoPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Emisión póliza auto nuevo"));
        this.numeroPlacaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de placa"));
        this.numeroChasisInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de chasis"));
        this.numeroMotorInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de motor"));
        this.colorVehiculoSelect = page.locator("oim-select[name='nColor'] select");
        this.siguienteButton = page.getByText("Siguiente");
        //Datos del contratante
        this.diaSelect = page.locator("oim-select[name='day'] select");
        this.mesSelect = page.locator("oim-select[name='month'] select");
        this.anioSelect = page.locator("oim-select[name='year'] select");
        this.nombreInput = page.locator("oim-input[name='Nombre'] input");
        this.apellidoPaternoInput = page.locator("oim-input[name='ApellidoPaterno'] input");
        this.apellidoMaternoInput = page.locator("oim-input[name='ApellidoMaterno'] input");
        this.numeroDocumentoContratante = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nombres"));
        this.profesionSelect = page.locator("oim-select[name='Profesion'] select");
        this.telefonoCasaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono Casa"));
        this.telefonoMovilInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono móvil"));
        this.correoElectronicoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Correo electrónico"));
        this.departamentoSelect = page.locator("oim-select[label='Departamento'] select");
        this.provinciaSelect = page.locator("oim-select[label='Provincia'] select");
        this.distritoSelect =  page.locator("oim-select[label='Distrito'] select");
        this.tipoViaSelect =  page.locator("oim-select[name='Via'] select");
        this.nombreViaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nombre Vía"));
        this.tipoUsoVehiculoSelect = page.locator("oim-select[name='nUseVehicle'] select");
        this.numeroAccidentesVehiculoSelect = page.locator("oim-select[name='nAccidentesVehicle'] select");
        this.numeroAntiguedadLicenciaSelect = page.locator("oim-select[name='nAntiguedadLicencia'] select[matnativecontrol]");
        this.tipoFinanciamientoSelect = page.locator("oim-select[name='nFinaciamiento'] select");
        this.emitirPolizaButton = page.getByText("Emitir Poliza");
        this.resultadoMensajeExitoPolizaEmitida = page.locator("div.swal2-popup.swal2-icon-info[role='dialog']");
        this.resultadoOKButton = page.locator("button.swal2-confirm");
        this.modalRootPolizaEmitida = page.locator("div.swal2-popup[role='dialog']");
        this.modalMsgPolizaEmitida = modalRootPolizaEmitida.locator("#swal2-html-container");
        this.modalOKButtonPolizaEmitida = modalRootPolizaEmitida.locator("button.swal2-confirm");
        this.summaryHeaderPolizaEmitada = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Póliza emitida de autos"));
        this.resultadoMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");

    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Emisión póliza auto nuevo DEBE SER VISIBLE");
    }

    public void fillFormDatosDePoliza(String numero_placa){
        numeroPlacaInput.fill(numero_placa);
        numeroChasisInput.fill(VehicleIdGenerator.generateVin());
        numeroMotorInput.fill(VehicleIdGenerator.generateEngineNumber(12));
        selectIndexOptionAndSync(colorVehiculoSelect,2);
        clickAndSync(siguienteButton);
    }

    public void fillFormDatosDelContratanteSeccionDatosPrincipales(String nombreContratante, String apellidoPaterno, String apellidoMaterno, String fechaNacimiento, String sexo, String profesion) {
        waitAppReady();

        fillInputIfEnabledAndSync(nombreInput,nombreContratante);
        fillInputIfEnabledAndSync(apellidoPaternoInput,apellidoPaterno);
        fillInputIfEnabledAndSync(apellidoMaternoInput,apellidoMaterno);

        String[] parts = fechaNacimiento.split("/");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];
        diaSelect.selectOption(day);
        mesSelect.selectOption(month);
        anioSelect.selectOption(year);
        Locator generoRadioButton = page.locator("oim-radio").filter(new Locator.FilterOptions().setHasText(sexo.trim()));
        generoRadioButton.click();
        profesionSelect.selectOption(new SelectOption().setIndex(1));
    }

    public void fillFormDatosDelContratanteSeccionDatosDelContacto(String numeroTelefonoCasa, String telefonoMovil, String correoElectronico, String departamento, String procinvia, String distrito, String tipoDeVia, String nombreDeVia) {
        telefonoCasaInput.fill(numeroTelefonoCasa);
        telefonoMovilInput.fill(telefonoMovil);
        correoElectronicoInput.fill(correoElectronico);
        selectAndSync(departamentoSelect,departamento);
        selectAndSync(provinciaSelect,procinvia);
        selectAndSync(distritoSelect,distrito);
        tipoViaSelect.selectOption(new SelectOption().setIndex(2));
        nombreViaInput.fill(nombreDeVia);
    }

    public void fillFormDatosDelContratanteSeccionDatosDelVehiculo(String frecuenciaDeUso, String numeroDeSiniestrosAnteriores, String aniosDeAntiguedadDeLaLicencia, String responderAutoConducidoPorUnaPersona, String responderGuardaElAutoEnUnGaraje) {
        selectAndSync(tipoUsoVehiculoSelect,frecuenciaDeUso);
        selectAndSync(numeroAccidentesVehiculoSelect,numeroDeSiniestrosAnteriores);
        Pattern startsWithNumber = Pattern.compile("^\\s*" + aniosDeAntiguedadDeLaLicencia + "\\b");
        Locator numeroAntiguedadLicenciaOption = numeroAntiguedadLicenciaSelect.locator("option",new Locator.LocatorOptions().setHasText(startsWithNumber));
        numeroAntiguedadLicenciaOption.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        String NumeroAntiguedadValue = numeroAntiguedadLicenciaOption.getAttribute("value");
        numeroAntiguedadLicenciaSelect.selectOption(NumeroAntiguedadValue);
        setRadioYesNoByName("nGaraje", responderAutoConducidoPorUnaPersona);
        setRadioYesNoByName("nUnicoConductor", responderGuardaElAutoEnUnGaraje);
        log.info("[EMISION][DATOS DEL CONTRATANTE][SECCION DATOS DEL VEHICULO] Se completó el formulario de Datos del Contratante - Sección Datos del Vehículo");
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1500);
        clickAndSync(siguienteButton);
        log.info("[EMISION][DATOS DEL CONTRATANTE][SECCION DATOS DEL VEHICULO] Y se hizo click en el botón Siguiente");
    }

    public void processAndAssertSuccessClickBotonSiguiente(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                resultadoMensajeError,
                tipoFinanciamientoSelect,
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue

    }

    public void setRadioYesNoByName(String valorNameEtiqueta, String SiNo) {
        Locator radio = page.locator("input[type='radio'][name='%s'][value='%s']".formatted(valorNameEtiqueta, SiNo));
        radio.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        radio.check(); // or .click()
    }

    public void fillFormTipoDeFinanciamiento(String tipoFinanciamiento) {
        waitAppReady();
        selectAndSync(tipoFinanciamientoSelect,tipoFinanciamiento);
        scrollPageBy(0,1000);
        clickAndSync(siguienteButton);
    }

    public void fillFormEmitirPoliza() {
        waitAppReady();
        scrollPageBy(0,1000);
        emitirPolizaButton.click();
    }

    public void processAndAssertEmitirPolizaModalSuccess(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                modalRootPolizaEmitida,
                summaryHeaderPolizaEmitada,
                modalMsgPolizaEmitida,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );
        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            String allVisibleText = modalRootPolizaEmitida.innerText();
            if(allVisibleText.contains("error") || allVisibleText.contains("Error") || allVisibleText.contains("ERROR")){
                System.out.println("Se detectó un mensaje de error en el modal de emisión de póliza:");
                System.out.println(allVisibleText);
                ElementAsserts.assertUIMessage("Proceso falló. UI error: " + modalRootPolizaEmitida.innerText()+ result.errorMessage()
                        + " (después de " + result.elapsedMs() + " ms)");
            }else{
                clickAndSync(modalOKButtonPolizaEmitida);
                var resultAfterClose = FirstAppearanceRace.waitForFirst(
                        page,
                        null,               // action (coloca null si la accion click fue realizad)
                        modalRootPolizaEmitida,
                        summaryHeaderPolizaEmitada,
                        modalMsgPolizaEmitida,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                        timeoutMs,
                        quietMs,                 // quietMs (stability)
                        150                      // pollMs
                );
                if (resultAfterClose.outcome() == FirstAppearanceRace.Outcome.ERROR) {
                    System.out.println("Se detectó un mensaje de error en el modal de emisión de póliza:");
                    System.out.println(allVisibleText);
                    ElementAsserts.assertUIMessage("Proceso falló. UI error: " + modalRootPolizaEmitida.innerText()+ resultAfterClose.errorMessage()
                            + " (después de " + resultAfterClose.elapsedMs() + " ms)");
                }
                if (resultAfterClose.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
                    throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                            + " ms (después de" + resultAfterClose.elapsedMs() + " ms).");
                }

            }
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
    }


}

