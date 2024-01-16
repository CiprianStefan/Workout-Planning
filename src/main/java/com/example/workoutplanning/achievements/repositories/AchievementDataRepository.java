package com.example.workoutplanning.achievements.repositories;

import com.example.workoutplanning.achievements.model.AchievementData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AchievementDataRepository extends JpaRepository<AchievementData, Long> {

    @Modifying
    @Transactional
    @Query(value = "SELECT id, user_id, achievement_id, progress, completed FROM achievements_data WHERE user_id = ?1", nativeQuery = true)
    List<AchievementData> findAllByUserID(int user_id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO achievements_data (user_id, achievement_id, progress, completed) SELECT ?1, ach.id, 0, false FROM achievements ach", nativeQuery = true)
    int initAchievementsDataForUser(int user_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE achievements_data achd SET progress = LEAST(COALESCE((SELECT SUM(act.units) FROM activities_data act WHERE exercise_id = ?2), 0),(SELECT units FROM achievements ach WHERE ach.id = achd.achievement_id)) WHERE achd.user_id = ?1 AND achd.achievement_id IN (SELECT ach.id FROM achievements ach where exercise_id = ?2)", nativeQuery = true)
    int updateAchievementsData(int user_id, int exercise_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE achievements_data achd SET completed = (achd.progress = (SELECT ach.units FROM achievements ach WHERE ach.id = achd.achievement_id)) WHERE user_id = ?1", nativeQuery = true)
    void updateAchievementCompetition(int user_id);
}
