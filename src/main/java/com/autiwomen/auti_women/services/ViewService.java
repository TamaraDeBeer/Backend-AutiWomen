package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.views.ViewDto;
import com.autiwomen.auti_women.dtos.views.ViewInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.models.View;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.repositories.ViewRepository;
import com.autiwomen.auti_women.security.repositories.UserRepository;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.security.utils.SecurityUtil;
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

    public int getViewCountByForumId(Long forumId) {
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new RecordNotFoundException("Forum not found"));
        Set<View> views = forum.getViews();
        return views.size();
    }

    public boolean hasUserViewedPost(String username, Long forumId) {
        if (!SecurityUtil.isOwnerOrAdmin(username)) {
            throw new SecurityException("Forbidden");
        }
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new RecordNotFoundException("Forum not found"));

        return viewRepository.findViewByUserAndForum(user, forum).isPresent();
    }

    public ViewDto addViewToForum(Long forumId, String username) {
        if (!SecurityUtil.isOwnerOrAdmin(username)) {
            throw new SecurityException("Forbidden");
        }
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new RecordNotFoundException("Forum not found"));

        Optional<View> existingView = viewRepository.findViewByUserAndForum(user, forum);
        if (existingView.isPresent()) {
            throw new IllegalStateException("User has already viewed this forum");
        }
        ViewInputDto viewInputDto = new ViewInputDto();
        viewInputDto.setId(null);
        View view = toView(viewInputDto);
        view.setUser(user);
        view.setForum(forum);
        view.setUsername(username);
        view.setForumTitle(forum.getTitle());
        viewRepository.save(view);
        return fromView(view);
    }

    public ViewDto fromView(View view) {
        var viewDto = new ViewDto();
        viewDto.id = view.getId();
        viewDto.username = view.getUsername();
        viewDto.forumTitle = view.getForumTitle();
        return viewDto;
    }

    public View toView (ViewInputDto viewInputDto) {
        var view = new View();
        view.setId(viewInputDto.getId());
        return view;
    }
}