package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.forums.ForumDto;
import com.autiwomen.auti_women.dtos.forums.ForumInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Comment;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.models.Like;
import com.autiwomen.auti_women.models.View;
import com.autiwomen.auti_women.repositories.CommentRepository;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.repositories.LikeRepository;
import com.autiwomen.auti_women.repositories.ViewRepository;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForumServiceTest {

    @InjectMocks
    ForumService forumService;

    @Mock
    ForumRepository forumRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    LikeRepository likeRepository;

    @Mock
    ViewRepository viewRepository;

    @Mock
    CommentRepository commentRepository;

    @Captor
    ArgumentCaptor<Forum> captor;

    Forum forum1;
    Forum forum2;
    Comment comment1;
    Comment comment2;
    Like like1;
    Like like2;
    View view1;
    View view2;
    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        LocalDate dob = LocalDate.of(1990, 5, 15);
        user1 = new User("user1", dob);
        user2 = new User("user2", dob);
        String currentDate = String.valueOf(LocalDate.now());
        forum1 = new Forum(1L, "user1", "1990-05-15", "title1", "text1", currentDate, null, "topic1", 0, 0, 0);
        forum2 = new Forum(2L, "user2", "1990-05-15", "title2", "text2", currentDate, null, "topic2", 0, 0, 0);
        comment1 = new Comment(1L, "user1", "comment1", currentDate, "1990-05-15");
        comment2 = new Comment(2L, "user2", "comment2", currentDate, "1990-05-15");
        like1 = new Like(user1, forum1);
        like2 = new Like(user2, forum2);
        view1 = new View(user1, forum1);
        view2 = new View(user2, forum2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllForums() {
        List<Forum> forumList = Arrays.asList(forum1, forum2);

        when(forumRepository.findAll()).thenReturn(forumList);
        when(likeRepository.getLikeCountByForumId(forum1.getId())).thenReturn(10);
        when(likeRepository.getLikeCountByForumId(forum2.getId())).thenReturn(20);
        when(viewRepository.getViewCountByForumId(forum1.getId())).thenReturn(100);
        when(viewRepository.getViewCountByForumId(forum2.getId())).thenReturn(200);
        when(commentRepository.getCommentCountByForumId(forum1.getId())).thenReturn(5);
        when(commentRepository.getCommentCountByForumId(forum2.getId())).thenReturn(15);

        List<ForumDto> forumDtoList = forumService.getAllForums();

        assertEquals(2, forumDtoList.size());

        ForumDto forumDto1 = forumDtoList.get(0);
        assertEquals(forum1.getId(), forumDto1.getId());
        assertEquals(10, forumDto1.getLikesCount());
        assertEquals(100, forumDto1.getViewsCount());
        assertEquals(5, forumDto1.getCommentsCount());

        ForumDto forumDto2 = forumDtoList.get(1);
        assertEquals(forum2.getId(), forumDto2.getId());
        assertEquals(20, forumDto2.getLikesCount());
        assertEquals(200, forumDto2.getViewsCount());
        assertEquals(15, forumDto2.getCommentsCount());
    }

    @Test
    void createForum() {
        String username = "user1";
        ForumInputDto forumInputDto = new ForumInputDto();
        forumInputDto.setTitle("Test Title");
        forumInputDto.setText("Test Text");
        forumInputDto.setTopic("Test Topic");

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));

        ForumDto forumDto = forumService.createForum(forumInputDto, username);

        assertNotNull(forumDto);
        assertEquals(forumInputDto.getTitle(), forumDto.getTitle());
        assertEquals(forumInputDto.getText(), forumDto.getText());
        assertEquals(forumInputDto.getTopic(), forumDto.getTopic());
        assertEquals(username, forumDto.getName());
        assertEquals(user1.getDob().toString(), forumDto.getAge());
        assertEquals(0, forumDto.getLikesCount());
        assertEquals(0, forumDto.getViewsCount());
        assertEquals(0, forumDto.getCommentsCount());

        verify(forumRepository).save(any(Forum.class));
    }

    @Test
    void assignForumToUser() {
        Long forumId = 1L;
        String username = "user1";

        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum1));
        when(userRepository.findById(username)).thenReturn(Optional.of(user1));

        forumService.assignForumToUser(forumId, username);

        verify(forumRepository, times(1)).save(captor.capture());
        Forum forum = captor.getValue();

        assertEquals(username, forum.getName());
    }

    @Test
    void updateLastReaction() {
        Long forumId = 1L;
        Comment lastComment = new Comment();
        lastComment.setDate(String.valueOf(LocalDate.now()));

        when(commentRepository.findTopByForumIdOrderByDateDesc(forumId)).thenReturn(Optional.of(lastComment));
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum1));

        forumService.updateLastReaction(forumId);

        verify(forumRepository, times(1)).save(captor.capture());
        Forum updatedForum = captor.getValue();

        assertEquals(String.valueOf(LocalDate.now()), updatedForum.getLastReaction());
    }

    @Test
    void getForumById() {
        Long forumId = 1L;

        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum1));
        when(likeRepository.getLikeCountByForumId(forum1.getId())).thenReturn(10);
        when(viewRepository.getViewCountByForumId(forum1.getId())).thenReturn(100);
        when(commentRepository.getCommentCountByForumId(forum1.getId())).thenReturn(5);

        ForumDto forumDto = forumService.getForumById(forumId);

        assertNotNull(forumDto);
        assertEquals(forum1.getId(), forumDto.getId());
        assertEquals(10, forumDto.getLikesCount());
        assertEquals(100, forumDto.getViewsCount());
        assertEquals(5, forumDto.getCommentsCount());
    }

    @Test
    void updateForum_Success() {
        Long forumId = 1L;
        ForumDto updateForumDto = new ForumDto();
        updateForumDto.setName("Updated Name");
        updateForumDto.setTitle("Updated Title");
        updateForumDto.setText("Updated Text");

        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum1));
        when(forumRepository.save(any(Forum.class))).thenReturn(forum1);

        ForumDto updatedForumDto = forumService.updateForum(forumId, updateForumDto);

        assertNotNull(updatedForumDto);
        assertEquals(updateForumDto.getName(), updatedForumDto.getName());
        assertEquals(updateForumDto.getTitle(), updatedForumDto.getTitle());
        assertEquals(updateForumDto.getText(), updatedForumDto.getText());

        verify(forumRepository, times(1)).findById(forumId);
        verify(forumRepository, times(1)).save(any(Forum.class));
    }

    @Test
    void updateForum_ForumNotFound() {
        Long forumId = 1L;
        ForumDto updateForumDto = new ForumDto();
        updateForumDto.setName("Updated Name");
        updateForumDto.setTitle("Updated Title");
        updateForumDto.setText("Updated Text");

        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.updateForum(forumId, updateForumDto));

        verify(forumRepository, times(1)).findById(forumId);
        verify(forumRepository, never()).save(any(Forum.class));
    }


    @Test
    void deleteForum() {
        Long forumId = 1L;

        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum1));
        when(commentRepository.findAllByForumId(forumId)).thenReturn(Arrays.asList(comment1, comment2));

        forumService.deleteForum(forumId);

        verify(commentRepository, times(1)).findAllByForumId(forumId);
        verify(commentRepository, times(1)).save(comment1);
        verify(commentRepository, times(1)).save(comment2);
        verify(commentRepository, times(1)).deleteAllByForumId(forumId);
        verify(forumRepository, times(1)).deleteById(forumId);

        for (Comment comment : Arrays.asList(comment1, comment2)) {
            comment.setForum(null);
            comment.setUser(null);
            commentRepository.save(comment);
        }

        assertNull(comment1.getForum());
        assertNull(comment1.getUser());
        assertNull(comment2.getForum());
        assertNull(comment2.getUser());
    }



    @Test
    void getLikedForumsByUsername() {
    }

    @Test
    void getViewedForumsByUsername() {
    }

    @Test
    void getCommentedForumsByUsername() {
    }

    @Test
    void getForumsByTopic() {
    }

    @Test
    void getUniqueTopics() {
    }

    @Test
    void getSortedUniqueTopics() {
    }

    @Test
    void getTopicFrequency() {
    }

    @Test
    void fromForum() {
        Forum forum = new Forum();
        forum.setId(1L);
        forum.setName("Test Name");
        forum.setAge("30");
        forum.setTitle("Test Title");
        forum.setText("Test Text");
        forum.setDate("2023-10-01");
        forum.setLastReaction("2023-10-02");
        forum.setTopic("Test Topic");
        forum.setLikesCount(10);
        forum.setViewsCount(100);
        forum.setCommentsCount(5);

        ForumDto forumDto = forumService.fromForum(forum);

        assertNotNull(forumDto);
        assertEquals(forum.getId(), forumDto.id);
        assertEquals(forum.getName(), forumDto.name);
        assertEquals(forum.getAge(), forumDto.age);
        assertEquals(forum.getTitle(), forumDto.title);
        assertEquals(forum.getText(), forumDto.text);
        assertEquals(forum.getDate(), forumDto.date);
        assertEquals(forum.getLastReaction(), forumDto.lastReaction);
        assertEquals(forum.getTopic(), forumDto.topic);
        assertEquals(forum.getLikesCount(), forumDto.likesCount);
        assertEquals(forum.getViewsCount(), forumDto.viewsCount);
        assertEquals(forum.getCommentsCount(), forumDto.commentsCount);
    }

    @Test
    void toForum() {
        ForumInputDto forumInputDto = new ForumInputDto();
        forumInputDto.setName("Test Name");
        forumInputDto.setTitle("Test Title");
        forumInputDto.setText("Test Text");
        forumInputDto.setDate("2023-10-01");
        forumInputDto.setLastReaction("2023-10-02");
        forumInputDto.setTopic("Test Topic");

        Forum forum = forumService.toForum(forumInputDto);

        assertNotNull(forum);
        assertEquals(forumInputDto.getName(), forum.getName());
        assertEquals(forumInputDto.getTitle(), forum.getTitle());
        assertEquals(forumInputDto.getText(), forum.getText());
        assertEquals(forumInputDto.getDate(), forum.getDate());
        assertEquals(forumInputDto.getLastReaction(), forum.getLastReaction());
        assertEquals(forumInputDto.getTopic(), forum.getTopic());
    }

    @Test
    void createForum_UserNotFound() {
        String username = "nonexistentUser";
        ForumInputDto forumInputDto = new ForumInputDto();
        forumInputDto.setTitle("Test Title");
        forumInputDto.setText("Test Text");
        forumInputDto.setTopic("Test Topic");

        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.createForum(forumInputDto, username));
    }

    @Test
    void assignForumToUser_ForumNotFound() {
        Long forumId = 1L;
        String username = "user1";

        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());
        when(userRepository.findById(username)).thenReturn(Optional.of(user1));

        assertThrows(RecordNotFoundException.class, () -> forumService.assignForumToUser(forumId, username));
    }

    @Test
    void assignForumToUser_UserNotFound() {
        Long forumId = 1L;
        String username = "user1";

        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum1));
        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.assignForumToUser(forumId, username));
    }

    @Test
    void updateLastReaction_CommentNotFound() {
        Long forumId = 1L;

        when(commentRepository.findTopByForumIdOrderByDateDesc(forumId)).thenReturn(Optional.empty());

        forumService.updateLastReaction(forumId);

        verify(forumRepository, never()).save(any(Forum.class));
    }

    @Test
    void updateLastReaction_ForumNotFound() {
        Long forumId = 1L;
        Comment lastComment = new Comment();
        lastComment.setDate(String.valueOf(LocalDate.now()));

        when(commentRepository.findTopByForumIdOrderByDateDesc(forumId)).thenReturn(Optional.of(lastComment));
        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.updateLastReaction(forumId));
    }

    @Test
    void getForumById_ForumNotFound() {
        Long forumId = 1L;

        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.getForumById(forumId));
    }

    @Test
    void deleteForum_ForumNotFound() {
        Long forumId = 1L;

        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.deleteForum(forumId));

        verify(commentRepository, never()).findAllByForumId(anyLong());
        verify(commentRepository, never()).save(any(Comment.class));
        verify(commentRepository, never()).deleteAllByForumId(anyLong());
        verify(forumRepository, never()).deleteById(anyLong());
    }



}