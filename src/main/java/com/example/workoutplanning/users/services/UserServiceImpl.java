package com.example.workoutplanning.users.services;

import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createUser(String Username, String Email, String Password){
        User userCheckUsername = userRepository.findByUsername(Username);
        if (userCheckUsername != null){
            throw new RuntimeException("User already exists!");
        }
        User userCheckEmail = userRepository.findByEmail(Email);
        if (userCheckEmail != null){
            throw new RuntimeException("Email already exists!");
        }
        User user = new User();
        user.setUsername(Username);
        user.setEmail(Email);
        user.setPassword(Password);
        userRepository.save(user);
        return "User created!";
    }

    @Override
    public Integer loginUser(String Username, String Password){
        User user = userRepository.findByUsername(Username);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        if(user.getPassword().equals(Password)){
            return user.getId();
        }
        throw new RuntimeException("Wrong password!");
    }
}
