package com.example.lab6.Task.errors;

public class InvalidSortParamException extends RuntimeException {
    public InvalidSortParamException(String sort) {
        super("Cannot sort tasks by " + sort);
    }
}
