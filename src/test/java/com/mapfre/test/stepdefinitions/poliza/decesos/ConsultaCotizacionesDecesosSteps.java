package com.mapfre.test.stepdefinitions.poliza.decesos;

import com.mapfre.playwright.pageobjects.poliza.decesos.MenuPrincipalDecesosPage;
import com.mapfre.playwright.pageobjects.poliza.decesos.consultaCotizaciones.DocumentosDecesosPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ConsultaCotizacionesDecesosSteps {
    private final MenuPrincipalDecesosPage menuPrincipalDecesosPage;
    private final DocumentosDecesosPage documentosDecesosPage;

    /**
     * Constructor que inicializa todas las páginas necesarias para los pasos de prueba
     * de consulta de cotizaciones de decesos utilizando el proveedor de páginas.
     *
     * @param pageProvider proveedor que gestiona las instancias de página
     */
    public ConsultaCotizacionesDecesosSteps(PageProvider pageProvider) {
        this.menuPrincipalDecesosPage = new MenuPrincipalDecesosPage(pageProvider.get());
        this.documentosDecesosPage = new DocumentosDecesosPage(pageProvider.get());
    }

    /**
     * Paso de prueba que selecciona la opción Consultar Cotizaciones de Decesos
     * desde el menú principal de Decesos.
     */
    @And("en la pagina Decesos selecciona la opcion Consultar Cotizaciones de Decesos")
    public void enLaPaginaDecesosSeleccionaLaOpcionConsultarCotizacionesDeDecesos() {
        // Verifica que el menú principal de Decesos haya cargado correctamente
        menuPrincipalDecesosPage.assertLoaded();
        // Hace clic en el botón de Consultar Cotizaciones de Decesos
        menuPrincipalDecesosPage.clickConsultarCotizacionesDecesosButton();
    }

    /**
     * Paso de prueba que completa el formulario de búsqueda de documentos de Decesos
     * con fechas de inicio y fin.
     *
     * @param fechaInicio la fecha de inicio del rango de búsqueda
     * @param fechaFin la fecha de fin del rango de búsqueda
     */
    @And("en la pagina Documentos Decesos completamos el formulario fecha de inicio {string} y fecha fin {string}")
    public void enLaPaginaDocumentosDecesosCompletamosElFormularioFechaDeInicioYFechaFin(String fechaInicio, String fechaFin) {
        // Verifica que la página de Documentos Decesos haya cargado correctamente
        documentosDecesosPage.assertLoaded();
        // Completa el formulario de opciones de búsqueda con las fechas proporcionadas
        documentosDecesosPage.fillFormOpcionesDeBusqueda(fechaInicio, fechaFin);
    }

    /**
     * Paso de prueba que valida que el sistema muestre las cotizaciones de Decesos
     * después de realizar la búsqueda.
     */
    @Then("el sistema muestra las cotizaciones de Decesos")
    public void elSistemaMuestraLasCotizacionesDeDecesos() {
        // Procesa la búsqueda y valida que sea exitosa, esperando a que aparezcan los resultados
        documentosDecesosPage.processAndAssertSuccessBusqueda(60_000,500);
    }
}
