package com.eazybooks.controller;


import com.eazybooks.Model.Note;
import com.eazybooks.Model.User;
import com.eazybooks.services.CredentialService;
import com.eazybooks.services.FileService;
import com.eazybooks.services.NoteService;
import com.eazybooks.services.UserService;
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
        if (noteid != null) {

            note = noteService.getNote(noteid);

            if(note !=null){
                noteService.deleteNotes(note.getNoteid());
                model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
                model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
                model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
                model.addAttribute("uploadStatus", "success");
                model.addAttribute("uploadMessage", "Note Deleted");
            }else{
                model.addAttribute("uploadStatus", "error");
                model.addAttribute("uploadMessage", "Note Not Found ");
            }


        }
        return "result";
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

        model.addAttribute("usernotes", userNotes);
        return "home";
    }

}
