 package com.mapfre.test.stepdefinitions.poliza.riegosgenerales.cotizacion;


import com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion.CotizacionRiesgosGeneralesRcDeshPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

 public class CotizacionRiesgosGeneralesDeshTrabajoEspecificoDatosPolizaSteps {
    private final CotizacionRiesgosGeneralesRcDeshPage cotizacionRiesgosGeneralesRcDeshPage;

    public CotizacionRiesgosGeneralesDeshTrabajoEspecificoDatosPolizaSteps(PageProvider pageProvider) {
        this.cotizacionRiesgosGeneralesRcDeshPage = new CotizacionRiesgosGeneralesRcDeshPage(pageProvider.get());
    }

    @And("en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Producto ingresamos el producto {string}")
    public void ingresarProductoNumeroDocumentoCorredor(String producto) {
        cotizacionRiesgosGeneralesRcDeshPage.seleccionarProducto(producto);



    }

    @And("en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos del contratante ingresamos el ruc {string}, numero de documento {string}, corredor {string}")
    public void ingresarDatosContratante(String tipoDocumento, String numeroDocumento, String correrdor ) {
        cotizacionRiesgosGeneralesRcDeshPage.ingresarDatosContratante(tipoDocumento,numeroDocumento,correrdor);

    }

    @And("en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos del contrato ingresamos trabajo u obra a realizar {string}, departamento {string}, Provincia {string}, distrito {string}, direccion del riesgo {string}, ramo {string}, moneda {string}, numero de trabajadores {string}, valor del contrato {string}, suma asegurada {string}, canal {string}")
    public void ingresarDatosContrato(String trabajoObra, String departamento, String provincia, String distrito, String direccionRiesgo, String ramo, String moneda,String nroTrabajadores, String valorContrato, String sumaAsegurada, String canal)
    {
        cotizacionRiesgosGeneralesRcDeshPage.ingresarDatosContrato(trabajoObra,departamento,provincia, distrito,direccionRiesgo, ramo, moneda, nroTrabajadores, valorContrato, sumaAsegurada, canal);
        cotizacionRiesgosGeneralesRcDeshPage.clicksiguiente();
    }

    @Then("en la pagina Cotizador de Riesgos Generales en el paso Resultado de la cotizacion se muestra el resumen de la cotizacion del producto")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnElPasoResultadoDeLaCotizacionSeMuestraElResumenDeLaCotizacionDelProducto() {
        cotizacionRiesgosGeneralesRcDeshPage.validarYObtenerNumeroCotizacion();
    }

     @And("en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos del contrato ingresamos trabajo u obra a realizar {string}, departamento {string}, provincia {string}, distrito {string}, direccion del riesgo {string}, ramo {string}, ramo deshonestidad {string}, moneda {string}, numero de trabajadores {string}, valor del contrato {string}, suma asegurada {string}, canal {string}")
     public void ingresarDatosContrato(String trabajoObra, String departamento, String provincia, String distrito, String direccionRiesgo, String ramo, String ramoDeshonestidad, String moneda, String nroTrabajadores, String valorContrato, String sumaAsegurada, String canal)
     {

         cotizacionRiesgosGeneralesRcDeshPage.ingresarDatosContratoDeshonestidad(trabajoObra,departamento,provincia,
                 distrito, direccionRiesgo,ramo, ramoDeshonestidad, moneda, nroTrabajadores, valorContrato, sumaAsegurada, canal);
         cotizacionRiesgosGeneralesRcDeshPage.clicksiguiente();

     }
 }