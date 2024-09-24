package com.autiwomen.auti_women.dtos.likes;

import com.autiwomen.auti_women.dtos.forums.ForumDto;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import jakarta.validation.Valid;

@Valid
public class LikeDto {
    public Long id;


    public LikeDto(Long id) {
        this.id = id;
    }

    public LikeDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
