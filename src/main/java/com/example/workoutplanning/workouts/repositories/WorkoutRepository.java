package com.example.workoutplanning.workouts.repositories;

import com.example.workoutplanning.workouts.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE workout_plans SET exercises_and_units = ?3 WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    void updateWorkout(int workout_id, int user_id, String exercises);
}
