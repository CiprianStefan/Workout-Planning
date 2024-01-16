package com.example.workoutplanning.friends.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.*;

@Entity
@Table(name = "friends")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int user_id;
    @Column
    private int friend_id;

    public Friend() {
    }

    public Friend(int user_id, int friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    public Friend(int id, int user_id, int friend_id) {
        this.id = id;
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
