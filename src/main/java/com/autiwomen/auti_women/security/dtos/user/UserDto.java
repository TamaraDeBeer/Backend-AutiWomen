package com.autiwomen.auti_women.security.dtos.user;

import com.autiwomen.auti_women.dtos.images.ImageDto;
import com.autiwomen.auti_women.security.models.Authority;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.Set;

@Valid
public class UserDto {

    public String username;
    public String password;
    public boolean enabled;
    public String apikey;
    public String email;
    public String name;
    public String gender;
    public LocalDate dob;
    public String autismDiagnoses;
    public Integer autismDiagnosesYear;

    public ImageDto imageDto;

    @JsonSerialize
    public Set<Authority> authorities;

    public UserDto(String username, String password, boolean enabled, String apikey, String email, String name, String gender, LocalDate dob, String autismDiagnoses, Integer autismDiagnosesYear, ImageDto imageDto, Set<Authority> authorities) {
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
        this.imageDto = imageDto;
        this.authorities = authorities;
    }

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public boolean isEnabled() {
        return enabled;
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

    public ImageDto getImageDto() {
        return imageDto;
    }

    public void setImageDto(ImageDto imageDto) {
        this.imageDto = imageDto;
    }
}
