package com.mapfre.test.stepdefinitions.inspeccionAutos;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.models.TimeSlot;
import com.mapfre.playwright.pageobjects.inspeccionAutos.DetalleSolicitudInspeccionAutosPage;
import com.mapfre.playwright.pageobjects.inspeccionAutos.InspeccionAutosSolicitudesPage;
import com.mapfre.playwright.pageobjects.inspeccionAutos.ProgramacionInspeccionModalPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.context.SchedulingState;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.test.hooks.ScenarioKeys;
import com.mapfre.utils.TestResourceFiles;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.nio.file.Path;
import java.util.List;

public class DetalleSolicitudInspeccionAutosSteps {
    private final DetalleSolicitudInspeccionAutosPage detalleSolicitudInspeccionAutosPage;
    private final ProgramacionInspeccionModalPage programacionInspeccionModalPage;
    private final ScenarioContext scenarioContext;
    private final SchedulingState schedulingState;

    public DetalleSolicitudInspeccionAutosSteps(PageProvider pageProvider, ScenarioContext scenarioContext, SchedulingState schedulingState) {
        this.detalleSolicitudInspeccionAutosPage = new DetalleSolicitudInspeccionAutosPage(pageProvider.get());
        this.programacionInspeccionModalPage = new ProgramacionInspeccionModalPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
        this.schedulingState = schedulingState;
    }

    @And("buscar fecha disponible")
    public void buscarFechaDisponible() {
        String numeroNuevaSolicitud = "153991";
        detalleSolicitudInspeccionAutosPage.assertLoaded(numeroNuevaSolicitud);
        detalleSolicitudInspeccionAutosPage.clickProgramacionDeLaInspeccionTab();
        detalleSolicitudInspeccionAutosPage.printFechasNoDisponibles();
        schedulingState.setTargetDate(detalleSolicitudInspeccionAutosPage.getCalendarDateToString());
        List<TimeSlot> unavailable = detalleSolicitudInspeccionAutosPage.getUnavailableSlots(schedulingState.getTargetDate());
        schedulingState.setUnavailableSlots(unavailable);
        System.out.println("=== Unavailable Slots ===");
        unavailable.forEach(slot -> System.out.println("  ❌ " + slot));
        //ElementAsserts.assertNotEmpty(unavailable, "No se encontraron slots no disponibles para la fecha: " + schedulingState.getTargetDate());
        List<TimeSlot> available = detalleSolicitudInspeccionAutosPage.getAvailableSlots(schedulingState.getTargetDate());
        schedulingState.setAvailableSlots(available);
        System.out.println("=== Available Slots ===");
        available.forEach(slot -> System.out.println("  ✅ " + slot));
        ElementAsserts.assertNotEmpty(available, "No se encontraron slots disponibles para la fecha: " + schedulingState.getTargetDate());
        TimeSlot firstSlot = schedulingState.getAvailableSlots().get(0);
        System.out.println("=== Scheduling ===");
        System.out.println("  📅 Booking: " + firstSlot);
        detalleSolicitudInspeccionAutosPage.clickOnTimeSlot(firstSlot.getStartTime());
        schedulingState.setScheduledSlot(firstSlot);
        ElementAsserts.assertNotNull(schedulingState.getScheduledSlot(), "No se pudo seleccionar un slot de inspección para la fecha: " + schedulingState.getTargetDate());
        detalleSolicitudInspeccionAutosPage.processAndAssertSuccessModalProgramacionInspeccionDisponible(40_000, 500);

    }

    @And("en la pagina Detalle Solicitud de Inspeccion de Autos realizamos la programacion de la inspeccion del vehiculo y seleccionamos el inspector {string}")
    public void enLaPaginaDetalleSolicitudDeInspeccionDeAutosRealizamosLaProgramacionDeLaInspeccionDelVehiculoYSeleccionamosElInspector(String inspector) {
        String numeroNuevaSolicitud = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO);
        detalleSolicitudInspeccionAutosPage.assertLoaded(numeroNuevaSolicitud);
        ExtentEvidence.shot("Evidencia despues de validar que el detalle de solicitud de inspeccion de autos se cargo correctamente");
        detalleSolicitudInspeccionAutosPage.clickProgramacionDeLaInspeccionTab();
        ExtentEvidence.shot("Evidencia despues de hacer click en la pestaña Programación de la Inspección");
        detalleSolicitudInspeccionAutosPage.evaluarDisponibilidadYSeleccionarFechaProgramacion();
        ExtentEvidence.shot("Evidencia despues de evaluar la disponibilidad y seleccionar la fecha de programación");
        int inspecctorIndex = Integer.parseInt(inspector); // Cambia este valor según el inspector que desees seleccionar (0, 1, 2, 3, etc.)
        detalleSolicitudInspeccionAutosPage.seleccionarInspector(inspecctorIndex);
        ExtentEvidence.shot("Evidencia despues de seleccionar el inspector para la programación de la inspección");



        detalleSolicitudInspeccionAutosPage.processAndAssertSuccessModalProgramacionInspeccionDisponible(40_000, 500);
        programacionInspeccionModalPage.assertLoaded();
        ExtentEvidence.shot("Evidencia despues de validar que el modal de programacion de inspeccion se cargo correctamente");
        programacionInspeccionModalPage.fillFormProgramacionInspeccion(inspecctorIndex);
    }

    @And("en la pagina Detalle Solicitud de Inspeccion de Autos realizamos la programacion de la inspeccion del vehiculo")
    public void enLaPaginaDetalleSolicitudDeInspeccionDeAutosRealizamosLaProgramacionDeLaInspeccionDelVehiculo() {
        String numeroNuevaSolicitud = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO);
        detalleSolicitudInspeccionAutosPage.assertLoaded(numeroNuevaSolicitud);
        ExtentEvidence.shot("Evidencia despues de validar que el detalle de solicitud de inspeccion de autos se cargo correctamente");
        detalleSolicitudInspeccionAutosPage.clickProgramacionDeLaInspeccionTab();
        ExtentEvidence.shot("Evidencia despues de hacer click en la pestaña Programación de la Inspección");
        detalleSolicitudInspeccionAutosPage.evaluarDisponibilidadYSeleccionarFechaProgramacion();
        ExtentEvidence.shot("Evidencia despues de evaluar la disponibilidad y seleccionar la fecha de programación");
        int inspecctorIndex = 2; // Cambia este valor según el inspector que desees seleccionar (0, 1, 2, 3, etc.)
        detalleSolicitudInspeccionAutosPage.seleccionarInspector(inspecctorIndex);
        ExtentEvidence.shot("Evidencia despues de seleccionar el inspector para la programación de la inspección");
        detalleSolicitudInspeccionAutosPage.processAndAssertSuccessModalProgramacionInspeccion(60_000, 500);
        programacionInspeccionModalPage.assertLoaded();
        ExtentEvidence.shot("Evidencia despues de validar que el modal de programacion de inspeccion se cargo correctamente");
        programacionInspeccionModalPage.fillFormProgramacionInspeccion(inspecctorIndex);
    }

    @Then("se muestra el mensaje de confirmacion de la programacion de la inspeccion del vehiculo y redirecciona a la pagina Inspeccion de Autos")
    public void seMuestraElMensajeDeConfirmacionDeLaProgramacionDeLaInspeccionDelVehiculoYRedireccionaALaPaginaInspeccionDeAutos() {
        programacionInspeccionModalPage.processAndAssertSuccessModalProgramacionInspeccionProgramarButton(20_000, 500);
        ExtentEvidence.shot("Evidencia despues validar el modal de resultado exitoso de programación de inspección se cargo correctamente");
        programacionInspeccionModalPage.processAndAssertSuccessModalSolicitudExitosaOkButton(30_000, 500);
    }

    @And("en la pagina Detalle Solicitud de Inspeccion de Autos realizamos el registro de inspeccion del vehiculo con estado En Proceso de Inspeccion, ingresamos el tipo de vehiculo {string}, modelo del vehiculo {string}, anio de fabricacion {string}, numero de placa {string}, numero de serie {string} y numero de motor {string}")
    public void enLaPaginaDetalleSolicitudDeInspeccionDeAutosRealizamosElRegistroDeInspeccionDelVehiculoConEstadoEnProcesoDeInspeccionIngresamosElTipoDeVehiculoModeloDelVehiculoAnioDeFabricacionNumeroDePlacaNumeroDeSerieYNumeroDeMotor(String tipo_vehiculo, String modelo_vehiculo, String anio_fabricacion, String numero_placa, String numero_serie, String numero_motor) {
        String numeroNuevaSolicitud = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO);
        detalleSolicitudInspeccionAutosPage.assertLoadedProgramda(numeroNuevaSolicitud);
        detalleSolicitudInspeccionAutosPage.processAndAssertSuccessInicioDeRegistrarInspeccionButton(30_000, 500);
        detalleSolicitudInspeccionAutosPage.abrirDatosDelVehiculoTabParaRegistroInspeccion();
        detalleSolicitudInspeccionAutosPage.fillFormDatosDelVehiculoParaRegistroInspeccionPrimeraParte(tipo_vehiculo, modelo_vehiculo, anio_fabricacion);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de datos del vehículo para el registro de inspección, tipo de vehiculo, modelo del vehiculo y anio de fabricacion");
        String numeroPlaca = numero_placa;
        String numeroSerieVin = numero_serie;
        String numeroMotor = numero_motor;
        if(!"OPCIONAL".equalsIgnoreCase(numero_placa) || !"OPCIONAL".equalsIgnoreCase(numero_serie) || !"OPCIONAL".equalsIgnoreCase(numero_motor)) {
            detalleSolicitudInspeccionAutosPage.fillFormDatosDelVehiculoParaRegistroInspeccionSegundaParte(numeroPlaca, numeroSerieVin, numeroMotor);
        }else{
            numeroPlaca = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_PLACA_VEHICULO);
            numeroSerieVin = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_SERIE_VIN_VEHICULO);
            numeroMotor = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_MOTOR_VEHICULO);
            detalleSolicitudInspeccionAutosPage.fillFormDatosDelVehiculoParaRegistroInspeccionSegundaParte(numeroPlaca, numeroSerieVin, numeroMotor);
        }
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de datos del vehículo para el registro de inspección, numero de placa, numero de serie y numero de motor");
        detalleSolicitudInspeccionAutosPage.fillFormDatosDelVehiculoParaRegistroInspeccionTerceraParte();
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de datos del vehículo para el registro de inspección, tercera parte");
        detalleSolicitudInspeccionAutosPage.processAndAssertSuccessGuardarInspeccionButton(30_000, 500);

    }

    @And("en la pagina Detalle Solicitud de Inspeccion de Autos realizamos el registro de inspeccion del vehiculo con estado En Proceso de Inspeccion")
    public void enLaPaginaDetalleSolicitudDeInspeccionDeAutosRealizamosElRegistroDeInspeccionDelVehiculoConEstadoEnProcesoDeInspeccion() {
        String numeroNuevaSolicitud = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO);
        String numeroPlaca = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_PLACA_VEHICULO);
        String numeroSerieVin = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_SERIE_VIN_VEHICULO);
        String numeroMotor = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_MOTOR_VEHICULO);
        detalleSolicitudInspeccionAutosPage.assertLoadedProgramda(numeroNuevaSolicitud);
        detalleSolicitudInspeccionAutosPage.processAndAssertSuccessInicioDeRegistrarInspeccionButton(30_000, 500);
        detalleSolicitudInspeccionAutosPage.abrirDatosDelVehiculoTabParaRegistroInspeccion();
        detalleSolicitudInspeccionAutosPage.fillFormDatosDelVehiculoParaRegistroInspeccion(numeroPlaca, numeroSerieVin, numeroMotor);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de datos del vehículo para el registro de inspección");
        detalleSolicitudInspeccionAutosPage.processAndAssertSuccessGuardarInspeccionButton(30_000, 500);
    }

    @Then("se muestra el mensaje de confirmacion de inspeccion guardada correctamente y redirecciona a la pagina Inspeccion de Autos")
    public void seMuestraElMensajeDeConfirmacionDeInspeccionGuardadaCorrectamenteYRedireccionaALaPaginaInspeccionDeAutos() {
        detalleSolicitudInspeccionAutosPage.processAndAssertSuccessModalInspeccionGuardadaExitosaOkButton(30_000, 500);
    }

    @And("en la pagina Detalle Solicitud de Inspeccion de Autos finalizamos el registro de inspeccion, seccion Fotos del vehiculo, con estado EN EVALUACION")
    public void enLaPaginaDetalleSolicitudDeInspeccionDeAutosFinalizamosElRegistroDeInspeccionSeccionFotosDelVehiculoConEstadoENEVALUACION() {
        String numeroNuevaSolicitud = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO);
        detalleSolicitudInspeccionAutosPage.assertLoadedEnEvaluacion(numeroNuevaSolicitud);
        detalleSolicitudInspeccionAutosPage.abrirFotosDatosDelVehiculoPrincipalTabParaRegistroInspeccion();
        Path tarjetaPropiedadFrontalFilePath = TestResourceFiles.path("images/inspeccionVehicular/tarjeta-propiedad-frontal.jpg");
        Path tarjetaPropiedadPosteriorFilePath = TestResourceFiles.path("images/inspeccionVehicular/tarjeta-propiedad-posterior.jpg");
        detalleSolicitudInspeccionAutosPage.fillFormFotosSeccionTarjetaPropiedadParaRegistroInspeccion(tarjetaPropiedadFrontalFilePath, tarjetaPropiedadPosteriorFilePath);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de fotos de la tarjeta de propiedad para el registro de inspección");
        Path serieFilePath = TestResourceFiles.path("images/inspeccionVehicular/vehiculo-serie.jpg");
        Path lateralDerechaFilePath = TestResourceFiles.path("images/inspeccionVehicular/vechiculo-lateral-derecha.jpg");
        Path lateralIzquierdaFilePath = TestResourceFiles.path("images/inspeccionVehicular/vechiculo-lateral-izquierda.jpg");
        Path maleteraFilePath = TestResourceFiles.path("images/inspeccionVehicular/vechiculo-maletera.jpg");
        Path tableroFilePath = TestResourceFiles.path("images/inspeccionVehicular/vechiculo-tablero.jpg");
        detalleSolicitudInspeccionAutosPage.fillFormFotosSeccionFotosDelVehiculoParaRegistroInspeccion(serieFilePath, lateralDerechaFilePath, lateralIzquierdaFilePath, maleteraFilePath, tableroFilePath);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de fotos del vehículo para el registro de inspección");
        detalleSolicitudInspeccionAutosPage.processAndAssertSuccessGuardarInspeccionButton(30_000, 500);
    }

    @And("en la pagina Detalle Solicitud de Inspeccion de Autos desactivamos el toggle EN EVALUACION para finalizar la inspeccion del vehiculo")
    public void enLaPaginaDetalleSolicitudDeInspeccionDeAutosDesactivamosElToggleENEVALUACIONParaFinalizarLaInspeccionDelVehiculo() {
        String numeroNuevaSolicitud = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO);
        detalleSolicitudInspeccionAutosPage.assertLoadedEnEvaluacion(numeroNuevaSolicitud);
        detalleSolicitudInspeccionAutosPage.desactivarBotonRegistrarInspeccion();
        ExtentEvidence.shot("Evidencia despues de desactivar el toggle EN EVALUACION para finalizar la inspeccion del vehiculo");
        detalleSolicitudInspeccionAutosPage.processAndAssertSuccessFinalizarRegistroModalConfirmacion(60_000, 500);
        ExtentEvidence.shot("Evidencia despues de validar que el modal de confirmacion de finalizacion de registro de inspeccion se cargo correctamente");
        detalleSolicitudInspeccionAutosPage.processAndAssertSuccessFinalizarRegistroModalConfirmacionOKButton(60_000, 500);
        //detalleSolicitudInspeccionAutosPage.processAndAssertSuccessFinalizarRegistroInspeccionButton(30_000, 500);
    }


    @And("en la pagina Detalle Solicitud de Inspeccion de Autos ingresamos al modulo Programacion de la inspeccion y seleccionamos el inspector {string}")
    public void enLaPaginaDetalleSolicitudDeInspeccionDeAutosIngresamosAlModuloProgramacionDeLaInspeccionYSeleccionamosElInspector(String inspector) {
        String numeroNuevaSolicitud = scenarioContext.getString(ScenarioKeys.PolizaAuto.NUMERO_NUEVA_SOLICITUD_VEHICULO);
        detalleSolicitudInspeccionAutosPage.assertLoaded(numeroNuevaSolicitud);
        ExtentEvidence.shot("Evidencia despues de validar que el detalle de solicitud de inspeccion de autos se cargo correctamente");
        detalleSolicitudInspeccionAutosPage.clickProgramacionDeLaInspeccionTab();
        ExtentEvidence.shot("Evidencia despues de hacer click en la pestaña Programación de la Inspección");
        detalleSolicitudInspeccionAutosPage.evaluarDisponibilidadYSeleccionarFechaProgramacion();
        ExtentEvidence.shot("Evidencia despues de evaluar la disponibilidad y seleccionar la fecha de programación");
        int inspecctorIndex = Integer.parseInt(inspector); // Cambia este valor según el inspector que desees seleccionar (0, 1, 2, 3, etc.)
        detalleSolicitudInspeccionAutosPage.seleccionarInspector(inspecctorIndex);
    }

    @And("en la pagina Detalle Solicitud de Inspeccion de Autos realizamos la programacion de la inspeccion del vehiculo en una fecha disponible")
    public void enLaPaginaDetalleSolicitudDeInspeccionDeAutosRealizamosLaProgramacionDeLaInspeccionDelVehiculoEnUnaFechaDisponible() {
        schedulingState.setTargetDate(detalleSolicitudInspeccionAutosPage.getCalendarDateToString());
        List<TimeSlot> unavailable = detalleSolicitudInspeccionAutosPage.getUnavailableSlots(schedulingState.getTargetDate());
        schedulingState.setUnavailableSlots(unavailable);
        System.out.println("=== Unavailable Slots ===");
        unavailable.forEach(slot -> System.out.println("  ❌ " + slot));
        List<TimeSlot> available = detalleSolicitudInspeccionAutosPage.getAvailableSlots(schedulingState.getTargetDate());
        schedulingState.setAvailableSlots(available);
        System.out.println("=== Available Slots ===");
        available.forEach(slot -> System.out.println("  ✅ " + slot));
        ElementAsserts.assertNotEmpty(available, "No se encontraron slots disponibles para la fecha: " + schedulingState.getTargetDate());
        TimeSlot firstSlot = schedulingState.getAvailableSlots().get(0);
        System.out.println("=== Scheduling ===");
        System.out.println("  📅 Booking: " + firstSlot);
        detalleSolicitudInspeccionAutosPage.clickOnTimeSlot(firstSlot.getStartTime());
        schedulingState.setScheduledSlot(firstSlot);
        ElementAsserts.assertNotNull(schedulingState.getScheduledSlot(), "No se pudo seleccionar un slot de inspección para la fecha: " + schedulingState.getTargetDate());
        detalleSolicitudInspeccionAutosPage.processAndAssertSuccessModalProgramacionInspeccionDisponible(40_000, 500);
        programacionInspeccionModalPage.assertLoaded();
        ExtentEvidence.shot("Evidencia despues de validar que el modal de programacion de inspeccion se cargo correctamente");
        programacionInspeccionModalPage.fillFormProgramacionInspeccion();
    }
}
