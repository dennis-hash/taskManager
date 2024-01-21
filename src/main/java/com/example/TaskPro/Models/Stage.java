package com.example.TaskPro.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "stage")
public class Stage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String name;
    private Integer createdBy;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project projectId;

    @OneToMany(mappedBy = "stageId")
    //@JsonIgnore
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();
}
