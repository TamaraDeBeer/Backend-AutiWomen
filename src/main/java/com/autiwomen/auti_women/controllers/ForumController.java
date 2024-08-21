package com.autiwomen.auti_women.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class ForumController {

//    @GetMapping(value = "/forums")
//    public ResponseEntity<String> getAllForums() {
//        return ResponseEntity.ok('AllForums');
//    }

    @GetMapping(value = "/forums/{id}")
    public String getForumById() {
        return "Forum with id";
    }

    @PostMapping(value = "/forums")
    public String createForum() {
        return "Forum created";
    }


}
