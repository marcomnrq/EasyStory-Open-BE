package com.dystopiastudios.easystory.controller;

import com.dystopiastudios.easystory.model.Comment;
import com.dystopiastudios.easystory.resource.CommentResource;
import com.dystopiastudios.easystory.resource.SaveCommentResource;
import com.dystopiastudios.easystory.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public Page<CommentResource> getAllCommentsByPostId(
            @PathVariable(name = "postId") Long postId,
            Pageable pageable) {
        Page<Comment> commentPage = commentService.getAllCommentsByPostId(postId, pageable);
        List<CommentResource> resources = commentPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public CommentResource getCommentByIdAndPostId(@PathVariable(name = "postId") Long postId,
                                           @PathVariable(name = "commentId") Long commentId) {
        return convertToResource(commentService.getCommentByIdAndPostId(postId, commentId));
    }

    @PostMapping("/posts/{postId}/comments")
    public CommentResource createComment(@PathVariable(name = "postId") Long postId,
                                 @Valid @RequestBody SaveCommentResource resource) {
        return convertToResource(commentService.createComment(postId, convertToEntity(resource)));

    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public CommentResource updateComment(@PathVariable(name = "postId") Long postId,
                                 @PathVariable(name = "commentId") Long commentId,
                                 @Valid @RequestBody SaveCommentResource resource) {
        return convertToResource(commentService.updateComment(postId, commentId, convertToEntity(resource)));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "postId") Long postId,
                                           @PathVariable(name = "commentId") Long commentId) {
        return commentService.deleteComment(postId, commentId);
    }

    private Comment convertToEntity(SaveCommentResource resource) {
        return mapper.map(resource, Comment.class);
    }

    private CommentResource convertToResource(Comment entity) {
        return mapper.map(entity, CommentResource.class);
    }


}
