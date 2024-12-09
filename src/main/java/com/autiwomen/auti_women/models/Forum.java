package com.autiwomen.auti_women.models;

import com.autiwomen.auti_women.security.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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

    private LocalDate date;
    private LocalDate lastReaction;
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

    public Forum(String name, String age, String title, String text, LocalDate date, String topic, int likesCount, int viewsCount, int commentsCount, User user) {
        this.name = name;
        this.age = age;
        this.title = title;
        this.text = text;
        this.date = date;
        this.topic = topic;
        this.likesCount = likesCount;
        this.viewsCount = viewsCount;
        this.commentsCount = commentsCount;
        this.user = user;
    }

    public Forum(Long id, String name, String age, String title, String text, LocalDate date, String topic) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.title = title;
        this.text = text;
        this.date = date;
        this.topic = topic;
    }



    public Forum() {
    }


}