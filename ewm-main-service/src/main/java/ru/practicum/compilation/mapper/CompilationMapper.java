package ru.practicum.compilation.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.compilation.dto.CompilationRequestDto;
import ru.practicum.compilation.dto.CompilationResponseDto;
import ru.practicum.compilation.entity.Compilation;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.entity.Event;

import java.util.List;

@Component
public class CompilationMapper {

    public Compilation mapToCompilation(CompilationRequestDto dto, List<Event> events) {
        return new Compilation(
                null,
                events,
                dto.getPinned(),
                dto.getTitle()
        );
    }

    public CompilationResponseDto mapToCompilationDto(Compilation compilation, List<EventShortDto> eventShortDtoList) {
        return new CompilationResponseDto(
                compilation.getId(),
                eventShortDtoList,
                compilation.getPinned(),
                compilation.getTitle()
        );
    }
}