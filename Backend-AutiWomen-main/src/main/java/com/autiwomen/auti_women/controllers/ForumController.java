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

    @PutMapping(value = "/forums/{id}/like")
    public ResponseEntity<ForumDto> likeForum(@PathVariable Long id) {
        ForumDto forumDto = forumService.likeForum(id);
        return ResponseEntity.ok().body(forumDto);
    }

    @PostMapping(value = "/forums/{username}")
    public ResponseEntity<ForumDto> createForum(@PathVariable("username") String username,@Valid @RequestBody ForumInputDto forumInputDto) {
        ForumDto forumDto = forumService.createForum(forumInputDto, username);
        forumService.assignForumsToUser(forumDto.getId(),username);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + forumDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(forumDto);
    }

    @PutMapping(value = "/forums/{id}")
    public ResponseEntity<ForumDto> updateForum(@PathVariable Long id, @RequestBody ForumDto updateForumDto) {
        ForumDto forumDto = forumService.updateForum(id, updateForumDto);
        if (forumDto.id == null) {
            throw new RecordNotFoundException("Er is geen forum met dit id nummer: " + id);
        } else {
            return ResponseEntity.ok().body(forumDto);
        }
    }

    @DeleteMapping(value = "/forums/{id}")
    public ResponseEntity<Forum> deleteForum(@PathVariable("id") Long id) {
        forumService.deleteForum(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{username}/forums")
    public ResponseEntity<Set<Forum>> getForumByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(forumService.getForumsByUsername(username));
    }

}
