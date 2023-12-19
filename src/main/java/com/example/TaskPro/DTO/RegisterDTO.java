package com.example.TaskPro.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class RegisterDTO {
    private String fName;
    private String mName;
    private String lName;
    private String email;
    private String password;
}
