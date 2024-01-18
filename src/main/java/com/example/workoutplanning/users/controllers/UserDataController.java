package com.example.workoutplanning.users.controllers;

import com.example.workoutplanning.users.services.UserDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.workoutplanning.users.model.UserData;

import java.util.Map;

@RestController
public class UserDataController {

    UserDataService userDataService;

    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @PostMapping("/userdata")
    public ResponseEntity<String> setUserData(@RequestHeader int user_id, @RequestBody UserData userData){
        try{
            return ResponseEntity.ok(userDataService.createUserData(new UserData(
                    user_id,
                    userData.getWeight(),
                    userData.getHeight(),
                    userData.getAge()
            )));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/userdata")
    public ResponseEntity<String> getUserData(@RequestHeader int user_id){
        try{
            return ResponseEntity.ok(userDataService.getUserData(user_id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/userdata")
    public ResponseEntity<String> updateUserData(@RequestHeader int user_id, @RequestBody UserData userData){
        try{
            return ResponseEntity.ok(userDataService.updateUserData(new UserData(
                    user_id,
                    userData.getWeight(),
                    userData.getHeight(),
                    userData.getAge()
            )));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/userdata")
    public ResponseEntity<String> deleteUserData(@RequestHeader int user_id){
        try{
            return ResponseEntity.ok(userDataService.deleteUserData(user_id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
