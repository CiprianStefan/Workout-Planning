package com.example.workoutplanning.friends.controllers;

import com.example.workoutplanning.friends.services.FriendsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class FriendsController {

    FriendsService friendsService;
    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @PostMapping("/friends")
    public ResponseEntity<String> addFriend(@RequestHeader int user_id, @RequestBody HashMap<String, String> body){
        try{
            return ResponseEntity.ok(friendsService.addFriend(
                    user_id,
                    Integer.parseInt(body.get("friend_id"))
            ));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/friends")
    public ResponseEntity<String> getFriends(@RequestHeader int user_id){
        try{
            return ResponseEntity.ok(friendsService.getFriendsList(user_id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/friends")
    public ResponseEntity<String> removeFriend(@RequestHeader int user_id, @RequestBody HashMap<String, String> body){
        try{
            return ResponseEntity.ok(friendsService.removeFriend(
                    user_id,
                    Integer.parseInt(body.get("friend_id"))
            ));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
