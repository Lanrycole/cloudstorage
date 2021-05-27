package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotePageTest {

    @FindBy(css = "#nav-notes-tab")
    private WebElement notesTab;

    @FindBy(css = "#addNote")
    private WebElement addNote;

    @FindBy(css = "#note-title")
    private WebElement title;

    @FindBy(css = "#note-description")
    private WebElement description;


    @FindBy(css = "#saveNote")
    private WebElement saveNote;

    @FindBy(css = ".return")
    private WebElement returnToPage;

    public NotePageTest(WebDriver webDriver) {
     PageFactory.initElements(webDriver, this);
    }

    public void testAddNote(String noteTitle, String noteDescription){
        notesTab.click();
        addNote.click();
        title.sendKeys(noteTitle);
        description.sendKeys(noteDescription);
        saveNote.click();
        returnToPage.click();
    }
}
