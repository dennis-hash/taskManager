package com.example.TaskPro.Services;

import com.example.TaskPro.Models.*;
import com.example.TaskPro.Repository.AssignedPersonRepository;
import com.example.TaskPro.Repository.AssignedTasksRepository;
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

    @Autowired
    AssignedTasksRepository assignedTasksRepository;

    @Autowired
    AssignedPersonRepository assignedPersonRepository;

    //create a task
    public Task createTask(Task task) {
        LocalDateTime localDateTime = LocalDateTime.now();
        task.setCreatedAt(localDateTime);



//        UserEntity user = userRepository.findById(task.getId()).orElse(null);
//        if(user != null) {
//            user.getTasks().add(task);
//            userRepository.save(user);
//        }
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
//        taskToUpdate.setId(task.getId());
//        taskToUpdate.setAssignedToName(task.getAssignedToName());
        taskToUpdate.setCreatedBy(task.getCreatedBy());
//        taskToUpdate.setStageId(task.getStageId());
        taskToUpdate.setPriority(task.getPriority());
        taskToUpdate.setTasksHistory(task.getTasksHistory());
//        taskToUpdate.setPriority(task.getPriority());
        taskToUpdate.setStage(task.getStage());
//        taskToUpdate.getAssignedPersons().add(task.getAssignedPersons())

//        UserEntity user = userRepository.findById(task);
//        List<Task> tasks = user.getTasks();
//        if(user != null) {
//             for(int i = 0; i < tasks.size(); i++){
//                 if(tasks.get(i).getTaskId() == task.getTaskId()){
//
//                     tasks.get(i).setTitle(task.getTitle());
//                     tasks.get(i).setDescription(task.getDescription());
//                     tasks.get(i).setCreatedAt(task.getCreatedAt());
//                     tasks.get(i).setUpdatedAt(currentDateAndTime);
//                     tasks.get(i).setDueDate(task.getDueDate());
////                     tasks.get(i).setId(task.getId());
////                     tasks.get(i).setAssignedToName(task.getAssignedToName());
//                     tasks.get(i).setCreatedBy(task.getCreatedBy());
////        taskToUpdate.setStageId(task.getStageId());
//                     tasks.get(i).setPriority(task.getPriority());
//                     tasks.get(i).setTasksHistory(task.getTasksHistory());
//
//                     user.setTasks(tasks);
//                     userRepository.save(user);
//                     break;
//                 }
//
//             }
//        }

        Task updatedTask = taskToUpdate;
        taskRepository.save(updatedTask);
        return updatedTask;
    }

    //assign task
    public AssignedPersons assignTask(AssignedPersons newAssignee) {
        System.out.println("ASSIGN TASK");
        LocalDateTime localDateTime = LocalDateTime.now();
        Task taskToAssign = taskRepository.findTaskByTaskId(newAssignee.getTaskId());
        UserEntity assignedUser = userRepository.findById(newAssignee.getId()).orElse(null);
        newAssignee.setAssignedAt(localDateTime);
        taskToAssign.getAssignedPersons().add(newAssignee);
        AssignedTasks task = new AssignedTasks(newAssignee.getTaskId(), newAssignee.getId());
        assignedUser.getAssignedTasks().add(task);

        assignedTasksRepository.save(task);
        userRepository.save(assignedUser);
//        userRepository.save(assignedUser);
        taskRepository.save(taskToAssign);
        assignedPersonRepository.save(newAssignee);
        return newAssignee;
    }

//    //update task history
//    public TasksHistory tasksHistory(Task task) {
//        Task taskToUpdate = taskRepository.findTaskByTaskId(task.getTaskId());
//        TasksHistory tasksHistoryToUpdate = taskToUpdate.getTasksHistory();
//
//    }

    //delete task
    public String deleteTask(int taskId) {

        taskRepository.deleteByTaskId(taskId);
        return "Task deleted successfully";
    }

    public String undoTaskAssignment(AssignedPersons assignee) {
        Task task = taskRepository.findTaskByTaskId(assignee.getTaskId());
        UserEntity user = userRepository.findById(assignee.getId()).orElse(null);
        List<AssignedTasks> assignedTasks = user.getAssignedTasks();
//        List<AssignedPersons> assignedPersons = task.getAssignedPersons();
        for(int i = 0; i < assignedTasks.size(); i++) {
            if(assignedTasks.get(i).getTaskId() == assignee.getTaskId()) {
                assignedTasks.remove(assignedTasks.get(i));

                user.setAssignedTasks(assignedTasks);

                userRepository.save(user);
                break;
            }

        }

        List<AssignedPersons> assignedPersons = task.getAssignedPersons();
        for(int i = 0; i < assignedPersons.size(); i++) {
            if(assignedPersons.get(i).getId() == assignee.getId()) {
                assignedPersons.remove(assignedPersons.get(i));
                task.setAssignedPersons(assignedPersons);
                taskRepository.save(task);
            }
        }



//        AssignedPersons assignedPerson = assignedPersonRepository.getById(assignee.getAssignedPersonPk());
        assignedPersonRepository.deleteById(assignee.getAssignedPersonPk());

        return "Task assignment undone";
    }

    //find task(s)

    public Task getTaskByTaskId(int taskId) {
        return taskRepository.findTaskByTaskId(taskId);

    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
