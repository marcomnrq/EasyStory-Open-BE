package com.dystopiastudios.easystory.resource;

import com.dystopiastudios.easystory.domain.model.AuditModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserResource extends AuditModel {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private Integer subscribers;
    private Integer subscriptions;
}
