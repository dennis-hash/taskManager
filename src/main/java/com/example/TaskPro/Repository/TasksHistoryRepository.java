package com.example.TaskPro.Repository;

import com.example.TaskPro.Models.TasksHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksHistoryRepository extends JpaRepository<TasksHistory, Integer> {
    public TasksHistory getTaskHistoryByTasksHistoryId(int tasksHistoryId);
}
