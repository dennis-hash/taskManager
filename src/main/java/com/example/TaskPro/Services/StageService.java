package com.example.TaskPro.Services;

import com.example.TaskPro.Models.Stage;
import com.example.TaskPro.Models.Task;
import com.example.TaskPro.Repository.StageRepository;
import com.example.TaskPro.Repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageService {
    @Autowired
    private StageRepository taskStageRepository;

    @Autowired
    private StageRepository stageRepository;


    @Autowired
    TaskRepository taskRepository;

    public Stage createStage(Stage stage) {
        return taskStageRepository.save(stage);
    }

    @Transactional
    public void initializeDefaultStage() {
        Stage defaultStage = taskStageRepository.findByName("DEFAULT");

        if (defaultStage == null) {
            defaultStage = new Stage();
            defaultStage.setName("DEFAULT");
            taskStageRepository.save(defaultStage);
        }
    }

    @Transactional
    public void deleteStage(int stageId) {
        Stage stage = stageRepository.findById(stageId);
        List<Task> tasksWithStage = taskRepository.findByStageId(stage);

        if (!tasksWithStage.isEmpty()) {
            Stage defaultStage = taskStageRepository.findByName("DEFAULT");
            tasksWithStage.forEach(task -> task.setStageId(defaultStage));
        }

        taskStageRepository.deleteById(stageId);
    }


}
