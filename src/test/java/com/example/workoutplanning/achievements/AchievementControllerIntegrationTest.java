package com.example.workoutplanning.achievements;

import com.example.workoutplanning.achievements.controllers.AchievementController;
import com.example.workoutplanning.achievements.services.AchievementService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AchievementController.class)
class AchievementControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AchievementService achievementService;

    @Test
    void getAchievementsInformation_Success() throws Exception {
        when(achievementService.getAchievementsInformation()).thenReturn("Mocked achievement information");

        mockMvc.perform(get("/achievements"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mocked achievement information"));
    }

    @Test
    void getAchievementsInformation_Exception() throws Exception {
        when(achievementService.getAchievementsInformation()).thenThrow(new RuntimeException("Some error"));

        mockMvc.perform(get("/achievements"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Some error"));
    }

    @Test
    void getAchievementsDataByUser_Success() throws Exception {
        when(achievementService.getAchievementsDataByUser(anyInt())).thenReturn("Mocked user achievements data");

        mockMvc.perform(get("/achievements_data").header("user_id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mocked user achievements data"));
    }

    @Test
    void getAchievementsDataByUser_Exception() throws Exception {
        when(achievementService.getAchievementsDataByUser(anyInt())).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(get("/achievements_data").header("user_id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));
    }

}
