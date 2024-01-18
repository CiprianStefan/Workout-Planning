package com.example.workoutplanning.goals;

import com.example.workoutplanning.goals.services.GoalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class GoalControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GoalService goalService;

    @Test
    public void testCreateGoalEndpoint() throws Exception {
        // Mocking the service response
        when(goalService.createGoal( anyInt(), anyInt(), anyInt(), any(LocalDate.class), any(LocalDate.class))).thenReturn("Goal created successfully!");

        // Request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("goal_id", 1);
        requestBody.put("exercise_id", 1);
        requestBody.put("units", 1);
        requestBody.put("date_start", LocalDate.now());
        requestBody.put("date_end", LocalDate.now());

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(post("/goals")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Goal created successfully!"));
    }

    @Test
    public void testUpdateGoalEndpoint() throws Exception {
        // Mocking the service response
        when(goalService.updateGoalGeneralInformation(anyInt(), anyInt(), anyInt(), anyInt(), any(LocalDate.class), any(LocalDate.class))).thenReturn("Goal updated successfully!");

        // Request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("goal_id", 1);
        requestBody.put("exercise_id", 1);
        requestBody.put("units", 1);
        requestBody.put("date_start", LocalDate.now());
        requestBody.put("date_end", LocalDate.now());

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(put("/goals")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Goal updated successfully!"));
    }

    @Test
    public void testDeleteGoalEndpoint() throws Exception {
        // Mocking the service response
        when(goalService.deleteGoal(anyInt(), anyInt())).thenReturn("Goal deleted successfully!");

        // Request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("goal_id", "1");

        mockMvc.perform(delete("/goals")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Goal deleted successfully!"));
    }

    @Test
    public void testGetGoalEndpoint() throws Exception {
        when(goalService.getGoal(anyInt(), anyInt())).thenReturn("Goal retrieved successfully!");

        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(get("/goals/1").headers(headers))
                .andExpect(status().isOk())
                .andExpect(content().string("Goal retrieved successfully!"));
    }

    @Test
    public void testGetAllGoalsByUserIdEndpoint() throws Exception {
        when(goalService.getAllGoals(anyInt())).thenReturn("Goals retrieved successfully!");

        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        mockMvc.perform(get("/goals").headers(headers))
                .andExpect(status().isOk())
                .andExpect(content().string("Goals retrieved successfully!"));
    }

}
