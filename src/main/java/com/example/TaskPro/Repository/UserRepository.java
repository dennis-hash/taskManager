package com.example.TaskPro.Repository;

import com.example.TaskPro.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    //Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

    UserEntity findById(int id);
    Boolean existsByEmail(String email);
    //UserEntity findById(int id);
    Boolean existsById(int id);

    List<UserEntity> findAll();

    @Query(value = "SELECT COUNT(DISTINCT user_id) FROM user_tasks", nativeQuery = true)
    long getTotalUsersAssignedToTasks();


    @Query(value = "SELECT COUNT(DISTINCT user_id) FROM user_tasks WHERE task_id IN (SELECT id FROM tasks WHERE created_at >= CURRENT_DATE - INTERVAL '7 days')", nativeQuery = true)
    long getUsersAssignedToTasksLastSevenDays();
}
