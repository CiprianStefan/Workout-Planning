package com.example.workoutplanning.reviews.services;

public interface ReviewService {
    String addReview(int user_id, String description);
    String updateReview(int user_id, String description);
    String deleteReview(int user_id);
    String getAllReviews();
}
