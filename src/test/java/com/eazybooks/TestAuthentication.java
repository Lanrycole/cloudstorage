package com.eazybooks;


import com.eazybooks.PagesTestFiles.LogInPageTest;
import com.eazybooks.PagesTestFiles.SignUpPageTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestAuthentication {


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

//    @Test
    void getLoginPage() {
        driver.get(baseURL + "/login");
        assertEquals("Login", driver.getTitle());
    }



//    @Test
    public void HomepageAccess() {

        //Testing that users can not access home page without Signing in
        driver.get(baseURL + "/home");
        assertEquals(baseURL + "/login", driver.getCurrentUrl());

    }


//This tests users Authentication flow.
//    @Test
    public void TestUserSignUpLogInAndLogOut() {

        //Signing user Up
        driver.get(baseURL + "/signup");
        SignUpPageTest signup = new SignUpPageTest(driver);
        signup.testSignUp(firstName, lastName, username, password);



        driver.get(baseURL + "/login");
        LogInPageTest logInPage = new LogInPageTest(driver);

        //Logging in with the wrong credentials
        logInPage.login("invalidUsername", "invalidPassword");
        assertEquals(baseURL + "/login?error", driver.getCurrentUrl());

        //Logging in with the correct credentials
        logInPage.login(username, password);
        assertEquals(baseURL + "/home", driver.getCurrentUrl());

        //Logging out and making sure a users are logged out and redirected to login page
        logInPage.logout();

        assertEquals(baseURL + "/login", driver.getCurrentUrl());

        //Testing that a logged out can not access the home page

        driver.get(baseURL + "/home");
        assertEquals(baseURL + "/login", driver.getCurrentUrl());

    }



}
