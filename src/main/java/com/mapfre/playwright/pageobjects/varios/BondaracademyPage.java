package com.mapfre.playwright.pageobjects.varios;

import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class BondaracademyPage extends BasePage {

    private final Locator title;
    private final Locator modalAndOverloadLink;
    private final Locator dialogLink;
    private final Locator enterNameButton;
    private final Locator enterNameInput;
    private final Locator submitButton;
    private final Locator friendlyReminderLabel;
    private final Locator okButton;
    public BondaracademyPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Home"));
        this.modalAndOverloadLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Modal & Overlays"));
        this.dialogLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Modal & Overlays"));
        this.enterNameButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("ENTER NAME"));
        this.enterNameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Name"));
        this.submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("SUBMIT"));
        this.friendlyReminderLabel = page.getByText("Friendly reminder");
        this.okButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK"));

    }

    public void fillName(String name) {
        clickAndSync(modalAndOverloadLink);
        clickAndSync(dialogLink);
        clickAndSync(enterNameButton);
        page.addLocatorHandler(friendlyReminderLabel, locator -> {
            log.info(">>> LOCATOR HANDLER EXECUTED <<<");
            okButton.click();
        });
        fillAndSync(enterNameInput, name);
        clickAndSync(submitButton);
    }
}
