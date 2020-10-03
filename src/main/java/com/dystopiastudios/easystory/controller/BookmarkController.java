package com.dystopiastudios.easystory.controller;

import com.dystopiastudios.easystory.model.Bookmark;
import com.dystopiastudios.easystory.resource.SaveBookmarkResource;
import com.dystopiastudios.easystory.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "bookmarks", description = "Bookmarks API")
@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping("/users/{userId}/bookmark")
    public Page<SaveBookmarkResource>getAllBookmarksByUserId(
            @PathVariable(name = "userId") Long userId,
            Pageable pageable){
        Page<Bookmark> bookmarkPage = bookmarkService.getAllBookmarksByUserId(userId, pageable);
        List<SaveBookmarkResource> resource = bookmarkPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resource, pageable,resource.size());
    }

    @GetMapping("/users/{usersId}/bookmarks/{bookmarkId}")
    public SaveBookmarkResource getBookmarkByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                                         @PathVariable(name= "bookmarkId") Long bookmarkId){
        return convertToResource(bookmarkService.getBookmarkByIdAndUserId(userId, bookmarkId));
    }

    @PostMapping("/users/{userId}/posts")
    public SaveBookmarkResource createBookmark(@PathVariable(name = "userId")Long userId,
                                               @PathVariable(name = "postId") Long postId){
        return convertToResource(bookmarkService.createBookmark(userId,postId));
    }

    @DeleteMapping("/users/{userId}/bookmark/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "userId")Long userId,
                                        @PathVariable(name = "postId") Long postId){
        return bookmarkService.deleteBookmark(userId, postId);
    }

    @Operation(summary = "Get Bookmarks", description = "Get All Bookmarks by Pages", tags = { "bookmarks"} )
    @GetMapping("/bookmarks")
    public Page<SaveBookmarkResource>getAllBookmarks(
            @Parameter(description = "Pageable Parameter")
            Pageable pageable){
        Page<Bookmark> bookmarkPage = bookmarkService.getAllBookmarks(pageable);
        List<SaveBookmarkResource> resources = bookmarkPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    private SaveBookmarkResource convertToResource(Bookmark entity){
        return mapper.map(entity, SaveBookmarkResource.class);
    }
}
