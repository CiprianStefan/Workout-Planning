package com.example.workoutplanning.users.services;

import com.example.workoutplanning.users.model.User;

public interface UserService {
    String createUser(String Username, String Email, String Password);
    Integer loginUser(String Username, String Password);
}
