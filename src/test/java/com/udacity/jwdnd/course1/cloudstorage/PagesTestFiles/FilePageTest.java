package com.udacity.jwdnd.course1.cloudstorage.PagesTestFiles;

import com.udacity.jwdnd.course1.cloudstorage.Model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FilePageTest {

    @FindBy(css = "#fileUpload")
    private WebElement uploadFile;

    @FindBy(css = "#upload-button")
    private WebElement submitButton;

    @FindBy(css = ".return")
    private WebElement returnToPage;

    @FindBy(css = "#delete-file")
    private WebElement deleteFile;

    @FindBy(css = "#uploaded-file")
    private WebElement uploadedFile;


    @FindBy(css = "#download-file")
    private WebElement downloadFile;
    File file = new File("/Users/lanre/Desktop/Counter");
    String filePath = file.getPath();


    public FilePageTest(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void testUpload(String fileUrl) {
        this.uploadFile.sendKeys(fileUrl);
        this.submitButton.click();
        this.returnToPage.click();

    }

    public void testFileDelete() {
        deleteFile.click();

    }

    public String getFileName() {

        Files fileName = new Files();
        fileName.setFilename(uploadedFile.getText());

        return fileName.getFilename();
    }

    public Boolean isFileDeleted(String filename) {
        if (uploadedFile.getText().equals(filename)) {
            testFileDelete();
            return true;
        } else {
            return false;
        }

    }

    public boolean isFileDownloaded() throws Exception {
        downloadFile.click();
        final int SLEEP_TIME_MILLIS = 1000;
        File file = new File(filePath);
        final int timeout = 60* SLEEP_TIME_MILLIS;
        int timeElapsed = 0;
        while (timeElapsed<timeout){
            if (file.exists()) {
                System.out.println(file + " is present");
                return true;
            } else {
                timeElapsed +=SLEEP_TIME_MILLIS;
                Thread.sleep(SLEEP_TIME_MILLIS);
            }
        }
        return false;
    }
}
