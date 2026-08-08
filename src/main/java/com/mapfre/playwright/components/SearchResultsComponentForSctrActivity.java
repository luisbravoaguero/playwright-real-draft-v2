package com.mapfre.playwright.components;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import static com.mapfre.utils.debug.DebugLocatorUtils.*;

import java.util.regex.Pattern;

public class SearchResultsComponentForSctrActivity {

    private final Page page;
    private final Locator root;

    private static final String CARD_CSS = "div.g-box.g-box--myd--no-padding";
    private static final String CODE_CSS = "div.item-label";
    private static final String DESC_CSS = "div.item-dato";
    private static final String SELECTED_ICON_CSS = "span.ico-mapfre_184_circleCheck.ico-verde";

    public SearchResultsComponentForSctrActivity(Page page) {
        this.page = page;
        this.root = page.locator("div.gnContentAuto-xlg, div.gnContentAuto-lg");
    }

    /** Finds the result card by exact code text (ignores spaces). Works for both layouts. */
    private Locator cardByCode(String code) {
        /*String xpath =
                ".//div[contains(@class,'g-box--myd--no-padding')]" +
                        "[.//div[contains(@class,'item-label') and normalize-space(.)='" + code + "']]";
        return root.locator("xpath=" + xpath).first();*/
        /*Pattern exact = Pattern.compile("^\\s*" + Pattern.quote(code) + "\\s*$");
        Locator cards = root.locator(CARD_CSS);
        System.out.println("cards.count = "+cards.count());
        return cards.filter(new Locator.FilterOptions().setHas(
                root.locator("div.item-label").filter(
                        new Locator.FilterOptions().setHasText(exact)
                )
        )).first();*/
        return root.locator(
                "div.g-box.g-box--myd--no-padding" +
                        ":has(div.item-label:has-text('" + code + "'))"
        ).first();
    }

    public Locator codeLocator(String code) {
        return cardByCode(code).locator(CODE_CSS);
    }

    public Locator descriptionLocator(String code) {
        return cardByCode(code).locator(DESC_CSS);
    }

    public Locator selectedIconLocator(String code) {
        return cardByCode(code).locator(SELECTED_ICON_CSS);
    }

    /** Click the result and verify the green icon appears (selected). */
    public void selectByCode(String code) {
        Locator card = cardByCode(code);
        //System.out.println("card.count = "+card.count());
        //debugLocator("roots",page.locator("div.gnContentAuto-xlg, div.gnContentAuto-lg"));
        //debugLocator("card",card);
        ElementAsserts.assertVisible(card, "Result card with code '" + code + "' should be visible");

        // Click strategy that works for both HTMLs:
        // Prefer clicking the description area if present; otherwise click the row container.
        Locator clickable = card.locator(DESC_CSS);
        if (clickable.count() > 0) {
            clickable.first().click();
        } else {
            card.locator("ul.g-list").first().click();
        }
        UiSync.waitForAppIdle(page);

        // Verify selected icon appears (it may be inserted dynamically)
        Locator icon = selectedIconLocator(code);
        icon.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    /** Useful for logs/reports */
    public String summary(String code) {
        String c = codeLocator(code).innerText().trim();
        String d = descriptionLocator(code).innerText().trim();
        return c + " | " + d;
    }

    /** Immediate check (no waiting) */
    public boolean isSelectedNow(String code) {
        return selectedIconLocator(code).isVisible();
    }
}