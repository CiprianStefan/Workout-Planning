package com.example.workoutplanning.activities.services;

import com.example.workoutplanning.achievements.repositories.AchievementDataRepository;
import com.example.workoutplanning.achievements.repositories.AchievementRepository;
import com.example.workoutplanning.achievements.services.AchievementService;
import com.example.workoutplanning.activities.model.Activity;
import com.example.workoutplanning.activities.repositories.ActivityRepository;
import com.example.workoutplanning.goals.repositories.GoalRepository;
import com.example.workoutplanning.goals.services.GoalService;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.form.AbstractCheckedElementTag;

@Service
public class ActivityServiceImpl implements ActivityService{

    ActivityRepository activityRepository;
    UserRepository userRepository;
    GoalRepository goalRepository;
    AchievementDataRepository achievementDataRepository;
    public ActivityServiceImpl(ActivityRepository activityRepository, UserRepository userRepository, GoalRepository goalRepository, AchievementDataRepository achievementDataRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.goalRepository = goalRepository;
        this.achievementDataRepository = achievementDataRepository;
    }

    @Override
    public String createActivity(int user_id, int exercise_id, int units) {
        CheckUserAuthorization((long) user_id);
        Activity activity = new Activity(user_id,exercise_id,units);
        activityRepository.save(activity);
        int goalsUpdated = goalRepository.updateGoalProgress(user_id,exercise_id);
        int goalsCompleted = goalRepository.updateGoalCompletion(user_id,exercise_id);
        int achievementsUpdated = achievementDataRepository.updateAchievementsData(user_id,exercise_id);
        achievementDataRepository.updateAchievementCompetition(user_id);
        return "Activity created! \n Goals updated: " + goalsUpdated
                + "! \n Goals completed: " + goalsCompleted
                + "! \n Achievements updated: " + achievementsUpdated
                + "!";
    }

    @Override
    public String getActivity(int activity_id, int user_id) {
        CheckUserAuthorization((long) user_id);
        Activity activity = getActivity((long) activity_id);
        return activity.toString();
    }

    @Override
    public String getAllActivities(int user_id) {
        CheckUserAuthorization((long) user_id);
        return activityRepository.findAll().toString();
    }

    @Override
    public String updateActivity(int activity_id, int user_id, int exercise_id, int units) {
        CheckUserAuthorization((long) user_id);
        getActivity((long) activity_id);
        activityRepository.updateActivity(
                activity_id,
                user_id,
                exercise_id,
                units);
        int goalsUpdated = goalRepository.updateGoalProgress(user_id,exercise_id);
        int goalsCompleted = goalRepository.updateGoalCompletion(user_id,exercise_id);
        int achievementsUpdated = achievementDataRepository.updateAchievementsData(user_id,exercise_id);
        achievementDataRepository.updateAchievementCompetition(user_id);
        return "Activity updated! \n Goals updated: " + goalsUpdated
                + "! \n Goals completed: " + goalsCompleted
                + "! \n Achievements updated: " + achievementsUpdated
                + "!";
    }

    @Override
    public String deleteActivity(int activity_id, int user_id) {
        CheckUserAuthorization((long) user_id);
        Activity activity = getActivity((long) activity_id);
        activityRepository.deleteById((long) activity_id);
        int goalsUpdated = goalRepository.updateGoalProgress(user_id,activity.getExercise_id());
        int goalsCompleted = goalRepository.updateGoalCompletion(user_id,activity.getExercise_id());
        int achievementsUpdated = achievementDataRepository.updateAchievementsData(user_id,activity.getExercise_id());
        achievementDataRepository.updateAchievementCompetition(user_id);
        return "Activity deleted! \n Goals updated: " + goalsUpdated
                + "! \n Goals completed: " + goalsCompleted
                + "! \n Achievements updated: " + achievementsUpdated
                + "!";
    }

    private Activity getActivity(long activity_id) {
        Activity activity = activityRepository.findById(activity_id).orElse(null);
        if (activity == null){
            throw new RuntimeException("Activity not found!");
        }
        return activity;
    }

    private void CheckUserAuthorization(long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
    }
}
