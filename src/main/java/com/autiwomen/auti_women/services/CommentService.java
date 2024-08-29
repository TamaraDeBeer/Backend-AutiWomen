package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.comments.CommentDto;
import com.autiwomen.auti_women.dtos.comments.CommentInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Comment;
import com.autiwomen.auti_women.repositories.CommentRepository;
import com.autiwomen.auti_women.repositories.ForumRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ForumRepository forumRepository;
    private final ForumService forumService;


    public CommentService(CommentRepository commentRepository, ForumRepository forumRepository, ForumService forumService) {
        this.commentRepository = commentRepository;
        this.forumRepository = forumRepository;
        this.forumService = forumService;
    }

    public CommentDto createComment(CommentInputDto commentInputDto) {
        Comment comment = toComment(commentInputDto);
        commentRepository.save(comment);
        return fromComment(comment);
    }


    public CommentDto fromComment(Comment comment) {
        var commentDto = new CommentDto();
        commentDto.id = comment.getId();
        commentDto.name = comment.getName();
        commentDto.text = comment.getText();
        commentDto.date = comment.getDate();

        if(comment.getForum() != null) {
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

    public void assignForumToComment(Long commentId, Long forumId) {
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



}
