package com.dystopiastudios.easystory.domain.service;

import com.dystopiastudios.easystory.domain.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    Page<Comment> getAllCommentsByPostId(Long postId, Pageable pageable);

    Page<Comment> getAllCommentsByUserId(Long userId, Pageable pageable);

    Comment getCommentById(Long commentId);

    Comment createComment(Long userId, Long postId, Comment comment);

    Comment updateComment(Long commentId, Comment commentDetails);

    ResponseEntity<?> deleteComment(Long commentId);

    Comment getCommentByUserIdAndPostId(Long userId, Long postId);
}