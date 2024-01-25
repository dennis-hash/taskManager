package com.example.TaskPro.Repository;

import com.example.TaskPro.Models.Project;
import com.example.TaskPro.Models.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProjectRepositoryTests {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void ProjectRepository_existbyId_returnTrue(){
        //Arrange
        Project project = Project.builder()
                .title("test project")
                .description("test project description")
                .createdBy(1).build();
        projectRepository.save(project);

        //Act
        boolean exists = projectRepository.existsById(project.getId());
        // Assert
        Assertions.assertThat(exists).isTrue();

    }

    @Test
    public void ProjectRepository_findById_returnProject(){
        //Arrange
        Project project = Project.builder()
                .title("test project")
                .description("test project description")
                .createdBy(1).build();
        projectRepository.save(project);

        //Act
        Project projectList = projectRepository.findById(project.getId()).get();
        //Assert
        Assertions.assertThat(projectList).isNotNull();

    }

    @Test
    public void ProjectRepository_findByCreatedBy_returnProject(){

        Project project = Project.builder()
                .title("test project")
                .description("test project description")
                .createdBy(1).build();
        projectRepository.save(project);


        List<Project> projectList = projectRepository.findByCreatedBy(project.getCreatedBy());

        Assertions.assertThat(projectList).isNotNull();
        Assertions.assertThat(projectList.size()).isEqualTo(1);
    }





}
