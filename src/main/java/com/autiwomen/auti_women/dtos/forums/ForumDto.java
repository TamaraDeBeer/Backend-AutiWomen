package com.autiwomen.auti_women.dtos.forums;

import com.autiwomen.auti_women.dtos.topics.TopicDto;
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
    public int likesCount;
    public int viewsCount;
    public int commentsCount;

    public UserDto userDto;
    public TopicDto topicDto;

    public ForumDto(String title, Long id, String name, String age, String text, String date, String lastReaction, int likesCount, int viewsCount, int commentsCount, UserDto userDto, TopicDto topicDto) {
        this.title = title;
        this.id = id;
        this.name = name;
        this.age = age;
        this.text = text;
        this.date = date;
        this.lastReaction = lastReaction;
        this.likesCount = likesCount;
        this.viewsCount = viewsCount;
        this.commentsCount = commentsCount;
        this.userDto = userDto;
        this.topicDto = topicDto;
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

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public TopicDto getTopicDto() {
        return topicDto;
    }

    public void setTopicDto(TopicDto topicDto) {
        this.topicDto = topicDto;
    }
}