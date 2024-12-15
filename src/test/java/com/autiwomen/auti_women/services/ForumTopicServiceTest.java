package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Comment;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.models.Like;
import com.autiwomen.auti_women.models.View;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.security.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForumTopicServiceTest {

    @InjectMocks
    ForumTopicService forumTopicService;

    @Mock
    ForumRepository forumRepository;

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
        LocalDate currentDate = LocalDate.now();
        comment1 = new Comment(1L, "user1", "comment1", currentDate, dob);
        comment2 = new Comment(2L, "user2", "comment2", currentDate, dob);
        forum1 = new Forum(1L, "Name1", dob, "title", "text", currentDate, "School", 0, 0, 0);
        forum2 = new Forum(2L, "Name1", dob, "title2", "text2", currentDate, "topic", 0, 0, 0);
        like1 = new Like(user1, forum1);
        like2 = new Like(user2, forum2);
        view1 = new View(user1, forum1);
        view2 = new View(user2, forum2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getForumsByTopic() {
        String topic = "topic1";

        Forum forum1 = new Forum();
        forum1.setTopic("topic1");

        Forum forum2 = new Forum();
        forum2.setTopic("topic2");

        List<Forum> forums = Arrays.asList(forum1, forum2);

        when(forumRepository.findAll()).thenReturn(forums);

        List<Forum> result = forumTopicService.getForumsByTopic(topic);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("topic1", result.get(0).getTopic());

        verify(forumRepository, times(1)).findAll();
    }

    @Test
    void getForumsByTopic_TopicNotFound() {
        String topic = "topic3";

        Forum forum1 = new Forum();
        forum1.setTopic("topic1");

        Forum forum2 = new Forum();
        forum2.setTopic("topic2");

        List<Forum> forums = Arrays.asList(forum1, forum2);

        when(forumRepository.findAll()).thenReturn(forums);

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> forumTopicService.getForumsByTopic(topic));

        assertNotNull(exception);
        assertEquals("No forums found for this topic", exception.getMessage());

        verify(forumRepository, times(1)).findAll();
    }


    @Test
    void getUniqueTopics() {
        Forum forum1 = new Forum();
        forum1.setTopic("topic1");

        Forum forum2 = new Forum();
        forum2.setTopic("topic2");

        Forum forum3 = new Forum();
        forum3.setTopic("topic1");

        List<Forum> forums = Arrays.asList(forum1, forum2, forum3);

        when(forumRepository.findAll()).thenReturn(forums);

        Set<String> result = forumTopicService.getUniqueTopics();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("topic1"));
        assertTrue(result.contains("topic2"));

        verify(forumRepository, times(1)).findAll();
    }

    @Test
    void getSortedUniqueTopics() {
        Forum forum1 = new Forum();
        forum1.setTopic("topic1");

        Forum forum2 = new Forum();
        forum2.setTopic("topic2");

        Forum forum3 = new Forum();
        forum3.setTopic("topic1");

        List<Forum> forums = Arrays.asList(forum1, forum2, forum3);

        when(forumRepository.findAll()).thenReturn(forums);

        List<String> result = forumTopicService.getSortedUniqueTopics();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("topic1", result.get(0));
        assertEquals("topic2", result.get(1));

        verify(forumRepository, times(1)).findAll();
    }

    @Test
    void getTopicFrequency() {
        Forum forum1 = new Forum();
        forum1.setTopic("topic1");

        Forum forum2 = new Forum();
        forum2.setTopic("topic2");

        Forum forum3 = new Forum();
        forum3.setTopic("topic1");

        List<Forum> forums = Arrays.asList(forum1, forum2, forum3);

        when(forumRepository.findAll()).thenReturn(forums);

        Map<String, Integer> result = forumTopicService.getTopicFrequency();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2, result.get("topic1").intValue());
        assertEquals(1, result.get("topic2").intValue());

        verify(forumRepository, times(1)).findAll();
    }
}