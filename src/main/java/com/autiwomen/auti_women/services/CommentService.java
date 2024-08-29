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

    public CommentDto createComment(CommentInputDto commentInputDto, Long id) {
        var optionalForum = forumRepository.findById(id);
        if (optionalForum.isPresent()) {
            var forum = optionalForum.get();
            Comment comment = toComment(commentInputDto);
            commentRepository.save(comment);
            forum.setComments(forum.getComments());
            forumRepository.save(forum);
            return fromComment(comment);
        } else {
            throw new RecordNotFoundException("Er is geen forum gevonden met id: " + commentInputDto.getForumDto().getId());
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
