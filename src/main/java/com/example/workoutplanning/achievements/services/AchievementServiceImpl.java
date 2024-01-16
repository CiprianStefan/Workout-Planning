package com.example.workoutplanning.achievements.services;

import com.example.workoutplanning.achievements.model.Achievement;
import com.example.workoutplanning.achievements.model.AchievementData;
import com.example.workoutplanning.achievements.repositories.AchievementDataRepository;
import com.example.workoutplanning.achievements.repositories.AchievementRepository;
import com.example.workoutplanning.users.model.User;
import com.example.workoutplanning.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementServiceImpl implements AchievementService {

    AchievementDataRepository achievementDataRepository;
    AchievementRepository achievementRepository;
    UserRepository userRepository;
    public AchievementServiceImpl(AchievementDataRepository achievementDataRepository, AchievementRepository achievementRepository, UserRepository userRepository) {
        this.achievementDataRepository = achievementDataRepository;
        this.achievementRepository = achievementRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void initAchievementsDataForUser(int user_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        List<Achievement> achievements = achievementRepository.findAll();
        if (achievements == null){
            throw new RuntimeException("Achievements not found!");
        }
        achievementDataRepository.initAchievementsDataForUser(user_id);

    }

    @Override
    public String getAchievementsDataByUser(int user_id) {
        User user = userRepository.findById((long) user_id).orElse(null);
        if (user == null){
            throw new RuntimeException("User not found!");
        }
        List<AchievementData> achievementsData = achievementDataRepository.findAllByUserID(user_id);
        if (achievementsData == null){
            throw new RuntimeException("Achievements not found!");
        }
        return achievementsData.toString();
    }

    @Override
    public String getAchievementsInformation() {
        List<Achievement> achievements = achievementRepository.findAll();
        if (achievements == null){
            throw new RuntimeException("Achievements not found!");
        }
        return achievements.toString();
    }


}
