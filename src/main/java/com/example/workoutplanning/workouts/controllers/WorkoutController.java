package com.example.workoutplanning.workouts.controllers;

import com.example.workoutplanning.workouts.services.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class WorkoutController {
    WorkoutService workoutService;
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("/workouts")
    public ResponseEntity<String> addWorkout(@RequestHeader int user_id, @RequestBody HashMap<String, Object> body){
        return ResponseEntity.ok(workoutService.createWorkout(user_id, (HashMap<String, String>) body.get("exercises")));
    }

    @PutMapping("/workouts")
    public ResponseEntity<String> updateWorkout(@RequestHeader int user_id, @RequestBody HashMap<String, Object> body){
        return ResponseEntity.ok(workoutService.updateWorkout(user_id, (int) body.get("workout_id"), (HashMap<String, String>) body.get("exercises")));
    }

    @DeleteMapping("/workouts")
    public ResponseEntity<String> deleteWorkout(@RequestHeader int user_id, @RequestBody HashMap<String, Object> body){
        return ResponseEntity.ok(workoutService.deleteWorkout(user_id, (int) body.get("workout_id")));
    }

    @GetMapping("/workouts")
    public ResponseEntity<String> getWorkout(@RequestHeader int user_id, @RequestBody HashMap<String, String> body) {
        try {
            return ResponseEntity.ok(workoutService.getWorkout(user_id, Integer.parseInt(body.get("workout_id"))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/workouts/all")
    public ResponseEntity<String> getAllWorkouts(@RequestHeader int user_id){
        try {
            return ResponseEntity.ok(workoutService.getAllWorkouts(user_id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
