package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CredentialController {
    CredentialService credentialService;
    UserService userService;
    User user;
    Credentials credentials;

    /**
     *
     * @param credentialService
     * @param userService
     */
    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    /**
     *
     * @param credential
     * @param model
     * @return
     */

    @GetMapping("/credential")
    public String getCredentials(Credentials credential, Model model) {

        List<Credentials> listOfUserCredentials = this.credentialService.getListOfCredential(credential.getUserid());
        System.out.println("Printing list of credentials");
        model.addAttribute("userCredentials", listOfUserCredentials);
        return "home";
    }

    /**
     *
     * @param authentication
     * @param credential
     * @param model
     * @return
     */

    @PostMapping("/credential")
    public String addCredential(Authentication authentication, Credentials credential, Model model) {
        user = userService.getUser(authentication.getName());

        credential.setUserid(user.getUserId());
        credentialService.addOrUpdateCredentials(credential);
        List<Credentials> listOfUserCredentials = this.credentialService.getListOfCredential(credential.getUserid());

        model.addAttribute("userCredentials", listOfUserCredentials);

        return "home";
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
        List<Credentials> listOfUserCredentials = this.credentialService.getListOfCredential(credentials.getUserid());
        model.addAttribute("userCredentials", listOfUserCredentials);
        return "home";


    }

}