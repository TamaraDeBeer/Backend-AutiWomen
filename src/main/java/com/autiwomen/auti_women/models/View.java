package com.autiwomen.auti_women.models;

import com.autiwomen.auti_women.security.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "views")
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "forum_id", nullable = false)
    private Forum forum;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String username;
    private String forumTitle;

    public View() {
    }

    public View(User user, Forum forum) {
        this.user = user;
        this.forum = forum;
    }

}
