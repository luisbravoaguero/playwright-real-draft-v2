package com.mapfre.playwright.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.mapfre.asserts.ElementAsserts;

import java.util.regex.Pattern;

public class HomePage extends BasePage {

    private final Locator title;
    private final Locator polizaLink;
    private final Locator constanciaSctrVLLink;
    private final Locator inspeccionAutosLink;
    private final Locator iconHomePageLink;
    public HomePage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Home"));
        this.constanciaSctrVLLink = page.locator("a:has(div.label:text-is('Constancias SCTR y VL'))");
        this.polizaLink = page.locator("a").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Pólizas$")));
        this.inspeccionAutosLink = page.locator("a").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Inspección de Autos$")));
        this.iconHomePageLink = page.locator("a.link-ico");
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Home DEBE SER VISIBLE");
    }

    public void clickPolizaLink(){
        clickAndSync(polizaLink);
    }

    public void clickConstanciaSctrVLLink() {
        clickAndSync(constanciaSctrVLLink);
    }

    public void clickHomePageButton() {
        clickAndSync(iconHomePageLink);
    }
    public void clickInspeccionAutosLink() {
        clickAndSync(inspeccionAutosLink);
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
    }

}
