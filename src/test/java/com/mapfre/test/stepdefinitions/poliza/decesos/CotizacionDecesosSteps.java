package com.mapfre.test.stepdefinitions.poliza.decesos;

import com.mapfre.playwright.pageobjects.poliza.decesos.MenuPrincipalDecesosPage;
import com.mapfre.playwright.pageobjects.poliza.decesos.cotizacion.CotizacionDecesosPage;
import com.mapfre.playwright.pageobjects.poliza.decesos.cotizacion.ResumenCotizacionDecesosPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class CotizacionDecesosSteps {

    private final MenuPrincipalDecesosPage menuPrincipalDecesosPage;
    private final CotizacionDecesosPage cotizacionDecesosPage;
    private final ResumenCotizacionDecesosPage resumenCotizacionDecesosPage;

    public CotizacionDecesosSteps(PageProvider pageProvider) {
        this.menuPrincipalDecesosPage = new MenuPrincipalDecesosPage(pageProvider.get());
        this.cotizacionDecesosPage = new CotizacionDecesosPage(pageProvider.get());
        this.resumenCotizacionDecesosPage = new ResumenCotizacionDecesosPage(pageProvider.get());
    }

    @And("en la pagina Decesos selecciona la opcion Cotizar Poliza Decesos")
    public void enLaPaginaDecesosSeleccionaLaOpcionCotizarPolizaDecesos() {
        menuPrincipalDecesosPage.clickCotizarDecesosButton();
        ExtentEvidence.shot("Evidencia despues de ingresar al cotizador de decesos");
    }

    @And("en la pagina Cotizador de Decesos en la seccion Datos de la poliza se ingresa el producto {string}, poliza grupo {string},modalidad {string}, medio de pago {string}")
    public void enLaPaginaCotizadorDeDecesosEnLaSeccionDatosDeLaPolizaSeIngresaElProductoPolizaGrupoModalidadMedioDePago(String producto, String polizagrupo, String modalidad, String mediopago) {
            cotizacionDecesosPage.fillFormDatosPoliza(producto, polizagrupo, modalidad, mediopago);
            ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos de la Poliza");
    }

    @And("en la pagina Cotizador de Decesos en la seccion Datos del contratante se ingresa el tipo de documento {string}, numero de documento {string},estado civil {string},profesion {string}, telefono de casa {string}, telefono movil {string}, correo electronico {string}, departmento {string}, provincia {string}, distrito {string} ,tipo_via {string}, nombre_via {string}, tipo_numero {string}, enumeracion {string}")
    public void enLaPaginaCotizadorDeDecesosEnLaSeccionDatosDelContratanteSeIngresaElTipoDeDocumentoNumeroDeDocumentoEstadoCivilProfesionTelefonoDeCasaTelefonoMovilCorreoElectronicoDepartmentoProvinciaDistritoTipo_viaNombre_viaTipo_numeroEnumeracion(String tipoDocumento, String numeroDocumento, String estadoCivil, String profesion, String telefonoCasa, String telefonoMovil, String correo, String departamento, String provincia, String distrito, String tipoVia, String nombreVia, String numero, String enumeracion) {
        String numeroDocumentoPrimerAsegurado = RandomData.idDocumentPeru(9, 8);
        cotizacionDecesosPage.fillFormDatosContratante(tipoDocumento, numeroDocumentoPrimerAsegurado, estadoCivil, profesion, telefonoCasa, telefonoMovil, correo, departamento, provincia, distrito, tipoVia, nombreVia, numero, enumeracion);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos del Contratante");
    }

    @And("en la pagina Cotizador de Decesos en la seccion Asegurados se ingresa el asegurado que es el mismo que el contratante")
    public void enLaPaginaCotizadorDeDecesosEnLaSeccionAseguradosSeIngresaElAseguradoQueEsElMismoQueElContratante() {
        cotizacionDecesosPage.clickAgregarAsegurado();
        cotizacionDecesosPage.processAndAssertSucessAgregandoAseguradoSeccion(40_000,500);
        ExtentEvidence.shot("Evidencia despues de abrir el formulario para agregar al primer asegurado al cotizador de decesos");
        cotizacionDecesosPage.clickAseguradoEsContratante();
        ExtentEvidence.shot("Evidencia despues de seleccionar el asegurado es el mismo que el contratante");
        cotizacionDecesosPage.clickAgregarPrimerAsegurado();
        cotizacionDecesosPage.processAndAssertSucessAseguradoRegistradoSeccion(20_000,500);
        cotizacionDecesosPage.validarAseguradoRegistrado(1);
    }

    @And("en la pagina cotizador de Decesos en la seccion Asegurados se ingresa el segundo asegurado con tipo de documento {string}, numero de documento {string},")
    public void enLaPaginaCotizadorDeDecesosEnLaSeccionAseguradosSeIngresaElSegundoAseguradoConTipoDeDocumentoNumeroDeDocumento(String tipoDocumento2, String numeroDocumento2) {
        cotizacionDecesosPage.clickAgregarOtroAsegurado();
        cotizacionDecesosPage.processAndAssertSucessAgregandoAseguradoSeccion(40_000,500);
        ExtentEvidence.shot("Evidencia despues de abrir el formulario para agregar al segundo asegurado al cotizador de decesos");
        cotizacionDecesosPage.fillFormSegundoAsegurado(tipoDocumento2, numeroDocumento2);
        cotizacionDecesosPage.clickSegundoAseguradoAgregarButton();
        cotizacionDecesosPage.processAndAssertSucessAseguradoRegistradoSeccion(20_000,500);
        cotizacionDecesosPage.validarAseguradoRegistrado(2);
    }

    @And("en la pagina Cotizador de Decesos  selecciona el boton de cotizar")
    public void enLaPaginaCotizadorDeDecesosSeleccionaElBotonDeCotizar() {
        cotizacionDecesosPage.clickcotizarButton();
    }

    @Then("el sistema muestra la pagina Resumen Cotizacion Decesos con el resumen de la cotizacion")
    public void elSistemaMuestraLaPaginaResumenCotizacionDecesosConElResumenDeLaCotizacion() {
        resumenCotizacionDecesosPage.processAndAssertSuccess(120_000,500);
        resumenCotizacionDecesosPage.assertLoaded();
    }
}
