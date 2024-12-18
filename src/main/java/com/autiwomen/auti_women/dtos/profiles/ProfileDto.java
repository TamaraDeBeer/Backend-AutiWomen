package com.autiwomen.auti_women.dtos.profiles;

import com.autiwomen.auti_women.security.dtos.user.UserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class ProfileDto {

    public Long id;
    public String bio;
    public LocalDate date;
    public String name;
    public UserDto userDto;

}
