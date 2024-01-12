package com.example.TaskPro.Repository;

import com.example.TaskPro.Models.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {
    Stage findByName(String name);
    boolean existsById(int id);
    Stage findById(int stageId);


}
