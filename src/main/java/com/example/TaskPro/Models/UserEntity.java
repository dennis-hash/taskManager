package com.example.TaskPro.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fName;
    private String mName;
    private String lName;
    private String email;
    private String imageUrl;
    @JsonIgnore
    private String password;


//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
//    private List<Task> tasks;




    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //roles o be shown whenever you pull user from the DB
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private List<Roles> roles = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    List<AssignedTasks> assignedTasks;


}
