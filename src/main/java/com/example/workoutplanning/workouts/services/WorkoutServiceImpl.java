package com.example.workoutplanning.workouts.services;

import com.example.workoutplanning.users.repositories.UserRepository;
import com.example.workoutplanning.workouts.model.Workout;
import com.example.workoutplanning.workouts.repositories.WorkoutRepository;
import com.example.workoutplanning.users.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    WorkoutRepository workoutRepository;
    UserRepository userRepository;
    public WorkoutServiceImpl(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String createWorkout(int user_id, HashMap<String, String> body) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        workoutRepository.save(new Workout(
                user_id,
                body
        ));
        return "Workout created!";
    }

    @Override
    public String updateWorkout(int user_id, int workout_id, HashMap<String, String> body) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        Workout workout = workoutRepository.findById((long) workout_id).orElse(null);
        if (workout == null){
            throw new RuntimeException("Workout not found!");
        }
        Workout updatedWorkout = new Workout(
                user_id,
                body
        );
        workoutRepository.updateWorkout(user_id, workout_id, updatedWorkout.getExercises_and_units());
        return "Workout updated!";
    }

    @Override
    public String deleteWorkout(int user_id, int workout_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        Workout workout = workoutRepository.findById((long) workout_id).orElse(null);
        if (workout == null){
            throw new RuntimeException("Workout not found!");
        }
        workoutRepository.deleteById((long) workout_id);
        return "Workout deleted!";
    }

    @Override
    public String getWorkout(int user_id, int workout_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        Workout workout = workoutRepository.findById((long) workout_id).orElse(null);
        if (workout == null){
            throw new RuntimeException("Workout not found!");
        }
        return workout.toString();
    }

    @Override
    public String getAllWorkouts(int user_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        List<Workout> workouts = workoutRepository.findAll();
        if (workouts == null){
            throw new RuntimeException("Workout not found!");
        }
        return workouts.toString();
    }

}
