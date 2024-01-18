package com.example.workoutplanning.exercises;

import com.example.workoutplanning.exercises.services.ExerciseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ExercisesControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExerciseService exercisesService;

    @Test
    public void testGetExercisesEndpoint() throws Exception {
        // Mocking the service response
        when(exercisesService.getAllExercises(anyInt())).thenReturn("Exercises retrieved successfully!");

        mockMvc.perform(get("/exercises").header("user_id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Exercises retrieved successfully!"));
    }

    @Test
    public void testGetExerciseByIdEndpoint() throws Exception {
        // Mocking the service response
        when(exercisesService.getExerciseById(anyInt(), anyInt())).thenReturn("Exercise retrieved successfully!");

        mockMvc.perform(get("/exercises/1").header("user_id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Exercise retrieved successfully!"));
    }
}
