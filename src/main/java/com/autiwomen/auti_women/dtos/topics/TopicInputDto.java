package com.autiwomen.auti_women.dtos.topics;

public class TopicInputDto {

    public String topic;

    public TopicInputDto(String topic) {
        this.topic = topic;
    }

    public TopicInputDto() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
