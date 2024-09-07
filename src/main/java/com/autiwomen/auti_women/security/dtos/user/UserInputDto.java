package com.autiwomen.auti_women.security.dtos.user;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.Year;

public class UserInputDto {
//    public static final int MAX_YEAR = Year.now().getValue() - 18;

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
    @NotNull
//    @Max(value = MAX_YEAR)
    public Integer autismDiagnosesYear;

    public UserInputDto(String username, String password, String email, String name, String gender, LocalDate dob, String autismDiagnoses, Integer autismDiagnosesYear) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.autismDiagnoses = autismDiagnoses;
        this.autismDiagnosesYear = autismDiagnosesYear;
    }

    public UserInputDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserInputDto(String username, String password, String email, String apikey, boolean enabled, String name, String gender, LocalDate dob, String autismDiagnoses, Integer autismDiagnosesYear) {
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
}
