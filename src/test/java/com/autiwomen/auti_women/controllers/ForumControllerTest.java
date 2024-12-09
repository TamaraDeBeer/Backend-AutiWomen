package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.AutiWomenApplication;
import com.autiwomen.auti_women.dtos.forums.ForumInputDto;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.security.dtos.user.UserInputDto;
import com.autiwomen.auti_women.security.models.Authority;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.security.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AutiWomenApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ForumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private UserRepository userRepository;

    private UserInputDto userInputDto;
    private ForumInputDto forumInputDto;

    @BeforeEach
    void setUp() {
        LocalDate dob = LocalDate.of(1990, 5, 5);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority("ROLE_USER"));

        userInputDto = new UserInputDto(
                "user1",
                "password1",
                "user1@example.com",
                "apikey1",
                true,
                "Name1",
                "vrouw",
                dob,
                "Diagnoses1",
                2000,
                null, // No MultipartFile, so set to null
                "http://localhost:1991/images/DefaultProfileImage.png",
                authorities
        );

        User user1 = new User(userInputDto.getUsername(), userInputDto.getDob());
        user1.setGender(userInputDto.getGender());
        user1.setPassword(userInputDto.getPassword());
        user1.setEmail(userInputDto.getEmail());
        user1.setApikey(userInputDto.getApikey());
        user1.setEnabled(userInputDto.isEnabled());
        user1.setName(userInputDto.getName());
        user1.setAutismDiagnoses(userInputDto.getAutismDiagnoses());
        user1.setAutismDiagnosesYear(userInputDto.getAutismDiagnosesYear());
        user1.setProfilePictureUrl(userInputDto.getProfilePictureUrl());
        userRepository.save(user1);

        Forum forum1 = new Forum(1L, "Name1", dob, "title", "text", LocalDate.of(2024, 7, 6), "topic", 0, 0, 0);
        forumRepository.save(forum1);

        Forum forum2 = new Forum(2L, "Name1", dob, "title2", "text2", LocalDate.of(2024, 7, 6), "topic", 0, 0, 0);
        forumRepository.save(forum2);
    }

    @Test
    public void testGetAllForums() throws Exception {
        mockMvc.perform(get("/forums"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Name1"))
                .andExpect(jsonPath("$[0].dob").value("1990-05-05"))
                .andExpect(jsonPath("$[0].title").value("title"))
                .andExpect(jsonPath("$[0].text").value("text"))
                .andExpect(jsonPath("$[0].date").value("2024-07-06"))
                .andExpect(jsonPath("$[0].topic").value("topic"))
                .andExpect(jsonPath("$[0].likesCount").value(0))
                .andExpect(jsonPath("$[0].viewsCount").value(0))
                .andExpect(jsonPath("$[0].commentsCount").value(0))
                .andExpect(jsonPath("$[1].name").value("Name1"))
                .andExpect(jsonPath("$[1].dob").value("1990-05-05"))
                .andExpect(jsonPath("$[1].title").value("title2"))
                .andExpect(jsonPath("$[1].text").value("text2"))
                .andExpect(jsonPath("$[1].date").value("2024-07-06"))
                .andExpect(jsonPath("$[1].topic").value("topic"))
                .andExpect(jsonPath("$[1].likesCount").value(0))
                .andExpect(jsonPath("$[1].viewsCount").value(0))
                .andExpect(jsonPath("$[1].commentsCount").value(0));
    }

    @Test
    public void testGetOneForum() throws Exception {
        mockMvc.perform(get("/forums/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.dob").value("1990-05-05"))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.text").value("text"))
                .andExpect(jsonPath("$.date").value("2024-07-06"))
                .andExpect(jsonPath("$.topic").value("topic"))
                .andExpect(jsonPath("$.likesCount").value(0))
                .andExpect(jsonPath("$.viewsCount").value(0))
                .andExpect(jsonPath("$.commentsCount").value(0));
    }
}