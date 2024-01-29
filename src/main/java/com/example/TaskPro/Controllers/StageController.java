package com.example.TaskPro.Controllers;


import com.example.TaskPro.DTO.Response;
import com.example.TaskPro.DTO.StageWithTask;
import com.example.TaskPro.DTO.UpdateStageName;
import com.example.TaskPro.Exceptions.NotFoundException;
import com.example.TaskPro.Models.Stage;
import com.example.TaskPro.Models.Task;
import com.example.TaskPro.Repository.ProjectRepository;
import com.example.TaskPro.Services.StageService;
import com.example.TaskPro.Services.TaskService;
import jakarta.transaction.Transactional;
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
public class StageController {
    @Autowired
    private StageService stageService;

    @Autowired
    ProjectRepository projectRepository;

    @PostMapping("/createStage/projectId={projectId}")
    public ResponseEntity<Response> createNewStage(@RequestBody @Valid Stage stage,@PathVariable("projectId") int projectId){
        if ( !projectRepository.existsById(projectId)) {
            throw new NotFoundException("Project does not exist");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(of("stage",stageService.createStage(stage, projectId)))
                        .message("Stage created successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
                );
    }

    @GetMapping("/allStagesAndTheirTasks/projectId={projectId}")
    public ResponseEntity<Response> getAll(@PathVariable("projectId") int projectId){
        if ( !projectRepository.existsById(projectId)) {
            throw new NotFoundException("Project does not exist");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(of("stages",stageService.getAllStagesWithTasks(projectId)))
                        .message("Stages and their tasks retreived successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );
    }

    @PutMapping("updateStageName")
    public ResponseEntity<Response> updateStageName (@RequestBody @Valid UpdateStageName up){
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(of("stage",stageService.update(up)))
                        .message("Updated successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );

    }


    @DeleteMapping("/deleteStage/stageId={stageId}")
    public ResponseEntity<Response> deleteStage(@PathVariable("stageId") int stageId){
        stageService.deleteStage(stageId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Stage deleted successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );
    }



}
