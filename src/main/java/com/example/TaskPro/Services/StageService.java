package com.example.TaskPro.Services;

import com.example.TaskPro.DTO.StageWithTask;
import com.example.TaskPro.DTO.UpdateStageName;
import com.example.TaskPro.Exceptions.NotFoundException;
import com.example.TaskPro.Models.Project;
import com.example.TaskPro.Models.Stage;
import com.example.TaskPro.Models.Task;
import com.example.TaskPro.Repository.ProjectRepository;
import com.example.TaskPro.Repository.StageRepository;
import com.example.TaskPro.Repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StageService {


    @Autowired
    private StageRepository stageRepository;


    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    public Stage createStage(Stage stage, int projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        Project project = optionalProject.get();

        Stage newStage = new Stage();
        newStage.setProjectId(project);
        newStage.setCreatedBy(stage.getCreatedBy());
        newStage.setName(stage.getName());
        return stageRepository.save(newStage);
    }

    @Transactional
    public void initializeDefaultStage() {
        Stage defaultStage = stageRepository.findByName("DEFAULT");

        if (defaultStage == null) {
            defaultStage = new Stage();
            defaultStage.setName("DEFAULT");
            stageRepository.save(defaultStage);
        }
    }

    @Transactional
    public void deleteStage(int stageId) {
        Optional<Stage> stage = stageRepository.findById(stageId);
        List<Task> tasksWithStage = taskRepository.findByStageId(stage);

        if (!tasksWithStage.isEmpty()) {
            Stage defaultStage = stageRepository.findByName("DEFAULT");
            tasksWithStage.forEach(task -> task.setStageId(defaultStage));
        }

        stageRepository.deleteById(stageId);
    }

    public Stage update(UpdateStageName up){
        if(!stageRepository.existsById(up.getStageId())){
            throw new NotFoundException("Stage does not exist");
        }

        Optional<Stage> sstage = stageRepository.findById(up.getStageId());
        Stage stage = sstage.get();

        stage.setName(up.getName());


        return stageRepository.save(stage);
    }

    public List<Stage> getAllStagesWithTasks(int projectId) {
        return stageRepository.findAllWithTasks(projectId);
    }



//    public List<StageWithTask> getAllStagesWithTasks() {
//        List<Stage> stages = stageRepository.findAll();
//
//        // Use findByStage_Id to fetch tasks for each stage directly
//        return stages.stream()
//                .map(stage -> new StageWithTask(stage, taskRepository.findByStage_Id(stage.getId())))
//                .collect(Collectors.toList());
//    }






}
