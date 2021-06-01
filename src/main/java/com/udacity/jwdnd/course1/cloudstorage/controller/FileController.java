package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.Files;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


@Controller
public class FileController {
    FileService fileService;
    Files file;
    UserService userService;
    NoteService noteService;
    CredentialService credentialService;

    /**
     * @param fileService
     * @param userService
     * @param noteService
     * @param credentialService
     */
    public FileController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    /**
     * @param model
     * @param authentication
     * @return Retrieving data from the database when /home gets a request
     */

    @GetMapping("/home")
    public String renderFilePage(Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
        model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
        model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));

        return "home";
    }

    /**
     * @param file
     * @param model
     * @param authentication
     * @return
     * @throws IOException uploading file
     */
    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("fileUpload")
                                     MultipartFile file, Model model, Authentication authentication) throws IOException {
        boolean fileExists = false;

        User user = userService.getUser(authentication.getName());

        //setting data retrieved from multipart to the UI

        if (!fileService.isFileNameAvailable(file.getOriginalFilename(), user.getUserId())) {
            fileExists = true;
            model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
            model.addAttribute("uploadStatus", "fileexists");
            model.addAttribute("uploadMessage", "Files Exists");

        }


        if (!fileExists) {
            int rowsAdded = fileService.saveFile(file, user.getUserId());
            if (rowsAdded < 0) {

                model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
                model.addAttribute("uploadStatus", "error");
                model.addAttribute("uploadMessage", "Error Adding Files");
            } else {
                model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
                model.addAttribute("uploadStatus", "success");
                model.addAttribute("uploadMessage", "Success");
            }


            if (!fileExists) {
                model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
                model.addAttribute("uploadStatus", "success");
                model.addAttribute("uploadMessage", "Success");
            }

        } else {
 
            model.addAttribute("uploadStatus", "fileexists");
            model.addAttribute("uploadMessage", "Files Exists");
 
        }


        return "result";
    }

    /**
     * @param fileId
     * @return home
     * <p>
     * Downloading file
     */


    @GetMapping("/download-file/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId) {
        Files file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\" " + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFiledata()));

    }

    /**
     * @param fileid
     * @param model
     * @param authentication
     * @return Deleting File
     */
    @GetMapping("/delete-file/{fileid}")
    public String deleteNote(@PathVariable Integer fileid, Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        file = fileService.getFileById(fileid);
        fileService.deleteFile(file.getFileId());
        model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
        model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
        model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
        model.addAttribute("uploadStatus", "success");
        model.addAttribute("uploadMessage", "File Deleted");
        return "result";
    }

}
