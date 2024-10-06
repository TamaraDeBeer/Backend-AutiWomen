package com.autiwomen.auti_women.security.dtos.user;

import com.autiwomen.auti_women.dtos.profiles.ProfileDto;
import com.autiwomen.auti_women.security.models.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Set<Authority> authorities;
}
