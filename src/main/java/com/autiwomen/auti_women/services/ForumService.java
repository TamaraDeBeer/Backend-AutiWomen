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
import com.autiwomen.auti_women.security.UserRepository;
import com.autiwomen.auti_women.security.models.User;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

            int likeCount = likeRepository.getLikeCountByForumId(forum.getId());
            forum.setLikesCount(likeCount);

            int viewCount = viewRepository.getViewCountByForumId(forum.getId());
            forum.setViewsCount(viewCount);

            int commentCount = commentRepository.getCommentCountByForumId(forum.getId());
            forum.setCommentsCount(commentCount);

            forumDtoList.add(fromForum(forum));
        }

        return forumDtoList;
    }

    public ForumDto createForum(ForumInputDto forumInputDto, String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        Forum forum = toForum(forumInputDto);
        forum.setDate(String.valueOf(LocalDate.now()));
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

    public void updateLastReaction(Long forumId) {
        Optional<Comment> lastComment = commentRepository.findTopByForumIdOrderByDateDesc(forumId);
        if (lastComment.isPresent()) {
            Forum forum = forumRepository.findById(forumId).orElseThrow(() -> new RecordNotFoundException("Forum not found"));
            forum.setLastReaction(String.valueOf(LocalDate.now()));
            forumRepository.save(forum);
        }
    }

    public ForumDto getForumById(Long id) {
        Optional<Forum> forumId = forumRepository.findById(id);
        if (forumId.isPresent()) {
            Forum forum = forumId.get();

            int likeCount = likeRepository.getLikeCountByForumId(forum.getId());
            forum.setLikesCount(likeCount);

            int viewCount = viewRepository.getViewCountByForumId(forum.getId());
            forum.setViewsCount(viewCount);

            int commentCount = commentRepository.getCommentCountByForumId(forum.getId());
            forum.setCommentsCount(commentCount);

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


    @Transactional
    public void deleteForum(Long id) {
        Optional<Forum> optionalForum = forumRepository.findById(id);
        if (optionalForum.isPresent()) {
            Forum forum = optionalForum.get();
            List<Comment> comments = commentRepository.findAllByForumId(id);
            for (Comment comment : comments) {
                comment.setForum(null);
                comment.setUser(null);
                commentRepository.save(comment);
            }
            commentRepository.deleteAllByForumId(id);
            forumRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Forum not found with id: " + id);
        }
    }

    public Set<Forum> getForumsByUsername(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found"));
        return new HashSet<>(forumRepository.findByUser(user));
    }

    public Set<ForumDto> getLikedForumsByUsername(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Set<Forum> likedForums = likeRepository.findLikedForumsByUser(user);
        Set<ForumDto> likedForumDtos = new HashSet<>();

        for (Forum forum : likedForums) {
            if (forum != null) {
                int likeCount = likeRepository.getLikeCountByForumId(forum.getId());
                forum.setLikesCount(likeCount);

                int viewCount = viewRepository.getViewCountByForumId(forum.getId());
                forum.setViewsCount(viewCount);

                int commentCount = commentRepository.getCommentCountByForumId(forum.getId());
                forum.setCommentsCount(commentCount);

                ForumDto forumDto = fromForum(forum);
                likedForumDtos.add(forumDto);
            }
        }
        return likedForumDtos;
    }

    public Set<ForumDto> getViewedForumsByUsername(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Set<Forum> viewedForums = viewRepository.findViewedForumsByUser(user);
        Set<ForumDto> viewedForumDtos = new HashSet<>();

        for (Forum forum : viewedForums) {
            if (forum != null) {
                int likeCount = likeRepository.getLikeCountByForumId(forum.getId());
                forum.setLikesCount(likeCount);

                int viewCount = viewRepository.getViewCountByForumId(forum.getId());
                forum.setViewsCount(viewCount);

                int commentCount = commentRepository.getCommentCountByForumId(forum.getId());
                forum.setCommentsCount(commentCount);

                ForumDto forumDto = fromForum(forum);
                viewedForumDtos.add(forumDto);
            }
        }
        return viewedForumDtos;
    }

    public Set<ForumDto> getCommentedForumsByUsername(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Set<Forum> commentedForums = commentRepository.findCommentedForumsByUser(user);
        Set<ForumDto> commentedForumDtos = new HashSet<>();

        for (Forum forum : commentedForums) {
            if (forum != null) {
                int likeCount = likeRepository.getLikeCountByForumId(forum.getId());
                forum.setLikesCount(likeCount);

                int viewCount = viewRepository.getViewCountByForumId(forum.getId());
                forum.setViewsCount(viewCount);

                int commentCount = commentRepository.getCommentCountByForumId(forum.getId());
                forum.setCommentsCount(commentCount);

                ForumDto forumDto = fromForum(forum);
                commentedForumDtos.add(forumDto);
            }
        }
        return commentedForumDtos;
    }

    public List<Forum> getForumsByTopic(String topic) {
        List<Forum> forums = forumRepository.findAll();
        List<Forum> forumsByTopic = new ArrayList<>();
        for (Forum forum : forums) {
            if (forum.getTopic().equals(topic)) {
                forumsByTopic.add(forum);
            }
        }
        return forumsByTopic;
    }

    public Set<String> getUniqueTopics() {
        List<Forum> forums = forumRepository.findAll();
        Set<String> uniqueTopics = new HashSet<>();
        for (Forum forum : forums) {
            if (!uniqueTopics.contains(forum.getTopic())) {
                uniqueTopics.add(forum.getTopic());
            }
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
