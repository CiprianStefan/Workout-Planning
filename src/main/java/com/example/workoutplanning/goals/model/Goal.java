package com.example.workoutplanning.goals.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public Goal(int user_id, int exercise_id, int units, LocalDate date_start, LocalDate date_end) {
        this.user_id = user_id;
        this.exercise_id = exercise_id;
        this.units = units;
        this.progress = 0;
        this.date_start = date_start;
        this.date_end = date_end;
        this.completed = false;
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
