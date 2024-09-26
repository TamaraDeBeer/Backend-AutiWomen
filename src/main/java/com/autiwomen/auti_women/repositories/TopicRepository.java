package com.autiwomen.auti_women.repositories;

import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicRepository extends JpaRepository<Forum, Long> {
    @Query("SELECT f FROM Forum f JOIN f.forumTopic t WHERE f.forumTopic = :topicName")
    List<Forum> findForumsByTopicName(@Param("topicName") String topicName);


}
