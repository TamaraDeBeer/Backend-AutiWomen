package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.forums.ForumDto;
import com.autiwomen.auti_women.dtos.forums.ForumInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.security.UserRepository;
import com.autiwomen.auti_women.security.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.time.LocalDateTime;

@Service
public class ForumService {

    private final ForumRepository forumRepository;
    private final UserRepository userRepository;

    public ForumService(ForumRepository forumRepository, UserRepository userRepository) {
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
    }

    public List<ForumDto> getAllForums() {
        List<Forum> forumList = forumRepository.findAll();
        List<ForumDto> forumDtoList = new ArrayList<>();

        for (Forum forum : forumList) {
            ForumDto forumDto = fromForum(forum);
            forumDtoList.add(forumDto);
        }
        return forumDtoList;
    }

    public ForumDto createForum(ForumInputDto forumInputDto, String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        Forum forum = toForum(forumInputDto);
        forum.setDate(String.valueOf(LocalDateTime.now()));
        forum.setName(user.getUsername());
        forum.setAge(user.getDob().toString());

        forumRepository.save(forum);
        return fromForum(forum);
    }

    public void assignForumToUser(Long forumId, String username) {
        Optional<Forum> optionalForum = forumRepository.findById(forumId);
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalForum.isEmpty() || optionalUser.isEmpty()) {
            throw new RecordNotFoundException("Forum or User not found");
        } else {
            Forum forum = optionalForum.get();
            User user = optionalUser.get();
            forum.setUser(user);
            forumRepository.save(forum);
        }
    }

    public ForumDto getForumById(Long id) {
        Optional<Forum> forumId = forumRepository.findById(id);
        if (forumId.isPresent()) {
            Forum forum = forumId.get();
            return fromForum(forum);
        } else {
            throw new RecordNotFoundException("Er is geen forum gevonden met id: " + id);
        }
    }

    public ForumDto updateForum(@PathVariable Long id, @RequestBody ForumDto updateForum) {
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isEmpty()) {
            throw new RecordNotFoundException("Er is geen forum gevonden met id: " + id);
        } else {
            Forum forum1 = forum.get();
            forum1.setName(updateForum.getName());
            forum1.setTitle(updateForum.getTitle());
            forum1.setText(updateForum.getText());
            Forum forum2 = forumRepository.save(forum1);

            return fromForum(forum2);
        }
    }

    public void deleteForum(@RequestParam Long id) {
        forumRepository.deleteById(id);
    }

    public Set<Forum> getForumsByUsername(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found"));
        return new HashSet<>(forumRepository.findByUser(user));
    }

    public ForumDto fromForum(Forum forum) {
        var forumDto = new ForumDto();
        forumDto.id = forum.getId();
        forumDto.name = forum.getName();
        forumDto.age = forum.getAge();
        forumDto.title = forum.getTitle();
        forumDto.text = forum.getText();
        forumDto.date = forum.getDate();
        forumDto.lastReaction = forum.getLastReaction();

        return forumDto;
    }

    public Forum toForum(ForumInputDto forumInputDto) {
        var forum = new Forum();
        forum.setName(forumInputDto.name);
        forum.setTitle(forumInputDto.title);
        forum.setText(forumInputDto.text);
        forum.setDate(forumInputDto.date);
        forum.setLastReaction(forumInputDto.lastReaction);

        return forum;
    }

}
