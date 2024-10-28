package com.autiwomen.auti_women.dtos.forums;

import com.autiwomen.auti_women.security.dtos.user.UserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class ForumDto {
    public Long id;
    public String name;
    public String age;
    public String title;
    public String text;
    public String date;
    public String lastReaction;
    public String topic;
    public int likesCount;
    public int viewsCount;
    public int commentsCount;

    public UserDto userDto;

}