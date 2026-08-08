package com.mapfre.utils.debug;

import com.microsoft.playwright.Locator;

public final class DebugLocatorUtils {

    private DebugLocatorUtils() {}

    public static void debugLocator(String name, Locator loc) {
        System.out.println("\n---- " + name + " ----");

        int count;
        try {
            count = loc.count();
            System.out.println("count=" + count);
        } catch (Exception e) {
            System.out.println("count=ERROR: " + e.getMessage());
            return;
        }

        if (count == 0) return;

        Locator first = loc.first();

        try {
            System.out.println("visible=" + first.isVisible());
        } catch (Exception e) {
            System.out.println("visible=ERROR: " + e.getMessage());
        }

        try {
            System.out.println("display=" + first.evaluate("e => getComputedStyle(e).display"));
            System.out.println("visibility=" + first.evaluate("e => getComputedStyle(e).visibility"));
            System.out.println("opacity=" + first.evaluate("e => getComputedStyle(e).opacity"));
        } catch (Exception e) {
            System.out.println("computedStyle=ERROR: " + e.getMessage());
        }

        try {
            System.out.println("bbox=" + first.boundingBox()); // null often means not visible
        } catch (Exception e) {
            System.out.println("bbox=ERROR: " + e.getMessage());
        }

        try {
            System.out.println("textContent=" + first.textContent());
        } catch (Exception e) {
            System.out.println("textContent=ERROR: " + e.getMessage());
        }
    }
}