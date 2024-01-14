package com.example.workoutplanning.exercises.services;

public interface ExerciseService {
    String getExerciseById(int id, int user_id);
    String getAllExercises(int user_id);
}
