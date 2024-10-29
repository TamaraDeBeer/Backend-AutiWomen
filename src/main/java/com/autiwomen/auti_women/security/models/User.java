package com.autiwomen.auti_women.security.models;

import com.autiwomen.auti_women.models.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(nullable = false)
    private boolean enabled = true;
    private String apikey;
    @Column(nullable = false, unique = true)
    private String email;

    private String name;
    private String gender;
    private LocalDate dob;
    private String autismDiagnoses;
    private Integer autismDiagnosesYear;
    private String profilePictureUrl;

    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> commentsList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Forum> forums;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<View> views;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    public User(String username, String password, boolean enabled, String apikey, String email, String name, String gender, LocalDate dob, String autismDiagnoses, Integer autismDiagnosesYear, String profilePictureUrl, Set<Authority> authorities, List<Comment> commentsList, Set<Forum> forums, Set<Like> likes, Set<View> views, Profile profile) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.apikey = apikey;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.autismDiagnoses = autismDiagnoses;
        this.autismDiagnosesYear = autismDiagnosesYear;
        this.profilePictureUrl = profilePictureUrl;
        this.authorities = authorities;
        this.commentsList = commentsList;
        this.forums = forums;
        this.likes = likes;
        this.views = views;
        this.profile = profile;
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, LocalDate dob) {
        this.username = username;
        this.dob = dob;
    }

    public User() {
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }

}