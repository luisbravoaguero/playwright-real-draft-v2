package com.mapfre.utils.validators;

import com.mapfre.exceptions.FrameworkException;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RadioGroupValidator {

    private static final Logger log = LoggerFactory.getLogger(RadioGroupValidator.class);

    private final Page page;
    private final String cardLabel;

    public RadioGroupValidator(Page page, String cardLabel) {
        this.page = page;
        this.cardLabel = cardLabel;
    }

    /**
     * Validate that at least one radio button is selected in the card
     * Your approach: Find card → Get radios → Loop → Validate
     */
    public void assertAtLeastOneRadioIsSelected() {
        // Step 1: Find the card container
        Locator mainCard = page.locator(
                        String.format(
                                "span:has-text('%s')",
                                cardLabel
                        )
                ).locator("xpath=ancestor::div[contains(@class, 'col-md')]")
                .first();

        if (mainCard.count() == 0) {
            throw new FrameworkException(
                    String.format("Radio group card '%s' not found on the page", cardLabel)
            );
        }

        // Step 2: Get all radio buttons inside the card
        Locator radioButtons = mainCard.locator("mat-radio-button");
        int totalRadios = radioButtons.count();

        if (totalRadios == 0) {
            throw new FrameworkException(
                    String.format("No radio buttons found in '%s' card", cardLabel)
            );
        }

        // Step 3: Loop through each radio to find if any is checked
        boolean anyChecked = false;
        String checkedOption = null;

        for (int i = 0; i < totalRadios; i++) {
            Locator currentRadio = radioButtons.nth(i);
            String isCheckedAttr = currentRadio.getAttribute("ng-reflect-checked");

            if ("true".equals(isCheckedAttr)) {
                anyChecked = true;
                checkedOption = currentRadio.locator("label").textContent().trim();
                break;  // Found it, stop looping
            }
        }

        // Step 4: Fail if none are checked
        if (!anyChecked) {
            throw new FrameworkException(
                    String.format(
                            "Radio group '%s' validation FAILED: " +
                                    "Expected at least one radio to be selected, but none are checked. " +
                                    "Total options available: %d",
                            cardLabel,
                            totalRadios
                    )
            );
        }

        // Success
        log.info("✅ Radio group '{}' validation passed. Selected: '{}'",
                cardLabel, checkedOption);
    }

    /**
     * Get the currently selected radio button label
     */
    public String getSelectedRadioLabel() {
        Locator mainCard = page.locator(
                String.format("div:has(span:has-text('%s'))", cardLabel)
        );

        Locator checkedRadio = mainCard.locator("mat-radio-button[ng-reflect-checked='true']");

        if (checkedRadio.count() == 0) {
            return null;
        }

        return checkedRadio.locator("label").textContent().trim();
    }

    public Locator mainCardLocator() {
        return page.locator(
                String.format("div:has(span:has-text('%s'))", cardLabel)
        );
    }
}