package com.autiwomen.auti_women.dtos.reviews;

import com.autiwomen.auti_women.security.dtos.user.UserDto;
import jakarta.validation.Valid;

import java.time.LocalDate;

@Valid
public class ReviewDto {

    public Long id;
    public String review;
    public UserDto userdto;
    public String name;
    public LocalDate dob;
    public Integer autismDiagnosesYear;
    public String profilePictureUrl;
    public String date;

    public ReviewDto() {
    }

    public ReviewDto(Long id, String review, UserDto userdto, String name, LocalDate dob, Integer autismDiagnosesYear, String profilePictureUrl, String date) {
        this.id = id;
        this.review = review;
        this.userdto = userdto;
        this.name = name;
        this.dob = dob;
        this.autismDiagnosesYear = autismDiagnosesYear;
        this.profilePictureUrl = profilePictureUrl;
        this.date = date;
    }

    public ReviewDto(Long id, String review, String name, LocalDate dob, Integer autismDiagnosesYear, String profilePictureUrl, String date) {
        this.id = id;
        this.review = review;
        this.name = name;
        this.dob = dob;
        this.autismDiagnosesYear = autismDiagnosesYear;
        this.profilePictureUrl = profilePictureUrl;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public UserDto getUserdto() {
        return userdto;
    }

    public void setUserdto(UserDto userdto) {
        this.userdto = userdto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
