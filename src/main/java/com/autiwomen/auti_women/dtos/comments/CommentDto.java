package com.autiwomen.auti_women.dtos.comments;

import com.autiwomen.auti_women.dtos.forums.ForumDto;
import jakarta.validation.Valid;

@Valid
public class CommentDto {

    public Long id;
    public String name;
    public String text;
    public String date;
    public ForumDto forumDto;

    public CommentDto(Long id, String name, String text, String date, ForumDto forumDto) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.date = date;
        this.forumDto = forumDto;
    }

    public CommentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ForumDto getForumDto() {
        return forumDto;
    }

    public void setForumDto(ForumDto forumDto) {
        this.forumDto = forumDto;
    }
}
