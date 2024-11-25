package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/count/forums/{forumId}")
    public ResponseEntity<Integer> getLikeCountByForumId(@PathVariable("forumId") Long forumId) {
        int likeCount = likeService.getLikeCountByForumId(forumId);
        return ResponseEntity.ok().body(likeCount);
    }

    @GetMapping("/check/forums/{forumId}/users/{username}")
    public ResponseEntity<Boolean> checkUserLike(@PathVariable Long forumId, @PathVariable String username) {
        boolean hasLiked = likeService.hasUserLikedPost(username, forumId);
        return ResponseEntity.ok().body(hasLiked);
    }

    @PostMapping("/add/forums/{forumId}/users/{username}")
    public ResponseEntity<Void> addLikeToForum(@PathVariable Long forumId, @PathVariable String username) {
        likeService.addLikeToForum(forumId, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/forums/{forumId}/users/{username}")
    public ResponseEntity<Void> removeLikeFromForum(@PathVariable Long forumId, @PathVariable String username) {
        likeService.removeLikeFromForum(forumId, username);
        return ResponseEntity.ok().build();
    }
}