package com.example.TaskPro.Repository;

import com.example.TaskPro.Models.AssignedTasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignedTasksRepository extends JpaRepository<AssignedTasks, Integer> {
}
