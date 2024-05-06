package com.example.reviewms.reviewspart.service;

import com.example.reviewms.reviewspart.model.Review;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ReviewService {
    ResponseEntity<List<Review>> findAll();

    ResponseEntity<Review> createReview(Long companyId,Review review);

    ResponseEntity<Review> findByCompanyId(Long companyId);

    ResponseEntity<Review> updateReview(Review review, Long id);

    ResponseEntity<String> deleteReview(Long id);

    ResponseEntity<Review> findByReviewId(Long reviewId);
}

