package com.example.workoutplanning.exercises.controllers;


import com.example.workoutplanning.exercises.services.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExerciseController {

    ExerciseService exerciseService;
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/exercises/{id}")
    public ResponseEntity<String> getExerciseById(@PathVariable String id, @RequestHeader int user_id){
        try {
            return ResponseEntity.ok(exerciseService.getExerciseById(Integer.parseInt(id), user_id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/exercises")
    public ResponseEntity<String> getAllExercises(@RequestHeader int user_id){
        try {
            return ResponseEntity.ok(exerciseService.getAllExercises(user_id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
