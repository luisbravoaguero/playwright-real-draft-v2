package com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.components.material.MaterialDatePicker;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DateUtils;
import com.mapfre.utils.waits.OptionalModalWait;
import com.mapfre.utils.waits.ValueWait;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.time.LocalDate;

public class CotizacionRiesgosGeneralesDatosPolizaPage extends BasePage {
    private final Locator title;
    private final Locator productoSelect;
    private final Locator coberturaSelect;
    private final Locator modalAlerta;
    private final Locator modalAlertaOKButton;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombreDocumentoInput;
    private final Locator corredorInput;
    private final Locator giroNegocioSelect;
    private final Locator agregarTerceroRadioButton;
    private final Locator monedaSelect;
    private final Locator canalSelect;
    private final Locator numeroLocalesInput;
    private final Locator descuentoDirectorInput;
    private final Locator ubicacionRiesgoInput;
    private final Locator departamentoSelect;
    private final Locator provinciaSelect;
    private final Locator distritoSelect;
    private final Locator cantidadUnitariaInput;
    private final Locator calcularButton;
    private final Locator siguienteButton;
    private final Locator datosLocalCard;
    //private final Locator fechaDuracionDesdeInput;
    //private final Locator fechaDuracionHastaInput;
    private final Locator fechaDuracionDesdeInput;
    private final MaterialDatePicker fechaDesdeDatePicker;
    private final MaterialDatePicker fechaHastaDatePicker;
    private final Locator primaTotalItemDato;
    private final Locator tipoProyectoSelect;
    private final Locator direccionRiesgoInput;
    private final Locator nombreObraInput;
    private final Locator montoObraInput;
    public CotizacionRiesgosGeneralesDatosPolizaPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Cotizador de Riesgos generales"));
        this.productoSelect = page.getByLabel("Producto");
        this.coberturaSelect = page.getByLabel("Escoger cobertura");
        this.modalAlerta = page.locator("div.swal2-popup.swal2-modal.swal2-icon-warning");
        this.modalAlertaOKButton = modalAlerta.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("OK"));
        this.tipoDocumentoSelect = page.locator("oim-select[name='TipoDocumento'] select");
        this.numeroDocumentoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Número de documento"));
        this.nombreDocumentoInput = page.locator("oim-input[name='nombreAsegurado'] input");
        this.corredorInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Corredor"));
        this.giroNegocioSelect = page.getByLabel("Giro del negocio");
        this.agregarTerceroRadioButton = page.locator("oim-radio").filter(new Locator.FilterOptions().setHasText("No"));
        //this.emitirPolizaButton = page.locator("label").filter(new Locator.FilterOptions().setHasText(Pattern.compile("emitir", Pattern.CASE_INSENSITIVE)));
        this.monedaSelect = page.getByLabel("Moneda");
        this.canalSelect = page.locator("oim-select[name='canal'] select");
        this.numeroLocalesInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nro. de locales"));
        this.descuentoDirectorInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Agregar Descuentos % Director"));
        this.ubicacionRiesgoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Ubicación del riesgo"));
        this.departamentoSelect = page.getByLabel("Departamento");
        this.provinciaSelect = page.getByLabel("Provincia");
        this.distritoSelect = page.getByLabel("Distrito");
        this.cantidadUnitariaInput = page.getByLabel("", new Page.GetByLabelOptions().setExact(true));
        this.calcularButton = page.getByText("Calcular");
        this.siguienteButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Siguiente"));
        this.datosLocalCard = page.locator("polizas-rrgg-locales li");
        this.fechaDuracionDesdeInput = page.locator("oim-datepicker[name='nFechaInicial'] input");
        //this.fechaDuracionHastaInput = page.locator("oim-datepicker[name='mFechaFinal'] input");
        this.fechaDesdeDatePicker = new MaterialDatePicker(page, page.locator("oim-datepicker[label='Desde']"), "Desde");
        this.fechaHastaDatePicker = new MaterialDatePicker(page, page.locator("oim-datepicker[label='Hasta']"), "Hasta");
        //this.locatortest = page.locator("p.text-md").filter(new Locator.FilterOptions().setHasText("Prima Total")).locator("../..").locator("p.text-md");
        this.primaTotalItemDato = page.locator("div.row:has(b:text('Prima Total'))").locator("div.col-sm-9 p.text-md");
        this.tipoProyectoSelect = page.locator("oim-select[name='tipoProyecto'] select");
        this.direccionRiesgoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Dirección del riesgo"));
        this.nombreObraInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Obra").setExact(true));
        this.montoObraInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Monto de la obra"));
    }
    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Cotizador de Riesgos generales DEBE SER VISIBLE");
    }

    public void fillPasoDatosDeLaPolizaSeccionProducto(String nombreProducto, String nombreCobertura) {
        selectAndSync(productoSelect,nombreProducto);
        waitForOptomalModal(modalAlerta, 5_000, 300, 100, modalAlertaOKButton);
        selectAndSync(coberturaSelect, nombreCobertura);
    }
    public void fillPasoDatosDeLaPolizaSeccionProducto(String nombreProducto) {
        selectAndSync(productoSelect,nombreProducto);
        waitForOptomalModal(modalAlerta, 5_000, 300, 100, modalAlertaOKButton);
    }

    public void fillPasoDatosDeLaPolizaSeccionDatosDeLaPoliza(String numeroRuc, String corredor, String giroNegocio, String asegurarTercero, String tipoMoneda, String numeroLocales, String descuentosDirector) {
        fillAndBlurSync(numeroDocumentoInput, numeroRuc);
        fillAndSync(corredorInput, corredor);
        selectAndSync(giroNegocioSelect, giroNegocio);
        clickAndSync(agregarTerceroRadioButton);
        selectAndSync(monedaSelect, tipoMoneda);
        fillAndBlurSync(numeroLocalesInput, numeroLocales);
        if (!isFieldOptional(descuentosDirector)) {
            selectAndSync(descuentoDirectorInput, descuentosDirector);
        }
    }

    public void fillPasoDatosDeLaPolizaSeccionDatosDeLaPolizaPrimeraParte(String numeroRuc, String corredor, String giroNegocio, String asegurar_tercero) {
        selectAndSync(tipoDocumentoSelect, "RUC");
        fillAndBlurSync(numeroDocumentoInput, numeroRuc);
        fillInputIfEnabledAndSync(nombreDocumentoInput, numeroRuc);
        fillAndSync(corredorInput, corredor);
        selectAndSync(giroNegocioSelect, giroNegocio);
        clickAndSync(agregarTerceroRadioButton);
    }
    public void fillPasoDatosDeLaPolizaSeccionDatosDeLaPolizaPrimeraParte(String numeroRuc, String corredor) {
        selectAndSync(tipoDocumentoSelect, "RUC");
        log.info("[RIESGOS GENERALES][COTIZACION] Seccion Datos de la Poliza - RUC completado");
        fillAndBlurSync(numeroDocumentoInput, numeroRuc);
        log.info("[RIESGOS GENERALES][COTIZACION] Seccion Datos de la Poliza - numero RUC completado");
        fillInputIfEnabledAndSync(nombreDocumentoInput, numeroRuc);
        log.info("[RIESGOS GENERALES][COTIZACION] Seccion Datos de la Poliza - no se introdujo nombre del asegurado, se usó el RUC como nombre");
        fillAndSync(corredorInput, corredor);
        log.info("[RIESGOS GENERALES][COTIZACION] Seccion Datos de la Poliza - corredor completado");
    }

    public void fillPasoDatosDeLaPolizaSeccionDatosDeLaPolizaSegundaParte(String tipoMoneda, String tipoCanal, String numeroLocales, String descuentosDirector) {
        selectAndSync(monedaSelect, tipoMoneda);
        selectAndSync(canalSelect, tipoCanal);
        fillAndBlurSync(numeroLocalesInput, numeroLocales);
        if (!isFieldOptional(descuentosDirector)) {
            selectAndSync(descuentoDirectorInput, descuentosDirector);
        }
    }

    public void fillPasoDatosDeLaPolizaSeccionDatosDeLaPolizaTerceraParte() {
        LocalDate today = LocalDate.now();
        LocalDate oneYearLater = today.plusYears(1);
        String todayStr = DateUtils.formatLocalDateToStringDdMmYyyy(today);
        String oneYearLaterStr = DateUtils.formatLocalDateToStringDdMmYyyy(oneYearLater);
        fechaDesdeDatePicker.seleccionarFecha(todayStr);
        fechaHastaDatePicker.seleccionarFecha(oneYearLaterStr);
        waitRandomBetween(500);
    }

    public void fillPasoDatosDeLaPolizaSeccionDatosDelLocal(String ubicacionRiesgo, String departamento, String provincia, String distrito) {
        ElementAsserts.assertVisible(datosLocalCard.first(), "SECCION Datos del local DEBE SER VISIBLE");
        for(int i = 0; i < datosLocalCard.count(); i++) {
            Locator card = datosLocalCard.nth(i);
            Locator ubiRiesgoInput = card.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Ubicación del riesgo"));
            Locator depaSelect = card.getByLabel("Departamento");
            Locator provSelect = card.getByLabel("Provincia");
            Locator distSelect = card.getByLabel("Distrito");
            fillAndSync(ubiRiesgoInput, ubicacionRiesgo);
            selectAndSync(depaSelect, departamento);
            selectAndSync(provSelect, provincia);
            selectAndSync(distSelect, distrito);
        }
    }

    public void waitForOptomalModal(Locator locator, long timeoutMs, long quietMs, long pollMs, Locator buttonToClick) {
        boolean modalShown = OptionalModalWait.waitForOptionalModal(
                page,
                locator,
                timeoutMs,
                quietMs,
                pollMs
        );

        if (modalShown) {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            clickAndSync(buttonToClick);
        }
    }

    public void fillPasoDatosDeLaPolizaSeccionSumaAsegurada(String cantidadUnitaria) {
        scrollToBottom();
        waitRandomBetween(500);
        fillAndSync(cantidadUnitariaInput, cantidadUnitaria);
    }

    public void processAndAssertSuccessSelecionarBotonCalcularPrima(long timeoutMs, long quietMs) {
        sync();
        waitForNetworkIdle();
        waitRandomBetween(500);
        ElementAsserts.assertVisible(calcularButton, "El boton CALCULAR PRIMA DEBE SER VISIBLE");
        clickAndSync(calcularButton);
        ValueWait.Result result = ValueWait.waitUntilText(
                page,
                primaTotalItemDato,
                text -> {
                    String clean = text.replace("S/", "").trim();
                    return !clean.isEmpty() && Double.parseDouble(clean) > 0.00;
                },
                timeoutMs,  // timeoutMs
                quietMs     // pollMs
        );

        if (!result.success()) {
            ElementAsserts.assertUIMessage(
                    "Importe Prima no superó 0.00 dentro del timeout. Último valor: "
                            + result.lastValue() + " (" + result.elapsedMs() + " ms)");
        }
        scrollToBottom();
        waitRandomBetween(500);
        log.info("[RIESGOS GENERALES][COTIZACION] Importe Prima: {}",result.lastValue());
    }


    public void fillPasoDatosDeLaPolizaSeccionDatosDeLaObraPrimeraParte(String tipoProyecto, String nombreObra) {
        scrollIntoView(tipoProyectoSelect);
        waitRandomBetween(500);
        selectAndSync(tipoProyectoSelect, tipoProyecto);
        fillAndSync(nombreObraInput, nombreObra);
        log.info("[RIESGOS GENERALES][COTIZACION] Seccion Datos de la Obra - primera parte ompletado");
    }

    public void fillPasoDatosDeLaPolizaSeccionDatosDeLaObraSegundaParte(String departamento, String provincia, String distrito, String direccionRiesgo) {
        selectAndSync(departamentoSelect, departamento);
        selectAndSync(provinciaSelect, provincia);
        selectAndSync(distritoSelect, distrito);
        fillAndBlurSync(direccionRiesgoInput, direccionRiesgo);
        log.info("[RIESGOS GENERALES][COTIZACION] Seccion Datos de la Obra - segunda parte completado");
    }

    public void fillPasoDatosDeLaPolizaSeccionDatosDeLaObraTerceraParte(String tipoMoneda, String tipoCanal, String montoManoObra) {
        selectAndSync(monedaSelect, tipoMoneda);
        selectAndSync(canalSelect, tipoCanal);
        fillAndBlurSync(montoObraInput, montoManoObra);
        log.info("[RIESGOS GENERALES][COTIZACION] Seccion Datos de la Obra - tercera parte completado");
    }
}
