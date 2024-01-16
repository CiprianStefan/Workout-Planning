package com.example.workoutplanning.workouts.services;

import java.util.HashMap;

public interface WorkoutService {
    String createWorkout(int user_id, HashMap<String, String> body);
    String updateWorkout(int user_id, int workout_id, HashMap<String, String> body);
    String deleteWorkout(int user_id, int workout_id);
    String getWorkout(int user_id, int workout_id);
    String getAllWorkouts(int user_id);
}
