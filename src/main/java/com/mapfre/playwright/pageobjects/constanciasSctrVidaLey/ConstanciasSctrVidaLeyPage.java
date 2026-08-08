package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ConstanciasSctrVidaLeyPage extends BasePage {
    private final Locator title;
    private final Locator sctrMineriaLink;
    private final Locator vidaLeyLink;
    private final Locator sctrGeneralLink;

    public ConstanciasSctrVidaLeyPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Constancias SCTR"));
        //this.sctrLink = page.locator("a:has(div.label:text-is('SCTR'))");
        this.sctrMineriaLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("SCTR Minería"));
        this.vidaLeyLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("Vida Ley"));
        this.sctrGeneralLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("SCTR General"));
    }

    // Verifica que el título de la página esté visible.
    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Constancias SCTR DEBE SER VISIBLE");
    }

    // Hace click en el enlace "SCTR General" y sincroniza la navegación.
    public void sctrGeneralLink() {
        clickAndSync(sctrGeneralLink);
    }

    // Hace click en el enlace "Vida Ley" y sincroniza la navegación.
    public void vidaLeyLink() {
        clickAndSync(vidaLeyLink);
    }

    // Hace click en el enlace "SCTR mINERIA" y sincroniza la navegación.
    public void sctrMineriaLink() {
        clickAndSync(sctrMineriaLink);
    }

}
