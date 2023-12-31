package com.example.TaskPro.Controllers;

import com.example.TaskPro.Models.Task;
import com.example.TaskPro.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    //create new task
    @PostMapping("/createTask")
    public Task createNewTask(@RequestBody Task task){
        return taskService.createTask(task);
    }

    //update task
    @PutMapping("/updateTask")
    public Task updateTask(@RequestBody Task task){
        return taskService.updateTask(task);
    }

    //delete task
    @DeleteMapping("deleteTask")
    public String deleteTask(@RequestBody Task task){
        return taskService.deleteTask(task);
    }

    //getTaskById
    @GetMapping("getTaskByTaskId/{taskId}")
    public Task getTaskByTaskId(@PathVariable int taskId){
        return taskService.getTaskByTaskId(taskId);
    }

    //get all tasks
    @GetMapping("getAllTasks/")
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }
}
