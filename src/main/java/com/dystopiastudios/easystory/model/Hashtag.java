package com.dystopiastudios.easystory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "hashtags")
@Getter
@Setter
public class Hashtag extends AuditModel{
    @Id // Patron de diseño Entity Field (llave primaria)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull // No acepta nulos en name
    @Size(max = 100) // Que no exceda de mas de 100 caracteres
    private @Getter @Setter String name;
    @ManyToMany(fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    mappedBy = "hashtags")
    //mapped significa que en Post va a haber una propiedad (List<Tag> tags;) tags que es el otro extremo del muchos a muchos
    @JsonIgnore // Ignorar en el Json esta propiedad, igual que en Post
    private @Getter @Setter List<Post> posts; // Mantiene vinculo con los Posts
}
