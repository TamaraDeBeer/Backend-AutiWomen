package com.autiwomen.auti_women.dtos.forums;

import com.autiwomen.auti_women.security.dtos.user.UserDto;
import jakarta.validation.Valid;

@Valid
public class ForumDto {
    public Long id;
    public String name;
    public String age;
    public String title;
    public String text;
    public String date;
    public String lastReaction;

    public UserDto userDto;

    public ForumDto (Long id, String name, String age, String title, String text, String date, String lastReaction, UserDto userDto) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.title = title;
        this.text = text;
        this.date = date;
        this.lastReaction = lastReaction;
        this.userDto = userDto;
    }

    public ForumDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastReaction() {
        return lastReaction;
    }

    public void setLastReaction(String lastReaction) {
        this.lastReaction = lastReaction;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}