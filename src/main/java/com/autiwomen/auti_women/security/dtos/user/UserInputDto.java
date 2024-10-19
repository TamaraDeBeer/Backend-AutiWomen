package com.autiwomen.auti_women.security.dtos.user;

import com.autiwomen.auti_women.security.models.Authority;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Year;
import java.util.Set;

public class UserInputDto {

    @NotEmpty
    public String username;

    @NotEmpty
    public String password;

    @NotEmpty
    @Email
    public String email;

    public String apikey;

    public boolean enabled;

    @NotEmpty
    public String name;

    @NotEmpty
    public String gender;

    @NotNull
    public LocalDate dob;

    @NotEmpty
    public String autismDiagnoses;

    @Digits(integer = 4, fraction = 0)
    public Integer autismDiagnosesYear;

    public MultipartFile photo;
    public String profilePictureUrl;

    private Set<Authority> authorities;

    public UserInputDto(String username, String password, String email, String apikey, boolean enabled, String name, String gender, LocalDate dob, String autismDiagnoses, Integer autismDiagnosesYear, MultipartFile photo, String profilePictureUrl, Set<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.apikey = apikey;
        this.enabled = enabled;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.autismDiagnoses = autismDiagnoses;
        this.autismDiagnosesYear = autismDiagnosesYear;
        this.photo = photo;
        this.profilePictureUrl = profilePictureUrl;
        this.authorities = authorities;
    }

    public UserInputDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

        public UserInputDto(String gender, String username, String password, String email, String apikey, boolean enabled, String name, LocalDate dob, String autismDiagnoses, Integer autismDiagnosesYear, MultipartFile photo, String profilePictureUrl, Set<Authority> authorities) {
            this.gender = gender;
            this.username = username;
            this.password = password;
            this.email = email;
            this.apikey = apikey;
            this.enabled = enabled;
            this.name = name;
            this.dob = dob;
            this.autismDiagnoses = autismDiagnoses;
            this.autismDiagnosesYear = autismDiagnosesYear;
            this.photo = photo;
            this.profilePictureUrl = profilePictureUrl;
            this.authorities = authorities;
        }

    public UserInputDto() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
