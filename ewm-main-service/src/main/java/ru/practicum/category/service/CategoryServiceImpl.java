package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dao.CategoryRepository;
import ru.practicum.category.dto.CategoryRequestDto;
import ru.practicum.category.dto.CategoryResponseDto;
import ru.practicum.category.entity.Category;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.DuplicatedDataException;
import ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        if (categoryRepository.findAll().contains(categoryMapper.mapToCategory(categoryRequestDto))) {
            throw new DuplicatedDataException("Эта категория уже существует.");
        }
        Category category = categoryRepository.save(categoryMapper.mapToCategory(categoryRequestDto));
        return categoryMapper.mapToCategoryResponseDto(category);
    }

    @Override
    public CategoryResponseDto updateCategory(long catId, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категории с id = {} не существует." + catId));

        if (Objects.nonNull(categoryRequestDto.getName())) {
            category.setName(categoryRequestDto.getName());
        }

        return categoryMapper.mapToCategoryResponseDto(category);
    }

    @Override
    public void deleteCategory(long catId) {
        if (eventRepository.existsByCategoryId(catId)) {
            throw new ConflictException("Нельзя удалить категорию, с которой связаны события.");
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryById(long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категории с id = {} не существует." + catId));
        return categoryMapper.mapToCategoryResponseDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryMapper.mapToCategoryResponseDtoList(categoryPage.getContent());
    }
}