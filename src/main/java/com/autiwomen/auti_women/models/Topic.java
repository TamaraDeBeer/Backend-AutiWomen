package com.autiwomen.auti_women.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    @OneToMany(mappedBy = "topic")
    private List<Forum> forums;

    public Topic() {
    }

    public Topic(String topic) {
        this.topic = topic;
    }

    public Topic(Long id, String topic, List<Forum> forums) {
        this.id = id;
        this.topic = topic;
        this.forums = forums;
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

    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }
}
