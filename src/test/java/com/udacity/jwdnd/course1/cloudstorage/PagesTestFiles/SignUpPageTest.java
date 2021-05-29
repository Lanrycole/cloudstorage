package com.udacity.jwdnd.course1.cloudstorage.PagesTestFiles;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPageTest {

    @FindBy(css = "#inputFirstName")
    private WebElement firstNameField;

    @FindBy(css = "#inputLastName")
    private WebElement lastNameField;

    @FindBy(css = "#inputUsername")
    private WebElement usernameField;

    @FindBy(css = "#inputPassword")
    private WebElement passwordField;

    @FindBy(css = "#signup-button")
    private WebElement submitButton;

    /**
     *
     * @param webDriver
     */

    public SignUpPageTest(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    /**
     *
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     */
    public void testSignUp(String firstName, String lastName,
                       String username, String password) {
        this.firstNameField.sendKeys(firstName);
        this.lastNameField.sendKeys(lastName);
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.click();
    }

}
