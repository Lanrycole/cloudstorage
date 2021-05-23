package com.udacity.jwdnd.course1.cloudstorage.Model;

public class Credentials {
    private String url;
    private String username;
    private String password;
    private String key;
    private Integer userid;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private Integer credentialId;


    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }


    public Credentials() {
    }
    public Integer getCredentialId() {
        return credentialId;
    }
    public Credentials(String url, String username, String password, Integer userid,  Integer credentialId, String key) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.userid = userid;
        this.credentialId = credentialId;
        this.key= key;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userid=" + userid +
                ", credentialId=" + credentialId +
                '}';
    }
}
