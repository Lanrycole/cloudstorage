package com.udacity.jwdnd.course1.cloudstorage.Model;

import java.util.Arrays;

public class Files {
private  Integer fileId;
    private String filename;
    private String contenttype;
    private Long  filesize;
    private Integer userid;
    private byte[] filedata;

    public Files() {
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Files(Integer fileId, String filename, String contenttype, Long filesize, Integer userid, byte[] filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
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

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public byte[] getFileData() {
        return filedata;
    }

    public void setFileData(byte[] fileData) {
        this.filedata = fileData;
    }

    @Override
    public String toString() {
        return "Files{" +
                "fileId=" + fileId +
                ", filename='" + filename + '\'' +
                ", contenttype='" + contenttype + '\'' +
                ", filesize=" + filesize +
                ", userid=" + userid +
                ", fileData=" + Arrays.toString(filedata) +
                '}';
    }
}



