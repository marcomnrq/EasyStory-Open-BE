package com.dystopiastudios.easystory.resource;

import com.dystopiastudios.easystory.model.AuditModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveUserResource extends AuditModel {
    //private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;

}
