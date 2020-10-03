package com.dystopiastudios.easystory.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity // Va a tener una contraparte relacional en la DB.
@Table(name = "users") // Para la clase User, la contraparte va a ser users en la DB.
@Getter // Lombok nos evita programar los metodos accesores.
@Setter // Lombok nos evita programar los metodos accesores.
public class User extends AuditModel {
    @Id // Patron de diseño Entity Field (llave primaria)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Este valor se genera automaticamente
    private Long id;

    @NotNull // No acepta nulos en name
    @Size(max = 15) // Que no exceda de mas de 100 caracteres
    @NaturalId  // Notacion Hibernate: username va a ser unico. Potencial llave alterna.
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
}
