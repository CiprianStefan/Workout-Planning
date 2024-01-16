package com.example.workoutplanning.achievements.controllers;

import com.example.workoutplanning.achievements.services.AchievementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AchievementController {

    AchievementService achievementService;
    public AchievementController (AchievementService achievementService){
        this.achievementService = achievementService;
    }

    @GetMapping("/achievements")
    public ResponseEntity<String> getAchievementsInformation(){
        try {
            return ResponseEntity.ok(achievementService.getAchievementsInformation());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/achievements_data")
    public ResponseEntity<String> getAchievementsDataByUser(@RequestHeader int user_id){
        try {
            return ResponseEntity.ok(achievementService.getAchievementsDataByUser(user_id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/achievements_test")
    public String updateAchievementsData(){
        achievementService.initAchievementsDataForUser(1);
        return "Hello";
    }
}
