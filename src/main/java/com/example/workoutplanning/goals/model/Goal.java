package com.example.workoutplanning.goals.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "goals_data")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int user_id;
    @Column
    private int exercise_id;
    @Column
    private int units;
    @Column
    private int progress;
    @Column
    private LocalDate date_start;
    @Column
    private LocalDate date_end;
    @Column
    private boolean completed;

    public Goal() {
    }

    public Goal(int user_id, int exercise_id, int units, LocalDate date_start, LocalDate date_end) {
        this.user_id = user_id;
        this.exercise_id = exercise_id;
        this.units = units;
        this.progress = 0;
        this.date_start = date_start;
        this.date_end = date_end;
        this.completed = false;
    }

    public Goal(int id, int user_id, int exercise_id, int units, int progress, LocalDate date_start, LocalDate date_end, boolean completed) {
        this.id = id;
        this.user_id = user_id;
        this.exercise_id = exercise_id;
        this.units = units;
        this.progress = progress;
        this.date_start = date_start;
        this.date_end = date_end;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }
    public int getUser_id() {
        return user_id;
    }
    public int getExercise_id() {
        return exercise_id;
    }
    public int getUnits() {
        return units;
    }
    public int getProgress() {
        return progress;
    }
    public LocalDate getDate_start() {
        return date_start;
    }
    public LocalDate getDate_end() {
        return date_end;
    }
    public boolean isCompleted() {
        return completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setDate_start(LocalDate date_start) {
        this.date_start = date_start;
    }

    public void setDate_end(LocalDate date_end) {
        this.date_end = date_end;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String toString(){
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
