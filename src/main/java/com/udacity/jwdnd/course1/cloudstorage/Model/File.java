package com.udacity.jwdnd.course1.cloudstorage.Model;

public class File {

    private String filename;
    private String contenttype;
    private String  filesize;
    private Integer userid;
    private String BLOB;

    public File() {
    }

    public File(String filename, String contenttype, String filesize, Integer userid, String BLOB) {
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.BLOB = BLOB;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getBLOB() {
        return BLOB;
    }

    public void setBLOB(String BLOB) {
        this.BLOB = BLOB;
    }

}
