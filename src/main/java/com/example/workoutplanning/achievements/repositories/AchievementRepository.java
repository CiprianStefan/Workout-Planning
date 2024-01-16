package com.example.workoutplanning.achievements.repositories;

import com.example.workoutplanning.achievements.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}
