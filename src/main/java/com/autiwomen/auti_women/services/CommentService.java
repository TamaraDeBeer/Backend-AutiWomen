package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.dtos.comments.CommentDto;
import com.autiwomen.auti_women.dtos.comments.CommentInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Comment;
import com.autiwomen.auti_women.repositories.CommentRepository;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.repositories.UserRepository;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByForumId(Long forumId) {
        List<Comment> comments = commentRepository.findByForumId(forumId);
        if (comments.isEmpty()) {
            throw new RecordNotFoundException("No comments found for forum ID: " + forumId);
        }
        return comments.stream().map(comment -> {
            User user = comment.getUser();
            if (user != null) {
                comment.setName(user.getUsername());
                comment.setAge(user.getDob().toString());
            }
            return toCommentDto(comment);
        }).collect(Collectors.toList());
    }

    public List<Comment> getCommentsByUsername(String username) {
        if (!SecurityUtil.isOwnerOrAdmin(username)) {
            throw new SecurityException("Forbidden");
        }
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
        return comments.size();
    }

    public CommentDto createComment(CommentInputDto commentInputDto, String username) {
        if (!SecurityUtil.isOwnerOrAdmin(username)) {
            throw new SecurityException("Forbidden");
        }
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        Comment comment = toComment(commentInputDto);
        comment.setName(user.getUsername());
        comment.setAge(user.getDob().toString());
        comment.setDate(String.valueOf(LocalDate.now()));
        commentRepository.save(comment);
        return fromComment(comment);
    }

    public CommentDto updateComment (Long commentId, CommentDto updateComment, String username) {
        if (!SecurityUtil.isOwnerOrAdmin(username)) {
            throw new SecurityException("Forbidden");
        }
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

    @Transactional
    public void deleteComment(Long commentId, String username) {
        if (!SecurityUtil.isOwnerOrAdmin(username)) {
            throw new SecurityException("Forbidden");
        }
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new RecordNotFoundException("Comment not found");
        }
        Comment comment = optionalComment.get();
        comment.setForum(null);
        comment.setUser(null);
        commentRepository.save(comment);
        commentRepository.delete(comment);
    }


// Helpers
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

    public CommentDto fromComment(Comment comment) {
        var commentDto = new CommentDto();
        commentDto.id = comment.getId();
        commentDto.name = comment.getName();
        commentDto.text = comment.getText();
        commentDto.date = comment.getDate();

        if (comment.getForum() != null) {
            commentDto.setForumDto(forumService.fromForum(comment.getForum()));

            User user = comment.getUser();
            if (user != null) {
                commentDto.userDto = new UserDto(user.getUsername(), user.getProfilePictureUrl());
            } else {
                commentDto.userDto = null;
            }
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

    private CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setText(comment.getText());
        dto.setDate(comment.getDate());
        dto.setAge(comment.getAge());

        if (comment.getUser() != null) {
            User user = comment.getUser();
            UserDto userDto = new UserDto(user.getUsername(), user.getProfilePictureUrl());
            dto.setUserDto(userDto);
        }
        if (comment.getForum() != null) {
            dto.setForumDto(forumService.fromForum(comment.getForum()));
        }

        return dto;
    }

}
