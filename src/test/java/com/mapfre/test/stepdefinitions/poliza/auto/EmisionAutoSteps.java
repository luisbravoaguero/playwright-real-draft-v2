package com.mapfre.test.stepdefinitions.poliza.auto;

import com.mapfre.playwright.pageobjects.poliza.auto.PolizaAutoPage;
import com.mapfre.playwright.pageobjects.poliza.auto.emsion.CotizacionesAutoPage;
import com.mapfre.playwright.pageobjects.poliza.auto.emsion.EmisionPolizaAutoNuevoPage;
import com.mapfre.playwright.pageobjects.poliza.auto.emsion.PolizaEmitidaAutoPage;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.test.hooks.ScenarioKeys;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class EmisionAutoSteps {
    private final PolizaAutoPage polizaAutoPage;
    private final CotizacionesAutoPage cotizacionesAutoPage;
    private final EmisionPolizaAutoNuevoPage emisionPolizaAutoNuevoPage;
    private final PolizaEmitidaAutoPage polizaEmitidaAutoPage;
    private final ScenarioContext scenarioContext;

    public EmisionAutoSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.polizaAutoPage = new PolizaAutoPage(pageProvider.get());
        this.cotizacionesAutoPage = new CotizacionesAutoPage(pageProvider.get());
        this.emisionPolizaAutoNuevoPage = new EmisionPolizaAutoNuevoPage(pageProvider.get());
        this.polizaEmitidaAutoPage = new PolizaEmitidaAutoPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @And("en la pagina Auto selecciona la opcion Emitir poliza de Auto")
    public void enLaPaginaAutoSeleccionaLaOpcionEmitirPolizaDeAuto() {
        polizaAutoPage.assertLoaded();
        polizaAutoPage.clickEmitirPolizaAutosButton();
    }

    @And("en la pagina Cotizaciones Auto realiza la busqueda del numero de cotizacion {string} entre el rango de fecha {string} y {string}")
    public void enLaPaginaCotizacionesAutoRealizaLaBusquedaDelNumeroDeCotizacionEntreElRangoDeFechaY(String numero_cotizacion, String fecha_inicio, String fecha_fin) {
        cotizacionesAutoPage.assertLoaded();
        cotizacionesAutoPage.fillFormCotizacionesAuto(fecha_inicio,fecha_fin);
        cotizacionesAutoPage.processAndAssertSuccess(30_000, 500);
        cotizacionesAutoPage.clickVerCotizacion(numero_cotizacion);
    }

    @And("en la pagina Cotizacion Guardada de Autos selecciona el boton Emitir poliza")
    public void enLaPaginaCotizacionGuardadaDeAutosSeleccionaElBotonEmitirPoliza() {
        cotizacionesAutoPage.processAndAssertVerCotizacionSuccess(30_000, 500);
        cotizacionesAutoPage.assertCotizacionNumberLoaded();
        cotizacionesAutoPage.clickEmitirPolizaAuto();
    }

    @And("en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos de la Pliza e ingresamos el numero de placa {string}")
    public void enLaPaginaEmisiónPólizaAutoNuevoCompletamosElFormularioDatosDeLaPlizaEIngresamosElNumeroDePlaca(String numero_placa) {
        emisionPolizaAutoNuevoPage.fillFormDatosDePoliza(numero_placa);
    }

    //Se crea un nuevo step ya que se necesita reutilizar la placa generada en la parte de la cotización
    @And("en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos de la Poliza e ingresamos el numero de placa")
    public void enLaPaginaEmisiónPólizaAutoNuevoCompletamosElFormularioDatosDeLaPolizaEIngresamosElNumeroDePlaca() {

        String numeroPlaca = (String) scenarioContext.get(ScenarioKeys.PolizaAuto.NUMERO_PLACA_VEHICULO);
        if (numeroPlaca == null) {
            throw new RuntimeException("No se encontró la placa en el ScenarioContext");
        }
        emisionPolizaAutoNuevoPage.fillFormDatosDePoliza(numeroPlaca);
   }

    @And("en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante en la seccion Datos Principales ingresamos el nombre {string} apellido paterno {string} apellido materno {string} fecha de nacimiento {string} sexo {string} y profesion {string}")
    public void enLaPaginaEmisiónPólizaAutoNuevoCompletamosElFormularioDatosDelContratanteEnLaSeccionDatosPrincipalesIngresamosElNombreApellidoPaternoApellidoMaternoFechaDeNacimientoSexoYProfesion(String nombre_contratante,String apellido_paterno,String apellido_materno,String fecha_nacimiento,String sexo,String profesion) {
        emisionPolizaAutoNuevoPage.fillFormDatosDelContratanteSeccionDatosPrincipales(nombre_contratante, apellido_paterno, apellido_materno, fecha_nacimiento, sexo, profesion);
    }

    @And("en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante en la seccion Datos de Contacto ingresamos el numero de telefono de casa {string} telefono movil {string} correo electronico {string} departamento {string} procinvia {string} distrito {string} tipo de via {string} y nombre de la via {string}")
    public void enLaPaginaEmisiónPólizaAutoNuevoCompletamosElFormularioDatosDelContratanteEnLaSeccionDatosDeContactoIngresamosElNumeroDeTelefonoDeCasaTelefonoMovilCorreoElectronicoDepartamentoProcinviaDistritoTipoDeViaYNombreDeLaVia(String numero_telefono_casa, String telefono_movil, String correo_electronico, String departamento, String procinvia, String distrito, String tipo_de_via, String nombre_de_via) {
        emisionPolizaAutoNuevoPage.fillFormDatosDelContratanteSeccionDatosDelContacto(numero_telefono_casa, telefono_movil, correo_electronico, departamento, procinvia, distrito, tipo_de_via, nombre_de_via);
    }

    @And("en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante en la seccion Datos del Vehiculo ingresamos la frecuencia de uso {string} el numero de siniestros en los ultimos dos anios {string} los anios de antiguedad de la licencia de conducir {string} y responder con un SI o NO en los casos si el auto es conducido por una persona {string} y responder si usualmente guarda el auto en un garaje {string}")
    public void enLaPaginaEmisiónPólizaAutoNuevoCompletamosElFormularioDatosDelContratanteEnLaSeccionDatosDelVehiculoIngresamosLaFrecuenciaDeUsoElNumeroDeSiniestrosEnLosUltimosDosAniosLosAniosDeAntiguedadDeLaLicenciaDeConducirYResponderConUnSIONOEnLosCasosSiElAutoEsConducidoPorUnaPersonaYResponderSiUsualmenteGuardaElAutoEnUnGaraje(String frecuencia_de_uso, String numero_de_siniestros_anteriores, String anios_de_antiguedad_de_la_licencia, String responder_auto_conducido_por_una_persona, String responder_guarda_el_auto_en_un_garaje) {
        emisionPolizaAutoNuevoPage.fillFormDatosDelContratanteSeccionDatosDelVehiculo(frecuencia_de_uso, numero_de_siniestros_anteriores, anios_de_antiguedad_de_la_licencia, responder_auto_conducido_por_una_persona, responder_guarda_el_auto_en_un_garaje);
        emisionPolizaAutoNuevoPage.processAndAssertSuccessClickBotonSiguiente(70_000,500);
    }

    @And("en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante ingresamos el tipo de financiamiento {string}")
    public void enLaPaginaEmisiónPólizaAutoNuevoCompletamosElFormularioDatosDelContratanteIngresamosElTipoDeFinanciamiento(String tipo_financiamiento) {
        emisionPolizaAutoNuevoPage.fillFormTipoDeFinanciamiento(tipo_financiamiento);
    }

    @And("en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante selecciona el boton Emitir Poliza")
    public void enLaPaginaEmisiónPólizaAutoNuevoCompletamosElFormularioDatosDelContratanteSeleccionaElBotonEmitirPoliza() {
        emisionPolizaAutoNuevoPage.fillFormEmitirPoliza();
        emisionPolizaAutoNuevoPage.processAndAssertEmitirPolizaModalSuccess(120_000,500);
    }


    @Then("en la pagina Póliza Emitida de Autos se muestra el resumen de la poliza")
    public void enLaPaginaPólizaEmitidaDeAutosSeMuestraElResumenDeLaPoliza() {
        polizaEmitidaAutoPage.assertLoaded();
    }



}
