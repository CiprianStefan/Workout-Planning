package com.example.workoutplanning.exercises.services;

import com.example.workoutplanning.exercises.model.Exercise;
import com.example.workoutplanning.exercises.repositories.ExerciseRepository;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements ExerciseService{

    ExerciseRepository exerciseRepository;
    UserRepository userRepository;
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String getExerciseById(int id, int user_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        Exercise exercise = exerciseRepository.findById((long) id).orElse(null);
        if (exercise == null){
            throw new RuntimeException("Exercise not found!");
        }
        return exercise.toString();
    }

    @Override
    public String getAllExercises(int user_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        return exerciseRepository.findAll().toString();
    }
}
