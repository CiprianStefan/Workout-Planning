package com.example.workoutplanning.goals.services;

import com.example.workoutplanning.goals.model.Goal;
import com.example.workoutplanning.goals.repositories.GoalRepository;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

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
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        goalRepository.save(new Goal(user_id,exercise_id,units,date_start,date_end));
        return "Goal created!";
    }

    @Override
    public String getGoal(int goal_id, int user_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        Goal goal = goalRepository.findById((long) goal_id).orElse(null);
        if (goal == null){
            throw new RuntimeException("Goal not found!");
        }
        return goal.toString();
    }

    @Override
    public String getAllGoals(int user_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        return goalRepository.findAll().toString();
    }

    @Override
    public String updateGoalGeneralInformation(int goal_id, int user_id, int exercise_id, int units, LocalDate date_start, LocalDate date_end) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        Goal goal = goalRepository.findById((long) goal_id).orElse(null);
        if (goal == null){
            throw new RuntimeException("Goal not found!");
        }
        goalRepository.updateGoalGeneralInformation(goal_id,user_id,exercise_id,units,date_start,date_end);
        return "Goal updated!";
    }

    @Override
    public String deleteGoal(int goal_id, int user_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        Goal goal = goalRepository.findById((long) goal_id).orElse(null);
        if (goal == null){
            throw new RuntimeException("Goal not found!");
        }
        goalRepository.delete(goal);
        return "Goal deleted!";
    }
}
