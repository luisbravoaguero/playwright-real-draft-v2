package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.vidaley;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral.InclusionPlanillaDetalleDeRiesgoPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral.InclusionPlanillaPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral.InclusionReporteConstanciaPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral.PerfilCienteInclusionPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class InclusionVidaLeySteps {
    private final PerfilCienteInclusionPage perfilCienteInclusionPage;
    private final InclusionPlanillaPage inclusionPlanillaPage;
    private final InclusionPlanillaDetalleDeRiesgoPage inclusionPlanillaDetalleDeRiesgoPage;
    private final InclusionReporteConstanciaPage inclusionReporteConstanciaPage;
    public InclusionVidaLeySteps(PageProvider pageProvider) {
        this.perfilCienteInclusionPage = new PerfilCienteInclusionPage(pageProvider.get());
        this.inclusionPlanillaPage = new InclusionPlanillaPage(pageProvider.get());
        this.inclusionPlanillaDetalleDeRiesgoPage = new InclusionPlanillaDetalleDeRiesgoPage(pageProvider.get());
        this.inclusionReporteConstanciaPage = new InclusionReporteConstanciaPage(pageProvider.get());
    }

    @And("en la pagina Perfil Cliente seleccionamos una aplicacion para incluir la poliza vidaley")
    public void enLaPaginaPerfilClienteSeleccionamosUnaAplicacionParaIncluirLaPolizaVidaley() {
        // Verifica que la página de Perfil Cliente de Inclusión haya cargado correctamente
        perfilCienteInclusionPage.assertLoaded();
        // Selecciona el checkbox de una aplicación con estado declarado
        perfilCienteInclusionPage.seleccionarAplicacionConEstadoNoDeclaradaVidaLeyCheckBox();
        // Toma una captura de pantalla como evidencia después de seleccionar la aplicación
        ExtentEvidence.shot("Evidencia despues de seleccionar una aplicacion con estado declarada");
        // Hace clic en el botón "Incluir Aplicación"
        perfilCienteInclusionPage.seleccionarIncluirAplicacionButton();
        // Procesa y valida la aparición del modal de recibos pendientes
        perfilCienteInclusionPage.processAndAssertSuccessRecibosPendientes(30_000,500);
        // Toma una captura de pantalla como evidencia del modal de recibos pendientes
        ExtentEvidence.shot("Evidencia despues del modal recibos pendientes");
    }

    @And("en la pagina Incluir Planilla completamos el formulario Datos de los asegurados vidaley")
    public void enLaPaginaIncluirPlanillaCompletamosElFormularioDatosDeLosAseguradosVidaley() {
        // Verifica que la página de Inclusión de Planilla haya cargado correctamente
        inclusionPlanillaPage.assertLoaded();
        // Genera nueve dígitos aleatorios para el documento de identidad del Perú
        String numeroDocumento = RandomData.idDocumentPeru(9,8);
        // Completa el formulario con los detalles de la póliza
        inclusionPlanillaPage.fillAseguradosCargaIndividualVidaLey(numeroDocumento);
        // Selecciona la opción "Facturada" para la inclusión
        inclusionPlanillaPage.seleccionarFacturadaVidaLey();
        ExtentEvidence.shot("Evidencia despues de completar el formulario asegurados carga individual");
        // Procesa y valida la aparición del modal de lista de observaciones
        inclusionPlanillaPage.processAndAssertSuccessListaDeObservacionesVidaLey(60_000,500);
        // Toma una captura de pantalla como evidencia del modal de lista de observaciones
        ExtentEvidence.shot("Evidencia despues del modal Lista de Observaciones");
        // Procesa y valida el mensaje que se ha cargado el número de asegurados
        inclusionPlanillaPage.processAndAssertSuccessCargaDeAsegurados(60_000,500);
        ExtentEvidence.shot("Evidencia despues de validar que se ha cargado el numero de asegurados");
        // Procesa y valida la aparición del modal de lista de observaciones
        inclusionPlanillaPage.clickSiguienteAngularButton();
        //inclusionPlanillaPage.processAndAssertSuccessListaDeObservaciones(60_000,500);
        // Procesa y valida la aparición del botón siguiente
        inclusionPlanillaPage.processAndAssertSuccessBotonSiguienteVidaLey(40_000,500);
    }

    @Then("en la pagina Inclusion de Planilla se muestra el resumen de la aplicacion incluida vidaley")
    public void enLaPaginaInclusionDePlanillaSeMuestraElResumenDeLaAplicacionIncluidaVidaley() {
        // Procesa y valida que aparezca la pantalla final de inclusión exitosa
        inclusionPlanillaDetalleDeRiesgoPage.processAndAssertSuccessProcesarInclusionVidaLeyPantallaFinal(150_000,500);
        // Verifica que la página de Reporte Constancia haya cargado correctamente
        inclusionReporteConstanciaPage.assertVidaLeyLoaded();
    }
}
