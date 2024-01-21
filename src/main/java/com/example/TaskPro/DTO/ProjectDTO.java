package com.example.TaskPro.DTO;

import lombok.Data;

@Data
public class ProjectDTO {
    private Integer id;
    private String title;
    private String description;

    public ProjectDTO(
            Integer id,
            String title,
            String description
    ){
        this.id = id;
        this.title =title;
        this.description = description;
    }
}
