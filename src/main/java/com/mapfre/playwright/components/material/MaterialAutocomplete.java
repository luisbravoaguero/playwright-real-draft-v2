package com.mapfre.playwright.components.material;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public final class MaterialAutocomplete {

    public enum AttemptKind { CLICK, HOVER_ENTER, KEYBOARD, FAILED }

    private final Page page;
    private final Locator comboboxAnchor;   // your getByRole(COMBOBOX, name="Marca y Modelo")
    private final Pattern labelPattern;     // used to find the mat-form-field reliably
    private boolean debug = false;

    private AttemptKind lastAttempt = AttemptKind.FAILED;

    public MaterialAutocomplete(Page page, Locator comboboxAnchor, String fieldLabelText) {
        this.page = Objects.requireNonNull(page, "page");
        this.comboboxAnchor = Objects.requireNonNull(comboboxAnchor, "comboboxAnchor");
        Objects.requireNonNull(fieldLabelText, "fieldLabelText");

        // matches: "Marca y Modelo" or "Marca y Modelo *"
        this.labelPattern = Pattern.compile("^\\s*" + Pattern.quote(fieldLabelText) + "\\*?\\s*$", Pattern.CASE_INSENSITIVE);
    }

    public MaterialAutocomplete setDebug(boolean enabled) {
        this.debug = enabled;
        return this;
    }

    public AttemptKind getLastAttempt() {
        return lastAttempt;
    }

    /**
     * Selects the exact value using 3 strategies and verifies persistence after blur.
     *
     * @param timeoutMs total timeout for panel operations (open/find/wait hide)
     * @param quietMs   stability window after value is correct (0 = no stability wait)
     */
    public AttemptKind selectExact(String valorExacto, long timeoutMs, long quietMs) {
        Objects.requireNonNull(valorExacto, "valorExacto");
        lastAttempt = AttemptKind.FAILED;

        // 1) <input> real (re-resolved as needed)
        Locator input = ensureMatInputLocator();

        // 2) Escribir robusto + abrir panel
        typeAndOpenPanel(input, valorExacto);

        // 3) Panel asociado por aria-controls
        Locator panel = getMaterialPanelFromInput(input, (int) timeoutMs);

        if (debug) dumpMaterialOptions(panel);

        // 4) Opción objetivo (smart matching)
        Locator target = findOptionSmart(panel, valorExacto);

        // ---------------------- INTENTO 1: CLICK ----------------------
        log("[INFO] Intento 1: CLICK sobre '" + valorExacto + "'");
        target.scrollIntoViewIfNeeded();
        try {
            target.click();
        } catch (Exception e) {
            target.click(new Locator.ClickOptions().setForce(true));
        }

        waitUntilPanelHidden(panel, 1500, 100);
        if (isPanelVisible(panel)) {
            input.press("Enter");
            waitUntilPanelHidden(panel, 2000, 100);
        }

        input = ensureMatInputLocator(); // recaptura por re-render
        input.press("Tab");

        if (waitUntilInputValueStable(input, valorExacto, 600, 100, quietMs)) {
            lastAttempt = AttemptKind.CLICK;
            log("[INFO] Intento que funcionó: " + lastAttempt);
            return lastAttempt;
        }

        // -------------------- INTENTO 2: HOVER + ENTER --------------------
        log("[INFO] Intento 2: HOVER+ENTER sobre '" + valorExacto + "'");
        typeAndOpenPanel(input, valorExacto);
        panel = getMaterialPanelFromInput(input, (int) timeoutMs);
        target = findOptionSmart(panel, valorExacto);

        target.scrollIntoViewIfNeeded();
        target.hover();
        input.focus();
        input.press("Enter");

        waitUntilPanelHidden(panel, 2000, 100);

        input = ensureMatInputLocator();
        input.press("Tab");

        if (waitUntilInputValueStable(input, valorExacto, 600, 100, quietMs)) {
            lastAttempt = AttemptKind.HOVER_ENTER;
            log("[INFO] Intento que funcionó: " + lastAttempt);
            return lastAttempt;
        }

        // ---------------- INTENTO 3: TECLADO (aria-activedescendant) ----------------
        log("[INFO] Intento 3: TECLADO (ArrowDown+Enter) para '" + valorExacto + "'");
        typeAndOpenPanel(input, valorExacto);
        panel = getMaterialPanelFromInput(input, (int) timeoutMs);

        boolean seleccionado = navigateAndSelectByKeyboard(input, panel, valorExacto);
        if (!seleccionado) {
            lastAttempt = AttemptKind.FAILED;
            throw new RuntimeException("No se pudo seleccionar por teclado la opción '" + valorExacto + "'.");
        }

        input = ensureMatInputLocator();
        input.press("Tab");

        if (!waitUntilInputValueStable(input, valorExacto, 1500, 100, quietMs)) {
            lastAttempt = AttemptKind.FAILED;
            throw new AssertionError("Tras teclado, el valor no persistió. Esperado: '" + valorExacto + "', actual: '" + input.inputValue() + "'");
        }

        lastAttempt = AttemptKind.KEYBOARD;
        log("[INFO] Intento que funcionó: " + lastAttempt);
        return lastAttempt;
    }

    // ----------------------------------------------------------------------
    // Helpers (same logic you had, but generic)
    // ----------------------------------------------------------------------

    private Locator ensureMatInputLocator() {
        // (1) Find mat-form-field by label text
        Locator field = page.locator(".mat-mdc-form-field").filter(new Locator.FilterOptions().setHas(
                page.getByText(labelPattern)
        ));

        if (field.count() == 0) {
            field = page.locator("xpath=(//*[contains(@class,'mat-mdc-form-field')][.//text()[contains(.,'Marca y Modelo')]])[1]");
        }

        if (field.count() > 0) {
            field = field.first();

            Locator inputPref = field.locator(
                    "input.mat-mdc-autocomplete-trigger[role='combobox']:visible:not([disabled]), " +
                            "input.mat-mdc-input-element[role='combobox']:visible:not([disabled])"
            );
            if (inputPref.count() >= 1) return inputPref.first();

            Locator inputPh = field.locator(
                    "input[placeholder*='INGRESE MARCA/MODELO']:visible:not([disabled]), " +
                            "input[placeholder*='Ingrese marca/modelo']:visible:not([disabled])"
            );
            if (inputPh.count() >= 1) return inputPh.first();
        }

        // (2) Fallback: use the combobox anchor and search for input inside/itself
        if (comboboxAnchor != null && comboboxAnchor.count() > 0) {
            Locator fromRole = comboboxAnchor.locator(
                    "xpath=.//input[(contains(@class,'mat-mdc-autocomplete-trigger') or contains(@class,'mat-mdc-input-element')) " +
                            "and (@role='combobox' or @type='text')][not(@disabled)]"
            ).locator(":visible");
            if (fromRole.count() >= 1) return fromRole.first();

            // Sometimes comboboxAnchor IS the input
            try {
                comboboxAnchor.inputValue(); // just to verify
                return comboboxAnchor;
            } catch (Exception ignored) {}
        }

        throw new RuntimeException("No se pudo resolver el <input> real del Autocomplete.");
    }

    private void typeAndOpenPanel(Locator input, String text) {
        input.scrollIntoViewIfNeeded();
        if (!input.isVisible() || !input.isEnabled()) {
            throw new RuntimeException("El input Autocomplete no está visible/habilitado.");
        }

        input.click();
        try {
            page.keyboard().press("Control+A");
            page.keyboard().press("Backspace");
        } catch (Exception ignored) {}

        input.type(text, new Locator.TypeOptions().setDelay(25));

        String expanded = safe(input.getAttribute("aria-expanded"));
        if (!"true".equalsIgnoreCase(expanded)) {
            try { input.press("ArrowDown"); } catch (Exception ignored) {}
        }
    }

    private Locator getMaterialPanelFromInput(Locator input, int timeoutMs) {
        String panelId = input.getAttribute("aria-controls");
        if (panelId != null && !panelId.isBlank()) {
            Locator byId = page.locator("#" + panelId + ".mat-mdc-autocomplete-panel[role='listbox'], " +
                    "#" + panelId + ".mat-autocomplete-panel[role='listbox']");
            byId.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeoutMs));
            return byId.first();
        }

        Locator fallback = page.locator(
                ".cdk-overlay-container .mat-mdc-autocomplete-panel[role='listbox']:visible, " +
                        ".cdk-overlay-container .mat-autocomplete-panel[role='listbox']:visible"
        );
        fallback.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeoutMs));
        return fallback.first();
    }

    private List<String> dumpMaterialOptions(Locator panel) {
        Locator opts = panel.locator(".mat-mdc-option, mat-option, [role='option']");
        int n = opts.count();
        List<String> items = new ArrayList<>();
        log("[DEBUG] Opciones visibles en Autocomplete: " + n);
        for (int i = 0; i < n; i++) {
            String t = opts.nth(i).innerText().trim().replaceAll("\\s+", " ");
            items.add(t);
            log("  - " + t);
        }
        return items;
    }

    private Locator findOptionSmart(Locator panel, String valorBuscado) {
        String want = normalize(valorBuscado);

        Locator byRoleExact = panel.getByRole(
                AriaRole.OPTION,
                new Locator.GetByRoleOptions().setName(valorBuscado).setExact(true)
        );
        if (byRoleExact.count() > 0) return byRoleExact.first();

        Locator byTextExact = panel.getByText(valorBuscado, new Locator.GetByTextOptions().setExact(true));
        if (byTextExact.count() > 0) return byTextExact.first();

        Locator opts = panel.locator(".mat-mdc-option, mat-option, [role='option']");
        int count = opts.count();
        Locator startsWithCandidate = null;
        Locator containsCandidate = null;

        for (int i = 0; i < count; i++) {
            Locator opt = opts.nth(i);
            String txt = normalize(opt.innerText());
            if (txt.equals(want)) return opt;
            if (startsWithCandidate == null && txt.startsWith(want)) startsWithCandidate = opt;
            if (containsCandidate == null && txt.contains(want)) containsCandidate = opt;
        }

        if (startsWithCandidate != null) return startsWithCandidate;
        if (containsCandidate != null) return containsCandidate;

        throw new RuntimeException("No se encontró opción que matchee: '" + valorBuscado + "'.");
    }

    private boolean navigateAndSelectByKeyboard(Locator input, Locator panel, String valorExacto) {
        Locator opts = panel.locator(".mat-mdc-option, mat-option, [role='option']");
        int total = Math.max(opts.count(), 1);

        input.focus();

        for (int i = 0; i < total + 5; i++) {
            try { input.press("ArrowDown"); } catch (Exception ignored) {}

            String activeId = safe(input.getAttribute("aria-activedescendant"));
            if (activeId != null && !activeId.isBlank()) {
                Locator activeOpt = page.locator("#" + activeId);
                String txt = normalize(activeOpt.innerText());
                if (debug) log("[DEBUG] activeId=" + activeId + " text='" + txt + "'");
                if (txt.equals(normalize(valorExacto))) {
                    input.press("Enter");
                    waitUntilPanelHidden(panel, 2000, 100);
                    return !isPanelVisible(panel);
                }
            }

            page.waitForTimeout(80); // playwight-native wait (no Thread.sleep)
        }
        return false;
    }

    private boolean waitUntilPanelHidden(Locator panel, long timeoutMs, long intervalMs) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < timeoutMs) {
            try {
                if (panel.count() == 0) return true;
                if (!isPanelVisible(panel)) return true;
            } catch (Exception ignored) {
                return true;
            }
            page.waitForTimeout(intervalMs);
        }
        return !isPanelVisible(panel);
    }

    private boolean isPanelVisible(Locator panel) {
        try {
            if (panel.count() == 0) return false;
            return panel.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean waitUntilInputValueStable(Locator input, String expected, long timeoutMs, long intervalMs, long quietMs) {
        String want = normalize(expected);
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < timeoutMs) {
            try {
                String val = normalize(input.inputValue());
                if (val.equals(want)) {
                    if (quietMs <= 0) return true;

                    long stableStart = System.currentTimeMillis();
                    while (System.currentTimeMillis() - stableStart < quietMs) {
                        String v2 = normalize(input.inputValue());
                        if (!v2.equals(want)) return false;
                        page.waitForTimeout(Math.max(50, intervalMs));
                    }
                    return true;
                }
            } catch (Exception ignored) {}
            page.waitForTimeout(intervalMs);
        }
        return normalize(input.inputValue()).equals(want);
    }

    private String normalize(String s) {
        return s == null ? "" : s.trim().replaceAll("\\s+", " ").toUpperCase();
    }

    private String safe(String s) { return s == null ? "" : s; }

    private void log(String msg) {
        if (debug) System.out.println(msg);
    }
    public AttemptKind selectAndValidateNotEmpty(String valorExacto, long timeoutMs, long quietMs) {
        Objects.requireNonNull(valorExacto, "valorExacto");
        lastAttempt = AttemptKind.FAILED;

        Locator input = ensureMatInputLocator();
        typeAndOpenPanel(input, valorExacto);

        Locator panel = getMaterialPanelFromInput(input, (int) timeoutMs);
        Locator target = findOptionSmart(panel, valorExacto);

        // Intento 1: click
        target.scrollIntoViewIfNeeded();
        try {
            target.click();
        } catch (Exception e) {
            target.click(new Locator.ClickOptions().setForce(true));
        }

        waitUntilPanelHidden(panel, 1500, 100);
        if (isPanelVisible(panel)) {
            input.press("Enter");
            waitUntilPanelHidden(panel, 2000, 100);
        }

        input = ensureMatInputLocator();
        input.press("Tab");

        if (waitUntilInputHasAnyValue(input, 1500, 100, quietMs)) {
            lastAttempt = AttemptKind.CLICK;
            return lastAttempt;
        }

        // Intento 2: hover + enter
        typeAndOpenPanel(input, valorExacto);
        panel = getMaterialPanelFromInput(input, (int) timeoutMs);
        target = findOptionSmart(panel, valorExacto);

        target.scrollIntoViewIfNeeded();
        target.hover();
        input.focus();
        input.press("Enter");

        waitUntilPanelHidden(panel, 2000, 100);

        input = ensureMatInputLocator();
        input.press("Tab");

        if (waitUntilInputHasAnyValue(input, 1500, 100, quietMs)) {
            lastAttempt = AttemptKind.HOVER_ENTER;
            return lastAttempt;
        }

        // Intento 3: teclado
        typeAndOpenPanel(input, valorExacto);
        panel = getMaterialPanelFromInput(input, (int) timeoutMs);

        boolean seleccionado = navigateAndSelectByKeyboard(input, panel, valorExacto);
        if (!seleccionado) {
            lastAttempt = AttemptKind.FAILED;
            throw new RuntimeException("No se pudo seleccionar por teclado la opción '" + valorExacto + "'.");
        }

        input = ensureMatInputLocator();
        input.press("Tab");

        if (!waitUntilInputHasAnyValue(input, 1500, 100, quietMs)) {
            lastAttempt = AttemptKind.FAILED;
            throw new AssertionError("Se seleccionó la opción, pero el campo quedó vacío.");
        }

        lastAttempt = AttemptKind.KEYBOARD;
        return lastAttempt;
    }
    private boolean waitUntilInputHasAnyValue(Locator input, long timeoutMs, long intervalMs, long quietMs) {
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < timeoutMs) {
            try {
                String val = normalize(input.inputValue());
                if (!val.isBlank()) {
                    if (quietMs <= 0) return true;

                    long stableStart = System.currentTimeMillis();
                    while (System.currentTimeMillis() - stableStart < quietMs) {
                        String v2 = normalize(input.inputValue());
                        if (v2.isBlank()) return false;
                        page.waitForTimeout(Math.max(50, intervalMs));
                    }
                    return true;
                }
            } catch (Exception ignored) {}
            page.waitForTimeout(intervalMs);
        }

        try {
            return !normalize(input.inputValue()).isBlank();
        } catch (Exception e) {
            return false;
        }
    }
}
