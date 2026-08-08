package com.mapfre.playwright.pageobjects.inspeccionAutos;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class InspeccionDeAutosPage extends BasePage {
    private final Locator filtroMenuCommon;
    public InspeccionDeAutosPage(Page page) {
        super(page);
        this.filtroMenuCommon = page.locator("div.g-card-filter");
    }
    public void assertLoaded() {
        ElementAsserts.assertVisible(filtroMenuCommon, "El filtro común DEBE SER VISIBLE");
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(5000);
    }

    public void clickCotizacionesMenuLink() {
        clickMenuPrincipal("Cotizaciones");
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(5000);
    }


    public void clickSolicitudesMenuLink() {
        clickMenuPrincipal("Solicitudes");
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(5000);
    }

    public void clickProgramacionesMenuLink() {
        clickMenuPrincipal("Programaciones");
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(5000);
    }
}
