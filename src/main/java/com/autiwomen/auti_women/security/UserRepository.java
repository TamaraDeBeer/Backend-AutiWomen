package com.autiwomen.auti_women.security;

import com.autiwomen.auti_women.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    User findByUsername(String username);
    List<User>findAllUsers(String username);
}
