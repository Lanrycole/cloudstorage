package com.eazybooks.PagesTestFiles;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.security.core.Authentication;

public class LogInPageTest {

    @FindBy(css = "#inputUsername")
    private WebElement usernameField;

    @FindBy(css = "#inputPassword")
    private WebElement passwordField;

    @FindBy(css = "#login-button")
    private WebElement submitButton;


    @FindBy(css = "#logout")
    private WebElement logoutButton;

    Authentication authentication;

    /**
     *
     * @param webDriver
     */

    public LogInPageTest(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    /**
     *
     * @param username
     * @param password
     * Logging in
     */
    public void login(String username, String password) {
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.click();
    }

    /**
     * Logging out
     */
    public void logout() {
        this.logoutButton.click();

    }
}
