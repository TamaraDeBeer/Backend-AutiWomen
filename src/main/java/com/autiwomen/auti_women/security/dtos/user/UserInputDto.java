package com.autiwomen.auti_women.security.dtos.user;

import com.autiwomen.auti_women.security.models.Authority;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
