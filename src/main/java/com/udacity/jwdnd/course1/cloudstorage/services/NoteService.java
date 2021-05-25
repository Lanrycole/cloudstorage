package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    //updates or adds note
    public int addOrUpdateNote(Note note) {
        Note notes = noteMapper.getNoteById(note.getNoteid());

        if (notes != null) {
            notes.setNoteTitle(note.getNoteTitle());
            notes.setNoteDescription(note.getNoteDescription());
            notes.setUserid(note.getUserid());
            notes.setNoteid(note.getNoteid());
            noteMapper.updateNoteById(note);
        } else {
            notes = new Note(note.getNoteTitle(), note.getNoteDescription(), note.getUserid(), note.getNoteid());
            noteMapper.addNote(notes);
        }
        return 1;
    }

    public boolean isFileNoteAvailable(String notetitle) {
        return noteMapper.getUserNotesByTitle(notetitle) == null;
    }

    public Note getNote(Integer id) {
        return noteMapper.getNoteById(id);
    }

    public List<Note> getUserNotes(Integer id) {
        return noteMapper.getUserNotesById(id);
    }

    public void deleteNotes(Integer id) {
        noteMapper.deleteNotebyId(id);
    }

}
