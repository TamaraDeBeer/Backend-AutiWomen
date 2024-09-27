package com.autiwomen.auti_women.security.dtos.user;

import com.autiwomen.auti_women.dtos.profiles.ProfileDto;

import java.time.LocalDate;

public class UserOutputDto {

    private String username;
    private String email;
    private String name;
    private String gender;
    private LocalDate dob;
    private String autismDiagnoses;
    private Integer autismDiagnosesYear;
    private String profilePictureUrl;

    private ProfileDto profileDto;

//    moet authority ook hier als output dto? Heb ik deze in de Front nodig?
    // en forums?

    public UserOutputDto() {
    }

    public UserOutputDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserOutputDto(ProfileDto profileDto, String username, String email, String name, String gender, LocalDate dob, String autismDiagnoses, Integer autismDiagnosesYear, String profilePictureUrl) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.autismDiagnoses = autismDiagnoses;
        this.autismDiagnosesYear = autismDiagnosesYear;
        this.profilePictureUrl = profilePictureUrl;
        this.profileDto = profileDto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAutismDiagnoses() {
        return autismDiagnoses;
    }

    public void setAutismDiagnoses(String autismDiagnoses) {
        this.autismDiagnoses = autismDiagnoses;
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

    public ProfileDto getProfileDto() {
        return profileDto;
    }

    public void setProfileDto(ProfileDto profileDto) {
        this.profileDto = profileDto;
    }

}
