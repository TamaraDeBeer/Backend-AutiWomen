package com.autiwomen.auti_women.models;

import com.autiwomen.auti_women.security.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
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
    private String text;
    private String date;
    private String lastReaction;

//    @ManyToOne (fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
////    @JsonBackReference
//    @JsonIgnore
//    private User user;

    @OneToMany(
            mappedBy = "forum",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
//    @JsonManagedReference
    @JsonIgnore
    private List<Comment> commentsList = new ArrayList<>();

//    @OneToMany(
//            mappedBy = "forum",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.EAGER)
////    @JsonManagedReference
//    @JsonIgnore
//    private Set<Like> likes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "username")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "forum")
    private Set<Like> likes;

    public Forum(String text, Long id, String name, String age, String title, String date, String lastReaction, List<Comment> commentsList, User user, Set<Like> likes) {
        this.text = text;
        this.id = id;
        this.name = name;
        this.age = age;
        this.title = title;
        this.date = date;
        this.lastReaction = lastReaction;
        this.commentsList = commentsList;
        this.user = user;
        this.likes = likes;
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
}