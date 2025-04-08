package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryRequestDto;
import ru.practicum.category.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto updateCategory(long catId, CategoryRequestDto categoryRequestDto);

    void deleteCategory(long catId);

    CategoryResponseDto getCategoryById(long catId);

    List<CategoryResponseDto> getAllCategories(int from, int size);
}