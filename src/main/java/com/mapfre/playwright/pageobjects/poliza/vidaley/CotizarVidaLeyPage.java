package com.mapfre.playwright.pageobjects.poliza.vidaley;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.ScreenshotUtils;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CotizarVidaLeyPage extends BasePage {
    // ===== Validación Cotización Vida Ley =====
    private final Locator tituloCotizacionVidaLey;

    // ===== Página Vida Ley =====
    private final Locator title;
    private final Locator btnCotizarVidaLey;
    private final Locator btnBandejaDocumentos;

    // ===== Datos del contratante =====
    private final Locator cboTipoDocumento;
    private final Locator txtNumeroDocumento;
    private final Locator txtNombreCompleto;
    private final Locator txtTelefono;
    private final Locator txtCorreoElectronico;

    // ===== Datos del contacto =====
    private final Locator txtCorreoContacto;
    private final Locator txtRepresentante;
    private final Locator cboCargoRepresentante;

    // ===== Dirección =====
    private final Locator txtTelefonoCasa;
    private final Locator cboDepartamento;
    private final Locator cboProvincia;
    private final Locator cboDistrito;

    private final Locator cboTipoVia;
    private final Locator txtNombreVia;
    private final Locator cboTipoNumero;
    private final Locator txtEnumeracion;

    // ===== Flujo =====
    private final Locator btnBuscarActividad;
    private final Locator btnSiguiente;
    private final Locator btnguardardatos;

    // Constructor
    public CotizarVidaLeyPage(Page page) {
        super(page);
        // ===== constructor pantalla cotizacion vida ley =====
        this.tituloCotizacionVidaLey = page.getByRole(AriaRole.HEADING,new Page.GetByRoleOptions().setName("Cotización póliza de vida ley"));
        // ===== Título =====
        this.title = page.getByRole(AriaRole.HEADING,new Page.GetByRoleOptions().setName("¿Qué quieres hacer?"));
        // ===== Botón Cotizar =====
        this.btnCotizarVidaLey = page.locator("div.grid-elegido-btns-box:has(a:has-text('COTIZAR VIDA LEY'))");
        //===== Botón bandeja de documentos =====
        this.btnBandejaDocumentos = page.getByText("BANDEJA DE DOCUMENTOS",new Page.GetByTextOptions().setExact(true));

        // ===== Datos del contratante =====
        this.cboTipoDocumento = page.getByLabel("Tipo de documento");
        this.txtNumeroDocumento = page.locator("input[name='documentNumber']");
        this.txtNombreCompleto = page.locator("input[name='FullName']");
        this.txtTelefono = page.locator("input[name='Phone']");
        this.txtCorreoElectronico = page.locator("input[name='Email']");

        // ===== Datos del contacto =====
        this.txtCorreoContacto = page.locator("input[name='CorreoElectronico']");
        this.txtRepresentante = page.locator("input[name='Representante']");
        this.cboCargoRepresentante = page.getByLabel("Cargo representante");



        // ===== Dirección =====
        this.txtTelefonoCasa = page.locator("input[name='Telefono']");
        this.cboDepartamento = page.getByLabel("Departamento");
        this.cboProvincia = page.getByLabel("Provincia");
        this.cboDistrito = page.getByLabel("Distrito");
        this.cboTipoVia = page.getByRole(AriaRole.COMBOBOX,new Page.GetByRoleOptions().setName("Vía"));
        this.txtNombreVia = page.getByRole(AriaRole.TEXTBOX,new Page.GetByRoleOptions().setName("Nombre Vía"));
        this.cboTipoNumero = page.getByRole(AriaRole.COMBOBOX,new Page.GetByRoleOptions().setName("Número"));
        this.txtEnumeracion = page.locator("input[name='TextoNumero']");
        this.btnBuscarActividad = page.getByText("Busca y selecciona una actividad");
        this.btnSiguiente = page.getByText("Siguiente", new Page.GetByTextOptions().setExact(true));
        this.btnguardardatos = page.locator(
                "xpath=//button[contains(@class,'g-button') and normalize-space(.)='Guardar y continuar']"
        );

    }

    // ===== Assertions =====
    public void assertLoaded() {
        ElementAsserts.assertVisible(title,"TÍTULO ¿Qué quieres hacer? debe ser visible");
    }

    /*** Valida la pantalla "Cotización póliza de vida ley"*/
    public void assertEnCotizacionVidaLey() {

        log.info("[VIDA_LEY][COTIZACION] Validando pantalla 'Cotización póliza de vida ley'");

        try {
            tituloCotizacionVidaLey.waitFor();
            UiSync.waitForAppIdle(page);

        } catch (com.microsoft.playwright.TimeoutError e) {

            log.error(
                    "[VIDA_LEY][COTIZACION][ERROR] No se cargó la pantalla de Cotización Vida Ley"
            );

            throw new AssertionError("No se pudo validar la pantalla 'Cotización póliza de vida ley'. " +
                            "La navegación no llegó al estado esperado.");
        }

        log.info("[VIDA_LEY][COTIZACION] Pantalla de Cotización Vida Ley lista");
    }

    // ===== Acciones Botones=====
    public void clickCotizarVidaLey() {
        btnCotizarVidaLey.waitFor();
        clickAndSync(btnCotizarVidaLey);
        ScreenshotUtils.screenshotBase64();
    }
    public void completarDatosDelContratante(String tipoDocumento,
            String numeroDocumento,String nombreCompleto,String telefono,String correoElectronico) {

        cboTipoDocumento.waitFor();

        if (tipoDocumento.equalsIgnoreCase("RUC")) {
            cboTipoDocumento.selectOption("2: Object");
        } else if (tipoDocumento.equalsIgnoreCase("DNI")) {
            cboTipoDocumento.selectOption("3: Object");
        } else {
            throw new IllegalArgumentException("Tipo no soportado: " + tipoDocumento);
        }

        txtNumeroDocumento.fill(numeroDocumento);
        UiSync.waitForAppIdle(page);

        txtNombreCompleto.fill(nombreCompleto);
        txtTelefono.fill(telefono);
        txtCorreoElectronico.fill(correoElectronico);

        UiSync.waitForAppIdle(page);

        txtTelefonoCasa.fill("92323343");

        cboDepartamento.selectOption(new SelectOption().setLabel("LIMA"));
        cboDepartamento.dispatchEvent("change");

        page.waitForCondition(() -> !(Boolean) cboProvincia.evaluate("el => el.disabled"));
        cboProvincia.selectOption(new SelectOption().setLabel("LIMA"));
        cboProvincia.dispatchEvent("change");

        page.waitForCondition(() -> !(Boolean) cboDistrito.evaluate("el => el.disabled"));
        cboDistrito.selectOption(new SelectOption().setLabel("LOS OLIVOS"));

        cboTipoVia.selectOption(new SelectOption().setLabel("AA.HH."));
        cboTipoVia.dispatchEvent("change");

        txtNombreVia.fill("automatizacion qa");

        cboTipoNumero.selectOption(new SelectOption().setLabel("CASA"));
        cboTipoNumero.dispatchEvent("change");

        txtEnumeracion.fill("123");

        //Se agrega CORREO CONTACTO//
        txtCorreoContacto.fill("");
        txtCorreoContacto.fill("Automatizacion@mapfre.com.pe");
        txtCorreoContacto.press("Tab");

        //Se agrega REPRESENTANTE//
        txtRepresentante.fill("");
        txtRepresentante.fill(nombreCompleto);
        txtRepresentante.press("Tab");

        //Se agrega CARGO REPRESENTANTE//
        cboCargoRepresentante.waitFor();
        cboCargoRepresentante.scrollIntoViewIfNeeded();

        cboCargoRepresentante.selectOption(new SelectOption().setLabel("ADMINISTRADOR"));
        cboCargoRepresentante.dispatchEvent("change");
        UiSync.waitForAppIdle(page);
    }
    public void clickBuscarActividad() {
        txtNombreCompleto.fill("Automatizador");
        btnBuscarActividad.waitFor();
        btnBuscarActividad.click();
        UiSync.waitForAppIdle(page);
    }

    public void clickSiguienteYConfirmarClausulas() {

        btnSiguiente.waitFor();
        btnSiguiente.click();
        UiSync.waitForAppIdle(page);
        Locator btnOkModal = page.locator("button.swal2-confirm");

        try {
            btnOkModal.waitFor(new Locator.WaitForOptions().setTimeout(3000));
            btnOkModal.click();
        } catch (Exception ignored) {

        }
        UiSync.waitForAppIdle(page);
        ScreenshotUtils.screenshotBase64();
    }

    public void clickBotonGuardarDatos() {

        log.info("[VIDA_LEY][COTIZACION] Buscando botón 'Guardar y continuar'");

        UiSync.waitForAppIdle(page);

        btnguardardatos.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(40000));

        btnguardardatos.scrollIntoViewIfNeeded();

        btnguardardatos.click();

        UiSync.waitForAppIdle(page);

        log.info("[VIDA_LEY][COTIZACION] Click en 'Guardar y continuar' realizado correctamente");
    }

    public void clickBandejaDeDocumentos() {
        UiSync.waitForAppIdle(page);
        btnBandejaDocumentos.waitFor();
        btnBandejaDocumentos.scrollIntoViewIfNeeded();
        btnBandejaDocumentos.click(new Locator.ClickOptions().setForce(true));
        UiSync.waitForAppIdle(page);
        log.info("[VIDA_LEY] Botón BANDEJA DE DOCUMENTOS seleccionado");
    }

}