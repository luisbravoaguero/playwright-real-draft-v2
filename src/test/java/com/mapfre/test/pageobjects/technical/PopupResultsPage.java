package com.mapfre.test.pageobjects.technical;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.playwright.tabs.ScenarioTabs;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public final class PopupResultsPage extends BasePage {
    public static final String DETAIL_TAB = "detalle-poliza";

    private final ScenarioTabs tabs;
    private final Locator title;
    private final Locator scenarioId;
    private final Locator seeMore;

    public PopupResultsPage(Page page, ScenarioTabs tabs) {
        super(page);
        this.tabs = tabs;
        title = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Resultados de pólizas"));
        scenarioId = page.locator("[data-testid='scenario-id']");
        seeMore = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("VER MÁS"));
    }

    public void loadFixture(String id) {
        page.setContent("""
                <!doctype html>
                <html lang="es">
                  <head><title>Resultados</title></head>
                  <body>
                    <h1>Resultados de pólizas</h1>
                    <span data-testid="scenario-id"></span>
                    <button type="button">VER MÁS</button>
                  </body>
                </html>
                """);
        scenarioId.evaluate("(element, value) => element.textContent = value", id);
        ElementAsserts.assertVisible(title, "La página técnica de resultados debe estar visible");
    }

    public PopupDetailPage openDetail(String id) {
        Page popup = tabs.open(DETAIL_TAB, page, () -> {
            seeMore.evaluate("""
                    (button, scenarioId) => {
                      button.onclick = () => {
                        const detail = window.open('about:blank', '_blank');
                        detail.document.write(
                          '<!doctype html><html lang="es"><body>' +
                          '<h1>Detalle de póliza</h1>' +
                          '<span data-testid="scenario-id">' + scenarioId + '</span>' +
                          '</body></html>');
                        detail.document.close();
                      };
                    }
                    """, id);
            seeMore.click();
        });
        return new PopupDetailPage(popup);
    }

    public void closeDetail() {
        tabs.close(DETAIL_TAB);
    }

    public void assertOriginalRemainsOpenFor(String expectedScenarioId) {
        ElementAsserts.assertVisible(title, "La pestaña original debe continuar abierta");
        ElementAsserts.assertContainsText(
                scenarioId,
                expectedScenarioId,
                "La pestaña original debe conservar los datos de su escenario");
    }

    public int openPopupCount() {
        return tabs.openCount();
    }

    public int contextPageCount() {
        return page.context().pages().size();
    }
}
