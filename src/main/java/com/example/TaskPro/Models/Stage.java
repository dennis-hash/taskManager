package com.example.TaskPro.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stage")
public class Stage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String projectStage;
}
