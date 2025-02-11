package com.eazybooks.Mapper;

import com.eazybooks.Model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int addNote(Note note);

    //gets a specific note
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNoteById(Integer noteid);

    //gets list of notes that belongs to a specific user bases on userID
    @Select("Select * from NOTES WHERE userid = #{userid}")
    List<Note> getUserNotesById(Integer userid);

    //gets list of notes that belongs to a specific user bases on userID
    @Select("Select * from NOTES WHERE notetitle = #{name} and userid= #{userid}")
    Note getUserNotesByTitle(String  name, int userid);


    //Deletes notes based on notes ID
    @Delete("delete from NOTES where noteId = #{noteid}")
    void deleteNotebyId(Integer noteid);

    //updates notes
    @Update("UPDATE NOTES SET notetitle=#{noteTitle},notedescription=#{noteDescription} " +
            "WHERE noteid =#{noteid}")
    void updateNoteById( Note note);
}
