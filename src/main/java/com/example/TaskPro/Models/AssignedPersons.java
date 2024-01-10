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
@Table(name = "assignedPersons")
public class AssignedPersons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assignedPersonPk;
//    @Column(unique = true)
    private int taskId;
    //assignee ID
    private int id;
    private String assignedToName;
    private String assignedToEmail;
    //assigner ID
    private int assignerId;
    private LocalDateTime assignedAt;
}
