package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Controller
@RequestMapping
public class HomeController {
    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String homeView(Model model, Authentication authentication) throws Exception {
        Integer userId = userService.getUserIdByUsername(authentication.getName());
        model.addAttribute("filesUploaded",fileService.getFiles(userId));
        model.addAttribute("notes", noteService.getAllNotes(userId));
        model.addAttribute("allCredentials", credentialService.getAllCredentials(userId));
        return "/home";
    }

    @PostMapping("/home")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, Authentication authentication) throws Exception {
        String uploadError = null;
        Integer userId = userService.getUserIdByUsername(authentication.getName());
        if (!fileService.isFileNameAvailable(fileUpload.getOriginalFilename(), userId)){
            uploadError = "File with the same name was uploaded before";
        }
        if (uploadError == null){
            int rowsAdded = fileService.uploadNewFile(fileUpload, userId);
            if (rowsAdded < 0){
                uploadError = "An error happened while uploading the File, pls try again!";
            }
            if (rowsAdded== 0){
                uploadError = "there is no file to upload, pls choose file!";
            }
        }
        if (!(uploadError == null)){
            model.addAttribute("uploadError", uploadError);
            return "/home";
        }else {
            model.addAttribute("success", true);
            return "/result";
        }
    }
}