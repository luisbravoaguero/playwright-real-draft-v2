package com.mapfre.test.pageobjects.technical;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public final class PopupDetailPage extends BasePage {
    private final Locator title;
    private final Locator scenarioId;

    public PopupDetailPage(Page page) {
        super(page);
        title = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Detalle de póliza"));
        scenarioId = page.locator("[data-testid='scenario-id']");
    }

    public void assertLoadedFor(String expectedScenarioId) {
        ElementAsserts.assertVisible(title, "El título del popup debe estar visible");
        ElementAsserts.assertContainsText(
                scenarioId,
                expectedScenarioId,
                "El popup debe contener el identificador de su escenario");
    }

    public boolean isClosed() {
        return page.isClosed();
    }
}
