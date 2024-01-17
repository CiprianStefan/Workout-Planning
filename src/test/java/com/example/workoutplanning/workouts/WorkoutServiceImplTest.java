package com.example.workoutplanning.workouts;

import com.example.workoutplanning.exercises.model.Exercise;
import com.example.workoutplanning.exercises.repositories.ExerciseRepository;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import com.example.workoutplanning.workouts.model.Workout;
import com.example.workoutplanning.workouts.repositories.WorkoutRepository;
import com.example.workoutplanning.workouts.services.WorkoutServiceImpl;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class WorkoutServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private WorkoutServiceImpl workoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateWorkout() {
        // Arrange
        int userId = 1;
        HashMap<String, String> body = new HashMap<>();
        body.put("1", "2");

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(new Exercise()));

        // Act
        String result = workoutService.createWorkout(userId, body);

        // Assert
        assertEquals("Workout created!", result);

        // Verify interactions
        verify(workoutRepository, times(1)).save(any(Workout.class));
    }

    @Test
    public void testCreateWorkout_UserNotFound() {
        // Arrange
        int userId = 1;
        HashMap<String, String> body = new HashMap<>();
        body.put("1", "2");

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> workoutService.createWorkout(userId, body));
    }

    @Test
    public void testCreateWorkout_ExerciseNotFound() {
        // Arrange
        int userId = 1;
        HashMap<String, String> body = new HashMap<>();
        body.put("1", "2");

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> workoutService.createWorkout(userId, body));
    }

    @Test
    public void testUpdateWorkout() {
        // Arrange
        int userId = 1;
        int workoutId = 1;
        HashMap<String, String> body = new HashMap<>();
        body.put("1", "1");

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(workoutRepository.findById((long) workoutId)).thenReturn(Optional.of(new Workout()));
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(new Exercise()));

        // Act
        String result = workoutService.updateWorkout(userId, workoutId, body);

        // Assert
        assertEquals("Workout updated!", result);

        // Verify interactions
        verify(workoutRepository, times(1)).updateWorkout(eq(userId), eq(workoutId), eq("{\"1\":\"1\"}"));
    }

    @Test
    public void testUpdateWorkout_UserNotFound() {
        // Arrange
        int userId = 1;
        int workoutId = 1;
        HashMap<String, String> body = new HashMap<>();
        body.put("1", "1");

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> workoutService.updateWorkout(userId, workoutId, body));
    }

    @Test
    public void testUpdateWorkout_WorkoutNotFound() {
        // Arrange
        int userId = 1;
        int workoutId = 1;
        HashMap<String, String> body = new HashMap<>();
        body.put("1", "1");

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(workoutRepository.findById((long) workoutId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> workoutService.updateWorkout(userId, workoutId, body));
    }

    @Test
    public void testUpdateWorkout_ExerciseNotFound() {
        // Arrange
        int userId = 1;
        int workoutId = 1;
        HashMap<String, String> body = new HashMap<>();
        body.put("1", "1");

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(workoutRepository.findById((long) workoutId)).thenReturn(Optional.of(new Workout()));
        when(exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> workoutService.updateWorkout(userId, workoutId, body));
    }

    @Test
    public void testDeleteWorkout() {
        // Arrange
        int userId = 1;
        int workoutId = 1;

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(workoutRepository.findById((long) workoutId)).thenReturn(Optional.of(new Workout()));

        // Act
        String result = workoutService.deleteWorkout(userId, workoutId);

        // Assert
        assertEquals("Workout deleted!", result);

        // Verify interactions
        verify(workoutRepository, times(1)).deleteById((long) workoutId);
    }

    @Test
    public void testDeleteWorkout_UserNotFound() {
        // Arrange
        int userId = 1;
        int workoutId = 1;

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> workoutService.deleteWorkout(userId, workoutId));
    }

    @Test
    public void testDeleteWorkout_WorkoutNotFound() {
        // Arrange
        int userId = 1;
        int workoutId = 1;

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(workoutRepository.findById((long) workoutId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> workoutService.deleteWorkout(userId, workoutId));
    }

    @Test
    public void testGetWorkout() {
        // Arrange
        int userId = 1;
        int workoutId = 1;

        Workout workout = new Workout(userId, new HashMap<>());

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(workoutRepository.findById((long) workoutId)).thenReturn(Optional.of(new Workout(userId, new HashMap<>())));

        // Act
        String result = workoutService.getWorkout(userId, workoutId);

        // Assert
        assertEquals(workout.toString(), result);
    }

    @Test
    public void testGetWorkout_UserNotFound() {
        // Arrange
        int userId = 1;
        int workoutId = 1;

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> workoutService.getWorkout(userId, workoutId));
    }

    @Test
    public void testGetWorkout_WorkoutNotFound() {
        // Arrange
        int userId = 1;
        int workoutId = 1;

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(workoutRepository.findById((long) workoutId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> workoutService.getWorkout(userId, workoutId));
    }

    @Test
    public void testGetAllWorkouts() {
        // Arrange
        int userId = 1;

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(workoutRepository.findAll()).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> workoutService.getAllWorkouts(userId));
    }

    @Test
    public void testGetAllWorkouts_UserNotFound() {
        // Arrange
        int userId = 1;

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> workoutService.getAllWorkouts(userId));
    }

    @Test
    public void testGetAllWorkouts_NoWorkoutsFound() {
        // Arrange
        int userId = 1;

        // Mocking behavior
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(workoutRepository.findAll()).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> workoutService.getAllWorkouts(userId));
    }



}
