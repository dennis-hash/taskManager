package com.example.TaskPro.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Title cannot be empty")
    private String title;
    private String description;
    private String dueDate;
    private Integer createdBy;
    private String priority;


    @ManyToOne
    @JoinColumn(name = "stage_id")
    @JsonBackReference
    private Stage stageId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    @JsonIgnore
    private Project projectId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_tasks", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<UserEntity> assignedUser = new ArrayList<>();


    @OneToMany(mappedBy = "task")
    @JsonManagedReference
    @JsonIgnore
    private List<TasksHistory> tasksHistory;










}
