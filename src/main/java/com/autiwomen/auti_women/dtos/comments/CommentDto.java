package com.autiwomen.auti_women.dtos.comments;

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
public class CommentDto {

    public Long id;
    public String name;
    public String text;
    public String date;
    public String age;

    public ForumDto forumDto;
    public UserDto userDto;

}
