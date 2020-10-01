package com.dystopiastudios.easystory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/*
JPA - Java Persistence API
Hibernate es el componente de JPA que se encarga de hacer el trabajo de Mapping entre el mundo
relacional y el mundo de la programacion orientada a objetos.
*/

@Entity // Va a tener una contraparte relacional en la DB
@Table(name = "tags") // Para la clase Tag, la contraparte va a ser tags en la DB
@Getter // Lombok nos evita programar los metodos accesores
@Setter // Lombok nos evita programar los metodos accesores
public class Tag extends AuditModel {
    @Id // Patron de diseño Entity Field (llave primaria)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Este valor se genera automaticamente
    private Long id;

    @NotNull // No acepta nulos en name
    @Size(max = 100) // Que no exceda de mas de 100 caracteres
    @NaturalId  // Notacion Hibernate: name va a ser unico. Potencial llave alterna.
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "tags")
    //mapped significa que en Post va a haber una propiedad (List<Tag> tags;) tags que es el otro extremo del muchos a muchos
    @JsonIgnore // Ignorar en el Json esta propiedad, igual que en Post
    private List<Post> posts; // Mantiene vinculo con los Posts

}
