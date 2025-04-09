package ru.practicum.compilation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDtoInput;
import ru.practicum.compilation.dto.CompilationDtoOutput;
import ru.practicum.compilation.dto.UpdateCompilationDto;
import ru.practicum.compilation.service.CompilationService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDtoOutput createCompilation(@Valid @RequestBody CompilationDtoInput compilationRequestDto) {
        return compilationService.createCompilation(compilationRequestDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDtoOutput updateCompilation(@Valid @RequestBody UpdateCompilationDto updateCompilationDto,
                                                  @PathVariable Long compId) {
        return compilationService.updateCompilation(updateCompilationDto, compId);
    }
}