package com.example.workoutplanning.workouts;

import com.example.workoutplanning.users.services.UserDataService;
import com.example.workoutplanning.workouts.services.WorkoutService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
public class WorkoutControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkoutService workoutService;

    @Test
    public void testCreateWorkoutEndpoint() throws Exception {
        // Mocking the service response
        when(workoutService.createWorkout(anyInt(), any())).thenReturn("Workout created successfully!");

        // Request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "testWorkout");
        requestBody.put("description", "testDescription");
        requestBody.put("difficulty", "testDifficulty");

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        // Performing the request and asserting the response
        mockMvc.perform(post("/workouts")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateWorkoutEndpoint() throws Exception {
        // Mocking the service response
        when(workoutService.updateWorkout(anyInt(), anyInt(), any())).thenReturn("Workout updated successfully!");

        // Request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("workout_id", 1);
        requestBody.put("exercises", new HashMap<String, String>() {{
            put("1", "2");
        }});

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        // Performing the request and asserting the response
        mockMvc.perform(put("/workouts")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Workout updated successfully!"));
    }


    @Test
    public void testGetWorkoutEndpoint() throws Exception {
        when(workoutService.getWorkout(anyInt(), anyInt())).thenReturn("Workout retrieved successfully!");

        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");
        headers.add("workout_id", "1");

        mockMvc.perform(get("/workouts").headers(headers))
                .andExpect(status().isOk())
                .andExpect(content().string("Workout retrieved successfully!"));
    }

    @Test
    public void testGetAllWorkoutsEndpoint() throws Exception {
        when(workoutService.getAllWorkouts(anyInt())).thenReturn("Workouts retrieved successfully!");

        mockMvc.perform(get("/workouts/all").header("user_id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Workouts retrieved successfully!"));
    }

    @Test
    public void testDeleteWorkoutEndpoint() throws Exception {
        when(workoutService.deleteWorkout(anyInt(), anyInt())).thenReturn("Workout deleted successfully!");

        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("workout_id", 1);

        mockMvc.perform(delete("/workouts").header("user_id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Workout deleted successfully!"));
    }
}
