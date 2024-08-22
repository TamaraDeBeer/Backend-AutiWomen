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

    public String age;
    public String date;
    public String lastReaction;
    public Integer likes;
    public Integer comments;
    public Integer views;




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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastReaction() {
        return lastReaction;
    }

    public void setLastReaction(String lastReaction) {
        this.lastReaction = lastReaction;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
