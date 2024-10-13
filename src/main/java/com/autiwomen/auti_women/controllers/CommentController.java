package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.dtos.comments.CommentDto;
import com.autiwomen.auti_women.dtos.comments.CommentInputDto;
import com.autiwomen.auti_women.models.Comment;
import com.autiwomen.auti_women.services.CommentService;
import com.autiwomen.auti_women.services.ForumService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/forums")
public class CommentController {

    private final CommentService commentService;
    private final ForumService forumService;

    public CommentController(CommentService commentService, ForumService forumService) {
        this.commentService = commentService;
        this.forumService = forumService;
    }

    @PostMapping("/{forumId}/comments/{username}")
    public ResponseEntity<CommentDto> createComment(@PathVariable("forumId") Long forumId, @PathVariable("username") String username, @Valid @RequestBody CommentInputDto commentInputDto) {
        CommentDto commentDto = commentService.createComment(commentInputDto, username);
        commentService.assignCommentToForum(commentDto.getId(), forumId);
        commentService.assignCommentToUser(commentDto.getId(), username);
        forumService.updateLastReaction(forumId);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + commentDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(commentDto);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{forumId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByForumId(@PathVariable("forumId") Long forumId) {
        List<CommentDto> comments = commentService.getCommentsByForumId(forumId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/users/{username}/comments")
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(commentService.getCommentsByUsername(username));
    }

    @GetMapping("/{forumId}/comments/count")
    public ResponseEntity<Integer> getCommentCountByForumId(@PathVariable("forumId") Long forumId) {
        return ResponseEntity.ok(commentService.getCommentCountByForumId(forumId));
    }

    @PutMapping("/{forumId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("forumId") Long forumId, @PathVariable("commentId") Long commentId, @RequestBody CommentDto updateCommentDto) {
        CommentDto commentDto = commentService.updateComment(commentId, updateCommentDto);
        forumService.updateLastReaction(forumId);
        return ResponseEntity.ok().body(commentDto);
    }

    @DeleteMapping("/{forumId}/comments/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable("forumId") Long forumId, @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

}
