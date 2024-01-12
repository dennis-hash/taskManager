package com.example.TaskPro.DTO;

import lombok.Data;

@Data
public class CreateTaskDTO {
    private String title;
    private String description;
    private String dueDate;
    private Integer createdBy;
    private String priority;
    private Integer stageId;
}
