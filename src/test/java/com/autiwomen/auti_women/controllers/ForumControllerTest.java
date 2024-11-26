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

        Forum forum1 = new Forum(1L, "Name1", "05-05-1990", "title", "text", "06-07-2024", null, "topic", 0, 0, 0);
        forumRepository.save(forum1);

        Forum forum2 = new Forum(2L, "Name1", "05-05-1990", "title2", "text2", "06-07-2024", null, "topic", 0, 0, 0);
        forumRepository.save(forum2);
    }

    @Test
    public void testGetAllForums() throws Exception {
        mockMvc.perform(get("/forums"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[12].name").value("Name1"))
                .andExpect(jsonPath("$[12].age").value("05-05-1990"))
                .andExpect(jsonPath("$[12].title").value("title"))
                .andExpect(jsonPath("$[12].text").value("text"))
                .andExpect(jsonPath("$[12].date").value("06-07-2024"))
                .andExpect(jsonPath("$[12].lastReaction").value(org.hamcrest.Matchers.nullValue()))
                .andExpect(jsonPath("$[12].topic").value("topic"))
                .andExpect(jsonPath("$[12].likesCount").value(0))
                .andExpect(jsonPath("$[12].viewsCount").value(0))
                .andExpect(jsonPath("$[12].commentsCount").value(0))
                .andExpect(jsonPath("$[13].name").value("Name1"))
                .andExpect(jsonPath("$[13].age").value("05-05-1990"))
                .andExpect(jsonPath("$[13].title").value("title2"))
                .andExpect(jsonPath("$[13].text").value("text2"))
                .andExpect(jsonPath("$[13].date").value("06-07-2024"))
                .andExpect(jsonPath("$[13].lastReaction").value(org.hamcrest.Matchers.nullValue()))
                .andExpect(jsonPath("$[13].topic").value("topic"))
                .andExpect(jsonPath("$[13].likesCount").value(0))
                .andExpect(jsonPath("$[13].viewsCount").value(0))
                .andExpect(jsonPath("$[13].commentsCount").value(0));
    }

    @Test
    public void testGetOneForum() throws Exception {
        mockMvc.perform(get("/forums/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.age").value("05-05-1990"))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.text").value("text"))
                .andExpect(jsonPath("$.date").value("06-07-2024"))
                .andExpect(jsonPath("$.lastReaction").value(org.hamcrest.Matchers.nullValue()))
                .andExpect(jsonPath("$.topic").value("topic"))
                .andExpect(jsonPath("$.likesCount").value(0))
                .andExpect(jsonPath("$.viewsCount").value(0))
                .andExpect(jsonPath("$.commentsCount").value(0));
    }
}