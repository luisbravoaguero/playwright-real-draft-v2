package com.mapfre.playwright.pageobjects.poliza.auto.emsion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DateUtils;
import com.mapfre.utils.validators.RadioGroupValidator;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class EmisionAutoUsadoPage extends BasePage {
    private final Locator title;
    private final Locator numeroPlacaInspeccionInput;
    private final Locator buscarButton;
    private final Locator resultadoNoEncontradoBusqueda;
    private final Locator resultadoListaBusqueda;
    private final Locator numeroInspeccionLabel;
    private final Locator numeroPlacaLabel;
    private final Locator estadoPlacaLabel;
    private final Locator siguienteButton;
    private final Locator elegirProductoAEmitirLabel;
    private final Locator productoSelect;
    private final Locator tipoUsoSelect;
    private final Locator vigenciaPolizaInput;
    private final Locator modalErrorGeneral;
    private final Locator datosContratanteLabel;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombreInput;
    private final Locator apellidoPaternoInput;
    private final Locator apellidoMaternoInput;
    private final Locator diaSelect;
    private final Locator mesSelect;
    private final Locator anioSelect;
    private final RadioGroupValidator sexoValidator;
    private final RadioGroupValidator radioGroupUnicoConductorValidator;
    private final RadioGroupValidator radioGarajeValidator;
    private final Locator profesionSelect;
    private final Locator telefonoCasaInput;
    private final Locator telefonoMovilInput;
    private final Locator correoElectronicoInput;
    private final Locator departamentoSelect;
    private final Locator provinciaSelect;
    private final Locator distritoSelect;
    private final Locator viaSelect;
    private final Locator nombreViaInput;
    private final Locator tipoNumeroSelect;
    private final Locator enumeracionInput;
    private final Locator correspondenciaFlagCheckbox;
    private final Locator aseguradoFlagCheckbox;
    private final Locator frecuenciaUsoSelect;
    private final Locator accidentesVehiculoSelect;
    private final Locator antiguedadLicenciaSelect;
    private final Locator nUnicoConductorRadioButton;
    private final Locator nGarajeRadioButton;
    private final Locator tipoFinanciamientoSelect;
    private final Locator calcularPrimaButton;
    private final Locator financiamientoLabel;
    private final Locator totalPrimaLabel;
    private final Locator emitirPolizaButton;
    private final Locator modalInfoGeneral;
    private final Locator exitoEmisionModalInfoLabel;
    private final Locator OKEmisionModalInfoButton;
    public EmisionAutoUsadoPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Emisión póliza auto usado"));
        this.numeroPlacaInspeccionInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Ingresa Nro. de placa o Nro."));
        this.buscarButton = page.getByText("Buscar");
        this.resultadoNoEncontradoBusqueda = page.getByText("No se encontro información");
        this.resultadoListaBusqueda = page.locator("div.g-myd-result");
        this.numeroInspeccionLabel = page.locator("li.cnt-item:has(div.item-label:has-text('Nro. Inspección:')) div.item-dato");
        this.numeroPlacaLabel = page.locator("li.cnt-item:has(div.item-label:has-text('Nro. de placa:')) div.item-dato");
        this.estadoPlacaLabel = page.locator("li.cnt-item:has(div.item-label:has-text('Estado:')) div.item-dato");
        this.siguienteButton = page.getByText("Siguiente");
        this.elegirProductoAEmitirLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Elige el producto a emitir").setLevel(2));
        this.productoSelect = page.getByLabel("Producto");
        this.tipoUsoSelect = page.getByLabel("Tipo de uso");
        this.vigenciaPolizaInput = page.locator("oim-datepicker[name='mInicioVigencia'] input");
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.datosContratanteLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Datos del Contratante").setLevel(2));
        this.tipoDocumentoSelect = page.locator("oim-select[name='documentType'] select");
        this.numeroDocumentoInput = page.locator("oim-input[name='documentNumber'] input");
        this.nombreInput = page.locator("oim-input[name='Nombre'] input");
        this.apellidoPaternoInput = page.locator("oim-input[name='ApellidoPaterno'] input");
        this.apellidoMaternoInput = page.locator("oim-input[name='ApellidoMaterno'] input");
        this.diaSelect = page.locator("oim-select[name='day'] select");
        this.mesSelect = page.locator("oim-select[name='month'] select");
        this.anioSelect = page.locator("oim-select[name='year'] select");
        this.sexoValidator = new RadioGroupValidator(page, "Sexo");
        this.radioGroupUnicoConductorValidator = new RadioGroupValidator(page, "¿El auto es conducido por una sola persona?");
        this.radioGarajeValidator = new RadioGroupValidator(page, "¿Usualmente guarda el auto en un garaje?");
        this.profesionSelect = page.getByLabel("Profesión");
        this.telefonoCasaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono Casa"));
        this.telefonoMovilInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Teléfono móvil"));
        this.correoElectronicoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Correo electrónico"));
        this.departamentoSelect = page.getByLabel("Departamento");
        this.provinciaSelect = page.getByLabel("Provincia");
        this.distritoSelect = page.getByLabel("Distrito");
        this.viaSelect = page.locator("oim-select[name='Via'] select");
        this.nombreViaInput = page.locator("oim-input[name='NombreVia'] input");
        this.tipoNumeroSelect = page.locator("oim-select[name='NumberType'] select");
        this.enumeracionInput = page.locator("oim-input[name='TextoNumero'] input");
        this.correspondenciaFlagCheckbox = page.locator("oim-checkbox[name='correspondenciaFlag'] input");
        this.aseguradoFlagCheckbox = page.locator("oim-checkbox[name='aseguradoFlag'] input");
        this.frecuenciaUsoSelect = page.locator("oim-select[name='nUseVehicle'] select");
        this.accidentesVehiculoSelect = page.locator("oim-select[name='nAccidentesVehicle'] select");
        this.antiguedadLicenciaSelect = page.locator("oim-select[name='nAntiguedadLicencia'] select");
        this.nUnicoConductorRadioButton = page.locator("oim-radio[name='nUnicoConductor']:has-text('Sí') mat-radio-button");
        this.nGarajeRadioButton = page.locator("oim-radio[name='nGaraje']:has-text('Sí') mat-radio-button");
        this.tipoFinanciamientoSelect = page.locator("oim-select[name='nTipoFinanciamiento'] select");
        this.calcularPrimaButton = page.getByText("Calcular prima");
        this.financiamientoLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Financiamiento").setLevel(2));
        this.totalPrimaLabel = page.locator("div.total-label ~ div.g-text-right-xs");
        this.emitirPolizaButton = page.getByText("Emitir poliza");
        this.modalInfoGeneral = page.locator("div.swal2-popup.swal2-icon-info[role='dialog']");
        this.exitoEmisionModalInfoLabel = modalInfoGeneral.getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setName(Pattern.compile("¡?\\s*exito!?\\s*$", Pattern.CASE_INSENSITIVE)));
        this.OKEmisionModalInfoButton = modalInfoGeneral.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName(Pattern.compile("^ok$", Pattern.CASE_INSENSITIVE)));
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Emisión póliza auto usado DEBE SER VISIBLE");
    }

    public void ingresarNumeroPlacaInspeccion(String numeroPlaca) {
        fillAndBlurSync(numeroPlacaInspeccionInput, numeroPlaca);
        log.info("[EMISION][AUTO USADO] Se ingresó el número de placa {}",numeroPlaca);
    }

    public void processAndAssertSuccessBusquedaNumeroPlacaParaEmisionAutoUsado(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(buscarButton, "El botón Buscar DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                buscarButton::click,               // action (coloca null si la accion click fue realizad)
                resultadoNoEncontradoBusqueda,
                resultadoListaBusqueda,
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
        log.info("[EMISION][AUTO USADO] Se encontró información relacionada a la placa ingresada, se muestra el resultado de la búsqueda.");
    }

    public void validarNumeroPlacaResultadoBusqueda(String numeroPlaca) {
        ElementAsserts.assertVisible(resultadoListaBusqueda, "El resultado de la búsqueda DEBE SER VISIBLE después de buscar por número de placa");
        ElementAsserts.assertContainsText(numeroPlacaLabel, numeroPlaca, "El resultado de la búsqueda DEBE contener el número de placa buscado");
        ElementAsserts.assertFilled(numeroInspeccionLabel, "El resultado de la búsqueda DEBE mostrar el número de inspección asociado a la placa");
        ElementAsserts.assertContainsText(estadoPlacaLabel, "TERMINADA", "El resultado de la búsqueda DEBE mostrar que el estado de la placa es Aprobado para poder emitir la póliza");
        log.info("[EMISION][AUTO USADO] Validación exitosa: El resultado de la búsqueda muestra el número de placa y número de inspección correctamente, y el estado de la placa es TERMINADA");
        log.info("[EMISION][AUTO USADO] Número de placa validado es {} y el numero de inspeccion es {}",numeroPlacaLabel.innerText(), numeroInspeccionLabel.innerText());
    }

    public void processAndAssertSuccessSeleccionarInspeccionSiguienteButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(siguienteButton, "El botón Siguiente DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                elegirProductoAEmitirLabel,
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
        log.info("[EMISION][AUTO USADO] Se hizo click en el botón Siguiente y se cargó correctamente la sección para elegir el producto a emitir");
    }

    public void assertLoadedElegirProductoEmitirLabel() {
        ElementAsserts.assertVisible(elegirProductoAEmitirLabel, "TITULO Elige el producto a emitir DEBE SER VISIBLE para continuar con la emisión de la póliza");
    }

    public void fillFormDatosPoliza(String producto, String tipoUso) {
        selectAndSync(productoSelect, producto);
        selectAndSync(tipoUsoSelect, tipoUso);
        LocalDate fechaVigencia = LocalDate.now();
        String fechaVigenciaString = DateUtils.formatLocalDateToStringDdMmYyyy(fechaVigencia);
        fillReadonlyDateIfEmpty(vigenciaPolizaInput, fechaVigenciaString);
        log.info("[EMISION][AUTO USADO] Se llenó el formulario de datos de la póliza con producto: {}, tipo de uso: {} y fecha de vigencia: {}", producto, tipoUso, fechaVigenciaString);
    }


    public void processAndAssertSuccessDatosDeLaPolizaSiguienteButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(siguienteButton, "El botón Siguiente DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                datosContratanteLabel,
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
        log.info("[EMISION][AUTO USADO] Se hizo click en el botón Siguiente y se cargó correctamente la sección de datos del contratante");
    }

    public void assertLoadedDatosContratanteLabel() {
        ElementAsserts.assertVisible(datosContratanteLabel, "TITULO Datos del Contratante DEBE SER VISIBLE para continuar con la emisión de la póliza");
    }

    public void fillFormDatosContratante() {
        selectOptionIfEnabledAndSync(tipoDocumentoSelect, "DNI");
        fillInputIfEnabledBlurAndSync(numeroDocumentoInput, "85634155");
        fillInputIfEnabledBlurAndSync(nombreInput, "Juan");
        fillInputIfEnabledBlurAndSync(apellidoPaternoInput, "Perez");
        fillInputIfEnabledBlurAndSync(apellidoMaternoInput, "Gomez");
        selectOptionIfEnabledAndEmptyAndSync(diaSelect, "15");
        selectOptionIfEnabledAndEmptyAndSync(mesSelect, "06");
        selectOptionIfEnabledAndEmptyAndSync(anioSelect, "1995");
        sexoValidator.assertAtLeastOneRadioIsSelected();
        selectOptionAndSync(profesionSelect, "ABOGADO");
        fillInputIfEnabledBlurAndSync(telefonoCasaInput, "012345678");
        fillInputIfEnabledBlurAndSync(telefonoMovilInput, "987654321");
        fillInputIfEnabledBlurAndSync(correoElectronicoInput, "EXTLUBA@MAPFRE.COM.PE");
        selectOptionAndSync(departamentoSelect, "LIMA");
        selectOptionAndSync(provinciaSelect, "LIMA");
        selectOptionAndSync(distritoSelect, "COMAS");
        selectOptionAndSync(viaSelect, "AA.HH.");
        fillInputIfEnabledBlurAndSync(nombreViaInput, "Los Alamos 123");
        selectOptionAndSync(tipoNumeroSelect, "BLOCK");
        fillInputIfEnabledBlurAndSync(enumeracionInput, "123");
        clickAndVerifyCheckboxIfNotChecked(correspondenciaFlagCheckbox, "correspondenciaFlag");
        clickAndVerifyCheckboxIfNotChecked(aseguradoFlagCheckbox, "aseguradoFlag");
        selectOptionAndSync(frecuenciaUsoSelect, "TODOS LOS DIAS");
        selectOptionAndSync(accidentesVehiculoSelect, "NINGUN EVENTO");
        selectIndexOptionIfEnabledAndSync(antiguedadLicenciaSelect, 2);
        clickAndSync(nUnicoConductorRadioButton);
        radioGroupUnicoConductorValidator.assertAtLeastOneRadioIsSelected();
        clickAndSync(nGarajeRadioButton);
        radioGarajeValidator.assertAtLeastOneRadioIsSelected();
        log.info("[EMISION][AUTO USADO] Se llenó el formulario de datos del contratante con la información del contratante y datos del vehículo");
    }

    public void fillFormDatosContratante(String numeroDocumento, String nombre, String apellidoPaterno, String apellidoMaterno) {
        sync();
        waitRandomBetween(3000);
        selectOptionIfEnabledAndSync(tipoDocumentoSelect, "DNI");
        fillInputIfEnabledAndSync(numeroDocumentoInput, numeroDocumento);
        sync();
        waitRandomBetween(1000);
        fillInputIfEmptyBlurAndSync(nombreInput, nombre);
        fillInputIfEmptyBlurAndSync(apellidoPaternoInput, apellidoPaterno);
        fillInputIfEmptyBlurAndSync(apellidoMaternoInput, apellidoMaterno);
        selectByOptionIfEnableAndSync(diaSelect, "19");
        selectByOptionIfEnableAndSync(mesSelect, "11");
        selectByOptionIfEnableAndSync(anioSelect, "1993");
        sexoValidator.assertAtLeastOneRadioIsSelected();
        selectOptionAndSync(profesionSelect, "ABOGADO");
        fillInputIfEnabledBlurAndSync(telefonoCasaInput, "012345678");
        fillInputIfEnabledBlurAndSync(telefonoMovilInput, "987654321");
        fillInputIfEnabledBlurAndSync(correoElectronicoInput, "EXTLUBA@MAPFRE.COM.PE");
        selectOptionAndSync(departamentoSelect, "LIMA");
        selectOptionAndSync(provinciaSelect, "LIMA");
        selectOptionAndSync(distritoSelect, "COMAS");
        selectOptionAndSync(viaSelect, "AA.HH.");
        fillInputIfEnabledBlurAndSync(nombreViaInput, "Los Alamos 123");
        selectOptionAndSync(tipoNumeroSelect, "BLOCK");
        fillInputIfEnabledBlurAndSync(enumeracionInput, "123");
        clickAndVerifyCheckboxIfNotChecked(correspondenciaFlagCheckbox, "correspondenciaFlag");
        clickAndVerifyCheckboxIfNotChecked(aseguradoFlagCheckbox, "aseguradoFlag");
        selectOptionAndSync(frecuenciaUsoSelect, "TODOS LOS DIAS");
        selectOptionAndSync(accidentesVehiculoSelect, "NINGUN EVENTO");
        selectIndexOptionIfEnabledAndSync(antiguedadLicenciaSelect, 2);
        clickAndSync(nUnicoConductorRadioButton);
        radioGroupUnicoConductorValidator.assertAtLeastOneRadioIsSelected();
        clickAndSync(nGarajeRadioButton);
        radioGarajeValidator.assertAtLeastOneRadioIsSelected();
        log.info("[EMISION][AUTO USADO] Se llenó el formulario de datos del contratante con la información del contratante y datos del vehículo");
    }

    public void processAndAssertSuccessDatosDelContranteSiguienteButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(siguienteButton, "El botón Siguiente DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                financiamientoLabel,
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
        log.info("[EMISION][AUTO USADO] Se hizo click en el botón Siguiente y se cargó correctamente la sección de financiamiento, se completó exitosamente el proceso de emisión de la póliza");
    }

    public void assertLoadedFinanciamientoLabel() {
        ElementAsserts.assertVisible(financiamientoLabel, "TITULO Financiamiento DEBE SER VISIBLE para continuar con la emisión de la póliza");
    }

    public void fillFormFinanciamiento() {
        selectOptionAndSync(tipoFinanciamientoSelect, "AL CONTADO");
        log.info("[EMISION][AUTO USADO] Se seleccionó el tipo de financiamiento: AL CONTADO");
    }

    public void processAndAssertSuccessFinanciamientoCalcularPrimaButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(calcularPrimaButton, "El botón Siguiente DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                calcularPrimaButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                totalPrimaLabel,
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

    public void validarTotalPrimaCalculadaContieneUnValor() {
        ElementAsserts.assertFilled(totalPrimaLabel, "El total de la prima calculada DEBE mostrar un valor después de hacer click en Calcular Prima");
        log.info("[EMISION][AUTO USADO] Validación exitosa: El total de la prima calculada muestra un valor después de hacer click en Calcular Prima");
    }

    public void processAndAssertSuccessFinanciamientoSiguienteButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(siguienteButton, "El botón Siguiente DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                emitirPolizaButton,
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
        log.info("[EMISION][AUTO USADO] Se hizo click en el botón Siguiente y se cargó correctamente la sección final para emitir la póliza, se muestra el botón Emitir póliza");
    }

    public void processAndAssertSuccessEmitirPolizaButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(emitirPolizaButton, "El botón Emitir Poliza DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                emitirPolizaButton::click,               // action (coloca null si la accion click fue realizad)
                modalErrorGeneral,
                modalInfoGeneral,
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
        log.info("[EMISION][AUTO USADO] Se hizo click en el botón Emitir póliza y se visualiza el modal de información general");
    }

    public void validarExitoEmisionDentroModalInfo() {
        modalInfoGeneral.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        ElementAsserts.assertVisible(exitoEmisionModalInfoLabel, "El mensaje de éxito de emisión DEBE SER VISIBLE dentro del modal de información general");
        log.info("[EMISION][AUTO USADO] Validación exitosa: El mensaje de éxito de emisión es visible dentro del modal de información general y contiene la palabra éxito");
        ElementAsserts.assertVisible(OKEmisionModalInfoButton, "El botón OK DEBE SER VISIBLE dentro del modal de información general");
        log.info("[EMISION][AUTO USADO] Validación exitosa: El botón OK es visible dentro del modal de información general de éxito de la Emision");
    }
}
