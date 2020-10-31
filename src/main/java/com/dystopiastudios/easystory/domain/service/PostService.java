package com.dystopiastudios.easystory.domain.service;

import com.dystopiastudios.easystory.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PostService {
    Page<Post> getAllPostsByUserId(Long userId, Pageable pageable);

    Post getPostByIdAndUserId(Long userId, Long postId);

    Post assignPostHashtag(Long postId, Long hashtagId);

    Post unassignPostHashtag(Long postId, Long hashtagId);

    Page<Post> getAllPostsByHashtagId(Long hashtagId, Pageable pageable);

    ResponseEntity<?> deletePost(Long userId, Long postId);

    Post updatePost(Long userId, Long postId, Post postRequest);

    Post createPost(Long userId, Post post);

    Post getPostById(Long postId);

    Page<Post> getAllPosts(Pageable pageable);

    Post getPostByTitle(String title);
}
