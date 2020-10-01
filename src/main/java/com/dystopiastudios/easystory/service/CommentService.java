package com.dystopiastudios.easystory.service;

import com.dystopiastudios.easystory.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    Page<Comment> getAllCommentsByPostId(Long postId, Pageable pageable);

    Comment getCommentByIdAndPostId(Long postId, Long commentId);

    Comment createComment(Long postId, Comment comment);

    Comment updateComment(Long postId, Long commentId, Comment commentDetails);

    ResponseEntity<?> deleteComment(Long postId, Long commentId);
}
/*
Porque hay implementacion en Service pero no en Repository?
Porque la implementacion de Repository la hace el Framework. Spring se encarga de eso.

La capa de servicio incluye la logica del negocio y Spring no puede adivinar esto
 */