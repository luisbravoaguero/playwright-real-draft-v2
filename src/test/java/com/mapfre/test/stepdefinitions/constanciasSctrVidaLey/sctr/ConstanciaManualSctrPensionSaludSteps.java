package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.constanciaManualSctrGeneral.GenerarNuevaConstanciaManualModalPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import testdata.TemplateFactory;

import java.nio.file.Path;

public class ConstanciaManualSctrPensionSaludSteps {
    private final GenerarNuevaConstanciaManualModalPage generarNuevaConstanciaManualModalPage;

    /**
     * Constructor que inicializa la página del modal de generación de nueva constancia manual
     * utilizando el proveedor de páginas.
     *
     * @param pageProvider proveedor que gestiona las instancias de página
     */
    public ConstanciaManualSctrPensionSaludSteps(PageProvider pageProvider) {
        this.generarNuevaConstanciaManualModalPage = new GenerarNuevaConstanciaManualModalPage(pageProvider.get());
    }

    /**
     * Paso de prueba que completa el formulario de generación de nueva constancia manual
     * con fecha de cobertura y carga de asegurados para SCTR Pensión y Salud.
     */
    @And("en el modal Generar Nueva Constancia Manual completamos la fecha de cobertura y cargamos los asegurados")
    public void enElModalGenerarNuevaConstanciaManualCompletamosLaFechaDeCoberturaYCargamosLosAsegurados() {
        // Verifica que el modal de generación de nueva constancia manual haya cargado correctamente
        generarNuevaConstanciaManualModalPage.assertLoaded();
        // Completa el formulario seleccionando fecha de coberturas, vigencia y centro de trabajo
        generarNuevaConstanciaManualModalPage.fillFormGenerarNuevaConstanciaManualModal();
        // Toma una captura de pantalla como evidencia después de completar el formulario
        ExtentEvidence.shot("Evidencia despues de completar el formulario Generar Nueva Constancia Manual Modal");

        // Genera nueve dígitos aleatorios para el documento de identidad del Perú
        String nineDigits = RandomData.idDocumentPeru(9,8);
        // Define el nombre de la hoja de cálculo para la constancia manual de SCTR Pensión y Salud
        String sheetName = "Formato_constancia_manual_sctr_pension_salud_mes_adelantado";
        // Construye la ruta de la plantilla con los dígitos aleatorios generados
        Path template = TemplateFactory.buildPlanillaWithNineDigits(nineDigits, sheetName, 1, 1);
        // Imprime en consola la ruta de la plantilla generada
        //System.out.println("template: "+template);

        // Carga el archivo de plantilla con los datos de los asegurados
        generarNuevaConstanciaManualModalPage.fillFormDatosDeLosAsegurados(template,sheetName);
        // Toma una captura de pantalla como evidencia después de cargar la planilla
        ExtentEvidence.shot("Evidencia despues de cargar la planilla");
        // Procesa el botón "Procesar" y valida que el resultado sea exitoso
        generarNuevaConstanciaManualModalPage.processAndAssertSuccessBotonProcesar(30_000,500);
        // Toma una captura de pantalla como evidencia después de procesar la planilla
        ExtentEvidence.shot("Evidencia despues de procesar la planilla");
        // Procesa el botón "Aceptar" de la lista de observaciones y valida el resultado
        generarNuevaConstanciaManualModalPage.processAndAssertSuccessBotonListaObservaciones(30_000,500);
    }

    /**
     * Paso de prueba que valida que se muestra el número de la constancia manual generada
     * después de guardar la constancia.
     */
    @Then("en el modal Generar Nueva Constancia Manual se muestra el numero de la constancia manual generada")
    public void enElModalGenerarNuevaConstanciaManualSeMuestraElNumeroDeLaConstanciaManualGenerada() {
        // Procesa el botón "Guardar" y valida que la constancia se haya generado exitosamente con su número
        generarNuevaConstanciaManualModalPage.processAndAssertSuccessBotonGuardarAntiguo(240_000,500);
        //generarNuevaConstanciaManualModalPage.processAndAssertSuccessBotonGuardar(240_000,500);
    }
}
