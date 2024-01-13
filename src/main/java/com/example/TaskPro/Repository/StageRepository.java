package com.example.TaskPro.Repository;

import com.example.TaskPro.Models.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {
    Stage findByName(String name);
    boolean existsById(int id);
    Optional<Stage> findById(int stageId);

    @Query("SELECT s FROM Stage s LEFT JOIN FETCH s.tasks")
    List<Stage> findAllWithTasks();


}
