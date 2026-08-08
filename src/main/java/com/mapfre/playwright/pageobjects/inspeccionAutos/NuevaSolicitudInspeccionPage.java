package com.mapfre.playwright.pageobjects.inspeccionAutos;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class NuevaSolicitudInspeccionPage extends BasePage {
    private final Locator title;
    private final Locator agenteSolicitanteInput;
    private final Locator tipoInspeccionInput;
    private final Locator emailInput;
    private final Locator emailCopyInput;
    private final Locator numeroPlacaInput;
    private final Locator numeroSerieInput;
    private final Locator numeroAccesoriosInput;
    private final Locator siguienteButton;
    private final Locator modalErrorGeneral;
    private final Locator datosContratanteSubTitle;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombreContratanteInput;
    private final Locator apellidoPaternoContratanteInput;
    private final Locator apellidoMaternoContratanteInput;
    private final Locator fechaNacimientoDiaSelect;
    private final Locator fechaNacimientoMesSelect;
    private final Locator fechaNacimientoAnioSelect;
    private final Locator estadoCivilSelect;
    private final Locator nacionalidadSelect;
    private final Locator profesionSelect;
    private final Locator sexoMasculinoRadioButton;
    private final Locator telefonoCasaInput;
    private final Locator telefonoOficinaInput;
    private final Locator telefonoMovilInput;
    private final Locator correoElectronicoPersonalInput;
    private final Locator correoElectronicoOficinaInput;
    private final Locator departamentoSelect;
    private final Locator provinciaSelect;
    private final Locator distritoSelect;
    private final Locator direccionViaSelect;
    private final Locator direccionViaInput;
    private final Locator direccionNumeroSelect;
    private final Locator direccionNumeroInput;
    private final Locator datosEmisionFinanciamientoSubTitle;
    private final Locator tipoFinanciamientoSelect;
    private final Locator confirmarSolicitudButton;
    private final Locator modaInspeccionModal;
    private final Locator inspeccionPresencialOption;
    private final Locator confirmarInspeccionOptionButton;
    private final Locator modalNuevaSolicitudInspeccionCreada;
    private final Locator numeroNuevaSolicitudInspeccion;
    private final Locator okNuevaSolicitudInspeccionButton;
    private final Locator inspeccionAutosSolicitudtitle;
    public NuevaSolicitudInspeccionPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Nueva Solicitud de Inspección"));
        this.agenteSolicitanteInput = page.locator("oim-input[formcontrolname='agentRequestName'] input");
        this.tipoInspeccionInput = page.locator("oim-input[formcontrolname='inspectionTypeName'] input");
        this.emailInput = page.locator("oim-input[name='email'] input");
        this.emailCopyInput = page.locator("oim-input[name='emailCopy'] input");
        this.numeroPlacaInput = page.locator("oim-input[name='placa'] input");
        this.numeroSerieInput = page.locator("oim-input[name='serie'] input");
        this.numeroAccesoriosInput = page.locator("input[formcontrolname='numAccesories']");
        this.siguienteButton = page.getByText("Siguiente");
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.datosContratanteSubTitle = page.locator("div.g-sub-title").filter(new Locator.FilterOptions().setHasText("Datos del contratante"));
        this.tipoDocumentoSelect = page.locator("oim-select[name='documentType'] select");
        this.numeroDocumentoInput = page.locator("oim-input[name='documentNumber'] input");
        this.nombreContratanteInput = page.locator("oim-input[formcontrolname='names'] input");
        this.apellidoPaternoContratanteInput = page.locator("oim-input[formcontrolname='lastNameFather'] input");
        this.apellidoMaternoContratanteInput = page.locator("oim-input[formcontrolname='lastNameMother'] input");
        this.fechaNacimientoDiaSelect = page.locator("oim-select[formcontrolname='day'] select");
        this.fechaNacimientoMesSelect = page.locator("oim-select[formcontrolname='month'] select");
        this.fechaNacimientoAnioSelect = page.locator("oim-select[formcontrolname='year'] select");
        this.estadoCivilSelect = page.locator("oim-select[name='statusCivil'] select");
        this.nacionalidadSelect = page.locator("oim-select[formcontrolname='nationality'] select");
        this.profesionSelect = page.locator("oim-select[formcontrolname='profession'] select");
        this.sexoMasculinoRadioButton = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Masculino"));
        this.telefonoCasaInput = page.locator("oim-input[label='Teléfono Casa'] input");
        this.telefonoOficinaInput = page.locator("oim-input[formcontrolname='officePhone'] input");
        this.telefonoMovilInput = page.locator("oim-input[formcontrolname='cellPhone'] input");
        this.correoElectronicoPersonalInput = page.locator("oim-input[formcontrolname='emailContact'] input");
        this.correoElectronicoOficinaInput = page.locator("oim-input[formcontrolname='emailOfficeContact'] input");
        this.departamentoSelect = page.locator("oim-select[formcontrolname='department'] select");
        this.provinciaSelect = page.locator("oim-select[formcontrolname='province'] select");
        this.distritoSelect = page.locator("oim-select[formcontrolname='district'] select");
        this.direccionViaSelect = page.locator("oim-select[formcontrolname='viaAddress'] select");
        this.direccionViaInput = page.locator("oim-input[formcontrolname='viaName'] input");
        this.direccionNumeroSelect= page.locator("oim-select[formcontrolname='numberAddress'] select");
        this.direccionNumeroInput = page.locator("oim-input[formcontrolname='enumeration'] input");
        this.datosEmisionFinanciamientoSubTitle = page.locator("div.g-sub-title").filter(new Locator.FilterOptions().setHasText("Financiamiento"));
        this.tipoFinanciamientoSelect = page.locator("oim-select[formcontrolname='financing'] select");
        this.confirmarSolicitudButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirmar solicitud"));
        this.modaInspeccionModal = page.locator("mat-dialog-container.mdc-dialog--open[role='dialog']");
        this.inspeccionPresencialOption = modaInspeccionModal.locator("div.container-light:has-text('INSPECCIÓN PRESENCIAL')");
        this.confirmarInspeccionOptionButton = modaInspeccionModal.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Confirmar"));
        this.modalNuevaSolicitudInspeccionCreada = page.locator("div.swal2-popup.swal2-icon-success[role='dialog']");
        this.numeroNuevaSolicitudInspeccion = modalNuevaSolicitudInspeccionCreada.locator("#swal2-html-container");
        this.okNuevaSolicitudInspeccionButton = modalNuevaSolicitudInspeccionCreada.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Ok"));
        this.inspeccionAutosSolicitudtitle = page.locator("h1, h2, h3").filter(new Locator.FilterOptions().setHasText("Solicitudes")).first();


    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Nueva Solicitud de Inspección DEBE SER VISIBLE");
        sync();
    }

    public void fillFormInformacionSolicitante() {
        ElementAsserts.assertFilled(agenteSolicitanteInput, "El campo Agente Solicitante debe estar completado por defecto.");
        ElementAsserts.assertFilled(tipoInspeccionInput, "El campo Agente Solicitante debe estar completado por defecto.");

        fillInputNewValueIfEnabledAndSync(emailInput, "EXTLUBA@MAPFRE.COM.PE");
        fillInputNewValueIfEnabledAndSync(emailCopyInput, "EXTLUBA@MAPFRE.COM.PE");
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se completa sección de información del solicitante, email y la copia del email");
    }

    public void fillFormDatosDelVehiculo(String numeroPlaca, String numeroSerie) {
        fillInputIfEmptyAndSync(numeroPlacaInput, numeroPlaca);
        clickAndSync(numeroSerieInput);
        fillInputIfEmptyAndSync(numeroSerieInput, numeroSerie);
        fillInputIfEmptyAndSync(numeroAccesoriosInput, "0");
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se completa sección de datos del vehículo. Placa: {}, Serie: {}", numeroPlaca, numeroSerie);
    }

    public void processAndAssertSuccessSolicitanteVehiculo(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(siguienteButton, "El botón Siguiente no está visible después de completar la sección Solicitante y vehiculo.");
        ElementAsserts.assertEnabled(siguienteButton, "El botón Siguiente no está habilitado después de completar la sección Solicitante y vehiculo.");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                datosContratanteSubTitle,
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
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se muestra sección de Datos del contratante después de hacer click en siguiente: {}", result.outcome());
    }

    public void fillFormDatosDelContratante() {
        sync();
        waitRandomBetween(3000);
        selectOptionIfEnabledAndEmptyAndSync(tipoDocumentoSelect, "DNI");
        sync();
        waitRandomBetween(1000);
        fillInputIfEmptyBlurAndSync(numeroDocumentoInput, "12345678");
        fillInputIfEmptyAndSync(nombreContratanteInput, "JUAN");
        fillInputIfEmptyAndSync(apellidoPaternoContratanteInput, "PEREZ");
        fillInputIfEmptyAndSync(apellidoMaternoContratanteInput, "GOMEZ");
        selectOptionIfEnabledAndEmptyAndSync(fechaNacimientoDiaSelect, "01");
        selectOptionIfEnabledAndEmptyAndSync(fechaNacimientoMesSelect, "01");
        selectOptionIfEnabledAndEmptyAndSync(fechaNacimientoAnioSelect, "1990");
        selectOptionAndSync(estadoCivilSelect, "soltero");
        selectOptionIfEnabledAndEmptyAndSync(nacionalidadSelect, "PERU");
        checkfEnabledAndSync(sexoMasculinoRadioButton);
        verifyCheckboxesIsChecked(getSexoRadioButton("Masculino").locator("input"), "El sexo Masculino no quedó seleccionado.");
        selectOptionIfEnabledAndSync(profesionSelect, "ABOGADO");
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se completa sección de datos del contratante. Documento: DNI 12345678, Nombre: JUAN PEREZ GOMEZ, Fecha de nacimiento: 01/01/1990, Estado civil: soltero, Nacionalidad: PERU, Sexo: Masculino, Profesión: ABOGADO");
    }

    public void fillFormDatosDelContratante(String numeroDocumento, String nombre, String apellidoPaterno, String apellidoMaterno) {
        sync();
        waitRandomBetween(3000);
        selectOptionIfEnabledAndEmptyAndSync(tipoDocumentoSelect, "DNI");
        sync();
        fillInputIfEnabledAndSync(numeroDocumentoInput, numeroDocumento);
        fillInputIfEnabledAndSync(nombreContratanteInput, nombre);
        fillInputIfEnabledAndSync(apellidoPaternoContratanteInput, apellidoPaterno);
        fillInputIfEnabledAndSync(apellidoMaternoContratanteInput, apellidoMaterno);
        String fechaNacimiento = "19/11/1993"; // Ejemplo de fecha de nacimiento
        String[] parts = fechaNacimiento.split("/");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];
        selectOptionIfEnabledAndSync(fechaNacimientoDiaSelect, day);
        selectOptionIfEnabledAndSync(fechaNacimientoMesSelect, month);
        selectOptionIfEnabledAndSync(fechaNacimientoAnioSelect, year);
        selectOptionAndSync(estadoCivilSelect, "soltero");
        selectByOptionIfEnableAndSync(nacionalidadSelect, "PERU");
        checkfEnabledAndSync(sexoMasculinoRadioButton);
        verifyCheckboxesIsChecked(getSexoRadioButton("Masculino").locator("input"), "El sexo Masculino no quedó seleccionado.");
        selectOptionIfEnabledAndSync(profesionSelect, "ABOGADO");
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se completa sección de datos del contratante. Documento: DNI {}, Nombre: {}, Fecha de nacimiento: 19/11/1993, Estado civil: soltero, Nacionalidad: PERU, Sexo: Masculino, Profesión: ABOGADO", numeroDocumento, nombre);
    }

    private Locator getSexoRadioButton(String sexo) {
        return page.locator("mat-radio-button:has-text('" + sexo + "')");
    }

    public void fillFormDatosDeContacto() {
        fillInputIfEmptyAndSync(telefonoCasaInput, "5256688");
        fillInputIfEmptyAndSync(telefonoOficinaInput, "5266688");
        fillInputIfEmptyAndSync(telefonoMovilInput, "912345678");
        fillInputIfEmptyAndSync(correoElectronicoPersonalInput, "EXTLUBA@MAPFRE.COM.PE");
        fillInputIfEmptyAndSync(correoElectronicoOficinaInput, "EXTLUBA@MAPFRE.COM.PE");
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se completa sección de datos de contacto. Teléfono casa: 5256688, Teléfono oficina: 5266688, Teléfono móvil: 912345678, Correo electrónico personal y de oficina");
    }

    public void fillFormDatosDeDireccion() {
        selectByOptionIfEnableAndSync(departamentoSelect, "LIMA");
        selectByOptionIfEnableAndSync(provinciaSelect, "LIMA");
        selectByOptionIfEnableAndSync(distritoSelect, "COMAS");
        selectOptionIfEnabledAndSync(direccionViaSelect, "AA.HH.");
        fillInputValueIfEnabledAndEmptyAndSync(direccionViaInput, "LA MARINA");
        selectOptionIfEnabledAndSync(direccionNumeroSelect, "BLOCK");
        fillInputValueIfEnabledAndEmptyAndSync(direccionNumeroInput, "1234");
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se completa sección de datos de dirección. Dirección: AA.HH. LA MARINA BLOCK 1234, Comas - Lima");
    }

    public void processAndAssertSuccessContratanteContrato(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(siguienteButton, "El botón Siguiente no está visible después de completar la sección Contratante / contacto.");
        ElementAsserts.assertEnabled(siguienteButton, "El botón Siguiente no está habilitado después de completar la sección Contratante / contacto.");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                datosEmisionFinanciamientoSubTitle,
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
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
    }

    public void fillFormDatosDeEmision() {
        selectAndSync(tipoFinanciamientoSelect, "Al contado");
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se completa sección de datos de emisión. Tipo de financiamiento: Al contado");
    }

    public void processAndAssertSuccessDatosEmision(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(siguienteButton, "El botón Siguiente no está visible después de completar la sección Datos de la emision.");
        ElementAsserts.assertEnabled(siguienteButton, "El botón Siguiente no está habilitado después de completar la sección Datos de la emision.");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                confirmarSolicitudButton,
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
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se muestra sección de Confirmar solicitud después de hacer click en siguiente: {}", result.outcome());
    }

    public void processAndAssertSuccessConfirmarSolicitud(int timeoutMs, int quietMs) {
        ElementAsserts.assertVisible(confirmarSolicitudButton, "El botón Confirmar Solicitud no está visible después de completar la sección Confirmar solicitud.");
        ElementAsserts.assertEnabled(confirmarSolicitudButton, "El botón Confirmar Solicitud no está habilitado después de completar la sección Confirmar solicitud.");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                confirmarSolicitudButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                modaInspeccionModal,
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
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se muestra modal de selección de tipo de inspección después de hacer click en Confirmar solicitud: {}", result.outcome());

    }

    public void seleccionarInspeccionPresencialDentroDelModal() {
        ElementAsserts.assertVisible(inspeccionPresencialOption, "La opción de INSPECCIÓN PRESENCIAL no está visible dentro del modal de selección de tipo de inspección.");
        clickAndSync(inspeccionPresencialOption);
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se selecciona opción de INSPECCIÓN PRESENCIAL dentro del modal de selección de tipo de inspección.");
     }

    public void processAndAssertSuccessSeleccionarTipoInspeccion(int timeoutMs, int quietMs) {
        ElementAsserts.assertVisible(modaInspeccionModal, "El modal de selección de tipo de inspección no se mostró después de confirmar la solicitud.");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                confirmarInspeccionOptionButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                modalNuevaSolicitudInspeccionCreada,
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
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se muestra modal de solicitud de inspección creada exitosamente después de seleccionar tipo de inspección y confirmar: {}", result.outcome());
    }

    public String obtenerNumeroSolicitudInspeccion() {
        ElementAsserts.assertVisible(modalNuevaSolicitudInspeccionCreada, "El modal de Nueva Solicitud de Inspección creada no se mostró después de seleccionar el tipo de inspección.");
        return numeroNuevaSolicitudInspeccion.textContent();
    }

    public void processAndAssertSuccessModalNuevaSolicitudInspeccionCreadaOkButton(int timeoutMs, int quietMs) {
        ElementAsserts.assertVisible(modalNuevaSolicitudInspeccionCreada, "El modal NUEVA SOLICITUD CREADA no se mostró después de confirmar la solicitud.");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                okNuevaSolicitudInspeccionButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                inspeccionAutosSolicitudtitle,
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
            throw new FrameworkException("Error de timeout ni SUCCESS or ERROR econtrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INSPECCION AUTOS][NUEVA SOLICITUD] Se muestra página de Detalle de solicitud de inspección después de hacer click en OK del modal de solicitud creada: {}", result.outcome());
    }
}
