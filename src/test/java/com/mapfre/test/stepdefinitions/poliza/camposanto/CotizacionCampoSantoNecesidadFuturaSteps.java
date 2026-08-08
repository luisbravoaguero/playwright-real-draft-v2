package com.mapfre.test.stepdefinitions.poliza.camposanto;

import com.mapfre.playwright.pageobjects.poliza.camposanto.cotizacion.CotizacionCampoSantoNecesidadFuturaPage;
import com.mapfre.playwright.pageobjects.poliza.camposanto.cotizacion.ResumenCotizacionNecesidadFuturaPage;
import com.mapfre.playwright.pageobjects.poliza.camposanto.MenuPrincipalCampoSantoPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class CotizacionCampoSantoNecesidadFuturaSteps {
    private final CotizacionCampoSantoNecesidadFuturaPage cotizacionCampoSantoNecesidadFuturaPage;
    private final ResumenCotizacionNecesidadFuturaPage resumenCotizacionNecesidadFuturaPage;
    private final MenuPrincipalCampoSantoPage menuPrincipalCampoSantoPage;


    public CotizacionCampoSantoNecesidadFuturaSteps(PageProvider pageProvider) {

        this.cotizacionCampoSantoNecesidadFuturaPage = new CotizacionCampoSantoNecesidadFuturaPage(pageProvider.get());
        this.resumenCotizacionNecesidadFuturaPage = new ResumenCotizacionNecesidadFuturaPage(pageProvider.get());
        this.menuPrincipalCampoSantoPage = new MenuPrincipalCampoSantoPage(pageProvider.get());
    }

    @And("en la pagina Camposanto selecciona la opcion Cotizar contrato")
    public void enLaPaginaCamposantoSeleccionaLaOpcionCotizarContrato() {
        menuPrincipalCampoSantoPage.clickCotizarContratoButton();
    }

    @And("en la pagina Cotizador de Camposanto selecciona el ramo necesidad futura al contado")
    public void enLaPaginaCotizadorDeCamposantoSeleccionaElRamoNecesidadFuturaAlContado() {
        cotizacionCampoSantoNecesidadFuturaPage.assertLoadedCotizacionCampoSantoNecesidadFuturaPage();
        cotizacionCampoSantoNecesidadFuturaPage.clickNecesidadFuturaButton();
        cotizacionCampoSantoNecesidadFuturaPage.clickNecesidadFuturaContadoButton();
    }

    @And("en la pagina Cotizador de Camposanto los Datos del Producto camposanto {string} tipo de contrato {string} modalidad {string} producto {string}")
    public void enLaPaginaCotizadorDeCamposantoLosDatosDelProductoCamposantoTipoDeContratoModalidadProducto(String camposanto_producto, String tipo_contrato, String modalidad, String producto) {
        cotizacionCampoSantoNecesidadFuturaPage.fillFormDatosProducto(camposanto_producto, tipo_contrato, modalidad, producto);
    }

    @And("en la pagina Cotizador de Camposanto los Datos del Cliente se ingresa el tipo de documento {string}, numero de documento {string}, nombre {string}, apellido paterno {string}, apellido materno {string}, fecha nacimiento {string}, estado civil {string}, telefono de casa {string}, telefono movil {string}, correo electronico {string}, departmento {string}, provincia {string}, distrito {string} y direccion {string}")
    public void enLaPaginaCotizadorDeCamposantoLosDatosDelClienteSeIngresaElTipoDeDocumentoNumeroDeDocumentoNombreApellidoPaternoApellidoMaternoFechaNacimientoEstadoCivilTelefonoDeCasaTelefonoMovilCorreoElectronicoDepartmentoProvinciaDistritoYDireccion(String tipo_doumento, String numero_documento, String nombre, String ap_paterno, String ap_materno, String fecha_nacimiento, String estado_civil, String telefono_casa, String telefono_movil, String correo_electronico, String departamento, String provincia, String distrito, String direccion) {
        cotizacionCampoSantoNecesidadFuturaPage.fillFormDatosCliente(tipo_doumento, numero_documento, nombre, ap_paterno, ap_materno, fecha_nacimiento, estado_civil, telefono_casa, telefono_movil, correo_electronico, departamento, provincia, distrito, direccion);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos del Cliente");
        cotizacionCampoSantoNecesidadFuturaPage.processAndAssertCotizacionSuccess(30_000, 500);
        ExtentEvidence.shot("Evidencia despues de guardar la cotizacion");
        cotizacionCampoSantoNecesidadFuturaPage.processAndAssertCotizacionPaso2Success(30_000, 500);
        ExtentEvidence.shot("Evidencia despues de generar la cotizacion");
        cotizacionCampoSantoNecesidadFuturaPage.processAndAssertCotizacionPaso3Success(30_000, 500);
    }

    @Then("el sistema muestra la pagina Cotizador de Camposanto con el resumen de la cotizacion")
    public void elSistemaMuestraLaPaginaCotizadorDeCamposantoConElResumenDeLaCotizacion() {
        resumenCotizacionNecesidadFuturaPage.assertLoaded();
    }

    @And("en la pagina Cotizador de Camposanto se envia el resumen de la cotización al cliente {string}")
    public void enLaPaginaCotizadorDeCamposantoSeEnviaElResumenDeLaCotizaciónAlCliente(String correo_electronico) {
        resumenCotizacionNecesidadFuturaPage.fillFormEnviarCotizacionPorCorreo(correo_electronico);
        ExtentEvidence.shot("Evidencia despues de enviar la cotizacion por correo");
        resumenCotizacionNecesidadFuturaPage.processAndAssertProcesarEnviarCorreoSuccess(30_000,500);
    }

    @And("en la pagina Cotizador de Camposanto se realiza la descarga del PDF de la cotización")
    public void enLaPaginaCotizadorDeCamposantoSeRealizaLaDescargaDelPDFDeLaCotización() {
        resumenCotizacionNecesidadFuturaPage.downloadPdfAndAssert();
    }
}
