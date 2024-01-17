package com.example.workoutplanning.exercises;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.workoutplanning.exercises.model.Exercise;
import com.example.workoutplanning.exercises.repositories.ExerciseRepository;
import com.example.workoutplanning.exercises.services.ExerciseServiceImpl;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

class ExerciseServiceImplTest {

    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExerciseById() {
        // Mocking user and exercise
        User user = new User();
        Exercise exercise = new Exercise();

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
        when(exerciseRepository.findById(anyLong())).thenReturn(java.util.Optional.of(exercise));

        // Test the method
        String result = exerciseService.getExerciseById(1, 2);

        verify(userRepository, times(1)).findById(2L);
        verify(exerciseRepository, times(1)).findById(1L);
    }

    @Test
    void testGetExerciseById_UserNotFound() {
        // Mocking user and exercise
        User user = new User();
        Exercise exercise = new Exercise();

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());
        when(exerciseRepository.findById(anyLong())).thenReturn(java.util.Optional.of(exercise));

        assertThrows(RuntimeException.class, () -> exerciseService.getExerciseById(1, 2));
        verify(userRepository, times(1)).findById(2L);
        verify(exerciseRepository, times(0)).findById(1L);
    }

    @Test
    void testGetExerciseById_ExerciseNotFound() {
        // Mocking user and exercise
        User user = new User();
        Exercise exercise = new Exercise();

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
        when(exerciseRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> exerciseService.getExerciseById(1, 2));
        verify(userRepository, times(1)).findById(2L);
        verify(exerciseRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllExercises() {
        // Mocking user
        User user = new User();

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
        when(exerciseRepository.findAll()).thenReturn(List.of(new Exercise(), new Exercise()));

        // Test the method
        String result = exerciseService.getAllExercises(2);

        verify(exerciseRepository, times(1)).findAll();
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void testGetAllExercises_UserNotFound() {
        // Mocking user
        User user = new User();

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());
        when(exerciseRepository.findAll()).thenReturn(List.of(new Exercise(), new Exercise()));

        // Test the method
        assertThrows(RuntimeException.class, () -> exerciseService.getAllExercises(2));

        verify(exerciseRepository, times(0)).findAll();
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void testGetAllExercises_NoExercises() {
        // Mocking user
        User user = new User();

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
        when(exerciseRepository.findAll()).thenReturn(List.of());

        // Test the method
        String result = exerciseService.getAllExercises(2);

        verify(exerciseRepository, times(1)).findAll();
        verify(userRepository, times(1)).findById(2L);
    }
}

