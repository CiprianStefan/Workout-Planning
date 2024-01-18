package com.example.workoutplanning.goals.controllers;

import com.example.workoutplanning.goals.model.Goal;
import com.example.workoutplanning.goals.services.GoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@RestController
public class GoalController {

    GoalService goalService;
    public GoalController(GoalService goalService) {this.goalService = goalService;}


    @PostMapping("/goals")
    public ResponseEntity<String> addGoal(@RequestHeader int user_id, @RequestBody Goal goal){
        try{
            return ResponseEntity.ok(goalService.createGoal(
                    user_id,
                    goal.getExercise_id(),
                    goal.getUnits(),
                    goal.getDate_start(),
                    goal.getDate_end()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/goals")
    public ResponseEntity<String> getGoals(@RequestHeader int user_id){
        try{
            return ResponseEntity.ok(goalService.getAllGoals(user_id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/goals/{goal_id}")
    public ResponseEntity<String> getGoal(@RequestHeader int user_id, @PathVariable int goal_id){
        try{
            return ResponseEntity.ok(goalService.getGoal(goal_id, user_id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/goals")
    public ResponseEntity<String> updateGoal(@RequestHeader int user_id, @RequestBody Goal goal){
        try{
            return ResponseEntity.ok(goalService.updateGoalGeneralInformation(
                    goal.getId(),
                    user_id,
                    goal.getExercise_id(),
                    goal.getUnits(),
                    goal.getDate_start(),
                    goal.getDate_end()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/goals")
    public ResponseEntity<String> deleteGoal(@RequestHeader int user_id, @RequestBody Map<String, String> body){
        try{
            return ResponseEntity.ok(goalService.deleteGoal(Integer.parseInt(body.get("goal_id")), user_id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
