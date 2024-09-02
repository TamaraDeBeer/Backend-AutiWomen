package com.autiwomen.auti_women.dtos.comments;

import com.autiwomen.auti_women.dtos.forums.ForumDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CommentInputDto {

    @NotEmpty
    public String name;

    @NotEmpty
    @Size(min = 1, max = 1000)
    public String text;
    public String date;

    public CommentInputDto(String name, String text, String date) {
        this.name = name;
        this.text = text;
        this.date = date;

    }

    public CommentInputDto() {
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

}
