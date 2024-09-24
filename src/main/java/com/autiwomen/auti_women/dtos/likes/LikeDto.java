package com.autiwomen.auti_women.dtos.likes;

import com.autiwomen.auti_women.dtos.forums.ForumDto;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import jakarta.validation.Valid;

@Valid
public class LikeDto {
    public Long id;
    public ForumDto forumDto;
    public UserDto userDto;

    public LikeDto(Long id, ForumDto forumDto, UserDto userDto) {
        this.id = id;
        this.forumDto = forumDto;
        this.userDto = userDto;
    }

    public LikeDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ForumDto getForumDto() {
        return forumDto;
    }

    public void setForumDto(ForumDto forumDto) {
        this.forumDto = forumDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
