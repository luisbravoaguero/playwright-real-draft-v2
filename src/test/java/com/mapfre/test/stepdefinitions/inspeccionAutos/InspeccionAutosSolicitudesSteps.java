package com.mapfre.test.stepdefinitions.inspeccionAutos;

import com.mapfre.playwright.pageobjects.inspeccionAutos.InspeccionAutosSolicitudesPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.test.hooks.ScenarioKeys;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class InspeccionAutosSolicitudesSteps {
    private final InspeccionAutosSolicitudesPage inspeccionAutosSolicitudesPage;
    private final ScenarioContext scenarioContext;
    public InspeccionAutosSolicitudesSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.inspeccionAutosSolicitudesPage = new InspeccionAutosSolicitudesPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @And("programa cita")
    public void programaCita() {
        String numeroNuevaSolicitud = "153991";
        inspeccionAutosSolicitudesPage.assertLoadedSolicitudes();
        inspeccionAutosSolicitudesPage.fillFormBuscarSolicitudInspeccion(numeroNuevaSolicitud);
        inspeccionAutosSolicitudesPage.processAndAssertSuccessBuscarSolicitudInspeccion(20_000, 500);
        inspeccionAutosSolicitudesPage.encontrarNumeroSolicitudEnResultados(numeroNuevaSolicitud);
        inspeccionAutosSolicitudesPage.clickProgramarInspeccionButton(numeroNuevaSolicitud);
        inspeccionAutosSolicitudesPage.processAndAssertSuccessClickProgramarInspeccionButton(20_000, 500);
    }
    @And("en la pagina Solicitudes de Inspeccion de Autos buscamos y seleccionamos el numero de solicitud de inspeccion con estado POR PROGRAMAR")
    public void enLaPaginaSolicitudesDeInspeccionDeAutosBuscamosYSeleccionamosElNumeroDeSolicitudDeInspeccionConEstadoPORPROGRAMAR() {
        String numeroNuevaSolicitud = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO);
        inspeccionAutosSolicitudesPage.assertLoadedSolicitudes();
        inspeccionAutosSolicitudesPage.fillFormBuscarSolicitudInspeccion(numeroNuevaSolicitud);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de busqueda de solicitud de inspeccion con numeroNuevaSolicitud = " + numeroNuevaSolicitud);
        inspeccionAutosSolicitudesPage.processAndAssertSuccessBuscarSolicitudInspeccion(20_000, 500);
        ExtentEvidence.shot("Evidencia despues de procesar la busqueda de solicitud de inspeccion");
        inspeccionAutosSolicitudesPage.encontrarNumeroSolicitudEnResultados(numeroNuevaSolicitud);
        ExtentEvidence.shot("Evidencia despues de encontrar el numero de solicitud en los resultados de busqueda");
        inspeccionAutosSolicitudesPage.clickProgramarInspeccionButton(numeroNuevaSolicitud);
        inspeccionAutosSolicitudesPage.processAndAssertSuccessClickProgramarInspeccionButton(20_000, 500);
    }

    @And("en la pagina Solicitudes de Inspeccion de Autos buscamos y seleccionamos el numero de solicitud de inspeccion con estado EN EVALUACION")
    public void enLaPaginaSolicitudesDeInspeccionDeAutosBuscamosYSeleccionamosElNumeroDeSolicitudDeInspeccionConEstadoENEVALUACION() {
        String numeroNuevaSolicitud = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO);
        inspeccionAutosSolicitudesPage.assertLoadedSolicitudes();
        inspeccionAutosSolicitudesPage.fillFormBuscarSolicitudEnEvaluacionInspeccion(numeroNuevaSolicitud);
        inspeccionAutosSolicitudesPage.processAndAssertSuccessBuscarSolicitudInspeccion(30_000, 500);
        inspeccionAutosSolicitudesPage.encontrarNumeroSolicitudEnResultados(numeroNuevaSolicitud);
        ExtentEvidence.shot("Evidencia despues de encontrar el numero de solicitud en los resultados de busqueda");
        inspeccionAutosSolicitudesPage.clickVerInspeccionButton(numeroNuevaSolicitud);
        inspeccionAutosSolicitudesPage.processAndAssertSuccessClickVerInspeccionButton(30_000, 500);
    }

    @And("en la pagina Solicitudes de Inspeccion de Autos buscamos y seleccionamos el numero de solicitud de inspeccion con estado TERMINADA")
    public void enLaPaginaSolicitudesDeInspeccionDeAutosBuscamosYSeleccionamosElNumeroDeSolicitudDeInspeccionConEstadoTERMINADA() {
        String numeroNuevaSolicitud = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO);
        inspeccionAutosSolicitudesPage.assertLoadedSolicitudes();
        inspeccionAutosSolicitudesPage.fillFormBuscarSolicitudTerminadaInspeccion(numeroNuevaSolicitud);
        ExtentEvidence.shot("Evidencia despues de encontrar el numero de solicitud en los resultados de busqueda");
        inspeccionAutosSolicitudesPage.processAndAssertSuccessBuscarSolicitudInspeccion(30_000, 500);
    }

    @Then("en la pagina Solicitudes de Inspeccion de Autos se muestra el estado de la inspeccion del vehiculo con estado TERMINADA")
    public void enLaPaginaSolicitudesDeInspeccionDeAutosSeMuestraElEstadoDeLaInspeccionDelVehiculoConEstadoTERMINADA() {
        String numeroNuevaSolicitud = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO);
        inspeccionAutosSolicitudesPage.verficaEstadoDeInspeccionTerminada(numeroNuevaSolicitud);
    }


}
