package com.autiwomen.auti_women.models;

import com.autiwomen.auti_women.security.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
////    @JsonBackReference
//    @JsonIgnore
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "forum_id")
////    @JsonBackReference
//    @JsonIgnore
//    private Forum forum;

    @ManyToOne
    @JoinColumn(name = "forum_id", nullable = false)
    private Forum forum;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Like() {
    }

    public Like(Forum forum, Long id, User user) {
        this.forum = forum;
        this.id = id;
        this.user = user;
    }

    public Like(User user, Forum forum) {
        this.user = user;
        this.forum = forum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

}
