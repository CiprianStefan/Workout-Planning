package com.example.workoutplanning.reviews;

import com.example.workoutplanning.reviews.services.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ReviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void testCreateReviewEndpoint() throws Exception {
        // Mocking the service response
        when(reviewService.addReview(anyInt(), anyString())).thenReturn("Review created successfully!");

        // Request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("description", "testComment");

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(post("/reviews")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Review created successfully!"));
    }

    @Test
    public void testUpdateReviewEndpoint() throws Exception {
        // Mocking the service response
        when(reviewService.updateReview(anyInt(), anyString())).thenReturn("Review updated successfully!");

        // Request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("description", "testComment");

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(put("/reviews")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Review updated successfully!"));
    }

    @Test
    public void testDeleteReviewEndpoint() throws Exception {
        // Mocking the service response
        when(reviewService.deleteReview(anyInt())).thenReturn("Review deleted successfully!");

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(delete("/reviews")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Review deleted successfully!"));
    }

    @Test
    public void testGetReviewEndpoint() throws Exception {
        // Mocking the service response
        when(reviewService.getAllReviews()).thenReturn("Review retrieved successfully!");

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(get("/reviews")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Review retrieved successfully!"));
    }
}
