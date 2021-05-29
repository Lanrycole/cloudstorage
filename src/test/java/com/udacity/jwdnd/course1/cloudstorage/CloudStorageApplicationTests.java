package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.PagesTestFiles.CredentialPageTest;
import com.udacity.jwdnd.course1.cloudstorage.PagesTestFiles.LogInPageTest;
import com.udacity.jwdnd.course1.cloudstorage.PagesTestFiles.NotePageTest;
import com.udacity.jwdnd.course1.cloudstorage.PagesTestFiles.SignUpPageTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {


    //port to serve the URL.
    @LocalServerPort
    private int port;

    //variables to data for testing.
    public static WebDriver driver;
    public String baseURL;

    //Please replace file URL with yours
    File file = new File("/Users/lanre/Desktop/test.txt");
    String filePath = file.getPath();
    String firstName = "lanre";
    String lastName = "Ore";
    String username = "lanre";
    String password = "lanre";
    String url = "www.google.com";
    String noteTitle = "Title";
    String noteDescription = "Description";


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

    @Test
    void getLoginPage() {
        driver.get(baseURL + "/login");
        assertEquals("Login", driver.getTitle());
    }


    /**
     * Authentication Tests
     * 1. Testing sign upon
     * 2.Testing signing in with wrong credentials on
     * 3.Testing signing it with correct credentials
     * 4. Testing logging out.
     */
    @Test
    void TestAuthenticationAndUserActionFlow() throws InterruptedException {

        //Only signed in user can access homePage
        driver.get(baseURL + "/home");
        assertEquals(baseURL + "/login", driver.getCurrentUrl());


        //test signing up
        driver.get(baseURL + "/signup");
        SignUpPageTest signup = new SignUpPageTest(driver);
        signup.testSignUp(firstName, lastName, username, password);

        //test invalid Log In credentials
        driver.get(baseURL + "/login");

        //Instantiating LogInTest that holds testing methods
        LogInPageTest logInPage = new LogInPageTest(driver);

        //Logging in with the wrong credentials
        logInPage.login(username + "s", password + "S");
        assertEquals(baseURL + "/login?error", driver.getCurrentUrl());

        //Logging in with the correct credentials
        logInPage.login(username, password);
        assertEquals(baseURL + "/home", driver.getCurrentUrl());

        //Logging out and making sure a logged out user cannot access home page
        logInPage.logout();
        driver.get(baseURL + "/home");
        assertEquals(baseURL + "/login", driver.getCurrentUrl());

        //Logging user back in
        driver.get(baseURL + "/login");
        logInPage.login(username, password);

        NotePageTest notePageTest = new NotePageTest(driver);

        driver.get(baseURL + "/login");
        logInPage.login(username, password);
        //Adding a new Note
        notePageTest.addNote(noteTitle, noteDescription);
        assertEquals(noteTitle, notePageTest.getNoteTitle());

        // Editing Note
        String updatedNote = notePageTest.EditNote("new Title",
                "new Description");
        assertEquals("new Title", updatedNote);


        driver.get(baseURL + "/login");
        logInPage.login(username, password);
//        deletingNotes
        notePageTest.deleteNote();
        assertThrows(NoSuchElementException.class, notePageTest::getNoteTitle);


//        Testing Credentials
//        1. Testing user adding credentials
//        2.Testing updating added Credentials
//        3. Testing deleting credentials
        driver.get(baseURL + "/login");
        logInPage.login(username, password);
        CredentialPageTest credentialPageTest = new CredentialPageTest(driver);

        //Adding a new Credential
        credentialPageTest.addCredentials(url, username, password);
        assertEquals(username, credentialPageTest.getCredentialUsername());
        assertEquals(password, credentialPageTest.getCredentialPassword());

        //Updating Credential
        Credentials updatedCredential = credentialPageTest.EditCredentials("www.yahoo.com",
                "new Username", "newpassword");
        assertEquals("new Username", updatedCredential.getUsername());
        assertEquals("www.yahoo.com", updatedCredential.getUrl());

        //Deleting Credentials
        credentialPageTest.deleteCredentials();
        assertThrows(NoSuchElementException.class, credentialPageTest::getCredentialUsername);

    }




}
