package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forums/{forumId}")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/users/{username}/likes/add")
    public void addLikeToForum(@PathVariable Long forumId, @PathVariable String username) {
        likeService.addLikeToForum(forumId, username);
    }

    @DeleteMapping("/users/{username}/likes/remove")
    public void removeLikeFromForum(@PathVariable Long forumId, @PathVariable String username) {
        likeService.removeLikeFromForum(forumId, username);
    }

    @GetMapping("likes/count")
    public ResponseEntity<Integer> getLikeCountByForumId(@PathVariable("forumId") Long forumId) {
        return ResponseEntity.ok(likeService.getLikeCountByForumId(forumId));
    }

    @GetMapping("users/{username}/likes/check")
    public ResponseEntity<Boolean> checkUserLike(@PathVariable Long forumId, @PathVariable String username) {
        boolean hasLiked = likeService.hasUserLikedPost(username, forumId);
        return ResponseEntity.ok(hasLiked);
    }
}