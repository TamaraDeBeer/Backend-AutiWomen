package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.repositories.ForumRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ForumTopicService {

    private final ForumRepository forumRepository;

    public ForumTopicService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public List<Forum> getForumsByTopic(String topic) {
        List<Forum> forums = forumRepository.findAll();
        List<Forum> forumsByTopic = new ArrayList<>();
        for (Forum forum : forums) {
            if (forum.getTopic().equals(topic)) {
                forumsByTopic.add(forum);
            }
        }
        if (forumsByTopic.isEmpty()) {
            throw new RecordNotFoundException("No forums found for this topic");
        }
        return forumsByTopic;
    }

    public Set<String> getUniqueTopics() {
        List<Forum> forums = forumRepository.findAll();
        Set<String> uniqueTopics = new HashSet<>();
        for (Forum forum : forums) {
            uniqueTopics.add(forum.getTopic());
        }
        return uniqueTopics;
    }

    public List<String> getSortedUniqueTopics() {
        List<Forum> forums = forumRepository.findAll();
        Map<String, Integer> topicFrequency = new HashMap<>();
        for (Forum forum : forums) {
            String topic = forum.getTopic();
            topicFrequency.put(topic, topicFrequency.getOrDefault(topic, 0) + 1);
        }
        return topicFrequency.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getTopicFrequency() {
        List<Forum> forums = forumRepository.findAll();
        Map<String, Integer> topicFrequency = new HashMap<>();
        for (Forum forum : forums) {
            String topic = forum.getTopic();
            if (topicFrequency.containsKey(topic)) {
                topicFrequency.put(topic, topicFrequency.get(topic) + 1);
            } else {
                topicFrequency.put(topic, 1);
            }
        }
        return topicFrequency.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), LinkedHashMap::putAll);
    }

}
