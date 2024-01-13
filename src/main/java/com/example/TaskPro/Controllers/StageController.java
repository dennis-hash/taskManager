package com.example.TaskPro.Controllers;


import com.example.TaskPro.DTO.Response;
import com.example.TaskPro.Models.Stage;
import com.example.TaskPro.Models.Task;
import com.example.TaskPro.Services.StageService;
import com.example.TaskPro.Services.TaskService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.util.Map.of;

@CrossOrigin(origins = {"http://localhost:4200", "https://taskpro-2mq8.onrender.com"})
@RestController
@RequestMapping("/api")
public class StageController {
    @Autowired
    private StageService stageService;
    @PostMapping("/createStage")
    public ResponseEntity<Response> createNewStage(@RequestBody @Valid Stage stage){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(of("stage",stageService.createStage(stage)))
                        .message("Stage created successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
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
