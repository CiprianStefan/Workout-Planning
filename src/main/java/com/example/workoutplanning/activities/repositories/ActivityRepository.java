package com.example.workoutplanning.activities.repositories;

import com.example.workoutplanning.activities.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>{

    @Modifying
    @Transactional
    @Query(value = "UPDATE activities_data SET exercise_id = ?3, units = ?4 WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    void updateActivity(int activity_id, int user_id, int exercise_id, int units);
}
