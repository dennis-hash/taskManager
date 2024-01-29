package com.example.TaskPro.DTO;

import com.example.TaskPro.Models.Stage;
import com.example.TaskPro.Models.Task;
import lombok.Data;

import java.util.List;

@Data
public class StageWithTask {
    private Stage stage;
    private List<Task> tasks;

    public StageWithTask(Stage stage, List<Task> tasks) {
        this.stage = stage;
        this.tasks = tasks;
    }

}
