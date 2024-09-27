package com.autiwomen.auti_women.repositories;

import com.autiwomen.auti_women.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
