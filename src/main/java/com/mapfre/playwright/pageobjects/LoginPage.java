package com.mapfre.playwright.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mapfre.config.ConfigManager;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {

    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    String user = ConfigManager.getProperty("user.email");
    String passwordConfig = ConfigManager.getProperty("user.password");


    public LoginPage(Page page) {
        super(page);
        this.usernameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Ingrese su usuario"));
        this.passwordInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Ingresa tu contraseña"));
        this.loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Ingresar"));
    }

    public void loginOim() {
        usernameInput.fill(user);
        passwordInput.fill(passwordConfig);
        clickAndSync(loginButton);
    }


}
