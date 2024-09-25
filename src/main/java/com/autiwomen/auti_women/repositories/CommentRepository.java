package com.autiwomen.auti_women.repositories;

import com.autiwomen.auti_women.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByForumId(Long forumId);
    List<Comment> findByName(String username);
}
