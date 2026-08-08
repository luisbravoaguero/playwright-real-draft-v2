package com.mapfre.test.stepdefinitions.poliza.sctr;

import com.mapfre.playwright.pageobjects.poliza.sctr.PolizaSctrPage;
import com.mapfre.playwright.pageobjects.poliza.sctr.emision.EmitirSctrPeriodoRegularPage;
import com.mapfre.playwright.pageobjects.poliza.sctr.emision.ResumenEmisionSctrPeriodoRegularPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import testdata.TemplateFactory;
import java.io.IOException;
import java.nio.file.Path;

public class EmitirSctrPeriodoRegularSteps {
    private final PolizaSctrPage polizaSctrPage;
    private final EmitirSctrPeriodoRegularPage emitirSctrPeriodoRegularPage;
    private final ResumenEmisionSctrPeriodoRegularPage resumenEmisionSctrPeriodoRegularPage;
    public EmitirSctrPeriodoRegularSteps(PageProvider pageProvider) {
        this.polizaSctrPage = new PolizaSctrPage(pageProvider.get());
        this.emitirSctrPeriodoRegularPage = new EmitirSctrPeriodoRegularPage(pageProvider.get());
        this.resumenEmisionSctrPeriodoRegularPage = new ResumenEmisionSctrPeriodoRegularPage(pageProvider.get());
    }

    @And("en la pagina Sctr selecciona la opcion Emitir SCTR periodo regular")
    public void enLaPaginaSctrSeleccionaLaOpcionEmitirSCTRPeriodoRegular() {
        polizaSctrPage.assertLoaded();
        polizaSctrPage.clickEmitirSctrPeriodoRegularButton();
    }

    @And("en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la empresa ingresamos tipo documento {string} numero documento {string} razon social {string} telefono de casa {string} telefono movil {string} correo electronico {string} representante {string} cargo del representante {string} departamento {string} provincia {string} distrito {string} tipo de via {string} nombre de la via {string} tipo_numero {string} enumeracion {string} y actividad sunat {string}")
    public void enLaPaginaEmitirPólizaSCTRPeriodoRegularCompletamosElFomrularioDatosDeLaEmpresaIngresamosTipoDocumentoNumeroDocumentoRazonSocialTelefonoDeCasaTelefonoMovilCorreoElectronicoRepresentanteCargoDelRepresentanteDepartamentoProvinciaDistritoTipoDeViaNombreDeLaViaTipo_numeroEnumeracionYActividadSunat(String tipo_documento, String numero_documento, String razon_social, String telefono_casa, String telefono_movil, String correo_electronico, String representante, String cargo_representante, String departamento, String provincia, String distrito, String tipo_via, String nombre_via, String tipo_numero, String enumeracion, String actividad_sunat) {
        emitirSctrPeriodoRegularPage.assertLoaded();
        emitirSctrPeriodoRegularPage.fillFormDatosDeLaEmpresa(tipo_documento, numero_documento, razon_social, telefono_casa, telefono_movil, correo_electronico, representante, cargo_representante, departamento, provincia, distrito, tipo_via, nombre_via, tipo_numero, enumeracion, actividad_sunat);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos de la empresa");
        emitirSctrPeriodoRegularPage.processAndAssertSuccessClickBotonSiguiente(30_000,500);
        ExtentEvidence.shot("Evidencia despues de hacer click en el boton Siguiente");
        emitirSctrPeriodoRegularPage.processAndAssertModalPolizasVigentesSuccess(30_000,500);
    }

    @And("en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la poliza ingresamos el codgio de actividad {string} el codigo de subactividad {string} la frecuencia de declaracion {string} la duracion de la cobertura {string} y el centro de riesgo {string}")
    public void enLaPaginaEmitirPólizaSCTRPeriodoRegularCompletamosElFomrularioDatosDeLaPolizaIngresamosElCodgioDeActividadElCodigoDeSubactividadLaFrecuenciaDeDeclaracionLaDuracionDeLaCoberturaYElCentroDeRiesgo(String codigo_actividad, String codigo_subactividad, String frecuencia_declaracion, String duracion_cobertura, String centro_riesgo) {
        emitirSctrPeriodoRegularPage.fillFormDatosDeLaPoliza(codigo_actividad,codigo_subactividad,frecuencia_declaracion, duracion_cobertura, centro_riesgo);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos de la poliza");
        emitirSctrPeriodoRegularPage.processAndAssertSuccessClickBotonGuardarSeguir(30_000,500);
        ExtentEvidence.shot("Evidencia despues de hacer click en el boton Guardar y Seguir");
        emitirSctrPeriodoRegularPage.processAndAssertSuccessClickBotonGuardarContinuar(50_000,500);
    }

    @And("en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos del riesgo seccion Mapfre Pension ingresamos el numero de trabajadores {string} y el importe planilla {string}")
    public void enLaPaginaEmitirPólizaSCTRPeriodoRegularCompletamosElFomrularioDatosDelRiesgoSeccionMapfrePensionIngresamosElNumeroDeTrabajadoresYElImportePlanilla(String numero_trabajadores, String importe_planilla) {
        emitirSctrPeriodoRegularPage.fillFormDatosDelRiesgoSeccionMapfrePension(numero_trabajadores, importe_planilla);
    }

    @And("en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos del riesgo seccion Mapfre Salud ingresamos el numero de trabajadores {string} y el importe planilla {string}")
    public void enLaPaginaEmitirPólizaSCTRPeriodoRegularCompletamosElFomrularioDatosDelRiesgoSeccionMapfreSaludIngresamosElNumeroDeTrabajadoresYElImportePlanilla(String numero_trabajadores, String importe_planilla) {
        emitirSctrPeriodoRegularPage.fillFormDatosDelRiesgoSeccionMapfreSalud(numero_trabajadores, importe_planilla);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos del Riesgo Salud");
        emitirSctrPeriodoRegularPage.processAndAssertSuccessSiguienteButton(40_000,500);
    }

    @And("en la pagina Emitir póliza SCTR Periodo Regular completamos el formulario Datos de los asegurados")
    public void enLaPaginaEmitirPólizaSCTRPeriodoRegularCompletamosElFormularioDatosDeLosAsegurados() throws IOException {
        String nineDigits = RandomData.idDocumentPeru(9,8);
        Path template = TemplateFactory.buildPlanillaWithNineDigits(nineDigits);
        //System.out.println("template: "+template);
        emitirSctrPeriodoRegularPage.fillFormDatosDeLosAsegurados(template, nineDigits);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos de los asegurados");
        emitirSctrPeriodoRegularPage.processAndAssertValidarTramaSucess(40_000,500);
        ExtentEvidence.shot("Evidencia despues de validar la trama de los asegurados");
        emitirSctrPeriodoRegularPage.processAndAssertModalEquifaxDescargarObservaciones(40_000,500);
        ExtentEvidence.shot("Evidencia despues de descargar las observaciones de Equifax");
        emitirSctrPeriodoRegularPage.validarMensajePlanillaCargadaExitosamente();
        ExtentEvidence.shot("Evidencia despues de validar el mensaje de planilla cargada exitosamente");
        emitirSctrPeriodoRegularPage.processAndAssertSuccessEmisionPolizaButton(300_000,500);
    }

    @And("en la pagina Emitir póliza SCTR Periodo Regular completamos el fomrulario Datos de la empresa ingresamos tipo documento RUC y numero documento {string}")
    public void enLaPaginaEmitirPólizaSCTRPeriodoRegularCompletamosElFomrularioDatosDeLaEmpresaIngresamosTipoDocumentoRUCYNumeroDocumento(String numero_documento) {
        emitirSctrPeriodoRegularPage.processAndAssertEmisionSuccessModalRucFraudulento(10_000,500, numero_documento);
    }

    @And("en la pagina Resumen Poliza Emitida se muestra el numero de poliza")
    public void enLaPaginaResumenPolizaEmitidaSeMuestraElNumeroDePoliza() {
        resumenEmisionSctrPeriodoRegularPage.assertLoaded();
        resumenEmisionSctrPeriodoRegularPage.validarVisibilidadYContenidoNumeroPoliza();
    }

    @Then("en la pagina Resumen Poliza Emitida se descarga la constancia")
    public void enLaPaginaResumenPolizaEmitidaSeDescargaLaConstancia() {
        resumenEmisionSctrPeriodoRegularPage.valdiarDescargaConstanciaSctrPensionSaludPeriodoRegular();
    }
    @Then("en la pagina Resumen Poliza Emitida se envia la informacion de la poliza al correo electronico del asegurado {string}")
    public void enLaPaginaResumenPolizaEmitidaSeEnviaLaInformacionDeLaPolizaAlCorreoElectronicoDelAsegurado(String correo_electronico) {
        resumenEmisionSctrPeriodoRegularPage.processAndAssertSuccessAbrirModalEnviarCorreoElectronico(20_000, 500);
        ExtentEvidence.shot("Evidencia despues de abrir el modal Enviar correo electronico");
        resumenEmisionSctrPeriodoRegularPage.fillInformacionPolizaPorCorreoElectronico(correo_electronico);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Enviar correo electronico");
        resumenEmisionSctrPeriodoRegularPage.processAndAssertSuccessModalEnviarCorreoElectronico(300_000, 500);
        resumenEmisionSctrPeriodoRegularPage.assertMensajeExitoEnvioCorreoElectronico();

    }

    @And("en la pagina Emitir póliza SCTR Periodo Regular seleccionamos el riesgo Mapfre Pension")
    public void enLaPaginaEmitirPólizaSCTRPeriodoRegularSeleccionamosElRiesgoMapfrePension() {
        emitirSctrPeriodoRegularPage.seleccionarValidarRiesgoMapfrePension();
    }

    @And("en la pagina Emitir póliza SCTR Periodo Regular, formulario Datos del riesgo seccion Mapfre Pension procesamos los datos ingresados")
    public void enLaPaginaEmitirPólizaSCTRPeriodoRegularFormularioDatosDelRiesgoSeccionMapfrePensionProcesamosLosDatosIngresados() {
        emitirSctrPeriodoRegularPage.processAndAssertSuccessSiguienteButton(40_000,500);
    }

    @And("en la pagina Emitir póliza SCTR Periodo Regular seleccionamos el riesgo Mapfre Salud")
    public void enLaPaginaEmitirPólizaSCTRPeriodoRegularSeleccionamosElRiesgoMapfreSalud() {
        emitirSctrPeriodoRegularPage.seleccionarValidarRiesgoMapfreSalud();
    }
}
