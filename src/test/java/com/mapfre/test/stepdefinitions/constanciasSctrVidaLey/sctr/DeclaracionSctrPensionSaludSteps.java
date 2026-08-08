package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral.*;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class DeclaracionSctrPensionSaludSteps {
    private final ClientesPage clientesPage;
    private final PerfilClientePage perfilClientePage;
    private final DeclaracionCargaAseguradoPage declaracionCargaAseguradoPage;
    private final DeclaracionDetalleDeRiesgoPage declaracionDetalleDeRiesgoPage;
    private final DeclaracionReporteConstacia declaracionReporteConstacia;
    public DeclaracionSctrPensionSaludSteps(PageProvider pageProvider) {
        this.clientesPage = new ClientesPage(pageProvider.get());
        this.perfilClientePage = new PerfilClientePage(pageProvider.get());
        this.declaracionCargaAseguradoPage = new DeclaracionCargaAseguradoPage(pageProvider.get());
        this.declaracionDetalleDeRiesgoPage = new DeclaracionDetalleDeRiesgoPage(pageProvider.get());
        this.declaracionReporteConstacia = new DeclaracionReporteConstacia(pageProvider.get());
    }
    @And("en la pagina Clientes seleccionamos el numero de poliza {string} para declarar")
    public void enLaPaginaClientesSeleccionamosElNumeroDePolizaParaDeclarar(String numero_poliza) {
        // Verifica que la página de Clientes haya cargado correctamente
        clientesPage.assertLoaded();
        // Completa el formulario de búsqueda con el número de póliza y filtra
        clientesPage.fillFormDeclaracionSctrPensionSalud(numero_poliza);
        // Toma una captura de pantalla como evidencia después de completar el formulario
        ExtentEvidence.shot("Evidencia despues de completar el formulario con el numero de poliza");
        // Procesa la búsqueda y valida que sea exitosa
        clientesPage.processAndAssertSuccess(30_000,500);
        // Toma una captura de pantalla como evidencia después de filtrar
        ExtentEvidence.shot("Evidencia despues de filtrar con el numero de poliza");
        // Procesa la selección del cliente y accede al perfil del cliente
        clientesPage.processAndAssertSuccessPerfilCliente(40_000,500);
    }

    @And("en la pagina Perfil Cliente seleccionamos una aplicacion para declarar la poliza")
    public void enLaPaginaPerfilClienteSeleccionamosElUnaAplicacionParaDeclararLaPoliza() {
        // Verifica que la página de Perfil Cliente haya cargado correctamente
        perfilClientePage.assertLoaded();
        // Selecciona el checkbox de una aplicación con estado no declarado
        perfilClientePage.seleccionarAplicacionConEstadoNoDeclaradaCheckBox();
        // Toma una captura de pantalla como evidencia después de seleccionar la aplicación
        ExtentEvidence.shot("Evidencia despues de seleccionar una aplicacion con estado no declarado");
        // Hace clic en el botón "Declarar Aplicación"
        perfilClientePage.seleccionarDeclararAplicacionButton();
        // Toma una captura de pantalla como evidencia después de hacer clic en declarar
        ExtentEvidence.shot("Evidencia despues de seleccionar el boton declarar aplicacion");
        // Procesa y valida la aparición del modal de recibos pendientes
        perfilClientePage.processAndAssertSuccessRecibosPendientes(30_000,500);
        // Toma una captura de pantalla como evidencia del modal de recibos pendientes
        ExtentEvidence.shot("Evidencia despues del modal recibos pendientes");
        // Hace clic en el botón "Aceptar" del modal de recibos pendientes
        perfilClientePage.clickAceptarRecibosPendientesButton();
        // Procesa y valida la aparición del botón de declaración
        perfilClientePage.processAndAssertSuccessDeclaracionButton(30_000,500);
    }

    @And("en la pagina Declaracion completamos el formulario Datos de los asegurados")
    public void enLaPaginaDeclaracionCompletamosElFormularioDatosDeLosAsegurados() {
        // Verifica que la página de Carga de Asegurado haya cargado correctamente
        declaracionCargaAseguradoPage.assertLoaded();
        // Completa el formulario con los detalles de la póliza
        declaracionCargaAseguradoPage.fillFormDetallesPolizas();
        // Toma una captura de pantalla como evidencia después de completar detalles de póliza
        ExtentEvidence.shot("Evidencia despues de completar el formulario detalles de poliza");
        // Completa el formulario de carga individual de asegurados
        declaracionCargaAseguradoPage.fillAseguradosCargaIndividual();
        // Toma una captura de pantalla como evidencia después de completar asegurados
        ExtentEvidence.shot("Evidencia despues de completar el formulario asegurados carga individual");
        // Procesa y valida la aparición del modal de lista de observaciones
        declaracionCargaAseguradoPage.processAndAssertSuccessListaDeObservaciones(60_000,500);
        // Toma una captura de pantalla como evidencia del modal de lista de observaciones
        ExtentEvidence.shot("Evidencia despues del modal Lista de Observaciones");
        // Hace clic en el botón "Aceptar" del modal de lista de observaciones
        declaracionCargaAseguradoPage.clickAceptarListaObservacionesButton();
        // Procesa y valida la aparición del botón generar
        declaracionCargaAseguradoPage.processAndAssertSuccessBotonGenerar(60_000,500);
    }

    @And("en la pagina Declaracion completamos el formulario Informacion de Declaracion")
    public void enLaPaginaDeclaracionCompletamosElFormularioInformacionDeDeclaracion() {
        // Verifica que la página de Detalle de Riesgo haya cargado correctamente
        declaracionDetalleDeRiesgoPage.assertLoaded();
        // Completa el formulario con los datos de la obra (riesgo)
        declaracionDetalleDeRiesgoPage.fillDatosObra();
    }

    @And("en la pagina Declaracion seleccionamos el boton Declarar")
    public void enLaPaginaDeclaracionSeleccionamosElBotonDeclarar() {
        // Hace clic en el botón "Generar" para iniciar el proceso de declaración
        declaracionDetalleDeRiesgoPage.clickGenerarButton();
        // Procesa y valida la aparición del modal de procesamiento de generación
        declaracionDetalleDeRiesgoPage.processAndAssertSuccessProcesarBotonGenerar(20_000,500);
        // Toma una captura de pantalla como evidencia del modal de procesamiento
        ExtentEvidence.shot("Evidencia despues del modal procesar generacion declaracion");
        // Procesa y valida que la declaración se haya completado con éxito
        declaracionDetalleDeRiesgoPage.processAndAssertSuccessProcesarBotonGenerarconExito(150_000,500);
    }

    @Then("en la pagina Resumen Declaracion SCTR se muestra el resumen de la aplicacion declarada")
    public void enLaPaginaResumenDeclaracionSCTRSeMuestraElResumenDeLaAplicacionDeclarada() {
        // Procesa y valida que aparezca la pantalla final de declaración exitosa
        declaracionDetalleDeRiesgoPage.processAndAssertSuccessProcesarDeclaracionPantallaFinal(200_000,500);
        // Verifica que la página de Reporte Constancia haya cargado correctamente
        declaracionReporteConstacia.assertLoaded();
    }

    @And("en la pagina Perfil Cliente selecciona la opcion Constancia Manual para abrir el modal Generar Nueva Constancia Manual")
    public void enLaPaginaPerfilClienteSeleccionaLaOpcionConstanciaManualParaAbrirElModalGenerarNuevaConstanciaManual() {
        // Activa el checkbox de la barra de constancia manual para habilitarla
        perfilClientePage.activarCheckboxBarraConstaciaManual();
        // Toma una captura de pantalla como evidencia después de seleccionar el checkbox
        ExtentEvidence.shot("Evidencia despues de seleccionar el checkbox constancia manual y activar la barra que contiene el boton constancia manual");
        // Procesa y valida la aparición del botón de constancia manual
        perfilClientePage.processAndAssertSuccessConstanciaManualButton(20_000,500);
    }

    @And("en la pagina Clientes ingresamos el tipo de documento {string} y numero de documento {string}")
    public void enLaPaginaClientesIngresamosElTipoDeDocumentoYNumeroDeDocumento(String tipo_documento, String numero_documento) {
        // Verifica que la página de Clientes haya cargado correctamente
        clientesPage.assertLoaded();
        // Completa el formulario de búsqueda con el número de póliza y filtra
        clientesPage.fillFormPorTipoDocumentoPensionSalud(tipo_documento, numero_documento);
        // Toma una captura de pantalla como evidencia después de completar el formulario
        ExtentEvidence.shot("Evidencia despues de completar el formulario con el numero de poliza");
        // Procesa la búsqueda y valida que sea exitosa
        clientesPage.processAndAssertSuccess(90_000,500);
        // Toma una captura de pantalla como evidencia después de filtrar
        ExtentEvidence.shot("Evidencia despues de filtrar con el numero de poliza");
        // Procesa la selección del cliente y accede al perfil del cliente
        clientesPage.processAndAssertSuccessPerfilCliente(50_000,500);
    }

    @Then("en la pagina Perfil Cliente se valida que la poliza esta vigente y el RUC esta activo")
    public void enLaPaginaPerfilClienteSeValidaQueLaPolizaEstaVigenteYElRUCEstaActivo() {
        // Verifica que la página de Perfil Cliente haya cargado correctamente
        perfilClientePage.assertLoaded();
        // Valida que la póliza esté vigente
        perfilClientePage.validarPolizaVigente();
    }

    @And("en la pagina Perfil Cliente seleccionamos una aplicacion para declarar la poliza con renovacion")
    public void enLaPaginaPerfilClienteSeleccionamosUnaAplicacionParaDeclararLaPolizaConRenovacion() {
        // Verifica que la página de Perfil Cliente haya cargado correctamente
        perfilClientePage.assertLoaded();
        // Selecciona el checkbox de una aplicación con estado no declarado
        perfilClientePage.seleccionarAplicacionConEstadoNoDeclaradaCheckBox();
        // Toma una captura de pantalla como evidencia después de seleccionar la aplicación
        ExtentEvidence.shot("Evidencia despues de seleccionar una aplicacion con estado no declarado");
        // Hace clic en el botón "Declarar Aplicación"
        perfilClientePage.seleccionarDeclararAplicacionButton();
        perfilClientePage.processAndAssertSuccessMultiplesRecibosPendientes(30_000,500);
    }
}
