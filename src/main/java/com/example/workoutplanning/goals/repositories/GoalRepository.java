package com.example.workoutplanning.goals.repositories;

import com.example.workoutplanning.goals.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE goals_data SET exercise_id = ?3, units = ?4, date_start = ?5, date_end = ?6 WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    void updateGoalGeneralInformation(int goal_id, int user_id, int exercise_id, int units, LocalDate start_date, LocalDate end_date);

    @Modifying
    @Transactional
    @Query(value = "UPDATE goals_data AS gls SET progress = LEAST(COALESCE((SELECT SUM(actv.units) FROM activities_data AS actv WHERE actv.user_id = ?1 AND actv.exercise_id = ?2 AND CAST(timestamp AS date) BETWEEN gls.date_start AND gls.date_end),0), gls.units) WHERE gls.user_id = ?1 AND gls.exercise_id = ?2", nativeQuery = true)
    int updateGoalProgress(int user_id, int exercise_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE goals_data SET completed = CASE WHEN progress >= units THEN true ELSE false END WHERE user_id = ?1 AND exercise_id=?2", nativeQuery = true)
    int updateGoalCompletion(int user_id, int exercise_id);
}
