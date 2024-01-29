package com.example.TaskPro.Exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException (String message)  {
        super(message);
    }
}
