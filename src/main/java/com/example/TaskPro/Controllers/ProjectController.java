package com.example.TaskPro.Controllers;

import com.example.TaskPro.DTO.AddMemberToProject;
import com.example.TaskPro.DTO.CreateTaskDTO;
import com.example.TaskPro.DTO.Response;
import com.example.TaskPro.DTO.UserDTO;
import com.example.TaskPro.Exceptions.NotFoundException;
import com.example.TaskPro.Models.Project;
import com.example.TaskPro.Repository.ProjectRepository;
import com.example.TaskPro.Repository.UserRepository;
import com.example.TaskPro.Services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Map.of;

@CrossOrigin(origins = {"http://localhost:4200", "https://taskpro-2mq8.onrender.com"})
@RestController
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @PostMapping("/createProject/userId={userId}")
    public ResponseEntity<Response> createTask(@RequestBody @Valid Project project, @PathVariable("userId") int userId){
        if ( !userRepository.existsById(userId)) {
            throw new NotFoundException("User does not exist");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(of("project",projectService.createProject(project,userId)))
                        .message("Task created successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
                );


    }


    //delete project
    @DeleteMapping("/deleteProject/projectId={projectId}")
    public ResponseEntity<Response> deleteProject(@PathVariable("projectId") int projectId){
        if ( !projectRepository.existsById(projectId)) {
            throw new NotFoundException("Project does not exist");
        }

        projectService.deleteProject(projectId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Deleted successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );
    }

    //add member
    @PostMapping("/addMember")
    public ResponseEntity<Response> addMember(@RequestBody AddMemberToProject add){
        if ( !projectRepository.existsById(add.getProjectId())) {
            throw new NotFoundException("Project does not exist");
        }

        int[] assigneeUserIds = add.getAssigneeUserId();
        for (int j = 0; j < assigneeUserIds.length; j++) {
            int assigneeUserId = assigneeUserIds[j];
            if(!userRepository.existsById(assigneeUserId)){
                throw new NotFoundException("User with ID"+assigneeUserId+" does not exist");
            }

        }


        projectService.addMembers(add);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Member added successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );
    }


    @GetMapping("/getProjects/userId={userId}")
    public ResponseEntity<Response> getAllProjects(@PathVariable("userId") int userId){
        if ( !userRepository.existsById(userId)) {
            throw new NotFoundException("User does not exist");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Retrieved successfully")
                        .data(of("projects",projectService.getAllProjects(userId)))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );

    }

    @GetMapping("projectMembers/projectId={projectId}")
    public ResponseEntity<List<UserDTO>> getAllUsers (@PathVariable("projectId") int projectId) {
        if ( !projectRepository.existsById(projectId)) {
            throw new NotFoundException("Project does not exist");
        }

        List<UserDTO> userDTOs = projectService.getAllMembers(projectId);
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }


}
