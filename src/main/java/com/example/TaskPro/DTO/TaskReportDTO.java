package com.example.TaskPro.DTO;

import com.example.TaskPro.Models.Task;
import lombok.Data;

import java.util.List;

@Data
public class TaskReportDTO {
    private long allTasks;
    private long tasksAddedSinceLastWeek;
    private long pending;
    private long newlyCreatedButPending;
    private long completed;
    private long newlyCompletedLastSevenDays;
    private long assignees;
    private long assigneesSinceLastWeek;
    private List<Task> recentTasks;
    private List<Object[]> todayTasksWithPriority;

    public TaskReportDTO(
            long totalTasks,
            long tasksAddedLastSevenDays,
            long totalTasksNotDone,
            long tasksNotDoneUpdatedToDoneLastSevenDays,
            long totalTasksWithDoneStage,
            long tasksUpdatedToDoneLastSevenDays,
            long totalUsersAssignedToTasks,
            long usersAssignedToTasksLastSevenDays,
            List<Task> tasksAddedLastSevenDaysDetails,
            List<Object[]> priorityOfTasksAddedToday
    ) {
        this.allTasks = totalTasks;
        this.tasksAddedSinceLastWeek = tasksAddedLastSevenDays;
        this.pending = totalTasksNotDone;
        this.newlyCreatedButPending = tasksNotDoneUpdatedToDoneLastSevenDays;
        this.completed = totalTasksWithDoneStage;
        this.newlyCompletedLastSevenDays = tasksUpdatedToDoneLastSevenDays;
        this.assignees = totalUsersAssignedToTasks;
        this.assigneesSinceLastWeek = usersAssignedToTasksLastSevenDays;
        this.recentTasks = tasksAddedLastSevenDaysDetails;
        this.todayTasksWithPriority = priorityOfTasksAddedToday;
    }
}
