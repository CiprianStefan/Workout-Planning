package com.example.workoutplanning.achievements;

import com.example.workoutplanning.achievements.model.Achievement;
import com.example.workoutplanning.achievements.model.AchievementData;
import com.example.workoutplanning.achievements.repositories.AchievementDataRepository;
import com.example.workoutplanning.achievements.repositories.AchievementRepository;
import com.example.workoutplanning.achievements.services.AchievementServiceImpl;
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

class AchievementServiceImplTest {

    @Mock
    private AchievementDataRepository achievementDataRepository;

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AchievementServiceImpl achievementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitAchievementsDataForUser() {
        // Arrange
        int userId = 1;
        User user = new User();
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));
        when(achievementRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        assertDoesNotThrow(() -> achievementService.initAchievementsDataForUser(userId));

        // Assert
        verify(achievementDataRepository, times(1)).initAchievementsDataForUser(userId);
    }

    @Test
    void testInitAchievementsDataForUser_AchievementsNotFound() {
        // Arrange
        int userId = 1;
        User user = new User();
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));
        when(achievementRepository.findAll()).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> achievementService.initAchievementsDataForUser(userId));
    }

    @Test
    void testInitAchievementsDataForUser_UserFound() {
        // Arrange
        int userId = 1;
        User user = new User();
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));
        when(achievementRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        assertDoesNotThrow(() -> achievementService.initAchievementsDataForUser(userId));

        // Assert
        verify(achievementDataRepository, times(1)).initAchievementsDataForUser(userId);
    }

    @Test
    void testInitAchievementsDataForUser_UserNotFound() {
        // Arrange
        int userId = 1;
        when(userRepository.findById((long) userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> achievementService.initAchievementsDataForUser(userId));
    }

    @Test
    void testGetAchievementsDataByUser_AchievementsNotFound() {
        // Arrange
        int userId = 1;
        User user = new User();
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));
        when(achievementDataRepository.findAllByUserID(userId)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> achievementService.getAchievementsDataByUser(userId));
    }

    @Test
    void testGetAchievementsDataByUser_UserFound() {
        // Arrange
        int userId = 1;
        User user = new User();
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));
        when(achievementDataRepository.findAllByUserID(userId)).thenReturn(new ArrayList<>());

        // Act
        String result = achievementService.getAchievementsDataByUser(userId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetAchievementsDataByUser() {
        // Arrange
        int userId = 1;
        User user = new User();
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));
        when(achievementDataRepository.findAllByUserID(userId)).thenReturn(new ArrayList<>());

        // Act
        String result = achievementService.getAchievementsDataByUser(userId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetAchievementsDataByUser_UserNotFound() {
        // Arrange
        int userId = 1;
        when(userRepository.findById((long) userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> achievementService.getAchievementsDataByUser(userId));
    }

    @Test
    void testGetAchievementsDataByUser_AchievementsFound() {
        // Arrange
        int userId = 1;
        User user = new User();
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));
        when(achievementDataRepository.findAllByUserID(userId)).thenReturn(new ArrayList<>());

        // Act
        String result = achievementService.getAchievementsDataByUser(userId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetAchievementsInformation_AchievementsNotFound() {
        // Arrange
        when(achievementRepository.findAll()).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> achievementService.getAchievementsInformation());
    }

    @Test
    void testGetAchievementsInformation_AchievementsFound() {
        // Arrange
        when(achievementRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        String result = achievementService.getAchievementsInformation();

        // Assert
        assertNotNull(result);
    }


    @Test
    void testGetAchievementsInformation() {
        // Arrange
        when(achievementRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        String result = achievementService.getAchievementsInformation();

        // Assert
        assertNotNull(result);
    }



}

