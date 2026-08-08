
package com.mapfre.test.stepdefinitions.poliza.vidaley;


import com.mapfre.playwright.pageobjects.poliza.vidaley.*;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class CotizacionPolizaVidaLeySteps {


    private final CotizarVidaLeyPage cotizarVidaLeyPage;
    private final ActividadModalPage actividadModalPage;
    private final DuracionDeclaracionSeguroPage duracionDeclaracionSeguroPage;
    private final DatosCoberturaYAseguradosPage datosCoberturaYAseguradosPage;
    private final ResultadosCotizacionVidaLeyPage resultadosCotizacionVidaLeyPage;

    // Constructor
    public CotizacionPolizaVidaLeySteps(PageProvider pageProvider) {

        this.cotizarVidaLeyPage = new CotizarVidaLeyPage(pageProvider.get());
        this.actividadModalPage = new ActividadModalPage(pageProvider.get());
        this.duracionDeclaracionSeguroPage = new DuracionDeclaracionSeguroPage(pageProvider.get());
        this.datosCoberturaYAseguradosPage = new DatosCoberturaYAseguradosPage(pageProvider.get());
        this.resultadosCotizacionVidaLeyPage = new ResultadosCotizacionVidaLeyPage(pageProvider.get());
    }

    @And("en la pagina Vida Ley selecciona la opcion Cotizar vida ley")
    public void seleccionarOpcionCotizarVidaLey() {
        cotizarVidaLeyPage.assertLoaded();
        cotizarVidaLeyPage.clickCotizarVidaLey();
    }
    @And("en la pagina Cotizacion poliza de vida ley completamos los datos del contratante y seleccionamos tipo documento {string}, numero documento {string}, nombre completo {string}, telefono {string}, correo electronico {string}")
    public void enLaPaginaCotizacionPolizaDeVidaLeyCompletamosLosDatosDelContratanteYSeleccionamosTipoDocumentoNumeroDocumentoNombreCompletoTelefonoCorreoElectronico(String tipoDocumento,String numeroDocumento,String nombreCompleto,String telefono,String correoElectronico) {
        cotizarVidaLeyPage.assertEnCotizacionVidaLey();
        cotizarVidaLeyPage.completarDatosDelContratante(tipoDocumento,numeroDocumento,nombreCompleto,telefono,correoElectronico);
    }
    @And("en la pagina Cotizacion poliza de vida ley buscamos y seleccionamos una actividad {string}")
    public void enLaPaginaCotizacionPolizaDeVidaLeyBuscamosYSeleccionamosUnaActividad(String actividad) {

        // 1️. Desde la página principal: abrir el modal
        cotizarVidaLeyPage.clickBuscarActividad();
        // 3. Buscar y seleccionar la actividad (ej: 3220 desde el Example)
        actividadModalPage.buscarYSeleccionarActividad(actividad);
        // 4.
        cotizarVidaLeyPage.clickSiguienteYConfirmarClausulas();
        cotizarVidaLeyPage.clickBotonGuardarDatos();
        ExtentEvidence.shot("Mensaje luego de hacer clic en guardar datos");
    }

    @And("en la pagina Cotizacion poliza de vida ley sección Duración y declaracion del seguro ingresamos frecuencia de declaracion {string}, fecha inicial {string}, duracion de cobertura {string}. centro de riesgo {string}")
    public void enLaPaginaCotizacionPolizaDeVidaLeySecciónDuraciónYDeclaracionDelSeguroIngresamosFrecuenciaDeDeclaracionFechaInicialDuracionDeCoberturaCentroDeRiesgo(String frecuencia, String fechaInicial, String duracionCobertura, String centroRiesgo) {

        duracionDeclaracionSeguroPage.completarDuracionYDeclaracionSeguro(
                frecuencia,
                duracionCobertura,
                centroRiesgo
        );
    }

    @And("en la pagina Cotizacion poliza de vida ley seccion datos del la cobertura y asegurados, se ingresa el registro manual del asegurado.")
    public void enLaPaginaCotizacionPolizaDeVidaLeySeccionDatosDelLaCoberturaYAseguradosSeIngresaElRegistroManualDelAsegurado() {

        //Validar que estamos en la sección correcta
        datosCoberturaYAseguradosPage.assertEnPaginaDatosCoberturaYAsegurados();

        //Seleccionar el tab correcto
        datosCoberturaYAseguradosPage.seleccionarRegistroManual();

        //Completar TODOS los datos obligatorios del asegurado
        datosCoberturaYAseguradosPage.completarRegistroManualObligatorio();

        //Obtener coberturas (incluye validación RENIEC si aparece)
        datosCoberturaYAseguradosPage.obtenerCoberturasYValidarRiesgos();

        // Confirmar y continuar al siguiente paso
        datosCoberturaYAseguradosPage.validarRiesgosYClickSiguiente();

    }

    @Then("en la pagina Cotizacion poliza de vida ley se acepta la cotizacion y se genera el numero de cotización")
    public void enLaPaginaCotizacionPolizaDeVidaLeySeAceptaLaCotizacionYSeGeneraElNumeroDeCotización() {

        // Aceptar la cotización
        resultadosCotizacionVidaLeyPage.aceptarCotizacion();
        // Extraer y mostrar el número de cotización (FIN DEL FLUJO)
        resultadosCotizacionVidaLeyPage.extraerYLoggearNumeroCotizacion();
    }
}

