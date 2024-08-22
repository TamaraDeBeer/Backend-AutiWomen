package com.autiwomen.auti_women.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ForumInputDto {
    @NotEmpty
    public String name;

    @NotEmpty
    @Size(min = 1, max = 50)
    public String title;

    @NotEmpty
    @Size(min = 1, max = 4000)
//  VARCHAR default is 250 max dus nog aan te passen met code: ALTER TABLE forum ALTER COLUMN text TYPE VARCHAR(10000)
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
