package com.dystopiastudios.easystory.junit;

import com.dystopiastudios.easystory.domain.model.Post;
import com.dystopiastudios.easystory.domain.repository.HashtagRepository;
import com.dystopiastudios.easystory.domain.repository.PostRepository;
import com.dystopiastudios.easystory.domain.repository.UserRepository;
import com.dystopiastudios.easystory.domain.service.PostService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.service.PostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PostImplIntegrationTest {

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private HashtagRepository hashtagRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PostService postService;


    @TestConfiguration
    static class PostImplIntegrationTestConfiguration{
        @Bean
        public PostService postService(){
            return new PostServiceImpl();
        }
    }

    @Test
    @DisplayName("When GetPostByTitle With Valid Title Then Returns Post")
    public void whenGetPostByTitleWithValidTitleThenReturnsPost() {
        // Arrange
        String title = "Nice Post";
        Post post = new Post();
        post.setId(1L);
        post.setTitle(title);
        //given(postRepository.findByTitle(post.getTitle()))
        //        .willReturn(Optional.of(post));
        when(postRepository.findByTitle(title))
                .thenReturn(Optional.of(post));

        // Act
        Post foundPost = postService.getPostByTitle(title);

        // Assert
        assertThat(foundPost.getTitle()).isEqualTo(title);

    }

    @Test
    @DisplayName("When GetPostByTitle With Invalid Title Then Returns ResourceNotFoundException")
    public void whenGetPostByTitleWithInvalidTitleThenReturnsResourceNotFoundException() {
        // Arrange
        String title = "Nice Post";
        String template = "Resource %s not found for %s with value %s";
        when(postRepository.findByTitle(title))
                .thenReturn(Optional.empty());
        String expectedMessage = String.format(template, "Post", "Title", title);

        // Act
        Throwable exception = catchThrowable(() -> {
            Post foundPost = postService.getPostByTitle(title);
        });

        // Assert
        assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedMessage);

    }
}

