package com.dystopiastudios.easystory.junit;

import com.dystopiastudios.easystory.domain.model.Comment;
import com.dystopiastudios.easystory.domain.model.Post;
import com.dystopiastudios.easystory.domain.model.User;
import com.dystopiastudios.easystory.domain.repository.CommentRepository;
import com.dystopiastudios.easystory.domain.repository.PostRepository;
import com.dystopiastudios.easystory.domain.repository.UserRepository;
import com.dystopiastudios.easystory.domain.service.CommentService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.service.CommentServiceImpl;
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
public class CommentImplIntegrationTest {
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PostRepository postRepository;
    @Autowired
    private CommentService commentService;

    @TestConfiguration
    static class  CommentImplIntegrationTestConfiguration{
        @Bean
        public CommentService commentService() {return  new CommentServiceImpl();
        }
    }
    @Test
    @DisplayName("When GetCommentByUserIdAndPostId With Valid UserId And PostId Then Returns Comment")
    public  void WhenGetCommentByUserIdAndPostIdWithValidUserIdAndPostIdThenReturnComment(){
        //Arrange
        User user=new User();
        Post post=new Post();
        Long Id=1L;
        user.setId(Id);
        post.setId(Id);
        Comment comment=new Comment();
        comment.setUser(user);
        comment.setPost(post);

        when(commentRepository.findByUserIdAndPostId(user.getId(),post.getId())).thenReturn(Optional.of(comment));
        //Act
        Comment foundComment=commentService.getCommentByUserIdAndPostId(user.getId(), post.getId());
        //Assert
        assertThat(foundComment.getUser().getId()).isEqualTo(Id);
        assertThat(foundComment.getPost().getId()).isEqualTo(Id);
    }
    @Test
    @DisplayName("When GetCommentByUserIdAndPostId With Valid UserId And PostId Then Returns ResourceNotFoundException")
    public  void WhenGetCommentByUserIdAndPostIdWithValidUserIdAndPostIdThenReturnsResourceNotFoundException() {
        //Arrange
        User user = new User();
        Post post = new Post();
        Long Id = 1L;
        user.setId(Id);
        post.setId(Id);
        String template = "Resource %s not found for %s with value %s";
        when(commentRepository.findByUserIdAndPostId(user.getId(), post.getId())).thenReturn(Optional.empty());
        String expectedMessage = String.format(template, "UserId", Id, "PostId", Id);
        //Act
        Throwable exception = catchThrowable(() -> {
            Comment foundComment = commentService.getCommentByUserIdAndPostId(Id, Id);
        });
        //Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
    }
}
