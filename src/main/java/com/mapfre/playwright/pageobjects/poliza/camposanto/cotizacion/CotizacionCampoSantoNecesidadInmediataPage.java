package com.mapfre.playwright.pageobjects.poliza.camposanto.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.JSHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CotizacionCampoSantoNecesidadInmediataPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(CotizacionCampoSantoNecesidadInmediataPage.class);

    // Encabezado
    private final Locator lblRamoNecesidadInmediata;

    // Datos producto
    private final Locator cmbCamposanto;
    private final Locator cmbTipoContrato;
    private final Locator cmbModalidad;
    private final Locator cmbProducto;

    // Datos cliente
    private final Locator cmbTipoDocumento;
    private final Locator txtNumeroDocumento;

    private final Locator txtNombres;
    private final Locator txtApellidoPaterno;
    private final Locator txtApellidoMaterno;

    private final Locator cmbDia;
    private final Locator cmbMes;
    private final Locator cmbAnio;

    private final Locator cmbEstadoCivil;

    private final Locator txtCelular;
    private final Locator txtCorreo;

    private final Locator cmbDepartamento;
    private final Locator cmbProvincia;
    private final Locator cmbDistrito;

    private final Locator txtDireccion;

    // Acciones
    private final Locator btnGenerarCotizacion;
    private final Locator btnAceptarModal;
    private final Locator btnokcotizacioncreada;

    // Resultado cotización
    private final Locator lblResultadoCotizacion;
    private final Locator lblDetalleCotizacionNecesidadInmediata;
    private final Locator lblNroCotizacion;
    private final Locator btnIrBandeja;


    public CotizacionCampoSantoNecesidadInmediataPage(Page page) {
        super(page);

        this.lblRamoNecesidadInmediata = page.locator("text=Ramo: Necesidad Inmediata");

        this.cmbCamposanto = selectByLabel("Camposanto");
        this.cmbTipoContrato = selectByLabel("Tipo Contrato");
        this.cmbModalidad = selectByLabel("Modalidad");
        this.cmbProducto = selectByLabel("Producto");

        this.cmbTipoDocumento = selectByLabel("Tipo de documento");
        this.cmbEstadoCivil = selectByLabel("Estado civil");

        this.txtNumeroDocumento = page.locator("input[name='documentNumber']");
        this.txtNombres = page.locator("input[name='Nombre']");
        this.txtApellidoPaterno = page.locator("input[name='ApellidoPaterno']");
        this.txtApellidoMaterno = page.locator("input[name='ApellidoMaterno']");

        this.cmbDia = selectByOptionText("Día");
        this.cmbMes = selectByOptionText("Mes");
        this.cmbAnio = selectByOptionText("Año");

        this.txtCelular = page.locator("input[name='Telefono2']");
        this.txtCorreo = page.locator("input[name='CorreoElectronico']");

        this.cmbDepartamento = selectByLabel("Departamento");
        this.cmbProvincia = selectByLabel("Provincia");
        this.cmbDistrito = selectByLabel("Distrito");

        this.txtDireccion = page.locator("input[name='Direccion']");

        this.btnGenerarCotizacion = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(" GENERAR COTIZACIÓN ")
        );

        this.btnAceptarModal = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("OK")
        );

        this.btnokcotizacioncreada = page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Ok"));

        this.lblResultadoCotizacion = page.locator("text=Resultado de la cotización");

        this.lblDetalleCotizacionNecesidadInmediata = page.locator(
                "text=Detalle de cotización : Necesidad Inmediata"
        );

        this.lblNroCotizacion = page.locator("h2")
                .filter(new Locator.FilterOptions().setHasText("Nro cotizacion:"))
                .first();

        this.btnIrBandeja = page.locator("a.g-button")
                .filter(new Locator.FilterOptions().setHasText("Ir a bandeja"));

    }

    private Locator selectByLabel(String label) {
        return page.locator("label:has-text('" + label + "')")
                .locator("xpath=following::select[1]");
    }

    private Locator selectByOptionText(String optionText) {
        return page.locator("select")
                .filter(new Locator.FilterOptions().setHasText(optionText))
                .first();
    }

    public void assertLoadedNecesidadInmedita() {
        UiSync.waitForAppIdle();

        ElementAsserts.assertVisible(
                lblRamoNecesidadInmediata,
                "Debe mostrarse el título de Cotizador Camposanto"
        );

        ElementAsserts.assertTextContains(
                lblRamoNecesidadInmediata,
                "Necesidad Inmediata",
                "El ramo seleccionado no es correcto"
        );
    }

    // ================= PRODUCTO =================
    public void ingresarDatosProducto(String camposanto, String tipoContrato,
                                      String modalidad, String producto) {

        UiSync.waitForAppIdle();

        try {
            log.info("=== SECCION DATOS PRODUCTO ===");

            seleccionarOpcionPorTexto(cmbCamposanto, "Camposanto", camposanto);
            seleccionarOpcionPorTexto(cmbTipoContrato, "Tipo Contrato", tipoContrato);
            seleccionarOpcionPorTexto(cmbModalidad, "Modalidad", modalidad);
            seleccionarOpcionPorTexto(cmbProducto, "Producto", producto);

            log.info("[PRODUCTO] Las opciones se seleccionaron correctamente");

        } catch (Exception e) {
            throw new AssertExceptions("Error en datos producto", e);
        }
    }

    // ================= CLIENTE =================
    public void ingresarDatosCliente(
            String tipoDoc,String numeroDoc,String nombre,String paterno,String materno,String dia,
            String mes, String anio, String estadoCivil,String telMovil,   String correo,String departamento,
            String provincia, String distrito, String direccion) {

        UiSync.waitForAppIdle();

        try {
            log.info("=== SECCION DATOS CLIENTE ===");

            seleccionarOpcionPorTexto(cmbTipoDocumento, "Tipo Documento", tipoDoc);

            log.info("[CLIENTE] Ingresando número de documento: {}", numeroDoc);
            txtNumeroDocumento.fill(numeroDoc);
            txtNumeroDocumento.press("Tab");

            UiSync.waitForAppIdle();

            boolean esAutocompletado =
                    tieneValor(txtNombres) &&
                            tieneValor(txtApellidoPaterno) &&
                            tieneValor(txtApellidoMaterno);

            if (esAutocompletado) {
                log.info("[CLIENTE] Autocompletado detectado");
                completarCamposFaltantes(estadoCivil, telMovil);

            } else {
                log.info("[CLIENTE] Iniciando el Ingreso manual");

                ingresarManual(nombre,paterno,materno,dia,mes,anio,
                        estadoCivil,telMovil,correo,departamento,provincia, distrito,direccion          );
            }

            log.info("[CLIENTE] Datos cliente procesados correctamente");

        } catch (Exception e) {
            throw new AssertExceptions("Error en los datos cliente", e);
        }
    }

    private void completarCamposFaltantes(String estadoCivil, String celular) {

        seleccionarEstadoCivil(estadoCivil);

        if (!tieneValor(txtCelular)) {
            log.info("[CLIENTE] Celular vacío. Ingresando celular: {}", celular);
            txtCelular.fill(celular);
            txtCelular.press("Tab");
            UiSync.waitForAppIdle();
        }
    }

    private void seleccionarEstadoCivil(String estadoCivil) {
        String valorActual = cmbEstadoCivil.inputValue();

        if (valorActual == null || valorActual.contains("1: Object")) {
            seleccionarOpcionPorTexto(cmbEstadoCivil, "Estado Civil**", estadoCivil);
        }
    }

    private void ingresarManual(String nombre, String paterno,   String materno,
            String dia,String mes,String anio,String estadoCivil,String celular,String correo,
            String departamento,String provincia,String distrito,String direccion)
    {

        log.info("[CLIENTE] Ingresando nombres y apellidos");

        txtNombres.fill(nombre);
        txtApellidoPaterno.fill(paterno);
        txtApellidoMaterno.fill(materno);

        seleccionarOpcionPorTexto(cmbDia, "Día", dia);
        seleccionarOpcionPorTexto(cmbMes, "Mes", mes);
        seleccionarOpcionPorTexto(cmbAnio, "Año", anio);

        seleccionarOpcionPorTexto(cmbEstadoCivil, "Estado Civil", estadoCivil);

        log.info("[CLIENTE] Ingresando datos de contacto");
        txtCelular.fill(celular);
        txtCorreo.fill(correo);

        seleccionarOpcionPorTexto(cmbDepartamento, "Departamento", departamento);
        seleccionarOpcionPorTexto(cmbProvincia, "Provincia", provincia);
        seleccionarOpcionPorTexto(cmbDistrito, "Distrito", distrito);

        txtDireccion.fill(direccion);
        UiSync.waitForAppIdle();

    }
    // ================= HELPERS =================
    private void seleccionarOpcionPorTexto(Locator select, String campo, String textoVisible) {
        try {
            esperarOpciones(select);

            Locator opcion = select.locator("option")
                    .filter(new Locator.FilterOptions().setHasText(textoVisible))
                    .first();

            opcion.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.ATTACHED));

            String value = opcion.getAttribute("value");

            if (value == null || value.isBlank()) {
                throw new AssertExceptions("No se encontró value para " + campo + ": " + textoVisible);
            }

            select.selectOption(value);
            UiSync.waitForAppIdle();
            //log.info("[{}] Opción Seleccionada: {}", campo, textoVisible);

        } catch (Exception e) {
            throw new AssertExceptions("Error seleccionando " + campo + ": " + textoVisible, e);
        }
    }

    private void esperarOpciones(Locator select) {
        select.waitFor();

        select.locator("option").nth(1).waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.ATTACHED)
        );
    }

    private boolean tieneValor(Locator campo) {
        return campo.inputValue() != null && !campo.inputValue().trim().isEmpty();
    }

    // ================= ACCIONES =================

    public void clickGenerarCotizacion() {
        page.keyboard().press("Tab");
        UiSync.waitForAppIdle();
        btnGenerarCotizacion.scrollIntoViewIfNeeded();
        btnGenerarCotizacion.click(new Locator.ClickOptions().setForce(true));
        UiSync.waitForAppIdle();
        log.info("[COTIZACION] Click en Generar cotización realizado");
    }

    public void aceptarModal() {
        btnAceptarModal.waitFor();
        btnAceptarModal.click();
        UiSync.waitForAppIdle();
        log.info("[MODAL] Click en OK realizado correctamente");
    }

    public void aceptarModalCotizacionCreada(){
        btnokcotizacioncreada.waitFor();
        btnokcotizacioncreada.click();
        UiSync.waitForAppIdle();
        log.info("[MODAL][COTIZACION CREADA] click en boton OK realizado correctamente");
    }

    public void assertResultadoCotizacionLoaded() {
        UiSync.waitForAppIdle();

        try {
            log.info("[RESULTADO COTIZACION] Validando pantalla de detalle de cotización");

            ElementAsserts.assertVisible(
                    lblDetalleCotizacionNecesidadInmediata,
                    "Debe mostrarse el título 'Detalle de cotización : Necesidad Inmediata'"
            );

            ElementAsserts.assertVisible(
                    lblNroCotizacion,
                    "Debe mostrarse el número de cotización"
            );

            String textoNroCotizacion = obtenerNumeroCotizacion();

            String nroCotizacion = textoNroCotizacion
                    .replace("Nro cotizacion:", "")
                    .replaceAll("\\s+", "")
                    .trim();

            if (nroCotizacion.isBlank()) {
                throw new AssertExceptions("El número de cotización se muestra vacío");
            }

            log.info("[RESULTADO COTIZACION] Número de cotización generado: {}", nroCotizacion);

            ElementAsserts.assertVisible(
                    btnIrBandeja,
                    "Debe mostrarse el botón 'Ir a bandeja'"
            );

            log.info("[RESULTADO COTIZACION] Pantalla validada correctamente");

        } catch (Exception e) {
            log.error("[RESULTADO COTIZACION] Error validando pantalla", e);
            throw new AssertExceptions("Error validando pantalla de detalle de cotización", e);
        }
    }

    private String obtenerNumeroCotizacion() {
        try {
            JSHandle handle = page.waitForFunction(
                    """
                    () => {
                        const h2s = Array.from(document.querySelectorAll('h2'));
    
                        const h2 = h2s.find(elemento =>
                            (elemento.textContent || '').includes('Nro cotizacion:')
                        );
    
                        if (!h2) {
                            return false;
                        }
    
                        const texto = (h2.textContent || '').replace(/\\s+/g, ' ').trim();
                        const numero = texto.replace(/\\D+/g, '');
    
                        return numero.length > 0 ? numero : false;
                    }
                    """,
                    null,
                    new Page.WaitForFunctionOptions().setTimeout(30000)
            );

            String nroCotizacion = String.valueOf(handle.jsonValue()).trim();

            if (nroCotizacion.isBlank() || "false".equalsIgnoreCase(nroCotizacion)) {
                throw new AssertExceptions("El número de cotización se muestra vacío");
            }

            return nroCotizacion;

        } catch (Exception e) {
            Object h2Debug = page.evaluate(
                    """
                    () => Array.from(document.querySelectorAll('h2'))
                        .map(h2 => h2.textContent)
                        .join(' | ')
                    """
            );

            throw new AssertExceptions(
                    "No se pudo obtener el número de cotización. H2 encontrados: " + h2Debug,
                    e
            );
        }
    }

    public void clickIrBandeja() {
        try {
            log.info("[RESULTADO COTIZACION] Haciendo clic en botón 'Ir a bandeja'");

            btnIrBandeja.waitFor();
            btnIrBandeja.scrollIntoViewIfNeeded();
            btnIrBandeja.click();
            UiSync.waitForAppIdle();
            log.info("[RESULTADO COTIZACION] Click en 'Ir a bandeja' realizado correctamente");

        } catch (Exception e) {
            log.error("[RESULTADO COTIZACION] Error haciendo clic en 'Ir a bandeja'", e);
            throw new AssertExceptions("Error haciendo clic en botón Ir a bandeja", e);
        }
    }

    public String obtenerNumeroCotizacionGenerado() {

        String nroCotizacion = obtenerNumeroCotizacion();

        if (nroCotizacion == null || nroCotizacion.isBlank() || "false".equalsIgnoreCase(nroCotizacion)) {
            throw new AssertExceptions("No se pudo obtener el número de cotización");
        }

        log.info("[RESULTADO COTIZACION] Número de cotización obtenido: {}", nroCotizacion);

        return nroCotizacion;
    }

}