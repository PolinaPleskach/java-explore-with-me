package ru.practicum.category.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.category.dto.CategoryRequestDto;
import ru.practicum.category.dto.CategoryResponseDto;
import ru.practicum.category.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public Category mapToCategory(CategoryRequestDto categoryRequestDto) {
        return new Category(
                categoryRequestDto.getId(),
                categoryRequestDto.getName()
        );
    }

    public CategoryResponseDto mapToCategoryResponseDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName()
        );
    }

    public List<CategoryResponseDto> mapToCategoryResponseDtoList(List<Category> categories) {
        return categories.stream()
                .map(this::mapToCategoryResponseDto)
                .collect(Collectors.toList());
    }
}