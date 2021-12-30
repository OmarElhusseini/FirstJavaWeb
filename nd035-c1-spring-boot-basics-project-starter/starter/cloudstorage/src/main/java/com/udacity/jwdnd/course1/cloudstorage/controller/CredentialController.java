package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/credential")
    public String createOrEditCredential(Credential credential, Authentication authentication, Model model){
        String error = null;
        int rowsAdded = -1;
        Integer userId = userService.getUserIdByUsername(authentication.getName());
        if (!(credential.getCredentialId()==null)){
            rowsAdded = credentialService.updateCredential(credential, credential.getCredentialId());
        }else {
            rowsAdded = credentialService.saveCredential(credential, userId);
        }
        if (rowsAdded<0){
            error = "there was an error adding Credential";
        }
        if (error == null){
            model.addAttribute("success", true);
            return "/result";
        }else {
            model.addAttribute("error", error);
            return "/result";
        }
    }

    @GetMapping("/credential/delete")
    public String deleteCredential(Integer credentialId){
        String errorMessage = null;
        int rowsdeleted = credentialService.deleteCredential(credentialId);
        if (rowsdeleted==0){
            errorMessage = "There was an error";
        }
        return "/home";
    }
}