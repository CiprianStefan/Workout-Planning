package com.example.workoutplanning.users.services;

import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.model.UserData;
import com.example.workoutplanning.users.repositories.UserDataRepository;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDataServiceImpl implements UserDataService {

    UserDataRepository userDataRepository;
    UserRepository userRepository;
    public UserDataServiceImpl(UserDataRepository userDataRepository, UserRepository userRepository) {
        this.userDataRepository = userDataRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String createUserData(UserData userData) {
        CheckUserAuthorization((long) userData.getUserid());
        UserData userDataCheck = userDataRepository.findByUserid(userData.getUserid());
        if (userDataCheck != null){
            throw new RuntimeException("User data already exists!");
        }
        userDataRepository.save(userData);
        return userData.toString();
    }

    @Override
    public String getUserData(int user_id) {
        CheckUserAuthorization((long) user_id);
        UserData userDataCheck = userDataRepository.findByUserid(user_id);
        return userDataCheck.toString();
    }

    @Override
    public String updateUserData(UserData userData) {
        CheckUserAuthorization((long) userData.getUserid());
        getUserDataCheck(userData.getUserid());
        userDataRepository.updateUserDataRegister(
                userData.getUserid(),
                userData.getWeight(),
                userData.getHeight(),
                userData.getAge());
        return userData.toString();
    }

    @Override
    public String deleteUserData(int user_id) {
        CheckUserAuthorization((long) user_id);
        UserData userDataCheck = getUserDataCheck(user_id);
        userDataRepository.delete(userDataCheck);
        return "User data deleted!";
    }

    private UserData getUserDataCheck(int user_id) {
        UserData userDataCheck = userDataRepository.findByUserid(user_id);
        if (userDataCheck == null){
            throw new RuntimeException("User data don't exists!");
        }
        return userDataCheck;
    }

    private void CheckUserAuthorization(long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
    }
}
