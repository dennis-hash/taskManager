package com.example.TaskPro.DTO;

import lombok.Data;


@Data
public class AddMemberToProject {
    private int projectId;
    private int[] assigneeUserId;
}
