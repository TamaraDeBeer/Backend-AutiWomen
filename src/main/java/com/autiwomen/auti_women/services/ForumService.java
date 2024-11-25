package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.forums.ForumDto;
import com.autiwomen.auti_women.dtos.forums.ForumInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Comment;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.repositories.CommentRepository;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.repositories.LikeRepository;
import com.autiwomen.auti_women.repositories.ViewRepository;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.repositories.UserRepository;
import com.autiwomen.auti_women.security.models.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ForumService {

    private final ForumRepository forumRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ViewRepository viewRepository;
    private final CommentRepository commentRepository;

    public ForumService(ForumRepository forumRepository, UserRepository userRepository, LikeRepository likeRepository, ViewRepository viewRepository, CommentRepository commentRepository) {
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.viewRepository = viewRepository;
        this.commentRepository = commentRepository;
    }

    public List<ForumDto> getAllForums() {
        List<Forum> forumList = forumRepository.findAll();
        List<ForumDto> forumDtoList = new ArrayList<>();

        for (Forum forum : forumList) {
            updateForumCounts(forum);
            forumDtoList.add(fromForum(forum));
        }
        return forumDtoList;
    }

    public List<ForumDto> getForumsSortedByLikes() {
        List<Forum> forums = forumRepository.findAll();
        forums.forEach(this::updateForumCounts);
        return forums.stream()
                .sorted(Comparator.comparingInt(Forum::getLikesCount).reversed())
                .map(this::fromForum)
                .collect(Collectors.toList());
    }

    public List<ForumDto> getForumsSortedByDate() {
        List<Forum> forums = forumRepository.findAll();
        forums.forEach(this::updateForumCounts);
        return forums.stream()
                .sorted(Comparator.comparing(Forum::getDate).reversed())
                .map(this::fromForum)
                .collect(Collectors.toList());
    }

    public List<ForumDto> searchForums(String title) {
        List<Forum> forums = forumRepository.findAllByTitleContainingIgnoreCase(title);
        return forums.stream()
                .map(this::fromForum)
                .collect(Collectors.toList());
    }

    public ForumDto getForumById(Long forumId) {
        Optional<Forum> forumOptional = forumRepository.findById(forumId);
        if (forumOptional.isPresent()) {
            Forum forum = forumOptional.get();
            updateForumCounts(forum);
            return fromForum(forum);
        } else {
            throw new RecordNotFoundException("No forum found with id: " + forumId);
        }
    }

    public Set<ForumDto> getForumsByUsername(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found"));
        Set<Forum> forums = new HashSet<>(forumRepository.findByUser(user));
        if (forums.isEmpty()) {
            throw new RecordNotFoundException("No forums found for user: " + username);
        }
        return forums.stream().map(forum -> {
            if (forum.getUser() != null) {
                forum.setName(forum.getUser().getUsername());
                forum.setAge(forum.getUser().getDob().toString());
            }
            updateForumCounts(forum);
            return fromForum(forum);
        }).collect(Collectors.toSet());
    }

    public Set<ForumDto> getLikedForumsByUsername(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Set<Forum> likedForums = likeRepository.findLikedForumsByUser(user);
        if (likedForums.isEmpty()) {
            throw new RecordNotFoundException("No liked forums found for user: " + username);
        }
        return convertForumsToDtos(likedForums);
    }

    public Set<ForumDto> getViewedForumsByUsername(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Set<Forum> viewedForums = viewRepository.findViewedForumsByUser(user);
        if (viewedForums.isEmpty()) {
            throw new RecordNotFoundException("No viewed forums found for user: " + username);
        }
        return convertForumsToDtos(viewedForums);
    }

    public Set<ForumDto> getCommentedForumsByUsername(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Set<Forum> commentedForums = commentRepository.findCommentedForumsByUser(user);
        if (commentedForums.isEmpty()) {
            throw new RecordNotFoundException("No commented forums found for user: " + username);
        }
        return convertForumsToDtos(commentedForums);
    }

    public ForumDto createForum(ForumInputDto forumInputDto, String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found: " + username));

        Forum forum = toForum(forumInputDto);
        forum.setDate(String.valueOf(LocalDate.now()));
        forum.setName(user.getUsername());
        forum.setAge(user.getDob().toString());

        forumRepository.save(forum);
        return fromForum(forum);
    }

    public ForumDto updateForum(Long forumId, ForumDto updateForum) {
        Optional<Forum> forum = forumRepository.findById(forumId);
        if (forum.isEmpty()) {
            throw new RecordNotFoundException("No forum found with id: " + forumId);
        } else {
            Forum forum1 = forum.get();
            forum1.setTitle(updateForum.getTitle());
            forum1.setText(updateForum.getText());
            forum1.setTopic(updateForum.getTopic());
            Forum forum2 = forumRepository.save(forum1);

            return fromForum(forum2);
        }
    }

    @Transactional
    public void deleteForum(Long forumId) {
        Optional<Forum> optionalForum = forumRepository.findById(forumId);
        if (optionalForum.isPresent()) {
            commentRepository.deleteAllByForumId(forumId);
            forumRepository.deleteById(forumId);
        } else {
            throw new RecordNotFoundException("No forum found with id: " + forumId);
        }
    }


//    Helpers
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

    private Set<ForumDto> convertForumsToDtos(Set<Forum> forums) {
        Set<ForumDto> forumDtos = new HashSet<>();
        for (Forum forum : forums) {
            if (forum != null) {
                updateForumCounts(forum);
                ForumDto forumDto = fromForum(forum);
                forumDtos.add(forumDto);
            }
        }
        return forumDtos;
    }

    public void updateLastReaction(Long forumId) {
        Optional<Comment> lastComment = commentRepository.findTopByForumIdOrderByDateDesc(forumId);
        if (lastComment.isPresent()) {
            Forum forum = forumRepository.findById(forumId).orElseThrow(() -> new RecordNotFoundException("Forum not found"));
            forum.setLastReaction(String.valueOf(LocalDate.now()));
            forumRepository.save(forum);
        }
    }

    public String getLastReaction(Long forumId) {
        Optional<Comment> lastComment = commentRepository.findTopByForumIdOrderByDateDesc(forumId);
        return lastComment.map(Comment::getDate).orElse(null);
    }

    private void updateForumCounts(Forum forum) {
        int likeCount = likeRepository.getLikeCountByForumId(forum.getId());
        forum.setLikesCount(likeCount);

        int viewCount = viewRepository.getViewCountByForumId(forum.getId());
        forum.setViewsCount(viewCount);

        int commentCount = commentRepository.getCommentCountByForumId(forum.getId());
        forum.setCommentsCount(commentCount);

        forum.setLastReaction(getLastReaction(forum.getId()));
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
        forumDto.topic = forum.getTopic();
        forumDto.likesCount = forum.getLikesCount();
        forumDto.viewsCount = forum.getViewsCount();
        forumDto.commentsCount = forum.getCommentsCount();

        User user = forum.getUser();
        if (user != null) {
            forumDto.userDto = new UserDto(user.getUsername(), user.getProfilePictureUrl());
        } else {
            forumDto.userDto = null;
        }
        return forumDto;
    }

    public Forum toForum(ForumInputDto forumInputDto) {
        var forum = new Forum();
        forum.setName(forumInputDto.name);
        forum.setTitle(forumInputDto.title);
        forum.setText(forumInputDto.text);
        forum.setDate(forumInputDto.date);
        forum.setLastReaction(forumInputDto.lastReaction);
        forum.setTopic(forumInputDto.topic);

        return forum;
    }
}
