package com.example.workoutplanning.achievements.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.*;

@Entity
@Table(name = "achievements_data")
public class AchievementData {

    @Id
    @Column
    private int id;
    @Column
    private int user_id;
    @Column
    private int achievement_id;
    @Column
    private int progress;
    @Column
    private boolean completed;

    public AchievementData() {
    }

    public AchievementData(int id, int user_id, int achievement_id, int progress, boolean completed) {
        this.id = id;
        this.user_id = user_id;
        this.achievement_id = achievement_id;
        this.progress = progress;
        this.completed = completed;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAchievement_id() {
        return achievement_id;
    }

    public void setAchievement_id(int achievement_id) {
        this.achievement_id = achievement_id;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "Error";
        }
        //return "id: " + id + ", user_id: " + user_id + ", achievement_id: " + achievement_id + ", progress: " + progress + ", completed: " + completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
