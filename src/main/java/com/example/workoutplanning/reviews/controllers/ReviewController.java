package com.example.workoutplanning.reviews.controllers;

import com.example.workoutplanning.reviews.model.Review;
import com.example.workoutplanning.reviews.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class ReviewController {

    ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<String> getReview(){
        try {
            return ResponseEntity.ok(reviewService.getAllReviews());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> addReview(@RequestHeader int user_id, @RequestBody Review review){
        try {
            return ResponseEntity.ok(reviewService.addReview(
                    user_id,
                    review.getDescription()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reviews")
    public ResponseEntity<String> updateReview(@RequestHeader int user_id, @RequestBody Review review){
        try {
            return ResponseEntity.ok(reviewService.updateReview(
                    user_id,
                    review.getDescription()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/reviews")
    public ResponseEntity<String> deleteReview(@RequestHeader int user_id){
        try {
            return ResponseEntity.ok(reviewService.deleteReview(
                    user_id
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
