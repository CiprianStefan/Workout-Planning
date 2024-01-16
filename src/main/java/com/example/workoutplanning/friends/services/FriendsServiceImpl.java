package com.example.workoutplanning.friends.services;

import com.example.workoutplanning.friends.model.Friend;
import com.example.workoutplanning.friends.repositories.FriendsRepository;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsServiceImpl implements FriendsService{
    UserRepository userRepository;
    FriendsRepository friendsRepository;
    public FriendsServiceImpl(UserRepository userRepository, FriendsRepository friendsRepository) {
        this.userRepository = userRepository;
        this.friendsRepository = friendsRepository;
    }
    @Override
    public String addFriend(int user_id, int friend_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        User friend = userRepository.findById((long) friend_id).orElse(null);
        if (friend == null){
            throw new RuntimeException("Friend not found!");
        }
        List<Friend> friends = friendsRepository.findAll();
        for (Friend f : friends){
            if (f.getUser_id() == user_id && f.getFriend_id() == friend_id){
                throw new RuntimeException("Friend already added!");
            }
        }
        Friend newFriend = new Friend(user_id,friend_id);
        friendsRepository.save(newFriend);
        return "Friend added!";
    }

    @Override
    public String removeFriend(int user_id, int friend_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        User friend = userRepository.findById((long) friend_id).orElse(null);
        if (friend == null){
            throw new RuntimeException("Friend not found!");
        }
        friendsRepository.deleteFriend(user_id,friend_id);
        return "Friend deleted!";
    }

    @Override
    public String getFriendsList(int user_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        return friendsRepository.findAll().toString();
    }
}
