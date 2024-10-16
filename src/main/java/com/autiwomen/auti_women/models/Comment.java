package com.autiwomen.auti_women.models;


import com.autiwomen.auti_women.security.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Column(length = 2000)
    private String text;
    private String date;
    private String age;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "forum_id")
//    @JsonIgnore
    private Forum forum;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "username")
//    @JsonIgnore
    private User user;

    public Comment(String name, String text, String date, String age, Forum forum, User user) {
        this.name = name;
        this.text = text;
        this.date = date;
        this.age = age;
        this.forum = forum;
        this.user = user;
    }

    public Comment(Long id, String name, String text, String date, String age) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.date = date;
        this.age = age;
    }

    public Comment() {
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

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
