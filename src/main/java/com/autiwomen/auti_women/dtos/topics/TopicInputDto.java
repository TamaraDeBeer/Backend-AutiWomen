package com.autiwomen.auti_women.dtos.topics;

import jakarta.validation.constraints.NotEmpty;

public class TopicInputDto {

    @NotEmpty
    public String topic;

    public TopicInputDto(String topic) {
        this.topic = topic;
    }

    public TopicInputDto() {
    }

    public @NotEmpty String getTopic() {
        return topic;
    }

    public void setTopic(@NotEmpty String topic) {
        this.topic = topic;
    }
}
