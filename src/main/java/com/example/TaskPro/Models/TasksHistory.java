package com.example.TaskPro.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasksHistory",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"taskId"})})
public class TasksHistory {
    @Id
    @GeneratedValue
    private int tasksHistoryId;

    private int taskId;
    private int previousStage;
    private String newStage;
    private LocalDateTime localDateTime;
    private int updatedBy;
}
