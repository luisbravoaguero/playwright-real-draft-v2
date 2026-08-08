package com.mapfre.test.stepdefinitions.inspeccionAutos;

import com.mapfre.playwright.pageobjects.inspeccionAutos.InspeccionAutosCotizacionesPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.test.hooks.ScenarioKeys;
import io.cucumber.java.en.And;

public class InspeccionAutosCotizacionesSteps {
    private final InspeccionAutosCotizacionesPage inspeccionAutosCotizacionesPage;
    private final ScenarioContext scenarioContext;
    public InspeccionAutosCotizacionesSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.inspeccionAutosCotizacionesPage = new InspeccionAutosCotizacionesPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }

    @And("en la pagina Cotizaciones de Inspeccion de Autos seleccionamos la cotizacion {string} en el rango de fechas {string} y {string} para realizar la inspeccion del vehiculo")
    public void enLaPaginaCotizacionesDeInspeccionDeAutosSeleccionamosLaCotizacionEnElRangoDeFechasYParaRealizarLaInspeccionDelVehiculo(String numero_cotizacion, String fecha_inicio_cotizacion, String fecha_fin_cotizacion) {
        String numeroCotizacion;
        if ("OPCIONAL".equalsIgnoreCase(numero_cotizacion)) {
            numeroCotizacion = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_COTIZACION_VEHICULO);
        } else {
            numeroCotizacion = numero_cotizacion;
            scenarioContext.put(ScenarioKeys.PolizaAuto.NUMERO_COTIZACION_VEHICULO, numeroCotizacion);
        }
        inspeccionAutosCotizacionesPage.assertLoadedCotizaciones();
        ExtentEvidence.shot("Evidencia de la pagina Cotizaciones de Inspeccion de Autos");
        inspeccionAutosCotizacionesPage.clickBotonFiltrar();
        if(!"OPCIONAL".equalsIgnoreCase(fecha_inicio_cotizacion) || !"OPCIONAL".equalsIgnoreCase(fecha_fin_cotizacion)) {
            inspeccionAutosCotizacionesPage.fillNumeroCotizacion(numeroCotizacion, fecha_inicio_cotizacion, fecha_fin_cotizacion);
            ExtentEvidence.shot("Evidencia de la pagina Cotizaciones de Inspeccion de Autos con el numero de cotizacion: " + numeroCotizacion + " y el rango de fechas: " + fecha_inicio_cotizacion + " y " + fecha_fin_cotizacion);
        }else{
            inspeccionAutosCotizacionesPage.fillNumeroCotizacion(numeroCotizacion);
            ExtentEvidence.shot("Evidencia de la pagina Cotizaciones de Inspeccion de Autos con el numero de cotizacion: " + numeroCotizacion);
            inspeccionAutosCotizacionesPage.fillRangoFechasPorDefecto();
            ExtentEvidence.shot("Evidencia de la pagina Cotizaciones de Inspeccion de Autos con el rango de fechas por defecto");
        }
        inspeccionAutosCotizacionesPage.clickBotonFiltrar();
        ExtentEvidence.shot("Evidencia de la pagina Cotizaciones de Inspeccion de Autos despues de dar click en el boton filtrar con el numero de cotizacion: " + numeroCotizacion);
        inspeccionAutosCotizacionesPage.processAndAssertSuccessBusquedaNumeroCotizacion(40_000,500);
        ExtentEvidence.shot("Evidencia despues de buscar la cotizacion con el numero: " + numeroCotizacion);
        inspeccionAutosCotizacionesPage.encontrarNumeroCotizacionEnResultados(numeroCotizacion);
        ExtentEvidence.shot("Evidencia despues de encontrar la cotizacion en los resultados con el numero: " + numeroCotizacion);
        inspeccionAutosCotizacionesPage.seleccionarVerCotizacion(numeroCotizacion);
        inspeccionAutosCotizacionesPage.processAndAssertSuccessSeleccionarVerCotizacion(40_000,500);
    }
}
