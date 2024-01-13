package com.example.TaskPro.Controllers;

import com.example.TaskPro.DTO.TaskReportDTO;
import com.example.TaskPro.Services.ReportService;
import com.example.TaskPro.Services.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = {"http://localhost:4200", "https://taskpro-2mq8.onrender.com"})
@RestController
@RequestMapping("/api")
public class ReportController {
    @Autowired
    ReportService reportService;

    @GetMapping("/report")
    public TaskReportDTO getReport(){
        return reportService.generateTaskReport();
    }
    
}
