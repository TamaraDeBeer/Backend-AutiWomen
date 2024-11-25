package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.dtos.profiles.ProfileDto;
import com.autiwomen.auti_women.dtos.profiles.ProfileInputDto;
import com.autiwomen.auti_women.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<List<ProfileDto>> getAllProfiles() {
        List<ProfileDto> profiles = profileService.getAllProfiles();
        return ResponseEntity.ok().body(profiles);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<ProfileDto> getProfileByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(profileService.getProfileByUsername(username));
    }

    @PostMapping("/users/{username}")
    public ResponseEntity<ProfileDto> createProfile(@PathVariable("username") String username, @RequestBody ProfileInputDto profileInputDto) {
        ProfileDto profileDto = profileService.createProfile(username, profileInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + profileDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(profileDto);
    }

    @PutMapping("/users/{username}")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable("username") String username, @RequestBody ProfileInputDto profileInputDto) {
        return ResponseEntity.ok().body(profileService.updateProfile(username, profileInputDto));
    }

}
