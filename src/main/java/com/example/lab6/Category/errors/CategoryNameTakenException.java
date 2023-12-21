package com.example.lab6.Category.errors;

public class CategoryNameTakenException extends RuntimeException {
    public CategoryNameTakenException(String name) {
        super("category with name (" + name + ") already exists");
    }

}
