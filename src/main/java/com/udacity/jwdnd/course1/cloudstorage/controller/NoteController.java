package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class NoteController {
    NoteService noteService;
    UserService userService;
    FileService fileService;
    CredentialService credentialService;
    User user;
    Note note;

    String uploadStatus = null;

    /**
     * @param noteService
     * @param userService
     */
    public NoteController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    /**
     * @param authentication
     * @param notes
     * @param model
     * @return
     */
    @PostMapping("/add-note")
    public String addNote(Authentication authentication, Note notes, Model model) {
        user = userService.getUser(authentication.getName());
        notes.setUserid(user.getUserId());

        int rowsAdded = noteService.addOrUpdateNote(notes);
        if (rowsAdded <= 0) {
            model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
            model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
            model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
            model.addAttribute("uploadStatus", "error");
            model.addAttribute("uploadMessage", "Error Adding Files");

        } else {
            model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
            model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
            model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
            model.addAttribute("uploadStatus", "success");
            model.addAttribute("uploadMessage", "Success");
        }

        return "result";
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/getNote/{noteid}")
    @ResponseBody
    public Note getNote(@PathVariable("noteid") String id, Model model) {
        return this.noteService.getNote(Integer.parseInt(id));
    }

    /**
     * @param noteid
     * @param model
     * @return
     */
    @GetMapping("/deletenote/{noteid}")
    public String deleteNote(@PathVariable("noteid") Integer noteid, Model model) {
        note = noteService.getNote(noteid);
        noteService.deleteNotes(note.getNoteid());
        model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
        model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
        model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
        return "home";
    }

    /**
     * @param note
     * @param model
     * @return
     */
    @GetMapping("/add-note")
    public String renderNotes(Note note, Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        List<Note> userNotes = this.noteService.getUserNotes(user.getUserId());
        System.out.println("Hitting render Note from GET");
        model.addAttribute("usernotes", userNotes);
        return "home";
    }

}
