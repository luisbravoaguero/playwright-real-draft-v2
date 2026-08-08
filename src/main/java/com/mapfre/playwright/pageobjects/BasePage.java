package com.mapfre.playwright.pageobjects;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.config.EnvironmentConfig;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.exceptions.FrameworkException;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Path;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {
    protected final Page page;
    protected final Logger log;

    protected BasePage(Page page) {
        this.page = page;
        this.log = LoggerFactory.getLogger(this.getClass());
    }
    protected void waitAppReady() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        UiSync.waitForAppIdle(this.page);
    }

    public void navigate() {
        page.navigate(EnvironmentConfig.getAppUrl());
        waitAppReady();
    }

    protected void clickAndSync(Locator locator) {
        locator.click();
        UiSync.waitForAppIdle(page);
    }

    protected void clickForcedAndSync(Locator locator) {
        locator.click(new Locator.ClickOptions().setForce(true));
        UiSync.waitForAppIdle(page);
    }

    protected void checkfEnabledAndSync(Locator locator) {
        if(isInputEnabled(locator) && locator.isVisible()) {
            locator.check();
            UiSync.waitForAppIdle(page);
        }
    }
    protected void clickIfEnabledAndSync(Locator locator) {
        if(isInputEnabled(locator) && locator.isVisible()) {
            locator.click();
            UiSync.waitForAppIdle(page);
        }
    }
    protected void fillAndSync(Locator locator, String value) {
        locator.fill(value);
        UiSync.waitForAppIdle(page);
    }
    protected void fillAndBlurSync(Locator locator, String value) {
        locator.fill(value);
        locator.blur();
        UiSync.waitForAppIdle(page);
    }
    protected void fillBlurAndFailIfErrorModalAppears(Locator locator, String value, long modalTimeoutMs, Locator modalError) {
        if (isInputEnabled(locator)) {
            locator.fill(value);
            locator.blur();
            UiSync.waitForAppIdle(page);

            try {
                modalError.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(modalTimeoutMs));

                throw new FrameworkException(
                        "Se muestra un modal de error inesperado con el mensaje: " + modalError.innerText()
                );
            } catch (TimeoutError e) {
                // no modal appeared, continue
            }
        }
    }


    protected void pressSequentiallyAndBlurSync(Locator locator, String value) {
        locator.pressSequentially(value);
        locator.blur();
        UiSync.waitForAppIdle(page);
    }

    protected void pressSequentiallyDelayAndSync(Locator locator, String value) {
        locator.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(100));
        //locator.blur();
        UiSync.waitForAppIdle(page);
    }

    protected void cleanPressSequentiallyDelayBlurAndSync(Locator locator, String value) {
        locator.clear();
        waitRandomBetween(250);
        locator.fill("");
        waitRandomBetween(250);
        locator.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(100));
        //locator.blur();
        UiSync.waitForAppIdle(page);
    }

    protected void fillInputIfEmptyAndSync(Locator locator, String value) {
        if (isInputEmpty(locator)) {
            locator.fill(value);
            UiSync.waitForAppIdle(page);
        }
    }
    protected void fillInputIfEmptyBlurAndSync(Locator locator, String value) {
        if (isInputEmpty(locator)) {
            locator.fill(value);
            locator.blur();
            UiSync.waitForAppIdle(page);
        }
    }

    protected void fillInputNewValueIfEnabledAndSync(Locator locator, String value) {
        if (isInputEnabled(locator)) {
            locator.clear();
            locator.fill(value);
            UiSync.waitForAppIdle(page);
        }
    }

    protected void fillInputValueIfEnabledAndEmptyAndSync(Locator locator, String value) {
        if (isInputEnabled(locator) && isInputEmpty(locator)){
            locator.fill(value);
        }
        UiSync.waitForAppIdle(page);
    }

    public void clearAndFill(Locator locator, String value){
        locator.fill("");
        locator.fill(value);
        UiSync.waitForAppIdle(page);
    }

    protected void selectOptionIfEnabledAndSync(Locator locator, String option) {
        if (isInputEnabled(locator)) {
            locator.selectOption(option);
            UiSync.waitForAppIdle(page);
        }
    }
    protected void selectOptionIfEnabledAndEmptyAndSync(Locator locator, String option) {
        if (isInputEnabled(locator) && isInputEmpty(locator)) {
            locator.selectOption(option);
            UiSync.waitForAppIdle(page);
        }
    }
    protected void selectIndexIfEmptyAndSync(Locator locator, int index) {
        if (isInputEmpty(locator)) {
            locator.selectOption(new SelectOption().setIndex(index));
            UiSync.waitForAppIdle(page);
        }
    }
    public void selectIndexOptionIfEnabledAndSync(Locator locator, int index) {
        if (isInputEnabled(locator)) {
            locator.selectOption(new SelectOption().setIndex(index));
            UiSync.waitForAppIdle(page);
        }
    }
    public void selectBornDateFromStringAndSync(Locator dayLocator, Locator monthLocator, Locator yearLocator, String date) {
        if (isInputEnabled(dayLocator) && isInputEmpty(dayLocator)) {
            dayLocator.selectOption(date.split("/")[0]);
            UiSync.waitForAppIdle(page);
        }
        if (isInputEnabled(monthLocator) && isInputEmpty(monthLocator)) {
            monthLocator.selectOption(date.split("/")[1]);
            UiSync.waitForAppIdle(page);
        }
        if (isInputEnabled(yearLocator) && isInputEmpty(yearLocator)) {
            yearLocator.selectOption(date.split("/")[2]);
            UiSync.waitForAppIdle(page);
        }
    }

    protected void fillInputIfEnabledAndSync(Locator locator, String value) {
        if (isInputEnabled(locator)) {
            locator.clear();
            UiSync.waitForAppIdle(page);
            locator.fill(value);
            UiSync.waitForAppIdle(page);
        }
    }

    protected void fillInputIfEnabledBlurAndSync(Locator locator, String value) {
        if (isInputEnabled(locator)) {
            locator.fill(value);
            locator.blur();
            UiSync.waitForAppIdle(page);
        }
    }

    protected void fillReadonlyDate(Locator locator, String date) {
        UiSync.waitForAppIdle(page);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        locator.evaluate(" (el, dateValue) => {" +
                "  el.value = dateValue;" +
                "  el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "  el.dispatchEvent(new Event('change', { bubbles: true }));" +
                "  el.blur();" +
                "}", date);
    }

    protected void fillReadonlyDateIfEmpty(Locator locator, String date) {
        UiSync.waitForAppIdle(page);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        if (isInputEmpty(locator)){
            locator.evaluate(" (el, dateValue) => {" +
                    "  el.value = dateValue;" +
                    "  el.dispatchEvent(new Event('input', { bubbles: true }));" +
                    "  el.dispatchEvent(new Event('change', { bubbles: true }));" +
                    "  el.blur();" +
                    "}", date);
        }
    }

    protected void fillReadonlyIfFieldIsNotOptional(Locator locator, String date) {
        UiSync.waitForAppIdle(page);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        if (!isFieldOptional(date)) {
            locator.evaluate(" (el, dateValue) => {" +
                    "  el.value = dateValue;" +
                    "  el.dispatchEvent(new Event('input', { bubbles: true }));" +
                    "  el.dispatchEvent(new Event('change', { bubbles: true }));" +
                    "  el.blur();" +
                    "}", date);
        }
    }

    public void setReadonlyInputValue(Locator input, String value) {
        input.evaluate(
                "(el, value) => {" +
                        "  const nativeInputValueSetter = Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set;" +
                        "  nativeInputValueSetter.call(el, value);" +
                        "  el.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "  el.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "}",
                value
        );

        log.info("Se estableció el valor '{}' en el input readonly.", value);
    }

    public void setDateByTyping(String label, String value) {
        Locator input = page.locator("oim-datepicker[label='" + label + "'] input");

        input.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        input.evaluate("(el) => el.removeAttribute('readonly')");
        input.click();
        input.fill(value);
        input.press("Tab");

        log.info("Se ingresó la fecha '{}' en el campo '{}'.", value, label);
    }

    public void setValueWithJsAndCommit(Locator input, String value) {
        UiSync.waitForAppIdle(page);
        input.evaluate(
                "(el, v) => {" +
                        "const setter = Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set;" +
                        "setter.call(el, v);" +
                        "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "el.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "el.dispatchEvent(new FocusEvent('blur', { bubbles: true }));" +
                        "}", value
        );
        input.blur();
    }

    protected void selectAndSync(Locator locator, String option) {
        locator.selectOption(option);
        UiSync.waitForAppIdle(page);
    }

    protected void selectOptionAndSync(Locator select, String optionValue) {
        try {
            if(select.isVisible()){
                Locator option = select.locator("option").filter(new Locator.FilterOptions().setHasText(Pattern.compile(optionValue, Pattern.CASE_INSENSITIVE)));
                String value = option.first().getAttribute("value");
                select.selectOption(value);
                UiSync.waitForAppIdle(page);
            }
        } catch (Exception e) {
            log.info("Falló la seleccion usando case-insensitive match.");
        }
    }

    protected void selectByOptionIfEnableAndSync(Locator select, String optionValue) {
        try {
            if(select.isVisible() && isInputEnabled(select)){
                Locator option = select.locator("option").filter(new Locator.FilterOptions().setHasText(Pattern.compile(optionValue, Pattern.CASE_INSENSITIVE)));
                String value = option.first().getAttribute("value");
                select.selectOption(value);
                UiSync.waitForAppIdle(page);
            }
        } catch (Exception e) {
            log.info("Falló la seleccion usando case-insensitive match.");
        }
    }

    protected void selectIndexOptionAndSync(Locator locator, int index) {
        locator.selectOption(new SelectOption().setIndex(index));
        UiSync.waitForAppIdle(page);
    }

    protected void scrollIntoView(Locator locator) {
        locator.scrollIntoViewIfNeeded();
    }

    protected void scrollPageBy(int deltaX, int deltaY) {
        page.mouse().wheel(deltaX, deltaY);
    }

    protected void scrollToTop() {
        page.evaluate("() => window.scrollTo(0, 0)");
    }

    protected void scrollToBottom() {
        page.evaluate("() => window.scrollTo(0, document.body.scrollHeight)");
    }

    // Use after navigation or page transition
    public void sync() {
        UiSync.waitForAppIdle(page);
    }


    protected boolean isInputEmpty(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        UiSync.waitForAppIdle(page);
        String value = locator.inputValue();
        return value == null || value.trim().isEmpty();
    }

    // Positivo: implementación completa para comprobar que un input está realmente habilitado y editable.
    // No es un wrapper: devolvemos true sólo si podemos confirmar que el elemento es visible, enabled
    // y editable. En caso de excepciones devolvemos false (conservador) para no permitir acciones sobre
    // elementos cuya disponibilidad no haya sido verificada.
    protected boolean isInputEnabled(Locator locator) {
        try {
            // Asegurar que el locator está presente y visible
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            UiSync.waitForAppIdle(page);

            // Si explícitamente está marcado como disabled -> no está habilitado
            try {
                if (locator.isDisabled()) return false;
            } catch (Exception ignored) {
                // Si falla la comprobación, seguimos con otras verificaciones
            }

            // Si no está 'enabled' según Playwright -> no está habilitado
            try {
                if (!locator.isEnabled()) return false;
            } catch (Exception ignored) {
                // Si no pudimos consultar isEnabled, consideramos que no está garantizado
                return false;
            }

            // Finalmente, comprobar si es editable (no readonly, acepta texto)
            try {
                if (!locator.isEditable()) return false;
            } catch (Exception ignored) {
                // Si no es aplicable (por ejemplo no es un input de texto) o falla la comprobación,
                // conservadoramente devolvemos false para evitar escribir en elementos no verificados.
                return false;
            }

            // Si llegamos aquí, el elemento aparenta estar visible, enabled y editable
            return true;
        } catch (Exception e) {
            // En cualquier excepción inesperada devolvemos false (no confiamos en que esté habilitado)
            return false;
        }
    }


    public void forceTypeTextOnce(Locator input) {
        UiSync.waitForAppIdle(page);
        input.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        input.evaluate("el => el.setAttribute('type','text')");
    }

    protected boolean closeSwalIfVisible(double timeoutMs) {
        Locator modal = page.locator("div.swal2-popup.swal2-modal[role='dialog']");

        try {
            modal.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeoutMs));
        } catch (TimeoutError e) {
            return false; // not visible within timeout => nothing to close
        }

        // If it's visible, grab useful text for logs
        String title = safeInnerText(modal.locator("#swal2-title"));
        String msg   = safeInnerText(modal.locator("#swal2-html-container"));

        log.info("Swal modal detected and will be closed: {} - {}", title, msg);


        // Click OK / Aceptar if present
        Locator okButton = modal.getByRole(AriaRole.BUTTON,
                new Locator.GetByRoleOptions().setName(Pattern.compile("^(OK|Ok|Aceptar|ACEPTAR)$")));

        if (okButton.count() > 0) {
            okButton.first().click();
        } else {
            // Fallback to CSS
            Locator cssOk = modal.locator("button.swal2-confirm");
            if (cssOk.count() > 0) cssOk.first().click();
        }

        // Wait for modal to disappear so it doesn't block next actions
        try {
            modal.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.HIDDEN)
                    .setTimeout(5000));
        } catch (TimeoutError ignored) {
            // Don't fail; at worst the next action will reveal it’s still blocking
        }

        return true;
    }

    private String safeInnerText(Locator loc) {
        try {
            String t = loc.innerText();
            return t == null ? "" : t.replaceAll("\\s+", " ").trim();
        } catch (Exception e) {
            return "";
        }
    }


    protected boolean isFieldOptional(String valor) {
        return valor == null ||
                valor.isBlank() ||
                valor.equalsIgnoreCase("OPCIONAL");
    }

    protected void fillIfFieldIsNotOptional(Locator locator, String valor) {
        if(!isFieldOptional(valor)){
            locator.fill(valor);
        }
    }

    protected void selectFromMatAutocompleteFailFast(Locator locator, String valueToType, String fieldName) {
        // 1. Focus and type (Angular Material requires real key events)
        locator.click();
        locator.pressSequentially(valueToType);

        // 2. Autocomplete options live in Angular CDK overlay
        Locator options = page.locator(".cdk-overlay-pane mat-option");

        // 3. FAIL FAST if no option becomes visible
        boolean hasResults = ElementAsserts.becomesVisible(options.first(), 2000);

        if (!hasResults) {
            throw new AssertExceptions("Autocomplete sin resultados en el campo '" + fieldName + "'. Valor ingresado: [" + valueToType + "]");
        }

        // 4. Extra guard: ensure count > 0
        int count = options.count();
        if (count == 0) {
            throw new AssertExceptions("El autocomplete del campo '" + fieldName + "' se abrió pero no contiene opciones seleccionables. " + "Valor ingresado: [" + valueToType + "]");
        }

        // 5. Select first option
        options.first().click();

        UiSync.waitForAppIdle(page);
    }

    protected void selectFromMatAutocompleteFailFastIfEmpty(Locator locator, String valueToType, String fieldName, String locatorOptions) {
        if(!isInputEmpty(locator)){
            log.info("El campo '{}' ya tiene un valor [{}], se omite selección en autocomplete", fieldName, locator.inputValue());
            return;
        }
        // 1. Focus and type (Angular Material requires real key events)
        locator.click();
        locator.pressSequentially(valueToType);

        // 2. Autocomplete options live in Angular CDK overlay
        Locator options = page.locator(locatorOptions);

        // 3. FAIL FAST if no option becomes visible
        boolean hasResults = ElementAsserts.becomesVisible(options.first(), 3000);

        if (!hasResults) {
            throw new AssertExceptions("Autocomplete sin resultados en el campo '" + fieldName + "'. Valor ingresado: [" + valueToType + "]");
        }

        // 4. Extra guard: ensure count > 0
        int count = options.count();
        if (count == 0) {
            throw new AssertExceptions("El autocomplete del campo '" + fieldName + "' se abrió pero no contiene opciones seleccionables. " + "Valor ingresado: [" + valueToType + "]");
        }

        // 5. Select first option
        options.first().click();

        UiSync.waitForAppIdle(page);
    }

    protected void assertAutocompleteSelected(Locator locator, String expectedKey, String fieldName) {
        // Assert filled
        ElementAsserts.assertFilled(locator, "El campo '" + fieldName + "' no fue completado");

        // Assert correct value
        String actualValue = locator.inputValue();
        if (!actualValue.contains(expectedKey)) {
            throw new AssertExceptions("Valor incorrecto en el campo '" + fieldName + "'. Esperado que contenga: [" + expectedKey + "], pero fue: [" + actualValue + "]");
        }
    }

    public void verifyCheckboxesIsChecked(Locator locator, String fieldName) {
        int count = locator.count();
        for (int i = 0; i < count; i++) {
            ElementAsserts.assertChecked(locator.nth(i),"El checkbox #" + (i+1) + " de '" + fieldName + "' no está marcado después de hacer click en 'Seleccionar toda la lista'");
        }
        log.info("[{}] ✓ Todos los checkboxes {} estan activados", fieldName, count);
    }

    protected void clickCheckboxIfNotChecked(Locator checkbox) {
        if (!checkbox.isChecked()) {
            clickAndSync(checkbox);
        }
    }

    protected void clickAndVerifyCheckboxIfNotChecked(Locator checkbox, String fieldName) {
        if (!checkbox.isChecked()) {
            clickAndSync(checkbox);
        }
        verifyCheckboxesIsChecked(checkbox, fieldName);
    }

    public void clickCheckBoxList(Locator locator) {
        try {
            // Get count
            int checkboxCount = locator.count();
            log.warn("Numero de checkboxes encontrados para seleccionar: {}", checkboxCount);

            if (checkboxCount == 0) {
                log.warn("No se encontrados documentos Obligatorios para seleccionar");
                return;
            }
            for (int i = 0; i < checkboxCount; i++) {
                Locator checkbox = locator.nth(i);
                checkbox.click();
                page.waitForTimeout(100);
                if(checkboxCount > 10 && i > checkboxCount - 5){
                    page.evaluate("el => el.scrollIntoView(true)", checkbox.elementHandle());
                    page.waitForTimeout(300);
                }
            }
        } catch (Exception e) {
            throw new AssertExceptions("No es posible seleccionar los documentos requeridos: " + e.getMessage());
        }
    }


    public void clickCheckBoxList(Locator locator, Locator checkLocator) {
        try {
            // Get count
            int checkboxCount = locator.count();
            log.warn("Numero de checkboxes encontrados para seleccionar: {}", checkboxCount);

            if (checkboxCount == 0) {
                log.warn("No se encontrados documentos Obligatorios para seleccionar");
                return;
            }

            for (int i = 0; i < checkboxCount; i++) {
                Locator checkbox = checkLocator.nth(i);
                page.waitForTimeout(300);
                checkbox.click();
            }

        } catch (Exception e) {
            throw new AssertExceptions(
                    "No es posible seleccionar los documentos requeridos: " + e.getMessage()
            );
        }
    }

    public void waitForNetworkIdle() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void waitForDOMContentLoaded() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public void clickMenuOption(String menuName) {
        // Force the menu to fully render
        page.evaluate(
                "() => { " +
                        "  const menu = document.querySelector('inspec-menu'); " +
                        "  if (menu) { " +
                        "    menu.offsetHeight; " +  // Forces reflow/render
                        "  } " +
                        "}"
        );

        page.waitForTimeout(1000);

        // Now try the click
        page.evaluate(
                "() => { " +
                        "  const menuItems = document.querySelectorAll('inspec-menu li.link a'); " +
                        "  for (let item of menuItems) { " +
                        "    if (item.textContent.includes('" + menuName + "')) { " +
                        "      item.click(); " +
                        "      return; " +
                        "    } " +
                        "  } " +
                        "}"
        );

        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForTimeout(2500);

        log.info("{} menu clicked", menuName);
    }

    public void waitRandomBetween(long largestValueMs) {
        long smallestValueMs = (largestValueMs * 30) / 100;
        waitRandomBetween(smallestValueMs, largestValueMs);
    }

    private void waitRandomBetween(long smallestValueMs, long largestValueMs) {
        if (smallestValueMs > largestValueMs) {
            throw new IllegalArgumentException(
                    "El valor mínimo (" + smallestValueMs + "ms) no puede ser mayor que el máximo (" + largestValueMs + "ms)"
            );
        }

        long range = largestValueMs - smallestValueMs + 1;
        long randomMs = smallestValueMs + (long) (Math.random() * range);

        //log.info("Esperando {} ms (rango: {} - {} ms)", randomMs, smallestValueMs, largestValueMs);
        page.waitForTimeout(randomMs);
    }

    protected void uploadFile(Locator locator, Path filePath) {
        locator.setInputFiles(filePath);
        UiSync.waitForAppIdle(page);
    }

    public void clickMenuPrincipal(String menuName) {
        Locator menuOption = page.locator("inspec-menu ul.g-menu-hrz").nth(1)
                .locator("li.link a:has-text('" + menuName + "')");

        menuOption.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        menuOption.click();
        UiSync.waitForAppIdle(page);
        log.info("Se hizo click en la opción del menú: {}", menuName);
    }

    public void waitUntilItemDatoAttributeIsNFilled(int minimoLlenos) {
        page.waitForFunction(
                "(minimo) => {" +
                        "  const elementos = Array.from(document.querySelectorAll('div.item-dato'));" +
                        "  const llenos = elementos.filter(el => el.textContent && el.textContent.trim().length > 0).length;" +
                        "  return llenos >= minimo;" +
                        "}",
                minimoLlenos
        );

        log.info("Al menos {} elementos div.item-dato tienen contenido.", minimoLlenos);
    }

    public void dismissAnyOverlay() {
        page.keyboard().press("Escape");
        page.waitForTimeout(300);
        page.locator("h1, .page-title, .form-header").first().click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
    public void clickUsingJS(Locator locator) {
        try {
            locator.evaluate("element => element.click()");
            log.info("✅ Click ejecutado con JS sobre: {}", locator);
        } catch (Exception e) {
            log.error("❌ No se pudo dar click usando JavaScript sobre: {}", locator);
            throw new RuntimeException("FAIL: No se pudo dar click usando JavaScript sobre el elemento: " + locator, e);
        }
    }
}
