package com.example.TaskPro.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue
    private int taskId;

    private String title;
    private String description;
    private LocalDateTime createdAt;
    //time task details are updated
    private LocalDateTime updatedAt;
    private String dueDate;
    //userId - ID of the person doing the task
    private int id;
    private String assignedToName;
    private int createdBy;
//    private String stageId;
    private String priority;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_task_id", referencedColumnName = "taskId")
    private List<TasksHistory> tasksHistory;


}
