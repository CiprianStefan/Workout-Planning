package com.example.workoutplanning.achievements.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "achievements")
public class Achievement {

    @Id
    private int id;
    @Column
    private String name;
    @Column
    private int exercise_id;
    @Column
    private int units;

    public Achievement() {
    }

    public Achievement(int id, String name, int exercise_id, int units) {
        this.id = id;
        this.name = name;
        this.exercise_id = exercise_id;
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String toString(){
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "Error";
        }
    }
}
