package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.profiles.ProfileDto;
import com.autiwomen.auti_women.dtos.profiles.ProfileInputDto;
import com.autiwomen.auti_women.models.Profile;
import com.autiwomen.auti_women.repositories.ProfileRepository;
import com.autiwomen.auti_women.security.UserRepository;
import com.autiwomen.auti_women.security.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    ProfileRepository profileRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ProfileService profileService;

    @Captor
    ArgumentCaptor<Profile> captor;
    User user1;
    User user2;
    Profile profile1;
    Profile profile2;

    @BeforeEach
    void setUp() {
        user1 = new User("user1");
        user2 = new User("user2");
        String currentDate = String.valueOf(LocalDate.now());
        profile1 = new Profile(1L, user1, "bio", "user1", currentDate);
        profile2 = new Profile(2L, user2, "bio", "user2", currentDate);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createProfile() {
        ProfileInputDto profileInputDto = new ProfileInputDto("bio", "user1", String.valueOf(LocalDate.now()));

        when(userRepository.findById("user1")).thenReturn(Optional.of(user1));
        when(profileRepository.save(any(Profile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        profileService.createProfile("user1", profileInputDto);
        verify(profileRepository, times(1)).save(captor.capture());
        Profile profileCreated = captor.getValue();

        assertEquals(profile1.getBio(), profileCreated.getBio());
        assertEquals(profile1.getName(), profileCreated.getName());
        assertEquals(profile1.getDate(), profileCreated.getDate());
    }

    @Test
    void getProfileByUsername() {
        when(userRepository.findById("user1")).thenReturn(Optional.of(user1));
        when(profileRepository.findByUser(user1)).thenReturn(Optional.of(profile1));

        ProfileDto profileFound = profileService.getProfileByUsername("user1");

        assertEquals(profile1.getBio(), profileFound.getBio());
        assertEquals(profile1.getName(), profileFound.getName());
        assertEquals(profile1.getDate(), profileFound.getDate());
    }

    @Test
    void updateProfile() {
        ProfileInputDto profileInputDto = new ProfileInputDto ("bio2", "user1", String.valueOf(LocalDate.now()));
        Profile profile = new Profile(1L, user1, "bio2", "user1", String.valueOf(LocalDate.now()));
        Profile profile3 = new Profile(1L, user1, "bio2", "user1", String.valueOf(LocalDate.now()));

        when(userRepository.findById("user1")).thenReturn(Optional.of(user1));
        when(profileRepository.findByUser(user1)).thenReturn(Optional.of(profile1));
        when(profileRepository.save(any())).thenReturn(profile3);

        profileService.updateProfile("user1", profileInputDto);
        verify(profileRepository, times(1)).save(captor.capture());
        Profile profileUpdated = captor.getValue();

        assertEquals(profile1.getBio(), profileUpdated.getBio());
        assertEquals(profile1.getName(), profileUpdated.getName());
        assertEquals(profile1.getDate(), profileUpdated.getDate());
    }

    @Test
    void fromProfile() {
        Profile profile = new Profile(1L, user1, "bio", "user1", String.valueOf(LocalDate.now()));

        ProfileDto profileDto = profileService.fromProfile(profile);

        assertEquals(profile.getId(), profileDto.id);
        assertEquals(profile.getName(), profileDto.name);
        assertEquals(profile.getDate(), profileDto.date);
        assertEquals(profile.getBio(), profileDto.bio);
    }

    @Test
    void toProfile() {
        ProfileInputDto profileInputDto = new ProfileInputDto("bio", "user1", String.valueOf(LocalDate.now()));

        Profile profile = profileService.toProfile(profileInputDto);

        assertEquals(profileInputDto.getBio(), profile.getBio());
        assertEquals(profileInputDto.getName(), profile.getName());
        assertEquals(profileInputDto.getDate(), profile.getDate());
    }

    @Test
    void getAllProfiles() {
        when(profileRepository.findAll()).thenReturn(List.of(profile1, profile2));

        List<ProfileDto> profilesFound = profileService.getAllProfiles();

        assertEquals(profile1.getBio(), profilesFound.get(0).getBio());
        assertEquals(profile2.getBio(), profilesFound.get(1).getBio());
    }
}