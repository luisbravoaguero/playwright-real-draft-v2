package com.mapfre.test.stepdefinitions.poliza.accidentes;

import com.mapfre.playwright.pageobjects.poliza.accidentes.*;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class CotizacionyEmisionPolizaAccidentesSteps {
    private final MenuPrincipalAccidentesPage menuPrincipalAccidentesPage;
    private final CotizacionPolizaAccidentesPage cotizacionPolizaAccidentesPage;
    private final CotizacionGuardadaAccidentesPage cotizacionGuardadaAccidentesPage;
    private final EmisionPolizaAccidentesPage emisionPolizaAccidentesPage;
    private final EmisionPolizaAccidentesResultadoPage emisionPolizaAccidentesResultadoPage;


    public CotizacionyEmisionPolizaAccidentesSteps(PageProvider pageProvider){
        this.menuPrincipalAccidentesPage = new MenuPrincipalAccidentesPage(pageProvider.get());
        this.cotizacionPolizaAccidentesPage = new CotizacionPolizaAccidentesPage(pageProvider.get());
        this.cotizacionGuardadaAccidentesPage = new CotizacionGuardadaAccidentesPage(pageProvider.get());
        this.emisionPolizaAccidentesPage = new EmisionPolizaAccidentesPage(pageProvider.get());
        this.emisionPolizaAccidentesResultadoPage = new EmisionPolizaAccidentesResultadoPage(pageProvider.get());
    }

    @And("en la pagina Accidentes selecciona la opcion COTIZAR POLIZA ACCIDENTES")
    public void enLaPaginaAccidentesSeleccionaLaOpcionCOTIZARPOLIZAACCIDENTES() {
        menuPrincipalAccidentesPage.assertLoaded();
        menuPrincipalAccidentesPage.clickCotizarPolizaAccidentes();
    }

    @And("en la pagina Cotizacion poliza Accidentes completamos los datos obligatorios del contratante con tipo documento {string}, numero documento {string} nombres {string}, apellido paterno {string} y apellido materno {string}")
    public void enLaPaginaCotizacionPolizaAccidentesCompletamosLosDatosObligatoriosDelContratanteConTipoDocumentoNumeroDocumentoNombresApellidoPaternoYApellidoMaterno(String tipoDocumento, String numerodocumento, String nombres, String apellidoPaterno, String apellidoMaterno) {
        //1. validamos la pagina inicial
        cotizacionPolizaAccidentesPage.assertLoaded();
        //2. completamos los campos de la seccion datos del contratante
        cotizacionPolizaAccidentesPage.completarDatosObligatoriosContratante(tipoDocumento,numerodocumento,nombres,apellidoPaterno,apellidoMaterno);
        //3. completamos los campos de la seccion datos del Riesgo (Hardcode)
        cotizacionPolizaAccidentesPage.completarDatosRiesgoHardcodeado();
        //4. completamos los campos de la seccion coberturas principales (hardcode)
        cotizacionPolizaAccidentesPage.completarCoberturasPrincipalesHardcodeado();
        //5. clic boton agregar riesgo
        cotizacionPolizaAccidentesPage.clickAgregarRiesgo();
        //6. clic boton guardar cotizacion
        cotizacionPolizaAccidentesPage.guardarCotizacion();
        cotizacionPolizaAccidentesPage.processAndAssertSuccessClickGuardarCotizacion(90_000,500);


    }

    @And("en la pagina Cotizacion guardada de Accidentes se selecciona la opcion Emitir Poliza")
    public void enLaPaginaCotizacionGuardadaDeAccidentesSeSeleccionaLaOpcionEmitirPoliza() {
        cotizacionGuardadaAccidentesPage.assertLoaded();
        cotizacionGuardadaAccidentesPage.clickEmitirPoliza();
    }

    @And("en la pagina Emision Poliza Accidentes completamos los campos obligatorios tipo documento {string}, numero documento {string}, fecha nacimiento {string}")
    public void enLaPaginaEmisionPolizaAccidentesCompletamosLosCamposObligatoriosTipoDocumentoNumeroDocumentoFechaNacimiento(String tipoDocumento, String numeroDocumento, String fechaNacimiento) {
        emisionPolizaAccidentesPage.assertLoaded();
        emisionPolizaAccidentesPage.completarCamposObligatoriosMinimos(tipoDocumento,numeroDocumento,fechaNacimiento);
        emisionPolizaAccidentesPage.clickSiguiente();
    }


    @And("en la pagina Emision Poliza Accidentes cargamos el archivo excel de asegurados y se emite la poliza accidentes")
    public void enLaPaginaEmisionPolizaAccidentesCargamosElArchivoExcelDeAseguradosYSeEmiteLaPolizaAccidentes() {

        String archivoExcel = "Trama_Emision_Accidentes.xlsx";
        emisionPolizaAccidentesPage.cargarPlanillaYEmitir(archivoExcel);

    }

    @Then("en la pagina Poliza Emitida se muestra el resultado de la emisión de la poliza accidente")
    public void enLaPaginaPolizaEmitidaSeMuestraElResultadoDeLaEmisiónDeLaPolizaAccidente() {

        emisionPolizaAccidentesResultadoPage.mostrarResumenEmision();
        emisionPolizaAccidentesResultadoPage.validarPolizaEmitida();

    }
}


