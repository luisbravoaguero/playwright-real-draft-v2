package com.mapfre.test.stepdefinitions.poliza.fola;

import com.mapfre.playwright.pageobjects.poliza.fola.CotizacionFolaPage;
import com.mapfre.playwright.pageobjects.poliza.fola.MenuPrincipalFolaPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;

public class CotizacionFolaSteps {
    private final MenuPrincipalFolaPage menuPrincipalFolaPage;
    private final CotizacionFolaPage cotizacionFolaPage;
    public CotizacionFolaSteps(PageProvider pageProvider) {
        this.menuPrincipalFolaPage = new MenuPrincipalFolaPage(pageProvider.get());
        this.cotizacionFolaPage = new CotizacionFolaPage(pageProvider.get());
    }

    @And("en la pagina FOLA selecciona la opcion Cotizar poliza FOLA")
    public void enLaPaginaFOLASeleccionaLaOpcionCotizarPolizaFOLA() {
        menuPrincipalFolaPage.clickCotizarFolaButton();

    }

    @And("en la pagina Cotizador de Fola en la seccion Informacion general se ingresa el numero de RUC {string} , la razon social {string}y el tipo de fraccionamiento {string}")
    public void enLaPaginaCotizadorDeFolaEnLaSeccionInformacionGeneralSeIngresaElNumeroDeRUCLaRazonSocialYElTipoDeFraccionamiento(String numeroRuc, String razonSocial, String tipoFraccionamiento) {
        cotizacionFolaPage.assertLoadedCotizacionFola();
        cotizacionFolaPage.fillFormInformacionGeneral(numeroRuc, razonSocial, tipoFraccionamiento);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de informacion general del cotizador de FOLA");


    }

    @And("en la pagina Cotizador de Folar en la seccion Asegurados se ingresa la cantidad {string} , actividad {string} y subvencion por persona {string}")
    public void enLaPaginaCotizadorDeFolarEnLaSeccionAseguradosSeIngresaLaCantidadActividadYSubvencionPorPersona(String cantidadAsegurado, String actividad, String subvencionPersona) {
        cotizacionFolaPage.fillFormAsegurados(cantidadAsegurado, actividad, subvencionPersona);
        ExtentEvidence.shot("Evidencia despues de llenar el formulario de asegurados del cotizador de FOLA");
    }

    @And("en la pagina Cotizador de Fola se selecciona el boton de cotizar")
    public void enLaPaginaCotizadorDeFolaSeSeleccionaElBotonDeCotizar() {
        //cotizacionFolaPage.clickAgregarAseguradoFola();
        cotizacionFolaPage.clickCotizarFolaButton();
        ExtentEvidence.shot("Evidencia despues de seleccionar el boton de cotizar en el cotizador de FOLA");

    }
}
