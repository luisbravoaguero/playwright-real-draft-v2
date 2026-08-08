package com.mapfre.test.stepdefinitions.inspeccionAutos;

import com.mapfre.playwright.pageobjects.inspeccionAutos.NuevaSolicitudInspeccionPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.test.hooks.ScenarioKeys;
import com.mapfre.utils.DateUtils;
import com.mapfre.utils.StringUtils;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NuevaSolicitudInspeccionSteps {
    private static final Logger log = LoggerFactory.getLogger(NuevaSolicitudInspeccionSteps.class);
    private final NuevaSolicitudInspeccionPage nuevaSolicitudInspeccionPage;
    private final ScenarioContext scenarioContext;

    public NuevaSolicitudInspeccionSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.nuevaSolicitudInspeccionPage = new NuevaSolicitudInspeccionPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @When("en la pagina Nueva Solicitud de Inspeccion, en el paso Solicitante y vehiculo, en la seccion Informacion del solicitante completamos el correo electronico y  en la seccion Datos del vehiculo completamoe el numero de placa {string}, numero de serie {string}, numero de motor {string} y cantidad de accesorios")
    public void enLaPaginaNuevaSolicitudDeInspeccionEnElPasoSolicitanteYVehiculoEnLaSeccionInformacionDelSolicitanteCompletamosElCorreoElectronicoYEnLaSeccionDatosDelVehiculoCompletamoeElNumeroDePlacaNumeroDeSerieNumeroDeMotorYCantidadDeAccesorios(String numero_placa, String numero_serie, String numero_motor) {
        String numeroPlaca;
        String numeroSerie;
        String numeroMotor;
        if(!"OPCIONAL".equalsIgnoreCase(numero_placa) || !"OPCIONAL".equalsIgnoreCase(numero_serie) || !"OPCIONAL".equalsIgnoreCase(numero_motor)){
            numeroPlaca = numero_placa;
            numeroSerie = numero_serie;
            numeroMotor = numero_motor;
            scenarioContext.put(ScenarioKeys.PolizaAuto.NUMERO_PLACA_VEHICULO, numeroPlaca);
            scenarioContext.put(ScenarioKeys.PolizaAuto.NUMERO_SERIE_VIN_VEHICULO, numeroSerie);
            scenarioContext.put(ScenarioKeys.PolizaAuto.NUMERO_MOTOR_VEHICULO, numeroMotor);

        }else{
            numeroPlaca = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_PLACA_VEHICULO);
            numeroSerie = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_SERIE_VIN_VEHICULO);
            //numeroMotor = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_MOTOR_VEHICULO);
        }

        nuevaSolicitudInspeccionPage.assertLoaded();
        nuevaSolicitudInspeccionPage.fillFormInformacionSolicitante();
        nuevaSolicitudInspeccionPage.fillFormDatosDelVehiculo(numeroPlaca, numeroSerie);
        ExtentEvidence.shot("Evidencia despues de completar el formulario del paso Solicitante y Vehiculo");
        nuevaSolicitudInspeccionPage.processAndAssertSuccessSolicitanteVehiculo(15_000, 500);
    }

    @When("en la pagina Nueva Solicitud de Inspeccion, en el paso Solicitante y vehiculo, en la seccion Informacion del solicitante completamos el correo electronico y  en la seccion Datos del vehiculo completamoe el numero de placa, serie y cantidad de accesorios")
    public void enLaPaginaNuevaSolicitudDeInspeccionEnElPasoSolicitanteYVehiculoEnLaSeccionInformacionDelSolicitanteCompletamosElCorreoElectronicoYEnLaSeccionDatosDelVehiculoCompletamoeElNumeroDePlacaSerieYCantidadDeAccesorios() {
        String numeroPlaca = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_PLACA_VEHICULO);
        String numeroSerie = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_SERIE_VIN_VEHICULO);
        nuevaSolicitudInspeccionPage.assertLoaded();
        nuevaSolicitudInspeccionPage.fillFormInformacionSolicitante();
        nuevaSolicitudInspeccionPage.fillFormDatosDelVehiculo(numeroPlaca, numeroSerie);
        ExtentEvidence.shot("Evidencia despues de completar el formulario del paso Solicitante y Vehiculo");
        nuevaSolicitudInspeccionPage.processAndAssertSuccessSolicitanteVehiculo(15_000, 500);
    }

    @And("en la pagina Nueva Solicitud de Inspeccion, en el paso Contratante Contacto, en la seccion Datos del contratante completamos la fecha de nacimiento, estado civil, nacionalidad, sexo y profesion")
    public void enLaPaginaNuevaSolicitudDeInspeccionEnElPasoContratanteContactoEnLaSeccionDatosDelContratanteCompletamosLaFechaDeNacimientoEstadoCivilNacionalidadSexoYProfesion() {
        nuevaSolicitudInspeccionPage.fillFormDatosDelContratante();
    }
    @And("en la pagina Nueva Solicitud de Inspeccion, en el paso Contratante Contacto, en la seccion Datos del contratante completamos el numero de documento {string}, nombre {string}, apellido paterno {string} y materno {string} la fecha de nacimiento, estado civil, nacionalidad, sexo y profesion")
    public void enLaPaginaNuevaSolicitudDeInspeccionEnElPasoContratanteContactoEnLaSeccionDatosDelContratanteCompletamosElNumeroDeDocumentoNombreApellidoPaternoYMaternoLaFechaDeNacimientoEstadoCivilNacionalidadSexoYProfesion(String numero_documento, String nombre, String apellido_paterno, String apellido_materno) {
        nuevaSolicitudInspeccionPage.fillFormDatosDelContratante(numero_documento, nombre, apellido_paterno, apellido_materno);
    }

    @And("en la pagina Nueva Solicitud de Inspeccion, en el paso Contratante Contacto, en la seccion Datos de contacto completamos el tefono de casa, oficina y movil, correo electronico personal y de oficina")
    public void enLaPaginaNuevaSolicitudDeInspeccionEnElPasoContratanteContactoEnLaSeccionDatosDeContactoCompletamosElTefonoDeCasaOficinaYMovilCorreoElectronicoPersonalYDeOficina() {
        nuevaSolicitudInspeccionPage.fillFormDatosDeContacto();
    }

    @And("en la pagina Nueva Solicitud de Inspeccion, en el paso Contratante Contacto, en la seccion Datos de direccion completamos el departamento, provincia y distrito, y la direccion de su residencia")
    public void enLaPaginaNuevaSolicitudDeInspeccionEnElPasoContratanteContactoEnLaSeccionDatosDeDireccionCompletamosElDepartamentoProvinciaYDistritoYLaDireccionDeSuResidencia() {
        nuevaSolicitudInspeccionPage.fillFormDatosDeDireccion();
        ExtentEvidence.shot("Evidencia despues de completar el formulario del paso Contratante Contacto");
        nuevaSolicitudInspeccionPage.processAndAssertSuccessContratanteContrato(30_000, 500);
    }

    @And("en la pagina Nueva Solicitud de Inspeccion, en el paso Datos de la emision, en la seccion Financiamiento completamos el tipo de financiamiento al contado")
    public void enLaPaginaNuevaSolicitudDeInspeccionEnElPasoDatosDeLaEmisionEnLaSeccionFinanciamientoCompletamosElTipoDeFinanciamientoAlContado() {
        nuevaSolicitudInspeccionPage.fillFormDatosDeEmision();
        ExtentEvidence.shot("Evidencia despues de completar el formulario del paso Datos de la Emision");
        nuevaSolicitudInspeccionPage.processAndAssertSuccessDatosEmision(30_000, 500);
    }

    @And("en la pagina Nueva Solicitud de Inspeccion, en el paso Confirmar solicitud, en la seccion Confirmar solicitud seleccionamos el boton Confirmar solicitud")
    public void enLaPaginaNuevaSolicitudDeInspeccionEnElPasoConfirmarSolicitudEnLaSeccionConfirmarSolicitudSeleccionamosElBotonConfirmarSolicitud() {
        nuevaSolicitudInspeccionPage.processAndAssertSuccessConfirmarSolicitud(30_000, 500);
        ExtentEvidence.shot("Evidencia despues de mostrar el mensaje de solicitud de inspeccion creada exitosamente");
        nuevaSolicitudInspeccionPage.seleccionarInspeccionPresencialDentroDelModal();

    }

    @And("en la pagina Nueva Solicitud de Inspeccion, seleccionamos la opcion Inspeccion Presencial dentro del modal")
    public void enLaPaginaNuevaSolicitudDeInspeccionSeleccionamosLaOpcionInspeccionPresencialDentroDelModal() {
        nuevaSolicitudInspeccionPage.processAndAssertSuccessSeleccionarTipoInspeccion(50_000, 500);
    }

    @Then("en la pagina Nueva Solicitud de Inspeccion, se crea el numero de solicitud de inspeccion y redirecciona a la pagina Inspeccion de Autos")
    public void enLaPaginaNuevaSolicitudDeInspeccionSeCreaElNumeroDeSolicitudDeInspeccionYRedireccionaALaPaginaInspeccionDeAutos() {
        String numeroNuevaSolicitud = StringUtils.extractAllDigits(nuevaSolicitudInspeccionPage.obtenerNumeroSolicitudInspeccion());
        log.info("Numero de nueva solicitud de inspeccion creada: {}", numeroNuevaSolicitud);
        scenarioContext.put(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO, numeroNuevaSolicitud);
        nuevaSolicitudInspeccionPage.processAndAssertSuccessModalNuevaSolicitudInspeccionCreadaOkButton(60_000, 500);
    }



}
