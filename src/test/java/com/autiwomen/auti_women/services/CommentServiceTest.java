package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.comments.CommentDto;
import com.autiwomen.auti_women.dtos.comments.CommentInputDto;
import com.autiwomen.auti_women.dtos.forums.ForumDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Comment;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.repositories.CommentRepository;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.security.repositories.UserRepository;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.security.utils.SecurityUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
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

    @Mock
    MockedStatic<SecurityUtil> securityUtilMock;

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
        LocalDate currentDate = LocalDate.now();
        comment1 = new Comment(1L, "user1", "comment1", currentDate, dob);
        comment2 = new Comment(2L, "user2", "comment2", currentDate, dob);
        forum1 = new Forum(1L, "Name1", dob, "title", "text", currentDate, "School", 0, 0, 0);
        forum2 = new Forum(2L, "Name1", dob, "title2", "text2", currentDate, "topic", 0, 0, 0);

        comment1.setForum(forum1);
        comment2.setForum(forum2);
        comment1.setUser(user1);
        comment2.setUser(user2);

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin("user1")).thenReturn(true);

    }

    @AfterEach
    void tearDown() {
        securityUtilMock.close();
    }

    @Test
    void createComment() {
        CommentInputDto commentInputDto = new CommentInputDto("user1", "comment1", LocalDate.now(), (LocalDate.of(1990, 5, 15)));
        String username = "user1";

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));
        doReturn(comment1).when(commentRepository).save(any(Comment.class));

        CommentDto result = commentService.createComment(commentInputDto, username);

        verify(commentRepository, times(1)).save(captor.capture());
        Comment savedComment = captor.getValue();

        assertEquals(comment1.getName(), savedComment.getName());
        assertEquals(comment1.getText(), savedComment.getText());
        assertEquals(comment1.getDate(), savedComment.getDate());
        assertEquals(comment1.getDob(), savedComment.getDob());
        assertNotNull(result);
        assertEquals(comment1.getName(), result.getName());
        assertEquals(comment1.getText(), result.getText());
        assertEquals(comment1.getDate(), result.getDate());
    }

    @Test
    void createComment_Forbidden() {
        String username = "user1";
        CommentInputDto commentInputDto = new CommentInputDto("user1", "comment1", LocalDate.now(), (LocalDate.of(1990, 5, 15)));

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(false);

        assertThrows(SecurityException.class, () -> commentService.createComment(commentInputDto, username));
    }

    @Test
    void getAllComments() {
        List<Comment> comments = List.of(comment1, comment2);
        when(commentRepository.findAll()).thenReturn(comments);

        List<CommentDto> commentDtos = commentService.getAllComments();

        assertNotNull(commentDtos);
        assertEquals(2, commentDtos.size());
        assertEquals(comment1.getId(), commentDtos.get(0).getId());
        assertEquals(comment1.getName(), commentDtos.get(0).getName());
        assertEquals(comment1.getText(), commentDtos.get(0).getText());
        assertEquals(comment1.getDate(), commentDtos.get(0).getDate());
        assertEquals(comment2.getId(), commentDtos.get(1).getId());
        assertEquals(comment2.getName(), commentDtos.get(1).getName());
        assertEquals(comment2.getText(), commentDtos.get(1).getText());
        assertEquals(comment2.getDate(), commentDtos.get(1).getDate());

        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void assignCommentToForum() {
        Long commentId = 1L;
        Long forumId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment1));
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum1));

        commentService.assignCommentToForum(commentId, forumId);

        verify(commentRepository, times(1)).save(captor.capture());
        Comment savedComment = captor.getValue();

        assertEquals(forum1, savedComment.getForum());
    }

    @Test
    void assignCommentToForum_CommentNotFound() {
        Long commentId = 1L;
        Long forumId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum1));

        assertThrows(RecordNotFoundException.class, () -> commentService.assignCommentToForum(commentId, forumId));
    }

    @Test
    void assignCommentToForum_ForumNotFound() {
        Long commentId = 1L;
        Long forumId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment1));
        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> commentService.assignCommentToForum(commentId, forumId));
    }

    @Test
    void assignCommentToUser() {
        Long commentId = 1L;
        String username = "user1";

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment1));
        when(userRepository.findById(username)).thenReturn(Optional.of(user1));

        commentService.assignCommentToUser(commentId, username);

        verify(commentRepository, times(1)).save(captor.capture());
        Comment savedComment = captor.getValue();

        assertEquals(user1, savedComment.getUser());
    }

    @Test
    void assignCommentToUser_CommentNotFound() {
        Long commentId = 1L;
        String username = "user1";

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        when(userRepository.findById(username)).thenReturn(Optional.of(user1));

        assertThrows(RecordNotFoundException.class, () -> commentService.assignCommentToUser(commentId, username));
    }

    @Test
    void assignCommentToUser_UserNotFound() {
        Long commentId = 1L;
        String username = "user1";

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment1));
        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> commentService.assignCommentToUser(commentId, username));
    }

    @Test
    void getCommentsByForumId() {
        Long forumId = 1L;
        List<Comment> comments = List.of(comment1);
        forum1.setCommentsList(comments);

        when(commentRepository.findByForumId(forumId)).thenReturn(comments);

        List<CommentDto> commentDtos = commentService.getCommentsByForumId(forumId);

        assertNotNull(commentDtos);
        assertEquals(1, commentDtos.size());
        assertEquals(comment1.getId(), commentDtos.get(0).getId());
        assertEquals(comment1.getName(), commentDtos.get(0).getName());
        assertEquals(comment1.getText(), commentDtos.get(0).getText());
        assertEquals(comment1.getDate(), commentDtos.get(0).getDate());
        assertEquals(comment1.getDob(), commentDtos.get(0).getDob());
    }

    @Test
    void getCommentsByForumId_CommentsNotFound() {
        Long forumId = 1L;

        when(commentRepository.findByForumId(forumId)).thenReturn(List.of());

        assertThrows(RecordNotFoundException.class, () -> commentService.getCommentsByForumId(forumId));
    }

    @Test
    void getCommentsByUsername() {
        String username = "user1";
        List<Comment> expectedComments = List.of(comment1);
        user1.setCommentsList(expectedComments);

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));

        List<Comment> actualComments = commentService.getCommentsByUsername(username);

        assertEquals(expectedComments, actualComments);
    }

    @Test
    void getCommentsByUsername_Forbidden() {
        String username = "user1";

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(false);

        assertThrows(SecurityException.class, () -> commentService.getCommentsByUsername(username));
    }

    @Test
    void getCommentsByUsername_UserNotFound() {
        String username = "nonexistentUser";

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(true);
        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> commentService.getCommentsByUsername(username));
    }

    @Test
    void getCommentCountByForumId() {
        Long forumId = 1L;
        List<Comment> comments = List.of(comment1);
        forum1.setCommentsList(comments);

        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum1));

        int commentCount = commentService.getCommentCountByForumId(forumId);

        assertEquals(comments.size(), commentCount);
    }

    @Test
    void getCommentCountByForumId_ForumNotFound() {
        Long forumId = 1L;

        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> commentService.getCommentCountByForumId(forumId));
    }

    @Test
    void deleteComment() {
        Long commentId = 1L;
        String username = "user1";

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment1));

        commentService.deleteComment(commentId, username);

        verify(commentRepository, times(1)).delete(comment1);
    }

    @Test
    void deleteComment_Forbidden() {
        Long commentId = 1L;
        String username = "user1";

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(false);

        assertThrows(SecurityException.class, () -> commentService.deleteComment(commentId, username));
    }

    @Test
    void deleteComment_CommentNotFound() {
        Long commentId = 1L;
        String username = "user1";

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> commentService.deleteComment(commentId, username));
    }

    @Test
    void updateComment() {
        Long commentId = 1L;
        String username = "user1";
        CommentDto updateCommentDto = new CommentDto();
        updateCommentDto.setText("Updated comment text");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment1));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment1);

        CommentDto updatedCommentDto = commentService.updateComment(commentId, updateCommentDto, username);

        verify(commentRepository, times(1)).save(captor.capture());
        Comment savedComment = captor.getValue();

        assertEquals("Updated comment text", savedComment.getText());
    }

    @Test
    void updateComment_Forbidden() {
        Long commentId = 1L;
        String username = "user1";
        CommentDto updateCommentDto = new CommentDto();
        updateCommentDto.setText("Updated comment text");

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(false);

        assertThrows(SecurityException.class, () -> commentService.updateComment(commentId, updateCommentDto, username));
    }

    @Test
    void updateComment_CommentNotFound() {
        Long commentId = 1L;
        String username = "user1";
        CommentDto updateCommentDto = new CommentDto();
        updateCommentDto.setText("Updated comment text");

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> commentService.updateComment(commentId, updateCommentDto, username));
    }

    @Test
    void fromComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setName("user1");
        comment.setText("Comment");
        comment.setDate(LocalDate.of(2023, 10, 1));

        Forum forum = new Forum();
        forum.setId(1L);
        comment.setForum(forum);
        ForumDto forumDto = new ForumDto();
        forumDto.setId(1L);
        when(forumService.fromForum(forum)).thenReturn(forumDto);

        CommentDto commentDto = commentService.fromComment(comment);

        assertNotNull(commentDto);
        assertEquals(comment.getId(), commentDto.getId());
        assertEquals(comment.getName(), commentDto.getName());
        assertEquals(comment.getText(), commentDto.getText());
        assertEquals(comment.getDate(), commentDto.getDate());
        assertEquals(forumDto, commentDto.getForumDto());
    }

    @Test
    void toComment() {
        CommentInputDto commentInputDto = new CommentInputDto();
        commentInputDto.setName("user1");
        commentInputDto.setText("Comment");
        commentInputDto.setDate(LocalDate.of(2023, 10, 1));

        Comment comment = commentService.toComment(commentInputDto);

        assertEquals(commentInputDto.getName(), comment.getName());
        assertEquals(commentInputDto.getText(), comment.getText());
        assertEquals(commentInputDto.getDate(), comment.getDate());
    }
}