package com.example.workoutplanning.achievements.calories_calculator.services;

import com.example.workoutplanning.activities.model.Activity;
import com.example.workoutplanning.activities.repositories.ActivityRepository;
import com.example.workoutplanning.exercises.model.Exercise;
import com.example.workoutplanning.exercises.repositories.ExerciseRepository;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import com.example.workoutplanning.workouts.model.Workout;
import com.example.workoutplanning.workouts.repositories.WorkoutRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
public class CaloriesCalculatorServiceImpl implements CaloriesCalculatorService {
    UserRepository userRepository;
    ExerciseRepository exerciseRepository;
    ActivityRepository activityRepository;
    WorkoutRepository workoutRepository;
    public CaloriesCalculatorServiceImpl(UserRepository userRepository, ExerciseRepository exerciseRepository, ActivityRepository activityRepository, WorkoutRepository workoutRepository) {
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
        this.activityRepository = activityRepository;
        this.workoutRepository = workoutRepository;
    }
    @Override
    public int calculateActivityCalories(int user_id, int activity_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        Activity activity = activityRepository.findById((long) activity_id).orElse(null);
        if (activity == null){
            throw new RuntimeException("Activity not found!");
        }
        Exercise exercise = exerciseRepository.findById((long) activity.getExercise_id()).orElse(null);
        if (exercise == null){
            throw new RuntimeException("Exercise not found!");
        }
        return (int) (activity.getUnits() * exercise.getCalories_burned_per_unit());
    }

    @Override
    public int calculateWorkoutCalories(int user_id, int workout_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }
        Workout workout = workoutRepository.findById((long) workout_id).orElse(null);
        if (workout == null) {
            throw new RuntimeException("Workout not found!");
        }
        int calories = 0;
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> workout_exercises_and_units = null;
        try {
            workout_exercises_and_units = mapper.readValue(workout.getExercises_and_units(), HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < workout_exercises_and_units.size(); i++) {
            Exercise exercise = exerciseRepository.findById((long) Integer.parseInt((String) workout_exercises_and_units.keySet().toArray()[i])).orElse(null);
            if (exercise == null) {
                throw new RuntimeException("Exercise not found!");
            }
            calories += (int) (Integer.parseInt((String) workout_exercises_and_units.values().toArray()[i]) * exercise.getCalories_burned_per_unit());
        }
        return calories;
    }
}
