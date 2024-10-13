package com.autiwomen.auti_women.dtos.reviews;

import com.autiwomen.auti_women.security.dtos.user.UserDto;
import jakarta.validation.Valid;

@Valid
public class ReviewDto {

    public Long id;
    public String review;
    public UserDto user;

    public ReviewDto() {
    }

    public ReviewDto(Long id, String review, UserDto user) {
        this.id = id;
        this.review = review;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public UserDto getUserdto() {
        return user;
    }

    public void setUserdto(UserDto userdto) {
        this.user = userdto;
    }

    public void setUser(UserDto userDto) {

    }
}
