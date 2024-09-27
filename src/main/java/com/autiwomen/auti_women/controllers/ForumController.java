package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.dtos.forums.ForumDto;
import com.autiwomen.auti_women.dtos.forums.ForumInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.services.ForumService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
public class ForumController {

    private final ForumService forumService;

    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping(value = "/forums")
    public ResponseEntity<List<ForumDto>> getAllForums() {
        return ResponseEntity.ok(forumService.getAllForums());
    }

    @GetMapping(value = "/forums/{id}")
    public ResponseEntity<ForumDto> getOneForum(@PathVariable("id") Long id) {
        ForumDto forumDto = forumService.getForumById(id);
        return ResponseEntity.ok().body(forumDto);
    }

    @PostMapping(value = "/forums/{username}")
    public ResponseEntity<ForumDto> createForum(@PathVariable("username") String username,@Valid @RequestBody ForumInputDto forumInputDto) {
        ForumDto forumDto = forumService.createForum(forumInputDto, username);
        forumService.assignForumToUser(forumDto.getId(),username);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + forumDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(forumDto);
    }

    @PutMapping(value = "/forums/{id}")
    public ResponseEntity<ForumDto> updateForum(@PathVariable Long id, @RequestBody ForumDto updateForumDto) {
        ForumDto forumDto = forumService.updateForum(id, updateForumDto);
        return ResponseEntity.ok().body(forumDto);
    }

    @DeleteMapping(value = "/forums/{id}")
    public ResponseEntity<Forum> deleteForum(@PathVariable("id") Long id) {
        forumService.deleteForum(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{username}/forums")
    public ResponseEntity<Set<Forum>> getForumsByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(forumService.getForumsByUsername(username));
    }

    @GetMapping("/users/{username}/liked-forums")
    public ResponseEntity<Set<ForumDto>> getLikedForumsByUsername(@PathVariable("username") String username) {
        Set<ForumDto> likedForums = forumService.getLikedForumsByUsername(username);
        return ResponseEntity.ok(likedForums);
    }

    @GetMapping("/users/{username}/viewed-forums")
    public ResponseEntity<Set<ForumDto>> getViewedForumsByUsername(@PathVariable("username") String username) {
        Set<ForumDto> viewedForums = forumService.getViewedForumsByUsername(username);
        return ResponseEntity.ok(viewedForums);
    }

    @GetMapping("/users/{username}/commented-forums")
    public ResponseEntity<Set<ForumDto>> getCommentedForumsByUsername(@PathVariable("username") String username) {
        Set<ForumDto> commentedForums = forumService.getCommentedForumsByUsername(username);
        return ResponseEntity.ok(commentedForums);
    }

    @GetMapping("/forums/topic/{topic}")
    public ResponseEntity<List<Forum>> getForumsByTopic(@PathVariable String topic) {
        List<Forum> forumsByTopic = forumService.getForumsByTopic(topic);
        return ResponseEntity.ok(forumsByTopic);
    }

    @GetMapping("/forums/unique-topics")
    public ResponseEntity<Set<String>> getUniqueTopics() {
        Set<String> uniqueTopics = forumService.getUniqueTopics();
        return ResponseEntity.ok(uniqueTopics);
    }

    @GetMapping("/forums/sorted-unique-topics")
    public ResponseEntity<List<String>> getSortedUniqueTopics() {
        List<String> sortedUniqueTopics = forumService.getSortedUniqueTopics();
        return ResponseEntity.ok(sortedUniqueTopics);
    }

    @GetMapping("/topics/frequency")
    public ResponseEntity<Map<String, Integer>> getTopicFrequency() {
        return ResponseEntity.ok(forumService.getTopicFrequency());
    }

}
