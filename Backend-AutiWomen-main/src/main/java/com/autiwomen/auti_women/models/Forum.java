package com.autiwomen.auti_women.models;

import com.autiwomen.auti_women.security.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "forums")
public class Forum {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String age;
    private String title;
    private String text;
    private String date;
    private String lastReaction;
    private Integer likes;
    private Integer comments;
    private Integer views;
    private String topic;

    @OneToMany(mappedBy = "forum", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> commentsList;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Forum(String text, Long id, String name, String age, String title, String date, String lastReaction, Integer likes, Integer comments, Integer views, String topic, List<Comment> commentsList, User user) {
        this.text = text;
        this.id = id;
        this.name = name;
        this.age = age;
        this.title = title;
        this.date = date;
        this.lastReaction = lastReaction;
        this.likes = likes;
        this.comments = comments;
        this.views = views;
        this.topic = topic;
        this.commentsList = commentsList;
        this.user = user;
    }

    public Forum() {
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}