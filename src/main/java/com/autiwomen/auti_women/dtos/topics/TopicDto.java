package com.autiwomen.auti_women.dtos.topics;

import com.autiwomen.auti_women.dtos.forums.ForumDto;
import jakarta.validation.Valid;

@Valid
public class TopicDto {

    public Long id;
    public String topic;

    public ForumDto forumDto;

    public TopicDto(String topic, Long id, ForumDto forumDto) {
        this.topic = topic;
        this.id = id;
        this.forumDto = forumDto;
    }

    public TopicDto() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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
}
