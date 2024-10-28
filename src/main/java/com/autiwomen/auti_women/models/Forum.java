package com.autiwomen.auti_women.models;

import com.autiwomen.auti_women.security.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "forums")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}