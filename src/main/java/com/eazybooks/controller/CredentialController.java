package com.eazybooks.controller;

import com.eazybooks.Model.Credentials;
import com.eazybooks.Model.User;
import com.eazybooks.services.CredentialService;
import com.eazybooks.services.EncryptionService;
import com.eazybooks.services.FileService;
import com.eazybooks.services.NoteService;
import com.eazybooks.services.UserService;
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
    EncryptionService encryptionService;


    /**
     * @param credentialService
     * @param userService
     * @param noteService
     * @param fileService
     */

    public CredentialController(CredentialService credentialService, UserService userService, NoteService noteService,
                                FileService fileService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.encryptionService = encryptionService;

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
        Credentials cred = credentialService.getCredential(credential.getCredentialId());

        if (cred != null) {
            List<Credentials> listOfUserCredentials =
                    this.credentialService.getListOfCredential(credential.getUserid());

            model.addAttribute("userCredentials", listOfUserCredentials);
        }


        return "result";
    }

    /**
     * @param authentication
     * @param credential
     * @param model
     * @return
     */

    /**
     * @param authentication
     * @param credential
     * @param model
     * @return Sends credential from the front end to controller
     */
    @PostMapping("/credential")
    public String addCredential(Authentication authentication, Credentials credential, Model model) {
        user = userService.getUser(authentication.getName());
        credential.setUserid(user.getUserId());

//        //Adding credentials
        int rowsAdded = credentialService.addOrUpdateCredentials(credential);

        //Sending all data back to the UI regardless if a new credential is added
        if (rowsAdded <= 0) {
            model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
            model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
            model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
            model.addAttribute("uploadStatus", "error");
            model.addAttribute("uploadMessage", "Error adding credential");
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
     * @return Gets credential by ID
     */
    @GetMapping("/credential/{credentialid}")
    @ResponseBody
    public Credentials getCredential(@PathVariable("credentialid") String id, Model model) {

        return this.credentialService.getCredential(Integer.parseInt(id));
    }

    /**
     * @param credentialId
     * @param model
     * @return homepage
     * <p>
     * <p>
     * this method deletes credential based on credential ID
     */
    @GetMapping("/deletecredential/{credentialid}")
    public String deleteCredential(@PathVariable("credentialid") Integer credentialId, Model model) {
        credentials = credentialService.getCredential(credentialId);
        credentialService.deleteCredential(credentials.getCredentialId());
        model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
        model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
        model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
        model.addAttribute("uploadStatus", "success");
        model.addAttribute("uploadMessage", "Credential deleted");
        return "result";
    }


}