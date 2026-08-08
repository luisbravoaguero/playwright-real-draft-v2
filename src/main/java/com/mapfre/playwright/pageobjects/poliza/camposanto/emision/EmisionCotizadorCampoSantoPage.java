package com.mapfre.playwright.pageobjects.poliza.camposanto.emision;

import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.pageobjects.BasePage;
import static com.mapfre.utils.UiSync.waitForAppIdle;

import com.mapfre.utils.ScreenshotUtils;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.JSHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mapfre.asserts.ElementAsserts;
import com.microsoft.playwright.options.SelectOption;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Paths;
import java.util.List;


public class EmisionCotizadorCampoSantoPage extends BasePage {

    private final Locator btnIrABandeja;
    private final Locator lblBandejaCamposanto;
    private final Locator lblNroCotizacion;
    private final Locator lblRepositorioDocumentos;
    private final Locator inputDocumentoTitular;

    private final Locator tabDatosTomador;
    private final Locator cmbSexo;
    private final Locator cmbTipoVia;
    private final Locator txtNombreVia;
    private final Locator cmbTipoNumero;
    private final Locator txtEnumeracion;

    private final Locator tabDatosBeneficiarios;
    private final Locator cmbTipoBeneficiario;
    private final Locator cmbTipoDocumento;
    private final Locator txtNumeroDocumento;
    private final Locator cmbEstadoCivil;

    private final Locator tabDatosAdicionales;
    private final Locator txtNumeroContrato;
    private final Locator txtCodigoUbicacion;
    private final Locator txtCodigoSector;

    private final Locator btnEmitir;

    private final Locator lblPolizaEmitida;
    private final Locator btnOkPolizaEmitida;

    private String contratoActual;
    private String ubicacionActual;

    //private final Locator lblPolizaEmitida;
    private final Locator lblContratoExiste;
    private final Locator btnOkModal;
    //private final Locator lblErrorContrato;

    public EmisionCotizadorCampoSantoPage(Page page) {
        super(page);

        this.btnIrABandeja = page.locator("a:has-text('IR A BANDEJA')");
        this.lblBandejaCamposanto =  page.locator("h1:text-is('Bandeja de Camposanto')");
        this.lblNroCotizacion = page.locator("h2").filter(new Locator.FilterOptions().setHasText("Nro cotizacion:"));
        this.lblRepositorioDocumentos = page.locator("h2").filter(new Locator.FilterOptions().setHasText("Repositorio de documentos"));
        this.inputDocumentoTitular = page.locator("input[type='file']").first();

        this.tabDatosTomador = page.locator(".mdc-tab__text-label").filter(new Locator.FilterOptions().setHasText("DATOS DEL TOMADOR")).first();
        this.cmbSexo = page.locator("select").filter(new Locator.FilterOptions().setHasText("Masculino")).first();
        this.txtNombreVia = page.locator("input[name='descripcionDomicilio']");
        this.cmbTipoVia = txtNombreVia.locator("xpath=preceding::select[1]");
        this.txtEnumeracion = page.locator("input[name='descripcionNumero']");
        this.cmbTipoNumero = txtEnumeracion.locator("xpath=preceding::select[1]");

        this.tabDatosBeneficiarios = page.getByText("DATOS BENEFICIARIOS");
        this.cmbTipoBeneficiario = page.locator("select").filter(new Locator.FilterOptions().setHasText("TITULAR")).first();
        this.txtNumeroDocumento = page.locator("input[name='documento0']");
        this.cmbTipoDocumento = txtNumeroDocumento.locator("xpath=preceding::select[1]");
        this.cmbEstadoCivil = page.locator("select").filter(new Locator.FilterOptions().setHasText("SOLTERO")).first();

        this.tabDatosAdicionales = page.getByText("DATOS ADICIONALES");
        this.txtNumeroContrato = page.locator("input[name='numeroContrato']");
        this.txtCodigoUbicacion = page.locator("input[name='ubicacion']");
        this.txtCodigoSector = page.locator("input[name='sector']");

        this.btnEmitir = page.getByText("EMITIR");
        this.lblPolizaEmitida = page.locator(".swal2-title").filter(new Locator.FilterOptions().setHasText("PÓLIZA EMITIDA"));
        this.btnOkPolizaEmitida = page.locator("button.swal2-confirm");


        this.lblContratoExiste = page.locator(".swal2-html-container")
                .filter(new Locator.FilterOptions()
                        .setHasText("ya existe"));

        this.btnOkModal = page.locator("button.swal2-confirm");
        //this.lblErrorContrato = page.locator(".swal2-title, .swal2-html-container").filter(  new Locator.FilterOptions().setHasText("contrato"));


    }
    public void clickIrABandeja() {
        btnIrABandeja.click();
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
    
                        const texto = (h2.textContent || '')
                                .replace(/\\s+/g, ' ')
                                .trim();
    
                        const numero = texto.replace(/\\D+/g, '');
    
                        return numero.length > 0 ? numero : false;
                    }
                    """,
                    null,
                    new Page.WaitForFunctionOptions().setTimeout(30000)
            );

            String nroCotizacion = String.valueOf(handle.jsonValue()).trim();
            if (nroCotizacion.isBlank() ||
                    "false".equalsIgnoreCase(nroCotizacion)) {

                throw new AssertExceptions(
                        "El número de cotización se muestra vacío"
                );
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
            throw new AssertExceptions("No se pudo obtener el número de cotización. H2 encontrados: "+ h2Debug, e);
        }
    }
    public String obtenerNumeroCotizacionGenerado() {

        String nroCotizacion = obtenerNumeroCotizacion();
        log.info("[EMISION CAMPOSANTO] Número de cotización obtenido: {}", nroCotizacion);
        return nroCotizacion;
    }

    public void assertLoadedRepositorioDocumentos() {

        UiSync.waitForAppIdle();

        try {

            log.info("[CHECK DOCUMENTOS] Validando pantalla Repositorio de documentos");

            ElementAsserts.assertVisible(
                    lblRepositorioDocumentos,
                    "Debe mostrarse 'Repositorio de documentos'"
            );

            log.info("[CHECK DOCUMENTOS] Pantalla cargada correctamente");

        } catch (Exception e) {

            throw new AssertExceptions(
                    "Error validando pantalla Repositorio de documentos",
                    e
            );
        }
    }

    public void cargarDniTitular() {
        try {
            log.info("[CHECK DE DOCUMENTOS] Cargando DNI TITULAR");
            Path archivoPdf = Paths.get(
                    "src",
                    "test",
                    "resources",
                    "testdata",
                    "uploads",
                    "templates",
                    "test_dni_CheckDocumentos.pdf"
            );
            if (!Files.exists(archivoPdf)) {
                throw new AssertExceptions(
                        "No existe el archivo PDF: " + archivoPdf.toAbsolutePath()
                );
            }
            //inputDocumentoTitular.waitFor();
            inputDocumentoTitular.setInputFiles(archivoPdf);
            waitForAppIdle();

            log.info("[CHECK DE DOCUMENTOS] DNI TITULAR cargado correctamente");

        } catch (Exception e) {

            throw new AssertExceptions(
                    "Error cargando DNI TITULAR",
                    e
            );
        }
    }

    public void TabDatosDelTomador() {
        //tabDatosTomador.click();
        try {
            log.info("[DATOS TOMADOR] Completando datos");
            tabDatosTomador.click();
            waitForAppIdle();
            // Sexo
            cmbSexo.selectOption(new SelectOption().setLabel("Masculino"));
            // Vía
            cmbTipoVia.selectOption(new SelectOption().setLabel("AVDA."));
            // Nombre vía
            txtNombreVia.fill("JIRON DE LA UNION");
            // Número
            cmbTipoNumero.selectOption(new SelectOption().setLabel("CASA"));
            // Enumeración
            txtEnumeracion.fill("123");
            waitForAppIdle();
            log.info("[DATOS TOMADOR] Datos completados correctamente");

        } catch (Exception e) {

            throw new AssertExceptions(
                    "Error completando datos del tomador",
                    e
            );
        }
    }

    public void TabDatosBeneficiarios (){
        tabDatosBeneficiarios.click();
        cmbTipoBeneficiario.selectOption(new SelectOption().setLabel("TITULAR"));
        cmbTipoDocumento.selectOption(new SelectOption().setLabel("DNI"));
        txtNumeroDocumento.pressSequentially("87654321",new Locator.PressSequentiallyOptions().setDelay(200));

        cmbEstadoCivil.click();
        txtNumeroDocumento.blur();
        page.waitForTimeout(1000);
        //page.keyboard().press("Tab");
        waitForAppIdle();
        cmbEstadoCivil.selectOption(new SelectOption().setLabel("SOLTERO"));
    }

    public void tabDatosAdicionales() {

        tabDatosAdicionales.click();

        String[] contratoDisponible =
                obtenerPrimerContratoDisponible();

        contratoActual = contratoDisponible[0];
        ubicacionActual = contratoDisponible[1];

        txtNumeroContrato.fill(contratoActual);
        txtCodigoUbicacion.fill(ubicacionActual);
        txtCodigoSector.fill("2E");
    }

//Trabajar con el txt de la data
private String[] obtenerPrimerContratoDisponible() {

    try {

        Path archivo = Paths.get(
                "src",
                "test",
                "resources",
                "testdata",
                "uploads",
                "templates",
                "CampoSanto_Emision_Data.txt"
        );

        List<String> lineas = Files.readAllLines(archivo);

        for (String linea : lineas) {

            if (linea.trim().isEmpty()) {
                continue;
            }

            String[] datos = linea.split("\\t");

            String estado = datos.length >= 3
                    ? datos[2].trim()
                    : "D";

            if ("D".equalsIgnoreCase(estado)) {

                log.info(
                        "[CAMPOSANTO] Contrato disponible encontrado: {} - {}",
                        datos[0].trim(),
                        datos[1].trim()
                );

                return new String[]{
                        datos[0].trim(), // contrato
                        datos[1].trim()  // ubicación


                };
            }

        }

        throw new AssertExceptions(
                "No se encontraron contratos disponibles"
        );

    } catch (Exception e) {

        throw new AssertExceptions(
                "Error leyendo archivo CampoSanto_Emision_Data.txt",
                e
        );
    }

}

    public void emitirPoliza() {

        try {

            while (true) {

                log.info("[EMISION] Intentando emitir contrato {} ubicación {}", contratoActual, ubicacionActual);
                ScreenshotUtils.screenshotBase64();
                btnEmitir.click();
                page.locator(".swal2-popup").waitFor();
                page.waitForTimeout(1000);

                String tituloModal =
                        page.locator(".swal2-title").textContent();

                String mensajeModal =
                        page.locator(".swal2-html-container").textContent();

                log.info("Modal detectado");
                log.info("Titulo modal: {}", tituloModal);
                log.info("Contenido modal: {}", mensajeModal);

                // CASO EXITOSO
                if (tituloModal != null
                        && tituloModal.contains("Póliza Emitida")) {

                    log.info("[EMISION] Póliza emitida correctamente");

                    marcarContratoComoUsado();

                    btnOkModal.click();

                    waitForAppIdle();

                    return;
                }

                // CASO CONTRATO YA EXISTE O YA FUE UTILIZADO
                if (mensajeModal != null &&
                        (mensajeModal.contains("ya existe")
                                || mensajeModal.contains("ya fue utilizado"))) {

                    log.info(
                            "[EMISION] Contrato ya utilizado. Buscando siguiente disponible"
                    );

                    marcarContratoComoUsado();

                    btnOkModal.click();

                    String[] siguienteContrato =
                            obtenerPrimerContratoDisponible();

                    contratoActual = siguienteContrato[0];
                    ubicacionActual = siguienteContrato[1];

                    txtNumeroContrato.fill("");
                    txtCodigoUbicacion.fill("");

                    txtNumeroContrato.fill(contratoActual);
                    txtCodigoUbicacion.fill(ubicacionActual);

                    waitForAppIdle();

                    continue;
                }

                throw new AssertExceptions(
                        "Mensaje no controlado durante la emisión. Título: "
                                + tituloModal
                                + " | Mensaje: "
                                + mensajeModal
                );
            }

        } catch (Exception e) {

            throw new AssertExceptions(
                    "Error durante la emisión de la póliza",
                    e
            );
        }
    }
    private void marcarContratoComoUsado() {

        try {

            Path archivo = Paths.get(
                    "src",
                    "test",
                    "resources",
                    "testdata",
                    "uploads",
                    "templates",
                    "CampoSanto_Emision_Data.txt"
            );

            List<String> lineas = Files.readAllLines(archivo);

            for (int i = 0; i < lineas.size(); i++) {

                String linea = lineas.get(i);

                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] datos = linea.split("\\t");
                String contrato = datos[0].trim();
                String ubicacion = datos[1].trim();

                if (contrato.equals(contratoActual)
                        && ubicacion.equals(ubicacionActual)) {

                    lineas.set(
                            i,
                            contratoActual + "\t" +
                                    ubicacionActual + "\tU"
                    );

                    break;
                }
            }

            Files.write(archivo, lineas);

            log.info(
                    "[CAMPOSANTO] Contrato marcado como usado: {} - {}",
                    contratoActual,
                    ubicacionActual
            );

        } catch (Exception e) {

            throw new AssertExceptions(
                    "Error actualizando archivo de contratos",
                    e
            );
        }
    }

}