package com.example.workoutplanning.calories_calculator.controllers;

import com.example.workoutplanning.calories_calculator.services.CaloriesCalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class CaloriesCalculatorControllers {

    CaloriesCalculatorService caloriesCalculatorService;
    public CaloriesCalculatorControllers(CaloriesCalculatorService caloriesCalculatorService) {
        this.caloriesCalculatorService = caloriesCalculatorService;
    }

    @GetMapping("/calories/activity")
    public ResponseEntity<String> calculateActivityCalories(@RequestHeader int user_id, @RequestBody HashMap<String, String> body){
        try {
            return ResponseEntity.ok(String.valueOf(caloriesCalculatorService.calculateActivityCalories(
                    user_id,
                    Integer.parseInt(body.get("activity_id"))
            )));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/calories/workout")
    public ResponseEntity<String> calculateWorkoutCalories(@RequestHeader int user_id, @RequestBody HashMap<String, String> body){
        try {
            return ResponseEntity.ok(String.valueOf(caloriesCalculatorService.calculateWorkoutCalories(
                    user_id,
                    Integer.parseInt(body.get("workout_id"))
            )));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
