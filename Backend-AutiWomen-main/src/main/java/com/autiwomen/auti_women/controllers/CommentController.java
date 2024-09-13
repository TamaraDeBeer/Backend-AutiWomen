package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.dtos.comments.CommentDto;
import com.autiwomen.auti_women.dtos.comments.CommentInputDto;
import com.autiwomen.auti_women.models.Comment;
import com.autiwomen.auti_women.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forums")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{forumId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("forumId") Long forumId, @Valid @RequestBody CommentInputDto commentInputDto) {
        CommentDto commentDto = commentService.createComment(commentInputDto);
        commentService.assignCommentToForum(commentDto.getId(), forumId);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + commentDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(commentDto);
    }

    @GetMapping("/{forumId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByForumId(@PathVariable("forumId") Long forumId) {
        return ResponseEntity.ok(commentService.getCommentsByForumId(forumId));
    }

    @GetMapping("/{forumId}/comments/count")
    public ResponseEntity<Integer> getCommentCountByForumId(@PathVariable("forumId") Long forumId) {
        return ResponseEntity.ok(commentService.getCommentCountByForumId(forumId));
    }

}
