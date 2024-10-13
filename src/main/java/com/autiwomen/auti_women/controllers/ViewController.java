package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.services.ViewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forums/{forumId}")
public class ViewController {

    private final ViewService viewService;

    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    @PostMapping("/users/{username}/views/add")
    public void addViewToForum(@PathVariable Long forumId, @PathVariable String username) {
        viewService.addViewToForum(forumId, username);
    }

    @GetMapping("views/count")
    public ResponseEntity<Integer> getViewCountByForumId(@PathVariable("forumId") Long forumId) {
        return ResponseEntity.ok(viewService.getViewCountByForumId(forumId));
    }

    @GetMapping("/users/{username}/views/check")
    public ResponseEntity<Boolean> checkUserView(@PathVariable Long forumId, @PathVariable String username) {
        boolean hasViewed = viewService.hasUserViewedPost(username, forumId);
        return ResponseEntity.ok(hasViewed);
    }
}
