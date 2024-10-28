package com.autiwomen.auti_women.dtos.reviews;

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
public class ReviewDto {

    public Long id;
    public String review;
    public UserDto userdto;
    public String name;
    public LocalDate dob;
    public Integer autismDiagnosesYear;
    public String profilePictureUrl;
    public String date;

}
