package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.comments.CommentDto;
import com.autiwomen.auti_women.dtos.comments.CommentInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Comment;
import com.autiwomen.auti_women.repositories.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
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
