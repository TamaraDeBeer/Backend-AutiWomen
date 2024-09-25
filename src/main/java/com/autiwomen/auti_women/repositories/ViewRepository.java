package com.autiwomen.auti_women.repositories;

import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.models.Like;
import com.autiwomen.auti_women.models.View;
import com.autiwomen.auti_women.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ViewRepository extends JpaRepository<View, Long> {
    Optional<View> findViewByUserAndForum(User user, Forum forum);

    @Query("SELECT COUNT(l) FROM View l WHERE l.forum.id = :forumId")
    int getViewCountByForumId(@Param("forumId") Long forumId);

    @Query("SELECT l.forum FROM View l WHERE l.user = :user")
    Set<Forum> findViewedForumsByUser(@Param("user") User user);


}
