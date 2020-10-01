package com.dystopiastudios.easystory.service;

import com.dystopiastudios.easystory.model.Comment;
import com.dystopiastudios.easystory.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PostService {
    Page<Post> getAllPostsByUserId(Long userId, Pageable pageable);

    Post getPostByIdAndUserId(Long userId, Long postId);

    Post assignPostTag(Long postId, Long tagId);

    Post unassignPostTag(Long postId, Long tagId);

    Page<Post> getAllPostsByTagId(Long tagId, Pageable pageable);

    ResponseEntity<?> deletePost(Long postId);

    Post updatePost(Long postId, Post postRequest);

    Post createPost(Post post);

    Post getPostById(Long postId);

    Page<Post> getAllPosts(Pageable pageable);
}
