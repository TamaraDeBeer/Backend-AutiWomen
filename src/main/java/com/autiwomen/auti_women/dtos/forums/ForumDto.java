package com.autiwomen.auti_women.dtos.forums;

import com.autiwomen.auti_women.security.dtos.user.UserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class ForumDto {
    public Long id;
    public String name;
    public LocalDate dob;
    public String title;
    public String text;
    public LocalDate date;
    public LocalDate lastReaction;
    public String topic;
    public int likesCount;
    public int viewsCount;
    public int commentsCount;

    public UserDto userDto;

}