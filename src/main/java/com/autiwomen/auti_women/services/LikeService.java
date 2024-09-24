package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.forums.ForumDto;
import com.autiwomen.auti_women.dtos.likes.LikeDto;
import com.autiwomen.auti_women.dtos.likes.LikeInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.models.Like;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.repositories.LikeRepository;
import com.autiwomen.auti_women.security.UserRepository;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.models.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;

    public LikeService(LikeRepository likeRepository, ForumRepository forumRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addLike(LikeInputDto likeInputDto) {
        Forum forum = forumRepository.findById(likeInputDto.getForum_id()).orElseThrow(() -> new RecordNotFoundException("Forum not found"));
        User user = userRepository.findById(likeInputDto.getUsername()).orElseThrow(() -> new RecordNotFoundException("User not found"));

        if (likeRepository.findByForumIdAndUsername(likeInputDto.getForum_id(), likeInputDto.getUsername()) != null) {
            throw new IllegalStateException("User has already liked this forum");
        }

        Like like = new Like(user, forum);
        likeRepository.save(like);
    }

    @Transactional
    public void removeLike(LikeInputDto likeInputDto) {
        Like like = likeRepository.findByForumIdAndUsername(likeInputDto.getForum_id(), likeInputDto.getUsername());
        if (like == null) {
            throw new RecordNotFoundException("Like not found");
        }
        likeRepository.delete(like);
    }

    public Long getLikesByForumId(Long forumId) {
        return likeRepository.countByForumId(forumId);
    }
}