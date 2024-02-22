package com.example.demo.service;

import com.example.demo.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    void addCategory(Category category);
    List<Category> getAllCategory();
    void removeCategoryById(int id);
    Optional<Category> getCategoryById(int id);
}
