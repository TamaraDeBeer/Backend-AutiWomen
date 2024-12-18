package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.likes.LikeDto;
import com.autiwomen.auti_women.dtos.likes.LikeInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Forum;
import com.autiwomen.auti_women.models.Like;
import com.autiwomen.auti_women.repositories.ForumRepository;
import com.autiwomen.auti_women.repositories.LikeRepository;
import com.autiwomen.auti_women.security.repositories.UserRepository;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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

    public int getLikeCountByForumId(Long forumId) {
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new RecordNotFoundException("Forum not found"));
        Set<Like> likes = forum.getLikes();
        return likes.size();
    }

    public boolean hasUserLikedPost(String username, Long forumId) {
        if (!SecurityUtil.isOwnerOrAdmin(username)) {
            throw new SecurityException("Forbidden");
        }
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new RecordNotFoundException("Forum not found"));

        return likeRepository.findLikeByUserAndForum(user, forum).isPresent();
    }

    public int addLikeToForum(Long forumId, String username) {
        if (!SecurityUtil.isOwnerOrAdmin(username)) {
            throw new SecurityException("Forbidden");
        }
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new RecordNotFoundException("Forum not found"));

        Optional<Like> existingLike = likeRepository.findLikeByUserAndForum(user, forum);
        if (existingLike.isPresent()) {
            throw new IllegalStateException("User has already liked this forum");
        }
        LikeInputDto likeInputDto = new LikeInputDto();
        likeInputDto.setId(null);
        Like like = toLike(likeInputDto);
        like.setUser(user);
        like.setForum(forum);
        likeRepository.save(like);

        updateLikesCount(forum);
        return forum.getLikesCount();
    }

    public void removeLikeFromForum(Long forumId, String username) {
        if (!SecurityUtil.isOwnerOrAdmin(username)) {
            throw new SecurityException("Forbidden");
        }
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new RecordNotFoundException("Forum not found"));

        Optional<Like> like = likeRepository.findLikeByUserAndForum(user, forum);
        if (like.isPresent()) {
            likeRepository.delete(like.get());
        } else {
            throw new RecordNotFoundException("Like not found for user and forum");
        }
    }

//    Helper
public void updateLikesCount(Forum forum) {
    int likeCount = likeRepository.getLikeCountByForumId(forum.getId());
    forum.setLikesCount(likeCount);
    forumRepository.save(forum);
}

    public Like toLike(LikeInputDto likeInputDto) {
        var like = new Like();
        like.setId(likeInputDto.id);
        return like;
    }

}