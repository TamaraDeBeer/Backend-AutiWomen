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

    @Mock
    MockedStatic<SecurityUtil> securityUtilMock;

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
        forum1 = new Forum(1L, "user1", "1990-05-15", "title1", "text1", "2023-10-01", null, "topic1", 0, 0, 0);
        forum2 = new Forum(2L, "user2", "1990-05-15", "title2", "text2", "2023-10-02", null, "topic2", 0, 0, 0);
        comment1 = new Comment(1L, "user1", "comment1", "2023-10-01", "1990-05-15");
        comment2 = new Comment(2L, "user2", "comment2", "2023-10-02", "1990-05-15");
        like1 = new Like(user1, forum1);
        like2 = new Like(user2, forum2);
        view1 = new View(user1, forum1);
        view2 = new View(user2, forum2);

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin("user1")).thenReturn(true);
    }

    @AfterEach
    void tearDown() {
        securityUtilMock.close();
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
    void updateLastReaction_ForumNotFound() {
        Long forumId = 1L;
        Comment lastComment = new Comment();
        lastComment.setDate(String.valueOf(LocalDate.now()));

        when(commentRepository.findTopByForumIdOrderByDateDesc(forumId)).thenReturn(Optional.of(lastComment));
        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.updateLastReaction(forumId));
    }

    @Test
    void updateLastReaction_CommentNotFound() {
        Long forumId = 1L;

        when(commentRepository.findTopByForumIdOrderByDateDesc(forumId)).thenReturn(Optional.empty());

        forumService.updateLastReaction(forumId);

        verify(forumRepository, never()).save(any(Forum.class));
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
    void getForumById_ForumNotFound() {
        Long forumId = 1L;

        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.getForumById(forumId));
    }

    @Test
    void updateForum() {
        Long forumId = 1L;
        String username = "user1";
        ForumDto updateForumDto = new ForumDto();
        updateForumDto.setTitle("Updated Title");
        updateForumDto.setText("Updated Text");

        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum1));
        when(forumRepository.save(any(Forum.class))).thenReturn(forum1);

        ForumDto updatedForumDto = forumService.updateForum(forumId, updateForumDto, username);

        assertNotNull(updatedForumDto);
        assertEquals(updateForumDto.getTitle(), updatedForumDto.getTitle());
        assertEquals(updateForumDto.getText(), updatedForumDto.getText());
    }

    @Test
    void updateForum_ForumNotFound() {
        Long forumId = 1L;
        String username = "user1";
        ForumDto updateForumDto = new ForumDto();
        updateForumDto.setName("Updated Name");
        updateForumDto.setTitle("Updated Title");
        updateForumDto.setText("Updated Text");

        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.updateForum(forumId, updateForumDto, username));
    }

    @Test
    void updateForum_Forbidden() {
        Long forumId = 1L;
        String username = "user1";
        ForumDto updateForumDto = new ForumDto();

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(false);

        assertThrows(SecurityException.class, () -> forumService.updateForum(forumId, updateForumDto, username));
    }

    @Test
    void deleteForum() {
        Long forumId = 1L;
        String username = "user1";

        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forum1));

        forumService.deleteForum(forumId, username);

        verify(commentRepository, times(1)).deleteAllByForumId(forumId);
        verify(forumRepository, times(1)).deleteById(forumId);
    }

    @Test
    void deleteForum_ForumNotFound() {
        Long forumId = 1L;
        String username = "user1";

        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.deleteForum(forumId, username));

        verify(commentRepository, never()).deleteAllByForumId(anyLong());
        verify(forumRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteForum_Forbidden() {
        Long forumId = 1L;
        String username = "user1";

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(false);

        assertThrows(SecurityException.class, () -> forumService.deleteForum(forumId, username));
    }

    @Test
    void getForumsByUsername() {
        String username = "user1";

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));
        when(forumRepository.findByUser(user1)).thenReturn(new HashSet<>(Arrays.asList(forum1, forum2)));
        when(likeRepository.getLikeCountByForumId(forum1.getId())).thenReturn(10);
        when(likeRepository.getLikeCountByForumId(forum2.getId())).thenReturn(20);
        when(viewRepository.getViewCountByForumId(forum1.getId())).thenReturn(100);
        when(viewRepository.getViewCountByForumId(forum2.getId())).thenReturn(200);
        when(commentRepository.getCommentCountByForumId(forum1.getId())).thenReturn(5);
        when(commentRepository.getCommentCountByForumId(forum2.getId())).thenReturn(15);

        Set<ForumDto> forumDtos = forumService.getForumsByUsername(username);

        assertNotNull(forumDtos);
        assertEquals(2, forumDtos.size());

        for (ForumDto forumDto : forumDtos) {
            if (forumDto.getId().equals(forum1.getId())) {
                assertEquals(user1.getUsername(), forumDto.getName());
                assertEquals(user1.getDob().toString(), forumDto.getAge());
            } else if (forumDto.getId().equals(forum2.getId())) {
                assertEquals(user2.getUsername(), forumDto.getName());
                assertEquals(user2.getDob().toString(), forumDto.getAge());
            }
        }

        verify(userRepository, times(1)).findById(username);
        verify(forumRepository, times(1)).findByUser(user1);
        verify(likeRepository, times(1)).getLikeCountByForumId(forum1.getId());
        verify(likeRepository, times(1)).getLikeCountByForumId(forum2.getId());
        verify(viewRepository, times(1)).getViewCountByForumId(forum1.getId());
        verify(viewRepository, times(1)).getViewCountByForumId(forum2.getId());
        verify(commentRepository, times(1)).getCommentCountByForumId(forum1.getId());
        verify(commentRepository, times(1)).getCommentCountByForumId(forum2.getId());
    }

    @Test
    void getForumsByUsername_UserNotFound() {
        String username = "nonexistentUser";

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(true);

        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.getForumsByUsername(username));

        verify(userRepository, times(1)).findById(username);
        verify(forumRepository, never()).findByUser(any(User.class));
    }

    @Test
    void getForumsByUsername_Forbidden() {
        String username = "user1";

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(false);

        assertThrows(SecurityException.class, () -> forumService.getForumsByUsername(username));
    }

    @Test
    void getForumsByUsername_NoForumsFound() {
        String username = "user1";
        User user = new User();
        user.setUsername(username);

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(true);

        when(userRepository.findById(username)).thenReturn(Optional.of(user));
        when(forumRepository.findByUser(user)).thenReturn(Collections.emptySet());

        assertThrows(RecordNotFoundException.class, () -> forumService.getForumsByUsername(username));
    }

    @Test
    void getForumsByUsername_SetsNameAndAge() {
        String username = "user1";

        forum1.setUser(user1);
        forum2.setUser(user2);

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));
        when(forumRepository.findByUser(user1)).thenReturn(new HashSet<>(Arrays.asList(forum1, forum2)));

        Set<ForumDto> forumDtos = forumService.getForumsByUsername(username);

        assertNotNull(forumDtos);
        assertEquals(2, forumDtos.size());

        for (ForumDto forumDto : forumDtos) {
            if (forumDto.getId().equals(forum1.getId())) {
                assertEquals(user1.getUsername(), forumDto.getName());
                assertEquals(user1.getDob().toString(), forumDto.getAge());
            } else if (forumDto.getId().equals(forum2.getId())) {
                assertEquals(user2.getUsername(), forumDto.getName());
                assertEquals(user2.getDob().toString(), forumDto.getAge());
            }
        }

        verify(userRepository, times(1)).findById(username);
        verify(forumRepository, times(1)).findByUser(user1);
    }

    @Test
    void getLikedForumsByUsername() {
        String username = "user1";

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));
        when(likeRepository.findLikedForumsByUser(user1)).thenReturn(new HashSet<>(Arrays.asList(forum1, forum2)));
        when(likeRepository.getLikeCountByForumId(forum1.getId())).thenReturn(10);
        when(likeRepository.getLikeCountByForumId(forum2.getId())).thenReturn(20);
        when(viewRepository.getViewCountByForumId(forum1.getId())).thenReturn(100);
        when(viewRepository.getViewCountByForumId(forum2.getId())).thenReturn(200);
        when(commentRepository.getCommentCountByForumId(forum1.getId())).thenReturn(5);
        when(commentRepository.getCommentCountByForumId(forum2.getId())).thenReturn(15);

        Set<ForumDto> likedForumDtos = forumService.getLikedForumsByUsername(username);

        assertNotNull(likedForumDtos);
        assertEquals(2, likedForumDtos.size());

        for (ForumDto forumDto : likedForumDtos) {
            if (forumDto.getId().equals(forum1.getId())) {
                assertEquals(10, forumDto.getLikesCount());
                assertEquals(100, forumDto.getViewsCount());
                assertEquals(5, forumDto.getCommentsCount());
            } else if (forumDto.getId().equals(forum2.getId())) {
                assertEquals(20, forumDto.getLikesCount());
                assertEquals(200, forumDto.getViewsCount());
                assertEquals(15, forumDto.getCommentsCount());
            }
        }

        verify(userRepository, times(1)).findById(username);
        verify(likeRepository, times(1)).findLikedForumsByUser(user1);
        verify(likeRepository, times(1)).getLikeCountByForumId(forum1.getId());
        verify(likeRepository, times(1)).getLikeCountByForumId(forum2.getId());
        verify(viewRepository, times(1)).getViewCountByForumId(forum1.getId());
        verify(viewRepository, times(1)).getViewCountByForumId(forum2.getId());
        verify(commentRepository, times(1)).getCommentCountByForumId(forum1.getId());
        verify(commentRepository, times(1)).getCommentCountByForumId(forum2.getId());
    }

    @Test
    void getLikedForumsByUsername_UserNotFound() {
        String username = "nonexistentUser";

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(true);

        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.getLikedForumsByUsername(username));

        verify(userRepository, times(1)).findById(username);
        verify(likeRepository, never()).findLikedForumsByUser(any(User.class));
        verify(likeRepository, never()).getLikeCountByForumId(anyLong());
        verify(viewRepository, never()).getViewCountByForumId(anyLong());
        verify(commentRepository, never()).getCommentCountByForumId(anyLong());
    }

    @Test
    void getLikedForumsByUsername_Forbidden() {
        String username = "user1";

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(false);

        assertThrows(SecurityException.class, () -> forumService.getLikedForumsByUsername(username));
    }

    @Test
    void getLikedForumsByUsername_NoForumsFound() {
        String username = "user1";
        User user = new User();
        user.setUsername(username);

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(true);

        when(userRepository.findById(username)).thenReturn(Optional.of(user));
        when(likeRepository.findLikedForumsByUser(user)).thenReturn(Collections.emptySet());

        assertThrows(RecordNotFoundException.class, () -> forumService.getLikedForumsByUsername(username));
    }

    @Test
    void getViewedForumsByUsername() {
        String username = "user1";

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));
        when(viewRepository.findViewedForumsByUser(user1)).thenReturn(new HashSet<>(Arrays.asList(forum1, forum2)));
        when(likeRepository.getLikeCountByForumId(forum1.getId())).thenReturn(10);
        when(likeRepository.getLikeCountByForumId(forum2.getId())).thenReturn(20);
        when(viewRepository.getViewCountByForumId(forum1.getId())).thenReturn(100);
        when(viewRepository.getViewCountByForumId(forum2.getId())).thenReturn(200);
        when(commentRepository.getCommentCountByForumId(forum1.getId())).thenReturn(5);
        when(commentRepository.getCommentCountByForumId(forum2.getId())).thenReturn(15);

        Set<ForumDto> viewedForumDtos = forumService.getViewedForumsByUsername(username);

        assertNotNull(viewedForumDtos);
        assertEquals(2, viewedForumDtos.size());

        for (ForumDto forumDto : viewedForumDtos) {
            if (forumDto.getId().equals(forum1.getId())) {
                assertEquals(10, forumDto.getLikesCount());
                assertEquals(100, forumDto.getViewsCount());
                assertEquals(5, forumDto.getCommentsCount());
            } else if (forumDto.getId().equals(forum2.getId())) {
                assertEquals(20, forumDto.getLikesCount());
                assertEquals(200, forumDto.getViewsCount());
                assertEquals(15, forumDto.getCommentsCount());
            }
        }

        verify(userRepository, times(1)).findById(username);
        verify(viewRepository, times(1)).findViewedForumsByUser(user1);
        verify(likeRepository, times(1)).getLikeCountByForumId(forum1.getId());
        verify(likeRepository, times(1)).getLikeCountByForumId(forum2.getId());
        verify(viewRepository, times(1)).getViewCountByForumId(forum1.getId());
        verify(viewRepository, times(1)).getViewCountByForumId(forum2.getId());
        verify(commentRepository, times(1)).getCommentCountByForumId(forum1.getId());
        verify(commentRepository, times(1)).getCommentCountByForumId(forum2.getId());
    }

    @Test
    void getViewedForumsByUsername_UserNotFound() {
        String username = "nonexistentUser";

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(true);

        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.getViewedForumsByUsername(username));

        verify(userRepository, times(1)).findById(username);
        verify(viewRepository, never()).findViewedForumsByUser(any(User.class));
        verify(likeRepository, never()).getLikeCountByForumId(anyLong());
        verify(viewRepository, never()).getViewCountByForumId(anyLong());
        verify(commentRepository, never()).getCommentCountByForumId(anyLong());
    }

    @Test
    void getViewedForumsByUsername_Forbidden() {
        String username = "user1";

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(false);

        assertThrows(SecurityException.class, () -> forumService.getViewedForumsByUsername(username));
    }

    @Test
    void getViewedForumsByUsername_NoForumsFound() {
        String username = "user1";
        User user = new User();
        user.setUsername(username);

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(true);

        when(userRepository.findById(username)).thenReturn(Optional.of(user));
        when(viewRepository.findViewedForumsByUser(user)).thenReturn(Collections.emptySet());

        assertThrows(RecordNotFoundException.class, () -> forumService.getViewedForumsByUsername(username));
    }

    @Test
    void getCommentedForumsByUsername() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        Forum forum = new Forum();
        forum.setId(1L);
        forum.setName("Test Forum");
        forum.setTitle("Test Title");
        forum.setText("Test Text");
        forum.setDate("2023-10-01");
        forum.setLastReaction("2023-10-02");
        forum.setTopic("Test Topic");

        Set<Forum> commentedForums = new HashSet<>();
        commentedForums.add(forum);

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(true);

        when(userRepository.findById(username)).thenReturn(Optional.of(user));
        when(commentRepository.findCommentedForumsByUser(user)).thenReturn(commentedForums);
        when(likeRepository.getLikeCountByForumId(forum.getId())).thenReturn(10);
        when(viewRepository.getViewCountByForumId(forum.getId())).thenReturn(100);
        when(commentRepository.getCommentCountByForumId(forum.getId())).thenReturn(5);

        Set<ForumDto> result = forumService.getCommentedForumsByUsername(username);

        assertNotNull(result);
        assertEquals(1, result.size());
        ForumDto forumDto = result.iterator().next();
        assertEquals(forum.getId(), forumDto.id);
        assertEquals(forum.getName(), forumDto.name);
        assertEquals(forum.getTitle(), forumDto.title);
        assertEquals(forum.getText(), forumDto.text);
        assertEquals(forum.getDate(), forumDto.date);
        assertEquals(forum.getLastReaction(), forumDto.lastReaction);
        assertEquals(forum.getTopic(), forumDto.topic);
        assertEquals(10, forumDto.likesCount);
        assertEquals(100, forumDto.viewsCount);
        assertEquals(5, forumDto.commentsCount);
    }

    @Test
    void getCommentedForumsByUsername_UserNotFound() {
        String username = "nonexistentUser";

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(true);

        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> forumService.getCommentedForumsByUsername(username));

        verify(userRepository, times(1)).findById(username);
        verify(commentRepository, never()).findCommentedForumsByUser(any(User.class));
        verify(likeRepository, never()).getLikeCountByForumId(anyLong());
        verify(viewRepository, never()).getViewCountByForumId(anyLong());
        verify(commentRepository, never()).getCommentCountByForumId(anyLong());
    }

    @Test
    void getCommentedForumsByUsername_Forbidden() {
        String username = "user1";

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(false);

        assertThrows(SecurityException.class, () -> forumService.getCommentedForumsByUsername(username));
    }

    @Test
    void getCommentedForumsByUsername_NoForumsFound() {
        String username = "user1";
        User user = new User();
        user.setUsername(username);

        securityUtilMock.when(() -> SecurityUtil.isOwnerOrAdmin(username)).thenReturn(true);

        when(userRepository.findById(username)).thenReturn(Optional.of(user));
        when(commentRepository.findCommentedForumsByUser(user)).thenReturn(Collections.emptySet());

        assertThrows(RecordNotFoundException.class, () -> forumService.getCommentedForumsByUsername(username));
    }

    @Test
    void getForumsSortedByLikes() {
        List<Forum> forums = Arrays.asList(forum1, forum2);

        when(forumRepository.findAll()).thenReturn(forums);
        when(likeRepository.getLikeCountByForumId(forum1.getId())).thenReturn(10);
        when(likeRepository.getLikeCountByForumId(forum2.getId())).thenReturn(20);
        when(viewRepository.getViewCountByForumId(forum1.getId())).thenReturn(100);
        when(viewRepository.getViewCountByForumId(forum2.getId())).thenReturn(200);
        when(commentRepository.getCommentCountByForumId(forum1.getId())).thenReturn(5);
        when(commentRepository.getCommentCountByForumId(forum2.getId())).thenReturn(15);
        when(commentRepository.findTopByForumIdOrderByDateDesc(forum1.getId())).thenReturn(Optional.of(comment1));
        when(commentRepository.findTopByForumIdOrderByDateDesc(forum2.getId())).thenReturn(Optional.of(comment2));

        List<ForumDto> sortedForums = forumService.getForumsSortedByLikes();

        assertNotNull(sortedForums);
        assertEquals(2, sortedForums.size());

        ForumDto firstForum = sortedForums.get(0);
        ForumDto secondForum = sortedForums.get(1);

        assertEquals(forum2.getId(), firstForum.getId());
        assertEquals(20, firstForum.getLikesCount());
        assertEquals(forum1.getId(), secondForum.getId());
        assertEquals(10, secondForum.getLikesCount());

        verify(forumRepository, times(1)).findAll();
        verify(likeRepository, times(1)).getLikeCountByForumId(forum1.getId());
        verify(likeRepository, times(1)).getLikeCountByForumId(forum2.getId());
        verify(viewRepository, times(1)).getViewCountByForumId(forum1.getId());
        verify(viewRepository, times(1)).getViewCountByForumId(forum2.getId());
        verify(commentRepository, times(1)).getCommentCountByForumId(forum1.getId());
        verify(commentRepository, times(1)).getCommentCountByForumId(forum2.getId());
    }

    @Test
    void getForumsSortedByDate() {
        forum1.setDate("2023-10-01");
        forum2.setDate("2023-10-02");

        List<Forum> forums = Arrays.asList(forum1, forum2);

        when(forumRepository.findAll()).thenReturn(forums);
        when(likeRepository.getLikeCountByForumId(forum1.getId())).thenReturn(10);
        when(likeRepository.getLikeCountByForumId(forum2.getId())).thenReturn(20);
        when(viewRepository.getViewCountByForumId(forum1.getId())).thenReturn(100);
        when(viewRepository.getViewCountByForumId(forum2.getId())).thenReturn(200);
        when(commentRepository.getCommentCountByForumId(forum1.getId())).thenReturn(5);
        when(commentRepository.getCommentCountByForumId(forum2.getId())).thenReturn(15);
        when(commentRepository.findTopByForumIdOrderByDateDesc(forum1.getId())).thenReturn(Optional.of(comment1));
        when(commentRepository.findTopByForumIdOrderByDateDesc(forum2.getId())).thenReturn(Optional.of(comment2));

        List<ForumDto> sortedForums = forumService.getForumsSortedByDate();

        assertNotNull(sortedForums);
        assertEquals(2, sortedForums.size());

        ForumDto firstForum = sortedForums.get(0);
        ForumDto secondForum = sortedForums.get(1);

        assertEquals(forum2.getId(), firstForum.getId());
        assertEquals(forum2.getDate(), firstForum.getDate());
        assertEquals(forum1.getId(), secondForum.getId());
        assertEquals(forum1.getDate(), secondForum.getDate());

        verify(forumRepository, times(1)).findAll();
        verify(likeRepository, times(1)).getLikeCountByForumId(forum1.getId());
        verify(likeRepository, times(1)).getLikeCountByForumId(forum2.getId());
        verify(viewRepository, times(1)).getViewCountByForumId(forum1.getId());
        verify(viewRepository, times(1)).getViewCountByForumId(forum2.getId());
        verify(commentRepository, times(1)).getCommentCountByForumId(forum1.getId());
        verify(commentRepository, times(1)).getCommentCountByForumId(forum2.getId());
    }

    @Test
    void searchForums() {
        String title = "Test Title";
        List<Forum> forums = Arrays.asList(forum1, forum2);

        when(forumRepository.findAllByTitleContainingIgnoreCase(title)).thenReturn(forums);

        List<ForumDto> result = forumService.searchForums(title);

        assertNotNull(result);
        assertEquals(2, result.size());

        ForumDto forumDto1 = result.get(0);
        assertEquals(forum1.getId(), forumDto1.getId());
        assertEquals(forum1.getTitle(), forumDto1.getTitle());

        ForumDto forumDto2 = result.get(1);
        assertEquals(forum2.getId(), forumDto2.getId());
        assertEquals(forum2.getTitle(), forumDto2.getTitle());

        verify(forumRepository, times(1)).findAllByTitleContainingIgnoreCase(title);
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
}