package com.mapfre.playwright.pageobjects.inspeccionAutos;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.models.TimeSlot;
import com.mapfre.playwright.components.material.MaterialAutocomplete;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DateUtils;
import com.mapfre.utils.UiSync;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetalleSolicitudInspeccionAutosPage extends BasePage {
    private final Locator estadoPorPogramarLabel;
    private final Locator programacionDeLaInspeccionTab;
    private final Locator fechaCompletaCanlendarioTitle;
    private final Locator anteriorDiaFlechaButton;
    private final Locator siguienteDiaFlechaButton;
    private final Locator inspectorCheckbox;
    private final Locator horaEspecificoButtonTd;
    private final Locator resultadoModalMensajeError;
    private final Locator resultadoModalInfo;
    private final Locator OKButtonModalInfo;
    private final Locator resultadoModalWarning;
    private final Locator programarInspeccionModal;
    private final Locator estadoProgramadaLabel;
    private final Locator registrarInspeccionButton;
    private final Locator enProcesoDeInspeccionLabel;
    private final Locator datosDelVehiculoTab;
    private final Locator tipoVehiculoSelect;
    private final MaterialAutocomplete marcaModeloAutocomplete;
    private MaterialAutocomplete.AttemptKind lastMarcaModeloAttempt;
    private final Locator marcaModeloCombo;
    private final Locator anioFabricacionSelect;
    private final Locator numeroPlacaInput;
    private final Locator numeroSerieVinInput;
    private final Locator numeroMotorInput;
    private final Locator timonVehiculoSelect;
    private final Locator colorVehiculoSelect;
    private final Locator traccionVehiculoSelect;
    private final Locator kilometrajeVehiculoInput;
    private final Locator sumaAseguradaVehiculoInput;
    private final Locator origenAutoVehiculoSelect;
    private final Locator estadoAutoVehiculoSelect;
    private final Locator fechaRevisionTecnicaVehiculoInput;
    private final Locator guardarButton;
    private final Locator resultadoExitosoModal;
    private final Locator mensajeSolicitudExitosaModal;
    private final Locator OkSolicitudExitosaModalButton;
    private final Locator breadcrumbsInspeccionAutosTitle;
    private final Locator estadoEnEvaluacionDetalleInspeccionLabel;
    private final Locator fotosDelVehiculoPrincipalTab;
    private final Locator tarjetaPropiedadTab;
    private final Locator tarjetaPropiedadFrontalFotoInput;
    private final Locator tarjetaPropiedadPosteriorFotoInput;
    private final Locator fotosDelVehiculoTab;
    private final Locator fotosDelVehiculoSerieFotoInput;
    private final Locator fotosDelVehiculolateralDerechaFotoInput;
    private final Locator fotosDelVehiculolateralIzquierdaFotoInput;
    private final Locator fotosDelVehiculoMaleteraFotoInput;
    private final Locator fotosDelVehiculoTableroFotoInput;
    private final Locator toggleEnEvaluacionButton;
    private final Locator finalizarButton;
    private final Locator fechasNoDisponiblesProgramacion;
    private final Locator filtroMenuCommon;
    private static final LocalTime WORK_START = LocalTime.of(8, 0);
    private static final LocalTime WORK_END = LocalTime.of(19, 0);
    private static final int SLOT_DURATION_MINUTES = 30;
    // Locators
    private static final String CALENDAR_EVENTS = ".fc-timegrid-event";
    private static final String EVENT_TIME = ".fc-event-time";
    private static final String SLOT_LANE = "td.fc-timegrid-slot-lane[data-time='%s']";
    private static final String CALENDAR_COLUMN = "td.fc-timegrid-col .fc-timegrid-col-frame";
    private static final String CALENDAR_BODY = ".fc-timegrid-body";

    public DetalleSolicitudInspeccionAutosPage(Page page) {
        super(page);
        this.estadoPorPogramarLabel = page.getByText("POR PROGRAMAR");
        this.programacionDeLaInspeccionTab = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("PROGRAMACION DE LA INSPECCIÓN"));
        this.fechaCompletaCanlendarioTitle = page.locator("full-calendar h2.fc-toolbar-title");
        this.anteriorDiaFlechaButton = page.locator("full-calendar button.fc-prev-button");
        this.siguienteDiaFlechaButton = page.locator("full-calendar button.fc-next-button");
        this.inspectorCheckbox = page.locator("inspec-calendar-search-filter oim-checkbox input[type='checkbox']");
        this.horaEspecificoButtonTd = page.locator("full-calendar td[data-time='12:30:00']").nth(1);
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.resultadoModalInfo = page.locator("div.swal2-popup.swal2-icon-info[role='dialog']");
        this.OKButtonModalInfo = resultadoModalInfo.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Ok"));
        this.resultadoModalWarning = page.locator("div.swal2-popup.swal2-icon-warning[role='dialog']");
        this.programarInspeccionModal = page.locator("mat-dialog-container").filter(new Locator.FilterOptions().setHasText("PROGRAMAR INSPECCIÓN"));
        this.estadoProgramadaLabel = page.locator("inspec-request-detail-header").getByText("PROGRAMADA");
        this.registrarInspeccionButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("REGISTRAR INSPECCIÓN"));
        this.enProcesoDeInspeccionLabel = page.getByText("EN PROCESO DE INSPECCIÓN");
        this.datosDelVehiculoTab = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("DATOS DEL VEHÍCULO"));
        this.tipoVehiculoSelect = page.getByLabel("Tipo de Vehículo");
        this.marcaModeloCombo = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Marca y Modelo"));
        this.marcaModeloAutocomplete = new MaterialAutocomplete(page, marcaModeloCombo, "Marca y Modelo").setDebug(false);
        this.anioFabricacionSelect = page.getByLabel("Año de Fabricación");
        this.numeroPlacaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de placa"));
        this.numeroSerieVinInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de Serie - VIN"));
        this.numeroMotorInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de motor"));
        this.timonVehiculoSelect = page.getByLabel("Timón");
        this.colorVehiculoSelect = page.getByLabel("Color");
        this.traccionVehiculoSelect = page.getByLabel("Tracción");
        this.kilometrajeVehiculoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Kilometraje (km.)"));
        this.sumaAseguradaVehiculoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Suma Asegurada"));
        this.origenAutoVehiculoSelect = page.getByLabel("Origen Auto");
        this.estadoAutoVehiculoSelect = page.getByLabel("Estado Auto");
        this.fechaRevisionTecnicaVehiculoInput = page.locator("oim-datepicker[formcontrolname='technicalReview'] input");
        this.guardarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("GUARDAR"));
        this.resultadoExitosoModal = page.locator("div.swal2-popup.swal2-icon-success[role='dialog']");
        this.mensajeSolicitudExitosaModal = resultadoExitosoModal.locator("#swal2-html-container");
        this.OkSolicitudExitosaModalButton = resultadoExitosoModal.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Ok"));
        this.breadcrumbsInspeccionAutosTitle = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Inspección de Autos >"));
        this.estadoEnEvaluacionDetalleInspeccionLabel = page.getByText("EN EVALUACION");
        this.fotosDelVehiculoPrincipalTab = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("FOTOS"));
        this.tarjetaPropiedadTab = page.getByText("TARJETA DE PROPIEDAD", new Page.GetByTextOptions().setExact(true));
        this.tarjetaPropiedadFrontalFotoInput = page.locator("inspec-inspection-register-photos-cards:has-text('TARJETA DE PROPIEDAD - FRONTAL') input[type='file']");
        this.tarjetaPropiedadPosteriorFotoInput = page.locator("inspec-inspection-register-photos-cards:has-text('TARJETA DE PROPIEDAD - POSTERIOR') input[type='file']");
        this.fotosDelVehiculoTab = page.getByText("FOTOS DEL VEHÍCULO");
        this.fotosDelVehiculoSerieFotoInput = page.locator("inspec-inspection-register-photos-cards:has-text('SERIE') input[type='file']");
        this.fotosDelVehiculolateralDerechaFotoInput = page.locator("inspec-inspection-register-photos-cards:has-text('LATERAL DERECHA') input[type='file']");
        this.fotosDelVehiculolateralIzquierdaFotoInput = page.locator("inspec-inspection-register-photos-cards:has-text('LATERAL IZQUIERDA') input[type='file']");
        this.fotosDelVehiculoMaleteraFotoInput = page.locator("inspec-inspection-register-photos-cards:has-text('MALETERA') input[type='file']");
        this.fotosDelVehiculoTableroFotoInput = page.locator("inspec-inspection-register-photos-cards:has-text('TABLERO') input[type='file']");
        this.toggleEnEvaluacionButton = page.locator("oim-checkbox-toggle button[role='switch']");
        this.finalizarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("FINALIZAR"));
        this.fechasNoDisponiblesProgramacion = page.locator("div.fc-event-main-frame div.fc-event-time");
        this.filtroMenuCommon = page.locator("div.g-card-filter");

    }

    public void waitForCalendarToLoad() {
        page.waitForSelector(CALENDAR_BODY,
                new Page.WaitForSelectorOptions().setTimeout(15000));
    }

    public void assertLoaded(String numeroNuevaSolicitud) {
        Locator titleLocator = page.getByRole(AriaRole.HEADING).filter(new Locator.FilterOptions().setHasText(Pattern.compile("Solicitud #"+numeroNuevaSolicitud)));
        ElementAsserts.assertVisible(titleLocator, "El titulo Detalle Solicitud # DEBE SER VISIBLE");
        ElementAsserts.assertVisible(estadoPorPogramarLabel, "El label de estado POR PROGRAMAR DEBE SER VISIBLE en el detalle de solicitud de inspeccion");
    }

    public void clickProgramacionDeLaInspeccionTab() {
        clickAndSync(programacionDeLaInspeccionTab);
    }

    public void evaluarDisponibilidadYSeleccionarFechaProgramacion() {
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(2000);
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Evaluando calendario para seleccionar fecha de programación de inspección");
        String displayedFullDate = fechaCompletaCanlendarioTitle.textContent();
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Fecha inicial en calendario: {}", displayedFullDate);
        LocalDate calendarDate = DateUtils.parseStringFullCalendarSpanishDateToLocalDate(displayedFullDate);
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        log.info("Fecha actual: {}", today);
        log.info("Fecha objetivo: {} (día después de hoy)", tomorrow);
        log.info("Fecha inicial en calendario: {}", calendarDate);

        if (calendarDate.isBefore(tomorrow)) {advanceCalendarToDate(tomorrow);}
        if (calendarDate.isAfter(tomorrow)) {retreatCalendarToDate(tomorrow);}

        validateCalendarDate(tomorrow);
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Fecha en calendario ajustada correctamente a: {}", tomorrow);
        waitRandomBetween(1000);
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(5000);
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] El calendario está mostrando la fecha correcta para programar la inspección: {}", tomorrow);
    }

    private void advanceCalendarToDate(LocalDate targetDate) {
        log.info("Avanzando calendario hasta: {}", targetDate);

        int maxRetries = 31; // Safety limit (max 31 days)
        int retries = 0;

        String displayedFullDate = fechaCompletaCanlendarioTitle.textContent();
        LocalDate calendarDate = DateUtils.parseStringFullCalendarSpanishDateToLocalDate(displayedFullDate);

        while (calendarDate.isBefore(targetDate) && retries < maxRetries) {
            clickAndSync(siguienteDiaFlechaButton);
            waitForNetworkIdle();

            displayedFullDate = fechaCompletaCanlendarioTitle.textContent();
            calendarDate = DateUtils.parseStringFullCalendarSpanishDateToLocalDate(displayedFullDate);

            log.info("Avance {}: Fecha = {}", ++retries, calendarDate);
        }

        if (retries >= maxRetries) {
            throw new FrameworkException("El calendario no pudo avanzar a " + targetDate + " después de " + maxRetries + " intentos");
        }
    }

    private void retreatCalendarToDate(LocalDate targetDate) {
        log.info("Retrocediendo calendario hasta: {}", targetDate);

        int maxRetries = 31; // Safety limit
        int retries = 0;

        String displayedFullDate = fechaCompletaCanlendarioTitle.textContent();
        LocalDate calendarDate = DateUtils.parseStringFullCalendarSpanishDateToLocalDate(displayedFullDate);

        while (calendarDate.isAfter(targetDate) && retries < maxRetries) {
            clickAndSync(anteriorDiaFlechaButton);
            waitForNetworkIdle();

            displayedFullDate = fechaCompletaCanlendarioTitle.textContent();
            calendarDate = DateUtils.parseStringFullCalendarSpanishDateToLocalDate(displayedFullDate);

            log.info("Retroceso {}: Fecha = {}", ++retries, calendarDate);
        }

        if (retries >= maxRetries) {
            throw new FrameworkException("El calendario no pudo retroceder a " + targetDate + " después de " + maxRetries + " intentos");
        }
    }

    private void validateCalendarDate(LocalDate expectedDate) {
        String displayedFullDate = fechaCompletaCanlendarioTitle.textContent();
        String expectedDateFormatted = DateUtils.formatLocalDateToFullCalendarSpanishFormat(expectedDate);
        log.info("Validando fecha: {} (esperada: {})", displayedFullDate, expectedDateFormatted);
        ElementAsserts.assertContainsText(fechaCompletaCanlendarioTitle, expectedDateFormatted, "El calendario no muestra la fecha esperada: " + expectedDateFormatted);
    }

    public void seleccionarInspector(int... indices) {
        for (int index : indices) {
            inspectorCheckbox.nth(index).check();
            verifyCheckboxesIsChecked(inspectorCheckbox.nth(index), "Inspector");
            waitRandomBetween(1000);
            log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Inspector seleccionado en el filtro, índice: {}", index);
        }
    }

    public void processAndAssertSuccessModalProgramacionInspeccion(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                horaEspecificoButtonTd::click,               // action (coloca null si la accion click fue realizad)
                resultadoModalMensajeError,
                programarInspeccionModal,
                resultadoModalMensajeError,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Programación de inspección procesada exitosamente, modal de programación de inspección apareció.");
    }

    public void processAndAssertSuccessModalProgramacionInspeccionDisponible(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizad)
                resultadoModalMensajeError,
                programarInspeccionModal,
                resultadoModalMensajeError,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Programación de inspección procesada exitosamente, modal de programación de inspección apareció.");
    }

    public void assertLoadedProgramda(String numeroNuevaSolicitud) {
        Locator titleLocator = page.getByRole(AriaRole.HEADING).filter(new Locator.FilterOptions().setHasText(Pattern.compile("Solicitud #"+numeroNuevaSolicitud)));
        ElementAsserts.assertVisible(titleLocator, "El titulo Detalle Solicitud # DEBE SER VISIBLE");
        ElementAsserts.assertVisible(estadoProgramadaLabel, "El label de estado PROGRAMADA DEBE SER VISIBLE en el detalle de solicitud de inspeccion");
    }

    public void processAndAssertSuccessInicioDeRegistrarInspeccionButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(registrarInspeccionButton, "El botón REGISTRAR INSPECCIÓN DEBE SER VISIBLE para iniciar el registro de la inspección");
        ElementAsserts.assertEnabled(registrarInspeccionButton, "El botón REGISTRAR INSPECCIÓN DEBE SER HABILITADO para iniciar el registro de la inspección");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                registrarInspeccionButton::click,               // action (coloca null si la accion click fue realizad)
                resultadoModalMensajeError,
                enProcesoDeInspeccionLabel,
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Inicio del proceso de registro de inspección exitoso, etiqueta de En Proceso de Inspección apareció.");
    }

    public void abrirDatosDelVehiculoTabParaRegistroInspeccion() {
        ElementAsserts.assertVisible(datosDelVehiculoTab, "La pestaña de DATOS DEL VEHÍCULO DEBE SER VISIBLE para llenar el formulario de datos del vehículo");
        clickAndSync(datosDelVehiculoTab);
    }

    public void fillFormDatosDelVehiculoParaRegistroInspeccion(String numeroPlaca, String numeroSerieVin, String numeroMotor) {
        selectOptionIfEnabledAndEmptyAndSync(tipoVehiculoSelect, "AUTOMOVIL");
        if(marcaModeloCombo.inputValue().isEmpty()){
            String marcaModelo = "TOYOTA YARIS";
            lastMarcaModeloAttempt = marcaModeloAutocomplete.selectExact(
                    marcaModelo,
                    6000, // timeoutMs
                    500   // quietMs
            );
            log.info("[INFO] Intento que funcionó en 'Marca y Modelo': {}", lastMarcaModeloAttempt);
        }
        //selectOptionIfEnabledAndSync(anioFabricacionSelect,"2013");
        fillAndSync(numeroPlacaInput, numeroPlaca);
        fillAndSync(numeroSerieVinInput, numeroSerieVin);
        fillAndSync(numeroMotorInput, numeroMotor);
        selectOptionAndSync(timonVehiculoSelect, "ORIGINAL");
        selectOptionAndSync(colorVehiculoSelect, "ARENA");
        selectOptionAndSync(traccionVehiculoSelect, "4 x 2");
        fillAndSync(kilometrajeVehiculoInput, "87000");
        fillInputValueIfEnabledAndEmptyAndSync(sumaAseguradaVehiculoInput, "16000");
        selectOptionAndSync(origenAutoVehiculoSelect, "OTROS");
        selectOptionAndSync(estadoAutoVehiculoSelect, "BUENO");
        LocalDate today = LocalDate.now();
        LocalDate newDate = today.plusYears(1);
        fillReadonlyDate(fechaRevisionTecnicaVehiculoInput, DateUtils.formatLocalDateToStringDdMmYyyy(newDate));
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Formulario de datos del vehículo para registro de inspección completado con número de placa {}, número de serie VIN {} y número de motor {}", numeroPlaca, numeroSerieVin, numeroMotor);
    }

    public void fillFormDatosDelVehiculoParaRegistroInspeccionPrimeraParte(String tipoVehiculo, String modeloVehiculo, String anioFabricacion) {
        selectOptionIfEnabledAndEmptyAndSync(tipoVehiculoSelect, tipoVehiculo);
        if(marcaModeloCombo.inputValue().isEmpty()){
            lastMarcaModeloAttempt = marcaModeloAutocomplete.selectExact(
                    modeloVehiculo,
                    6000, // timeoutMs
                    500   // quietMs
            );
            log.info("[INFO] Intento que funcionó en 'Marca y Modelo': {}", lastMarcaModeloAttempt);
        }
        selectOptionIfEnabledAndSync(anioFabricacionSelect,anioFabricacion);
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Formulario de datos del vehículo para registro de inspección completado con tipo de vehículo {}, modelo del vehículo {} y año de fabricación {}", tipoVehiculo, modeloVehiculo, anioFabricacion);
    }

    public void fillFormDatosDelVehiculoParaRegistroInspeccionSegundaParte(String numeroPlaca, String numeroSerieVin, String numeroMotor) {
        fillAndSync(numeroPlacaInput, numeroPlaca);
        fillAndSync(numeroSerieVinInput, numeroSerieVin);
        fillAndSync(numeroMotorInput, numeroMotor);
        selectOptionAndSync(timonVehiculoSelect, "ORIGINAL");
        selectOptionAndSync(colorVehiculoSelect, "ARENA");
        selectOptionAndSync(traccionVehiculoSelect, "4 x 2");
        fillAndSync(kilometrajeVehiculoInput, "87000");
        fillInputValueIfEnabledAndEmptyAndSync(sumaAseguradaVehiculoInput, "16000");
        selectOptionAndSync(origenAutoVehiculoSelect, "OTROS");
        selectOptionAndSync(estadoAutoVehiculoSelect, "BUENO");
        LocalDate today = LocalDate.now();
        LocalDate newDate = today.plusYears(1);
        fillReadonlyDate(fechaRevisionTecnicaVehiculoInput, DateUtils.formatLocalDateToStringDdMmYyyy(newDate));
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Formulario de datos del vehículo para registro de inspección completado con número de placa {}, número de serie VIN {} y número de motor {}", numeroPlaca, numeroSerieVin, numeroMotor);
    }

    public void fillFormDatosDelVehiculoParaRegistroInspeccionTerceraParte() {
        selectOptionAndSync(timonVehiculoSelect, "ORIGINAL");
        selectOptionAndSync(colorVehiculoSelect, "ARENA");
        selectOptionAndSync(traccionVehiculoSelect, "4 x 2");
        fillAndSync(kilometrajeVehiculoInput, "87000");
        fillInputValueIfEnabledAndEmptyAndSync(sumaAseguradaVehiculoInput, "16000");
        selectOptionAndSync(origenAutoVehiculoSelect, "OTROS");
        selectOptionAndSync(estadoAutoVehiculoSelect, "BUENO");
        LocalDate today = LocalDate.now();
        LocalDate newDate = today.plusYears(1);
        fillReadonlyDate(fechaRevisionTecnicaVehiculoInput, DateUtils.formatLocalDateToStringDdMmYyyy(newDate));
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Formulario de datos del vehículo para registro de inspección completado con timón ORIGINAL, color ARENA, tracción 4 x 2, kilometraje 87000, suma asegurada 16000, origen OTROS, estado BUENO y fecha de revisión técnica un año después de la fecha actual.");
    }

    public void processAndAssertSuccessGuardarInspeccionButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(guardarButton, "El botón GUARDAR DEBE SER VISIBLE para guardar el registro de la inspección");
        ElementAsserts.assertEnabled(guardarButton, "El botón GUARDAR DEBE SER HABILITADO para guardar el registro de la inspección");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                guardarButton::click,               // action (coloca null si la accion click fue realizad)
                resultadoModalMensajeError,
                resultadoExitosoModal,
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Registro de inspección guardado exitosamente, modal de resultado exitoso apareció.");
    }

    public void processAndAssertSuccessModalInspeccionGuardadaExitosaOkButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(resultadoExitosoModal, "El modal de resultado exitoso DEBE SER VISIBLE después de guardar la inspección");
        ElementAsserts.assertVisible(OkSolicitudExitosaModalButton, "El botón Ok del modal de resultado exitoso DEBE SER VISIBLE para cerrar el modal después de guardar la inspección");
        ElementAsserts.assertEnabled(OkSolicitudExitosaModalButton, "El botón Ok del modal de resultado exitoso DEBE SER HABILITADO para cerrar el modal después de guardar la inspección");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                OkSolicitudExitosaModalButton::click,               // action (coloca null si la accion click fue realizad)
                resultadoModalMensajeError,
                breadcrumbsInspeccionAutosTitle,
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Modal de resultado exitoso cerrado exitosamente, se volvió al listado de inspección de autos.");
    }

    public void assertLoadedEnEvaluacion(String numeroNuevaSolicitud) {
        Locator titleLocator = page.getByRole(AriaRole.HEADING).filter(new Locator.FilterOptions().setHasText(Pattern.compile("Solicitud #"+numeroNuevaSolicitud)));
        ElementAsserts.assertVisible(titleLocator, "El titulo Detalle Solicitud # DEBE SER VISIBLE");
        ElementAsserts.assertVisible(estadoEnEvaluacionDetalleInspeccionLabel, "El label de estado EN EVALUACION DEBE SER VISIBLE en el detalle de solicitud de inspeccion");
    }

    public void abrirFotosDatosDelVehiculoPrincipalTabParaRegistroInspeccion() {
        ElementAsserts.assertVisible(fotosDelVehiculoPrincipalTab, "La pestaña principal FOTOS del vehiculo DEBE SER VISIBLE para llenar el formulario de fotos del vehículo");
        clickAndSync(fotosDelVehiculoPrincipalTab);
    }

    public void fillFormFotosSeccionTarjetaPropiedadParaRegistroInspeccion(Path tarjetaPropiedadFrontalFilePath, Path tarjetaPropiedadPosteriorFilePath) {
        clickAndSync(tarjetaPropiedadTab);
        uploadFile(tarjetaPropiedadFrontalFotoInput, tarjetaPropiedadFrontalFilePath);
        waitRandomBetween(800);
        clickAndSync(tarjetaPropiedadTab);
        uploadFile(tarjetaPropiedadPosteriorFotoInput, tarjetaPropiedadPosteriorFilePath);
        waitRandomBetween(800);
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Formulario de fotos del vehículo, sección tarjeta de propiedad, completado con las fotos frontal y posterior de la tarjeta de propiedad.");
    }

    public void fillFormFotosSeccionFotosDelVehiculoParaRegistroInspeccion(Path serieFilePath, Path lateralDerechaFilePath, Path lateralIzquierdaFilePath, Path maleteraFilePath, Path tableroFilePath) {
        clickAndSync(fotosDelVehiculoTab);
        uploadFile(fotosDelVehiculoSerieFotoInput, serieFilePath);
        waitRandomBetween(800);
        clickAndSync(fotosDelVehiculoTab);
        uploadFile(fotosDelVehiculolateralDerechaFotoInput, lateralDerechaFilePath);
        waitRandomBetween(800);
        clickAndSync(fotosDelVehiculoTab);
        uploadFile(fotosDelVehiculolateralIzquierdaFotoInput, lateralIzquierdaFilePath);
        waitRandomBetween(800);
        clickAndSync(fotosDelVehiculoTab);
        uploadFile(fotosDelVehiculoMaleteraFotoInput, maleteraFilePath);
        waitRandomBetween(800);
        clickAndSync(fotosDelVehiculoTab);
        uploadFile(fotosDelVehiculoTableroFotoInput, tableroFilePath);
        waitRandomBetween(800);
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Formulario de fotos del vehículo, sección fotos del vehículo, completado con las fotos de serie, lateral derecha, lateral izquierda, maletera y tablero.");
    }

    public void handleToggleState(Locator locator, boolean desiredState, String toggleName) {
        // Get current state
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        waitForNetworkIdle();
        UiSync.waitForAppIdle(page);
        waitRandomBetween(2500);


        boolean currentState = isToggleChecked(locator);
        String currentStateText = getToggleStateAsText(currentState);
        String desiredStateText = getToggleStateAsText(desiredState);

        log.info("Toggle '{}' estado actual: {}", toggleName, currentStateText);

        // Only click if state needs to change
        if (currentState != desiredState) {
            log.info("Alternando '{}': {} → {}", toggleName, currentStateText, desiredStateText);
            clickAndSync(locator);
            waitForNetworkIdle();
        } else {
            log.info("Toggle '{}' ya seleccionado con el estado deseado: {}", toggleName, desiredStateText);
        }

        // Validate the action was successful
        validateToggleState(locator, desiredState, toggleName);
    }

    private String getToggleStateAsText(boolean state) {
        return state ? "ON" : "OFF";
    }

    public boolean isToggleChecked(Locator toggleButton) {
        String ariaChecked = toggleButton.getAttribute("aria-checked");
        return "true".equals(ariaChecked);
    }

    private void validateToggleState(Locator toggleButton, boolean expectedState, String toggleName) {
        boolean actualState = isToggleChecked(toggleButton);

        if (actualState != expectedState) {
            throw new FrameworkException(
                    String.format("La validacion del Toggle falló por '%s': valor esperado %s pero se obtuvo %s",
                            toggleName, getToggleStateAsText(expectedState), getToggleStateAsText(actualState))
            );
        }

        log.info("Toggle validado satisfactoriamente: '{}' = {}", toggleName, getToggleStateAsText(expectedState));
    }

    public void desactivarBotonRegistrarInspeccion() {
        handleToggleState(toggleEnEvaluacionButton, false, "EN EVALUACION");
    }

    public void processAndAssertSuccessFinalizarRegistroInspeccionButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(finalizarButton, "El botón FINALIZAR DEBE SER VISIBLE para terminar el registro de la inspección");
        ElementAsserts.assertEnabled(finalizarButton, "El botón FINALIZAR DEBE ESTAR HABILITADO para terminar el registro de la inspección");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                finalizarButton::click,               // action (coloca null si la accion click fue realizad)
                resultadoModalMensajeError,
                breadcrumbsInspeccionAutosTitle,
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Registro de inspección finalizado exitosamente, se volvió al listado de inspección de autos.");
    }

    public void processAndAssertSuccessFinalizarRegistroModalConfirmacion(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                finalizarButton::click,               // action (coloca null si la accion click fue realizad)
                resultadoModalMensajeError,
                resultadoModalInfo,
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Modal de confirmación de finalización de registro de inspección apareció exitosamente.");
    }

    public void processAndAssertSuccessFinalizarRegistroModalConfirmacionOKButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(resultadoModalInfo, "El modal de confirmación DEBE SER VISIBLE para finalizar el registro de la inspección");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                OKButtonModalInfo::click,               // action (coloca null si la accion click fue realizad)
                resultadoModalWarning,
                filtroMenuCommon,
                null,       // coloca null si el mensaje del error esta en ventanaErrorDescripcion
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Modal de confirmación de finalización de registro de inspección cerrado exitosamente, se volvió al listado de inspección de autos.");
    }

    public void printFechasNoDisponibles() {
        ElementAsserts.assertVisible(fechaCompletaCanlendarioTitle, "El título de fecha completa del calendario DEBE SER VISIBLE para imprimir las fechas no disponibles");
        sync();
        waitRandomBetween(1000);
        for(int i = 0; i <fechasNoDisponiblesProgramacion.count();i++){
            String fechaNoDisponible = fechasNoDisponiblesProgramacion.nth(i).textContent().trim();
            String horaNoDisponible = fechaNoDisponible.substring(0,5);
            log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Fecha no disponible para programación de inspección: {}", fechaNoDisponible);
            log.info("[INSPECCION AUTOS][DETALLE SOLICITUD] Hora no disponible para programación de inspección: {}", horaNoDisponible);

        }
    }

    public LocalDate getCalendarDateToString() {
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(5000);
        ElementAsserts.assertVisible(fechaCompletaCanlendarioTitle, "El título de fecha completa del calendario DEBE SER VISIBLE para obtener la fecha actual del calendario");
        String displayedFullDate = fechaCompletaCanlendarioTitle.textContent();
        return DateUtils.parseStringFullCalendarSpanishDateToLocalDate(displayedFullDate);
    }
    /**
     * Reads all existing events from the calendar DOM.
     * Returns them as unavailable timeslots.
     */
    public List<TimeSlot> getUnavailableSlots(LocalDate date) {
        List<TimeSlot> unavailableSlots = new ArrayList<>();
        Locator events = page.locator(CALENDAR_EVENTS);

        int eventCount = events.count();
        Pattern pattern = Pattern.compile("(\\d{1,2}:\\d{2})\\s*[-–]\\s*(\\d{1,2}:\\d{2})");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

        for (int i = 0; i < eventCount; i++) {
            String timeText = events.nth(i).locator(EVENT_TIME).textContent().trim();
            Matcher matcher = pattern.matcher(timeText);

            if (matcher.find()) {
                LocalTime startTime = LocalTime.parse(matcher.group(1), timeFormatter);
                LocalTime endTime = LocalTime.parse(matcher.group(2), timeFormatter);

                unavailableSlots.add(new TimeSlot(
                        LocalDateTime.of(date, startTime),
                        LocalDateTime.of(date, endTime)
                ));
            }
        }

        unavailableSlots.sort(Comparator.comparing(TimeSlot::getStart));
        return unavailableSlots;
    }

    /**
     * Calculates available slots by finding gaps between unavailable ones.
     */
    public List<TimeSlot> getAvailableSlots(LocalDate date) {
        List<TimeSlot> unavailable = getUnavailableSlots(date);

        LocalDateTime workStart = LocalDateTime.of(date, WORK_START);
        LocalDateTime workEnd = LocalDateTime.of(date, WORK_END);

        List<TimeSlot> availableSlots = new ArrayList<>();
        LocalDateTime currentTime = workStart;

        for (TimeSlot event : unavailable) {
            if (currentTime.isBefore(event.getStart())) {
                long gapMinutes = Duration.between(currentTime, event.getStart()).toMinutes();
                if (gapMinutes >= SLOT_DURATION_MINUTES) {
                    availableSlots.add(new TimeSlot(
                            currentTime,
                            currentTime.plusMinutes(SLOT_DURATION_MINUTES)
                    ));
                }
            }
            if (event.getEnd().isAfter(currentTime)) {
                currentTime = event.getEnd();
            }
        }

        // Time remaining after the last event
        if (currentTime.isBefore(workEnd)) {
            long gapMinutes = Duration.between(currentTime, workEnd).toMinutes();
            if (gapMinutes >= SLOT_DURATION_MINUTES) {
                availableSlots.add(new TimeSlot(
                        currentTime,
                        currentTime.plusMinutes(SLOT_DURATION_MINUTES)
                ));
            }
        }

        return availableSlots;
    }

    /**
     * Clicks on a specific time slot in the calendar grid.
     */
    public void clickOnTimeSlot(LocalTime time) {
        String dataTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String selector = String.format(SLOT_LANE, dataTime);
        Locator slotLane = page.locator(selector);

        if (slotLane.isVisible()) {
            slotLane.click();
        } else {
            clickOnColumnAtTime(time);
        }
    }

    /**
     * Fallback: clicks at a calculated Y position in the calendar column.
     */
    private void clickOnColumnAtTime(LocalTime time) {
        Locator column = page.locator(CALENDAR_COLUMN);
        var box = column.boundingBox();
        if (box == null) {
            throw new RuntimeException("Calendar column not found or not visible");
        }

        double totalMinutes = Duration.between(WORK_START, WORK_END).toMinutes();
        double targetMinutes = Duration.between(WORK_START, time).toMinutes();
        double ratio = targetMinutes / totalMinutes;

        double clickX = box.x + (box.width / 2);
        double clickY = box.y + (box.height * ratio);

        page.mouse().click(clickX, clickY);
    }

}

