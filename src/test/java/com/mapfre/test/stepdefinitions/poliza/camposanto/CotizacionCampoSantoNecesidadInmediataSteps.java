package com.mapfre.test.stepdefinitions.poliza.camposanto;

import com.mapfre.playwright.pageobjects.poliza.camposanto.cotizacion.*;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.playwright.pageobjects.poliza.camposanto.cotizacion.CotizarContratodeSepelioPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;


public class CotizacionCampoSantoNecesidadInmediataSteps {
    private final CotizarContratodeSepelioPage cotizarContratodeSepelioPage;
    private final CotizacionCampoSantoNecesidadInmediataPage cotizacionCampoSantoNecesidadInmediataPage;
    private final BandejaCamposantoPage bandejaCamposantoPage;
    private final CotizacionCampoSantoCheckDocumentosPage cotizacionCampoSantoCheckDocumentosPage;
    private String nroCotizacion;

    public CotizacionCampoSantoNecesidadInmediataSteps (PageProvider pageProvider){
        this.cotizarContratodeSepelioPage = new CotizarContratodeSepelioPage (pageProvider.get());
        this.cotizacionCampoSantoNecesidadInmediataPage = new CotizacionCampoSantoNecesidadInmediataPage(pageProvider.get());
        this.cotizacionCampoSantoCheckDocumentosPage = new CotizacionCampoSantoCheckDocumentosPage(pageProvider.get());
        this.bandejaCamposantoPage = new BandejaCamposantoPage(pageProvider.get());
    }

    @And("en la pagina Cotizador de Camposanto selecciona el ramo necesidad inmediata")
    public void seleccionarRamoNecesidadInmediata() {
        cotizarContratodeSepelioPage.assertLoadedCotizadorCampoSanto();
        cotizarContratodeSepelioPage.clickNecesidadInmediata();

    }

    @And("en la pagina Cotizador de Camposanto ramo necesidad inmediata ingresamos los datos del producto camposanto {string} tipo de contrato {string} modalidad {string} producto {string}")
    public void enLaPaginaCotizadorDeCamposantoRamoNecesidadInmediataIngresamosLosDatosDelProductoCamposantoTipoDeContratoModalidadProducto(String camposanto,String tipoContrato,String modalidad,String producto) {

        cotizacionCampoSantoNecesidadInmediataPage.assertLoadedNecesidadInmedita();
        cotizacionCampoSantoNecesidadInmediataPage.ingresarDatosProducto(camposanto,tipoContrato,modalidad, producto);

    }


    @And("en la pagina Cotizador de Camposanto ramo necesidad inmediata ingresamos los Datos del Cliente se ingresa el tipo de documento {string}, numero de documento {string}, nombre {string}, apellido paterno {string}, apellido materno {string}, fecha nacimiento {string},{string},{string}, estado civil {string}, telefono de casa {string}, telefono movil {string}, correo electronico {string}, departmento {string}, provincia {string}, distrito {string} y direccion {string}")
    public void ingresarDatosCliente(
            String tipoDoc, String numeroDoc,String nombre,String paterno,String materno,String dia,
            String mes,String anio,String estadoCivil,String telCasa,String telMovil, String correo, String departamento,String provincia, String distrito, String direccion
    ) {
        cotizacionCampoSantoNecesidadInmediataPage.ingresarDatosCliente(tipoDoc, numeroDoc,nombre,paterno,materno, dia,  mes, anio,
                estadoCivil, telMovil,correo,  departamento, provincia, distrito, direccion );

        cotizacionCampoSantoNecesidadInmediataPage.clickGenerarCotizacion();
        ExtentEvidence.shot("Evidencia");
        cotizacionCampoSantoNecesidadInmediataPage.aceptarModal();
        cotizacionCampoSantoNecesidadInmediataPage.aceptarModalCotizacionCreada();
        ExtentEvidence.shot("Evidencia");
    }

    @And("en la pagina Cotizador de Camposanto ramo necesidad inmediata el sistema muestra la pagina Cotizador de   Camposanto con el resumen de la cotizacion y se selecciona la opcion Ir a Bandeja")
    public void enLaPaginaCotizadorDeCamposantoRamoNecesidadInmediataElSistemaMuestraLaPaginaCotizadorDeCamposantoConElResumenDeLaCotizacionYSeSeleccionaLaOpcionIrABandeja() {
        cotizacionCampoSantoNecesidadInmediataPage.assertResultadoCotizacionLoaded();
        nroCotizacion = cotizacionCampoSantoNecesidadInmediataPage.obtenerNumeroCotizacionGenerado();
        cotizacionCampoSantoNecesidadInmediataPage.clickIrBandeja();
    }
    @And("en la pagina Bandeja de Camposanto seleccionamos la opcion emitir del nro de cotizacion creado")
    public void enLaPaginaBandejaDeCamposantoSeleccionamosLaOpcionEmitirDelNroDeCotizacionCreado() {

        bandejaCamposantoPage.assertLoaded();
        bandejaCamposantoPage.emitirCotizacionPorNumero(nroCotizacion);

    }
    @And("en la pagina Cotizador de Camposanto ramo necesidad inmediata se ingresan LOS CHECK DE DOCUMENTOS, DATOS DEL TOMADOR, DATOS BENEFICIARIOS, DATOS ADICIONALES")
    public void enLaPaginaCotizadorDeCamposantoRamoNecesidadInmediataSeIngresanLOSCHECKDEDOCUMENTOSDATOSDELTOMADORDATOSBENEFICIARIOSDATOSADICIONALES() {
        ExtentEvidence.shot("Evidencia pantalla");
        cotizacionCampoSantoCheckDocumentosPage.assertLoaded();
        cotizacionCampoSantoCheckDocumentosPage.ingresarDatosTomadorDireccion();
        cotizacionCampoSantoCheckDocumentosPage.ingresarDatosBeneficiario();
        cotizacionCampoSantoCheckDocumentosPage.ingresarDatosAdicionales();

    }

    @Then("en la pagina Cotizador de Camposanto ramo necesidad inmediata emitimos la poliza")
    public void enLaPaginaCotizadorDeCamposantoRamoNecesidadInmediataEmitimosLaPoliza() {
        ExtentEvidence.shot("Evidencia pantalla");
        cotizacionCampoSantoCheckDocumentosPage.emitirPoliza();


    }
}
