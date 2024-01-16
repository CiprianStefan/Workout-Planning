package com.example.workoutplanning.friends.repositories;

import com.example.workoutplanning.friends.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FriendsRepository extends JpaRepository<Friend, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM friends WHERE user_id = ?1 AND friend_id = ?2", nativeQuery = true)
    void deleteFriend(int user_id, int friend_id);


}
