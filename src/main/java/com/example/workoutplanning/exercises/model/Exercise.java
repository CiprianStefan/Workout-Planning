package com.example.workoutplanning.exercises.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.*;

@Entity
@Table(name = "exercises_data")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private float calories_burned_per_unit;

    public Exercise() {
    }

    public Exercise(int id, String name, String type, float calories_burned_per_unit) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.calories_burned_per_unit = calories_burned_per_unit;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public float getCalories_burned_per_unit() {
        return calories_burned_per_unit;
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
