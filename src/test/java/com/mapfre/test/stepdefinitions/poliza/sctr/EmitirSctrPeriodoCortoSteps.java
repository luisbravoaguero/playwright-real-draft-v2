package com.mapfre.test.stepdefinitions.poliza.sctr;

import com.mapfre.playwright.pageobjects.poliza.sctr.PolizaSctrPage;
import com.mapfre.playwright.pageobjects.poliza.sctr.consultaDocumento.DocumentosSctrPage;
import com.mapfre.playwright.pageobjects.poliza.sctr.emision.EmitirSctrPeriodoCortoPage;
import com.mapfre.playwright.pageobjects.poliza.sctr.emision.ResumenEmisionSctrPeriodoCortoPage;
import com.mapfre.playwright.pageobjects.poliza.sctr.emision.ResumenEmisionSctrPeriodoRegularPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.test.hooks.ScenarioKeys;
import com.mapfre.utils.StringUtils;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import testdata.TemplateFactory;

import java.nio.file.Path;

public class EmitirSctrPeriodoCortoSteps {
    private final PolizaSctrPage polizaSctrPage;
    private final EmitirSctrPeriodoCortoPage emitirSctrPeriodoCortoPage;
    private final ResumenEmisionSctrPeriodoCortoPage resumenEmisionSctrPeriodoCortoPage ;
    private final DocumentosSctrPage documentosSctrPage;
    private final ScenarioContext scenarioContext;
    public EmitirSctrPeriodoCortoSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.polizaSctrPage = new PolizaSctrPage(pageProvider.get());
        this.emitirSctrPeriodoCortoPage = new EmitirSctrPeriodoCortoPage(pageProvider.get());
        this.resumenEmisionSctrPeriodoCortoPage = new ResumenEmisionSctrPeriodoCortoPage(pageProvider.get());
        this.documentosSctrPage = new DocumentosSctrPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }

    @And("en la pagina Sctr selecciona la opcion Emitir SCTR periodo corto")
    public void enLaPaginaSctrSeleccionaLaOpcionEmitirSCTRPeriodoCorto() {
        polizaSctrPage.assertLoaded();
        polizaSctrPage.clickEmitirSctrPeriodoCortoButton();
    }

    @And("en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos de la empresa ingresamos tipo documento {string} numero documento {string} razon social {string} telefono de casa {string} telefono movil {string} correo electronico {string} representante {string} cargo del representante {string} departamento {string} provincia {string} distrito {string} tipo de via {string} nombre de la via {string} tipo_numero {string} enumeracion {string} y actividad sunat {string}")
    public void enLaPaginaEmitirPólizaSCTRPeriodoCortoCompletamosElFomrularioDatosDeLaEmpresaIngresamosTipoDocumentoNumeroDocumentoRazonSocialTelefonoDeCasaTelefonoMovilCorreoElectronicoRepresentanteCargoDelRepresentanteDepartamentoProvinciaDistritoTipoDeViaNombreDeLaViaTipo_numeroEnumeracionYActividadSunat(String tipo_documento, String numero_documento, String razon_social, String telefono_casa, String telefono_movil, String correo_electronico, String representante, String cargo_representante, String departamento, String provincia, String distrito, String tipo_via, String nombre_via, String tipo_numero, String enumeracion, String actividad_sunat){
        emitirSctrPeriodoCortoPage.assertLoaded();
        emitirSctrPeriodoCortoPage.fillFormDatosDeLaEmpresa(tipo_documento, numero_documento, razon_social, telefono_casa, telefono_movil, correo_electronico, representante, cargo_representante, departamento, provincia, distrito, tipo_via, nombre_via, tipo_numero, enumeracion, actividad_sunat);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos de la empresa");
        emitirSctrPeriodoCortoPage.processAndAssertSuccessClickBotonSiguiente(30_000,500);
        ExtentEvidence.shot("Evidencia despues de hacer click en el boton Siguiente");
        emitirSctrPeriodoCortoPage.processAndAssertModalPolizasVigentesSuccess(30_000,500);
    }

    @And("en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos de la poliza ingresamos el codgio de actividad {string} el codigo de subactividad {string} la frecuencia de declaracion {string} la duracion de la cobertura {string} y el centro de riesgo {string}")
    public void enLaPaginaEmitirPólizaSCTRPeriodoCortoCompletamosElFomrularioDatosDeLaPolizaIngresamosElCodgioDeActividadElCodigoDeSubactividadLaFrecuenciaDeDeclaracionLaDuracionDeLaCoberturaYElCentroDeRiesgo(String codigo_actividad, String codigo_subactividad, String frecuencia_declaracion, String duracion_cobertura, String centro_riesgo) {
        emitirSctrPeriodoCortoPage.fillFormDatosDeLaPoliza(codigo_actividad,codigo_subactividad,frecuencia_declaracion, duracion_cobertura, centro_riesgo);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos de la poliza");
        emitirSctrPeriodoCortoPage.processAndAssertSuccessClickBotonGuardarSeguir(30_000,500);
        ExtentEvidence.shot("Evidencia despues de hacer click en el boton Guardar y Seguir");
        emitirSctrPeriodoCortoPage.processAndAssertSuccessClickBotonGuardarContinuar(50_000,500);
    }

    @And("en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos del riesgo seccion Mapfre Pension ingresamos el numero de trabajadores {string} y el importe planilla {string}")
    public void enLaPaginaEmitirPólizaSCTRPeriodoCortoCompletamosElFomrularioDatosDelRiesgoSeccionMapfrePensionIngresamosElNumeroDeTrabajadoresYElImportePlanilla(String numero_trabajadores, String importe_planilla) {
        emitirSctrPeriodoCortoPage.fillFormDatosDelRiesgoSeccionMapfrePension(numero_trabajadores, importe_planilla);

    }

    @And("en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos del riesgo seccion Mapfre Salud ingresamos el numero de trabajadores {string} y el importe planilla {string}")
    public void enLaPaginaEmitirPólizaSCTRPeriodoCortoCompletamosElFomrularioDatosDelRiesgoSeccionMapfreSaludIngresamosElNumeroDeTrabajadoresYElImportePlanilla(String numero_trabajadores, String importe_planilla) {
        emitirSctrPeriodoCortoPage.fillFormDatosDelRiesgoSeccionMapfreSalud(numero_trabajadores, importe_planilla);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos del Riesgo Salud");
        emitirSctrPeriodoCortoPage.processAndAssertSuccessAceptarCondicionesButton(40_000,500);
    }

    @And("en la pagina Emitir póliza SCTR Periodo Corto completamos el fomrulario Datos del riesgo seccion Mapfre Salud ingresamos el numero de trabajadores {string} y el importe planilla {string} para rechazar las condiciones")
    public void enLaPaginaEmitirPólizaSCTRPeriodoCortoCompletamosElFomrularioDatosDelRiesgoSeccionMapfreSaludIngresamosElNumeroDeTrabajadoresYElImportePlanillaParaRechazarLasCondiciones(String numero_trabajadores, String importe_planilla) {
        String numeroDocumentoSctr = StringUtils.extractAllDigits(emitirSctrPeriodoCortoPage.obtenerNumeroCotizacion());
        scenarioContext.put(ScenarioKeys.PolizaSctr.NUMERO_DOCUMENTO_SCTR, numeroDocumentoSctr);
        emitirSctrPeriodoCortoPage.fillFormDatosDelRiesgoSeccionMapfreSalud(numero_trabajadores, importe_planilla);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos del Riesgo Salud");
        emitirSctrPeriodoCortoPage.processAndAssertSuccessDisappearanceRechazarCondicionesButton(40_000,500);
    }

    @And("en la pagina Emitir póliza SCTR Periodo Corto completamos el formulario Datos de los asegurados")
    public void enLaPaginaEmitirPólizaSCTRPeriodoCortoCompletamosElFormularioDatosDeLosAsegurados() {
        String nineDigits = RandomData.idDocumentPeru(9,8);
        Path template = TemplateFactory.buildPlanillaWithNineDigits(nineDigits);
        //System.out.println("template: "+template);
        emitirSctrPeriodoCortoPage.fillFormDatosDeLosAsegurados(template, nineDigits);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos de los asegurados");
        emitirSctrPeriodoCortoPage.processAndAssertValidarTramaSucess(40_000,500);
        ExtentEvidence.shot("Evidencia despues de validar la trama de los asegurados");
        emitirSctrPeriodoCortoPage.processAndAssertModalEquifaxDescargarObservaciones(40_000,500);
        ExtentEvidence.shot("Evidencia despues de descargar las observaciones de Equifax");
        emitirSctrPeriodoCortoPage.validarMensajePlanillaCargadaExitosamente();
        ExtentEvidence.shot("Evidencia despues de validar el mensaje de planilla cargada exitosamente");
        emitirSctrPeriodoCortoPage.processAndAssertSuccessEmisionPolizaButton(300_000,500);
    }

    @Then("en la pagina Resumen Poliza SCTR Emitida Periodo Corto se muestra el numero de poliza")
    public void enLaPaginaResumenPolizaSCTREmitidaPeriodoCortoSeMuestraElNumeroDePoliza() {
        resumenEmisionSctrPeriodoCortoPage.assertLoaded();
        resumenEmisionSctrPeriodoCortoPage.validarVisibilidadYContenidoNumeroPoliza();
    }

    @Then("en la pagina Resumen Poliza SCTR Emitida Periodo Corto se muestra el numero de poliza y descargamos los documentos generados")
    public void enLaPaginaResumenPolizaSCTREmitidaPeriodoCortoSeMuestraElNumeroDePolizaYDescargamosLosDocumentosGenerados() {
        resumenEmisionSctrPeriodoCortoPage.assertLoaded();
        resumenEmisionSctrPeriodoCortoPage.validarVisibilidadYContenidoNumeroPoliza();
        resumenEmisionSctrPeriodoCortoPage.valdiarDescargaTodosDocumentosSctrPensionSaludPeriodoCorto();
    }

    @And("en la pagina Documentos SCTR buscamos el numero de documento SCTR y vemos el detalle")
    public void enLaPaginaDocumentosSCTRBuscamosElNumeroDeDocumentoSCTRYVemosElDetalle() {
        String numeroDocumentoSctr = scenarioContext.getString(ScenarioKeys.PolizaSctr.NUMERO_DOCUMENTO_SCTR);
        documentosSctrPage.assertLoaded();
        documentosSctrPage.fillFormDocumentoSctr(numeroDocumentoSctr);
        documentosSctrPage.clickBuscarButton();
        documentosSctrPage.processAndAssertSuccess(30_000,500);
        documentosSctrPage.encontrarNumeroDocumentoSctrEnResultados(numeroDocumentoSctr);
        ExtentEvidence.shot("Evidencia despues de encontrar el numero de documento SCTR");
        documentosSctrPage.clickVerDetalleNumeroDocumentoSctrButton(numeroDocumentoSctr);
        documentosSctrPage.processAndAssertSuccessVerDetalleNumeroDocumentoSctrButton(60_000,500);
    }

    @Then("en la pagina Emitir poliza SCTR Periodo Corto se muestra el mensaje TASAS RECHAZADAS al aceptar las condiciones de pago")
    public void enLaPaginaEmitirPolizaSCTRPeriodoCortoSeMuestraElMensajeTASASRECHAZADASAlAceptarLasCondicionesDePago() {
        emitirSctrPeriodoCortoPage.processAndAssertSuccessClickBotonAceptarCondicionesButton(40_000,500);
        emitirSctrPeriodoCortoPage.validarMensajeTasasRechazadas();
    }


}
