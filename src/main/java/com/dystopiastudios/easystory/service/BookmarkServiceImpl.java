package com.dystopiastudios.easystory.service;

import com.dystopiastudios.easystory.domain.service.BookmarkService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.domain.model.Bookmark;
import com.dystopiastudios.easystory.domain.model.Post;
import com.dystopiastudios.easystory.domain.model.User;
import com.dystopiastudios.easystory.domain.repository.BookmarkRepository;
import com.dystopiastudios.easystory.domain.repository.PostRepository;
import com.dystopiastudios.easystory.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public Bookmark getBookmarkByUserIdAndPostId(Long userId, Long postId) {
        return bookmarkRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(()->new ResourceNotFoundException("Bookmark not found with Id" + postId +  "and UserId" + userId));
    }


    @Override
    public Bookmark createBookmark(Long userId, Long postId, Bookmark bookmark) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Optional<Bookmark> existingBookmark = bookmarkRepository.findByUserIdAndPostId(userId, postId);
        if (!existingBookmark.isEmpty()){
            throw new IllegalArgumentException("El usuario ya tiene un bookmark con este postId");
        }
        // Si to do fue correcto entonces...
        bookmark.setUser(user);
        bookmark.setPost(post);
        return bookmarkRepository.save(bookmark);
    }

    @Override
    public Page<Bookmark> getAllBookmarksByUserId(Long userId, Pageable pageable) {
        return bookmarkRepository.findByUserId(userId, pageable);
    }

    @Override
    public ResponseEntity<?> deleteBookmark(Long userId, Long postId) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndPostId(userId,postId).orElseThrow(()->
                new ResourceNotFoundException("Post", "Id", userId));
        bookmarkRepository.delete(bookmark);
        return ResponseEntity.ok().build();
    }

    @Override
    public Page<Bookmark> getAllBookmarks(Pageable pageable) {
        return bookmarkRepository.findAll(pageable);
    }
}
