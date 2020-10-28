package com.dystopiastudios.easystory.service;

import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.model.Bookmark;
import com.dystopiastudios.easystory.model.Post;
import com.dystopiastudios.easystory.model.User;
import com.dystopiastudios.easystory.repository.BookmarkRepository;
import com.dystopiastudios.easystory.repository.PostRepository;
import com.dystopiastudios.easystory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookmarkServiceImpl implements BookmarkService{
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public Bookmark getBookmarkByIdAndUserId(Long userId, Long postId) {
        return bookmarkRepository.findByIdAndUserId(userId, postId)
                .orElseThrow(()->new ResourceNotFoundException("Bookmark not found with Id" + postId +  "and UserId" + userId));
    }

    @Override
    public Bookmark createBookmark(Long userId, Long postId) {
        User userObj = new User();
        if(!userRepository.equals(userId)){
            new ResourceNotFoundException("userId not found");
        }else{
            userObj.setId(userId);
        }
        Post postObj = new Post();
        if(!postRepository.equals(postId)){
            new ResourceNotFoundException("postId not found");
        }else{
            postObj.setId(postId);
        }
        Bookmark bookmarkObj = new Bookmark();
        bookmarkObj.setUser(userObj);
        bookmarkObj.setPosts(postObj);
        return bookmarkRepository.save(bookmarkObj);
    }

    @Override
    public Page<Bookmark> getAllBookmarksByUserId(Long userId, Pageable pageable) {
        return bookmarkRepository.findByUserId(userId, pageable);
    }

    @Override
    public ResponseEntity<?> deleteBookmark(Long userId, Long postId) {
        Bookmark bookmark = bookmarkRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("User", "Post", postId));
        bookmarkRepository.delete(bookmark);
        return ResponseEntity.ok().build();
    }

    @Override
    public Page<Bookmark> getAllBookmarks(Pageable pageable) {
        return bookmarkRepository.findAll(pageable);
    }
}
