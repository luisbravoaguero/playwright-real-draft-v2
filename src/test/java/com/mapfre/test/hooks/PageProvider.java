package com.mapfre.test.hooks;

import com.microsoft.playwright.Page;
import com.mapfre.playwright.driver.DriverManager;

public class PageProvider {
    public Page get() {
        return DriverManager.page();
    }
}
