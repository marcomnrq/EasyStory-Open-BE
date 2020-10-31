package com.dystopiastudios.easystory.controller;

import com.dystopiastudios.easystory.domain.model.Post;
import com.dystopiastudios.easystory.resource.CommentResource;
import com.dystopiastudios.easystory.resource.PostResource;
import com.dystopiastudios.easystory.resource.SavePostResource;
import com.dystopiastudios.easystory.domain.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "posts", description = "Posts API")
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PostService postService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Posts returned", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/users/{userId}/posts")
    public Page<PostResource> getAllPostsByUserId(
            @PathVariable(name = "userId") Long userId,
            Pageable pageable) {
        Page<Post> postPage = postService.getAllPostsByUserId(userId, pageable);
        List<PostResource> resources = postPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<PostResource>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public PostResource getPostByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                                   @PathVariable(name = "postId") Long postId) {
        return convertToResource(postService.getPostByIdAndUserId(userId, postId));
    }

    @PostMapping("/users/{userId}/posts")
    public PostResource createPost(@PathVariable(name = "userId") Long userId,
                                         @Valid @RequestBody SavePostResource resource) {
        return convertToResource(postService.createPost(userId, convertToEntity(resource)));

    }

    @PutMapping("/users/{userId}/posts/{postId}")
    public PostResource updatePost(@PathVariable(name = "userId") Long userId,
                                         @PathVariable(name = "postId") Long postId,
                                         @Valid @RequestBody SavePostResource resource) {
        return convertToResource(postService.updatePost(userId, postId, convertToEntity(resource)));
    }

    @DeleteMapping("/users/{userId}/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "userId") Long userId,
                                           @PathVariable(name = "postId") Long postId) {
        return postService.deletePost(userId, postId);
    }

    @Operation(summary = "Get Posts", description = "Get All Posts by Pages", tags = { "posts" })
    @GetMapping("/posts")
    public Page<PostResource> getAllPosts(
            @Parameter(description="Pageable Parameter")
            Pageable pageable) {
        Page<Post> postsPage = postService.getAllPosts(pageable);
        List<PostResource> resources = postsPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<PostResource>(resources,pageable , resources.size());
    }

    @GetMapping("/hashtags/{hashtagId}/posts")
    public Page<PostResource> getAllPostsByHashtagId(@PathVariable(name = "hashtagId") Long hashtagId, Pageable pageable) {
        Page<Post> postsPage = postService.getAllPostsByHashtagId(hashtagId, pageable);
        List<PostResource> resources = postsPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @PostMapping("/posts/{postId}/hashtags/{hashtagId}")
    public PostResource assignPostHashtag(@PathVariable(name = "postId") Long postId,
                              @PathVariable(name = "hashtagId") Long hashtagId) {
        return convertToResource(postService.assignPostHashtag(postId, hashtagId));
    }

    @DeleteMapping("/posts/{postId}/hashtags/{hashtagId}")
    public PostResource unassignPostHashtag(@PathVariable(name = "postId") Long postId,
                                @PathVariable(name = "hashtagId") Long hashtagId) {

        return convertToResource(postService.unassignPostHashtag(postId, hashtagId));
    }

    private Post convertToEntity(SavePostResource resource) {
        return mapper.map(resource, Post.class);
    }

    private PostResource convertToResource(Post entity) {
        return mapper.map(entity, PostResource.class);
    }

}
