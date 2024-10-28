package com.autiwomen.auti_women.repositories;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    Set<Forum> findByUser (User user);

    List<Forum> findAllByTitleContainingIgnoreCase(String title);

}
