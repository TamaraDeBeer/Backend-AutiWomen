package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.comments.CommentInputDto;
import com.autiwomen.auti_women.models.Comment;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.repositories.CommentRepository;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.security.UserRepository;
import com.autiwomen.auti_women.security.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    ForumRepository forumRepository;

    @Mock
    ForumService forumService;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CommentService commentService;

    @Captor
    ArgumentCaptor<Comment> captor;

    Comment comment1;
    Comment comment2;
    Forum forum1;
    Forum forum2;
    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        LocalDate dob = LocalDate.of(1990, 5, 15);
        user1 = new User("user1", dob);
        user2 = new User("user2", dob);
        String currentDate = String.valueOf(LocalDate.now());
        comment1 = new Comment(1L, "user1", "comment1", currentDate, "1990-05-15");
        comment2 = new Comment(2L, "user2", "comment2", currentDate, "1990-05-15");
        forum1 = new Forum(1L, "user1", "1990-05-15", "title1", "text1", currentDate, "School");
        forum2 = new Forum(2L, "user2", "1990-05-15", "title2", "text2", currentDate, "School");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createComment() {
        CommentInputDto commentInputDto = new CommentInputDto("user1", "comment1", String.valueOf(LocalDate.now()), "1990-05-15");

        when(userRepository.findById("user1")).thenReturn(Optional.of(user1));
        doReturn(comment1).when(commentRepository).save(any(Comment.class));

        commentService.createComment(commentInputDto, "user1");
        verify(commentRepository, times(1)).save(captor.capture());
        Comment commentCreated = captor.getValue();

        assertEquals(comment1.getName(), commentCreated.getName());
        assertEquals(comment1.getText(), commentCreated.getText());
        assertEquals(comment1.getDate(), commentCreated.getDate());
        assertEquals(comment1.getAge(), commentCreated.getAge());


    }

    @Test
    void assignCommentToForum() {
    }

    @Test
    void assignCommentToUser() {
    }

    @Test
    void getCommentsByForumId() {
    }

    @Test
    void getCommentsByUsername() {
    }

    @Test
    void getCommentCountByForumId() {
    }

    @Test
    void deleteComment() {
    }

    @Test
    void updateComment() {
    }

    @Test
    void fromComment() {
    }

    @Test
    void toComment() {
    }
}