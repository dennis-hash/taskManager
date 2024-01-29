package com.example.TaskPro.Services;

import com.example.TaskPro.DTO.AssignTaskDTO;
import com.example.TaskPro.DTO.CreateTaskDTO;
import com.example.TaskPro.DTO.UpdateStageDTO;
import com.example.TaskPro.Exceptions.NotFoundException;
import com.example.TaskPro.Models.*;
import com.example.TaskPro.Repository.ProjectRepository;
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
import java.util.List;
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

    @Autowired
    ProjectRepository projectRepository;


        //create a task
    public Task createTask(CreateTaskDTO task, int userId, int projectId) {


        Task newTask = new Task();
        Optional<Stage> optionalStage = stageRepository.findById(task.getStageId());
        Stage stage = optionalStage.get();

        Optional<Project> optionalProject = projectRepository.findById(projectId);
        Project project = optionalProject.get();

        newTask.setCreatedBy(task.getCreatedBy());
        newTask.setDescription(task.getDescription());
        newTask.setPriority(task.getPriority());
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setStageId(stage);
        newTask.setProjectId(project);
        newTask.setDueDate(task.getDueDate());
        LocalDateTime localDateTime = LocalDateTime.now();
        newTask.setCreatedAt(localDateTime);
        newTask.setUpdatedAt(localDateTime);
        newTask.setCreatedBy(userId);

        taskRepository.save(newTask);

        AssignTaskDTO assignTaskDTO = new AssignTaskDTO();
        assignTaskDTO.setTaskId(newTask.getId());
        int[] userIds = {userId};
        assignTaskDTO.setAssigneeUserId(userIds);
        assignTask(assignTaskDTO);

        return newTask;

    }

    //assign task
    public void assignTask(AssignTaskDTO newAssignee) {
        Task task = taskRepository.findById(newAssignee.getTaskId());
        if (task == null) {
            throw new NotFoundException("Task  not found");
        }

        int[] assigneeUserIds = newAssignee.getAssigneeUserId();
        for (int j = 0; j < assigneeUserIds.length; j++) {
            int assigneeUserId = assigneeUserIds[j];
            Project project = task.getProjectId();
            List<UserEntity> projectMembers = project.getMembers();

            UserEntity user = userRepository.findById(assigneeUserId);
            if (projectMembers.contains(user)) {
                task.getAssignedUser().add(user);
                taskRepository.save(task);
            } else {
                throw new NotFoundException("User with ID " + assigneeUserId + " is not a member of the project");
            }

        }

    }

    //assign task
    public void undoTaskAssignment(AssignTaskDTO newAssignee) {
        Task task = taskRepository.findById(newAssignee.getTaskId());
        //UserEntity user = userRepository.findById(newAssignee.getAssigneeUserId());

        int[] assigneeUserIds = newAssignee.getAssigneeUserId();
        for (int j = 0; j < assigneeUserIds.length; j++) {
            UserEntity user = userRepository.findById(assigneeUserIds[j]);
            task.getAssignedUser().remove(user);
            taskRepository.save(task);
        }


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

    public Collection<Task> userTasks(int userId, int projectId){
        Optional<Project> pproject = projectRepository.findById(projectId);
        Project project = pproject.get();
        return taskRepository.findByAssignedUserIdAndProjectId(userId,project);
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
