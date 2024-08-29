package com.autiwomen.auti_women.models;


import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String text;
    private String date;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Forum forum;

    public Comment(Long id, String name, String text, String date) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.date = date;
    }

    public Comment(Long id, String name, String text, String date, Forum forum) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.date = date;
        this.forum = forum;
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
}
