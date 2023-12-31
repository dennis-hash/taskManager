package com.example.TaskPro.Services;

import com.example.TaskPro.Models.TasksHistory;
import com.example.TaskPro.Repository.TasksHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskHistoryService {
    @Autowired
    TasksHistoryRepository tasksHistoryRepository;

    //Get the history for all tasks

    public List<TasksHistory> getAllTasksHistory() {
        return tasksHistoryRepository.findAll();
    }

    //Get task history by id
    public TasksHistory getTaskHistoryByTasksHistoryId(int tasksHistoryId){
        return tasksHistoryRepository.getTaskHistoryByTasksHistoryId(tasksHistoryId);
    }

    //Update task history
    public TasksHistory updateTasksHistory(TasksHistory tasksHistory) {
        TasksHistory taskToUpdate = tasksHistoryRepository.getTaskHistoryByTasksHistoryId(tasksHistory.getTasksHistoryId());
        LocalDateTime localDateTime = LocalDateTime.now();
        taskToUpdate.setPreviousStage(tasksHistory.getPreviousStage());
        taskToUpdate.setNewStage(tasksHistory.getNewStage());
        taskToUpdate.setLocalDateTime(localDateTime);
        taskToUpdate.setUpdatedBy(tasksHistory.getUpdatedBy());

        TasksHistory updatedTaskHistory = taskToUpdate;
        tasksHistoryRepository.save(updatedTaskHistory);
        return updatedTaskHistory;
    }
}
