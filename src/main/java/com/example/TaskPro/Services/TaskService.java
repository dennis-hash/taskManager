package com.example.TaskPro.Services;

import com.example.TaskPro.Models.Task;
import com.example.TaskPro.Models.TasksHistory;
import com.example.TaskPro.Models.UserEntity;
import com.example.TaskPro.Repository.TaskRepository;
import com.example.TaskPro.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;

    //create a task
    public Task createTask(Task task) {
        LocalDateTime localDateTime = LocalDateTime.now();
        task.setCreatedAt(localDateTime);

        UserEntity user = userRepository.findById(task.getId()).orElse(null);
        if(user != null) {
            user.getTasks().add(task);
            userRepository.save(user);
        }
        taskRepository.save(task);
        return task;
    }

    //update task
    public Task updateTask(Task task) {
        Task taskToUpdate = taskRepository.findTaskByTaskId(task.getTaskId());
        LocalDateTime currentDateAndTime = LocalDateTime.now();

        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setCreatedAt(task.getCreatedAt());
        taskToUpdate.setUpdatedAt(currentDateAndTime);
        taskToUpdate.setDueDate(task.getDueDate());
        taskToUpdate.setId(task.getId());
        taskToUpdate.setAssignedToName(task.getAssignedToName());
        taskToUpdate.setCreatedBy(task.getCreatedBy());
//        taskToUpdate.setStageId(task.getStageId());
        taskToUpdate.setPriority(task.getPriority());
        taskToUpdate.setTasksHistory(task.getTasksHistory());

        UserEntity user = userRepository.findById(task.getId()).orElse(null);
        List<Task> tasks = user.getTasks();
        if(user != null) {
             for(int i = 0; i < tasks.size(); i++){
                 if(tasks.get(i).getTaskId() == task.getTaskId()){

                     tasks.get(i).setTitle(task.getTitle());
                     tasks.get(i).setDescription(task.getDescription());
                     tasks.get(i).setCreatedAt(task.getCreatedAt());
                     tasks.get(i).setUpdatedAt(currentDateAndTime);
                     tasks.get(i).setDueDate(task.getDueDate());
                     tasks.get(i).setId(task.getId());
                     tasks.get(i).setAssignedToName(task.getAssignedToName());
                     tasks.get(i).setCreatedBy(task.getCreatedBy());
//        taskToUpdate.setStageId(task.getStageId());
                     tasks.get(i).setPriority(task.getPriority());
                     tasks.get(i).setTasksHistory(task.getTasksHistory());

                     user.setTasks(tasks);
                     userRepository.save(user);
                     break;
                 }

             }
        }

        Task updatedTask = taskToUpdate;
        taskRepository.save(updatedTask);
        return updatedTask;
    }

//    //update task history
//    public TasksHistory tasksHistory(Task task) {
//        Task taskToUpdate = taskRepository.findTaskByTaskId(task.getTaskId());
//        TasksHistory tasksHistoryToUpdate = taskToUpdate.getTasksHistory();
//
//    }

    //delete task
    public String deleteTask(Task task) {
        Task taskToDelete = taskRepository.findTaskByTaskId(task.getTaskId());
        taskRepository.deleteById(task.getId());
        return "Task deleted successfully";
    }

    //find task(s)

    public Task getTaskByTaskId(int taskId) {
        return taskRepository.findTaskByTaskId(taskId);

    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
