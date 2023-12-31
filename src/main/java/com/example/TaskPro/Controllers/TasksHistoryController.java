package com.example.TaskPro.Controllers;

import com.example.TaskPro.Models.TasksHistory;
import com.example.TaskPro.Services.TaskHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TasksHistoryController {
    @Autowired
    private TaskHistoryService taskHistoryService;

    //get all tasks history
    @GetMapping("getAllTasksHistory/")
    public List<TasksHistory> getAllTasksHistory(){
        return taskHistoryService.getAllTasksHistory();
    }

    //get task history by ID
    @GetMapping("getHistoryOfIndividualTask/{tasksHistoryId}/")
    public TasksHistory tasksHistory(@RequestBody int tasksHistoryId){
        return taskHistoryService.getTaskHistoryByTasksHistoryId(tasksHistoryId);
    }

    //update the history of a task
    @PutMapping("updateTaskHistory")
    public TasksHistory updateTaskHistory(@RequestBody TasksHistory tasksHistory){
        return taskHistoryService.updateTasksHistory(tasksHistory);
    }
}
