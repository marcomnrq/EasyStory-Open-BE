package com.dystopiastudios.easystory.junit;

import com.dystopiastudios.easystory.domain.model.Hashtag;
import com.dystopiastudios.easystory.domain.repository.HashtagRepository;
import com.dystopiastudios.easystory.domain.repository.PostRepository;
import com.dystopiastudios.easystory.domain.service.HashtagService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.service.HashtagServiceImpl;
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
public class HashtagImplIntegrationTest {
    @MockBean
    private HashtagRepository hashtagRepository;

    @MockBean
    private PostRepository postRepository;
    @Autowired
    private HashtagService hashtagService;


    @TestConfiguration
    static class HashtagImplIntegrationTestConfiguration{
        @Bean
        public HashtagService hashtagService(){
            return new HashtagServiceImpl();
        }
    }

    @Test
    @DisplayName("When GetHashtagByName With Valid Name Then Returns Hashtag")
    public void whenGetHashtagByNameWithValidNameThenReturnsHashtag() {
        // Arrange
        String name = "Comidas";
        Hashtag hashtag = new Hashtag();
        hashtag.setName(name);
        hashtag.setId(1L);
        when(hashtagRepository.findByName(name))
                .thenReturn(Optional.of(hashtag));

        // Act
        Hashtag foundHashtag = hashtagService.getHashtagByName(name);

        // Assert
        assertThat(foundHashtag.getName()).isEqualTo(name);

    }

    @Test
    @DisplayName("When GetHashtagByName With Invalid Name Then Returns ResourceNotFoundException")
    public void whenGetHashtagByNameWithInvalidNameThenReturnsResourceNotFoundException() {
        // Arrange
        String name = "Terror";
        String template = "Resource %s not found for %s with value %s";
        when(hashtagRepository.findByName(name))
                .thenReturn(Optional.empty());
        String expectedMessage = String.format(template, "Hashtag", "Name", name);

        // Act
        Throwable exception = catchThrowable(() -> {
           Hashtag foundHashtag = hashtagService.getHashtagByName(name);
        });

        // Assert
        assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedMessage);

    }
}
