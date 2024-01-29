package com.example.TaskPro.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String fName;
    private String mName;
    private String lName;
    private String email;

    public UserDTO(
            Integer id,
           String fName,
         String mName,
        String lName,
         String email
    ){
        this.id = id;
        this.fName =fName;
        this.mName = mName;
        this.lName = lName;
        this.email = email;
    }
}
