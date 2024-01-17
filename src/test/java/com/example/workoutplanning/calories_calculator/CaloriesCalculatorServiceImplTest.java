package com.example.workoutplanning.calories_calculator;

import com.example.workoutplanning.activities.model.Activity;
import com.example.workoutplanning.activities.repositories.ActivityRepository;
import com.example.workoutplanning.calories_calculator.services.CaloriesCalculatorServiceImpl;
import com.example.workoutplanning.exercises.model.Exercise;
import com.example.workoutplanning.exercises.repositories.ExerciseRepository;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import com.example.workoutplanning.workouts.model.Workout;
import com.example.workoutplanning.workouts.repositories.WorkoutRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CaloriesCalculatorServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private CaloriesCalculatorServiceImpl caloriesCalculatorService;

    public CaloriesCalculatorServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateActivityCalories_validData_calculatesCalories() {
        // Arrange
        int userId = 1;
        int activityId = 2;
        User user = new User();
        user.setId(userId);

        Activity activity = new Activity();
        activity.setExercise_id(3);
        activity.setUnits(2);

        Exercise exercise = new Exercise();
        exercise.setCalories_burned_per_unit(5);

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));
        when(activityRepository.findById((long) activityId)).thenReturn(Optional.of(activity));
        when(exerciseRepository.findById((long) activity.getExercise_id())).thenReturn(Optional.of(exercise));

        // Act
        int result = caloriesCalculatorService.calculateActivityCalories((int) userId, (int) activityId);

        // Assert
        assertEquals(2 * 5, result);
    }

    @Test
    void calculateActivityCalories_userNotFound_throwsException() {
        // Arrange
        int userId = 1;
        int activityId = 2;

        when(userRepository.findById((long) userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> caloriesCalculatorService.calculateActivityCalories((int) userId, (int) activityId));
    }

    @Test
    void calculateActivityCalories_activityNotFound_throwsException() {
        // Arrange
        int userId = 1;
        int activityId = 2;

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(activityRepository.findById((long) activityId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> caloriesCalculatorService.calculateActivityCalories((int) userId, (int) activityId));
    }

    @Test
    void calculateActivityCalories_exerciseNotFound_throwsException() {
        // Arrange
        int userId = 1;
        int activityId = 2;

        User user = new User();
        user.setId(userId);

        Activity activity = new Activity();
        activity.setExercise_id(3);
        activity.setUnits(2);

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));
        when(activityRepository.findById((long) activityId)).thenReturn(Optional.of(activity));
        when(exerciseRepository.findById((long) activity.getExercise_id())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> caloriesCalculatorService.calculateActivityCalories((int) userId, (int) activityId));
    }

    @Test
    void calculateWorkoutCalories_validData_calculatesCalories() {
        // Arrange
        int userId = 1;
        int workoutId = 2;
        User user = new User();
        user.setId(userId);
        HashMap<String, String> body = new HashMap<>();
        body.put("4", "2");

        Workout workout = new Workout(1, body);
        workout.setId(workoutId);

        Activity activity = new Activity();
        activity.setExercise_id(4);
        activity.setUnits(2);

        Exercise exercise = new Exercise();
        exercise.setCalories_burned_per_unit(5);

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));
        when(workoutRepository.findById((long) workoutId)).thenReturn(Optional.of(workout));
        when(activityRepository.findById((long) activity.getId())).thenReturn(Optional.of(activity));
        when(exerciseRepository.findById((long) activity.getExercise_id())).thenReturn(Optional.of(exercise));

        // Act
        int result = caloriesCalculatorService.calculateWorkoutCalories((int) userId, (int) workoutId);

        // Assert
        assertEquals(2 * 5, result);
    }

    @Test
    void calculateWorkoutCalories_userNotFound_throwsException() {
        // Arrange
        int userId = 1;
        int workoutId = 2;
        HashMap<String, String> body = new HashMap<>();
        body.put("4", "2");

        when(userRepository.findById((long) userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> caloriesCalculatorService.calculateWorkoutCalories((int) userId, (int) workoutId));
    }

    @Test
    void calculateWorkoutCalories_workoutNotFound_throwsException() {
        // Arrange
        int userId = 1;
        int workoutId = 2;
        HashMap<String, String> body = new HashMap<>();
        body.put("4", "2");

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(new User()));
        when(workoutRepository.findById((long) workoutId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> caloriesCalculatorService.calculateWorkoutCalories((int) userId, (int) workoutId));
    }

    @Test
    void calculateWorkoutCalories_activityNotFound_throwsException() {
        // Arrange
        int userId = 1;
        int workoutId = 2;
        User user = new User();
        user.setId(userId);
        HashMap<String, String> body = new HashMap<>();
        body.put("4", "2");

        Workout workout = new Workout(1, body);
        workout.setId(workoutId);

        Activity activity = new Activity();
        activity.setExercise_id(4);
        activity.setUnits(2);

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));
        when(workoutRepository.findById((long) workoutId)).thenReturn(Optional.of(workout));
        when(activityRepository.findById((long) activity.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> caloriesCalculatorService.calculateWorkoutCalories((int) userId, (int) workoutId));
    }

    @Test
    void calculateWorkoutCalories_exerciseNotFound_throwsException() {
        // Arrange
        int userId = 1;
        int workoutId = 2;
        User user = new User();
        user.setId(userId);
        HashMap<String, String> body = new HashMap<>();
        body.put("4", "2");

        Workout workout = new Workout(1, body);
        workout.setId(workoutId);

        Activity activity = new Activity();
        activity.setExercise_id(4);
        activity.setUnits(2);

        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));
        when(workoutRepository.findById((long) workoutId)).thenReturn(Optional.of(workout));
        when(activityRepository.findById((long) activity.getId())).thenReturn(Optional.of(activity));
        when(exerciseRepository.findById((long) activity.getExercise_id())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> caloriesCalculatorService.calculateWorkoutCalories((int) userId, (int) workoutId));
    }

}

