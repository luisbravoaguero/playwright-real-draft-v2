package com.mapfre.playwright.pageobjects.poliza.transporte;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.mapfre.utils.waits.ValueWait;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CotizacionTransportePage extends BasePage {
    private final Locator title;
    private final Locator agenteInput;
    private final Locator resultadoAgenteOption;
    private final Locator modalGeneralError;
    private final Locator resultadoPolizaGrupoContenedor;
    private final Locator polizaGrupoSiguienteButton;
    private final Locator polizaGrupoElegirOtraPolizaButton;
    private final Locator inicioVigenciaInput;
    private final Locator siguinteButton;
    private final Locator datosRiesgoPaso;
    private final Locator materiaAseguradaSelect;
    private final Locator tasaMercaderiaInput;
    private final Locator descripcionMateriaAseguradaInput;
    private final Locator paisOrigenSelect;
    private final Locator companiaTransporteInput;
    private final Locator nombreNaveInput;
    private final Locator facturaGuiaInput;
    private final Locator nombreProveedorInput;
    private final Locator nombreDestinoInput;
    private final Locator departamentoDestinoSelect;
    private final Locator provinciaDestinoSelect;
    private final Locator distritoDestinoSelect;
    private final Locator almacenDestinoInput;
    private final Locator importePaso;
    private final Locator valuacionMercaderiaSelect;
    private final Locator valorMercaderiaInput;
    private final Locator fleteInput;
    private final Locator derechoAduanaInput;
    private final Locator porcentajeSobreseguroInput;
    private final Locator calcularPrimaButton;
    private final Locator importePrimaLabel;
    private final Locator datosContratantePaso;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombreInput;
    private final Locator apellidoPaternoInput;
    private final Locator apellidoMaternoInput;
    private final Locator fechaNacimientoDiaSelect;
    private final Locator fechaNacimientoMesSelect;
    private final Locator fechaNacimientoAnioSelect;
    private final Locator sexoMasculinoRadio;
    private final Locator profesionSelect;
    private final Locator telefonoCasaInput;
    private final Locator telefonoMovilInput;
    private final Locator correoElectronicoInput;
    private final Locator departamentoSelect;
    private final Locator provinciaSelect;
    private final Locator distritoSelect;
    private final Locator tipoViaSelect;
    private final Locator nombreViaInput;
    private final Locator tipoNumeroSelect;
    private final Locator nombreEnumeracionInput;
    private final Locator emitirPolizaPaso;
    private final Locator emisionPolizaButton;
    private final Locator modalEmitirButton;
    private final Locator polizaEmitidaTitle;
    public CotizacionTransportePage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Emitir póliza transporte"));
        this.agenteInput = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Agente"));
        this.resultadoAgenteOption = page.locator("div[role='listbox'] mat-option[role='option'] span").filter(new Locator.FilterOptions().setHasText("9808")).first();
        this.modalGeneralError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.resultadoPolizaGrupoContenedor = page.locator("div.g-myd-result");
        this.polizaGrupoSiguienteButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Seleccionar"));
        this.polizaGrupoElegirOtraPolizaButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("ELEGIR OTRA PÓLIZA"));
        this.inicioVigenciaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Inicio de vigencia"));
        this.siguinteButton = page.getByText("Siguiente");
        this.datosRiesgoPaso = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Datos del riesgo").setLevel(2));
        this.materiaAseguradaSelect = page.getByLabel("Materia Asegurada", new Page.GetByLabelOptions().setExact(true));
        this.tasaMercaderiaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Tasa mercadería"));
        this.descripcionMateriaAseguradaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Descripción materia asegurada"));
        this.paisOrigenSelect = page.getByLabel("País de origen");
        this.companiaTransporteInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Compañia de transporte"));
        this.nombreNaveInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nombre nave"));
        this.facturaGuiaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Factura/Guía"));
        this.nombreProveedorInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nombre del proveedor"));
        this.nombreDestinoInput = page.locator("oim-input[name='cNombreDestino'] input");
        this.departamentoDestinoSelect = page.getByLabel("Departamento");
        this.provinciaDestinoSelect = page.getByLabel("Provincia");
        this.distritoDestinoSelect = page.getByLabel("Distrito");
        this.almacenDestinoInput = page.getByLabel("Almacén");
        this.importePaso = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Importes").setLevel(2));
        this.valuacionMercaderiaSelect = page.getByLabel("Valuación mercadería");
        this.valorMercaderiaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Valor mercadería(FOB)"));
        this.fleteInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Flete"));
        this.derechoAduanaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Derecho aduana"));
        this.porcentajeSobreseguroInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("% de sobreseguro"));
        this.calcularPrimaButton = page.getByText("CALCULAR PRIMA");
        this.importePrimaLabel = page.locator("div.item-label", new Page.LocatorOptions().setHasText("Importe Prima")).locator("~ div.item-dato");
        this.datosContratantePaso = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Datos personales").setLevel(2));
        this.tipoDocumentoSelect = page.getByLabel("Tipo de documento");
        this.numeroDocumentoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de documento"));
        this.nombreInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nombres"));
        this.apellidoPaternoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Apellido Paterno"));
        this.apellidoMaternoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Apellido Materno"));
        this.fechaNacimientoDiaSelect = page.locator("oim-select[name='day'] select");
        this.fechaNacimientoMesSelect = page.locator("oim-select[name='month'] select");
        this.fechaNacimientoAnioSelect = page.locator("oim-select[name='year'] select");
        this.sexoMasculinoRadio = page.getByText("Masculino");
        this.profesionSelect = page.locator("oim-select[name='Profesion'] select");
        this.telefonoCasaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono Casa"));
        this.telefonoMovilInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono móvil"));
        this.correoElectronicoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Correo electrónico"));
        this.departamentoSelect = page.getByLabel("Departamento");
        this.provinciaSelect = page.getByLabel("Provincia");
        this.distritoSelect = page.getByLabel("Distrito");
        this.tipoViaSelect = page.getByLabel("Vía", new Page.GetByLabelOptions().setExact(true));
        this.nombreViaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nombre Vía"));
        this.tipoNumeroSelect = page.getByLabel("Número", new Page.GetByLabelOptions().setExact(true));
        this.nombreEnumeracionInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enumeración").setExact(true));
        this.emitirPolizaPaso = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Resumen Prima"));
        this.emisionPolizaButton = page.getByText("EMITIR POLIZA");
        this.modalEmitirButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Emitir"));
        this.polizaEmitidaTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Póliza emitida"));
    }


    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Emitir póliza transporte DEBE SER VISIBLE");
        sync();
        waitForNetworkIdle();
        waitRandomBetween(500);
    }

    public void selectAgente(String agente) {
        clickAndSync(agenteInput);
        pressSequentiallyDelayAndSync(agenteInput, agente);
        //clickAndSync(resultadoAgenteOption);
    }

    public void processAndAssertSuccessSeleccionarAgente(long timeoutMs, long quietMs) {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(500);
        ElementAsserts.assertVisible(resultadoAgenteOption, "El resultado de la busqueda del agente DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                resultadoAgenteOption::click,               // action (coloca null si la accion click fue realizada)
                modalGeneralError,
                resultadoPolizaGrupoContenedor,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[TRANSPORTE][COTIZACION] Se proceso la seleccion del agente");
    }

    public void processAndAssertSuccessSeleccionarPolizaGrupo(long timeoutMs, long quietMs) {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(500);
        ElementAsserts.assertVisible(polizaGrupoSiguienteButton, "El boton SELECCIONAR de la poliza de grupo DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                polizaGrupoSiguienteButton::click,               // action (coloca null si la accion click fue realizada)
                modalGeneralError,
                polizaGrupoElegirOtraPolizaButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[TRANSPORTE][COTIZACION] Se proceso la seleccion de la poliza de grupo");
    }

    public void validarCamposVigenciaPolizaGrupo() {
        ElementAsserts.assertFilled(inicioVigenciaInput, "El campo Inicio de vigencia DEBE ESTAR LLENO");
        ElementAsserts.assertVisible(siguinteButton, "El boton Siguiente DEBE SER VISIBLE");
    }

    public void processAndAssertSuccessSelecionarBotonSiguientePolizaGrupo(long timeoutMs, long quietMs) {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(500);
        ElementAsserts.assertVisible(siguinteButton, "El boton SIGUIENTE DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguinteButton::click,               // action (coloca null si la accion click fue realizada)
                modalGeneralError,
                datosRiesgoPaso,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[TRANSPORTE][COTIZACION] Se proceso la seleccion y se muestra el paso 2 - Datos del riesgo");
    }

    public void assertLoadedDatosRiesgo() {
        ElementAsserts.assertVisible(datosRiesgoPaso, "El paso 2 - Datos del riesgo DEBE SER VISIBLE");
    }

    public void fillFormPasoSeccionDatosRiesgo(String materiaAseguradora, String tasaMercaderia, String descripcionMateriaAseguradora) {
        selectAndSync(materiaAseguradaSelect, materiaAseguradora);
        clearAndFill(tasaMercaderiaInput, tasaMercaderia);
        fillAndBlurSync(descripcionMateriaAseguradaInput, descripcionMateriaAseguradora);
        log.info("[TRANSPORTE][COTIZACION] Se llenaron los campos de la seccion Datos del riesgo");
    }

    public void fillFormPasoDatosSeccionRiesgoLugarOrigen(String paisDestino, String companiaTransporte, String nombreNave, String facturaGuia, String nombreProveedor) {
        selectAndSync(paisOrigenSelect, paisDestino);
        clearAndFill(companiaTransporteInput, companiaTransporte);
        clearAndFill(nombreNaveInput, nombreNave);
        clearAndFill(facturaGuiaInput, facturaGuia);
        clearAndFill(nombreProveedorInput, nombreProveedor);
        log.info("[TRANSPORTE][COTIZACION] Se llenaron los campos de la seccion Lugar origen");
    }

    public void fillFormPasoDatosSeccionRiesgoLugarDestino(String otro, String departamento, String provincia, String distrito, String almacen) {
        clearAndFill(nombreDestinoInput, otro);
        selectByOptionIfEnableAndSync(departamentoDestinoSelect, departamento);
        selectByOptionIfEnableAndSync(provinciaDestinoSelect, provincia);
        selectByOptionIfEnableAndSync(distritoDestinoSelect, distrito);
        selectAndSync(almacenDestinoInput, almacen);
        log.info("[TRANSPORTE][COTIZACION] Se llenaron los campos de la seccion Lugar destino");
    }

    public void processAndAssertSuccessSelecionarBotonSiguientePasoDatosSeccionRiesgoLugarDestino(long timeoutMs, long quietMs) {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(500);
        ElementAsserts.assertVisible(siguinteButton, "El boton SIGUIENTE DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguinteButton::click,               // action (coloca null si la accion click fue realizada)
                modalGeneralError,
                importePaso,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[TRANSPORTE][COTIZACION] Se proceso la seleccion y se muestra el paso 3 - Calculo de prima, seccion Importes");
    }
    public void fillFormPasoCalculoPrimaSeccionImportes(String valuacionMercaderia, String valorMercaderia, String flete, String derechoAduana, String porcentajeSobreseguro) {
        selectAndSync(valuacionMercaderiaSelect, valuacionMercaderia);
        cleanPressSequentiallyDelayBlurAndSync(valorMercaderiaInput, valorMercaderia);
        cleanPressSequentiallyDelayBlurAndSync(fleteInput, flete);
        cleanPressSequentiallyDelayBlurAndSync(derechoAduanaInput, derechoAduana);
        cleanPressSequentiallyDelayBlurAndSync(porcentajeSobreseguroInput, porcentajeSobreseguro);
        log.info("[TRANSPORTE][COTIZACION] Se llenaron los campos del paso 3 Calculo Prima de la seccion Importes");
    }

    public void processAndAssertSuccessSelecionarBotonCalcularPrima(long timeoutMs, long quietMs) {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(500);
        ElementAsserts.assertVisible(calcularPrimaButton, "El boton CALCULAR PRIMA DEBE SER VISIBLE");
        clickAndSync(calcularPrimaButton);
        //Locator primaRow = page.locator("div.row.mx-0:has(div.item-label:has-text('Importe Prima'))");
        //Locator primaValue = primaRow.locator("div.item-dato");
        ValueWait.Result result = ValueWait.waitUntilText(
                page,
                importePrimaLabel,
                text -> {
                    String clean = text.replace(",", "").replace("$", "").trim();
                    return !clean.isEmpty() && Double.parseDouble(clean) > 0.00;
                },
                timeoutMs,  // timeoutMs
                quietMs     // pollMs
        );

        if (!result.success()) {
            ElementAsserts.assertUIMessage(
                    "Importe Prima no superó 0.00 dentro del timeout. Último valor: "
                            + result.lastValue() + " (" + result.elapsedMs() + " ms)");
        }

        log.info("[TRANSPORTE][COTIZACION] Importe Prima: {}",result.lastValue());
    }

    public void processAndAssertSuccessSelecionarBotonCalculoPrima(long timeoutMs, long quietMs) {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(500);
        ElementAsserts.assertVisible(siguinteButton, "El boton SIGUIENTE DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguinteButton::click,               // action (coloca null si la accion click fue realizada)
                modalGeneralError,
                datosContratantePaso,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[TRANSPORTE][COTIZACION] Se proceso la seleccion y se muestra el paso 4 - Datos del contratante");
    }

    public void fillFormPasoDatosContratanteSeccionDatosPersonales(String numeroDocumento) {
        selectAndSync(tipoDocumentoSelect, "DNI");
        fillAndBlurSync(numeroDocumentoInput, numeroDocumento);
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        fillInputIfEnabledAndSync(nombreInput, "JUAN");
        fillInputIfEnabledAndSync(apellidoPaternoInput, "PEREZ");
        fillInputIfEnabledAndSync(apellidoMaternoInput, "GOMEZ");
        selectByOptionIfEnableAndSync(fechaNacimientoDiaSelect, "11");
        selectByOptionIfEnableAndSync(fechaNacimientoMesSelect, "11");
        selectByOptionIfEnableAndSync(fechaNacimientoAnioSelect, "1990");
        log.info("[TRANSPORTE][COTIZACION] Se llenaron los campos de la seccion Datos personales");
    }

    public void fillFormPasoDatosContratanteSeccionCaracteristicasPersonales() {
        clickAndSync(sexoMasculinoRadio);
        log.info("[TRANSPORTE][COTIZACION] Se llenaron los campos de la seccion Caracteristicas personales");
    }

    public void fillFormPasoDatosContratanteSeccionDatosLaborales() {
        selectByOptionIfEnableAndSync(profesionSelect, "ABOGADO");
        log.info("[TRANSPORTE][COTIZACION] Se llenaron los campos de la seccion Datos laborales");
    }

    public void fillFormPasoDatosContratanteSeccionDatosContacto() {
        fillInputIfEnabledAndSync(telefonoCasaInput,    "5586341");
        fillInputIfEnabledAndSync(telefonoMovilInput, "985254638");
        fillInputIfEnabledAndSync(correoElectronicoInput, "EXTLUBA@MAPFRE.COM.PE");
        log.info("[TRANSPORTE][COTIZACION] Se llenaron los campos de la seccion Datos contacto");
    }

    public void fillFormPasoDatosContratanteSeccionDatosDireccion() {
        selectOptionIfEnabledAndSync(departamentoSelect, "LIMA");
        selectOptionIfEnabledAndSync(provinciaSelect, "LIMA");
        selectOptionIfEnabledAndSync(distritoSelect, "COMAS");
        selectOptionIfEnabledAndSync(tipoViaSelect, "AGRUPACION");
        fillInputIfEnabledAndSync(nombreViaInput, "VIA PRINCIPAL");
        selectOptionIfEnabledAndSync(tipoNumeroSelect, "BLOCK");
        fillInputIfEnabledAndSync(nombreEnumeracionInput, "1234");
        log.info("[TRANSPORTE][COTIZACION] Se llenaron los campos de la seccion Datos direccion");
    }

    public void processAndAssertSuccessSelecionarBotonSiguientePasoDatosContratante(long timeoutMs, long quietMs) {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(500);
        ElementAsserts.assertVisible(siguinteButton, "El boton SIGUIENTE DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguinteButton::click,               // action (coloca null si la accion click fue realizada)
                modalGeneralError,
                emitirPolizaPaso,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[TRANSPORTE][COTIZACION] Se proceso la seleccion y se muestra el paso 5 - Emitir poliza");
    }


    public void processAndAssertSuccessSelecionarBotonEmitirPoliza(long timeoutMs, long quietMs) {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(500);
        ElementAsserts.assertVisible(emisionPolizaButton, "El boton EMITIR POLIZA DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                emisionPolizaButton::click,               // action (coloca null si la accion click fue realizada)
                modalGeneralError,
                modalEmitirButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[TRANSPORTE][COTIZACION] Se procesa la emision de la poliza y se muestra el modal ¿Estás seguro que quieres emitir la póliza?");
    }

    public void processAndAssertSuccessSelecionarBotonEmitirDentroModal(long timeoutMs, long quietMs) {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(500);
        ElementAsserts.assertVisible(modalEmitirButton, "El boton EMITIR DEBE SER VISIBLE dentro del modal");
        log.info("[TRANSPORTE][EMISION] El proceso de emision ha iniciado...");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                modalEmitirButton::click,               // action (coloca null si la accion click fue realizada)
                modalGeneralError,
                polizaEmitidaTitle,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[TRANSPORTE][COTIZACION] Se procesa la emision de la poliza y se muestra la pagina de Resumen de la poliza");
    }
}
