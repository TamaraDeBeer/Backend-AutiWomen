package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.ForumDto;
import com.autiwomen.auti_women.dtos.ForumInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.repositories.ForumRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ForumService {

    private final ForumRepository forumRepository;

    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
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

    public ForumDto createForum(ForumInputDto forumInputDto) {
        Forum forum = toForum(forumInputDto);
        forumRepository.save(forum);
        return fromForum(forum);
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

    public void deleteForum(@RequestParam Long id) {
        forumRepository.deleteById(id);
    }

    public ForumDto updateForum(Long id, ForumDto updateForum) {
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isEmpty()) {
            throw new RecordNotFoundException("Er is geen forum gevonden met id: " + id);
        } else {
            Forum forum1 = forum.get();
            forum1.setTitle(updateForum.getTitle());
            forum1.setText(updateForum.getText());
            Forum forum2 = forumRepository.save(forum1);

            return fromForum(forum2);
        }
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
        forumDto.likes = forum.getLikes();
        forumDto.comments = forum.getComments();
        forumDto.views = forum.getViews();

        return forumDto;
    }

    public Forum toForum(ForumInputDto forumInputDto) {
        var forum = new Forum();
        forum.setName(forumInputDto.name);
        forum.setAge(forumInputDto.age);
        forum.setTitle(forumInputDto.title);
        forum.setText(forumInputDto.text);
        forum.setDate(forumInputDto.date);
        forum.setLastReaction(forumInputDto.lastReaction);
        forum.setLikes(forumInputDto.likes);
        forum.setComments(forumInputDto.comments);
        forum.setViews(forumInputDto.views);

        return forum;
    }


}
