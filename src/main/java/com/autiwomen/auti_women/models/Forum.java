package com.autiwomen.auti_women.models;

import com.autiwomen.auti_women.security.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "forums")
public class Forum {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String age;
    private String title;

    @Column(length = 4000)
    private String text;

    private String date;
    private String lastReaction;
    private String topic;
    private int likesCount;
    private int viewsCount;
    private int commentsCount;

//    @OneToMany(
//            mappedBy = "forum",
//            fetch = FetchType.EAGER,
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    @JsonIgnore
//    private List<Comment> commentsList = new ArrayList<>();

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentsList = new ArrayList<>();

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "username")
//    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "forum",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private Set<Like> likes;

    @OneToMany(mappedBy = "forum",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<View> views;

    public Forum(Long id, String name, String age, String title, String text, String date, String lastReaction, String topic, int likesCount, int viewsCount, int commentsCount, List<Comment> commentsList, User user, Set<Like> likes, Set<View> views) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.title = title;
        this.text = text;
        this.date = date;
        this.lastReaction = lastReaction;
        this.topic = topic;
        this.likesCount = likesCount;
        this.viewsCount = viewsCount;
        this.commentsCount = commentsCount;
        this.commentsList = commentsList;
        this.user = user;
        this.likes = likes;
        this.views = views;
    }

    public Forum(Long id, String name, String age, String title, String text, String date, String topic) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.title = title;
        this.text = text;
        this.date = date;
        this.topic = topic;
    }

    public Forum(Long id, String name, String age, String title, String text, String date, String lastReaction, String topic, int likesCount, int viewsCount, int commentsCount) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.title = title;
        this.text = text;
        this.date = date;
        this.lastReaction = lastReaction;
        this.topic = topic;
        this.likesCount = likesCount;
        this.viewsCount = viewsCount;
        this.commentsCount = commentsCount;
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

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
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

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<View> getViews() {
        return views;
    }

    public void setViews(Set<View> views) {
        this.views = views;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


}