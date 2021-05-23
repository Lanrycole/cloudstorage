package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
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
    User user;
    Note note;

    /**
     *
     * @param noteService
     * @param userService
     */
    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    /**
     *
     * @param authentication
     * @param notes
     * @param model
     * @return
     */
    @PostMapping("/note")
    public String addNote(Authentication authentication, Note notes, Model model) {
        user = userService.getUser(authentication.getName());
        notes.setUserid(user.getUserId());
        noteService.addOrUpdateNote(notes);
        List<Note> userNotes = this.noteService.getUserNotes(notes.getUserid());
        model.addAttribute("usernotes", userNotes);
        return "home";
    }

    /**
     *
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
     *
     * @param noteid
     * @param model
     * @return
     */
    @GetMapping("/deletenote/{noteid}")
    public String deleteNote(@PathVariable("noteid") Integer noteid, Model model) {
         note = noteService.getNote(noteid);
        noteService.deleteNotes(note.getNoteid());
        System.out.println(note.getNoteid());
        List <Note> userNotes = this.noteService.getUserNotes(note.getUserid());
        model.addAttribute("usernotes", userNotes);

        return "home";
    }

    /**
     *
     * @param note
     * @param model
     * @return
     */
    @GetMapping("/note")
    public String renderNotes(Note note, Model model){
        List <Note> userNotes = this.noteService.getUserNotes(note.getUserid());
        model.addAttribute("usernotes", userNotes);
        return "home";
    }

}
