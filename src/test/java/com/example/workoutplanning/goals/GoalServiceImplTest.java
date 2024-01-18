package com.example.workoutplanning.goals;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.workoutplanning.goals.model.Goal;
import com.example.workoutplanning.goals.repositories.GoalRepository;
import com.example.workoutplanning.goals.services.GoalServiceImpl;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;

class GoalServiceImplTest {

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GoalServiceImpl goalService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateGoal() {
        // Arrange
        int userId = 1;
        int exerciseId = 2;
        int units = 3;
        LocalDate dateStart = LocalDate.now();
        LocalDate dateEnd = LocalDate.now().plusDays(7);

        // Mock user authorization
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));

        // Act
        String result = goalService.createGoal(userId, exerciseId, units, dateStart, dateEnd);

        // Assert
        assertEquals("Goal created!", result);
        verify(goalRepository, times(1)).save(any(Goal.class));
    }

    @Test
    public void testGetGoal() {
        // Arrange
        int goalId = 1;
        int userId = 2;

        // Mock user authorization
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));

        // Mock goal retrieval
        when(goalRepository.findById((long) goalId)).thenReturn(Optional.of(new Goal()));

        // Act
        String result = goalService.getGoal(goalId, userId);

        // Assert
        assertNotNull(result);
        // Add more specific assertions based on the expected behavior
    }

    // Similar tests for getAllGoals, updateGoalGeneralInformation, deleteGoal can be added

    @Test
    public void testCheckUserAuthorization_UserNotFound() {
        // Arrange
        long userId = 1;

        // Mock user not found
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> goalService.CheckUserAuthorization(userId));
    }

    @Test
    public void testCheckUserAuthorization_UserFound() {
        // Arrange
        long userId = 1;

        // Mock user found
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        // Act
        assertDoesNotThrow(() -> goalService.CheckUserAuthorization(userId));
    }

    @Test
    public void testGetGoal_GoalNotFound() {
        // Arrange
        long goalId = 1;

        // Mock goal not found
        when(goalRepository.findById(goalId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> goalService.checkAndGetGoal(goalId));
    }

    @Test
    public void testGetGoal_GoalFound() {
        // Arrange
        long goalId = 1;

        // Mock goal found
        when(goalRepository.findById(goalId)).thenReturn(Optional.of(new Goal()));

        // Act
        assertDoesNotThrow(() -> goalService.checkAndGetGoal(goalId));
    }

    @Test
    public void testGetAllGoals() {
        // Arrange
        List<Goal> goals = new ArrayList<>();
        goals.add(new Goal());
        goals.add(new Goal());
        goals.add(new Goal());

        // Mock goal retrieval
        when(goalRepository.findAll()).thenReturn(goals);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        // Act
        String result = goalService.getAllGoals(anyInt());

        // Assert
        assertNotNull(result);
        verify(goalRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateGoalGeneralInformation() {

        // Mock
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(goalRepository.findById(anyLong())).thenReturn(Optional.of(new Goal()));


        // Act
        String result = goalService.updateGoalGeneralInformation(1, 1, 1, 1, LocalDate.now(), LocalDate.now().plusDays(7));

        // Assert
        assertEquals("Goal updated!", result);
        verify(goalRepository, times(1)).updateGoalGeneralInformation(anyInt(), anyInt(), anyInt(), anyInt(), any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    public void testUpdateGoalGeneralInformation_UserNotFound() {

            // Mock
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
            when(goalRepository.findById(anyLong())).thenReturn(Optional.of(new Goal()));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> goalService.updateGoalGeneralInformation(1, 1, 1, 1, LocalDate.now(), LocalDate.now().plusDays(7)));
            verify(goalRepository, never()).updateGoalGeneralInformation(anyInt(), anyInt(), anyInt(), anyInt(), any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    public void testUpdateGoalGeneralInformation_GoalNotFound() {

            // Mock
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
            when(goalRepository.findById(anyLong())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(RuntimeException.class, () -> goalService.updateGoalGeneralInformation(1, 1, 1, 1, LocalDate.now(), LocalDate.now().plusDays(7)));
            verify(goalRepository, never()).updateGoalGeneralInformation(anyInt(), anyInt(), anyInt(), anyInt(), any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    public void testDeleteGoal() {

        // Mock
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(goalRepository.findById(anyLong())).thenReturn(Optional.of(new Goal()));

        // Act
        String result = goalService.deleteGoal(1, 1);

        // Assert
        assertEquals("Goal deleted!", result);
        verify(goalRepository, times(1)).delete(any(Goal.class));
    }

    @Test
    public void testDeleteGoal_UserNotFound() {

            // Mock
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
            when(goalRepository.findById(anyLong())).thenReturn(Optional.of(new Goal()));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> goalService.deleteGoal(1, 1));
            verify(goalRepository, never()).delete(any(Goal.class));
    }

    @Test
    public void testDeleteGoal_GoalNotFound() {

            // Mock
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
            when(goalRepository.findById(anyLong())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(RuntimeException.class, () -> goalService.deleteGoal(1, 1));
            verify(goalRepository, never()).delete(any(Goal.class));
    }

}

