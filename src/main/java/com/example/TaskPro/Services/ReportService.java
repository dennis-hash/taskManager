package com.example.TaskPro.Services;

import com.example.TaskPro.DTO.CreateTaskDTO;
import com.example.TaskPro.DTO.PriorityOfTaskAddedToday;
import com.example.TaskPro.DTO.TaskReportDTO;
import com.example.TaskPro.Models.Task;
import com.example.TaskPro.Repository.TaskRepository;
import com.example.TaskPro.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public TaskReportDTO generateTaskReport() {
        long totalTasks = taskRepository.getTotalTasks();
        long tasksAddedLastSevenDays = taskRepository.getTasksAddedLastSevenDays();
        long totalTasksNotDone = taskRepository.getTotalTasksNotDone();
        long tasksNotDoneUpdatedToDoneLastSevenDays = taskRepository.getTasksNotDoneUpdatedToDoneLastSevenDays();
        long totalTasksWithDoneStage = taskRepository.getTotalTasksWithDoneStage();
        long tasksUpdatedToDoneLastSevenDays = taskRepository.getTasksUpdatedToDoneLastSevenDays();
        long totalUsersAssignedToTasks = userRepository.getTotalUsersAssignedToTasks();
        long usersAssignedToTasksLastSevenDays = userRepository.getUsersAssignedToTasksLastSevenDays();
        List<Task> tasksAddedLastSevenDaysDetails = taskRepository.findTasksAddedLastSevenDays();
        List<Object[]> priorityOfTasksAddedToday = taskRepository.getPriorityOfTasksAddedToday();

        return new TaskReportDTO(
                totalTasks,
                tasksAddedLastSevenDays,
                totalTasksNotDone,
                tasksNotDoneUpdatedToDoneLastSevenDays,
                totalTasksWithDoneStage,
                tasksUpdatedToDoneLastSevenDays,
                totalUsersAssignedToTasks,
                usersAssignedToTasksLastSevenDays,
                tasksAddedLastSevenDaysDetails,
                priorityOfTasksAddedToday
        );
    }
}
