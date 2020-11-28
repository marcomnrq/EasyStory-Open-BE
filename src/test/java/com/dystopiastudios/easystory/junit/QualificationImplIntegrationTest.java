package com.dystopiastudios.easystory.junit;

import com.dystopiastudios.easystory.domain.model.Post;
import com.dystopiastudios.easystory.domain.model.Qualification;
import com.dystopiastudios.easystory.domain.model.User;
import com.dystopiastudios.easystory.domain.repository.PostRepository;
import com.dystopiastudios.easystory.domain.repository.QualificationRepository;
import com.dystopiastudios.easystory.domain.repository.UserRepository;
import com.dystopiastudios.easystory.domain.service.QualificationService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.service.QualificationServiceImpl;
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
public class QualificationImplIntegrationTest {

    @MockBean
    private QualificationRepository qualificationRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PostRepository postRepository;
    @Autowired
    private QualificationService qualificationService;

    @TestConfiguration
    static class  QualificationImplIntegrationTestConfiguration{
        @Bean
        public QualificationService qualificationService() {return  new QualificationServiceImpl();
        }
    }
    @Test
    @DisplayName("When GetQualificationByUserIdAndPostId With Valid UserId And PostId Then Returns Qualification")
    public  void WhenGetQualificationByUserIdAndPostIdWithValidUserIdAndPostIdThenReturnQualifications(){
        //Arrange
        User user=new User();
        Post post=new Post();
        Long Id=1L;
        user.setId(Id);
        post.setId(Id);
        Qualification qualification=new Qualification();
        qualification.setUser(user);
        qualification.setPost(post);

        when(qualificationRepository.findByUserIdAndPostId(user.getId(),post.getId())).thenReturn(Optional.of(qualification));
        //Act
        Qualification foundQualification=qualificationService.getQualificationByUserIdAndPostId(user.getId(), post.getId());
        //Assert
        assertThat(foundQualification.getUser().getId()).isEqualTo(Id);
        assertThat(foundQualification.getPost().getId()).isEqualTo(Id);
    }
    @Test
    @DisplayName("When GetQualificationByUserIdAndPostId With Valid UserId And PostId Then Returns ResourceNotFoundException")
    public  void WhenGetQualificationByUserIdAndPostIdWithValidUserIdAndPostIdThenReturnsResourceNotFoundException() {
        //Arrange
        User user = new User();
        Post post = new Post();
        Long Id = 1L;
        user.setId(Id);
        post.setId(Id);
        String template = "Resource %s not found for %s with value %s";
        when(qualificationRepository.findByUserIdAndPostId(user.getId(), post.getId())).thenReturn(Optional.empty());
        String expectedMessage = String.format(template, "UserId", Id, "PostId", Id);
        //Act
        Throwable exception = catchThrowable(() -> {
            Qualification foundQualification = qualificationService.getQualificationByUserIdAndPostId(Id, Id);
        });
        //Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
    }
}
