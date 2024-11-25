package com.autiwomen.auti_women.controllers;

import com.autiwomen.auti_women.dtos.reviews.ReviewDto;
import com.autiwomen.auti_women.dtos.reviews.ReviewInputDto;
import com.autiwomen.auti_women.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewDto> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok().body(reviews);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ReviewDto> getReviewByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(reviewService.getReviewByUsername(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable("username") String username, @RequestBody ReviewInputDto reviewInputDto) {
        return ResponseEntity.ok().body(reviewService.updateReview(username, reviewInputDto));
    }

    @PostMapping("/{username}")
    public ResponseEntity<ReviewDto> createReview(@PathVariable("username") String username, @RequestBody ReviewInputDto reviewInputDto) {
        ReviewDto reviewDto = reviewService.createReview(username, reviewInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + reviewDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(reviewDto);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteReviewByUsername(@PathVariable("username") String username) {
        reviewService.deleteReviewByUsername(username);
        return ResponseEntity.noContent().build();
    }

}
