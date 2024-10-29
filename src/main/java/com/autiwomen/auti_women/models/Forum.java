package com.autiwomen.auti_women.models;

import com.autiwomen.auti_women.security.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
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

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentsList = new ArrayList<>();

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "username")
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


}