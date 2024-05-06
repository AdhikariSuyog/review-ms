package com.example.reviewms.reviewspart.controller;

import com.example.reviewms.reviewspart.model.Review;
import com.example.reviewms.reviewspart.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/review")
@Controller
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Review>> findAll() {
        return reviewService.findAll();
    }

    @GetMapping("/get")
    public ResponseEntity<Review> findById(@RequestParam Long companyId) {
        return reviewService.findByCompanyId(companyId);
    }

    @GetMapping("/get/{reviewId}")
    public ResponseEntity<Review> findByReviewId(@PathVariable Long reviewId) {
        return reviewService.findByReviewId(reviewId);
    }

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestParam Long companyId,
            @RequestBody Review review) {
        return reviewService.createReview(companyId,review);
    }

    @PutMapping("/update/{reviewId}")
    public ResponseEntity<Review> updateReview(@RequestBody Review review,
                                               @PathVariable Long reviewId) {
        return reviewService.updateReview(review, reviewId);
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }
}
