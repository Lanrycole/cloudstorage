package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.web.multipart.MultipartFile;

public class FilePageTest {

    @FindBy(css = "#fileUpload")
    private WebElement uploadFile;

    @FindBy(css = "#upload-button")
    private WebElement submitButton;

    @FindBy(css = ".return")
    private WebElement returnToPage;

    @FindBy(css = "#delete-file")
    private WebElement deleteFile;


    public FilePageTest(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void testUpload(String fileUrl){
       this.uploadFile.sendKeys(fileUrl);
        this.submitButton.click();
        this.returnToPage.click();
//        Thread.sleep(1000);

    }

}
