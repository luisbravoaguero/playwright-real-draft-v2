package com.mapfre.playwright.driver;

import com.microsoft.playwright.*;
import com.mapfre.config.ConfigManager;
import com.mapfre.exceptions.FrameworkException;

public final class DriverFactory {
    private DriverFactory() {}

    public static void init() {
        ConfigManager.load();

        Playwright pw = Playwright.create();

        String browserName = ConfigManager.getBrowser();
        BrowserType.LaunchOptions launchOptions = BrowserOptions.launchOptions();

        Browser browser;
        switch (browserName) {
            case "chromium":
            case "chrome":
                browser = pw.chromium().launch(launchOptions);
                break;
            case "firefox":
                browser = pw.firefox().launch(launchOptions);
                break;
            case "webkit":
                browser = pw.webkit().launch(launchOptions);
                break;
            default:
                pw.close();
                throw new FrameworkException("Navegador no compatible: " + browserName);
        }

        BrowserContext context = browser.newContext(BrowserOptions.contextOptions());
        Page page = context.newPage();
        page.setDefaultTimeout(ConfigManager.getDefaultTimeoutMs());

        DriverManager.set(pw, browser, context, page);
    }
}
