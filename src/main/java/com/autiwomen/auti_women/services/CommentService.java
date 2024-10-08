package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.comments.CommentDto;
import com.autiwomen.auti_women.dtos.comments.CommentInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Comment;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.repositories.CommentRepository;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.security.UserRepository;
import com.autiwomen.auti_women.security.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ForumRepository forumRepository;
    private final ForumService forumService;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ForumRepository forumRepository, ForumService forumService, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.forumRepository = forumRepository;
        this.forumService = forumService;
        this.userRepository = userRepository;
    }

    public CommentDto createComment(CommentInputDto commentInputDto, String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        Comment comment = toComment(commentInputDto);
        comment.setName(user.getUsername());
        comment.setAge(user.getDob().toString());
        comment.setDate(String.valueOf(LocalDate.now()));
        commentRepository.save(comment);
        return fromComment(comment);
    }

    public void assignCommentToForum(Long commentId, Long forumId) {
        var optionalComment = commentRepository.findById(commentId);
        var optionalForum = forumRepository.findById(forumId);

        if (optionalComment.isEmpty() || optionalForum.isEmpty()) {
            throw new RecordNotFoundException("Comment or Forum not found");
        } else {
            var comment = optionalComment.get();
            var forum = optionalForum.get();
            comment.setForum(forum);
            commentRepository.save(comment);
        }
    }

    public void assignCommentToUser(Long commentId, String username) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalComment.isEmpty() || optionalUser.isEmpty()) {
            throw new RecordNotFoundException("Forum or User not found");
        } else {
            Comment comment = optionalComment.get();
            User user = optionalUser.get();
            comment.setUser(user);
            commentRepository.save(comment);
        }
    }

    public List<Comment> getCommentsByForumId(Long forumId) {
        Forum forum = forumRepository.findById(forumId).orElseThrow(() -> new RecordNotFoundException("Forum not found"));
        return forum.getCommentsList();
    }

    public List<Comment> getCommentsByUsername(String username) {
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isEmpty()) {
            throw new RecordNotFoundException("User not found");
        }
        User user = optionalUser.get();
        return user.getCommentsList();
    }

    public int getCommentCountByForumId(Long forumId) {
        Forum forum = forumRepository.findById(forumId).orElseThrow(() -> new RecordNotFoundException("Forum not found"));
        List<Comment> comments = forum.getCommentsList();
        int commentCount = comments.size();
        return commentCount;
    }

    public void deleteComment(@RequestParam Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new RecordNotFoundException("Comment not found");
        }
        Comment comment = optionalComment.get();
        commentRepository.delete(comment);
    }

    public CommentDto updateComment (@PathVariable Long commentId, @RequestBody CommentDto updateComment) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new RecordNotFoundException("Comment not found");
        } else {
            Comment comment1 = comment.get();
            comment1.setText(updateComment.getText());
            comment1.setDate(String.valueOf(LocalDate.now()));
            Comment comment2 = commentRepository.save(comment1);

            return fromComment(comment2);
        }
    }

    public CommentDto fromComment(Comment comment) {
        var commentDto = new CommentDto();
        commentDto.id = comment.getId();
        commentDto.name = comment.getName();
        commentDto.text = comment.getText();
        commentDto.date = comment.getDate();

        if (comment.getForum() != null) {
            commentDto.setForumDto(forumService.fromForum(comment.getForum()));
        }
        return commentDto;
    }

    public Comment toComment(CommentInputDto commentInputDto) {
        var comment = new Comment();
        comment.setName(commentInputDto.getName());
        comment.setText(commentInputDto.getText());
        comment.setDate(commentInputDto.getDate());

        return comment;
    }

}
