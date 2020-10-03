package com.dystopiastudios.easystory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity // Clase de tipo de Entidad, va a tener una contraparte en base de datos relacional
@Table(name = "posts") // Nombre de la tabla
@Getter
@Setter
public class Post extends AuditModel { //Esta clase es descendiente de AuditModel

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 100)
    @Column(unique = true)
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 250)
    private String description;

    @NotNull
    @Lob // Large Object, podria alejar gran cantidad de informacion.
    private String content;

    // Relationships
    // Un usuario puede vincularse con varios post
    // pero un post solo se puede vincular con un usuario
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // La relacion
    @JoinColumn(name = "user_id", nullable = false)
    // De todas maneras un post tiene que tener un usuario, sino no se puede guardar
    @OnDelete(action = OnDeleteAction.CASCADE) // Si borro el usuario, se borran tambien los post
    @JsonIgnore
    private User user;



    //hashtags
    @ManyToMany(fetch = FetchType.LAZY, // por defecto no este cargando a cada rato los elementos, segun se requiera puntualmente
               cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //aplique cascada el almacenamiento y la actualizacion
    @JoinTable(name = "posts_hashtags", // Tabla intermedia
            joinColumns = {@JoinColumn(name = "post_id")}, // Esta clase
            inverseJoinColumns = {@JoinColumn(name = "hashtag_id")}) // Clase inversa
    @JsonIgnore // No va a pedir la lista en el Json
    List<Hashtag> hashtags; // Mantiene vinculo con los tags
}
