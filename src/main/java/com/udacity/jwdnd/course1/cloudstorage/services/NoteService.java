package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    /**
     * @param noteMapper
     */
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    /**
     * @param note
     * @return added note
     * updates or adds note
     */

    public int addOrUpdateNote(Note note) {
        Note notes = noteMapper.getNoteById(note.getNoteid());

        //Checks if phone is null to update it
        if (notes != null) {
            notes.setNoteTitle(note.getNoteTitle());
            notes.setNoteDescription(note.getNoteDescription());
            notes.setUserid(note.getUserid());
            notes.setNoteid(note.getNoteid());
            noteMapper.updateNoteById(note);
        } else {
            //Adding notes
            notes = new Note(note.getNoteTitle(), note.getNoteDescription(), note.getUserid(), note.getNoteid());
            noteMapper.addNote(notes);
        }
        return 1;
    }

    /**
     * @param id
     * @return note of the ID passed
     */
    public Note getNote(Integer id) {
        return noteMapper.getNoteById(id);
    }

    /**
     * @param id
     * @return List of notes
     */
    public List<Note> getUserNotes(Integer id) {
        return noteMapper.getUserNotesById(id);
    }

    /**
     * @param id Delete notes
     */
    public void deleteNotes(Integer id) {
        noteMapper.deleteNotebyId(id);
    }

}
