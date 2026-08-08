package com.mapfre.test.stepdefinitions.poliza.soat;

import com.mapfre.playwright.pageobjects.poliza.soat.EmisionPolizaSoatPage;
import com.mapfre.playwright.pageobjects.poliza.soat.PolizaSoatPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class EmisionPolizaSoatSteps {

    private final PolizaSoatPage polizaSoatPage;
    private final EmisionPolizaSoatPage emisionPolizaSoatPage;

    public EmisionPolizaSoatSteps(PageProvider pageProvider) {
        this.polizaSoatPage = new PolizaSoatPage(pageProvider.get());
        this.emisionPolizaSoatPage = new EmisionPolizaSoatPage(pageProvider.get());
    }

    @And("en la pagina SOAT selecciona la opcion Emitir póliza soat")
    public void enLaPaginaSOATSeleccionaLaOpcion() {
        polizaSoatPage.assertLoaded();
        polizaSoatPage.clickEmitirPolizaSOAT();
        //ExtentEvidence.shot("ingreso correctamente");
    }
    @And("en la pagina Emision poliza SOAT se ingresa los datos numero de placa {string}, tipo de vehiculo {string}, marca y modelo {string}, numero de chasis {string}, anio de fabricacion {string}, producto {string}, tipo de uso {string}, Nro asientos {string}")
    public void enLaPaginaEmisionPolizaSOATSeIngresaLosDatosVehiculo(String placa,String tipoVehiculo, String marcaModelo,String chasis, String anioFabricacion,String producto, String tipoUso, String nroAsientos) {
        emisionPolizaSoatPage.assertLoaded();
        emisionPolizaSoatPage.ingresarDatosVehiculo(placa,tipoVehiculo,marcaModelo,chasis,anioFabricacion, producto,tipoUso,nroAsientos);
        emisionPolizaSoatPage.clickSiguiente();
        ExtentEvidence.shot("Evidencia ");
    }

    @And("en la pagina Emision poliza SOAT se ingresa los datos del contratante tipo de documento {string}, Nro documento {string}")
    public void enLaPaginaEmisionPolizaSOATSeIngresaLosDatosDelContratante(String tipoDocumento,String nroDocumento) {
        emisionPolizaSoatPage.assertLoadedDatosContratante();
        emisionPolizaSoatPage.ingresarDatosContratante();
    }

    @And("en la pagina Emision poliza SOAT se ingresa los datos principales")
    public void enLaPaginaEmisionPolizaSOATSeIngresaLosDatosPrincipales() {
        emisionPolizaSoatPage.ingresarDatosPrincipales();

    }

    @And("en la pagina Emision poliza SOAT se ingresa los datos de contacto")
    public void enLaPaginaEmisionPolizaSOATSeIngresaLosDatosDeContacto() {
        emisionPolizaSoatPage.ingresarDatosContacto();
    }

    @And("en la pagina Emision poliza SOAT se ingresa los datos de direccion")
    public void enLaPaginaEmisionPolizaSOATSeIngresaLosDatosDeDireccion() {
        emisionPolizaSoatPage.ingresarDatosDireccion();
    }

    @And("en la pagina Emision poliza SOAT seleccionar la opcion CALCULAR PRIMA")
    public void enLaPaginaEmisionPolizaSOATSeleccionarLaOpcionCALCULARPRIMA() {
        emisionPolizaSoatPage.clickCalcularprima();
        ExtentEvidence.shot("prima calculada");

    }

    @And("en la pagina Emision poliza SOAT se EMITE SOAT")
    public void enLaPaginaEmisionPolizaSOATSeEMITESOAT() {
        emisionPolizaSoatPage.validarPrimaCalculada();
    }

    @Then("en la pagina Emision poliza SOAT se muestra los datos de la EMISION DEL SOAT")
    public void enLaPaginaEmisionPolizaSOATSeMuestraLosDatosDeLaEMISIONDELSOAT() {
        emisionPolizaSoatPage.emitirPolizaYValidarResultado();
        //ExtentEvidence.shot("Emisión SOAT");
    }

}
