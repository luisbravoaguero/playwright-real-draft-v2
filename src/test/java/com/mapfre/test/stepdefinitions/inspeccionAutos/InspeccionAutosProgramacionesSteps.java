package com.mapfre.test.stepdefinitions.inspeccionAutos;

import com.mapfre.playwright.pageobjects.inspeccionAutos.InspeccionAutosProgramacionesPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.test.hooks.ScenarioKeys;
import io.cucumber.java.en.And;

public class InspeccionAutosProgramacionesSteps {
    private final InspeccionAutosProgramacionesPage inspeccionAutosProgramacionesPage;
    private final ScenarioContext scenarioContext;

    public InspeccionAutosProgramacionesSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.inspeccionAutosProgramacionesPage = new InspeccionAutosProgramacionesPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @And("en la pagina Programaciones de Inspeccion de Autos buscamos y seleccionamos el numero de solicitud de inspeccion con estado PROGRAMADA")
    public void enLaPaginaProgramacionesDeInspeccionDeAutosBuscamosYSeleccionamosElNumeroDeSolicitudDeInspeccionConEstadoPROGRAMADA() {
        String numeroNuevaSolicitud = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO);
        inspeccionAutosProgramacionesPage.assertLoadedProgramaciones();
        inspeccionAutosProgramacionesPage.fillFormBuscarProgramacionInspeccion(numeroNuevaSolicitud);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de busqueda de solicitud de inspeccion con numeroNuevaSolicitud = " + numeroNuevaSolicitud);
        inspeccionAutosProgramacionesPage.processAndAssertSuccessBuscarProgramacionInspeccion(20_000, 500);
        inspeccionAutosProgramacionesPage.encontrarNumeroSolicitudEnResultados(numeroNuevaSolicitud);
        ExtentEvidence.shot("Evidencia despues de encontrar el numero de solicitud en los resultados de busqueda");
        inspeccionAutosProgramacionesPage.clickVerProgramacionInspeccionButton(numeroNuevaSolicitud);
        inspeccionAutosProgramacionesPage.processAndAssertSuccessclickVerProgramacionInspeccionButton(30_000, 500);
    }
}
