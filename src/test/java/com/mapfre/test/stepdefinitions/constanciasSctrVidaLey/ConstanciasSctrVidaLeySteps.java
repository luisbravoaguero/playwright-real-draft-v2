package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey;

import com.mapfre.playwright.pageobjects.HomePage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.ConstanciasSctrVidaLeyPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class ConstanciasSctrVidaLeySteps {
    private final ConstanciasSctrVidaLeyPage constanciasSctrVidaLeyPage;

    /**
     * Constructor que inicializa las páginas necesarias para los pasos de prueba
     * utilizando el proveedor de páginas.
     *
     * @param pageProvider proveedor que gestiona las instancias de página
     */
    public ConstanciasSctrVidaLeySteps(PageProvider pageProvider) {
        this.constanciasSctrVidaLeyPage = new ConstanciasSctrVidaLeyPage(pageProvider.get());
    }

    /**
     * Paso de prueba que valida y selecciona la opción SCTR General
     * desde la página de Constancias SCTR y Vida Ley.
     */
    @And("en la pagina Constancias SCTR y VL selecciona la opcion SCTR General")
    public void enLaPaginaConstanciasSCTRYVLSeleccionaLaOpcionSCTRGeneral() {
        // Verifica que la página de Constancias SCTR y Vida Ley haya cargado correctamente
        constanciasSctrVidaLeyPage.assertLoaded();
        // Hace clic en el enlace de SCTR General para acceder a esa sección
        constanciasSctrVidaLeyPage.sctrGeneralLink();
    }

    /**
     * Paso de prueba que valida y selecciona la opción SCTR Mineria
     * desde la página de Constancias SCTR y Vida Ley.
     */
    @And("en la pagina Constancias SCTR y VL selecciona la opcion SCTR Mineria")
    public void enLaPaginaConstanciasSCTRYVLSeleccionaLaOpcionSCTRMineria() {
        // Verifica que la página de Constancias SCTR y Vida Ley haya cargado correctamente
        constanciasSctrVidaLeyPage.assertLoaded();
        // Hace clic en el enlace de SCTR Mineria para acceder a esa sección
        constanciasSctrVidaLeyPage.sctrMineriaLink();
    }

    /**
     * Paso de prueba que valida y accede a la opción Vida Ley
     * desde la página de Constancias SCTR y Vida Ley.
     */
    @And("en la pagina Constancias SCTR y VL selecciona la opcion Vida Ley")
    public void enLaPaginaConstanciasSCTRYVLSeleccionaLaOpcionVidaLey() {
        // Verifica que la página de Constancias SCTR y Vida Ley haya cargado correctamente
        constanciasSctrVidaLeyPage.assertLoaded();
        // Hace clic en el enlace de Vida Ley para acceder a esa sección
        constanciasSctrVidaLeyPage.vidaLeyLink();
    }

}
