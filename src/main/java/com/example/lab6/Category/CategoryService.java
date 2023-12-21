package com.example.lab6.Category;

import com.example.lab6.Category.errors.CategoryNameTakenException;
import com.example.lab6.Category.errors.CategoryNotFoundException;
import com.example.lab6.Task.utils.DateParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository, DateParser dateParser) {
    this.categoryRepository = categoryRepository;
  }

  List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  Category getCategoryById(Long id) {
    Optional<Category> task = categoryRepository.findById(id);
    if (task.isEmpty()) {
      throw new CategoryNotFoundException(id);
    }
    return task.get();
  }

  Category createCategory(String name) {
    Optional<Category> found = categoryRepository.findByName(name);
    if(found.isPresent()) throw new CategoryNameTakenException(name);
    Category newCategory = new Category(name);
    return categoryRepository.save(newCategory);
  }

  void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }
}
