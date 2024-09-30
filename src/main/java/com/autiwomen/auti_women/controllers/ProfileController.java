package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.dtos.profiles.ProfileDto;
import com.autiwomen.auti_women.dtos.profiles.ProfileInputDto;
import com.autiwomen.auti_women.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<ProfileDto> createProfile(@PathVariable("username") String username, @RequestBody ProfileInputDto profileInputDto) {
        ProfileDto profileDto = profileService.createProfile(username, profileInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + profileDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(profileDto);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileDto> getProfileByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(profileService.getProfileByUsername(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable("username") String username, @RequestBody ProfileInputDto profileInputDto) {
        return ResponseEntity.ok(profileService.updateProfile(username, profileInputDto));
    }

//    @DeleteMapping("/{username}")
//    public ResponseEntity<Void> deleteProfile(@PathVariable("username") String username) {
//        profileService.deleteProfile(username);
//        return ResponseEntity.noContent().build();
//    }
}