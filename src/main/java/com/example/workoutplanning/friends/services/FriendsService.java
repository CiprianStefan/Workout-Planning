package com.example.workoutplanning.friends.services;

public interface FriendsService {
    String addFriend(int user_id, int friend_id);
    String removeFriend(int user_id, int friend_id);
    String getFriendsList(int user_id);
}
