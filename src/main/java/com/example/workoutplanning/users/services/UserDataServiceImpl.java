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
        User user = userRepository.findById((long) userData.getUserid()).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        UserData userDataCheck = userDataRepository.findByUserid(userData.getUserid());
        if (userDataCheck != null){
            throw new RuntimeException("User data already exists!");
        }
        userDataRepository.save(userData);
        return userData.toString();
    }

    @Override
    public String getUserData(int user_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        UserData userDataCheck = userDataRepository.findByUserid(user_id);
        return userDataCheck.toString();
    }

    @Override
    public String updateUserData(UserData userData) {
        User user = userRepository.findById((long) userData.getUserid()).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        UserData userDataCheck = userDataRepository.findByUserid(userData.getUserid());
        if (userDataCheck == null){
            throw new RuntimeException("User data don't exists!");
        }
        userDataRepository.updateUserDataRegister(
                userData.getUserid(),
                userData.getWeight(),
                userData.getHeight(),
                userData.getAge());
        return userData.toString();
    }

    @Override
    public String deleteUserData(int user_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        UserData userDataCheck = userDataRepository.findByUserid(user_id);
        if (userDataCheck == null){
            throw new RuntimeException("User data don't exists!");
        }
        userDataRepository.delete(userDataCheck);
        return "User data deleted!";
    }
}
