package com.udacity.jwdnd.course1.cloudstorage;

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
import org.springframework.boot.test.web.server.LocalServerPort;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestNote {

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

    //Addding notes
//    @Test
    public void addNote() throws InterruptedException {
        driver.get(baseURL + "/signup");
        SignUpPageTest signup = new SignUpPageTest(driver);
        signup.testSignUp(firstName, lastName, username, password);

        //Instantiating LogInTest that holds testing methods
        LogInPageTest logInPage = new LogInPageTest(driver);

        driver.get(baseURL + "/login");

        logInPage.login(username, password);
        NotePageTest notePageTest = new NotePageTest(driver);
        //Adding a new Note
        notePageTest.addNote(noteTitle, noteDescription);
        assertEquals(noteTitle, notePageTest.getNoteTitle());

    }

    //Editing notes
//    @Test
    public void editNote() throws InterruptedException {

        LogInPageTest logInPage = new LogInPageTest(driver);
        driver.get(baseURL + "/login");
        logInPage.login(username, password);

        NotePageTest notePageTest = new NotePageTest(driver);

        String updatedNote = notePageTest.editNote("new Title",
                "new Description");
        assertEquals("new Title", updatedNote);
    }

    //deleting notes
//    @Test
    public void deleteNote() {

        LogInPageTest logInPage = new LogInPageTest(driver);
        driver.get(baseURL + "/login");
        logInPage.login(username, password);

        NotePageTest notePageTest = new NotePageTest(driver);

        notePageTest.deleteNote();
        assertThrows(NoSuchElementException.class, notePageTest::getNoteTitle);

    }
}
