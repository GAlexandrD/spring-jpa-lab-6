package com.example.lab6.Category;

import com.example.lab6.Category.dto.CreateCategoryDto;
import com.example.lab6.Category.errors.CategoryNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/categories")
@Tag(name = "category", description = "Operations with categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "get categories", description = "get all existing categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found categories",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Category.class)))})})
    @GetMapping
    List<Category> getAll()
    {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "get category", description = "get category by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found categories",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Category.class)))})})
    @GetMapping(path = "{categoryId}")
    Category getOneById(
            @PathVariable(name = "categoryId") Long categoryId
    ) {
        return categoryService.getCategoryById(categoryId);
    }

    @Operation(summary = "create category", description = "create one category from given json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))})})
    @PostMapping
    Category createcategory(@RequestBody CreateCategoryDto category) {
        return categoryService.createCategory(category.getName());
    }

    @Operation(summary = "delete category", description = "delete category by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully",
                    content = {@Content})})
    @DeleteMapping(path = "{categoryId}")
    void deleteCategory(@PathVariable("categoryId") Long categoryId) {
         categoryService.deleteCategory(categoryId);
    }

    @ExceptionHandler({CategoryNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleNFException(Exception ex) {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
