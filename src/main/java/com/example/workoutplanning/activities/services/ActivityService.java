package com.example.workoutplanning.activities.services;


import com.example.workoutplanning.activities.model.Activity;

public interface ActivityService {
    String createActivity(int user_id, int exercise_id, int units);
    String getActivity(int activity_id, int user_id);
    String getAllActivities(int user_id);
    String updateActivity(int activity_id, int user_id, int exercise_id, int units);
    String deleteActivity(int activity_id, int user_id);
}
