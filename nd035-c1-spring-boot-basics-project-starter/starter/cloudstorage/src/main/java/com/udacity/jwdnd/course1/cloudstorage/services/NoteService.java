package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addOrEditNote(Note note){
        if (!(note.getNoteId()==null)){
            return noteMapper.updateNote(note, note.getNoteId());
        }else {
            return noteMapper.saveNewNote(note);
        }
    }

    public Note getNoteById(Integer noteId){
        return noteMapper.getNote(noteId);
    }

    public List<Note> getAllNotes(Integer userId){
        return noteMapper.getAllNotes(userId);
    }

    public int deleteNoteByID(Integer noteID){
        return noteMapper.deleteNoteWithNoteId(noteID);
    }
}