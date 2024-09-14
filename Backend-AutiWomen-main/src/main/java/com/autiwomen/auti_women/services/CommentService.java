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
import com.autiwomen.auti_women.security.services.UserService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ForumRepository forumRepository;
    private final ForumService forumService;
    private final UserRepository userRepository;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, ForumRepository forumRepository, ForumService forumService, UserRepository userRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.forumRepository = forumRepository;
        this.forumService = forumService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public CommentDto createComment(CommentInputDto commentInputDto, String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        Comment comment = toComment(commentInputDto);
        comment.setName(user.getUsername());
        comment.setAge(user.getDob().toString());
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

    public void assignCommentsToUser(Long commentId, String username) {
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

    public Set<Comment> getCommentsbyUsername(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found"));
        return user.getComments();
    }


    public int getCommentCountByForumId(Long forumId) {
        Forum forum = forumRepository.findById(forumId).orElseThrow(() -> new RecordNotFoundException("Forum not found"));
        List<Comment> comments = forum.getCommentsList();
        int commentCount = comments.size();
        return commentCount;
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
