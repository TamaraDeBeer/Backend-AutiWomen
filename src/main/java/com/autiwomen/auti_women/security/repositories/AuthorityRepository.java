package com.autiwomen.auti_women.security.repositories;

import com.autiwomen.auti_women.security.models.Authority;
import com.autiwomen.auti_women.security.models.AuthorityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityKey> {
}