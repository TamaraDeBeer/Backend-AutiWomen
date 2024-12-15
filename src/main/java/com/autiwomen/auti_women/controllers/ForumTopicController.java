package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.services.ForumTopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/topics")
public class ForumTopicController {

    private final ForumTopicService forumTopicService;

    public ForumTopicController(ForumTopicService forumTopicService) {
        this.forumTopicService = forumTopicService;
    }

    @GetMapping("/{topic}/forums")
    public ResponseEntity<List<Forum>> getForumsByTopic(@PathVariable String topic) {
        List<Forum> forumsByTopic = forumTopicService.getForumsByTopic(topic);
        return ResponseEntity.ok().body(forumsByTopic);
    }

    @GetMapping("/unique/forums")
    public ResponseEntity<Set<String>> getUniqueTopics() {
        Set<String> uniqueTopics = forumTopicService.getUniqueTopics();
        return ResponseEntity.ok().body(uniqueTopics);
    }

    @GetMapping("/sorted/forums")
    public ResponseEntity<List<String>> getSortedUniqueTopics() {
        List<String> sortedUniqueTopics = forumTopicService.getSortedUniqueTopics();
        return ResponseEntity.ok().body(sortedUniqueTopics);
    }

    @GetMapping("/frequency")
    public ResponseEntity<Map<String, Integer>> getTopicFrequency() {
        return ResponseEntity.ok().body(forumTopicService.getTopicFrequency());
    }

}
