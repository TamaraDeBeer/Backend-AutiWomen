package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.AutiWomenApplication;
import com.autiwomen.auti_women.dtos.profiles.ProfileInputDto;
import com.autiwomen.auti_women.models.Profile;
import com.autiwomen.auti_women.repositories.ProfileRepository;
import com.autiwomen.auti_women.security.UserRepository;
import com.autiwomen.auti_women.security.dtos.user.UserInputDto;
import com.autiwomen.auti_women.security.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AutiWomenApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProfileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    private UserInputDto userInputDto;
    private ProfileInputDto profileInputDto;


    @BeforeEach
    public void setUp() {
        LocalDate dob = LocalDate.of(1990, 5, 15);

        userInputDto = new UserInputDto("user1", "password1", "user1@example.com", "apikey1", true, "Name1", "Gender1", dob, "Diagnoses1", 2000, "profilePictureUrl1");

        User user1 = new User(userInputDto.getUsername(), userInputDto.getDob());
        user1.setEmail(userInputDto.getEmail());
        user1.setPassword(userInputDto.getPassword());
        user1.setName(userInputDto.getName());
        user1.setGender(userInputDto.getGender());
        user1.setAutismDiagnoses(userInputDto.getAutismDiagnoses());
        userRepository.save(user1);

        Profile profile1 = new Profile(1L, user1, "bio1", "naam1", LocalDate.now().toString());
        profileRepository.save(profile1);

        profileInputDto = new ProfileInputDto();
        profileInputDto.setBio("bio1");
        profileInputDto.setName("naam1");

    }

    @Test
    public void testGetProfileByUsername() throws Exception {
        mockMvc.perform(get("/users/profiles/user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bio").value("bio1"))
                .andExpect(jsonPath("$.name").value("naam1"));
    }

    @Test
    public void testCreateProfile() throws Exception {
        User user2 = new User("user2", LocalDate.of(1990, 5, 15));
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");
        user2.setName("User2");
        user2.setGender("Gender2");
        user2.setAutismDiagnoses("Diagnoses2");
        userRepository.save(user2);

        profileInputDto.setBio("bio2");
        profileInputDto.setName("user2");

        mockMvc.perform(post("/users/profiles/user2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(profileInputDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.bio").value("bio2"))
                .andExpect(jsonPath("$.name").value("user2"));
    }

    @Test
    public void testUpdateProfile() throws Exception {
        profileInputDto.setBio("updatedBio");

        mockMvc.perform(put("/users/profiles/user1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(profileInputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bio").value("updatedBio"))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()));
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}