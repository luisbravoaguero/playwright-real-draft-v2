package com.mapfre.test.hooks;

import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Scenario-scoped owner of popup pages.
 *
 * <p>PicoContainer creates one instance per scenario. The registry never creates Playwright
 * infrastructure and must only be called by the TestNG worker thread that owns the scenario.</p>
 */
public final class ScenarioTabs implements AutoCloseable {
    private final PageProvider pageProvider;
    private final Map<String, Page> popups = new LinkedHashMap<>();
    private Thread ownerThread;

    public ScenarioTabs(PageProvider pageProvider) {
        this.pageProvider = Objects.requireNonNull(pageProvider, "pageProvider must not be null");
    }

    /** Atomically captures and owns the popup produced by {@code trigger}. */
    public Page open(String name, Page source, Runnable trigger) {
        checkThread();
        String tabName = requireName(name);
        Objects.requireNonNull(source, "source must not be null");
        Objects.requireNonNull(trigger, "trigger must not be null");

        if (popups.containsKey(tabName)) {
            throw new IllegalStateException("A popup named '" + tabName + "' is already open");
        }
        if (source.context() != pageProvider.get().context()) {
            throw new IllegalArgumentException("The popup source belongs to another scenario context");
        }

        Page popup = source.waitForPopup(trigger);
        popups.put(tabName, popup);
        return popup;
    }

    public Page get(String name) {
        checkThread();
        String tabName = requireName(name);
        Page popup = popups.get(tabName);
        if (popup == null) {
            throw new IllegalStateException("No popup named '" + tabName + "' belongs to this scenario");
        }
        return popup;
    }

    public void close(String name) {
        checkThread();
        Page popup = popups.remove(requireName(name));
        closeIfOpen(popup);
    }

    public int openCount() {
        checkThread();
        return (int) popups.values().stream().filter(page -> !page.isClosed()).count();
    }

    @Override
    public void close() {
        checkThread();
        for (Page popup : new ArrayList<>(popups.values())) {
            closeIfOpen(popup);
        }
        popups.clear();
    }

    private void checkThread() {
        Thread current = Thread.currentThread();
        if (ownerThread == null) {
            ownerThread = current;
        } else if (ownerThread != current) {
            throw new IllegalStateException(
                    "ScenarioTabs cannot be shared across threads; owner=" + ownerThread.getName()
                            + ", caller=" + current.getName());
        }
    }

    private static String requireName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Popup name must not be blank");
        }
        return name;
    }

    private static void closeIfOpen(Page popup) {
        if (popup != null && !popup.isClosed()) {
            popup.close();
        }
    }
}
