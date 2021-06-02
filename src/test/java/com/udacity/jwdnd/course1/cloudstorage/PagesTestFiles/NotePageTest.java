package com.udacity.jwdnd.course1.cloudstorage.PagesTestFiles;

import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePageTest {

    private final WebDriver driver;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "note-title-text")
    private WebElement notesTitleText;

    @FindBy(css = "#addNote")
    private WebElement addNote;

    @FindBy(css = "#editNote")
    private WebElement editNote;

    @FindBy(css = "#delete-note")
    private WebElement deleteNote;

    @FindBy(css = "#note-title")
    private WebElement title;

    @FindBy(css = "#note-description")
    private WebElement description;


    @FindBy(css = "#saveNote")
    private WebElement saveNote;

    @FindBy(css = ".return")
    private WebElement returnToPage;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    /**
     *
     * @param driver
     */
    public NotePageTest(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     *
     * @param noteTitle
     * @param noteDescription
     * @throws InterruptedException
     */
    public void addNote(String noteTitle, String noteDescription) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.notesTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.addNote);


        // fill in data:
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + noteTitle + "';", this.noteTitle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + noteDescription + "';", this.noteDescription);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.saveNote);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.returnToPage);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.notesTab);
        Thread.sleep(2200);
    }

    /**
     *
     * @return note title
     */
    public String getNoteTitle() throws InterruptedException {
        Note note = new Note();
        Thread.sleep(2200);
        note.setNoteTitle(notesTitleText.getText());
        return note.getNoteTitle();
    }

    /**
     *
     * @param newNote
     * @param newDescription
     * @returnnote tittle;
     * @throws InterruptedException
     *
     */
    public String editNote(String newNote, String newDescription)
            throws InterruptedException {
        Note note = new Note();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.editNote);
        note.setNoteTitle(newNote);
        note.setNoteDescription(newDescription);

        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newNote + "';", this.noteTitle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newDescription + "';", this.noteDescription);
        Thread.sleep(2200);
        return note.getNoteTitle();
    }

    /**
     * Deletes note
     */
    public void deleteNote() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.notesTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.deleteNote);
    }


}
