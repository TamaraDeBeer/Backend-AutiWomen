package com.autiwomen.auti_women.dtos.profiles;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInputDto {

    @NotEmpty
    @Size(min = 1, max = 2000)
    public String bio;

    public String name;
    public String date;

}
