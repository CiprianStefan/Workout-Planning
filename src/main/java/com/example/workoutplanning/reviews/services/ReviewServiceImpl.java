package com.example.workoutplanning.reviews.services;

import com.example.workoutplanning.reviews.model.Review;
import com.example.workoutplanning.reviews.repositories.ReviewRepository;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    UserRepository userRepository;
    ReviewRepository reviewRepository;
    public ReviewServiceImpl(UserRepository userRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public String addReview(int user_id, String description) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        int reviewCount = reviewRepository.findAll().stream().filter(review -> review.getUser_id() == user_id).toList().size();
        if (reviewCount > 0){
            throw new RuntimeException("Review already exists!");
        }
        reviewRepository.save(new Review(
                user_id,
                description
        ));
        return "Review added!";
    }

    @Override
    public String updateReview(int user_id, String description) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        int reviewCount = reviewRepository.findAll().stream().filter(review -> review.getUser_id() == user_id).toList().size();
        if (reviewCount == 0){
            throw new RuntimeException("Review not found!");
        }
        Review updatedReview = new Review(
                user_id,
                description
        );
        reviewRepository.updateReview(user_id, updatedReview.getDescription());
        return "Review updated!";
    }

    @Override
    public String deleteReview(int user_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        int reviewCount = reviewRepository.findAll().stream().filter(review -> review.getUser_id() == user_id).toList().size();
        if (reviewCount == 0){
            throw new RuntimeException("Review not found!");
        }
        reviewRepository.deleteReview(user_id);
        return "Review deleted!";
    }

    @Override
    public String getAllReviews() {
        return reviewRepository.findAll().toString();
    }
}
