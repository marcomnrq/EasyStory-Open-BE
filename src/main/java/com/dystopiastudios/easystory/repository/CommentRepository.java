package com.dystopiastudios.easystory.repository;

import com.dystopiastudios.easystory.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /*

    Igual es un repository
    Desciende de JpaRepository
    Tengo unos metodos que no los implemento (los de abajito) pero eso es gracias a Spring


    Diferencia entre framework y biblioteca:
    la biblioteca tiene comportamiento pasivo, yo uso lo que necesito
    mientras que el framework asume el control de la aplicacion por eso crea clases

     */

    Page<Comment> findByPostId(Long postId, Pageable pageable); // Retorna una paginacion

    // Como esto tienda a retornar un solo objeto le digo que es Opcional porque podria ser que no lo encuentre
    Optional<Comment> findByIdAndPostId(Long id, Long postId);

}
