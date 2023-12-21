package com.example.lab6.Category.errors;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Category with id (" + id + ") wasn't found");
    }

    public CategoryNotFoundException(String name) {
        super("Category with name (" + name + ") wasn't found");
    }
}
