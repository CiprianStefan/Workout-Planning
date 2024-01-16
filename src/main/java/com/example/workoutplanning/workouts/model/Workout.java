package com.example.workoutplanning.workouts.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashMap;


@Entity
@Table(name = "workout_plans")
public class Workout {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int user_id;
    @Column
    private String exercises_and_units;

    public Workout() {
    }

    public Workout(int user_id, HashMap exercises_and_units) {
        this.user_id = user_id;
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.exercises_and_units = mapper.writeValueAsString(exercises_and_units);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Workout(int user_id, String exercises_and_units) {
        this.user_id = user_id;
        this.exercises_and_units = exercises_and_units;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setExercises_and_units(String exercises_and_units) {
        this.exercises_and_units = exercises_and_units;
    }

    public String getExercises_and_units() {
        return exercises_and_units;
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

