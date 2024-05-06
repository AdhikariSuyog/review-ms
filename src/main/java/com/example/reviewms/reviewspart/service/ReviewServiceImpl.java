package com.example.reviewms.reviewspart.service;

import com.example.reviewms.reviewspart.exception.InvalidIdException;
import com.example.reviewms.reviewspart.model.Review;
import com.example.reviewms.reviewspart.repo.ReviewRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ResponseEntity<List<Review>> findAll() {
        return new ResponseEntity<>(reviewRepository.findAll(), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Review> createReview(Long companyId, Review review) {
        review.setCompanyId(companyId);
        return new ResponseEntity<>(reviewRepository.save(review), HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<Review> findByCompanyId(Long companyId) {
        var review = reviewRepository.findByCompanyId(companyId).orElseThrow(() -> new InvalidIdException("Review with id: " + companyId + " not found"));
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Review> updateReview(Review review, Long reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            Review review1 = findByCompanyId(reviewId).getBody();

            Optional.ofNullable(review.getDescription()).map(String::trim)
                    .filter(desc -> !desc.isEmpty())
                    .ifPresent(review1::setDescription);

            Optional.ofNullable(review.getTitle()).map(String::trim)
                    .filter(title -> !title.isEmpty())
                    .ifPresent(review1::setTitle);
            Optional.ofNullable(review.getRating()).ifPresent(review1::setRating);

            Optional.ofNullable(review.getCompanyId()).ifPresent(review1::setCompanyId);
            return new ResponseEntity<>(reviewRepository.save(review1), HttpStatus.OK);
        }
        return new ResponseEntity<>(review, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> deleteReview(Long reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
            return new ResponseEntity<>("review with id: " + reviewId + " is deleted.", HttpStatus.OK);
        }
        return new ResponseEntity<>("review with id: " + reviewId + " is not deleted.", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Review> findByReviewId(Long reviewId) {
        return new ResponseEntity<>(reviewRepository.findById(reviewId)
                .orElseThrow(() -> new InvalidIdException("Review with id: " + reviewId + " not found")), HttpStatus.OK);
    }
}
