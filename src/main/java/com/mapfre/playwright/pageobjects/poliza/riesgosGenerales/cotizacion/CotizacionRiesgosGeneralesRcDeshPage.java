package com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.GestorModal;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CotizacionRiesgosGeneralesRcDeshPage extends BasePage {


    private final Locator cmbProducto;
    private final Locator modalAlerta;
    private final Locator tituloModalAlerta;
    private final Locator mensajeModalAlerta;

    private final Locator cmbTipoDocumento;
    private final Locator txtNumeroDocumento;
    private final Locator txtCorredor;

// DATOS DEL CONTRATO U OBRA
    private final Locator txtTrabajoObra;
    private final Locator cmbDepartamento;
    private final Locator cmbProvincia;
    private final Locator cmbDistrito;
    private final Locator txtDireccionRiesgo;
    private final Locator cmbRamo;
    private final Locator cmbMoneda;
    private final Locator txtNumeroTrabajadores;
    private final Locator txtValorContrato;
    private final Locator txtSumaAsegurada;
    private final Locator cmbCanal;

    private final Locator btnSiguiente;

    private final Locator lblTituloResultadoCotizacion;
    private final Locator lblNumeroCotizacion;

    private final Locator cmbRamoDeshonestidad;
    // Suma asegurada Deshonestidad
    private final Locator txtSumaAseguradaDesh;
    private final Locator cmbCanalDesh;

    public CotizacionRiesgosGeneralesRcDeshPage(Page page) {
        super(page);

        this.cmbProducto = page.locator("oim-select select").first();

        this.modalAlerta = page.locator("div.swal2-popup.swal2-modal");
        this.tituloModalAlerta = page.locator(".swal2-title");
        this.mensajeModalAlerta = page.locator(".swal2-html-container");

        this.cmbTipoDocumento = page.getByLabel("Tipo de documento");
        this.txtNumeroDocumento = page.locator("input[name='numDocumento']");
        this.txtCorredor = page.locator("input[name='corredor']");


        this.txtTrabajoObra =page.locator("input[name='detalleTrabajoObra']");
        this.cmbDepartamento =page.locator("select").nth(2);
        this.cmbProvincia = page.locator("select").nth(3);
        this.cmbDistrito =  page.locator("select").nth(4);
        this.txtDireccionRiesgo = page.locator("input[name='ubicacion']");
        this.cmbRamo = page.locator("select").nth(5);
        this.cmbMoneda = page.locator("select").filter(new Locator.FilterOptions() .setHasText("NUEVOS SOLES"));
        this.txtNumeroTrabajadores = page.locator("input[name='numTrabajadores']");
        this.txtValorContrato = page.locator("input[name='valorContrato']");
        this.txtSumaAsegurada = page.locator("input[name='sumaAsegurada']");
        this.cmbCanal = page.locator("select").nth(7);

        this.btnSiguiente =   page.locator("input[value='Siguiente']");

        this.lblTituloResultadoCotizacion = page.getByText("Cotización de riesgos generales");
        this.lblNumeroCotizacion = page.getByText("Nro de cotización:");

        this.cmbRamoDeshonestidad = page.locator("oim-select").filter(new Locator.FilterOptions().setHasText("Ramo Deshonestidad")).locator("select");
        this.txtSumaAseguradaDesh = page.locator("input[name='sumaAseguradaDesh']");
        this.cmbCanalDesh = page.locator("select:has-text('Enlace')");
    }

    public void seleccionarProducto(String producto) {

        selectAndSync(cmbProducto, producto);
        waitAppReady();
        if (GestorModal.existeModal(modalAlerta)) {

            String titulo = GestorModal.obtenerTexto(tituloModalAlerta);
            String mensaje = GestorModal.obtenerTexto(mensajeModalAlerta);
            log.info("[RIESGOS GENERALES][RC DESH] Modal detectado. Titulo: '{}' Mensaje: '{}'", titulo, mensaje );

            GestorModal.clickBotonSiExiste(modalAlerta,"OK");
            log.info("[RIESGOS GENERALES][RC DESH] Se seleccionó el botón OK del modal");
        }

        log.info("[RIESGOS GENERALES][RC DESH] Producto seleccionado: {}",producto);
    }

    public void ingresarDatosContratante(String tipoDocumento, String numeroDocumento, String corredor)
    {
        selectAndSync(cmbTipoDocumento,tipoDocumento);
        fillAndBlurSync(txtNumeroDocumento,numeroDocumento);
        fillAndBlurSync(txtCorredor,  corredor );
        log.info("[RIESGOS GENERALES][RC DESH] Datos del contratante completados. Tipo Documento: {}, Documento: {}, Corredor: {}",tipoDocumento, numeroDocumento,corredor);
    }

    public void ingresarDatosContrato(
            String trabajoObra,
            String departamento,
            String provincia,
            String distrito,
            String direccionRiesgo,
            String ramo,
            String moneda,
            String numeroTrabajadores,
            String valorContrato,
            String sumaAsegurada,
            String canal
    ) {

        fillAndBlurSync(txtTrabajoObra, trabajoObra);
        selectAndSync(cmbDepartamento, departamento);
        selectAndSync(cmbProvincia, provincia);
        selectAndSync(cmbDistrito, distrito);
        fillAndBlurSync(txtDireccionRiesgo, direccionRiesgo);
        selectAndSync(cmbRamo, ramo);
        waitAppReady();
        selectAndSync(cmbMoneda, moneda);
        fillAndBlurSync(txtNumeroTrabajadores, numeroTrabajadores);
        fillAndBlurSync(txtValorContrato, valorContrato);
        fillAndBlurSync(txtSumaAsegurada, sumaAsegurada);
        selectAndSync(cmbCanal, canal);

        log.info("[RIESGOS GENERALES][RC DESH] Datos del contrato u obra completados");
    }

    public void clicksiguiente() {
        clickAndSync(btnSiguiente);
        log.info("[RIESGOS GENERALES][RC DESH] clic siguiente realizado correctamente");
    }
    public String validarYObtenerNumeroCotizacion() {

        ElementAsserts.assertVisible(
                lblTituloResultadoCotizacion,
                "No se encontró la página Resultado de la cotización"
        );

        String numeroCotizacion = lblNumeroCotizacion.textContent().replace("Nro de cotización:", "").trim();

        log.info("[RIESGOS GENERALES][RC DESH] Número de cotización generado: {}", numeroCotizacion  );
        waitAppReady();
        return numeroCotizacion;

    }

    public void ingresarDatosContratoDeshonestidad(
            String trabajoObra,
            String departamento,
            String provincia,
            String distrito,
            String direccionRiesgo,
            String ramo,
            String ramoDeshonestidad,
            String moneda,
            String numeroTrabajadores,
            String valorContrato,
            String sumaAsegurada,
            String canal
    ) {

        fillAndBlurSync(txtTrabajoObra, trabajoObra);
        selectAndSync(cmbDepartamento, departamento);
        selectAndSync(cmbProvincia, provincia);
        selectAndSync(cmbDistrito, distrito);
        fillAndBlurSync(txtDireccionRiesgo, direccionRiesgo);
        selectAndSync(cmbRamo, ramo);
        waitAppReady();
        selectAndSync(cmbRamoDeshonestidad,ramoDeshonestidad);
        waitAppReady();
        selectAndSync(cmbMoneda, moneda);
        fillAndBlurSync(txtNumeroTrabajadores, numeroTrabajadores);
        fillAndBlurSync(txtValorContrato, valorContrato);
        fillAndBlurSync(txtSumaAseguradaDesh, sumaAsegurada);
        selectAndSync(cmbCanalDesh, canal);
        waitAppReady();

        log.info("[RIESGOS GENERALES][RC DESH][Ramo Deshonestidad] Datos del contrato u obra completados");
    }

}