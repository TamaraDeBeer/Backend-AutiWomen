package com.autiwomen.auti_women.dtos.views;

import com.autiwomen.auti_women.dtos.forums.ForumDto;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class ViewDto {
    public Long id;
    public ForumDto forumDto;
    public UserDto userDto;

}
