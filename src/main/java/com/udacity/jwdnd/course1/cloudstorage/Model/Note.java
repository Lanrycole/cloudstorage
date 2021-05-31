package com.udacity.jwdnd.course1.cloudstorage.Model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Note {
    private @Getter
    @Setter
    String noteTitle;
    private @Getter
    @Setter
    String noteDescription;
    private @Getter
    @Setter
    Integer userid;
    private @Getter
    @Setter
    Integer noteid;

}

