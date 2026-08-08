package com.mapfre.playwright.pageobjects.poliza.vidaley;

import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;

public class DatosCoberturaYAseguradosPage extends BasePage {

    /* ===== Validación de página ===== */
    private final Locator titleCotizacion;
    private final Locator sectionTitle;

    /* ===== Tab y contenedor ===== */
    private final Locator tabRegistroManual;
    private final Locator panelRegistroManualActivo;

    /* ===== Inputs ===== */
    private final Locator txtNumeroDocumento;
    private final Locator txtNombres;
    private final Locator txtApellidoPaterno;
    private final Locator txtApellidoMaterno;
    private final Locator txtNombresCompletos;
    private final Locator txtFechaNacimiento;
    private final Locator txtOcupacion;
    private final Locator txtSueldo;
    private final Locator txtLugarExposicion;

    /* ===== Selects (HTML nativo) ===== */
    private final Locator cboCantidad;
    private final Locator cboTipoDocumento;
    private final Locator cboTipoRiesgo;
    private final Locator cboSexo;

    /* ===== Estados ===== */
    private final Locator formularioInvalido;

    /* ===== Acciones ===== */
    private final Locator btnObtenerCoberturas;
    private final Locator tituloRiesgos;
    private final Locator btnSiguiente;

    /* ===== Modal RENIEC ===== */
    private final Locator modalReniec;
    private final Locator btnOkModalReniec;

    /* ===== Modal Confirmación ===== */
    private final Locator modalConfirmacion;
    private final Locator btnGuardarContinuar;

    // Constructor

    public DatosCoberturaYAseguradosPage(Page page) {
        super(page);

        this.titleCotizacion = page.getByRole(AriaRole.HEADING,new Page.GetByRoleOptions().setName("Cotización póliza de vida ley"));
        this.sectionTitle = page.getByText("Datos de la cobertura y asegurados");
        this.tabRegistroManual = page.getByRole(AriaRole.TAB,new Page.GetByRoleOptions().setName("REGISTRO MANUAL"));
        this.panelRegistroManualActivo = page.locator("mat-tab-body.mat-mdc-tab-body-active");

        /* Inputs confirmados por HTML */
        this.txtNumeroDocumento = panelRegistroManualActivo.locator("input[name='nNumeroDocumento0']");
        this.txtNombres = panelRegistroManualActivo.locator("input[name='nNombreAsegurado0']");
        this.txtApellidoPaterno = panelRegistroManualActivo.locator("input[name='nApellidoPaterno0']");
        this.txtApellidoMaterno = panelRegistroManualActivo.locator("input[name='nApellidoMaterno0']");
        this.txtNombresCompletos =panelRegistroManualActivo.locator("input[name='nNombreCompleto0']");
        this.txtFechaNacimiento =panelRegistroManualActivo.locator("input.mat-datepicker-input");
        this.txtOcupacion =panelRegistroManualActivo.locator("input[name='nOcupacion0']");
        this.txtSueldo = panelRegistroManualActivo.locator("input[name='nSueldo0']");
        this.txtLugarExposicion = panelRegistroManualActivo.locator("input[placeholder*='Lugar de Exp']");
        this.cboCantidad = panelRegistroManualActivo.locator("select:has(option:text('01'))");
        this.cboTipoDocumento =panelRegistroManualActivo.locator("select:has(option:text('DNI'))");
        this.cboTipoRiesgo =panelRegistroManualActivo.locator("select:has(option:text('EMPLEADOS'))");
        this.cboSexo =panelRegistroManualActivo.locator("select:has(option:text('MASCULINO'))");
        this.formularioInvalido = panelRegistroManualActivo.locator(".ng-invalid");
        this.btnObtenerCoberturas =panelRegistroManualActivo.getByText("Obtener coberturas");
        this.tituloRiesgos = panelRegistroManualActivo.getByRole(AriaRole.HEADING,new Locator.GetByRoleOptions().setName("Riesgos"));
        this.btnSiguiente = page.getByText("Siguiente", new Page.GetByTextOptions().setExact(true));

        /* Modal RENIEC */
        this.modalReniec =  page.locator("div.swal2-popup.swal2-show[role='dialog']");
        this.btnOkModalReniec = modalReniec.locator("button.swal2-confirm");

        /* Modal CONFIRMACION */
        this.modalConfirmacion = page.locator("div.cdk-overlay-pane").filter(new Locator.FilterOptions().setHasText("¿Estás seguro que quieres guardar los datos?"));
        this.btnGuardarContinuar = modalConfirmacion.getByText("Guardar y continuar");
    }

    /* ================= VALIDACIONES ================= */

    public void assertEnPaginaDatosCoberturaYAsegurados() {
        titleCotizacion.waitFor();
        sectionTitle.waitFor();
    }

    public void seleccionarRegistroManual() {
        tabRegistroManual.waitFor();
        tabRegistroManual.click();
        panelRegistroManualActivo.waitFor();
    }

    public void completarRegistroManualObligatorio() {

        /* Tipo y número documento */
        cboCantidad.selectOption(new SelectOption().setLabel("01"));
        cboTipoDocumento.selectOption(new SelectOption().setLabel("DNI"));

        txtNumeroDocumento.fill("45228114");
        txtNumeroDocumento.press("Tab");

        /* Nombres */
        txtNombres.fill("AUTOMATIZACION");
        txtApellidoPaterno.fill("AUTOMATIZACIONP");
        txtApellidoMaterno.fill("AUTOMATIZACIONM");

        /* Nombres completos (NO se autogenera) */
        txtNombresCompletos.fill("AUTOMATIZACION AUTOMATIZACIONP AUTOMATIZACIONM");
        txtNombresCompletos.press("Tab");

        /* Fecha nacimiento */
        txtFechaNacimiento.click();
        txtFechaNacimiento.fill("28/07/1988");
        txtFechaNacimiento.press("Tab");

        /* Ocupación */
        txtOcupacion.fill("TI");
        txtOcupacion.press("Tab");

        /* Sueldo */
        txtSueldo.fill("2500");
        txtSueldo.press("Tab");

        /* Tipo de riesgo */
        cboTipoRiesgo.selectOption(new SelectOption().setLabel("EMPLEADOS"));

        /* Sexo */
        cboSexo.selectOption(new SelectOption().setLabel("MASCULINO"));

        /* Lugar de exposición (regla de negocio) */
        txtLugarExposicion.fill("OFICINA");
        txtLugarExposicion.press("Tab");

        /* Validar que el asegurado quedó completo */
        page.waitForCondition(() -> formularioInvalido.count() == 0);

        log.info("[VIDA_LEY][COTIZACION] Asegurado completado correctamente (1 completado)");
    }

    /* ================= COBERTURAS ================= */

    public void obtenerCoberturasYValidarRiesgos() {

        page.waitForCondition(() ->
                !btnObtenerCoberturas.getAttribute("class").contains("disabled")
        );

        btnObtenerCoberturas.scrollIntoViewIfNeeded();
        page.waitForTimeout(500);

        btnObtenerCoberturas.click(new Locator.ClickOptions().setForce(true));

        log.info("[VIDA_LEY][COBERTURAS] Click en 'Obtener coberturas' ejecutado");

        try {
            modalReniec.waitFor();
            btnOkModalReniec.click();
            page.waitForCondition(() -> !modalReniec.isVisible());
            log.info("[VIDA_LEY][RENIEC] Modal RENIEC confirmado");
        } catch (Exception e) {
            log.info("[VIDA_LEY][RENIEC] No apareció modal RENIEC");
        }
    }

    /* ================= CONTINUAR ================= */

    public void validarRiesgosYClickSiguiente() {

        tituloRiesgos.waitFor();
        btnSiguiente.click();
        modalConfirmacion.waitFor();
        btnGuardarContinuar.click();
        page.waitForCondition(() -> !modalConfirmacion.isVisible());
    }
}