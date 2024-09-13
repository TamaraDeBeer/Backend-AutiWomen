package com.autiwomen.auti_women.dtos.forums;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ForumInputDto {

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
    public String topic;

    public ForumInputDto(String name, String title, String text, String age, String date, String lastReaction, Integer likes, Integer comments, Integer views, String topic) {
        this.name = name;
        this.title = title;
        this.text = text;
        this.age = age;
        this.date = date;
        this.lastReaction = lastReaction;
        this.likes = likes;
        this.comments = comments;
        this.views = views;
        this.topic = topic;
    }

    public ForumInputDto(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public ForumInputDto() {
    }

    public String getLastReaction() {
        return lastReaction;
    }

    public void setLastReaction(String lastReaction) {
        this.lastReaction = lastReaction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @NotEmpty @Size(min = 1, max = 50) String getTitle() {
        return title;
    }

    public void setTitle(@NotEmpty @Size(min = 1, max = 50) String title) {
        this.title = title;
    }

    public @NotEmpty @Size(min = 1, max = 4000) String getText() {
        return text;
    }

    public void setText(@NotEmpty @Size(min = 1, max = 4000) String text) {
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
