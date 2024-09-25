package com.autiwomen.auti_women.models;

import jakarta.persistence.*;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    @OneToOne(mappedBy = "topic")
    private Forum forum;

    public Topic() {
    }

    public Topic(String topic) {
        this.topic = topic;
    }

    public Topic(Forum forum, String topic, Long id) {
        this.forum = forum;
        this.topic = topic;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }
}
