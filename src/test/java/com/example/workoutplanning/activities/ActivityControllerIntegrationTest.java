package com.example.workoutplanning.activities;

import com.example.workoutplanning.activities.services.ActivityService;
import com.example.workoutplanning.calories_calculator.services.CaloriesCalculatorService;
import com.example.workoutplanning.exercises.services.ExerciseService;
import com.example.workoutplanning.friends.services.FriendsService;
import com.example.workoutplanning.users.services.UserDataService;
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
public class ActivityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ActivityService activityService;

    @Test
    public void testGetActivitiesEndpoint() throws Exception {
        // Mocking the service response
        when(activityService.getAllActivities(anyInt())).thenReturn("Activities retrieved successfully!");

        mockMvc.perform(get("/activities").header("user_id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Activities retrieved successfully!"));
    }


    @Test
    public void testCreateActivityEndpoint() throws Exception {
        // Mocking the service response
        when(activityService.createActivity(anyInt(), anyInt(), anyInt())).thenReturn("Activity created successfully!");

        // Request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("exercise_id", 1);
        requestBody.put("units", 2);


        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(post("/activities")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Activity created successfully!"));
    }

    @Test
    public void testUpdateActivityEndpoint() throws Exception {
        // Mocking the service response
        when(activityService.updateActivity(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn("Activity updated successfully!");

        // Request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("activity_id", 1);
        requestBody.put("exercise_id", 1);
        requestBody.put("units", 2);

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(put("/activities")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Activity updated successfully!"));
    }

    @Test
    public void testDeleteActivityEndpoint() throws Exception {
        // Mocking the service response
        when(activityService.deleteActivity(anyInt(), anyInt())).thenReturn("Activity deleted successfully!");

        // Request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("activity_id", "1");

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(delete("/activities")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Activity deleted successfully!"));
    }

}
