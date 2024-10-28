package com.autiwomen.auti_women.dtos.reviews;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInputDto {

    @NotEmpty
    @Size
    public String review;

}
