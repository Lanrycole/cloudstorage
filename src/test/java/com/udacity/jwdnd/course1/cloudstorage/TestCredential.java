package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.PagesTestFiles.CredentialPageTest;
import com.udacity.jwdnd.course1.cloudstorage.PagesTestFiles.LogInPageTest;
import com.udacity.jwdnd.course1.cloudstorage.PagesTestFiles.NotePageTest;
import com.udacity.jwdnd.course1.cloudstorage.PagesTestFiles.SignUpPageTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestCredential {

    //port to serve the URL.
    @LocalServerPort
    private int port;

    //variables to data for testing.
    public static WebDriver driver;
    public String baseURL;
    String firstName = "firstname";
    String lastName = "lastname";
    String username = "username";
    String password = "password";
    String url = "www.google.com";


    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        baseURL = baseURL = "http://localhost:" + port;

    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    //Adding credentials
//    @Test
    public void addCredentials() throws InterruptedException {
        driver.get(baseURL + "/signup");
        SignUpPageTest signup = new SignUpPageTest(driver);
        signup.testSignUp(firstName, lastName, username, password);

        LogInPageTest logInPage = new LogInPageTest(driver);

        driver.get(baseURL + "/login");
        logInPage.login(username, password);
        CredentialPageTest credentialPageTest = new CredentialPageTest(driver);

        //Adding a new Credential
        credentialPageTest.addCredentials(url, username, password);
        assertEquals(username, credentialPageTest.getCredentialUsername());
        assertEquals(password, credentialPageTest.getCredentialPassword());
    }

    //Editing credentials
//    @Test
    public void editCredentials() throws InterruptedException {

        LogInPageTest logInPage = new LogInPageTest(driver);
        driver.get(baseURL + "/login");
        logInPage.login(username, password);
        //Updating Credential
        CredentialPageTest credentialPageTest = new CredentialPageTest(driver);
        Credentials updatedCredential = credentialPageTest.EditCredentials("www.yahoo.com",
                "new Username", "newpassword");
        assertEquals("new Username", updatedCredential.getUsername());
        assertEquals("www.yahoo.com", updatedCredential.getUrl());
    }

    //Deleting credentials
//    @Test
    public void deleteCredential() {
        LogInPageTest logInPage = new LogInPageTest(driver);
        driver.get(baseURL + "/login");
        logInPage.login(username, password);
        CredentialPageTest credentialPageTest = new CredentialPageTest(driver);

        credentialPageTest.deleteCredentials();
        assertThrows(NoSuchElementException.class, credentialPageTest::getCredentialUsername);

    }
}
