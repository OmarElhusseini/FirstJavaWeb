package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

//    @GetMapping("/home/note")
//    public String noteView(Model model, Authentication authentication){
//        Integer userId = userService.getUserIdByUsername(authentication.getName());
//        model.addAttribute("notes", noteService.getAllNotes(userId));
//        return "/home";
//    }

    @PostMapping("/home/note")
    public String addNote(@ModelAttribute Note note, Model model, Authentication authentication){
        String error = null;
//        boolean success = true;
        Integer userId = userService.getUserIdByUsername(authentication.getName());
        note.setUserId(userId);
        int rowsAdded = noteService.addOrEditNote(note);
        if (rowsAdded<0){
            error = "there was an error adding note";
        }
        if (error == null){
            model.addAttribute("success", true);
            return "/result";
        }else {
            model.addAttribute("error", error);
            return "/result";
        }
    }

    @GetMapping("/home/delete")
    public String deleteNote(@RequestParam("id") Integer noteId, Model model, Authentication authentication){
        String errorDeletingingNote = null;
        Integer userId = userService.getUserIdByUsername(authentication.getName());
        if (noteId > 0) {
            int rowsDeleted = noteService.deleteNoteByID(noteId);
            if (rowsDeleted<0){
                errorDeletingingNote = "there was some error while deleting the note, pls try again";
            }
        }
//
//        model.addAttribute("notes", noteService.getAllNotes(userId));
        return "/home";
    }

}
