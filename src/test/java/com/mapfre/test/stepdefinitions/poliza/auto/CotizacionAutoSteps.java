package com.mapfre.test.stepdefinitions.poliza.auto;

import com.mapfre.playwright.pageobjects.poliza.auto.cotizacion.CotizacionGuardadaPage;
import com.mapfre.playwright.pageobjects.poliza.auto.cotizacion.DatosDelAutoPage;
import com.mapfre.playwright.pageobjects.poliza.auto.PolizaAutoPage;
import com.mapfre.playwright.pageobjects.poliza.auto.cotizacion.ProductosACotizarPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.test.hooks.ScenarioKeys;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CotizacionAutoSteps {
    private static final Logger log = LoggerFactory.getLogger(CotizacionAutoSteps.class);
    private final PolizaAutoPage polizaAutoPage;
    private final DatosDelAutoPage datosDelAutoPage;
    private final ProductosACotizarPage productosACotizarPage;
    private final CotizacionGuardadaPage cotizacionGuardadaPage;
    private final ScenarioContext scenarioContext;

    public CotizacionAutoSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.polizaAutoPage = new PolizaAutoPage(pageProvider.get());
        this.datosDelAutoPage = new DatosDelAutoPage(pageProvider.get());
        this.productosACotizarPage = new ProductosACotizarPage(pageProvider.get());
        this.cotizacionGuardadaPage = new CotizacionGuardadaPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }

    @And("en la pagina Auto selecciona la opcion Cotizar poliza de Auto")
    public void enLaPaginaAutoSeleccionaLaOpcionCotizarPolizaDeAuto() {
        polizaAutoPage.assertLoaded();
        polizaAutoPage.clickCotizarPolizaAutosButton();
    }
    @And("en la pagina Cotización póliza de auto ingresa el numero de placa")
    public void enLaPaginaCotizaciónPólizaDeAutoIngresaElNumeroDePlaca() {
        String numeroPlaca = RandomData.randomPlate();
        String numeroSerie = RandomData.randomVehicleIdentificationNumber();
        String numeroMotor = RandomData.randomEngineNumber();
        //metodo de ejemplo que recibe los datos generados desde el step, estos datos se envian a traves de este metodo.
        datosDelAutoPage.imprimirDatosDelAuto(numeroPlaca, numeroSerie, numeroMotor);
        scenarioContext.put(ScenarioKeys.PolizaAuto.NUMERO_PLACA_VEHICULO,numeroPlaca);
        scenarioContext.put(ScenarioKeys.PolizaAuto.NUMERO_SERIE_VIN_VEHICULO,numeroSerie);
        scenarioContext.put(ScenarioKeys.PolizaAuto.NUMERO_MOTOR_VEHICULO,numeroMotor);
        //impresiones de ejemplo para validar el funcionamiento de los metodos randomPlate(), randomVehicleIdentificationNumber() y randomEngineNumber()
        //log.info("Numero de placa generado: {}", numeroPlaca);
        //log.info("Numero de serie generado: {}", numeroSerie);
        //log.info("Numero de motor generado: {}", numeroMotor);
        datosDelAutoPage.sendNumeroPlaca(numeroPlaca);
        datosDelAutoPage.processAndAssertSuccessMultiplesModales(40_000,500);


    }

    @And("en la pagina Cotización póliza de auto en la seccion Bien Asegurado se ingresa el tipo de vehiculo {string}, marca y modelo {string}, anio de fabricacion {string}")
    public void enLaPaginaCotizaciónPólizaDeAutoEnLaSeccionBienAseguradoSeIngresaElTipoDeVehiculoMarcaYModeloAnioDeFabricacion(String tipo_vehiculo, String marca_modelo, String anio_fabricacion) {
        datosDelAutoPage.fillFormBienAsegurado(tipo_vehiculo, marca_modelo, anio_fabricacion);

    }
    @And("en la pagina Cotización póliza de auto en la seccion Contratante y circulación del riesgo se ingresa el tipo de documento {string}, numero de documento {string}, nombre {string}, apellido paterno {string}, apellido materno {string}, fecha nacimiento {string}, correo electronico {string}, sexo {string}, departmento {string}, provincia {string}, distrito {string}")
    public void enLaPaginaCotizaciónPólizaDeAutoEnLaSeccionContratanteYCirculaciónDelRiesgoSeIngresaElTipoDeDocumentoNumeroDeDocumentoNombreApellidoPaternoApellidoMaternoFechaNacimientoCorreoElectronicoSexoDepartmentoProvinciaDistrito(String tipo_documento, String numero_documento, String nombre, String ap_paterno, String ap_materno, String fecha_nacimiento, String correo_electronico, String sexo, String departamento, String provincia, String distrito) {
        datosDelAutoPage.fillFormContrante(tipo_documento, numero_documento, nombre, ap_paterno, ap_materno, fecha_nacimiento, correo_electronico, sexo, departamento, provincia, distrito);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario contrante completado");
        datosDelAutoPage.clickFormContrante();
    }

    @And("en la pagina Cotización póliza de auto en la seccion Elige el producto a cotizar se ingresa el producto {string} y tipo de uso {string}")
    public void enLaPaginaCotizaciónPólizaDeAutoEnLaSeccionEligeElProductoACotizarSeIngresaElProductoYTipoDeUso(String producto, String tipo_uso) {
        productosACotizarPage.fillFormEligeProducto(producto, tipo_uso);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario elige producto");
        productosACotizarPage.clickFormEligeProducto();
        ExtentEvidence.shot("Evidencia despues de hace click en el boton generar cotizacion");
        productosACotizarPage.processAndAssertSuccess(40_000,500);
    }

    @Then("el sistema muestra el resumen de la cotización")
    public void elSistemaMuestraElResumenDeLaCotización() {
        cotizacionGuardadaPage.assertLoaded();
        String numeroCotizacion = cotizacionGuardadaPage.obtenerNumeroCotizacion();
        scenarioContext.put(ScenarioKeys.PolizaAuto.NUMERO_COTIZACION_VEHICULO, numeroCotizacion);
    }

    @And("en la pagina Cotización póliza de auto en la seccion Bien Asegurado se ingresa el tipo de vehiculo {string}, marca y modelo {string}, anio de fabricacion {string}, estado del vehiculo {string}")
    public void enLaPaginaCotizaciónPólizaDeAutoEnLaSeccionBienAseguradoSeIngresaElTipoDeVehiculoMarcaYModeloAnioDeFabricacionEstadoDelVehiculo(String tipo_vehiculo, String marca_modelo, String anio_fabricacion, String estado_vehiculo) {
        datosDelAutoPage.fillFormBienAsegurado(tipo_vehiculo, marca_modelo, anio_fabricacion, estado_vehiculo);
    }
}
