package com.example.workoutplanning.calories_calculator;

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

public class CaloriesCalculatorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CaloriesCalculatorService caloriesCalculatorService;

    @Test
    public void testGetCaloriesByActivityIdEndpoint() throws Exception {
        // Mocking the service response
        when(caloriesCalculatorService.calculateActivityCalories(anyInt(), anyInt())).thenReturn(10);

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("activity_id", "1");

        mockMvc.perform(get("/calories/activity").header("user_id", "1").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    public void testGetCaloriesByWorkoutIdEndpoint() throws Exception {
        // Mocking the service response
        when(caloriesCalculatorService.calculateWorkoutCalories(anyInt(), anyInt())).thenReturn(10);

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("workout_id", "1");

        mockMvc.perform(get("/calories/workout").header("user_id", "1").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }
}
