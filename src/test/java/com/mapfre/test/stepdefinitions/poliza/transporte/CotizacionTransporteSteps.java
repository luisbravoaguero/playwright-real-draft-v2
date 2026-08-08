package com.mapfre.test.stepdefinitions.poliza.transporte;

import com.mapfre.playwright.pageobjects.poliza.transporte.CotizacionTransportePage;
import com.mapfre.playwright.pageobjects.poliza.transporte.MenuPrincipalTransportePage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.en.And;

public class CotizacionTransporteSteps {
    private final CotizacionTransportePage cotizacionTransportePage;
    public CotizacionTransporteSteps(PageProvider pageProvider) {
        this.cotizacionTransportePage = new CotizacionTransportePage(pageProvider.get());
    }
    @And("en la pagina Emitir poliza de Transporte se elige la poliza de grupo del agente {string}")
    public void enLaPaginaEmitirPolizaDeTransporteSeEligeLaPolizaDeGrupoDelAgente(String agente) {
        cotizacionTransportePage.assertLoaded();
        cotizacionTransportePage.selectAgente(agente);
        ExtentEvidence.shot("Evidencia despues de seleccionar el agente: " + agente);
        cotizacionTransportePage.processAndAssertSuccessSeleccionarAgente(60_000,500);
        cotizacionTransportePage.processAndAssertSuccessSeleccionarPolizaGrupo(60_000,500);
        cotizacionTransportePage.validarCamposVigenciaPolizaGrupo();
        ExtentEvidence.shot("Evidencia despues de validar los campos de vigencia de la poliza de grupo");
        cotizacionTransportePage.processAndAssertSuccessSelecionarBotonSiguientePolizaGrupo(60_000,500);

    }

    @And("en la pagina Emitir poliza de Transporte, paso y seccion Datos del riesgo, ingresamos materia aseguradora {string}, tasa de mercaderia {string} y  descripcion materia aseguradora {string}")
    public void enLaPaginaEmitirPolizaDeTransportePasoYSeccionDatosDelRiesgoIngresamosMateriaAseguradoraTasaDeMercaderiaYDescripcionMateriaAseguradora(String materia_aseguradora, String tasa_mercaderia, String descripcion_materia_aseguradora) {
        cotizacionTransportePage.assertLoadedDatosRiesgo();
        cotizacionTransportePage.fillFormPasoSeccionDatosRiesgo(materia_aseguradora, tasa_mercaderia, descripcion_materia_aseguradora);
    }

    @And("en la pagina Emitir poliza de Transporte, paso Datos del riesgo, seccion Lugar Origen ingresamos el pais de origen {string}, compania de transporte {string}, nombre nave {string}, factura y guia {string} y nombre del proveedor {string}")
    public void enLaPaginaEmitirPolizaDeTransportePasoDatosDelRiesgoSeccionLugarOrigenIngresamosElPaisDeOrigenCompaniaDeTransporteNombreNaveFacturaYGuiaYNombreDelProveedor(String pais_origen, String compania_transporte, String nombre_nave, String factura_guia, String nombre_proveedor) {
        cotizacionTransportePage.fillFormPasoDatosSeccionRiesgoLugarOrigen(pais_origen, compania_transporte, nombre_nave, factura_guia, nombre_proveedor);
    }

    @And("en la pagina Emitir poliza de Transporte, paso Datos del riesgo, seccion Lugar Destino ingresamos en el campo Otro {string}, departamento {string}, provincia {string}, distrito {string} y almacen {string}")
    public void enLaPaginaEmitirPolizaDeTransportePasoDatosDelRiesgoSeccionLugarDestinoIngresamosEnElCampoOtroDepartamentoProvinciaDistritoYAlmacen(String otro, String departamento, String provincia, String distrito, String almacen) {
        cotizacionTransportePage.fillFormPasoDatosSeccionRiesgoLugarDestino(otro, departamento, provincia, distrito, almacen);
        ExtentEvidence.shot("Evidencia despues de llenar los campos de la seccion Lugar Destino");
        cotizacionTransportePage.processAndAssertSuccessSelecionarBotonSiguientePasoDatosSeccionRiesgoLugarDestino(60_000,500);
    }

    @And("en la pagina Emitir poliza de Transporte, paso Calulo de prima, seccion Importes ingresamos la valuacion mercaderia {string}, valor mercaderia {string}, flete {string}, derecho de aduana {string} y porcentaje de sobreseguro {string}")
    public void enLaPaginaEmitirPolizaDeTransportePasoCaluloDePrimaSeccionImportesIngresamosLaValuacionMercaderiaValorMercaderiaFleteDerechoDeAduanaYPorcentajeDeSobreseguro(String valuacion_mercaderia, String valor_mercaderia, String flete, String derecho_aduana, String porcentaje_sobreseguro) {
        cotizacionTransportePage.fillFormPasoCalculoPrimaSeccionImportes(valuacion_mercaderia, valor_mercaderia, flete, derecho_aduana, porcentaje_sobreseguro);
        ExtentEvidence.shot("Evidencia despues de llenar los campos de la seccion Importes");
        cotizacionTransportePage.processAndAssertSuccessSelecionarBotonCalcularPrima(60_000,500);
        ExtentEvidence.shot("Evidencia despues de calcular la prima");
        cotizacionTransportePage.processAndAssertSuccessSelecionarBotonCalculoPrima(60_000,500);
    }

    @And("en la pagina Emitir poliza de Transporte, paso Datos del contratante, seccion Datos personales ingresamos los datos personales, caracteristicas personales, datos laborales y datos de direccion")
    public void enLaPaginaEmitirPolizaDeTransportePasoDatosDelContratanteSeccionDatosPersonalesIngresamosLosDatosPersonalesCaracteristicasPersonalesDatosLaboralesYDatosDeDireccion() {
        String numeroDocumento = RandomData.idDocumentPeru(9,8);
        cotizacionTransportePage.fillFormPasoDatosContratanteSeccionDatosPersonales(numeroDocumento);
        ExtentEvidence.shot("Evidencia despues de llenar los campos de la seccion Datos personales");
        cotizacionTransportePage.fillFormPasoDatosContratanteSeccionCaracteristicasPersonales();
        ExtentEvidence.shot("Evidencia despues de llenar los campos de la seccion Caracteristicas personales");
        cotizacionTransportePage.fillFormPasoDatosContratanteSeccionDatosLaborales();
        ExtentEvidence.shot("Evidencia despues de llenar los campos de la seccion Datos laborales");
        cotizacionTransportePage.fillFormPasoDatosContratanteSeccionDatosContacto();
        ExtentEvidence.shot("Evidencia despues de llenar los campos de la seccion Datos de contacto");
        cotizacionTransportePage.fillFormPasoDatosContratanteSeccionDatosDireccion();
        ExtentEvidence.shot("Evidencia despues de llenar los campos de la seccion Datos personales");
        cotizacionTransportePage.processAndAssertSuccessSelecionarBotonSiguientePasoDatosContratante(60_000,500);
    }

    @And("en la pagina Emitir poliza de Transporte, paso Emitir poliza, seccion Resumen Prima seleccionamos el boton Emitir poliza")
    public void enLaPaginaEmitirPolizaDeTransportePasoEmitirPolizaSeccionResumenPrimaSeleccionamosElBotonEmitirPoliza() {
        cotizacionTransportePage.processAndAssertSuccessSelecionarBotonEmitirPoliza(90_000,500);
        ExtentEvidence.shot("Evidencia despues de seleccionar el boton Emitir poliza");
        cotizacionTransportePage.processAndAssertSuccessSelecionarBotonEmitirDentroModal(240_000,500);
    }
}
