package com.autiwomen.auti_women.repositories;

import com.autiwomen.auti_women.models.Review;
import com.autiwomen.auti_women.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM Review r WHERE r.user = :user")
    void deleteByUser(User user);
}
