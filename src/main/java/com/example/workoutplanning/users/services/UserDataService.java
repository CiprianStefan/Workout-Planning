package com.example.workoutplanning.users.services;

import com.example.workoutplanning.users.model.UserData;

public interface UserDataService {
    String createUserData(UserData userData);
    String getUserData(int user_id);
    String updateUserData(UserData userData);
    String deleteUserData(int user_id);
}
