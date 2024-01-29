package com.example.TaskPro.Services;

import com.example.TaskPro.DTO.UserDTO;
import com.example.TaskPro.Models.UserEntity;
import com.example.TaskPro.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity registerUser(UserEntity userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return repository.save(userInfo);
    }


    public UserEntity getUserById(int id) {
        return repository.findById(id);
    }

    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = repository.findAll();
        return mapUsersToDTOs(users);
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
