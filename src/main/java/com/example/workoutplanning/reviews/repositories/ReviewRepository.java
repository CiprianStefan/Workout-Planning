package com.example.workoutplanning.reviews.repositories;

import com.example.workoutplanning.reviews.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE reviews SET description = ?2 WHERE user_id = ?1", nativeQuery = true)
    void updateReview(int user_id, String description);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reviews WHERE user_id = ?1", nativeQuery = true)
    void deleteReview(int user_id);
}
