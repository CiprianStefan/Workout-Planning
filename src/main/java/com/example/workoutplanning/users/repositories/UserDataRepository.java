package com.example.workoutplanning.users.repositories;

import com.example.workoutplanning.users.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long>{
    UserData findByUserid(int user_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users_data SET weight = ?2, height = ?3, age = ?4 WHERE user_id = ?1", nativeQuery = true)
    void updateUserDataRegister(int user_id, int weight, int height, int age);
}
