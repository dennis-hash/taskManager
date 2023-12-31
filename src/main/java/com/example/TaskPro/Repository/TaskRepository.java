package com.example.TaskPro.Repository;

import com.example.TaskPro.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task findTaskByTaskId(int taskId);
//    List<Task> getAllTasks();
}
