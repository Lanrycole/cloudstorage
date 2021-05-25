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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
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
        this.credentialService = credentialService;
    }

    @GetMapping("/file")
    public String renderFilePage(Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
        model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
        model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));

        return "home";
    }

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("fileUpload")
                                     MultipartFile[] file, Model model, Authentication authentication) throws IOException {
        String uploadStatus = null;

        User user = userService.getUser(authentication.getName());



            for (MultipartFile multipartFile : file) {

                if (!fileService.isFileNameAvailable(multipartFile.getOriginalFilename())) {
                    uploadStatus = "File name exist.";
                    model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
                    model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
                    model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
                    model.addAttribute("uploadStatus", "fileexists");
                    model.addAttribute("uploadMessage", "File Exists");
                }


                if (uploadStatus == null) {
                    int rowsAdded = fileService.saveFile(multipartFile, user.getUserId());
                    if (rowsAdded < 0) {

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
                }

                if (uploadStatus == null) {

                    model.addAttribute("uploadSuccess", uploadStatus);
                    model.addAttribute("usernotes", noteService.getUserNotes(user.getUserId()));
                    model.addAttribute("files", fileService.getUserFilesById(user.getUserId()));
                    model.addAttribute("userCredentials", credentialService.getListOfCredential(user.getUserId()));
                    model.addAttribute("uploadStatus", "success");
                    model.addAttribute("uploadMessage", "Success");
                }

            }

        return "result";
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
