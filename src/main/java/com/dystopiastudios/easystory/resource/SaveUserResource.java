package com.dystopiastudios.easystory.resource;

import com.dystopiastudios.easystory.domain.model.AuditModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SaveUserResource extends AuditModel {
    //private Long id;

    // todo: agregar anotaciones @
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;

}
