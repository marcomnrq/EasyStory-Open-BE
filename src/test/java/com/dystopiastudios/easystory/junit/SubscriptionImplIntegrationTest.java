package com.dystopiastudios.easystory.junit;

import com.dystopiastudios.easystory.domain.model.Bookmark;
import com.dystopiastudios.easystory.domain.model.Post;
import com.dystopiastudios.easystory.domain.model.Subscription;
import com.dystopiastudios.easystory.domain.model.User;
import com.dystopiastudios.easystory.domain.repository.*;
import com.dystopiastudios.easystory.domain.service.BookmarkService;
import com.dystopiastudios.easystory.domain.service.SubscriptionService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.service.BookmarkServiceImpl;
import com.dystopiastudios.easystory.service.SubscriptionServiceImpl;
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
public class SubscriptionImplIntegrationTest {
    @MockBean
    private PostRepository postRepository;

    @MockBean
    private SubscriptionRepository subscriptionRepository;

    @MockBean
    private BookmarkRepository bookmarkRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private SubscriptionService subscriptionService;


    @TestConfiguration
    static class SubscriptionImplIntegrationTestConfiguration{
        @Bean
        public SubscriptionService subscriptionService(){
            return new SubscriptionServiceImpl();
        }
    }

    @Test
    @DisplayName("When GetSubscriptionByUserIdAndSubscribedId With Valid UserId and SubscribedId Then Returns Subscription")
    public void whenGetSubscriptionByUserIdAndSubscribedIdWithValidUserIdAndSubscribedIdThenReturnsSubscription() {
        // Arrange
        User user=new User();
        User subscribed=new User();
        Long Id=1L;
        user.setId(Id);
        subscribed.setId(Id);
        Subscription subscription=new Subscription();
        subscription.setUser(user);
        subscription.setSubscribed(subscribed);
        //given(postRepository.findByTitle(post.getTitle()))
        //        .willReturn(Optional.of(post));
        when(subscriptionRepository.findByUserIdAndSubscribedId(user.getId(),subscribed.getId())).thenReturn(Optional.of(subscription));
        //Act
        Subscription foundSubscription=subscriptionService.getSubscriptionByUserIdAndSubscribedId(user.getId(), subscribed.getId());
        //Assert
        assertThat(foundSubscription.getUser().getId()).isEqualTo(Id);
        assertThat(foundSubscription.getSubscribed().getId()).isEqualTo(Id);

    }

    @Test
    @DisplayName("When GetSubscriptionByUserIdAndSubscribedId With Invalid UserId and SubscribedId Then Returns ResourceNotFoundException")
    public void whenGetSubscriptionByUserIdAndSubscribedIdWithInvalidUserIdAndSubscribedIdThenReturnsResourceNotFoundException() {
        // Arrange
        User user = new User();
        User subscribed = new User();
        Long Id = 1L;
        user.setId(Id);
        subscribed.setId(Id);
        String template = "Resource %s not found for %s with value %s";
        when(subscriptionRepository.findByUserIdAndSubscribedId(user.getId(), subscribed.getId())).thenReturn(Optional.empty());
        String expectedMessage = String.format(template, "UserId", Id, "SubscribedId", Id);
        //Act
        Throwable exception = catchThrowable(() -> {
            Subscription foundSubscription = subscriptionService.getSubscriptionByUserIdAndSubscribedId(Id, Id);
        });
        //Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class);

    }
}
