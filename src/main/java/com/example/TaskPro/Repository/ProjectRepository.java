package com.example.TaskPro.Repository;

import com.example.TaskPro.DTO.UserDTO;
import com.example.TaskPro.Models.Project;
import com.example.TaskPro.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    boolean existsById(int id);
    Optional<Project> findById(int projectId);
    List<Project> findByCreatedBy(int userId);
    @Query(value = "SELECT * FROM users u JOIN project_members pm ON u.id = pm.user_id WHERE pm.project_id = :projectId", nativeQuery = true)
    List<Object[]> findAllMembersByProjectIdNative(@Param("projectId") int projectId);
}




