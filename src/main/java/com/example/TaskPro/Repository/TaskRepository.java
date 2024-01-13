package com.example.TaskPro.Repository;

import com.example.TaskPro.Models.Stage;
import com.example.TaskPro.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
     Task findById(int taskId);
     List<Task> findByStageId(Optional<Stage> stage);
     boolean existsById(int taskId);
     List<Task> findByAssignedUserId(int userId);

     Task deleteById(int taskId);
     // Total number of all tasks
     @Query(value = "SELECT COUNT(*) FROM tasks", nativeQuery = true)
     long getTotalTasks();

     // Total number of tasks added within the last seven days
     @Query(value = "SELECT COUNT(*) FROM tasks WHERE created_at >= CURRENT_DATE - INTERVAL '7 days'", nativeQuery = true)
     long getTasksAddedLastSevenDays();

     // Total number of tasks where stage is not DONE
     @Query(value = "SELECT COUNT(*) FROM tasks WHERE stage_id <> 5", nativeQuery = true)
     long getTotalTasksNotDone();

     // Total number of tasks where stage is not DONE and updated to DONE within the last seven days
     @Query(value = "SELECT COUNT(*) FROM tasks WHERE stage_id <> 5 AND updated_at >= CURRENT_DATE - INTERVAL '7 days'", nativeQuery = true)
     long getTasksNotDoneUpdatedToDoneLastSevenDays();

     // Total number of tasks where stage is DONE
     @Query(value = "SELECT COUNT(*) FROM tasks WHERE stage_id = 5", nativeQuery = true)
     long getTotalTasksWithDoneStage();

     // Total number of tasks where stage is DONE and they were updated to done within the last seven days
     @Query(value = "SELECT COUNT(*) FROM tasks WHERE stage_id = 5 AND updated_at >= CURRENT_DATE - INTERVAL '7 days'", nativeQuery = true)
     long getTasksUpdatedToDoneLastSevenDays();

     // Retrieve tasks added within the last seven days
     @Query(value = "SELECT * FROM tasks WHERE created_at >= CURRENT_DATE - INTERVAL '7 days'", nativeQuery = true)
     List<Task> findTasksAddedLastSevenDays();

     // Priority of all tasks added today (Task title and priority)
     @Query(value = "SELECT title, priority FROM tasks WHERE DATE(created_at) = CURRENT_DATE", nativeQuery = true)
     List<Object[]> getPriorityOfTasksAddedToday();






}
