package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.models.Profile;
import com.autiwomen.auti_women.repositories.ProfileRepository;
import com.autiwomen.auti_women.security.UserRepository;
import com.autiwomen.auti_women.security.dtos.user.UserInputDto;
import com.autiwomen.auti_women.security.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    private UserInputDto userInputDto1;
    private UserInputDto userInputDto2;
    private UserInputDto userInputDto3;

    @BeforeEach
    public void setUp() {
        LocalDate dob = LocalDate.of(1990, 5, 15);

        userInputDto1 = new UserInputDto("user1", "password1", "user1@example.com", "apikey1", true, "Name1", "Gender1", dob, "Diagnoses1", 2000, "profilePictureUrl1");
        userInputDto2 = new UserInputDto("user2", "password2", "user2@example.com", "apikey2", true, "Name2", "Gender2", dob, "Diagnoses2", 2001, "profilePictureUrl2");
        userInputDto3 = new UserInputDto("user3", "password3", "user3@example.com", "apikey3", true, "Name3", "Gender3", dob, "Diagnoses3", 2002, "profilePictureUrl3");

        User user1 = new User(userInputDto1.getUsername(), userInputDto1.getDob());
        user1.setEmail(userInputDto1.getEmail());
        user1.setPassword(userInputDto1.getPassword());
        user1.setName(userInputDto1.getName());
        user1.setGender(userInputDto1.getGender());
        user1.setAutismDiagnoses(userInputDto1.getAutismDiagnoses());

        User user2 = new User(userInputDto2.getUsername(), userInputDto2.getDob());
        user2.setEmail(userInputDto2.getEmail());
        user2.setPassword(userInputDto2.getPassword());
        user2.setName(userInputDto2.getName());
        user2.setGender(userInputDto2.getGender());
        user2.setAutismDiagnoses(userInputDto2.getAutismDiagnoses());

        User user3 = new User(userInputDto3.getUsername(), userInputDto3.getDob());
        user3.setEmail(userInputDto3.getEmail());
        user3.setPassword(userInputDto3.getPassword());
        user3.setName(userInputDto3.getName());
        user3.setGender(userInputDto3.getGender());
        user3.setAutismDiagnoses(userInputDto3.getAutismDiagnoses());

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Profile profile1 = new Profile(1L, user1, "bio1", "naam1", LocalDate.now().toString());
        Profile profile2 = new Profile(2L, user2, "bio2", "naam2", LocalDate.now().toString());
        Profile profile3 = new Profile(3L, user3, "bio3", "naam3", LocalDate.now().toString());

        profileRepository.save(profile1);
        profileRepository.save(profile2);
        profileRepository.save(profile3);
    }

    @Test
    public void testGetProfileByUsername() throws Exception {
        mockMvc.perform(get("/users/profiles/user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bio").value("bio1"))
                .andExpect(jsonPath("$.name").value("naam1"));
    }
}