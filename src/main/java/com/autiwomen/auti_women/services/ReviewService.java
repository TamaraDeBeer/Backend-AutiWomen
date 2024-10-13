package com.autiwomen.auti_women.services;

import com.autiwomen.auti_women.dtos.reviews.ReviewDto;
import com.autiwomen.auti_women.dtos.reviews.ReviewInputDto;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.models.Review;
import com.autiwomen.auti_women.repositories.ReviewRepository;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.security.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewService(UserRepository userRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    public ReviewDto createReview(String username, ReviewInputDto reviewInputDto) {
        User user = userRepository.findById(username).orElseThrow(() -> new RuntimeException("User not found"));

        Review review = toReview(reviewInputDto);
        review.setReview(review.getReview());
        reviewRepository.save(review);

        return fromReview(review);
    }

    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::fromReview)
                .collect(Collectors.toList());
    }

    public ReviewDto getReviewByUsername(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found"));
        Review review = reviewRepository.findByUser(user).orElseThrow(() -> new RecordNotFoundException("Review not found"));
        return fromReview(review);
    }

    public ReviewDto updateReview(String username, ReviewInputDto reviewInputDto) {
        User user = userRepository.findById(username).orElseThrow(() -> new RecordNotFoundException("User not found"));
        Review review = reviewRepository.findByUser(user).orElseThrow(() -> new RecordNotFoundException("Review not found"));

        review.setReview(reviewInputDto.getReview());
        reviewRepository.save(review);

        return fromReview(review);
    }

    public ReviewDto fromReview(Review review) {
        var reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setReview(review.getReview());
        return reviewDto;
    }

    public Review toReview(ReviewInputDto reviewInputDto) {
        var review = new Review();
        review.setReview(reviewInputDto.getReview());
        return review;
    }
}