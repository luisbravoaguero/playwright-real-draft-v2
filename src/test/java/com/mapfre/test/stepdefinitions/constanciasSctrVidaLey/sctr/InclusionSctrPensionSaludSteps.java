package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral.InclusionPlanillaDetalleDeRiesgoPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral.InclusionPlanillaPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral.InclusionReporteConstanciaPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral.PerfilCienteInclusionPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import testdata.TemplateFactory;

import java.nio.file.Path;

public class InclusionSctrPensionSaludSteps {
    private final PerfilCienteInclusionPage perfilCienteInclusionPage;
    private final InclusionPlanillaPage inclusionPlanillaPage;
    private final InclusionPlanillaDetalleDeRiesgoPage inclusionPlanillaDetalleDeRiesgoPage;
    private final InclusionReporteConstanciaPage inclusionReporteConstanciaPage;

    /**
     * Constructor que inicializa todas las páginas necesarias para los pasos de prueba
     * de inclusión SCTR de Pensión y Salud utilizando el proveedor de páginas.
     *
     * @param pageProvider proveedor que gestiona las instancias de página
     */
    public InclusionSctrPensionSaludSteps(PageProvider pageProvider) {
        this.perfilCienteInclusionPage = new PerfilCienteInclusionPage(pageProvider.get());
        this.inclusionPlanillaPage = new InclusionPlanillaPage(pageProvider.get());
        this.inclusionPlanillaDetalleDeRiesgoPage = new InclusionPlanillaDetalleDeRiesgoPage(pageProvider.get());
        this.inclusionReporteConstanciaPage = new InclusionReporteConstanciaPage(pageProvider.get());
    }
    /**
     * Paso de prueba que selecciona una aplicación con estado declarado en la página Perfil Cliente
     * e inicia el proceso de inclusión.
     */
    @And("en la pagina Perfil Cliente seleccionamos una aplicacion para incluir la poliza")
    public void enLaPaginaPerfilClienteSeleccionamosUnaAplicacionParaIncluirLaPoliza() {
        // Verifica que la página de Perfil Cliente de Inclusión haya cargado correctamente
        perfilCienteInclusionPage.assertLoaded();
        // Selecciona el checkbox de una aplicación con estado declarado
        perfilCienteInclusionPage.seleccionarAplicacionConEstadoDeclaradaCheckBox();
        // Toma una captura de pantalla como evidencia después de seleccionar la aplicación
        ExtentEvidence.shot("Evidencia despues de seleccionar una aplicacion con estado declarada");
        // Hace clic en el botón "Incluir Aplicación"
        perfilCienteInclusionPage.seleccionarIncluirAplicacionButton();
        // Procesa y valida la aparición del modal de recibos pendientes
        perfilCienteInclusionPage.processAndAssertSuccessRecibosPendientes(30_000,500);
        // Toma una captura de pantalla como evidencia del modal de recibos pendientes
        ExtentEvidence.shot("Evidencia despues del modal recibos pendientes");
    }

    /**
     * Paso de prueba que completa el formulario "Datos de los asegurados" en la página de Inclusión
     * con detalles de póliza e información de asegurados.
     */
    @And("en la pagina Incluir Planilla completamos el formulario Datos de los asegurados")
    public void enLaPaginaIncluirPlanillaCompletamosElFormularioDatosDeLosAsegurados() {
        // Verifica que la página de Inclusión de Planilla haya cargado correctamente
        inclusionPlanillaPage.assertLoaded();
        // Completa el formulario con los detalles de la póliza
        inclusionPlanillaPage.fillFormDetallesPolizas();
        // Toma una captura de pantalla como evidencia después de completar detalles de póliza
        ExtentEvidence.shot("Evidencia despues de completar el formulario detalles de poliza");
        // Genera nueve dígitos aleatorios para el documento de identidad del Perú
        String numeroDocumento = RandomData.idDocumentPeru(9,8);
        // Completa el formulario de carga individual de asegurados con el número de documento generado
        inclusionPlanillaPage.fillAseguradosCargaIndividual(numeroDocumento);
        // Selecciona la opción "No Facturada" para la inclusión
        inclusionPlanillaPage.seleccionarFacturadaSctr();
        // Hace clic en el botón "Siguiente" de Angular para avanzar al siguiente paso
        inclusionPlanillaPage.clickSiguienteAngularButton();
        // Procesa y valida la aparición del modal de lista de observaciones
        inclusionPlanillaPage.processAndAssertSuccessListaDeObservaciones(60_000,500);
        // Toma una captura de pantalla como evidencia del modal de lista de observaciones
        ExtentEvidence.shot("Evidencia despues del modal Lista de Observaciones");
        // Hace clic en el botón "Aceptar" del modal de lista de observaciones
        inclusionPlanillaPage.clickAceptarListaObservacionesButton();
        // Procesa y valida la aparición del botón siguiente
        inclusionPlanillaPage.processAndAssertSuccessBotonSiguiente(40_000,500);
    }

    /**
     * Paso de prueba que completa el formulario "Información de Inclusión" en la página de Inclusión
     * con datos de obra relacionados al riesgo.
     */
    @And("en la pagina Incluir Planilla completamos el formulario Informacion de Inclusion")
    public void enLaPaginaIncluirPlanillaCompletamosElFormularioInformacionDeInclusion() {
        // Verifica que la página de Detalle de Riesgo de Inclusión haya cargado correctamente
        inclusionPlanillaDetalleDeRiesgoPage.assertLoaded();
        // Completa el formulario con los datos de la obra (riesgo)
        inclusionPlanillaDetalleDeRiesgoPage.fillDatosObra();
    }

    /**
     * Paso de prueba que selecciona el botón "Generar" para procesar la inclusión
     * y valida que se complete exitosamente.
     */
    @And("en la pagina Incluir Planilla seleccionamos el boton Generar")
    public void enLaPaginaIncluirPlanillaSeleccionamosElBotonGenerar() {
        // Hace clic en el botón "Generar" para iniciar el proceso de inclusión
        inclusionPlanillaDetalleDeRiesgoPage.clickGenerarButton();
        // Procesa y valida la aparición del modal de procesamiento de generación
        inclusionPlanillaDetalleDeRiesgoPage.processAndAssertSuccessProcesarBotonGenerar(20_000,500);
        // Toma una captura de pantalla como evidencia del modal de procesamiento
        ExtentEvidence.shot("Evidencia despues del modal procesar generacion declaracion");
        // Procesa y valida que la inclusión se haya completado con éxito
        inclusionPlanillaDetalleDeRiesgoPage.processAndAssertSuccessProcesarBotonGenerarconExito(120_000,500);
    }

    /**
     * Paso de prueba que valida que se muestre el resumen de la aplicación incluida
     * en la página de Inclusión de Planilla.
     */
    @Then("en la pagina Inclusion de Planilla se muestra el resumen de la aplicacion incluida")
    public void enLaPaginaInclusionDePlanillaSeMuestraElResumenDeLaAplicacionIncluida() {
        // Procesa y valida que aparezca la pantalla final de inclusión exitosa
        inclusionPlanillaDetalleDeRiesgoPage.processAndAssertSuccessProcesarInclusionSctrPantallaFinal(150_000,500);
        // Verifica que la página de Reporte Constancia haya cargado correctamente
        inclusionReporteConstanciaPage.assertLoaded();
    }

    /*@And("en la pagina Declaracion completamos los datos de los asegurados de forma masiva sctr mineria")
    public void enLaPaginaDeclaracionCompletamosLosDatosDeLosAseguradosDeFormaMasivaSctrMineria() {
        inclusionPlanillaPage.assertLoadedImportarPlanillaMineria();
        String nineDigits = RandomData.idDocumentPeru(9,8);
        Path template = TemplateFactory.buildPlanillaWithNineDigits(nineDigits, "File_declaracion_sctr_mineria_mes adelantado", 1, 1);
        //System.out.println("template: "+template);
        inclusionPlanillaPage.fillFormDatosDeLosAseguradosSctrMineria(template);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos de los asegurados");
        inclusionPlanillaPage.processAndAssertSuccessProcesarPlanillaAseguradoMineria(70_000,500);
        inclusionPlanillaPage.validarAseguradosCargadosSctrMineria(nineDigits);
        ExtentEvidence.shot("Evidencia despues de procesar la planilla de asegurados");
        inclusionPlanillaPage.processAndAssertSuccessBotonSiguienteMineria(60_000,500);
        ExtentEvidence.shot("Evidencia despues de dar click en el boton siguiente de la planilla de asegurados");
        inclusionPlanillaPage.processAndAssertSuccessListaDeObservacionesMineria(60_000,500);
    }*/

    @And("en la pagina Incluir completamos los datos de los asegurados de forma masiva sctr mineria")
    public void enLaPaginaIncluirCompletamosLosDatosDeLosAseguradosDeFormaMasivaSctrMineria() {
        inclusionPlanillaPage.assertLoadedImportarPlanillaMineria();
        String nineDigits = RandomData.idDocumentPeru(9,8);
        Path template = TemplateFactory.buildPlanillaWithNineDigits(nineDigits, "File_declaracion_sctr_mineria_mes adelantado", 1, 1);
        //System.out.println("template: "+template);
        inclusionPlanillaPage.fillFormDatosDeLosAseguradosSctrMineria(template);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos de los asegurados");
        inclusionPlanillaPage.processAndAssertSuccessProcesarPlanillaAseguradoMineria(70_000,500);
        inclusionPlanillaPage.validarAseguradosCargadosSctrMineria(nineDigits);
        ExtentEvidence.shot("Evidencia despues de procesar la planilla de asegurados");
        inclusionPlanillaPage.processAndAssertSuccessBotonSiguienteMineria(60_000,500);
        ExtentEvidence.shot("Evidencia despues de dar click en el boton siguiente de la planilla de asegurados");
        inclusionPlanillaPage.processAndAssertSuccessListaDeObservacionesMineria(60_000,500);
    }
}
