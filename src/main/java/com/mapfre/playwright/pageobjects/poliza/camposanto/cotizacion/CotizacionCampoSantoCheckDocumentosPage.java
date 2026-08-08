package com.mapfre.playwright.pageobjects.poliza.camposanto.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import static com.mapfre.utils.UiSync.waitForAppIdle;

public class CotizacionCampoSantoCheckDocumentosPage extends BasePage {

    private final Locator tabCheckDocumentos;
    private final Locator lblRepositorioDocumentos;
    // TAB DATOS DEL TOMADOR
    private final Locator tabDatosTomador;
    private final Locator cmbSexo;
    private final Locator cmbTipoVia;
    private final Locator txtNombreVia;
    private final Locator cmbTipoNumero;
    private final Locator txtNumero;
    private final Locator tabTomadorContainer;

// TAB DATOS DEL BENEFICIARIO
    private final Locator tabDatosBeneficiarios;

    private final Locator cmbTipoDocumento;
    private final Locator txtNumeroDocumento;
    private final Locator txtNombres;
    private final Locator txtApellidoPaterno;
    private final Locator txtApellidoMaterno;
    private final Locator cmbEstadoCivil;
    private final Locator txtFechaDefuncion;
    private final Locator cmbSexoBen;
    private final Locator cmbParentesco;
    private final Locator cmbOcupacion;

// ================= DATOS ADICIONALES =================
    private final Locator tabDatosAdicionales;
    private final Locator txtNumeroContrato;
    private final Locator txtAgenciaFuneraria;
    private final Locator cmbVendedorFunerario;

    Locator tabBeneficiarioContainer = page.locator("div[role='tabpanel']")
            .filter(new Locator.FilterOptions().setHasText("Datos del Beneficiario"));
    private final Locator txtSupervisorNI;
    private final Locator txtCodigoUbicacion;
    private final Locator txtCodigoSector;

    //boton emitir
    private final Locator btnEmitir;

    private final Locator lblPolizaEmitida;
    private final Locator btnOkModal;

    private final Locator lblErrorContrato;

    public CotizacionCampoSantoCheckDocumentosPage(Page page) {
        super(page);

        this.tabCheckDocumentos = page.locator(".mdc-tab__text-label")
                .filter(new Locator.FilterOptions().setHasText("CHECK DE DOCUMENTOS"))
                .first();

        this.lblRepositorioDocumentos = page.locator("h2.g-sub-title.g-myd-subtitle")
                .filter(new Locator.FilterOptions().setHasText("Repositorio de documentos"));

        this.tabTomadorContainer = page.locator(".mat-mdc-tab-body-active");


        // ================= TOMADOR =================
        this.tabDatosTomador = page.locator(".mdc-tab__text-label")
                .filter(new Locator.FilterOptions().setHasText("DATOS DEL TOMADOR"))
                .first();

        this.cmbSexo = page.locator("mat-form-field")
                .filter(new Locator.FilterOptions().setHasText("Sexo"))
                .locator("select");

        this.txtNombreVia = tabTomadorContainer
                .locator("input[name='descripcionDomicilio']");

        this.cmbTipoVia = page.locator("input[name='descripcionDomicilio']")
                .locator("xpath=preceding::select[1]");

        this.txtNumero = tabTomadorContainer
                .locator("input[name='descripcionNumero']");

        this.cmbTipoNumero = page.locator("input[name='descripcionDomicilio']")
                .locator("xpath=following::select[1]");

        // ================= BENEFICIARIO =================

        this.tabDatosBeneficiarios = page.locator(".mdc-tab__text-label")
                .filter(new Locator.FilterOptions().setHasText("DATOS BENEFICIARIOS"))
                .first();

        // CONTENEDOR BENEFICIARIO
        this.tabBeneficiarioContainer = page.locator("div[role='tabpanel']")
                .filter(new Locator.FilterOptions().setHasText("Datos de los beneficiarios"));
        this.txtNumeroDocumento = page.locator("input[name='documento0']");
        this.cmbTipoDocumento = txtNumeroDocumento
                .locator("xpath=preceding::select[1]");


        this.txtNombres = page.locator("input[name='nombres0']");
        this.txtApellidoPaterno = page.locator("input[name='primerApellido0']");
        this.txtApellidoMaterno = page.locator("input[name='segundoApellido0']");
        this.cmbSexoBen = selectByLabel("Sexo");

        this.cmbEstadoCivil = page.locator("mat-form-field")
                .filter(new Locator.FilterOptions().setHasText("Estado Civil"))
                .locator("select");

        this.txtFechaDefuncion = page.locator("mat-form-field")
                .filter(new Locator.FilterOptions().setHasText("Fecha de defunción"))
                .locator("input");

        this.cmbParentesco = page.locator("mat-form-field")
                .filter(new Locator.FilterOptions().setHasText("Parentesco"))
                .locator("select");

        this.cmbOcupacion = page.locator("mat-form-field")
                .filter(new Locator.FilterOptions().setHasText("Ocupación"))
                .locator("select");

        //TAB DATOS ADICIONALES
        this.tabDatosAdicionales = page.locator(".mdc-tab__text-label")
                .filter(new Locator.FilterOptions().setHasText("DATOS ADICIONALES"))
                .first();

        //  input simple
        this.txtNumeroContrato = page.locator("input[name='numeroContrato']");

        // selects con Angular (mat-form-field)
        this.txtAgenciaFuneraria = page.locator("input[placeholder*='agencia']");

        this.cmbVendedorFunerario = page.locator("mat-form-field")
                .filter(new Locator.FilterOptions().setHasText("vendedor"))
                .locator("input");


        this.txtCodigoUbicacion = page.locator("input[name='ubicacion']");
        this.txtSupervisorNI = page.locator("input[placeholder*='supervisor']");
        this.txtCodigoSector = page.locator("input[name='sector']");
        // Boton emitir
        this.btnEmitir = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("EMITIR"));

        this.lblPolizaEmitida = page.locator(".swal2-title")
                .filter(new Locator.FilterOptions().setHasText("Póliza Emitida"));

        this.btnOkModal = page.locator("button.swal2-confirm");

        this.lblErrorContrato = page.locator(".swal2-title, .swal2-html-container")
                .filter(new Locator.FilterOptions()
                        .setHasText("número de contrato"));

    }

    public void assertLoaded() {
        waitForAppIdle();

        try {
            log.info("[CHECK DOCUMENTOS] Esperando carga de tabs");

            tabDatosAdicionales.waitFor(
                    new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.VISIBLE)
                            .setTimeout(60000)
            );

            log.info("[CHECK DOCUMENTOS] Validando pantalla Check de Documentos");

            ElementAsserts.assertVisible(
                    tabCheckDocumentos,
                    "Debe mostrarse el tab 'CHECK DE DOCUMENTOS'"
            );

            ElementAsserts.assertVisible(
                    lblRepositorioDocumentos,
                    "Debe mostrarse el título 'Repositorio de documentos'"
            );

        } catch (Exception e) {
            log.error("[CHECK DOCUMENTOS] Error validando pantalla", e);
            throw new AssertExceptions("Error validando pantalla Check de Documentos", e);
        }
    }


    public void ingresarDatosTomadorDireccion() {

        try {
            log.info("[DATOS TOMADOR] Ingresando datos TAB TOMADOR");

            abrirTabDatosTomador();

            page.locator(".mat-mdc-tab-body-active").waitFor();
            page.waitForTimeout(2000);
            // Sexo (NUEVO)
            cmbSexo.waitFor();
            page.waitForFunction("el => !el.disabled", cmbSexo.elementHandle());
            cmbSexo.selectOption(new SelectOption().setLabel("Masculino"));

            // Tipo Vía
            log.info("Seleccionando Tipo Via");

            cmbTipoVia.waitFor();
            page.waitForFunction("el => !el.disabled", cmbTipoVia.elementHandle());
            cmbTipoVia.selectOption(new SelectOption().setLabel("CALLE"));

            // Nombre vía
            txtNombreVia.waitFor();
            txtNombreVia.fill("LOS ALAMOS");

            // Tipo Número
            log.info("Seleccionando Tipo Numero");

            cmbTipoNumero.waitFor();
            page.waitForFunction("el => !el.disabled", cmbTipoNumero.elementHandle());
            cmbTipoNumero.selectOption(new SelectOption().setLabel("NRO"));

            // Número
            txtNumero.waitFor();
            txtNumero.fill("123");

            waitForAppIdle();

            log.info("[DATOS TOMADOR] Dirección OK");

        } catch (Exception e) {
            throw new AssertExceptions("Error ingresando datos del tomador", e);
        }
    }

    private void abrirTabDatosTomador() {
        try {
            tabDatosTomador.waitFor();
            tabDatosTomador.click();
            waitForAppIdle();

        } catch (Exception e) {
            throw new AssertExceptions("Error al abrir tab DATOS DEL TOMADOR", e);
        }
    }

    public void ingresarDatosBeneficiario() {

        try {

            // abrir tab
            abrirTabDatosBeneficiarios();

            // asegurar render completo
            page.locator(".mat-mdc-tab-body-active").waitFor();
            page.waitForTimeout(1500);

            // Tipo Documento
            cmbTipoDocumento.waitFor();
            page.waitForFunction("el => !el.disabled", cmbTipoDocumento.elementHandle());
            seleccionarOpcionPorTexto(cmbTipoDocumento, "Tipo Documento", "DNI");

            // Número documento
            txtNumeroDocumento.waitFor();
            txtNumeroDocumento.fill("12345678");

            // nombres
            txtNombres.waitFor();
            txtNombres.fill("JUAN");

            txtApellidoPaterno.waitFor();
            txtApellidoPaterno.fill("PEREZ");

            txtApellidoMaterno.waitFor();
            txtApellidoMaterno.fill("LOPEZ");

            // Sexo
            cmbSexoBen.waitFor();
            page.waitForFunction("el => !el.disabled", cmbSexoBen.elementHandle());
            seleccionarOpcionPorTexto(cmbSexoBen, "Sexo", "Masculino");

            // Estado civil
            cmbEstadoCivil.waitFor();
            page.waitForFunction("el => !el.disabled", cmbEstadoCivil.elementHandle());
            seleccionarOpcionPorTexto(cmbEstadoCivil, "Estado Civil", "SOLTERO");

            //Fecha de defunción
            seleccionarFechaDefuncion();
            waitForAppIdle();

            // PARIENTESCO (ESTO FALTABA)
            cmbParentesco.waitFor();
            page.waitForFunction("el => !el.disabled", cmbParentesco.elementHandle());
            seleccionarOpcionPorTexto(cmbParentesco, "Parentesco", "HIJO/A");


            //Ocupación
            cmbOcupacion.waitFor();
            page.waitForFunction("el => !el.disabled", cmbOcupacion.elementHandle());
            cmbOcupacion.selectOption(new SelectOption().setLabel("ABOGADO"));

            log.info("[BENEFICIARIO] Datos ingresados correctamente");

        } catch (Exception e) {
            throw new AssertExceptions("Error ingresando datos del beneficiario", e);
        }
    }

    private void seleccionarOpcionPorTexto(Locator select, String campo, String textoVisible) {
        select.waitFor();

        Locator opcion = select.locator("option")
                .filter(new Locator.FilterOptions().setHasText(textoVisible))
                .first();

        opcion.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));

        String value = opcion.getAttribute("value");

        if (value == null || value.isBlank()) {
            throw new AssertExceptions("No se encontró value para " + campo + ": " + textoVisible);
        }

        select.selectOption(value);
    }

    private Locator selectByLabel(String label) {
        return page.locator("label:has-text('" + label + "')")
                .locator("xpath=following::select[1]");
    }

    private void abrirTabDatosBeneficiarios() {
        try {
            tabDatosBeneficiarios.waitFor();
            tabDatosBeneficiarios.click();
            waitForAppIdle();

        } catch (Exception e) {
            throw new AssertExceptions("Error al abrir tab DATOS BENEFICIARIOS", e);
        }
    }
    public void seleccionarFechaDefuncion() {

        try {
            // abrir calendario correcto
            Locator btnCalendario = page.locator("mat-form-field")
                    .filter(new Locator.FilterOptions().setHasText("Fecha de defunción"))
                    .getByRole(AriaRole.BUTTON);

            btnCalendario.waitFor();
            btnCalendario.click();

            // esperar calendario
            Locator calendario = page.locator(".mat-datepicker-content");
            calendario.waitFor();

            // seleccionar día habilitado (hoy)
            Locator diaActivo = calendario.locator(
                    ".mat-calendar-body-cell:not(.mat-calendar-body-disabled)"
            ).first();

            diaActivo.waitFor();
            diaActivo.click();

            waitForAppIdle();

            log.info("[BENEFICIARIO] Fecha seleccionada correctamente");

        } catch (Exception e) {
            throw new AssertExceptions("Error seleccionando fecha de defunción", e);
        }
    }

    public void ingresarDatosAdicionales() {

        try {
            // abrir tab
            tabDatosAdicionales.waitFor();
            tabDatosAdicionales.click();

            page.locator(".mat-mdc-tab-body-active").waitFor();
            page.waitForTimeout(1500);

            // Número contrato
            txtNumeroContrato.waitFor();
            txtNumeroContrato.fill("09668");

            // Agencia funeraria (autocomplete)
            seleccionarAgenciaFuneraria();

            // Vendedor funerario
            seleccionarVendedorFunerario();

            // Supervisor
            seleccionarSupervisorNI();

            // Código ubicación
            txtCodigoUbicacion.waitFor();
            txtCodigoUbicacion.fill("CL/12");

            // Código sector
            txtCodigoSector.waitFor();
            txtCodigoSector.fill("2E");
            waitForAppIdle();

            log.info("[DATOS ADICIONALES] Datos ingresados correctamente");

        } catch (Exception e) {
            throw new AssertExceptions("Error ingresando datos adicionales", e);
        }
    }
    private void seleccionarAgenciaFuneraria() {

        // escribir texto base
        txtAgenciaFuneraria.waitFor();
        txtAgenciaFuneraria.click();
        txtAgenciaFuneraria.fill("AGEN");

        // esperar overlay (lista)
        Locator opcion = page.locator("mat-option")
                .filter(new Locator.FilterOptions().setHasText("AGENCIA COMAS"))
                .first();

        opcion.waitFor();

        // seleccionar opción EXACTA
        opcion.click();

        waitForAppIdle();
    }
    private void seleccionarVendedorFunerario() {

        cmbVendedorFunerario.waitFor();

        // activar input
        cmbVendedorFunerario.click();

        // escribir parte del nombre
        cmbVendedorFunerario.fill("ELBER");

        // esperar que cargue la opción
        Locator opcion = page.locator("mat-option")
                .filter(new Locator.FilterOptions().setHasText("ELBERT MAZA CARRION"))
                .first();

        opcion.waitFor();

        //seleccionar opción exacta
        opcion.click();

        waitForAppIdle();
    }
    private void seleccionarSupervisorNI() {

        txtSupervisorNI.waitFor();

        // activar input
        txtSupervisorNI.click();

        // escribir texto base
        txtSupervisorNI.fill("CARMEN");

        // esperar opción del overlay
        Locator opcion = page.locator("mat-option")
                .filter(new Locator.FilterOptions().setHasText("CARMEN MELITINA MERINO RUIZ"))
                .first();

        opcion.waitFor();

        // seleccionar opción correcta
        opcion.click();

        waitForAppIdle();
    }

    public void emitirPoliza() {

        try {
            log.info("[EMISION] Iniciando emisión");

            for (int intento = 0; intento < 2; intento++) {

                btnEmitir.click();
                // esperar aparición de cualquier modal
                Locator modal = page.locator(".swal2-popup");
                modal.waitFor();

                // CASO ERROR
                if (lblErrorContrato.isVisible()) {

                    log.info("[EMISION] Contrato duplicado detectado");

                    // CLICK EN OK (CRÍTICO)
                    btnOkModal.waitFor();
                    btnOkModal.click();

                    // esperar que modal desaparezca
                    modal.waitFor(new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.HIDDEN));

                    // regenerar número
                    String nuevoNumero = generarNumeroContrato();
                    log.info("Nuevo contrato: " + nuevoNumero);

                    // limpiar + llenar
                    txtNumeroContrato.fill("");
                    txtNumeroContrato.fill(nuevoNumero);
                    waitForAppIdle();

                }
                //CASO ÉXITO
                else if (lblPolizaEmitida.isVisible()) {

                    log.info("[EMISION] Emisión exitosa");
                    btnOkModal.click();
                    waitForAppIdle();
                    return;
                }
            }

            throw new AssertExceptions("No se pudo emitir la póliza tras reintentos");

        } catch (Exception e) {
            throw new AssertExceptions("Error al emitir la póliza", e);
        }
    }


    public void validarEmisionPoliza() {

        try {
            log.info("[EMISION] Validando modal de póliza emitida");
            // esperar modal visible
            lblPolizaEmitida.waitFor(
                    new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)
            );

            log.info("[EMISION] Modal visible ");

            //ESPERAR A QUE TERMINE LA CARGA (CLAVE REAL)
            page.waitForLoadState(LoadState.NETWORKIDLE);
            waitForAppIdle();

            // esperar que botón esté habilitado
            page.waitForFunction(
                    "btn => btn && !btn.disabled",
                    btnOkModal.elementHandle()
            );

            page.waitForTimeout(500);
            btnOkModal.click();

            log.info("[EMISION] Modal cerrado correctamente");

        } catch (Exception e) {
            throw new AssertExceptions("Error validando emisión de póliza", e);
        }
    }

    private String generarNumeroContrato() {
        int numero = (int) (Math.random() * 90000) + 10000;
        return String.valueOf(numero);
    }

}