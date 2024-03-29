package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signUpView() {return "/signup";}


    @PostMapping
    public String signUpUser(@ModelAttribute User user, Model model){
        String signUpError = null;
        if (!this.userService.isUsernameAvailable(user.getUsername())){
            signUpError = "Username already exits";
        }

        if (signUpError == null){
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0){
                signUpError = "An error happened while signing you up, pls try again!";
            }
        }
        if (signUpError == null){
            model.addAttribute("signUpSuccess", true);
            return "/login";
        }else {
            model.addAttribute("signUpError", signUpError);
            return "/signup";
        }
    }
}