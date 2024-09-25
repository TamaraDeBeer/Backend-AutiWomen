package com.autiwomen.auti_women.repositories;

import com.autiwomen.auti_women.models.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Forum, Long> {
}
