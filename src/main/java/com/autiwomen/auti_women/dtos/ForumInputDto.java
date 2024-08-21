package com.autiwomen.auti_women.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

public class ForumInputDto {
    @NotEmpty
    public String name;

    @NotEmpty
    @Max(value = 40)
    public String title;

    @NotEmpty
    @Max (value = 2000)
    public String text;

//Ik weer niet of topic hierbij moet of een aparte class moet hebben


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
