package com.example.TaskPro.Services;

import com.example.TaskPro.DTO.AssignTaskDTO;
import com.example.TaskPro.DTO.CreateTaskDTO;
import com.example.TaskPro.DTO.UpdateStageDTO;
import com.example.TaskPro.Exceptions.NotFoundException;
import com.example.TaskPro.Models.*;
import com.example.TaskPro.Repository.StageRepository;
import com.example.TaskPro.Repository.TaskRepository;
import com.example.TaskPro.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


@Service
@Slf4j
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    StageRepository stageRepository;


        //create a task
    public Task createTask(CreateTaskDTO task, int userId) {


        Task newTask = new Task();
        Optional<Stage> optionalStage = stageRepository.findById(task.getStageId());
        Stage stage = optionalStage.get();

        newTask.setCreatedBy(task.getCreatedBy());
        newTask.setDescription(task.getDescription());
        newTask.setPriority(task.getPriority());
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setStageId(stage);
        newTask.setDueDate(task.getDueDate());
        LocalDateTime localDateTime = LocalDateTime.now();
        newTask.setCreatedAt(localDateTime);
        newTask.setUpdatedAt(localDateTime);
        newTask.setCreatedBy(userId);

        log.info("saving new task:{}",task.getTitle());
        return taskRepository.save(newTask);
    }

    //assign task
    public void assignTask(AssignTaskDTO newAssignee) {
        Task task = taskRepository.findById(newAssignee.getTaskId());

        if(task == null){
            throw new NotFoundException("Task not found");
        }
        UserEntity user = userRepository.findById(newAssignee.getAssigneeUserId());

        task.getAssignedUser().add(user);
        taskRepository.save(task);
    }

    //assign task
    public void undoTaskAssignment(AssignTaskDTO newAssignee) {
        Task task = taskRepository.findById(newAssignee.getTaskId());
        UserEntity user = userRepository.findById(newAssignee.getAssigneeUserId());

        task.getAssignedUser().remove(user);
        taskRepository.save(task);
    }

    //update task
    public Task updateTask(CreateTaskDTO task, int taskId) {
        Task taskToUpdate = taskRepository.findById(taskId);
        Optional<Stage> sstage = stageRepository.findById(task.getStageId());
        Stage stage = sstage.get();


        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setUpdatedAt(LocalDateTime.now());
        taskToUpdate.setDueDate(task.getDueDate());
        taskToUpdate.setCreatedBy(task.getCreatedBy());
        taskToUpdate.setPriority(task.getPriority());
        taskToUpdate.setStageId(stage);


        return taskRepository.save(taskToUpdate);

    }

    public Task getTaskByTaskId(int taskId) {
        return taskRepository.findById(taskId);
    }

        //delete task
    public String deleteTask(int taskId) {
        Task taskToDelete = taskRepository.findById(taskId);
        taskToDelete.setStageId(null);
        taskToDelete.setCreatedBy(null);
        taskRepository.save(taskToDelete);
        taskRepository.deleteById(taskId);
        return "Task deleted successfully";
    }

    public Collection<Task> userTasks(int userId){

        return taskRepository.findByAssignedUserId(userId);
    }


    public Task updateTaskStage(UpdateStageDTO updateStageDTO) {
        Task task = taskRepository.findById(updateStageDTO.getTaskId());


        Optional<Stage> sstage = stageRepository.findById(updateStageDTO.getStageId());
        Stage stage = sstage.get();

        // Chec if the stage exists or create a new one
        if (stage == null) {
            stage = new Stage();
            stage.setId(updateStageDTO.getStageId());
            stage = stageRepository.save(stage);
        }

        task.setStageId(stage);

        return taskRepository.save(task);
    }



}
