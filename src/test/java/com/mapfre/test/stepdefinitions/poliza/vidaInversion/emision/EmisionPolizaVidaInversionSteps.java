package com.mapfre.test.stepdefinitions.poliza.vidaInversion.emision;

import com.mapfre.playwright.pageobjects.poliza.vidaInversion.emision.EmisionPolizaVidaInversionPage;
import com.mapfre.playwright.pageobjects.poliza.vidaInversion.emision.ResultadoEmisionPolizaVidaInversionPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class EmisionPolizaVidaInversionSteps {
    private final EmisionPolizaVidaInversionPage emisionPolizaVidaInversionPage;
    private final ResultadoEmisionPolizaVidaInversionPage resultadoEmisionPolizaVidaInversionPage;
    public EmisionPolizaVidaInversionSteps(PageProvider pageProvider) {
        this.emisionPolizaVidaInversionPage = new EmisionPolizaVidaInversionPage(pageProvider.get());
        this.resultadoEmisionPolizaVidaInversionPage = new ResultadoEmisionPolizaVidaInversionPage(pageProvider.get());
    }

    @And("en la pagina Emisión Póliza Vida, en el paso Datos del Contratante y Asegurado, seccion Datos del Contratante, Datos Principales ingresamos la fecha de nacimiento {string}, estado civil {string}, el pais de residencia fiscal {string}")
    public void enLaPaginaEmisiónPólizaVidaEnElPasoDatosDelContratanteYAseguradoSeccionDatosDelContratanteDatosPrincipalesIngresamosLaFechaDeNacimientoEstadoCivilElPaisDeResidenciaFiscal(String fecha_nacimiento, String estado_civil, String residencia_fiscal) {
        emisionPolizaVidaInversionPage.emisionPolizaVidaTitle();
        emisionPolizaVidaInversionPage.fillFormularioDatosPrincipalesContratante(fecha_nacimiento, estado_civil, residencia_fiscal);
    }

    @And("en la pagina Emisión Póliza Vida, en el paso Datos del Contratante y Asegurado, seccion Datos del Contratante, Datos Laborales ingresamos sexo {string}, profesion {string} y ocupacion {string}")
    public void enLaPaginaEmisiónPólizaVidaEnElPasoDatosDelContratanteYAseguradoSeccionDatosDelContratanteDatosLaboralesIngresamosSexoProfesionYOcupacion(String sexo, String profesion, String ocupacion) {
        emisionPolizaVidaInversionPage.fillFormularioDatosLaboralesContratante(sexo, profesion, ocupacion);
    }

    @And("en la pagina Emisión Póliza Vida, en el paso Datos del Contratante y Asegurado, seccion Datos del Contratante, Datos de Contacto ingresamos el prefijo telefono {string}, telefono casa {string}, telefono de oficina {string}, telefono movil {string} y correo electronico {string}")
    public void enLaPaginaEmisiónPólizaVidaEnElPasoDatosDelContratanteYAseguradoSeccionDatosDelContratanteDatosDeContactoIngresamosElPrefijoTelefonoTelefonoCasaTelefonoDeOficinaTelefonoMovilYCorreoElectronico(String prefijo_telefono, String telefono_casa, String telefono_oficina, String telefono_movil, String correo_electronico) {
        emisionPolizaVidaInversionPage.fillFormularioDatosContactoContratante(prefijo_telefono, telefono_casa, telefono_oficina, telefono_movil, correo_electronico);
    }

    @And("en la pagina Emisión Póliza Vida, en el paso Datos del Contratante y Asegurado, seccion Datos del Contratante, Datos de Direccion ingresamos el pais natal {string}, departamento {string}, provincia {string}, distrito {string}, tipo de via {string} y nombre de via {string}")
    public void enLaPaginaEmisiónPólizaVidaEnElPasoDatosDelContratanteYAseguradoSeccionDatosDelContratanteDatosDeDireccionIngresamosElPaisNatalDepartamentoProvinciaDistritoTipoDeViaYNombreDeVia(String pais_natal, String departamento, String provincia, String distrito, String tipo_via, String nombre_via) {
        emisionPolizaVidaInversionPage.fillFormularioDatosDireccionContratante(pais_natal, departamento, provincia, distrito, tipo_via, nombre_via);
    }

    @And("en la pagina Emisión Póliza Vida, en el paso Datos del Contratante y Asegurado, seccion Datos del Asegurado activamos la opcion Asegurado es Contratante e ingreamos el centro de trabajo {string}")
    public void enLaPaginaEmisiónPólizaVidaEnElPasoDatosDelContratanteYAseguradoSeccionDatosDelAseguradoActivamosLaOpcionAseguradoEsContratanteEIngreamosElCentroDeTrabajo(String centro_trabajo) {
        emisionPolizaVidaInversionPage.activarOpcionAseguradoEsContratante();
        emisionPolizaVidaInversionPage.fillFormularioCentroTrabajoAsegurado(centro_trabajo);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario del centro de trabajo del asegurado");
        emisionPolizaVidaInversionPage.processAndAssertSucessDatosContratanteAegurado(60_000,500);
    }

    @And("en la pagina Emisión Póliza Vida, en el paso Datos de la Poliza, seccion Datos Bancarios para el Pago de Renta ingresamos la entidad financiera {string}, tipo de cuenta {string}, numero de cuenta {string} y el codgio del gestor de la entidad financiera")
    public void enLaPaginaEmisiónPólizaVidaEnElPasoDatosDeLaPolizaSeccionDatosBancariosParaElPagoDeRentaIngresamosLaEntidadFinancieraTipoDeCuentaNumeroDeCuentaYElCodgioDelGestorDeLaEntidadFinanciera(String entidad_financiera, String tipo_cuenta, String numero_cuenta) {
        emisionPolizaVidaInversionPage.fillFormularioDatosBancariosPagoRenta(entidad_financiera, tipo_cuenta, numero_cuenta);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de datos bancarios para el pago de renta");
        emisionPolizaVidaInversionPage.processAndAssertSucessDatosDePoliza(60_000,500);
    }

    @And("en la pagina Emisión Póliza Vida, en el paso Datos del Contratante y Asegurado, seccion Datos del Asegurado activamos la opcion Asegurado es Contratante e ingreamos el centro de trabajo certivida {string}")
    public void enLaPaginaEmisiónPólizaVidaEnElPasoDatosDelContratanteYAseguradoSeccionDatosDelAseguradoActivamosLaOpcionAseguradoEsContratanteEIngreamosElCentroDeTrabajoCertivida(String centro_trabajo) {
        emisionPolizaVidaInversionPage.activarOpcionAseguradoEsContratante();
        emisionPolizaVidaInversionPage.fillFormularioCentroTrabajoAsegurado(centro_trabajo);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario del centro de trabajo del asegurado");
        emisionPolizaVidaInversionPage.processAndAssertSucessDatosContratanteAeguradoCertivida(60_000,500);
    }

    @And("en la pagina Emisión Póliza Vida, en el paso Datos de la Poliza, seccion Beneficiarios en caso de muerte pasamos al paso Declaracion Personal de Salud")
    public void enLaPaginaEmisiónPólizaVidaEnElPasoDatosDeLaPolizaSeccionBeneficiariosEnCasoDeMuertePasamosAlPasoDeclaracionPersonalDeSalud() {
        emisionPolizaVidaInversionPage.processAndAssertSucessDatosDePolizaCertirenta(60_000,500);
    }

    @And("en la pagina Emisión Póliza Vida, en el paso Declaracion Personal de Salud, llenamos el Formulario Declaracion Personal de Salud")
    public void enLaPaginaEmisiónPólizaVidaEnElPasoDeclaracionPersonalDeSaludLlenamosElFormularioDeclaracionPersonalDeSalud() {
        emisionPolizaVidaInversionPage.fillFormularioDeclaracionPersonalSalud();
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de declaracion personal de salud");
        emisionPolizaVidaInversionPage.processAndAssertSucessGrabarDpsButton(15_000,500);
        ExtentEvidence.shot("Evidencia despues de procesar el grabar dps button");
        emisionPolizaVidaInversionPage.processAndAssertSucessAprobacionGrabarDpsButton(40_000,500);
        ExtentEvidence.shot("Evidencia despues de procesar la aprobacion del grabar dps button");
        emisionPolizaVidaInversionPage.evaluarAprobacionDpsLabel();
    }

    @And("en la pagina Emisión Póliza Vida, en el paso Documentos Requeridos, seleccionamos los documentos obligatorios para la emision de la poliza")
    public void enLaPaginaEmisiónPólizaVidaEnElPasoDocumentosRequeridosSeleccionamosLosDocumentosObligatoriosParaLaEmisionDeLaPoliza() {
        emisionPolizaVidaInversionPage.seleccionarDocumentosRequeridos();
            ExtentEvidence.shot("Evidencia despues de seleccionar los documentos requeridos para la emision de la poliza");
        emisionPolizaVidaInversionPage.processAndAssertSucessEmitirPoliza(150_000,500);
    }

    @Then("en la pagina Resultado de Emisión Póliza Vida Inversion se muestra el numero de poliza generado")
    public void enLaPaginaResultadoDeEmisiónPólizaVidaInversionSeMuestraElNumeroDePolizaGenerado() {
        resultadoEmisionPolizaVidaInversionPage.assertLoaded();
        resultadoEmisionPolizaVidaInversionPage.assertNumeroPolizaVisibleAndFilled();
    }
}
