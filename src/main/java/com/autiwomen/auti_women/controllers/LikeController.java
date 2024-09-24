package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forums/{forumId}/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<Void> addLike(@PathVariable Long forumId) {
        likeService.addLike(forumId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeLike(@PathVariable Long forumId) {
        likeService.removeLike(forumId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getLikesByForumId(@PathVariable Long forumId) {
        Long likeCount = likeService.getLikesByForumId(forumId);
        return ResponseEntity.ok(likeCount);
    }
}