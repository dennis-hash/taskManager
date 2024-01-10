package com.example.TaskPro.Controllers;

import com.example.TaskPro.DTO.*;
import com.example.TaskPro.Models.Roles;
import com.example.TaskPro.Models.UserEntity;
import com.example.TaskPro.Repository.RoleRepository;
import com.example.TaskPro.Repository.UserRepository;
import com.example.TaskPro.Security.JwtService;
import com.example.TaskPro.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.of;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Response> addNewUser(@RequestBody RegisterDTO userInfo){
        if (userRepository.existsByEmail(userInfo.getEmail())) {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Email is taken!")
                            .status(HttpStatus.CONFLICT)
                            .statusCode(HttpStatus.CONFLICT.value())
                            .build()
            );
        }

        UserEntity user = new UserEntity();
        user.setFName(userInfo.getFName());
        user.setMName(userInfo.getMName());
        user.setLName(userInfo.getLName());
        user.setEmail(userInfo.getEmail());


        user.setPassword(userInfo.getPassword());

        user.setPassword(userInfo.getPassword());

        Roles roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(of("user", service.registerUser(user)))
                        .message("created successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    ));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(loginDto.getEmail());
                //return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
                //userRepository.existsByEmail(loginDto.getEmail());
                Map<String, Object> tokenAndUserMap = new HashMap<>();
                tokenAndUserMap.put("token",token);
                tokenAndUserMap.put("user",userRepository.findByEmail(loginDto.getEmail()));

                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(tokenAndUserMap)
                                .message("Logged in successfully")
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build()
                );
            }
        } catch (AuthenticationException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Invalid email or password")
                            .status(HttpStatus.UNAUTHORIZED)
                            .statusCode(HttpStatus.UNAUTHORIZED.value())
                            .build()
                    );


        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Unknown error occurred")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build()
        );

    }



    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "Downloads/images/" + fileName));
    }


    @GetMapping("user/{userId}")
    public UserEntity getUserById(@PathVariable int userId) {
        return service.getUserById(userId);
    }

}
