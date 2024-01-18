package com.example.workoutplanning.reviews;

import com.example.workoutplanning.reviews.model.Review;
import com.example.workoutplanning.reviews.repositories.ReviewRepository;
import com.example.workoutplanning.reviews.services.ReviewServiceImpl;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReview() {
        // Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(reviewRepository.findAll()).thenReturn(new ArrayList<>());

        // Execution
        String result = reviewService.addReview(1, "Sample review");

        // Verification
        assertEquals("Review added!", result);
        verify(userRepository, times(1)).findById(anyLong());
        verify(reviewRepository, times(1)).findAll();
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testAddReviewWithExistingReview() {
        // Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        List<Review> existingReviews = new ArrayList<>();
        existingReviews.add(new Review(1, "Existing review"));
        when(reviewRepository.findAll()).thenReturn(existingReviews);

        // Execution and Verification
        assertThrows(RuntimeException.class, () -> reviewService.addReview(1, "Sample review"));
        verify(userRepository, times(1)).findById(anyLong());
        verify(reviewRepository, times(1)).findAll();
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void testAddReviewWithInvalidUser() {
        // Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Execution and Verification
        assertThrows(RuntimeException.class, () -> reviewService.addReview(1, "Sample review"));
        verify(userRepository, times(1)).findById(anyLong());
        verify(reviewRepository, never()).findAll();
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void testUpdateWithInvalidUser() {
        // Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Execution and Verification
        assertThrows(RuntimeException.class, () -> reviewService.updateReview(1, "Sample review"));
        verify(userRepository, times(1)).findById(anyLong());
        verify(reviewRepository, never()).findAll();
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void testUpdateReview_SuccessfulUpdate() {
        // Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(reviewRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(new Review(1, "Existing review"));
        }});

        // Execution
        String result = reviewService.updateReview(1, "Updated review");

        // Verification
        verify(reviewRepository, times(1)).updateReview(eq(1), eq("Updated review"));
        assertEquals("Review updated!", result);
    }

    @Test
    void testUpdateReview_UserNotAuthorized() {
        // Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Execution and Verification
        assertThrows(RuntimeException.class, () -> reviewService.updateReview(1, "Updated review"));
        verify(reviewRepository, never()).updateReview(anyInt(), anyString());
    }

    @Test
    void testUpdateReview_ReviewNotFound() {
        // Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(reviewRepository.findAll()).thenReturn(new ArrayList<>());

        // Execution and Verification
        assertThrows(RuntimeException.class, () -> reviewService.updateReview(1, "Updated review"));
        verify(reviewRepository, never()).updateReview(anyInt(), anyString());
    }

    @Test
    void testDeleteReview_SuccessfulDelete() {
        // Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(reviewRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(new Review(1, "Existing review"));
        }});

        // Execution
        String result = reviewService.deleteReview(1);

        // Verification
        verify(reviewRepository, times(1)).deleteReview(eq(1));
        assertEquals("Review deleted!", result);
    }

    @Test
    void testDeleteReview_UserNotAuthorized() {
        // Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Execution and Verification
        assertThrows(RuntimeException.class, () -> reviewService.deleteReview(1));
        verify(reviewRepository, never()).deleteReview(anyInt());
    }

    @Test
    void testDeleteReview_ReviewNotFound() {
        // Mocking
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(reviewRepository.findAll()).thenReturn(new ArrayList<>());

        // Execution and Verification
        assertThrows(RuntimeException.class, () -> reviewService.deleteReview(1));
        verify(reviewRepository, never()).deleteReview(anyInt());
    }

}

