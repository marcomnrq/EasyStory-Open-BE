package com.dystopiastudios.easystory.domain.model;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data
public class User extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 15)
    @NaturalId
    @Column(unique = true)
    private String username;

    @NotNull
    @NotBlank
    @Size(max = 15, min = 5)
    private String password;

    @NotNull
    @NotBlank
    @Size(max = 25)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 25)
    private String lastName;

    @NotNull
    @NotBlank
    @Size(max = 25)
    private String email;

    @NotNull
    @NotBlank
    @Size(max = 25)
    private String telephone;

    @JsonIgnore
    private Integer subscribers = 0;

    @JsonIgnore
    private Integer subscriptions = 0;
}
