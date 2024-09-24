package com.autiwomen.auti_women.repositories;

import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.models.Like;
import com.autiwomen.auti_women.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndForum(User user, Forum forum);
    //    void deleteByUserAndForum(User user, Forum forum);

//    @Query("SELECT COUNT(l) FROM Like l WHERE l.forum.id = :forumId")
//    Long countByForumId(@Param("forumId") Long forumId);
//
//    @Query("SELECT l FROM Like l WHERE l.forum.id = :forumId AND l.user.username = :username")
//    Like findByForumIdAndUsername(@Param("forumId") Long forumId, @Param("username") String username);
}
