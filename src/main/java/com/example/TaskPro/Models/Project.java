package com.example.TaskPro.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @JsonIgnore
    private Integer createdBy;

    @OneToMany(mappedBy = "projectId")
    private List<Stage> stages;

    //members
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_members", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<UserEntity> members = new ArrayList<>();


}
