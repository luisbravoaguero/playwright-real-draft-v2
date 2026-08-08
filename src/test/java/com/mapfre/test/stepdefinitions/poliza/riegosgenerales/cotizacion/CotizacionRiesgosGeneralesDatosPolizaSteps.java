package com.mapfre.test.stepdefinitions.poliza.riegosgenerales.cotizacion;

import com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion.CotizacionRiesgosGeneralesDatosPolizaPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;

public class CotizacionRiesgosGeneralesDatosPolizaSteps {
    private final CotizacionRiesgosGeneralesDatosPolizaPage cotizacionRiesgosGeneralesDatosPolizaPage;

    public CotizacionRiesgosGeneralesDatosPolizaSteps(PageProvider pageProvider) {
        this.cotizacionRiesgosGeneralesDatosPolizaPage = new CotizacionRiesgosGeneralesDatosPolizaPage(pageProvider.get());
    }
    @And("en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Producto ingresamos el nombre del producto {string} y la cobertura {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnElPasoDatosDeLaPolizaSeccionProductoIngresamosElNombreDelProductoYLaCobertura(String nombre_producto, String nombre_cobertura) {
        cotizacionRiesgosGeneralesDatosPolizaPage.assertLoaded();
        cotizacionRiesgosGeneralesDatosPolizaPage.fillPasoDatosDeLaPolizaSeccionProducto(nombre_producto,nombre_cobertura);
    }

    @And("en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos de la Poliza ingresamos el numero de documento {string}, el corredor {string}, giro del negocio {string}, marca la opcion asegurar tercero igual a {string}, tipo de moneda {string}, tipo de canal {string}, numero de locales {string} y descuentos director {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnElPasoDatosDeLaPolizaSeccionDatosDeLaPolizaIngresamosElNumeroDeDocumentoElCorredorGiroDelNegocioMarcaLaOpcionAsegurarTerceroIgualATipoDeMonedaTipoDeCanalNumeroDeLocalesYDescuentosDirector(String numero_documento, String corredor, String giro_negocio, String asegurar_tercero, String tipo_moneda, String tipo_canal, String numero_locales, String descuentos_director) {
        cotizacionRiesgosGeneralesDatosPolizaPage.fillPasoDatosDeLaPolizaSeccionDatosDeLaPolizaPrimeraParte(numero_documento,corredor,giro_negocio,asegurar_tercero);
        ExtentEvidence.shot("Evidencia despues de llenar la primera parte del formulario Datos de la Poliza");
        cotizacionRiesgosGeneralesDatosPolizaPage.fillPasoDatosDeLaPolizaSeccionDatosDeLaPolizaSegundaParte(tipo_moneda,tipo_canal,numero_locales,descuentos_director);
        ExtentEvidence.shot("Evidencia despues de llenar la segunda parte del formulario Datos de la Poliza");
        cotizacionRiesgosGeneralesDatosPolizaPage.fillPasoDatosDeLaPolizaSeccionDatosDeLaPolizaTerceraParte();
    }

    @And("en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos del local ingresamos la ubicacion del riesgo {string}, departamento {string}, provincia {string} y distrito {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnElPasoDatosDeLaPolizaSeccionDatosDelLocalIngresamosLaUbicacionDelRiesgoDepartamentoProvinciaYDistrito(String ubicacion_riesgo, String departamento, String provincia, String distrito) {
        cotizacionRiesgosGeneralesDatosPolizaPage.fillPasoDatosDeLaPolizaSeccionDatosDelLocal(ubicacion_riesgo,departamento,provincia,distrito);
    }

    @And("en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Suma asegurada ingresamos la cantidad unitaria {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnElPasoDatosDeLaPolizaSeccionSumaAseguradaIngresamosLaCantidadUnitaria(String cantidad_unitaria) {
        cotizacionRiesgosGeneralesDatosPolizaPage.fillPasoDatosDeLaPolizaSeccionSumaAsegurada(cantidad_unitaria);
        cotizacionRiesgosGeneralesDatosPolizaPage.processAndAssertSuccessSelecionarBotonCalcularPrima(60_000,500);
    }

    @And("en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Producto ingresamos el nombre del producto {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnElPasoDatosDeLaPolizaSeccionProductoIngresamosElNombreDelProducto(String nombre_producto) {
        cotizacionRiesgosGeneralesDatosPolizaPage.fillPasoDatosDeLaPolizaSeccionProducto(nombre_producto);

    }

    @And("en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos de la Poliza ingresamos el numero de documento {string}, el corredor {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnElPasoDatosDeLaPolizaSeccionDatosDeLaPolizaIngresamosElNumeroDeDocumentoElCorredor(String numero_documento, String corredor) {
        cotizacionRiesgosGeneralesDatosPolizaPage.fillPasoDatosDeLaPolizaSeccionDatosDeLaPolizaPrimeraParte(numero_documento,corredor);

    }

    @And("en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos de la obra ingresamos el tipo de proyecto {string}, nombre de la obra {string}, departamento {string}, provincia {string}, distrito {string}, direccion del riesgo {string}, tipo de moneda {string}, tipo de canal {string}, monto de la mano de obra {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnElPasoDatosDeLaPolizaSeccionDatosDeLaObraIngresamosElTipoDeProyectoNombreDeLaObraDepartamentoProvinciaDistritoDireccionDelRiesgoTipoDeMonedaTipoDeCanalMontoDeLaManoDeObra(String tipo_proyecto, String nombre_obra, String departamento, String provincia, String distrito, String direccion_riesgo, String tipo_moneda, String tipo_canal, String monto_mano_obra) {
        cotizacionRiesgosGeneralesDatosPolizaPage.fillPasoDatosDeLaPolizaSeccionDatosDeLaObraPrimeraParte(tipo_proyecto,nombre_obra);
        ExtentEvidence.shot("Evidencia despues de llenar la primera parte del formulario Datos de la Obra");
        cotizacionRiesgosGeneralesDatosPolizaPage.fillPasoDatosDeLaPolizaSeccionDatosDeLaObraSegundaParte(departamento,provincia,distrito,direccion_riesgo);
        ExtentEvidence.shot("Evidencia despues de llenar la segunda parte del formulario Datos de la Obra");
        cotizacionRiesgosGeneralesDatosPolizaPage.fillPasoDatosDeLaPolizaSeccionDatosDeLaObraTerceraParte(tipo_moneda,tipo_canal,monto_mano_obra);
        ExtentEvidence.shot("Evidencia despues de llenar la tercera parte del formulario Datos de la Obra");
        cotizacionRiesgosGeneralesDatosPolizaPage.fillPasoDatosDeLaPolizaSeccionDatosDeLaPolizaTerceraParte();
    }
}
