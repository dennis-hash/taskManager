package com.example.TaskPro.Controllers;

import com.example.TaskPro.Models.AssignedPersons;
import com.example.TaskPro.Models.Task;
import com.example.TaskPro.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskService taskService;

    //create new task
    @PostMapping("/createTask")
    public Task createNewTask(@RequestBody Task task){
        return taskService.createTask(task);
    }

    @PostMapping("/assignTask")
    public AssignedPersons assignTask(@RequestBody AssignedPersons newAssignee) {return taskService.assignTask(newAssignee);}
    //update task

    //assign task
    @PutMapping("/updateTask")
    public Task updateTask(@RequestBody Task task){
        return taskService.updateTask(task);
    }

    //undo task assignment
    @DeleteMapping("/undoAssignment")
    public String undoTaskAssignment(@RequestBody AssignedPersons assignee) {return taskService.undoTaskAssignment(assignee);}

    //delete task
    @DeleteMapping("/deleteTask{taskId}")
    public String deleteTask(@PathVariable int taskId){
        return taskService.deleteTask(taskId);
    }

    //getTaskById
    @GetMapping("/getTaskByTaskId/{taskId}")
    public Task getTaskByTaskId(@PathVariable int taskId){
        return taskService.getTaskByTaskId(taskId);
    }

    //get all tasks
    @GetMapping("/getAllTasks/")
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }
}
