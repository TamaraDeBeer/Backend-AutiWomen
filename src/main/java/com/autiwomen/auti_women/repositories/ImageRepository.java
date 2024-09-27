package com.autiwomen.auti_women.repositories;


import com.autiwomen.auti_women.models.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByFileName(String fileName);
}