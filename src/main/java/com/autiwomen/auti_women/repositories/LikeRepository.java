package com.autiwomen.auti_women.repositories;

import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.models.Like;
import com.autiwomen.auti_women.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteByUserAndForum(User user, Forum forum);
}
