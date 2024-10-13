package com.autiwomen.auti_women.dtos.reviews;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ReviewInputDto {

    @NotEmpty
    @Size
    public String review;

    public ReviewInputDto(String review) {
        this.review = review;
    }

    public @NotEmpty @Size String getReview() {
        return review;
    }

    public void setReview(@NotEmpty @Size String review) {
        this.review = review;
    }
}
