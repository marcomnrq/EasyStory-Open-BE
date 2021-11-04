package com.dystopiastudios.easystory.controller;

import com.dystopiastudios.easystory.domain.model.Bookmark;
import com.dystopiastudios.easystory.domain.model.Hashtag;
import com.dystopiastudios.easystory.resource.BookmarkResource;
import com.dystopiastudios.easystory.resource.HashtagResource;
import com.dystopiastudios.easystory.resource.SaveBookmarkResource;
import com.dystopiastudios.easystory.domain.service.BookmarkService;
import com.dystopiastudios.easystory.resource.SaveHashtagResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "bookmarks", description = "Bookmarks desc")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://easystory-open.web.app")
public class BookmarkController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BookmarkService bookmarkService;

    //@Operation(security={ @SecurityRequirement(name="Authorization") })
    @Operation (summary = "Get bookmarks by a user")
    @GetMapping("/users/{userId}/bookmarks")
    public Page<BookmarkResource>getAllBookmarksByUserId(
            @PathVariable(name = "userId") Long userId, Pageable pageable){

        List<BookmarkResource> bookmarks= bookmarkService.getAllBookmarksByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int bookmark_count=bookmarks.size();
        return new PageImpl<>(bookmarks, pageable, bookmark_count);
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") }, summary = "Add a post to saved bookmarks")
    @PostMapping("/users/{userId}/posts/{postId}/bookmarks")
    public BookmarkResource createBookmark(@PathVariable(name = "userId")Long userId,
                                           @PathVariable(name = "postId") Long postId,
                                           @Valid @RequestBody SaveBookmarkResource resource){
        return convertToResource(bookmarkService.createBookmark(userId,postId,convertToEntity(resource)));
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") }, summary = "Get a bookmark by a post and user id")
    @GetMapping("/users/{userId}/posts/{postId}/bookmarks")
    public BookmarkResource getBookmarkByUserIdAndPostId(@PathVariable(name = "userId") Long userId,
                                                         @PathVariable(name= "postId") Long postId){
        return convertToResource(bookmarkService.getBookmarkByUserIdAndPostId(userId, postId));
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") }, summary = "Removed bookmark from a user post")
    @DeleteMapping("/users/{userId}/posts/{postId}/bookmarks")
    public ResponseEntity<?> deleteBookmark(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name= "postId") Long postId) {
        return bookmarkService.deleteBookmark(userId, postId);
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") }, summary = "Get all bookmarks")
    @GetMapping("/bookmarks")
    public Page<BookmarkResource>getAllBookmarks(
            @Parameter(description = "Pageable Parameter")
            Pageable pageable){
        Page<Bookmark> bookmarkPage = bookmarkService.getAllBookmarks(pageable);
        List<BookmarkResource> resources = bookmarkPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    private BookmarkResource convertToResource(Bookmark entity){
        return mapper.map(entity, BookmarkResource.class);
    }
    private Bookmark convertToEntity(SaveBookmarkResource resource) {
        return mapper.map(resource, Bookmark.class);
    }

}
