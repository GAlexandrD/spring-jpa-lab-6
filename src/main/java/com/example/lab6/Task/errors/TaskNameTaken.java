package com.example.lab6.Task.errors;

public class TaskNameTaken extends RuntimeException {
    public TaskNameTaken(String name) {
        super("Task with name (" + name + ") already exists");
    }

}
