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

    @NotEmpty
    public String apikey;

    public boolean enabled;

    @NotEmpty
    public String name;

    @NotEmpty
    public String gender;

    @NotEmpty
    public LocalDate dob;

    @NotEmpty
    public String autismDiagnoses;

    @Digits(integer = 4, fraction = 0)
    @NotEmpty
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

    public UserInputDto() {
    }

    public @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty String username) {
        this.username = username;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }

    public @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty String email) {
        this.email = email;
    }

    public @NotEmpty String getApikey() {
        return apikey;
    }

    public void setApikey(@NotEmpty String apikey) {
        this.apikey = apikey;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public @NotEmpty String getName() {
        return name;
    }

    public void setName(@NotEmpty String name) {
        this.name = name;
    }

    public @NotEmpty String getGender() {
        return gender;
    }

    public void setGender(@NotEmpty String gender) {
        this.gender = gender;
    }

    public @NotEmpty LocalDate getDob() {
        return dob;
    }

    public void setDob(@NotEmpty LocalDate dob) {
        this.dob = dob;
    }

    public @NotEmpty String getAutismDiagnoses() {
        return autismDiagnoses;
    }

    public void setAutismDiagnoses(@NotEmpty String autismDiagnoses) {
        this.autismDiagnoses = autismDiagnoses;
    }

    public @Digits(integer = 4, fraction = 0) @NotEmpty @NotNull Integer getAutismDiagnosesYear() {
        return autismDiagnosesYear;
    }

    public void setAutismDiagnosesYear(@Digits(integer = 4, fraction = 0) @NotEmpty @NotNull Integer autismDiagnosesYear) {
        this.autismDiagnosesYear = autismDiagnosesYear;
    }
}
