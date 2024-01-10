package com.example.TaskPro.Controllers;

import com.example.TaskPro.Models.Roles;
import com.example.TaskPro.Services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
//@RequestMapping("/api")
public class RolesController {
    @Autowired
    RolesService rolesService;

    @PostMapping("/createRole")
    public Roles createRole(@RequestBody Roles role) {
        return rolesService.createRole(role);
    }
}
