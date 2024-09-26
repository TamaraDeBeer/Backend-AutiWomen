package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.models.Topic;
import com.autiwomen.auti_women.repositories.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Forum> getTopics() {
        return topicRepository.findAll();
    }



}