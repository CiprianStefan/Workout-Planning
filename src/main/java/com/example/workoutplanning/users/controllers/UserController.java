package com.example.workoutplanning.users.controllers;

import com.example.workoutplanning.users.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody Map<String, String> body){
        try {
            return ResponseEntity.ok(userService.createUser(
                    body.get("username"),
                    body.get("email"),
                    body.get("password")
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> body){
        try {
            return ResponseEntity.ok(userService.loginUser(
                    body.get("username"),
                    body.get("password")
            ).toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
