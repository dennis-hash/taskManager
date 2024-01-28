package com.example.TaskPro.Controllers;

import com.example.TaskPro.DTO.*;
import com.example.TaskPro.Exceptions.NotFoundException;
import com.example.TaskPro.Models.Project;
import com.example.TaskPro.Models.Stage;
import com.example.TaskPro.Models.Task;
import com.example.TaskPro.Repository.ProjectRepository;
import com.example.TaskPro.Repository.StageRepository;
import com.example.TaskPro.Repository.TaskRepository;
import com.example.TaskPro.Repository.UserRepository;
import com.example.TaskPro.Services.StageService;
import com.example.TaskPro.Services.TaskService;
import com.example.TaskPro.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Map.of;

@CrossOrigin(origins = {"http://localhost:4200", "https://taskpro-2mq8.onrender.com"})
@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    ProjectRepository projectRepository;

    //create new task
    @PostMapping("/createTask/userId={userId}/projectId={projectId}")
    public ResponseEntity<Response> createNewTask(@RequestBody @Valid CreateTaskDTO task, @PathVariable("userId") int userId,@PathVariable("projectId") int projectId){
        Optional<Project> pproject = projectRepository.findById(projectId);
        Project project = pproject.get();
        Stage stage = stageRepository.findByIdAndProjectId(task.getStageId(), project);
        if (stage == null) {
            throw new NotFoundException("Stage does not exist in this project");
        }
//        if(!stageRepository.existsById(task.getStageId())){
//            throw new NotFoundException("Stage does not exist");
//        }
        if(!projectRepository.existsById(projectId)){
            throw new NotFoundException("project does not exist");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(of("task",taskService.createTask(task,userId,projectId)))
                        .message("Task created successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
                );
    }

    //assign task
    @PostMapping("/assignTask")
    public ResponseEntity<Response> assignTask(@RequestBody AssignTaskDTO newAssignee) {

        if (!taskRepository.existsById(newAssignee.getTaskId())) {
            throw new NotFoundException("Task does not exist");
        }

        int[] assigneeUserIds = newAssignee.getAssigneeUserId();
        for (int j = 0; j < assigneeUserIds.length; j++) {
            int assigneeUserId = assigneeUserIds[j];
            if(!userRepository.existsById(assigneeUserId)){
                throw new NotFoundException("User with ID " + assigneeUserId + " does not exist");
            }

        }
        taskService.assignTask(newAssignee);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Task assigned successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );
    }

        //undo task assignment
    @DeleteMapping("/undoAssignment")
    public ResponseEntity<Response> undoAssignment(@RequestBody AssignTaskDTO assignee) {

        if (!taskRepository.existsById(assignee.getTaskId())) {
            throw new NotFoundException("Task does not exist");
        }

        int[] assigneeUserIds = assignee.getAssigneeUserId();
        for (int j = 0; j < assigneeUserIds.length; j++) {
            int assigneeUserId = assigneeUserIds[j];
            if(!userRepository.existsById(assigneeUserId)){
                throw new NotFoundException("User with ID " +assigneeUserId+ " does not exist");
            }

        }
        taskService.undoTaskAssignment(assignee);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Task removed successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );
    }

    //update task
    @PutMapping("/updateTask/taskId={taskId}")
    public ResponseEntity<Response> updateTask(@RequestBody CreateTaskDTO task,@PathVariable("taskId") int taskId){

        if(!taskRepository.existsById(taskId)){
            throw new NotFoundException("Task does not exist");
        }
        if(!stageRepository.existsById(task.getStageId())){
            throw new NotFoundException("Stage does not exist");
        }


            return ResponseEntity.status(HttpStatus.OK)
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(of("task",taskService.updateTask(task, taskId)))
                            .message("Task updated successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
                    );
    }


    //getTaskById
    @GetMapping("/getTaskByTaskId/taskId={taskId}")
    public ResponseEntity<Response> getTaskByTaskId(@PathVariable int taskId){

        if(!taskRepository.existsById(taskId)){
            throw new NotFoundException("Task does not exist");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Task retreived successfully")
                        .data(of("task", taskService.getTaskByTaskId(taskId)))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );

    }

    //delete task
    @DeleteMapping("/deleteTask/taskId={taskId}")
    public ResponseEntity<Response> deleteTask(@PathVariable("taskId") int taskId){

        if(!taskRepository.existsById(taskId)){
            throw new NotFoundException("Task does not exist");
        }
        taskService.deleteTask(taskId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Deleted successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );

    }

    //get user's tasks
    @GetMapping("/getUserTasks/userId={userId}/projectId={projectId}")
    public ResponseEntity<Response> getAllTasks(@PathVariable("userId") int userId, @PathVariable("projectId") int projectId){

        if ( !userRepository.existsById(userId)) {
            throw new NotFoundException("User does not exist");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Task retreived successfully")
                        .data(of("tasks", taskService.userTasks(userId,projectId)))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );

    }

    @PutMapping("/updateTaskStage")
    public ResponseEntity<Response> updateTaskStage(@RequestBody UpdateStageDTO updateStageDT0){
        if(!stageRepository.existsById(updateStageDT0.getStageId())){
            throw new NotFoundException("Stage does not exist");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Stage updated successfully")
                        .data(of("task", taskService.updateTaskStage(updateStageDT0)))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );
    }

    @GetMapping("users")
    public ResponseEntity<List<UserDTO>> getAllUsers () {
        List<UserDTO> userDTOs = userService.getAllUsers();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

}
