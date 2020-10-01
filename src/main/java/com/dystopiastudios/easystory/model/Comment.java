package com.dystopiastudios.easystory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob // Large Object
    private String text;

    // Relationships
    // Un post puede vincularse con varios comments
    // pero un comment solo se puede vincular con un post
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // La relacion
    @JoinColumn(name = "post_id", nullable = false)
    // De todas maneras un comment tiene que tener un post, sino no se puede guardar
    @OnDelete(action = OnDeleteAction.CASCADE) // Si borro el post, se borra tambien los comentarios
    @JsonIgnore
    private Post post;

    //Many To One se considera mas eficiente (rendimiento) si se establece en el dependiente (comment)
    //ya que el independiente (post) tendria que siempre estar haciendo un join
}
