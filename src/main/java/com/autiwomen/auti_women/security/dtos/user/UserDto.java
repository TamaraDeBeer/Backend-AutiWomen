package com.autiwomen.auti_women.security.dtos.user;

import com.autiwomen.auti_women.dtos.profiles.ProfileDto;
import com.autiwomen.auti_women.security.models.Authority;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class UserDto {

    public String username;
    public String password;
    private String oldPassword;
    public boolean enabled;
    public String apikey;
    public String email;
    public String name;
    public String gender;
    public LocalDate dob;
    public String autismDiagnoses;
    public Integer autismDiagnosesYear;
    public String profilePictureUrl;
    public MultipartFile photo;
    public ProfileDto profileDto;

    public String oldAuthority;
    public String newAuthority;

    @JsonSerialize
    public Set<Authority> authorities;

    public UserDto(String username, String profilePictureUrl) {
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
    }

    public UserDto(String username, LocalDate dob, Integer autismDiagnosesYear, String profilePictureUrl) {
        this.username = username;
        this.dob = dob;
        this.autismDiagnosesYear = autismDiagnosesYear;
        this.profilePictureUrl = profilePictureUrl;

    }


}
