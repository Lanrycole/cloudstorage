package com.udacity.jwdnd.course1.cloudstorage.Model;

import lombok.*;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Files {
    private @Getter @Setter
    Integer fileId;
    private @Getter @Setter
    String filename;
    private @Getter @Setter
    String contenttype;
    private @Getter @Setter
    Long filesize;
    private @Getter @Setter
    Integer userid;
    private @Getter @Setter
    byte[] filedata;


}



