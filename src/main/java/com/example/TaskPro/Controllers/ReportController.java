package com.example.TaskPro.Controllers;

import com.example.TaskPro.DTO.TaskReportDTO;
import com.example.TaskPro.Services.ReportService;
import com.example.TaskPro.Services.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
