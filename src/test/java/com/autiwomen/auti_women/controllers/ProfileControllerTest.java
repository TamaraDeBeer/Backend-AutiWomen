package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.dtos.profiles.ProfileDto;
import com.autiwomen.auti_women.dtos.profiles.ProfileInputDto;
import com.autiwomen.auti_women.models.Profile;
import com.autiwomen.auti_women.repositories.ProfileRepository;
import com.autiwomen.auti_women.security.UserRepository;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.services.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private ProfileService profileService;

    User user1;
    User user2;
    User user3;

    Profile profile1;
    Profile profile2;
    Profile profile3;

    ProfileDto profileDto1;
    ProfileDto profileDto2;
    ProfileDto profileDto3;

    ProfileInputDto profileInputDto1;
    ProfileInputDto profileInputDto2;
    ProfileInputDto profileInputDto3;

    @BeforeEach
    public void setUp() {
        String currentDate = String.valueOf(LocalDate.now());
        LocalDate dob = LocalDate.of(1990, 5, 15);
        user1 = new User("user1", dob);
        user2 = new User("user2", dob);
        user3 = new User("user3", dob);
        profile1 = new Profile(1L, user1, "bio1", "naam1", currentDate);
        profile2 = new Profile(2L, user2, "bio2", "naam2", currentDate);
        profile3 = new Profile(3L, user3, "bio3", "naam3", currentDate);

        profileRepository.save(profile1);
        profileRepository.save(profile2);
        profileRepository.save(profile3);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        profileDto1 = new ProfileDto(1L, "bio1", "naam1", currentDate);
        profileDto2 = new ProfileDto(2L, "bio2", "naam2", currentDate);
        profileDto3 = new ProfileDto(3L, "bio3", "naam3", currentDate);

        profileInputDto1 = new ProfileInputDto("bio1", "naam1", currentDate);
        profileInputDto2 = new ProfileInputDto("bio2", "naam2", currentDate);
        profileInputDto3 = new ProfileInputDto("bio3", "naam3", currentDate);
    }

    @Test
    void createProfile() {
    }

    @Test
    void getProfileByUsername() {
    }

    @Test
    void updateProfile() {
    }
}