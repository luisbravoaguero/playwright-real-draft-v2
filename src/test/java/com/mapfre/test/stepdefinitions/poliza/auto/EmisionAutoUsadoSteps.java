package com.mapfre.test.stepdefinitions.poliza.auto;

import com.mapfre.playwright.pageobjects.poliza.auto.emsion.EmisionAutoUsadoPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.test.hooks.ScenarioKeys;
import io.cucumber.java.en.And;

public class EmisionAutoUsadoSteps {
    private final EmisionAutoUsadoPage emisionAutoUsadoPage;
    private final ScenarioContext scenarioContext;
    public EmisionAutoUsadoSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.emisionAutoUsadoPage = new EmisionAutoUsadoPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @And("en la pagina Emision poliza auto usado, en el paso Seleccionar inspeccion, seccion Elige un auto inspeccionado para emitir, ingresamos y buscamos el numero de placa del vehiculo inspeccionado")
    public void enLaPaginaEmisionPolizaAutoUsadoEnElPasoSeleccionarInspeccionSeccionEligeUnAutoInspeccionadoParaEmitirIngresamosYBuscamosElNumeroDePlacaDelVehiculoInspeccionado() {
        String numeroPlaca = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_PLACA_VEHICULO);
        emisionAutoUsadoPage.assertLoaded();
        emisionAutoUsadoPage.ingresarNumeroPlacaInspeccion(numeroPlaca);
        emisionAutoUsadoPage.processAndAssertSuccessBusquedaNumeroPlacaParaEmisionAutoUsado(30_000, 500);
        emisionAutoUsadoPage.validarNumeroPlacaResultadoBusqueda(numeroPlaca);
        ExtentEvidence.shot("Evidencia despues de validar el numero de placa en el resultado de busqueda con numero de placa = " + numeroPlaca);
        emisionAutoUsadoPage.processAndAssertSuccessSeleccionarInspeccionSiguienteButton(30_000, 500);
    }

    @And("en la pagina Emision poliza auto usado, en el paso Seleccionar inspeccion, seccion Elige un auto inspeccionado para emitir, ingresamos y buscamos el numero de placa {string} del vehiculo inspeccionado")
    public void enLaPaginaEmisionPolizaAutoUsadoEnElPasoSeleccionarInspeccionSeccionEligeUnAutoInspeccionadoParaEmitirIngresamosYBuscamosElNumeroDePlacaDelVehiculoInspeccionado(String numero_placa) {
        String numeroPlaca;
        if("OPCIONAL".equalsIgnoreCase(numero_placa)) {
            numeroPlaca = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_PLACA_VEHICULO);
        }else{
            numeroPlaca = numero_placa;
            scenarioContext.put(ScenarioKeys.PolizaAuto.NUMERO_PLACA_VEHICULO, numeroPlaca);
        }
        emisionAutoUsadoPage.assertLoaded();
        emisionAutoUsadoPage.ingresarNumeroPlacaInspeccion(numeroPlaca);
        emisionAutoUsadoPage.processAndAssertSuccessBusquedaNumeroPlacaParaEmisionAutoUsado(30_000, 500);
        emisionAutoUsadoPage.validarNumeroPlacaResultadoBusqueda(numeroPlaca);
        ExtentEvidence.shot("Evidencia despues de validar el numero de placa en el resultado de busqueda con numero de placa = " + numeroPlaca);
        emisionAutoUsadoPage.processAndAssertSuccessSeleccionarInspeccionSiguienteButton(30_000, 500);

    }

    @And("en la pagina Emision poliza auto usado, en el paso Datos de la poliza, seccion Elige el producto a emitir, ingresamos el producto {string} y tipo de uso {string}")
    public void enLaPaginaEmisionPolizaAutoUsadoEnElPasoDatosDeLaPolizaSeccionEligeElProductoAEmitirIngresamosElProductoYTipoDeUso(String producto, String tipo_uso) {
        emisionAutoUsadoPage.assertLoadedElegirProductoEmitirLabel();
        emisionAutoUsadoPage.fillFormDatosPoliza(producto, tipo_uso);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de datos de la poliza con producto = " + producto + " y tipo_uso = " + tipo_uso);
        emisionAutoUsadoPage.processAndAssertSuccessDatosDeLaPolizaSiguienteButton(30_000, 500);
    }

    @And("en la pagina Emision Poliza auto usado, en el paso Datos del contratante, ingresamos la informacion del contratante y los datos del vehiculo")
    public void enLaPaginaEmisionPolizaAutoUsadoEnElPasoDatosDelContratanteIngresamosLaInformacionDelContratanteYLosDatosDelVehiculo() {
        emisionAutoUsadoPage.assertLoadedDatosContratanteLabel();
        emisionAutoUsadoPage.fillFormDatosContratante();
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de datos del contratante");
        emisionAutoUsadoPage.processAndAssertSuccessDatosDelContranteSiguienteButton(30_000, 500);
    }

    @And("en la pagina Emision Poliza auto usado, en el paso Datos del contratante, ingresamos la informacion del contratante numero documento {string}, nombre {string}, apellido paterno {string}, materno {string} y los datos del vehiculo")
    public void enLaPaginaEmisionPolizaAutoUsadoEnElPasoDatosDelContratanteIngresamosLaInformacionDelContratanteNumeroDocumentoNombreApellidoPaternoMaternoYLosDatosDelVehiculo(String numero_documento, String nombre, String apellido_paterno, String apellido_materno) {
        emisionAutoUsadoPage.assertLoadedDatosContratanteLabel();
        emisionAutoUsadoPage.fillFormDatosContratante(numero_documento, nombre, apellido_paterno, apellido_materno);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de datos del contratante");
        emisionAutoUsadoPage.processAndAssertSuccessDatosDelContranteSiguienteButton(30_000, 500);
    }

    @And("en la pagina Emision Poliza auto usado, en el paso Datos de financiamiento ingresamos el tipo de financiamiento al contado para la calcular la prima de la poliza")
    public void enLaPaginaEmisionPolizaAutoUsadoEnElPasoDatosDeFinanciamientoIngresamosElTipoDeFinanciamientoAlContadoParaLaCalcularLaPrimaDeLaPoliza() {
        emisionAutoUsadoPage.assertLoadedFinanciamientoLabel();
        emisionAutoUsadoPage.fillFormFinanciamiento();
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de datos de financiamiento con tipo de financiamiento al contado");
        emisionAutoUsadoPage.processAndAssertSuccessFinanciamientoCalcularPrimaButton(60_000, 500);
        emisionAutoUsadoPage.validarTotalPrimaCalculadaContieneUnValor();
        ExtentEvidence.shot("Evidencia despues de validar que el total prima calculada contiene un valor");
        emisionAutoUsadoPage.processAndAssertSuccessFinanciamientoSiguienteButton(50_000, 500);
    }

    @And("en la pagina Emision Poliza auto usado, en el paso Emitir poliza seleccionamos el boton Emitir poliza")
    public void enLaPaginaEmisionPolizaAutoUsadoEnElPasoEmitirPolizaSeleccionamosElBotonEmitirPoliza() {
        emisionAutoUsadoPage.processAndAssertSuccessEmitirPolizaButton(150_000, 500);
        ExtentEvidence.shot("Evidencia despues de hacer click en el boton emitir poliza donde se muestra el modal Info");
        emisionAutoUsadoPage.validarExitoEmisionDentroModalInfo();
    }



}
