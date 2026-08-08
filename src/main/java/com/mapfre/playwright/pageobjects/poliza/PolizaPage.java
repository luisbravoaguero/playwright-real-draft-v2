package com.mapfre.playwright.pageobjects.poliza;

import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.mapfre.asserts.ElementAsserts;

public class PolizaPage extends BasePage {
    private final Locator title;
    private final Locator sctrLink;
    private final Locator soatLink;
    private final Locator autoLink;
    private final Locator campoSantoLink;
    private final Locator transporteLink;
    private final Locator accidentesLink;
    private final Locator vidaLeyLink;
    private final Locator decesosLink;
    private final Locator riegosGeneralesLink;
    private final Locator folaLink;
    private final Locator vidaInversionLink;
    public PolizaPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Pólizas"));
        this.sctrLink = page.locator("a:has(div.label:text-is('SCTR'))");
        this.soatLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("SOAT"));
        this.autoLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("Autos"));
        this.campoSantoLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("Camposanto"));
        this.transporteLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("Transportes"));
        this.accidentesLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("Accidentes"));
        this.vidaLeyLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("Vida Ley"));
        this.decesosLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("Decesos"));
        this.riegosGeneralesLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("Riesgos Generales"));
        this.folaLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("FOLA"));
        this.vidaInversionLink = page.locator("a").filter(new Locator.FilterOptions().setHasText("Vida Inversión"));
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Pólizas DEBE SER VISIBLE");
    }

    public void clickSctrLink() {
        clickAndSync(sctrLink);
    }
    public void clickSoatLink(){
        clickAndSync(soatLink);
    }
    public void clickAutoLink(){
        clickAndSync(autoLink);
    }
    public void clickCampoSantoLink(){
        clickAndSync(campoSantoLink);
    }
    public void clickTransporteLink(){
        clickAndSync(transporteLink);
    }
    public void clickAccidentesLink(){
        clickAndSync(accidentesLink);
    }
    public void clickVidaLeyLink() {clickAndSync(vidaLeyLink);}
    public void clickDecesosLink() {clickAndSync(decesosLink);}
    public void clickRiesgosGeneralesLink() {
        clickAndSync(riegosGeneralesLink);
    }
    public void clickFolaLink() {
        clickAndSync(folaLink);
    }
    public void clickVidaInversionLink() {clickAndSync(vidaInversionLink);}
}
