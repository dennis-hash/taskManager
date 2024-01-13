package com.example.TaskPro.Controllers;

import com.example.TaskPro.Models.Roles;
import com.example.TaskPro.Services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = {"http://localhost:4200", "https://taskpro-2mq8.onrender.com"})
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
