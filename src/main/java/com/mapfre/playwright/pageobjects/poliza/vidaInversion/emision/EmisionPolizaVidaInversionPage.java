package com.mapfre.playwright.pageobjects.poliza.vidaInversion.emision;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class EmisionPolizaVidaInversionPage extends BasePage {
    private final Locator emisionPolizaVidaTitle;
    private final Locator estadoCivilSelect;
    private final Locator paisResidencialFiscalSelect;
    private final Locator prefijoTelefonoInput;
    private final Locator telefonoCasaInput;
    private final Locator telefonoOficinaInput;
    private final Locator telefonoMovilInput;
    private final Locator paisNatalSelect;
    private final Locator departamentoSelect;
    private final Locator provinciaSelect;
    private final Locator distritoSelect;
    private final Locator tipoViaSelect;
    private final Locator nombreViaSelect;
    private final Locator diaNacimientoSelect;
    private final Locator mesNacimientoSelect;
    private final Locator anioNacimientoSelect;
    private final Locator profesionSelect;
    private final Locator ocupacionSelect;
    private final Locator centroTrabajoInput;
    private final Locator aseguradoEsContranteCheckBox;
    private final Locator siguienteButton;
    private final Locator datosBancariosPagoRentatitle;
    private final Locator entidadFinancieraSelect;
    private final Locator tipoCuentaSelect;
    private final Locator numeroCuentaInput;
    private final Locator codigoGestorSelect;
    private final Locator documentosRequeridosTitle;
    private final Locator documentosObligatoriosCheckBox;
    private final Locator emitirPolizaButton;
    private final Locator correoElectronicoContratanteInput;
    private final Locator resultadoModalMensajeError;
    private final Locator beneficiarioCasoMuertetitle;
    private final Locator declaracionPersonalSaludTitle;
    private final Locator declaracionPersonalSaludRadioButton;
    private final Locator observacionesAdicionalesInput;
    private final Locator grabarDpsButton;
    private final Locator modalGrabarDps;
    private final Locator guardarModalButton;
    private final Locator modalAprobacionGrabarDps;
    private final Locator OKModalButton;
    private final Locator resultadoDpsAprobada;
    private final Locator declaracionPersonalSaludQuestionList;
    private final Locator polizaDeVidaEmitidatitle;
    public EmisionPolizaVidaInversionPage(Page page) {
        super(page);
        this.emisionPolizaVidaTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Emisión póliza vida"));
        this.estadoCivilSelect = page.locator("oim-select[name='civilState'] select");
        this.paisResidencialFiscalSelect = page.locator("oim-select[name='residenceCountryFiscal'] select");
        this.prefijoTelefonoInput = page.locator("oim-input[name='prefijo'] input");
        this.telefonoCasaInput = page.locator("oim-input[name='Telefono'] input");
        this.telefonoOficinaInput = page.locator("oim-input[name='TelefonoOficina'] input");
        this.telefonoMovilInput = page.locator("oim-input[name='Telefono2'] input");
        this.paisNatalSelect = page.locator("oim-select[name='nativeCountry'] select");
        this.departamentoSelect = page.locator("oim-select[name='Department'] select");
        this.provinciaSelect = page.locator("oim-select[name='Province'] select");
        this.distritoSelect = page.locator("oim-select[name='District'] select");
        this.tipoViaSelect = page.locator("oim-select[name='Via'] select");
        this.nombreViaSelect = page.locator("oim-input[name='NombreVia'] input");
        this.diaNacimientoSelect = page.locator("oim-select[name='day'] select");
        this.mesNacimientoSelect = page.locator("oim-select[name='month'] select");
        this.anioNacimientoSelect = page.locator("oim-select[name='year'] select");
        this.profesionSelect = page.locator("oim-select[name='Profesion'] select");
        this.ocupacionSelect = page.locator("oim-select[name='Ocupacion'] select");
        this.aseguradoEsContranteCheckBox = page.locator("oim-checkbox[name='nIgualAsegurado'] input");
        this.centroTrabajoInput = page.locator("oim-input[name='nCentroTrabajo'] input");
        this.siguienteButton = page.getByText("Siguiente");
        this.datosBancariosPagoRentatitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Datos Bancarios para el Pago de Renta").setLevel(2));
        this.beneficiarioCasoMuertetitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Beneficiarios en caso de muerte").setLevel(2));
        this.entidadFinancieraSelect = page.locator("oim-select[name='nEntidad'] select");
        this.tipoCuentaSelect = page.locator("oim-select[name='nTipoCuenta'] select");
        this.numeroCuentaInput = page.locator("oim-input[name='nCalcular'] input");
        this.codigoGestorSelect = page.locator("oim-select[name='nCodigoGestorEn'] select");
        this.documentosRequeridosTitle = page.locator("b:has-text('Documentos Requeridos')");
        this.declaracionPersonalSaludTitle = page.locator("b:has-text('Declaración Personal de Salud')");
        this.documentosObligatoriosCheckBox =  page.locator("li:has(span:has-text('(Obligatorio)')) mat-checkbox");
        this.emitirPolizaButton = page.getByText("Emitir póliza");
        this.correoElectronicoContratanteInput = page.locator("oim-input[name='CorreoElectronico'] input");
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.declaracionPersonalSaludRadioButton = page.locator("mat-radio-button:has-text('NO')");
        this.declaracionPersonalSaludQuestionList = page.locator("polizas-vida-question-item");
        this.observacionesAdicionalesInput = page.locator("textarea[matinput]");
        this.grabarDpsButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("GRABAR DPS"));
        this.modalGrabarDps = page.locator("div.swal2-popup.swal2-icon-info[role='dialog']");
        this.guardarModalButton = modalGrabarDps.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("GRABAR"));
        this.modalAprobacionGrabarDps = page.locator("div.swal2-popup.swal2-icon-success[role='dialog']");
        this.OKModalButton = modalAprobacionGrabarDps.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("OK"));
        this.resultadoDpsAprobada = page.getByText("APROBADA.");
        this.polizaDeVidaEmitidatitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Póliza de vida").setLevel(2));
    }

    public void emisionPolizaVidaTitle(){
        ElementAsserts.assertVisible(emisionPolizaVidaTitle, "TITULO Emisión póliza vida DEBE SER VISIBLE");
        log.info("[EMISION][DATOS DEL CONTRATANTE] Se muestra la pagina Emisión poliza vida");
    }

    public void fillFormularioDatosPrincipalesContratante(String fechaNacimiento, String estadoCivil, String residenciaFiscal) {
        selectBornDateFromStringAndSync(diaNacimientoSelect, mesNacimientoSelect, anioNacimientoSelect, fechaNacimiento);
        selectOptionIfEnabledAndSync(estadoCivilSelect, estadoCivil);
        selectOptionIfEnabledAndEmptyAndSync(paisResidencialFiscalSelect, residenciaFiscal);
        log.info("[EMISION][DATOS DEL CONTRATANTE] Se completo los datos principales del contratante");
    }

    public void fillFormularioDatosLaboralesContratante(String sexo, String profesion, String ocupacion) {
        clickIfEnabledAndSync(getSexoRadioButton(sexo));
        selectAndSync(profesionSelect, profesion);
        selectAndSync(ocupacionSelect, ocupacion);
        log.info("[EMISION][DATOS DEL CONTRATANTE] Se completo los datos laborales del contratante");
    }
    private Locator getSexoRadioButton(String sexo) {
        return page.locator("mat-radio-button:has-text('" + sexo + "')");
    }
    public void fillFormularioDatosContactoContratante(String prefijoTelefono, String telefonoCasa, String telefonoOficina, String telefonoMovil, String correoElectronico) {
        fillInputValueIfEnabledAndEmptyAndSync(prefijoTelefonoInput, prefijoTelefono);
        fillInputNewValueIfEnabledAndSync(telefonoCasaInput, telefonoCasa);
        fillIfFieldIsNotOptional(telefonoOficinaInput,telefonoOficina);
        fillIfFieldIsNotOptional(telefonoMovilInput,telefonoMovil);
        fillInputValueIfEnabledAndEmptyAndSync(correoElectronicoContratanteInput, correoElectronico);
        log.info("[EMISION][DATOS DEL CONTRATANTE] Se completo los datos de contacto del contratante");
    }

    public void fillFormularioDatosDireccionContratante(String paisNatal, String departamento, String provincia, String distrito, String tipoVia, String nombreVia) {
        selectOptionIfEnabledAndEmptyAndSync(paisNatalSelect, paisNatal);
        selectOptionIfEnabledAndEmptyAndSync(departamentoSelect, departamento);
        selectOptionIfEnabledAndEmptyAndSync(provinciaSelect, provincia);
        selectOptionIfEnabledAndEmptyAndSync(distritoSelect, distrito);
        selectOptionIfEnabledAndEmptyAndSync(tipoViaSelect, tipoVia);
        selectOptionIfEnabledAndEmptyAndSync(nombreViaSelect, nombreVia);
        log.info("[EMISION][DATOS DEL CONTRATANTE] Se completo los datos de direccion del contratante");
    }

    public void activarOpcionAseguradoEsContratante() {
        clickCheckboxIfNotChecked(aseguradoEsContranteCheckBox);
        log.info("[EMISION][DATOS DEL ASEGURADO] Se activo la opcion Asegurado es Contratante");
    }

    public void fillFormularioCentroTrabajoAsegurado(String centroTrabajo) {
        fillAndSync(centroTrabajoInput, centroTrabajo);
        log.info("[EMISION][DATOS DEL ASEGURADO] Se completo el centro de trabajo del asegurado");
    }

    public void processAndAssertSucessDatosContratanteAegurado(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                datosBancariosPagoRentatitle,
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
        log.info("[EMISION][PROCESAR DATOS DEL CONTRANTE Y ASEGURADO] Se completo con exito el paso de datos del contratante y asegurado, se mostro el titulo de Datos Bancarios para el Pago de Renta");
    }

    public void processAndAssertSucessDatosContratanteAeguradoCertivida(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                beneficiarioCasoMuertetitle,
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
            log.info("[EMISION][PROCESAR DATOS DEL CONTRANTE Y ASEGURADO] Se completo con exito el paso de datos del contratante y asegurado, se mostro el titulo de Beneficiarios en caso de muerte");
    }

    public void fillFormularioDatosBancariosPagoRenta(String entidadFinanciera, String tipoCuenta, String numeroCuenta) {
        selectAndSync(entidadFinancieraSelect, entidadFinanciera);
        selectAndSync(tipoCuentaSelect, tipoCuenta);
        fillAndBlurSync(numeroCuentaInput, numeroCuenta);
        selectAndSync(codigoGestorSelect, entidadFinanciera);
        log.info("[EMISION][DATOS DE LA POLIZA] Se completo el formulario de datos bancarios para el pago de renta");
    }

    public void processAndAssertSucessDatosDePoliza(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                documentosRequeridosTitle,
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
        log.info("[EMISION][PROCESAR DATOS DE LA POLIZA] Se completo con exito el paso de datos de la poliza, se mostro el titulo de Documentos Requeridos");
    }

    public void processAndAssertSucessDatosDePolizaCertirenta(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                declaracionPersonalSaludTitle,
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
        log.info("[EMISION][PROCESAR DATOS DE LA POLIZA] Se completo con exito el paso de datos de la poliza, se mostro el titulo de Declaración Personal de Salud");
    }

    public void seleccionarDocumentosRequeridos() {
        clickCheckBoxList(documentosObligatoriosCheckBox);
        log.info("[EMISION][DOCUMENTOS REQUERIDOS] Se selecciono los documentos obligatorios para la emision de la poliza");
    }

    public void processAndAssertSucessEmitirPoliza(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                emitirPolizaButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                polizaDeVidaEmitidatitle,
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
        log.info("[EMISION][EMITIR POLIZA] Se completo con exito el proceso de emisión de la poliza, se mostro el titulo de Poliza de Vida");
    }

    public void fillFormularioDeclaracionPersonalSalud() {
        clickCheckBoxList(declaracionPersonalSaludQuestionList, declaracionPersonalSaludRadioButton);
        //selectAllNoWithAggressiveScrollFrom37();
        fillAndSync(observacionesAdicionalesInput, "NINGUNA OBSERVACION ADICIONAL");
        log.info("[EMISION][DECLARACION PERSONAL DE SALUD] Se completo el formulario de declaracion personal de salud");
    }

    public void processAndAssertSucessGrabarDpsButton(long timeoutMs, long quietMs) {
        grabarDpsButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                grabarDpsButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                modalGrabarDps,
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
            log.info("[EMISION][GRABAR DPS] Se completo con exito el proceso de grabar dps, se mostro el modal de grabar dps");
    }

    public void processAndAssertSucessAprobacionGrabarDpsButton(long timeoutMs, long quietMs) {
        modalGrabarDps.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                guardarModalButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                modalAprobacionGrabarDps,
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
        log.info("[EMISION][APROBACION GRABAR DPS] Se completo con exito el proceso de aprobacion del grabar dps, se mostro el modal de aprobacion del grabar dps");

        clickAndSync(OKModalButton);
        log.info("[EMISION][APROBACION GRABAR DPS] Se hizo click en boton OK del modal de aprobacion del grabar dps");
    }

    public void evaluarAprobacionDpsLabel() {
        ElementAsserts.assertVisible(resultadoDpsAprobada, "Después de aprobar el grabar DPS, se debería mostrar el mensaje de DPS APROBADA");
        log.info("[EMISION][APROBACION GRABAR DPS] Se evaluo que el resultado de la aprobacion del grabar DPS fue aprobado, se mostro el label de APROBADA");
    }
}
