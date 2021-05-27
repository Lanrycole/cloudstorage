package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.Model.Files;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    public static WebDriver driver;
    public String baseURL;
    File file = new File("/Users/lanre/Desktop/test.txt");
    String filePath = file.getPath();


    @FindBy(css = "#uploaded-file")
    private WebElement uploadedFile;


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
    public void getLoginPage() {
        driver.get(baseURL + "/login");
        assertEquals("Login", driver.getTitle());
    }


    @Test
    public void TestApplicationFeatures() throws InterruptedException {
        String firstName = "lanre";
        String lastName = "Ore";
        String username = "lanre";
        String password = "lanre";

        String noteTitle= "Title";
        String noteDescription = "Description";


        driver.get(baseURL + "/signup");

        SignUpPageTest signup = new SignUpPageTest(driver);
        signup.testSignUp(firstName, lastName, username, password);

        driver.get(baseURL + "/login");

        LogInPageTest logInPage = new LogInPageTest(driver);
        logInPage.login(username, password);

        driver.get(baseURL + "/file");
        FilePageTest filePage = new FilePageTest(driver);
        filePage.testUpload(filePath);


        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement domIsLoaded = wait.until(presenceOfElementLocated(By.cssSelector("#uploaded-file")));
            assertEquals("test.txt",domIsLoaded.getText());

        }finally {

        }
//
//        driver.get(baseURL + "/file");
//
//        NotePageTest notePageTest = new NotePageTest(driver);
//        notePageTest.testAddNote(noteTitle, noteDescription);
//        Note note = new Note();

//         try {
////            WebElement firstResult = wait.until(presenceOfElementLocated(By.cssSelector("#uploaded-file")));
//            assertEquals(noteDescription,note.getNoteDescription());
//            assertEquals(noteTitle,note.getNoteTitle());
//        }finally {
//            driver.quit();
//        }

    }
}
