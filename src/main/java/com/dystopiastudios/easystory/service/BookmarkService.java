package com.dystopiastudios.easystory.service;

import com.dystopiastudios.easystory.model.Bookmark;
import com.dystopiastudios.easystory.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface BookmarkService {

    Bookmark getBookmarkByIdAndUserId(Long userId, Long postId);
    Bookmark createBookmark(Long userId, Long postId);
    Page<Bookmark>getAllBookmarksByUserId(Long userId, Pageable pageable);
    ResponseEntity<?>deleteBookmark(Long userId, Long postId);
    Page<Bookmark>getAllBookmarks(Pageable pageable);
}
