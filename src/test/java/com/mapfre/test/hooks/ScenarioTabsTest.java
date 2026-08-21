package com.mapfre.test.hooks;

import com.mapfre.playwright.driver.DriverFactory;
import com.mapfre.playwright.driver.DriverManager;
import com.mapfre.playwright.tabs.ScenarioTabs;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertTrue;

/** Browser-backed tests for the scenario popup lifecycle. */
public final class ScenarioTabsTest {
    private ScenarioTabs tabs;
    private BrowserContext context;
    private Page source;

    @BeforeMethod
    public void createBrowserScenario() {
        DriverFactory.init();
        context = DriverManager.context();
        source = DriverManager.page();
        source.setContent("""
                <!doctype html>
                <html lang="es">
                  <head><title>Resultados</title></head>
                  <body>
                    <button id="ver-mas"
                      onclick="window.open('about:blank', '_blank')">VER MÁS</button>
                  </body>
                </html>
                """);
        tabs = new ScenarioTabs();
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowserScenario() {
        try {
            if (tabs != null) {
                tabs.close();
            }
        } finally {
            DriverManager.cleanup();
        }
    }

    @Test
    public void capturesRetrievesAndClosesARealPopup() {
        Page detail = tabs.open(
                "detalle",
                source,
                () -> source.locator("#ver-mas").click());

        detail.setContent("<h1>Detalle de póliza 12345</h1>");
        detail.getByRole(AriaRole.HEADING).waitFor();

        assertSame(tabs.get("detalle"), detail);
        assertSame(detail.context(), context);
        assertEquals(detail.locator("h1").innerText(), "Detalle de póliza 12345");
        assertEquals(tabs.openCount(), 1);
        assertEquals(context.pages().size(), 2);

        tabs.close("detalle");

        assertTrue(detail.isClosed());
        assertEquals(tabs.openCount(), 0);
        assertEquals(context.pages().size(), 1);
        assertFalse(source.isClosed());
    }

    @Test
    public void cleanupClosesEveryOwnedPopupAfterAFailurePath() {
        Page first = tabs.open(
                "primero",
                source,
                () -> source.locator("#ver-mas").click());
        Page second = tabs.open(
                "segundo",
                source,
                () -> source.locator("#ver-mas").click());

        assertEquals(context.pages().size(), 3);
        assertThrows(IllegalStateException.class, () -> tabs.open(
                "primero",
                source,
                () -> source.locator("#ver-mas").click()));

        tabs.close();

        assertTrue(first.isClosed());
        assertTrue(second.isClosed());
        assertEquals(context.pages().size(), 1);
        assertFalse(source.isClosed());
    }
}
