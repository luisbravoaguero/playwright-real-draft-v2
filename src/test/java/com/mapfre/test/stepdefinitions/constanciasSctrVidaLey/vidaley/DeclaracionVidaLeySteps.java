package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.vidaley;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral.*;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class DeclaracionVidaLeySteps {
    private final PerfilClientePage perfilClientePage;
    private final DeclaracionCargaAseguradoPage declaracionCargaAseguradoPage;
    private final DeclaracionDetalleDeRiesgoPage declaracionDetalleDeRiesgoPage;
    private final DeclaracionReporteConstacia declaracionReporteConstacia;

    public DeclaracionVidaLeySteps(PageProvider pageProvider) {
        this.perfilClientePage = new PerfilClientePage(pageProvider.get());
        this.declaracionCargaAseguradoPage = new DeclaracionCargaAseguradoPage(pageProvider.get());
        this.declaracionDetalleDeRiesgoPage = new DeclaracionDetalleDeRiesgoPage(pageProvider.get());
        this.declaracionReporteConstacia = new DeclaracionReporteConstacia(pageProvider.get());
    }
    @And("en la pagina Perfil Cliente seleccionamos una aplicacion para declarar la poliza vidaley")
    public void enLaPaginaPerfilClienteSeleccionamosUnaAplicacionParaDeclararLaPolizaVidaley() {
        // Verifica que la página de Perfil Cliente haya cargado correctamente
        perfilClientePage.assertLoaded();
        // Selecciona el checkbox de una aplicación con estado no declarado
        perfilClientePage.seleccionarAplicacionConEstadoNoDeclaradaVidaLeyCheckBox();
        // Toma una captura de pantalla como evidencia después de seleccionar la aplicación
        ExtentEvidence.shot("Evidencia despues de seleccionar una aplicacion con estado no declarado");
        // Hace clic en el botón "Declarar Aplicación"
        perfilClientePage.seleccionarDeclararAplicacionButton();
        // Toma una captura de pantalla como evidencia después de hacer clic en declarar
        ExtentEvidence.shot("Evidencia despues de seleccionar el boton declarar aplicacion");
        // Procesa y valida la aparición del modal de recibos pendientes
        //perfilClientePage.processAndAssertSuccessRecibosPendientes(30_000,500);
        // Toma una captura de pantalla como evidencia del modal de recibos pendientes
        //ExtentEvidence.shot("Evidencia despues del modal recibos pendientes");
        // Hace clic en el botón "Aceptar" del modal de recibos pendientes
        //perfilClientePage.clickAceptarRecibosPendientesButton();
        // Procesa y valida la aparición del botón de declaración
        perfilClientePage.processAndAssertSuccessMultiplesRecibosPendientes(30_000,500);
        //perfilClientePage.processAndAssertSuccessDeclaracionButton(30_000,500);
    }

    @And("en la pagina Declaracion completamos el formulario Datos de los asegurados vidaley")
    public void enLaPaginaDeclaracionCompletamosElFormularioDatosDeLosAseguradosVidaley() {
        // Verifica que la página de Carga de Asegurado haya cargado correctamente
        declaracionCargaAseguradoPage.assertDeclaracionLoaded();
        // Completa el formulario de carga individual de asegurados
        declaracionCargaAseguradoPage.fillAseguradosVidaLeyCargaIndividual();
        // Toma una captura de pantalla como evidencia después de completar asegurados
        ExtentEvidence.shot("Evidencia despues de completar el formulario asegurados carga individual");
        // Procesa y valida la aparición del modal de lista de observaciones
        declaracionCargaAseguradoPage.processAndAssertSuccessListaDeObservacionesVidaLey(60_000,500);
        // Toma una captura de pantalla como evidencia del modal de lista de observaciones
        ExtentEvidence.shot("Evidencia despues del modal Lista de Observaciones");
        // Procesa y valida la aparición del botón generar
        declaracionCargaAseguradoPage.processAndAssertSuccessCargaDeAsegurados(60_000,500);
        declaracionCargaAseguradoPage.processAndAssertSuccessBotonSiguiente(60_000,500);
    }


    @Then("en la pagina Resumen Declaracion Vida Ley se muestra el resumen de la aplicacion declarada")
    public void enLaPaginaResumenDeclaracionVidaLeySeMuestraElResumenDeLaAplicacionDeclarada() {
        // Procesa y valida que aparezca la pantalla final de declaración exitosa
        declaracionDetalleDeRiesgoPage.processAndAssertSuccessProcesarDeclaracionVidaLeyPantallaFinal(150_000,500);
        // Verifica que la página de Reporte Constancia haya cargado correctamente
        declaracionReporteConstacia.assertVidaLeyLoaded();
    }
}
