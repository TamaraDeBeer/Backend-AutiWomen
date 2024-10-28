package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.services.ForumTopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
public class ForumTopicController {

    private final ForumTopicService forumTopicService;

    public ForumTopicController(ForumTopicService forumTopicService) {
        this.forumTopicService = forumTopicService;
    }

    @GetMapping("/forums/topic/{topic}")
    public ResponseEntity<List<Forum>> getForumsByTopic(@PathVariable String topic) {
        List<Forum> forumsByTopic = forumTopicService.getForumsByTopic(topic);
        return ResponseEntity.ok(forumsByTopic);
    }

    @GetMapping("/forums/unique-topics")
    public ResponseEntity<Set<String>> getUniqueTopics() {
        Set<String> uniqueTopics = forumTopicService.getUniqueTopics();
        return ResponseEntity.ok(uniqueTopics);
    }

    @GetMapping("/forums/sorted-unique-topics")
    public ResponseEntity<List<String>> getSortedUniqueTopics() {
        List<String> sortedUniqueTopics = forumTopicService.getSortedUniqueTopics();
        return ResponseEntity.ok(sortedUniqueTopics);
    }

    @GetMapping("/topics/frequency")
    public ResponseEntity<Map<String, Integer>> getTopicFrequency() {
        return ResponseEntity.ok(forumTopicService.getTopicFrequency());
    }

}
