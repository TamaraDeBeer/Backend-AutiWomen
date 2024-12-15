package com.autiwomen.auti_women.repositories;

import com.autiwomen.auti_women.models.Comment;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findTopByForumIdOrderByDateDesc(Long forumId);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.forum.id = :forumId")
    int getCommentCountByForumId(@Param("forumId") Long forumId);

    @Query("SELECT c.forum FROM Comment c WHERE c.user = :user")
    Set<Forum> findCommentedForumsByUser(@Param("user") User user);

    void deleteAllByForumId(Long forumId);

    List<Comment> findByForumId(Long forumId);

}
