package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@Controller
public class FileController {
    FileService fileService;
    File file;
    UserService userService;
    NoteService noteService;
    CredentialService credentialService;

    public FileController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService =credentialService;
    }

    @GetMapping("/file")
    public String renderFile(Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
        model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
        model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
        return "home";
    }

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("fileUpload")
                                     MultipartFile[] file, Model model, Authentication authentication) throws IOException {
        String uploadError = null;
        User user = userService.getUser(authentication.getName());
        for (MultipartFile multipartFile : file) {
            if (!fileService.isFileNameAvailable(multipartFile.getOriginalFilename())) {
                uploadError = "File exists. Please choose another file";
                model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
                model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
                model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
            }

            if (uploadError == null) {
                int rowsAdded = fileService.saveFile(multipartFile, user.getUserId());
                if (rowsAdded < 0) {
                    uploadError = "There was an error uploading file";
                    model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
                    model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
                    model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
                }
                else{
//                    uploadError = "File uploaded successfully";
                    model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
                    model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
                    model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
                }
            }

            if (uploadError == null) {
//                uploadError = "File uploaded successfully";
                model.addAttribute("uploadSuccess", uploadError);
                model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
                model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
                model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
            } else {
                model.addAttribute("uploadError", uploadError);
                model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
                model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
                model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
            }

        }
        model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
        return "home";
    }


    @GetMapping("/download-file/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId) {
        File file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\" " + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFileData()));

    }

    @GetMapping("/delete-file/{fileid}")
    public String deleteNote(@PathVariable Integer fileid, Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        file = fileService.getFileById(fileid);
        fileService.deleteFile(file.getFileId());
        model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
        model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
        model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
        return "home";
    }

}
