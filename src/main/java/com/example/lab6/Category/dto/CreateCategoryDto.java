package com.example.lab6.Category.dto;

public class CreateCategoryDto {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CreateCategoryDto(String name) {
        this.name = name;
    }

    public CreateCategoryDto() {

    }
}
