package com.example.workoutplanning.workouts.services;

import com.example.workoutplanning.exercises.model.Exercise;
import com.example.workoutplanning.exercises.repositories.ExerciseRepository;
import com.example.workoutplanning.users.repositories.UserRepository;
import com.example.workoutplanning.workouts.model.Workout;
import com.example.workoutplanning.workouts.repositories.WorkoutRepository;
import com.example.workoutplanning.users.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    UserRepository userRepository;
    WorkoutRepository workoutRepository;
    ExerciseRepository exerciseRepository;
    public WorkoutServiceImpl( UserRepository userRepository, WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public String createWorkout(int user_id, HashMap<String, String> body) {
        CheckUserAuthorization((long) user_id);
        CheckExercises(body);
        workoutRepository.save(new Workout(
                user_id,
                body
        ));
        return "Workout created!";
    }



    @Override
    public String updateWorkout(int user_id, int workout_id, HashMap<String, String> body) {
        CheckUserAuthorization((long) user_id);
        Workout workout = workoutRepository.findById((long) workout_id).orElse(null);
        if (workout == null || workout.getUser_id() != user_id){
            throw new RuntimeException("Workout not found!");
        }
        CheckExercises(body);
        Workout updatedWorkout = new Workout(
                user_id,
                body
        );
        workoutRepository.updateWorkout(user_id, workout_id, updatedWorkout.getExercises_and_units());
        return "Workout updated!";
    }

    @Override
    public String deleteWorkout(int user_id, int workout_id) {
        CheckUserAuthorization((long) user_id);
        Workout workout = workoutRepository.findById((long) workout_id).orElse(null);
        if (workout == null || workout.getUser_id() != user_id){
            throw new RuntimeException("Workout not found!");
        }
        workoutRepository.deleteById((long) workout_id);
        return "Workout deleted!";
    }

    @Override
    public String getWorkout(int user_id, int workout_id) {
        CheckUserAuthorization((long) user_id);
        Workout workout = workoutRepository.findById((long) workout_id).orElse(null);
        if (workout == null || workout.getUser_id() != user_id){
            throw new RuntimeException("Workout not found!");
        }
        return workout.toString();
    }

    @Override
    public String getAllWorkouts(int user_id) {
        CheckUserAuthorization((long) user_id);
        List<Workout> workouts = workoutRepository.findAll();
        if (workouts == null){
            throw new RuntimeException("No workout found!");
        }
        workouts.removeIf(workout -> workout.getUser_id() != user_id);
        return workouts.toString();
    }

    private void CheckUserAuthorization(long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
    }

    private void CheckExercises(HashMap<String, String> body) {
        for (String exercise : body.keySet()) {
            if (exercise == null || exercise.isEmpty()) {
                throw new RuntimeException("Exercise name cannot be empty!");
            }
            if (body.get(exercise) == null || body.get(exercise).isEmpty()) {
                throw new RuntimeException("Exercise unit cannot be empty!");
            }
            if (exerciseRepository.findById((long) Integer.parseInt(exercise)).orElse(null) == null) {
                throw new RuntimeException("Exercise not found!");
            }
        }
    }

}
