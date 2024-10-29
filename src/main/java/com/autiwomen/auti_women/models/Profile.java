package com.autiwomen.auti_women.models;

import com.autiwomen.auti_women.security.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @Column(length = 2000)
    private String bio;

    private String name;
    private String date;

    public Profile() {
    }

    public Profile(Long id, User user, String bio, String name, String date) {
        this.id = id;
        this.user = user;
        this.bio = bio;
        this.name = name;
        this.date = date;
    }

}
