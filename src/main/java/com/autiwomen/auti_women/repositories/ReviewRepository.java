package com.autiwomen.auti_women.repositories;

import com.autiwomen.auti_women.models.Profile;
import com.autiwomen.auti_women.models.Review;
import com.autiwomen.auti_women.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUser(User user);
}
