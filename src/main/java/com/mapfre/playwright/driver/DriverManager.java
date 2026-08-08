package com.mapfre.playwright.driver;

import com.microsoft.playwright.*;

public final class DriverManager {
    private static final ThreadLocal<Playwright> TL_PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<Browser> TL_BROWSER = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> TL_CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Page> TL_PAGE = new ThreadLocal<>();

    private DriverManager() {}

    public static void set(Playwright pw, Browser br, BrowserContext ctx, Page pg) {
        TL_PLAYWRIGHT.set(pw);
        TL_BROWSER.set(br);
        TL_CONTEXT.set(ctx);
        TL_PAGE.set(pg);
    }

    public static Playwright playwright() { return TL_PLAYWRIGHT.get(); }
    public static Browser browser() { return TL_BROWSER.get(); }
    public static BrowserContext context() { return TL_CONTEXT.get(); }
    public static Page page() { return TL_PAGE.get(); }

    public static void cleanup() {
        Page p = TL_PAGE.get();
        if (p != null) p.close();

        BrowserContext c = TL_CONTEXT.get();
        if (c != null) c.close();

        Browser b = TL_BROWSER.get();
        if (b != null) b.close();

        Playwright pw = TL_PLAYWRIGHT.get();
        if (pw != null) pw.close();

        TL_PAGE.remove();
        TL_CONTEXT.remove();
        TL_BROWSER.remove();
        TL_PLAYWRIGHT.remove();
    }
}
