package com.dystopiastudios.easystory.service;

import com.dystopiastudios.easystory.domain.service.PostService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.domain.model.User;
import com.dystopiastudios.easystory.domain.model.Hashtag;
import com.dystopiastudios.easystory.domain.model.Post;

import com.dystopiastudios.easystory.domain.repository.HashtagRepository;
import com.dystopiastudios.easystory.domain.repository.PostRepository;
import com.dystopiastudios.easystory.domain.repository.UserRepository;

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
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

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
    public Post assignPostHashtag(Long postId, Long hashtagId) {
        Hashtag hashtag = hashtagRepository.findById(hashtagId)
                .orElseThrow(() -> new ResourceNotFoundException("Hashtag", "Id", hashtagId));
        return postRepository.findById(postId).map(post -> {
            if (!post.getHashtags().contains(hashtag)) {
                post.getHashtags().add(hashtag);
                return postRepository.save(post);
            }
            return post;
        }).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

    }

    @Override
    public Post unassignPostHashtag(Long postId, Long hashtagId) {
        Hashtag hashtag = hashtagRepository.findById(hashtagId)
                .orElseThrow(() -> new ResourceNotFoundException("Hashtag", "Id", hashtagId));
        return postRepository.findById(postId).map(post -> {
            post.getHashtags().remove(hashtag);
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
    }

    public Page<Post> getAllPostsByHashtagId(Long hashtagId, Pageable pageable) {
        return hashtagRepository.findById(hashtagId).map(hashtag -> {
            List<Post> posts = hashtag.getPosts();
            int postsCount = posts.size();
            return new PageImpl<>(posts, pageable, postsCount);
        }).orElseThrow(() -> new ResourceNotFoundException("Hashtag", "Id", hashtagId));
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

    @Override
    public Post getPostByTitle(String title) {
        return postRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Title", title));
    }
}
