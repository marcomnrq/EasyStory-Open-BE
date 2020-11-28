package com.dystopiastudios.easystory.service;

import com.dystopiastudios.easystory.domain.service.CommentService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.domain.model.Comment;
import com.dystopiastudios.easystory.domain.model.Post;
import com.dystopiastudios.easystory.domain.model.User;
import com.dystopiastudios.easystory.domain.repository.CommentRepository;
import com.dystopiastudios.easystory.domain.repository.PostRepository;
import com.dystopiastudios.easystory.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment getCommentByUserIdAndPostId(Long userId, Long postId) {
        return commentRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(()->new ResourceNotFoundException("Comment not found with Id" + postId +  "and UserId" + userId));
    }


    @Override
    public Page<Comment> getAllCommentsByPostId(Long postId, Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }

    @Override
    public Page<Comment> getAllCommentsByUserId(Long userId, Pageable pageable){
        return commentRepository.findByUserId(userId, pageable);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment not found with Id " + commentId));
    }

    @Override
    public Comment createComment(Long userId, Long postId, Comment comment) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with Id " + userId));
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with Id " + postId));
        comment.setUser(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, Comment commentDetails) {
        /*
        if (!postRepository.existsById(postId))
            throw new ResourceNotFoundException("Post", "Id", postId);
         */

        return commentRepository.findById(commentId).map(comment -> {
            comment.setContent(commentDetails.getContent());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

    }

    @Override
    public ResponseEntity<?> deleteComment(Long commentId) {
        return commentRepository.findById(commentId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(
                "Comment not found with Id " + commentId));

    }
}
