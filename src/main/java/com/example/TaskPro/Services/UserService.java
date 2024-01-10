package com.example.TaskPro.Services;

import com.example.TaskPro.Models.UserEntity;
import com.example.TaskPro.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

<<<<<<< HEAD
    public UserEntity getUserById(int id) {
        return repository.findById(id).orElse(null);
    }

=======
>>>>>>> 4aa7db81f3aa327f2c3c05a2b7fa192bb183d725
//    public UserEntity updateUserDetails (UserEntity userInfo){
//        //UserEntity user = repository.findById(userInfo.getId());
//
//
//    }

<<<<<<< HEAD
    //add a task

=======
>>>>>>> 4aa7db81f3aa327f2c3c05a2b7fa192bb183d725

}
