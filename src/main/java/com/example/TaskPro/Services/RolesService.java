package com.example.TaskPro.Services;

import com.example.TaskPro.Models.Roles;
import com.example.TaskPro.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    @Autowired
    RoleRepository roleRepository;

    public Roles createRole(Roles role) {
        return roleRepository.save(role);
    }
}
