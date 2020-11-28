package com.dystopiastudios.easystory.junit;

import com.dystopiastudios.easystory.domain.model.User;
import com.dystopiastudios.easystory.domain.repository.SubscriptionRepository;
import com.dystopiastudios.easystory.domain.repository.UserRepository;
import com.dystopiastudios.easystory.domain.service.UserService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserImplIntegrationTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @MockBean
    private SubscriptionRepository subscriptionRepository;

    @TestConfiguration
    static class UserImplIntegrationTestConfiguration{
        @Bean
        public UserService userService(){
            return new UserServiceImpl();
        }
    }

    @Test
    @DisplayName("When GetUserByUsername With Valid User Then Returns User")
    public void whenGetUserByUsernameWithValidUserThenReturnsUser(){
        String username = "ProUser1";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.getUserByUsername(username);

        // Assert
        assertThat(foundUser.getUsername()).isEqualTo(username);
    }


    @Test
    @DisplayName("When GetUserByUsername With Invalid Username Then Returns ResourceNotFoundException")
    public void whenGetUserByUsernameWithInvalidUsernameThenReturnsResourceNotFoundException() {
        // Arrange
        String username = "ProUser1";
        String template = "Resource %s not found for %s with value %s";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        String expectedMessage = String.format(template, "User", "Username", username);

        // Act
        Throwable exception = catchThrowable(() -> {
            User foundUser = userService.getUserByUsername(username);
        });

        // Assert
        assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedMessage);

    }
}

