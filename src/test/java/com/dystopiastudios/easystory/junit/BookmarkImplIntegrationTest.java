package com.dystopiastudios.easystory.junit;

import com.dystopiastudios.easystory.domain.model.*;
import com.dystopiastudios.easystory.domain.repository.BookmarkRepository;
import com.dystopiastudios.easystory.domain.repository.HashtagRepository;
import com.dystopiastudios.easystory.domain.repository.PostRepository;
import com.dystopiastudios.easystory.domain.repository.UserRepository;
import com.dystopiastudios.easystory.domain.service.BookmarkService;
import com.dystopiastudios.easystory.domain.service.PostService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.service.BookmarkServiceImpl;
import com.dystopiastudios.easystory.service.PostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.print.Book;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BookmarkImplIntegrationTest {

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private HashtagRepository hashtagRepository;

    @MockBean
    private BookmarkRepository bookmarkRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private BookmarkService bookmarkService;


    @TestConfiguration
    static class BookmarkImplIntegrationTestConfiguration{
        @Bean
        public BookmarkService bookmarkService(){
            return new BookmarkServiceImpl();
        }
    }

    @Test
    @DisplayName("When GetBookmarkByUserIdAndPostId With Valid UserId and PostId Then Returns Bookmark")
    public void whenGetBookmarkByUserIdAndPostIdWithValidUserIdAndPostIdThenReturnsBookmark() {
        // Arrange
        User user=new User();
        Post post=new Post();
        Long Id=1L;
        user.setId(Id);
        post.setId(Id);
        Bookmark bookmark=new Bookmark();
        bookmark.setUser(user);
        bookmark.setPost(post);
        //given(postRepository.findByTitle(post.getTitle()))
        //        .willReturn(Optional.of(post));
        when(bookmarkRepository.findByUserIdAndPostId(user.getId(),post.getId())).thenReturn(Optional.of(bookmark));
        //Act
        Bookmark foundBookmark=bookmarkService.getBookmarkByUserIdAndPostId(user.getId(), post.getId());
        //Assert
        assertThat(foundBookmark.getUser().getId()).isEqualTo(Id);
        assertThat(foundBookmark.getPost().getId()).isEqualTo(Id);

    }

    @Test
    @DisplayName("When GetBookmarkByUserIdAndPostId With Invalid UserId and PostId Then Returns ResourceNotFoundException")
    public void whenGetBookmarkByUserIdAndPostIdWithInvalidUserIdAndPostIdThenReturnsResourceNotFoundException() {
        // Arrange
        User user = new User();
        Post post = new Post();
        Long Id = 1L;
        user.setId(Id);
        post.setId(Id);
        String template = "Resource %s not found for %s with value %s";
        when(bookmarkRepository.findByUserIdAndPostId(user.getId(), post.getId())).thenReturn(Optional.empty());
        String expectedMessage = String.format(template, "UserId", Id, "PostId", Id);
        //Act
        Throwable exception = catchThrowable(() -> {
            Bookmark foundBookmark = bookmarkService.getBookmarkByUserIdAndPostId(Id, Id);
        });
        //Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class);

    }
}
