package com.udacity.jwdnd.course1.cloudstorage.Model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Credentials {
    private @Getter @Setter Integer credentialId;
    private @Getter @Setter String url;
    private @Getter @Setter String username;
    private @Getter @Setter String password;
    private @Getter @Setter String key;
    private @Getter @Setter Integer userid;

}
