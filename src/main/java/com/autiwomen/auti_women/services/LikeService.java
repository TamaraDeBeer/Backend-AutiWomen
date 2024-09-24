//package com.autiwomen.auti_women.services;
//
//import com.autiwomen.auti_women.dtos.likes.LikeInputDto;
//import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
//import com.autiwomen.auti_women.models.Forum;
//import com.autiwomen.auti_women.models.Like;
//import com.autiwomen.auti_women.repositories.ForumRepository;
//import com.autiwomen.auti_women.repositories.LikeRepository;
//import com.autiwomen.auti_women.security.UserRepository;
//import com.autiwomen.auti_women.security.models.User;
//import jakarta.transaction.Transactional;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//@Service
//public class LikeService {
//
//    private final LikeRepository likeRepository;
//    private final ForumRepository forumRepository;
//    private final UserRepository userRepository;
//
//    public LikeService(LikeRepository likeRepository, ForumRepository forumRepository, UserRepository userRepository) {
//        this.likeRepository = likeRepository;
//        this.forumRepository = forumRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Transactional
//    public void addLike(Long forumId) {
//        String username = getCurrentUsername();
//        Forum forum = forumRepository.findById(forumId).orElseThrow(() -> new RecordNotFoundException("Forum not found"));
//        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found"));
//
//        if (likeRepository.findByForumIdAndUsername(forumId, username) != null) {
//            throw new IllegalStateException("User has already liked this forum");
//        }
//
//        Like like = new Like(user, forum);
//        likeRepository.save(like);
//    }
//
//    @Transactional
//    public void removeLike(Long forumId) {
//        String username = getCurrentUsername();
//        Like like = likeRepository.findByForumIdAndUsername(forumId, username);
//        if (like == null) {
//            throw new RecordNotFoundException("Like not found");
//        }
//        likeRepository.delete(like);
//    }
//
//    public Long getLikesByForumId(Long forumId) {
//        return likeRepository.countByForumId(forumId);
//    }
//
//    private String getCurrentUsername() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            return ((UserDetails) principal).getUsername();
//        } else {
//            return principal.toString();
//        }
//    }
//
//    //    public ForumDto likeForum(@PathVariable Long id) {
////        Optional<Forum> forum = forumRepository.findById(id);
////        if (forum.isEmpty()) {
////            throw new RecordNotFoundException("Er is geen forum gevonden met id: " + id);
////        } else {
////            Forum forum1 = forum.get();
////            forum1.setLikes(forum1.getLikes() + 1);
////
////            Forum forum2 = forumRepository.save(forum1);
////
////            return fromForum(forum2);
////        }
////    }
//}