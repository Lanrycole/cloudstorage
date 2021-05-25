package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CredentialController {

    User user;
    CredentialService credentialService;
    UserService userService;
    Credentials credentials;
    NoteService noteService;
    FileService fileService;

    public CredentialController(CredentialService credentialService, UserService userService, NoteService noteService, FileService fileService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
    }


    /**
     *
     * @param credentialService
     * @param userService
     */

    /**
     * @param credential
     * @param model
     * @return
     */

    @GetMapping("/credential")
    public String getCredentials(Credentials credential, Model model) {

        List<Credentials> listOfUserCredentials = this.credentialService.getListOfCredential(credential.getUserid());
        model.addAttribute("userCredentials", listOfUserCredentials);
        return "result";
    }

    /**
     * @param authentication
     * @param credential
     * @param model
     * @return
     */

    @PostMapping("/credential")
    public String addCredential(Authentication authentication, Credentials credential, Model model) {
        user = userService.getUser(authentication.getName());
        credential.setUserid(user.getUserId());


        int rowsAdded = credentialService.addOrUpdateCredentials(credential);
        if(rowsAdded<= 0){
            model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
            model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
            model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
            model.addAttribute("uploadStatus", "error");
            model.addAttribute("uploadMessage", "Error adding credential");
        }else{
            model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
            model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
            model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
            model.addAttribute("uploadStatus", "success");
            model.addAttribute("uploadMessage", "Success");
        }


        return "result";
    }


    @GetMapping("/credential/{credentialid}")
    @ResponseBody
    public Credentials getCredential(@PathVariable("credentialid") String id, Model model) {
        return this.credentialService.getCredential(Integer.parseInt(id));
    }


    @GetMapping("/deletecredential/{credentialid}")
    public String deleteCredential(@PathVariable("credentialid") Integer credentialId, Model model) {
        credentials = credentialService.getCredential(credentialId);
        credentialService.deleteCredential(credentials.getCredentialId());
        model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
        model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
        model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
        return "home";


    }

}