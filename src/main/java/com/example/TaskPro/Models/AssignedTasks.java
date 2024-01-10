package com.example.TaskPro.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "assignedTasks")
public class AssignedTasks {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int pk;
    private int taskId;
    //ID of assignee

    private int id;

    public AssignedTasks(int taskId, int id) {
        this.taskId = taskId;
        this.id = id;
    }
}
