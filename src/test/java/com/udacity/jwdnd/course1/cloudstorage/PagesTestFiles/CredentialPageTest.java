package com.udacity.jwdnd.course1.cloudstorage.PagesTestFiles;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import org.apache.http.client.CredentialsProvider;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialPageTest {

    private final WebDriver driver;
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "add-credential")
    private WebElement addCredential;

    @FindBy(id = "credential-url")
    private WebElement url;

    @FindBy(id = "credential-username")
    private WebElement username;

    @FindBy(id = "credential-password")
    private WebElement password;

    @FindBy(id = "save-credentials")
    private WebElement submitCredentials;

    @FindBy(id = "urlText")
    private WebElement urlText;

    @FindBy(id = "usernameText")
    private WebElement usernameText;

    @FindBy(id = "passwordText")
    private WebElement passwordText;


    @FindBy(id = "edit-credential")
    private WebElement editCredentials;

    @FindBy(css = "#delete-credential")
    private WebElement deleteCredentials;

    @FindBy(css = ".return")
    private WebElement returnToPage;


    public CredentialPageTest(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addCredentials(String url, String username, String password) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credentialTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.addCredential);

        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + url + "';", this.url);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", this.username);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", this.password);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.submitCredentials);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.returnToPage);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credentialTab);
        Thread.sleep(2000);

    }


    public String getCredentialUsername() {

        Credentials credentials = new Credentials();
        credentials.setUsername(usernameText.getText());
        return credentials.getUsername();
    }
   public String getCredentialPassword() {

        Credentials credentials = new Credentials();
        credentials.setUrl(urlText.getText());
        credentials.setPassword(passwordText.getText());
        credentials.setUsername(usernameText.getText());

        return credentials.getUsername();
    }

    public Credentials EditCredentials(String newUrl, String newUserName, String newPassword) throws InterruptedException {
        Credentials credentials = new Credentials();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credentialTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.editCredentials);

        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newUrl + "';", this.url);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newUserName + "';", this.username);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newPassword + "';", this.password);

        credentials.setUrl(newUrl);
        credentials.setPassword(newPassword);
        credentials.setUsername(newUserName);
        Thread.sleep(2000);
        return credentials;
    }

    public void deleteCredentials() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credentialTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.deleteCredentials);
    }


}
