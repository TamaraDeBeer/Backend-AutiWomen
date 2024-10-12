package com.autiwomen.auti_women.security.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private String email;
    private String name;
    private LocalDate dob;
    private String autismDiagnoses;
    private Integer autismDiagnosesYear;
    private String gender;
}
