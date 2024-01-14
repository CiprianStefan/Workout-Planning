package com.example.workoutplanning.goals.services;


import java.time.LocalDate;
import java.util.Date;

public interface GoalService {
    String createGoal(int user_id, int exercise_id, int units, LocalDate date_start, LocalDate date_end);
    String getGoal(int goal_id, int user_id);
    String getAllGoals(int user_id);
    String updateGoalGeneralInformation(int goal_id, int user_id, int exercise_id, int units, LocalDate date_start, LocalDate date_end);
    String deleteGoal(int goal_id, int user_id);
}
