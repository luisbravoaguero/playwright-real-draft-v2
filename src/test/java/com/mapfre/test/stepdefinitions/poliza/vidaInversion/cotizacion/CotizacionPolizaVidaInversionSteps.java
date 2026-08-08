package com.mapfre.test.stepdefinitions.poliza.vidaInversion.cotizacion;

import com.mapfre.playwright.pageobjects.poliza.vidaInversion.cotizacion.CotizacionPolizaVidaInversionPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CotizacionPolizaVidaInversionSteps {
    private final CotizacionPolizaVidaInversionPage cotizacionPolizaVidaInversionPage;

    public CotizacionPolizaVidaInversionSteps(PageProvider pageProvider) {
        this.cotizacionPolizaVidaInversionPage = new CotizacionPolizaVidaInversionPage(pageProvider.get());
    }
    @And("en la pagina Cotizacion Poliza Vida Inversion, en el paso Datos de la poliza, seccion Datos del Contratante ingresamos el tipo de documento {string}, numero de documento {string}, nombre {string}, apellido paterno {string}, apellido materno, {string}, telefono movil {string} y correo electronico {string}")
    public void enLaPaginaCotizacionPolizaVidaInversionEnElPasoDatosDeLaPolizaSeccionDatosDelContratanteIngresamosElTipoDeDocumentoNumeroDeDocumentoNombreApellidoPaternoApellidoMaternoTelefonoMovilYCorreoElectronico(String tipo_documento, String numero_documento, String nombre, String apellido_paterno, String apellido_materno, String telefono_movil, String correo_electronico) {
        cotizacionPolizaVidaInversionPage.assertLoaded();
        cotizacionPolizaVidaInversionPage.fillFormularioPasoDatosPolizaSeccionDatosContratante(tipo_documento, numero_documento, nombre, apellido_paterno, apellido_materno, telefono_movil, correo_electronico);
    }

    @And("en la pagina Cotizacion Poliza Vida Inversion, en el paso Datos de la poliza, seccion Datos del Asegurado activamos la opcion Los datos del asegurado son los mismos que los del contratante")
    public void enLaPaginaCotizacionPolizaVidaInversionEnElPasoDatosDeLaPolizaSeccionDatosDelAseguradoActivamosLaOpcionLosDatosDelAseguradoSonLosMismosQueLosDelContratante() {
        cotizacionPolizaVidaInversionPage.activarOpcionDatosAseguradoIgualesContratante();
        cotizacionPolizaVidaInversionPage.validarDatosAseguradoIgualesContratante();
    }

    @And("en la pagina Cotizacion Poliza Vida Inversion, en el paso Datos de la poliza, seccion Datos del Asesor ingresamos el GestorSupervisor {string} y agente {string}")
    public void enLaPaginaCotizacionPolizaVidaInversionEnElPasoDatosDeLaPolizaSeccionDatosDelAsesorIngresamosElGestorSupervisorYAgente(String gestor_supervisor, String agente) {
        cotizacionPolizaVidaInversionPage.fillFormularioDatosDelAsesor(gestor_supervisor, agente);
    }

    @And("en la pagina Cotizacion Poliza Vida Inversion, en el paso Datos de la poliza, seccion Caracteristicas del Seguro ingresamos el tipo del producto {string}, tipo de moneda {string}, anios de duracion del seguro {string} porcentaje de devolucion {string}, prima comercial unica {string}, diferimiento de pago {string}, periocidad de pago renta {string} y codigo de promocion {string}")
    public void enLaPaginaCotizacionPolizaVidaInversionEnElPasoDatosDeLaPolizaSeccionCaracteristicasDelSeguroIngresamosElTipoDelProductoTipoDeMonedaAniosDeDuracionDelSeguroPorcentajeDeDevolucionPrimaComercialUnicaDiferimientoDePagoPeriocidadDePagoRentaYCodigoDePromocion(String tipo_producto, String tipo_moneda, String anios_duracion, String porcentaje_devolucion, String prima_comercial_unica, String diferimiento_pago, String periocidad_pago_renta, String codigo_promocion) {
        cotizacionPolizaVidaInversionPage.fillFormularioPasoDatosPolizaSeccionCaracteristicasDelSeguro(tipo_producto, tipo_moneda, anios_duracion, porcentaje_devolucion, prima_comercial_unica, diferimiento_pago, periocidad_pago_renta, codigo_promocion);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Caracteristicas Del Seguro");
        cotizacionPolizaVidaInversionPage.processAndAssertSucessRegistrarNuevaCarateristicaDelSeguro(60_000,500);
        ExtentEvidence.shot("Evidencia despues de procesar el registro de la nueva caracteristica del seguro");
        cotizacionPolizaVidaInversionPage.activarCheckBoxSeleccionarTodo();
    }

    @Then("en la pagina Cotizacion Poliza Vida Inversion, en el paso Resultado cotizacion, se muestra el numero de cotizacion")
    public void enLaPaginaCotizacionPolizaVidaInversionEnElPasoResultadoCotizacionSeMuestraElNumeroDeCotizacion() {
        cotizacionPolizaVidaInversionPage.processAndAssertSucessCotizacion(60_000,500);
    }

    @When("se inicia la emision desde el Resultado de la Cotizacion")
    public void seIniciaLaEmisionDesdeElResultadoDeLaCotizacion() {
        cotizacionPolizaVidaInversionPage.activarEmisionCheckBox();
        ExtentEvidence.shot("Evidencia despues de activar el checkbox de emision");
        cotizacionPolizaVidaInversionPage.processAndAssertSucessInicioEmision(60_000,500);
    }

    @And("en la pagina Cotizacion Poliza Vida Inversion, en el paso Datos de la poliza, seccion Caracteristicas del Seguro ingresamos el tipo del producto {string}, tipo de moneda {string}, anios de duracion del seguro {string} modalidad {string}, prima comercial unica {string} y codigo de promocion {string}")
    public void enLaPaginaCotizacionPolizaVidaInversionEnElPasoDatosDeLaPolizaSeccionCaracteristicasDelSeguroIngresamosElTipoDelProductoTipoDeMonedaAniosDeDuracionDelSeguroModalidadPrimaComercialUnicaYCodigoDePromocion(String tipo_producto, String tipo_moneda, String anios_duracion_seguro, String modalidad, String prima_comercial_unica, String codigo_promocion) {
        cotizacionPolizaVidaInversionPage.fillFormularioPasoDatosPolizaSeccionCaracteristicasDelSeguroCertivida(tipo_producto, tipo_moneda, anios_duracion_seguro, modalidad, prima_comercial_unica, codigo_promocion);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Caracteristicas Del Seguro");
        cotizacionPolizaVidaInversionPage.processAndAssertSucessRegistrarNuevaCarateristicaDelSeguro(60_000,500);
        ExtentEvidence.shot("Evidencia despues de procesar el registro de la nueva caracteristica del seguro");
        cotizacionPolizaVidaInversionPage.activarCheckBoxSeleccionarTodo();
    }

}
