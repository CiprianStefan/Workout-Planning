package com.example.workoutplanning.activities.controllers;

import com.example.workoutplanning.activities.model.Activity;
import com.example.workoutplanning.activities.services.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ActivityController {

    ActivityService activityService;
    public ActivityController(ActivityService activityService) {this.activityService = activityService;}

        @PostMapping("/activities")
    public ResponseEntity<String> addActivity(@RequestHeader int user_id, @RequestBody Activity activity){
        try{
            return ResponseEntity.ok(activityService.createActivity(
                    user_id,
                    activity.getExercise_id(),
                    activity.getUnits()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/activities")
    public ResponseEntity<String> getActivities(@RequestHeader int user_id){
        try{
            return ResponseEntity.ok(activityService.getAllActivities(user_id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/activities")
    public ResponseEntity<String> updateActivity(@RequestHeader int user_id, @RequestBody Activity activity){
        try{
            return ResponseEntity.ok(activityService.updateActivity(
                    activity.getId(),
                    user_id,
                    activity.getExercise_id(),
                    activity.getUnits()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/activities")
    public ResponseEntity<String> deleteActivity(@RequestHeader int user_id, @RequestBody Map<String,String> body){
        try{
            return ResponseEntity.ok(activityService.deleteActivity(
                    Integer.parseInt(body.get("activity_id")),
                    user_id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
