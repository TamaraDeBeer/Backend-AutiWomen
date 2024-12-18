package com.autiwomen.auti_women.models;

import com.autiwomen.auti_women.security.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Column(length = 2000)
    private String text;

    private LocalDate date;
    private LocalDate dob;

    @ManyToOne
    @JoinColumn(name = "forum_id")
    private Forum forum;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "username")
    private User user;

    public Comment(String name, String text, LocalDate date, LocalDate dob, Forum forum, User user) {
        this.name = name;
        this.text = text;
        this.date = date;
        this.dob = dob;
        this.forum = forum;
        this.user = user;
    }

    public Comment(Long id,String name, String text, LocalDate date, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.date = date;
        this.dob = dob;
    }

    public Comment() {
    }

}
