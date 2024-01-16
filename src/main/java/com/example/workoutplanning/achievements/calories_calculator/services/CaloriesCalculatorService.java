package com.example.workoutplanning.achievements.calories_calculator.services;

public interface CaloriesCalculatorService {
    int calculateActivityCalories(int user_id, int activity_id);
    int calculateWorkoutCalories(int user_id, int workout_id);
}
