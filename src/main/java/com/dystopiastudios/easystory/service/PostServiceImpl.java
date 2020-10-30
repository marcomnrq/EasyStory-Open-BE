package com.dystopiastudios.easystory.service;

import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.model.Comment;
import com.dystopiastudios.easystory.model.Hashtag;
import com.dystopiastudios.easystory.model.Post;

import com.dystopiastudios.easystory.model.User;
import com.dystopiastudios.easystory.repository.HashtagRepository;
import com.dystopiastudios.easystory.repository.PostRepository;

import com.dystopiastudios.easystory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private HashtagRepository hashtagRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<Post> getAllPostsByUserId(Long userId, Pageable pageable) {
        return postRepository.findByUserId(userId, pageable);
    }

    @Override
    public Post getPostByIdAndUserId(Long userId, Long postId) {
        return postRepository.findByIdAndUserId(postId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post not found with Id " + postId +
                                " and UserId " + userId));
    }

    @Override
    public Post assignPostTag(Long postId, Long tagId) {
        Hashtag tag = hashtagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId));
        return postRepository.findById(postId).map(post -> {
            if (!post.getHashtags().contains(tag)) {
                post.getHashtags().add(tag);
                return postRepository.save(post);
            }
            return post;
        }).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

    }

    @Override
    public Post unassignPostTag(Long postId, Long tagId) {
        Hashtag tag = hashtagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId));
        return postRepository.findById(postId).map(post -> {
            post.getHashtags().remove(tag);
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
    }

    //Page es una interfaz
    public Page<Post> getAllPostsByTagId(Long tagId, Pageable pageable) {
        return hashtagRepository.findById(tagId).map(tag -> {
            List<Post> posts = tag.getPosts();
            int postsCount = posts.size();
            return new PageImpl<>(posts, pageable, postsCount);
        })
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId));
    }

    @Override
    public ResponseEntity<?> deletePost(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        postRepository.delete(post);
        return ResponseEntity.ok().build();
    }

    @Override
    public Post updatePost(Long userId, Long postId, Post postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setContent(postRequest.getContent());
        return postRepository.save(post);
    }

    @Override
    public Post createPost(Long userId, Post post) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
