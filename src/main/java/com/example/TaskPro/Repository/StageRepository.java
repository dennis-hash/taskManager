package com.example.TaskPro.Repository;

import com.example.TaskPro.Models.Project;
import com.example.TaskPro.Models.Stage;
import com.example.TaskPro.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {
    Stage findByName(String name);
    Stage findByIdAndProjectId(int stageId, Project projectId);
    boolean existsById(int id);
    Optional<Stage> findById(int stageId);

    List<Stage> findByProjectId(Optional<Project> project);

    @Query("SELECT s FROM Stage s LEFT JOIN FETCH s.tasks WHERE s.projectId.id = :projectId")
    List<Stage> findAllWithTasks(@Param("projectId") int projectId);


}
