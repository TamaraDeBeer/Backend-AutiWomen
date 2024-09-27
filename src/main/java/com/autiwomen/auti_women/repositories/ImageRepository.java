package com.autiwomen.auti_women.repositories;


import com.autiwomen.auti_women.dtos.images.ImageOutputDto;
import com.autiwomen.auti_women.models.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<ImageOutputDto> findByFileName (String fileName);
}
