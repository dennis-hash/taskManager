package com.example.TaskPro.Controllers;

import com.example.TaskPro.DTO.TaskReportDTO;
import com.example.TaskPro.Services.ReportService;
import com.example.TaskPro.Services.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = {"http://localhost:4200", "https://taskpro-2mq8.onrender.com"})
@RestController
@RequestMapping("/api")
public class ReportController {
    @Autowired
    ReportService reportService;

    @GetMapping("/report/projectId={projectId}")
    public TaskReportDTO getReport(@PathVariable("projectId") int projectId){

        return reportService.generateTaskReport(projectId);
    }
    
}
