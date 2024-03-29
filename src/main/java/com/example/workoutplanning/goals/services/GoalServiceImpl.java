package com.example.workoutplanning.goals.services;

import com.example.workoutplanning.goals.model.Goal;
import com.example.workoutplanning.goals.repositories.GoalRepository;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GoalServiceImpl implements GoalService{

    GoalRepository goalRepository;
    UserRepository userRepository;
    public GoalServiceImpl(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String createGoal(int user_id, int exercise_id, int units, LocalDate date_start, LocalDate date_end) {
        CheckUserAuthorization((long) user_id);
        goalRepository.save(new Goal(user_id,exercise_id,units,date_start,date_end));
        return "Goal created!";
    }

    @Override
    public String getGoal(int goal_id, int user_id) {
        CheckUserAuthorization((long) user_id);
        Goal goal = checkAndGetGoal((long) goal_id);
        return goal.toString();
    }

    @Override
    public String getAllGoals(int user_id) {
        CheckUserAuthorization((long) user_id);
        List<Goal> goals = goalRepository.findAll();
        if (goals.isEmpty()){
            throw new RuntimeException("No goals found!");
        }
        goals.removeIf(goal -> goal.getUser_id() != user_id);
        return goals.toString();
    }

    @Override
    public String updateGoalGeneralInformation(int goal_id, int user_id, int exercise_id, int units, LocalDate date_start, LocalDate date_end) {
        CheckUserAuthorization((long) user_id);
        Goal goal = checkAndGetGoal((long) goal_id);
        if (goal.getUser_id() != user_id){
            throw new RuntimeException("User not authorized!");
        }
        goalRepository.updateGoalGeneralInformation(goal_id,user_id,exercise_id,units,date_start,date_end);
        return "Goal updated!";
    }

    @Override
    public String deleteGoal(int goal_id, int user_id) {
        CheckUserAuthorization((long) user_id);
        Goal goal = checkAndGetGoal((long) goal_id);
        if (goal.getUser_id() != user_id){
            throw new RuntimeException("User not authorized!");
        }
        goalRepository.delete(goal);
        return "Goal deleted!";
    }

    public void CheckUserAuthorization(long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
    }

    public Goal checkAndGetGoal(long goal_id) {
        Goal goal = goalRepository.findById(goal_id).orElse(null);
        if (goal == null || goal.getId() != goal_id){
            throw new RuntimeException("Goal not found!");
        }
        return goal;
    }
}
