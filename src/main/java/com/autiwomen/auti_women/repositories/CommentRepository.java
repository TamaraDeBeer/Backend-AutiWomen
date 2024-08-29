package com.autiwomen.auti_women.repositories;

import com.autiwomen.auti_women.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
