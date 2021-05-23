package com.udacity.jwdnd.course1.cloudstorage.Model;

public class Note {
    private String noteTitle;
    private String noteDescription;
    private Integer userid;
    private Integer noteid;


    public Note() {
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public Note(String noteTitle, String noteDescription, Integer userid, Integer noteid) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userid = userid;
        this.noteid = noteid;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getNoteid() { return noteid; }

    @Override
    public String toString() {
        return "Note{" +
                "noteTitle='" + noteTitle + '\'' +
                ", noteDescription='" + noteDescription + '\'' +
                ", userid=" + userid +
                ", noteid=" + noteid +
                '}';
    }
}

