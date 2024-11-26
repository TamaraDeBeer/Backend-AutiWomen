package com.autiwomen.auti_women.security.repositories;

import com.autiwomen.auti_women.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
}
