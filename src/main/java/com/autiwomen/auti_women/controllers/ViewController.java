package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.dtos.likes.LikeDto;
import com.autiwomen.auti_women.dtos.views.ViewDto;
import com.autiwomen.auti_women.services.ViewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/views")
public class ViewController {

    private final ViewService viewService;

    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping("/count/forums/{forumId}")
    public ResponseEntity<Integer> getViewCountByForumId(@PathVariable("forumId") Long forumId) {
        int viewCount = viewService.getViewCountByForumId(forumId);
        return ResponseEntity.ok().body(viewCount);
    }

    @GetMapping("/check/forums/{forumId}/users/{username}")
    public ResponseEntity<Boolean> checkUserView(@PathVariable Long forumId, @PathVariable String username) {
        boolean hasViewed = viewService.hasUserViewedPost(username, forumId);
        return ResponseEntity.ok().body(hasViewed);
    }

    @PostMapping("/add/forums/{forumId}/users/{username}")
    public ResponseEntity<ViewDto> addViewToForum(@PathVariable Long forumId, @PathVariable String username) {
        ViewDto viewDto = viewService.addViewToForum(forumId, username);
        return ResponseEntity.ok().body(viewDto);
    }
}
