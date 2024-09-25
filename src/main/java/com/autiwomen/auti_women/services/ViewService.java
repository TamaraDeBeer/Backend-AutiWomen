package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.models.View;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.repositories.ViewRepository;
import com.autiwomen.auti_women.security.UserRepository;
import com.autiwomen.auti_women.security.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ViewService {

    private final ViewRepository viewRepository;
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;

    public ViewService(ViewRepository viewRepository, ForumRepository forumRepository, UserRepository userRepository) {
        this.viewRepository = viewRepository;
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
    }

    public void addViewToForum(Long forumId, String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new RecordNotFoundException("Forum not found"));

        Optional<View> existingView = viewRepository.findViewByUserAndForum(user, forum);
        if (existingView.isPresent()) {
            return;
        }

        View view = new View(user, forum);
        viewRepository.save(view);
    }

    public int getViewCountByForumId(Long forumId) {
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new RecordNotFoundException("Forum not found"));
        Set<View> views = forum.getViews();
        int viewCount = views.size();
        return viewCount;
    }

}
