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

    public TaskReportDTO generateTaskReport(int projectId) {

        long totalTasks = taskRepository.getTotalTasks(projectId);
        long tasksAddedLastSevenDays = taskRepository.getTasksAddedLastSevenDays(projectId);
        long totalTasksNotDone = taskRepository.getTotalTasksNotDone(projectId);
        long tasksNotDoneUpdatedToDoneLastSevenDays = taskRepository.getTasksNotDoneUpdatedToDoneLastSevenDays(projectId);
        long totalTasksWithDoneStage = taskRepository.getTotalTasksWithDoneStage(projectId);
        long tasksUpdatedToDoneLastSevenDays = taskRepository.getTasksUpdatedToDoneLastSevenDays(projectId);
        long totalUsersAssignedToTasks = userRepository.getTotalUsersAssignedToTasks(projectId);
        long usersAssignedToTasksLastSevenDays = userRepository.getUsersAssignedToTasksLastSevenDays(projectId);
        List<Task> tasksAddedLastSevenDaysDetails = taskRepository.findTasksAddedLastSevenDays(projectId);
        List<Object[]> priorityOfTasksAddedToday = taskRepository.getPriorityOfTasksAddedToday(projectId);

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
