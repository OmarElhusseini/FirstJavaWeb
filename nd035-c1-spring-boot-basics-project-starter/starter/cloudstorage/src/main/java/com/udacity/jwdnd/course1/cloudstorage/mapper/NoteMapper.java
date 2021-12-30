package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note getNote(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userid= #{userId}")
    List<Note> getAllNotes(Integer userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int saveNewNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    int deleteNoteWithNoteId(Integer noteID);

    @Update("UPDATE NOTES SET notetitle = #{note.noteTitle}, notedescription = #{note.noteDescription} WHERE noteid = #{noteId}")
    public int updateNote(Note note, Integer noteId);
}