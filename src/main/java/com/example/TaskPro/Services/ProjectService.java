package com.example.TaskPro.Services;

import com.example.TaskPro.DTO.AddMemberToProject;
import com.example.TaskPro.DTO.AssignTaskDTO;
import com.example.TaskPro.DTO.ProjectDTO;
import com.example.TaskPro.DTO.UserDTO;
import com.example.TaskPro.Exceptions.NotFoundException;
import com.example.TaskPro.Models.Project;
import com.example.TaskPro.Models.Stage;
import com.example.TaskPro.Models.Task;
import com.example.TaskPro.Models.UserEntity;
import com.example.TaskPro.Repository.ProjectRepository;
import com.example.TaskPro.Repository.StageRepository;
import com.example.TaskPro.Repository.TaskRepository;
import com.example.TaskPro.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;


    @Autowired
    StageRepository stageRepository;

    public Project createProject(Project project, int userId){
        Project newProject = new Project();
        newProject.setTitle(project.getTitle());
        newProject.setDescription(project.getDescription());
        newProject.setCreatedBy(userId);
        projectRepository.save(newProject);

        AddMemberToProject newAssignee = new AddMemberToProject();
        newAssignee.setProjectId(newProject.getId());
        int[] userIds = {userId};
        newAssignee.setAssigneeUserId(userIds);
        addMembers(newAssignee);

        return newProject;
    }

    public void addMembers(AddMemberToProject newAssignee) {
        Optional<Project> pproject = projectRepository.findById(newAssignee.getProjectId());
        Project project = pproject.get();

        int[] assigneeUserIds = newAssignee.getAssigneeUserId();
        for (int j = 0; j < assigneeUserIds.length; j++) {
            int assigneeUserId = assigneeUserIds[j];
            UserEntity user = userRepository.findById(assigneeUserId);
            project.getMembers().add(user);
            projectRepository.save(project);
        }
    }

    public List<ProjectDTO> getAllProjects(int userId) {
        List<Project> createdProjects = projectRepository.findByCreatedBy(userId);

        List<Task> assignedTasks = taskRepository.findByAssignedUserId(userId);
        List<Project> memberProjects = assignedTasks.stream()
                .map(Task::getProjectId)
                .distinct()
                .toList();

        List<Project> allProjects = Stream.concat(createdProjects.stream(), memberProjects.stream())
                .distinct()
                .toList();

        return allProjects.stream()
                .map(project -> new ProjectDTO(project.getId(),project.getTitle(), project.getDescription()))
                .collect(Collectors.toList());

    }
    @Transactional
    public void deleteProject(int projectId){

        Optional<Project> pproject = projectRepository.findById(projectId);
        Project project = pproject.get();

        List<Stage> stagesInProject = stageRepository.findByProjectId(pproject);
        for (Stage stage : stagesInProject) {
            for (Task task : stage.getTasks()) {
                task.getAssignedUser().clear();
            }
            taskRepository.deleteAll(stage.getTasks());
        }

        stageRepository.deleteAll(stagesInProject); // Efficient bulk deletion

        projectRepository.delete(project);
        log.info("Deleted project");


    }

    public List<UserDTO> getAllMembers(int projectId) {
        List<Object[]> results = projectRepository.findAllMembersByProjectIdNative(projectId);
        List<UserEntity> members = new ArrayList<>();

        for (Object[] result : results) {
            UserEntity user = new UserEntity();
            user.setId((Integer) result[0]);
            user.setFName((String) result[1]);
            user.setMName((String) result[2]);
            user.setLName((String) result[3]);
            user.setEmail((String) result[4]);
            members.add(user);
        }

        return mapUsersToDTOs(members);
    }

    private List<UserDTO> mapUsersToDTOs(List<UserEntity> users) {
        List<UserDTO> userDTOs = new ArrayList<>();

        for (UserEntity user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setFName(user.getFName());
            userDTO.setMName(user.getMName());
            userDTO.setLName(user.getLName());
            userDTO.setEmail(user.getEmail());


            userDTOs.add(userDTO);
        }

        return userDTOs;
    }

}
