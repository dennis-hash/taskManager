package com.example.TaskPro;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Task Pro APIs", version = "1.0"))
@SecurityScheme(
		name = "Bearer authentication",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT",
		description = "Enter your JWT access token here to access protected resources."
)
public class TaskProApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskProApplication.class, args);
	}

}
