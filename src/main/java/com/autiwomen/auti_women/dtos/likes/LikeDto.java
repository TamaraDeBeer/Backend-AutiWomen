package com.autiwomen.auti_women.dtos.likes;

import com.autiwomen.auti_women.dtos.forums.ForumDto;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import jakarta.validation.Valid;

@Valid
public class LikeDto {
    public Long id;
    public UserDto userDto;
    public ForumDto forumDto;

    public LikeDto(Long id, UserDto userDto, ForumDto forumDto) {
        this.id = id;
        this.userDto = userDto;
        this.forumDto = forumDto;
    }

    public LikeDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public ForumDto getForumDto() {
        return forumDto;
    }

    public void setForumDto(ForumDto forumDto) {
        this.forumDto = forumDto;
    }
}
