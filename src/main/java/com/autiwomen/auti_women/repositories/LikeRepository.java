package com.autiwomen.auti_women.repositories;

import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.models.Like;
import com.autiwomen.auti_women.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findLikeByUserAndForum(User user, Forum forum);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.forum.id = :forumId")
    int getLikeCountByForumId(@Param("forumId") Long forumId);

    @Query("SELECT l.forum FROM Like l WHERE l.user = :user")
    Set<Forum> findLikedForumsByUser(@Param("user") User user);

}
