package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.profiles.ProfileDto;
import com.autiwomen.auti_women.dtos.profiles.ProfileInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.repositories.ProfileRepository;
import com.autiwomen.auti_women.security.repositories.UserRepository;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.models.Profile;
import com.autiwomen.auti_women.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public ProfileService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    public List<ProfileDto> getAllProfiles() {
        return profileRepository.findAll().stream()
                .map(this::fromProfile)
                .collect(Collectors.toList());
    }

    public ProfileDto getProfileByUsername(String username) {
        if (!SecurityUtil.isOwnerOrAdmin(username)) {
            throw new SecurityException("Forbidden");
        }
        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found"));
        Profile profile = profileRepository.findByUser(user).orElseThrow(() -> new RecordNotFoundException("Profile not found"));
        return fromProfile(profile);
    }

    public ProfileDto createProfile(String username, ProfileInputDto profileInputDto) {
        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found"));

        Profile profile = toProfile(profileInputDto);
        profile.setName(user.getUsername());
        profile.setDate(LocalDate.parse(String.valueOf(LocalDate.now())));
        profile.setUser(user);
        profileRepository.save(profile);
        return fromProfile(profile);
    }

    public ProfileDto updateProfile(String username, ProfileInputDto profileInputDto) {
        if (!SecurityUtil.isOwnerOrAdmin(username)) {
            throw new SecurityException("Forbidden");
        }
        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found"));
        Profile profile = profileRepository.findByUser(user).orElseThrow(() -> new RecordNotFoundException("Profile not found"));

        profile.setBio(profileInputDto.getBio());
        profile.setDate(LocalDate.parse(String.valueOf(LocalDate.now())));
        profileRepository.save(profile);

        return fromProfile(profile);
    }


    public ProfileDto fromProfile(Profile profile) {
        var profileDto = new ProfileDto();
        profileDto.id = profile.getId();
        profileDto.name = profile.getName();
        profileDto.date = profile.getDate();
        profileDto.bio = profile.getBio();
        return profileDto;
    }

    public Profile toProfile(ProfileInputDto profileInputDto) {
        var profile = new Profile();
        profile.setName(profileInputDto.getName());
        profile.setDate(profileInputDto.getDate());
        profile.setBio(profileInputDto.getBio());
        return profile;
    }
}
