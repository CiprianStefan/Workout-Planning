package com.example.workoutplanning.workouts.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public String toString() {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

