package com.example.workoutplanning.achievements.services;


public interface AchievementService {
    void initAchievementsDataForUser(int user_id);
    String getAchievementsDataByUser(int user_id);
    String getAchievementsInformation();
}
