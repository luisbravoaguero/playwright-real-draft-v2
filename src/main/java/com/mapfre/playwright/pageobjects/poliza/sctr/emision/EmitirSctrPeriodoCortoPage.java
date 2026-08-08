package com.mapfre.playwright.pageobjects.poliza.sctr.emision;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.components.SearchResultsComponentForSctrActivity;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.mapfre.utils.waits.FirstDisappearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Path;
import java.util.regex.Pattern;

public class EmitirSctrPeriodoCortoPage extends BasePage {
    private final Locator title;

    //Seccion: Datos de la empresa
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator razonSocialInput;
    private final Locator telefonoCasaInput;
    private final Locator telefonoMovilInput;
    private final Locator correoElectronicoInput;
    private final Locator representanteInput;
    private final Locator tipoRepresentanteSelect;
    private final Locator departamentoSelect;
    private final Locator provinciaSelect;
    private final Locator distritoSelect;
    private final Locator tipoViaSelect;
    private final Locator nombreViaInput;
    private final Locator tipoNumeroSelect;
    private final Locator enumeracionInput;
    private final Locator tipoInteriorSelect;
    private final Locator enumeracionInteriorInput;
    private final Locator tipoZonaSelect;
    private final Locator nombreZonaInput;
    private final Locator referenciaInput;
    private final Locator filtradoActividadSunat;
    private final Locator actividadSunarInput;
    private final Locator siguienteButton;

    //Modal ¿Estás seguro que quieres guardar los datos de la empresa? -> seleccionar GUARDAR Y CONTINUAR
    private final Locator modalguardarContinuarButton;

    //Modal Pólizas Vigentes -> modal condicional, no aparece siempre, depende del ruc que se ingrese
    private final Locator modalPolizasVigenetes;
    private final Locator modalPolizasVigenetesextoCentral;
    private final Locator modalPolizasVigenetesOKButton;

    //Datos de la poliza
    private final Locator buscarSeleccionarActividadButton;

    //Modal Actividad
    private final Locator modalActividad;
    private final Locator modalActividadBuscarActividadInput;
    private final Locator modalActividadResultadoActividadClick;
    private final Locator modalActividadValidarCheckActivado;
    private final Locator modalActividadOKButton;
    private final SearchResultsComponentForSctrActivity modalResults;

    //Datos de la poliza continuacion despues del modal anterior
    private final Locator seleccionarSubActividadButton;

    //Modal SubActividad
    private final Locator modalSubActividad;
    private final Locator modalSubActividadSeleccionarClick;
    private final Locator modalSubActividadValidarCheckActivado;
    private final Locator modalSubActividadOKButton;
    private final Locator modalSubActividadList;
    //Datos de la poliza:  continuacion despues del modal anterior -> Modal SubActividad
    private final Locator frecuenciaDeclaracionSelect;
    private final Locator fechaInicioInput;
    private final Locator duracionCoberturaSelect;
    private final Locator centroDeRiesgoInput;
    private final Locator guardarSeguirButton;
    //Datos del riesgo
    private final Locator pensionSeccion;
    private final Locator saludSeccion;
    //Datos de los asegurados
    private final Locator planillaAseguradosInput;
    private final Locator planillaAseguradosLoadedLabel;
    private final Locator procesarExcelButton;
    private final Locator resultadoMensajeErrorProcesarPlantilla;
    private final Locator emitirPolizaButton;
    private final Locator resultadoMensajeError;
    private final Locator mensajeDeExito;
    private final Locator modalDescargarObservacionesButton;
    private final Locator modalWarning;
    //private final Locator modalWarningMensajeRucFraudulento;
    private final Locator modalWarningRucFraudulento;
    private final Locator modalWarningRucInvalido;
    private final Locator polizaProcesadaConExitoTitle;
    private final Locator aceptarCondicionesButton;
    private final Locator rechazarCondicionesButton;
    private final Locator datosDelAseguradoSubTitle;
    private final Locator seccionRiesgosContainer;
    private final Locator formPlanillaAsegurados;
    private final Locator planillaCargadaExitosamente;
    private final Locator tasasRechazadasWarningModal;
    private final Locator numeroDocumentoSctr;
    public EmitirSctrPeriodoCortoPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Emitir póliza SCTR Periodo"));

        //Seccion: Datos de la empresa
        this.tipoDocumentoSelect = page.locator("oim-select[name='documentType'] select");
        this.numeroDocumentoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de documento"));
        this.razonSocialInput = page.locator("oim-input[name='razonSocial'] input");
        this.telefonoCasaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono Casa"));
        this.telefonoMovilInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono móvil"));
        this.correoElectronicoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Correo electrónico"));
        this.representanteInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Representante"));
        this.tipoRepresentanteSelect = page.locator("oim-select[name='RepresentanteCargo'] select");
        this.departamentoSelect = page.locator("oim-select[name='Department'] select");
        this.provinciaSelect = page.locator("oim-select[name='Province'] select");
        this.distritoSelect =  page.locator("oim-select[name='District'] select");
        this.tipoViaSelect =  page.locator("oim-select[name='Via'] select");
        this.nombreViaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nombre Vía"));
        this.tipoNumeroSelect = page.locator("oim-select[name='NumberType'] select");
        this.enumeracionInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enumeración").setExact(true));
        this.tipoInteriorSelect = page.locator("oim-select[name='Inside'] select");
        this.enumeracionInteriorInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enumeración interior"));
        this.tipoZonaSelect = page.locator("oim-select[name='Zone'] select");
        this.nombreZonaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nombre de la Zona"));
        this.referenciaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Referencia"));
        this.actividadSunarInput = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Actividad SUNAT"));
        this.filtradoActividadSunat = page.locator("mat-option.mat-mdc-option[role='option']").first();
        this.modalResults = new SearchResultsComponentForSctrActivity(page);

        this.siguienteButton = page.getByText("Siguiente");

        //Modal ¿Estás seguro que quieres guardar los datos de la empresa? -> seleccionar GUARDAR Y CONTINUAR
        this.modalguardarContinuarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guardar y continuar"));

        //Modal Pólizas Vigentes -> modal condicional, no aparece siempre, depende del ruc que se ingrese
        this.modalPolizasVigenetes = page.getByRole(AriaRole.DIALOG, new Page.GetByRoleOptions().setName("Paso"));
        this.modalPolizasVigenetesextoCentral = page.getByText("Pólizas Vigentes Póliza -");
        this.modalPolizasVigenetesOKButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK"));

        //Datos de la poliza
        this.buscarSeleccionarActividadButton = page.locator("a").filter(new Locator.FilterOptions().setHasText("BUSCA Y SELECCIONA UNA"));

        //Modal Actividad -> 500002
        this.modalActividad = page.getByText("ACTIVIDAD", new Page.GetByTextOptions().setExact(true));
        this.modalActividadBuscarActividadInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Busca por código o descripció"));
        //this.modalActividadResultadoActividadClick = page.getByText("PREPARACIO DEL TERRENO");
        this.modalActividadResultadoActividadClick = page.locator(  "ul.g-list.row").first();
        //opcion 2: del locator anterior: this.modalResultadoActividadClick = page.locator(".g-box-header");
        //opcion 3: del locator anterior: this.modalResultadoActividadClick = page.getByRole(AriaRole.LISTITEM).filter(new Locator.FilterOptions().setHasText("PREPARACIO DEL TERRENO"))
        this.modalActividadValidarCheckActivado = page.locator("span.ico-mapfre_184_circleCheck");
        this.modalActividadOKButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK"));

        //Datos de la poliza:  continuacion despues del modal anterior -> Modal Actividad
        this.seleccionarSubActividadButton = page.getByText("SELECCIONA UNA SUBACTIVIDAD");

        //Modal SubActividad
        this.modalSubActividad = page.getByText("SUBACTIVIDAD", new Page.GetByTextOptions().setExact(true));
        this.modalSubActividadSeleccionarClick = page.getByText("335");
        this.modalSubActividadList = page.locator("ul.g-list.row").first();
        this.modalSubActividadValidarCheckActivado = page.locator("span.ico-mapfre_184_circleCheck");
        this.modalSubActividadOKButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK"));

        //Datos de la poliza:  continuacion despues del modal anterior -> Modal SubActividad
        this.frecuenciaDeclaracionSelect =  page.locator("oim-select[name='nFrecDeclaracion'] select");
        this.fechaInicioInput = page.locator("oim-datepicker[name='mConsultaDesde']").locator("input");
        this.duracionCoberturaSelect =  page.locator("oim-select[name='nDuracionCobertura'] select");
        this.centroDeRiesgoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Centro de riesgo"));
        this.guardarSeguirButton = page.getByText("Guardar y Seguir");

        //Modal ¿Estás seguro que quieres guardar los datos de la empresa? -> seleccionar GUARDAR Y CONTINUAR
        //reusar el existente, ver locator creado mas arriba -> this.modalguardarContinuarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guardar y continuar"));

        //Datos del riesgo
        this.pensionSeccion = page.locator("div.g-section:has(h2.g-sub-title:has-text('Mapfre Pensión'))");
        this.saludSeccion = page.locator("div.g-section:has(h2.g-sub-title:has-text('Mapfre Salud'))");

        //Datos del los asegurados
        this.planillaAseguradosInput = page.locator("input[type='file']#file-upload");
        this.planillaAseguradosLoadedLabel = page.locator("label[for='file-upload']");
        this.procesarExcelButton = page.getByText("Procesar");
        this.resultadoMensajeErrorProcesarPlantilla = page.locator(".g-file-success-box ul.gErrorIco li");


        //this.emitirPolizaButton = page.getByLabel(Pattern.compile("^(Emitir Póliza|EMITIR PÓLIZA|EMITIR POLIZA|Emitir póliza)$"));
        this.emitirPolizaButton = page.locator("label.g-button").filter(new Locator.FilterOptions().setHasText(Pattern.compile("emitir", Pattern.CASE_INSENSITIVE)));

        //Mensaje error en la emision
        this.resultadoMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.mensajeDeExito = page.locator("falta completar");
        //this.modalDescargarObservacionesButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Descargar observaciones"));
        this.modalDescargarObservacionesButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText(Pattern.compile("descargar observaciones", Pattern.CASE_INSENSITIVE)));
        this.modalWarning = page.locator("div.swal2-popup.swal2-icon-warning[role='dialog']");
        //this.modalWarningMensajeRucFraudulento = modalWarning.locator("div.swal2-html-container");
        this.modalWarningRucFraudulento = page.locator("div.swal2-popup.swal2-icon-warning[role='dialog']:has(#swal2-html-container:text-is('Cliente registrado como no elegible por estudios técnicos.'))");
        this.modalWarningRucInvalido = page.locator("div.swal2-popup.swal2-icon-warning[role='dialog']:has(#swal2-html-container:text-is('RUC no válido, por favor corregir.'))");
        this.polizaProcesadaConExitoTitle = page.locator("h2").filter(new Locator.FilterOptions().setHasText(Pattern.compile("La póliza ha sido emitida con éxito", Pattern.CASE_INSENSITIVE)));
        this.aceptarCondicionesButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Aceptar condiciones", Pattern.CASE_INSENSITIVE)));
        this.rechazarCondicionesButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Rechazar condiciones", Pattern.CASE_INSENSITIVE)));
        this.datosDelAseguradoSubTitle = page.locator("h2.g-sub-title").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Datos de los asegurados", Pattern.CASE_INSENSITIVE)));
        this.seccionRiesgosContainer = page.locator("div.risk-box--inputs");
        this.formPlanillaAsegurados = page.locator("form#frmPlanillaFile");
        this.planillaCargadaExitosamente = page.locator("span.file-message");
        this.tasasRechazadasWarningModal = modalWarning.locator("#swal2-title");
        this.numeroDocumentoSctr = page.getByText(Pattern.compile("Nro\\. documento SCTR:.*"));
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Emitir póliza SCTR Periodo Regular DEBE SER VISIBLE");
    }

    public void fillFormDatosDeLaEmpresa(String tipoDocumento, String numeroDocumento, String razonSocial, String telefonoCasa, String telefonoMovil, String correoElectronico, String representante, String cargoRepresentante, String departamento, String provincia, String distrito, String tipoVia, String nombreVia, String tipoNumero, String enumeracion, String actividadSunat) {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        selectAndSync(tipoDocumentoSelect,tipoDocumento);
        fillAndBlurSync(numeroDocumentoInput, numeroDocumento);

        waitAppReady();
        if(ElementAsserts.isElementDisabled(razonSocialInput,3000)){
            log.info("DESABILITADO: Numero de RUC {} existe. Nombre mostrado en la web: {}",numeroDocumento, razonSocialInput.inputValue());
        }else {
            log.info("HABILITADO: Numero de RUC {} es nuevo. Completando la razon social: {}",numeroDocumento, razonSocial);
            fillAndSync(razonSocialInput, razonSocial);
        }

        fillInputIfEmptyAndSync(telefonoCasaInput,telefonoCasa);
        fillInputIfEmptyAndSync(telefonoMovilInput,telefonoMovil);
        fillInputIfEmptyAndSync(correoElectronicoInput,correoElectronico);
        fillInputIfEmptyAndSync(representanteInput,representante);
        selectAndSync(tipoRepresentanteSelect,cargoRepresentante);
        selectAndSync(departamentoSelect,departamento);
        selectAndSync(provinciaSelect,provincia);
        selectAndSync(distritoSelect,distrito);
        selectAndSync(tipoViaSelect,tipoVia);
        fillInputIfEmptyAndSync(nombreViaInput,nombreVia);
        selectAndSync(tipoNumeroSelect,tipoNumero);
        fillInputIfEmptyAndSync(enumeracionInput,enumeracion);
        fillAndSync(actividadSunarInput,actividadSunat);
        clickAndSync(filtradoActividadSunat);
        waitRandomBetween(1000);
        //clickAndSync(siguienteButton);
        //clickAndSync(modalguardarContinuarButton);
        //closeIfPresent(modalPolizasVigenetes,modalPolizasVigenetesOKButton,5000);
        log.info("[EMISION][SCTR PERIODO CORTO] Formulario Datos de la empresa completado correctamente");
    }

    public void processAndAssertSuccessClickBotonSiguiente(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(siguienteButton, "EL BOTON SIGUIENTE DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoMensajeError,
                modalguardarContinuarButton,
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
        // SUCCESS: continue
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[EMISION][SCTR PERIODO CORTO] Boton Siguiente clickeado exitosamente.");
    }

    public void processAndAssertModalPolizasVigentesSuccess(long timeoutMs, long quietMs){
        ElementAsserts.assertVisible(modalguardarContinuarButton, "EL BOTON GUARDAR Y CONTINUAR DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                modalguardarContinuarButton::click,               // action (coloca null si la accion click fue realizada)
                modalPolizasVigenetes,
                buscarSeleccionarActividadButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            clickAndSync(modalPolizasVigenetesOKButton);
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[EMISION][SCTR PERIODO CORTO] Modal Polizas Vigentes procesado exitosamente");
    }

    public void fillFormDatosDeLaPoliza(String codigoActividad, String codigoSubactividad, String frecuenciaDeclaracion, String duracionCobertura, String centroRiesgo) {
        clickAndSync(buscarSeleccionarActividadButton);
        fillAndSync(modalActividadBuscarActividadInput,codigoActividad);
        modalResults.selectByCode(codigoActividad);
        clickAndSync(modalActividadOKButton);
        clickAndSync(seleccionarSubActividadButton);
        modalResults.selectByCode(codigoSubactividad);
        clickAndSync(modalSubActividadOKButton);
        selectAndSync(frecuenciaDeclaracionSelect,frecuenciaDeclaracion);
        selectAndSync(duracionCoberturaSelect,duracionCobertura);
        fillAndSync(centroDeRiesgoInput,centroRiesgo);
        //clickAndSync(guardarSeguirButton);
        //clickAndSync(modalguardarContinuarButton);
        log.info("[EMISION][SCTR PERIODO CORTO] Formulario Datos de la poliza completado correctamente");
    }

    public void processAndAssertSuccessClickBotonGuardarSeguir(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(guardarSeguirButton, "EL BOTON GUARDAR Y SEGUIR DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                guardarSeguirButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoMensajeError,
                modalguardarContinuarButton,
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
        // SUCCESS: continue
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[EMISION][SCTR PERIODO CORTO] Boton Guardar y Seguir clickeado exitosamente");
    }

    public void processAndAssertSuccessClickBotonGuardarContinuar(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(modalguardarContinuarButton, "EL BOTON GUARDAR Y CONTINUAR DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                modalguardarContinuarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoMensajeError,
                seccionRiesgosContainer.first(),
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
        // SUCCESS: continue
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[EMISION][SCTR PERIODO CORTO] Boton Guardar y Continuar clickeado exitosamente");
    }

    public void fillFormDatosDelRiesgoSeccionMapfrePension(String numeroTrabajadores, String importePlanilla) {
        fillAndSync(numeroTrabajadores(pensionSeccion),numeroTrabajadores);
        fillAndBlurSync(importePlanillaPension(),(importePlanilla));
        closeSwalIfVisible(2500);
        sync();
        log.info("[EMISION][SCTR PERIODO CORTO] Formulario Datos del Riesgo Pension completado correctamente");
    }

    private Locator numeroTrabajadores(Locator section){
        return section.locator("input[name='nNroTrabajadores']:visible");
    }

    private Locator importePlanillaPension(){
        return pensionSeccion.locator("oim-numeric-textbox[name='nImportePlanillaPension'] input.mdc-text-field__input:visible");
    }

    public void fillFormDatosDelRiesgoSeccionMapfreSalud(String numeroTrabajadores, String importePlanilla) {
        fillAndBlurSync(numeroTrabajadores(saludSeccion),numeroTrabajadores);
        fillAndBlurSync(importePlanillaSalud(),importePlanilla);
        closeSwalIfVisible(2500);
        sync();
        log.info("[EMISION][SCTR PERIODO CORTO] Formulario Datos del Riesgo Salud completado correctamente");
    }

    private Locator importePlanillaSalud(){
        return saludSeccion.locator("oim-numeric-textbox[name='nImportePlanillaSalud'] input.mdc-text-field__input:visible");
    }

    public void processAndAssertSuccessAceptarCondicionesButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(aceptarCondicionesButton, "EL BOTON ACEPTAR CONDICIONES DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarCondicionesButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoMensajeError,
                datosDelAseguradoSubTitle,
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
        // SUCCESS: continue
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[EMISION][SCTR PERIODO CORTO] Boton Aceptar Condiciones clickeado exitosamente");
    }

    public void fillFormDatosDeLosAsegurados(Path filePath, String numeroDocumento) {
        planillaAseguradosInput.setInputFiles(filePath);
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        ElementAsserts.assertVisible(formPlanillaAsegurados, "Formulario de Datos de los asegurados debe ser visible");
        Locator numeroDocumentoPlanillaLabel = formPlanillaAsegurados.locator("div.item-column").filter(new Locator.FilterOptions().setHasText(Pattern.compile(numeroDocumento.trim(), Pattern.CASE_INSENSITIVE)));
        ElementAsserts.assertContainsText(numeroDocumentoPlanillaLabel,numeroDocumento.trim(),"Numero de documento " + numeroDocumento + " debe estar presente en la planilla cargada");
        log.info("[EMISION][SCTR PERIODO CORTO] Formulario Datos de los asegurados completado correctamente");
    }

    public void processAndAssertValidarTramaSucess(long timeoutMs, long quietMs){
        var result = FirstAppearanceRace.waitForFirst(
                page,
                procesarExcelButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoMensajeErrorProcesarPlantilla,
                modalDescargarObservacionesButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoMensajeErrorProcesarPlantilla.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        // SUCCESS: continue
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[EMISION][SCTR PERIODO CORTO] Boton Procesar clickeado exitosamente");
    }

    public void processAndAssertModalEquifaxDescargarObservaciones(long timeoutMs, int quietMs) {
        ElementAsserts.assertVisible(modalDescargarObservacionesButton, "Boton Descargar Observaciones debe ser visible");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                modalDescargarObservacionesButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoMensajeError,
                emitirPolizaButton,
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
        // SUCCESS: continue
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[EMISION][SCTR PERIODO CORTO] Boton Descargar Observaciones clickeado exitosamente");
    }

    public void validarMensajePlanillaCargadaExitosamente(){
        ElementAsserts.assertVisible(planillaCargadaExitosamente,"Mensaje de 'planilla cargada exitosamente' debe ser visible");
        ElementAsserts.assertContainsText(planillaCargadaExitosamente,"Planilla cargada exitosamente","Mensaje de 'planilla cargada exitosamente' debe estar presente");
        log.info("[EMISION][SCTR PERIODO CORTO] Mensaje de 'planilla cargada exitosamente' validado correctamente");
    }

    public void processAndAssertSuccessEmisionPolizaButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(emitirPolizaButton, "Boton Emitir Póliza debe ser visible");
        sync();
        waitRandomBetween(2500);
        log.info("[EMISION][SCTR PERIODO CORTO] El proceso de emision ha iniciado...");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                emitirPolizaButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoMensajeError,
                polizaProcesadaConExitoTitle,
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
        // SUCCESS: continue
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[EMISION][SCTR PERIODO CORTO] Boton Emitir Póliza clickeado exitosamente. Proceso de emision finalizado.");
    }


    public void processAndAssertSuccessClickBotonAceptarCondicionesButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(aceptarCondicionesButton, "Boton Aceptar Condiciones debe ser visible");
        sync();
        waitRandomBetween(2500);
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarCondicionesButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoMensajeError,
                modalWarning,
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
        // SUCCESS: continue
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[EMISION][SCTR PERIODO CORTO] Boton Aceptar Condiciones clickeado exitosamente. Proceso de validacion finalizado.");
    }

    public void validarMensajeTasasRechazadas() {
        ElementAsserts.assertVisible(tasasRechazadasWarningModal,"Mensaje de 'tasas rechazadas' debe ser visible");
        ElementAsserts.assertContainsText(tasasRechazadasWarningModal,"Tasas Rechazadas","Mensaje de 'tasas Rechazadas' debe estar presente");
        log.info("[EMISION][SCTR PERIODO CORTO] Mensaje de 'tasas Rechazadas' validado correctamente");
    }

    public void processAndAssertSuccessDisappearanceRechazarCondicionesButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(rechazarCondicionesButton, "El botón Rechazar Condiciones debe ser visible");
        var result = FirstDisappearanceRace.waitForFirst(
                page,
                rechazarCondicionesButton::click,               // action: ejecutar el click
                resultadoMensajeError,                   // errorRoot: si aparece este elemento = ERROR
                rechazarCondicionesButton,                     // successRoot: si desaparece este elemento = SUCCESS
                null,                                    // errorMessageLocator: null para usar errorRoot
                timeoutMs,                               // timeout máximo
                quietMs,                                 // quietMs (estabilidad)
                150                                      // pollMs (intervalo de sondeo)
        );

        if (result.outcome() == FirstDisappearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstDisappearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout. El elemento no desapareció dentro de " + timeoutMs
                    + " ms (después de " + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[EMISION][SCTR PERIODO CORTO] El boton Rechazar Condiciones desapareció exitosamente tras procesar.");
    }

    public String obtenerNumeroCotizacion() {
        ElementAsserts.assertVisible(numeroDocumentoSctr, "El número de documento SCTR no esta visible.");
        log.info("[EMISION][SCTR PERIODO CORTO] {}", numeroDocumentoSctr.textContent());
        return numeroDocumentoSctr.textContent();
    }
}
