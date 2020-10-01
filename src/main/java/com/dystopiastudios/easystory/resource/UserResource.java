package com.dystopiastudios.easystory.resource;

import com.dystopiastudios.easystory.model.AuditModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResource extends AuditModel {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
}
