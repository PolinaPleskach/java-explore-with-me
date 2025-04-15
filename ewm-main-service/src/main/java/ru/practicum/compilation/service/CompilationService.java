package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationRequestDto;
import ru.practicum.compilation.dto.CompilationResponseDto;
import ru.practicum.compilation.dto.UpdateCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationResponseDto createCompilation(CompilationRequestDto compilationRequestDto);

    void deleteCompilation(Long compId);

    CompilationResponseDto updateCompilation(UpdateCompilationDto updateCompilationDto, Long compId);

    List<CompilationResponseDto> getAllCompilations(Boolean pinned, int from, int size);

    CompilationResponseDto getCompilationById(Long compId);
}