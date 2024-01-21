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
public interface TaskRepository extends JpaRepository<Task, Integer> {
     Task findById(int taskId);
     List<Task> findByStageId(Optional<Stage> stage);
     boolean existsById(int taskId);

     List<Task> findByCreatedBy(int userId);
     List<Task> findByAssignedUserIdAndProjectId(int userId, Project projectId);
     List<Task> findByAssignedUserId(int userId);
     @Query(value = "SELECT COUNT(*) FROM tasks WHERE project_id = :projectId", nativeQuery = true)
     long getTotalTasks(@Param("projectId") int projectId);

     // Total number of tasks added within the last seven days
     @Query(value = "SELECT COUNT(*) FROM tasks WHERE project_id = :projectId AND created_at >= CURRENT_DATE - INTERVAL '7 days'", nativeQuery = true)
     long getTasksAddedLastSevenDays(@Param("projectId") int projectId);

     // Total number of tasks where stage is not DONE
     @Query(value = "SELECT COUNT(*) FROM tasks WHERE project_id = :projectId AND stage_id <> 5", nativeQuery = true)
     long getTotalTasksNotDone(@Param("projectId") int projectId);

     // Total number of tasks where stage is not DONE and updated to DONE within the last seven days
     @Query(value = "SELECT COUNT(*) FROM tasks WHERE project_id = :projectId AND stage_id <> 5 AND updated_at >= CURRENT_DATE - INTERVAL '7 days'", nativeQuery = true)
     long getTasksNotDoneUpdatedToDoneLastSevenDays(@Param("projectId") int projectId);

     // Total number of tasks where stage is DONE
     @Query(value = "SELECT COUNT(*) FROM tasks WHERE project_id = :projectId AND stage_id = 5", nativeQuery = true)
     long getTotalTasksWithDoneStage(@Param("projectId") int projectId);

     // Total number of tasks where stage is DONE and they were updated to done within the last seven days
     @Query(value = "SELECT COUNT(*) FROM tasks WHERE project_id = :projectId AND stage_id = 5 AND updated_at >= CURRENT_DATE - INTERVAL '7 days'", nativeQuery = true)
     long getTasksUpdatedToDoneLastSevenDays(@Param("projectId") int projectId);

     // Retrieve tasks added within the last seven days
     @Query(value = "SELECT * FROM tasks WHERE project_id = :projectId AND created_at >= CURRENT_DATE - INTERVAL '7 days'", nativeQuery = true)
     List<Task> findTasksAddedLastSevenDays(@Param("projectId") int projectId);

     // Priority of all tasks added today (Task title and priority)
     @Query(value = "SELECT title, priority FROM tasks WHERE project_id = :projectId AND DATE(created_at) = CURRENT_DATE", nativeQuery = true)
     List<Object[]> getPriorityOfTasksAddedToday(@Param("projectId") int projectId);






}
