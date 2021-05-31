package com.udacity.jwdnd.course1.cloudstorage.Model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private @Getter @Setter  Integer userId;
    private @Getter @Setter String username;
    private @Getter @Setter String salt;
    private @Getter @Setter String password;
    private @Getter @Setter String firstname;
    private @Getter @Setter String lastname;


}


