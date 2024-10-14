package com.autiwomen.auti_women.security.models;

import com.autiwomen.auti_women.models.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

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
//    @JsonManagedReference
    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

//    @OneToMany(
//            mappedBy = "user",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.EAGER)
////    @JsonManagedReference
//    @JsonIgnore
//    private Set<Forum> forums = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
//    @JsonManagedReference
    @JsonIgnore
    private List<Comment> commentsList = new ArrayList<>();

//    @OneToMany(
//            mappedBy = "user",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.EAGER)
////    @JsonManagedReference
//    @JsonIgnore
//    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Forum> forums;

    @OneToMany(mappedBy = "user")
    private Set<Like> likes;

    @OneToMany(mappedBy = "user")
    private Set<View> views;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Review review;

    public User(String username, String password, boolean enabled, String apikey, String email, String name, String gender, LocalDate dob, String autismDiagnoses, Integer autismDiagnosesYear, String profilePictureUrl, Set<Authority> authorities, List<Comment> commentsList, Set<Forum> forums, Set<Like> likes, Set<View> views, Profile profile, Review review) {
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
        this.review = review;
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

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAutismDiagnoses() {
        return autismDiagnoses;
    }

    public void setAutismDiagnoses(String autismDiagnoses) {
        this.autismDiagnoses = autismDiagnoses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAutismDiagnosesYear() {
        return autismDiagnosesYear;
    }

    public void setAutismDiagnosesYear(Integer autismDiagnosesYear) {
        this.autismDiagnosesYear = autismDiagnosesYear;
    }

    public Set<Forum> getForums() {
        return forums;
    }

    public void setForums(Set<Forum> forums) {
        this.forums = forums;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<View> getViews() {
        return views;
    }

    public void setViews(Set<View> views) {
        this.views = views;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}