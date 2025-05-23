package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dao.CompilationRepository;
import ru.practicum.compilation.dto.CompilationRequestDto;
import ru.practicum.compilation.dto.CompilationResponseDto;
import ru.practicum.compilation.dto.UpdateCompilationDto;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.entity.Compilation;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.entity.Event;
import ru.practicum.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;
    private final EventMapper eventMapper;

    @Override
    public CompilationResponseDto createCompilation(CompilationRequestDto compilationRequestDto) {
        List<Event> events = eventRepository.findByIdIn(compilationRequestDto.getEvents());
        Compilation compilation = compilationMapper.mapToCompilation(compilationRequestDto, events);
        if (Objects.isNull(compilationRequestDto.getPinned())) {
            compilation.setPinned(false);
        }
        Compilation newCompilation = compilationRepository.save(compilation);
        log.info("Подборка с id = {} создана.", compilation.getId());
        return compilationMapper.mapToCompilationDto(newCompilation, events.stream()
                .map(eventMapper::mapToEventShortDto)
                .toList());
    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
        log.info("Удаление подборки с id = {} администратором.", compId);
    }

    @Override
    public CompilationResponseDto updateCompilation(UpdateCompilationDto updateCompilationDto, Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборки с id = {} не существует." + compId));
        List<Event> events = eventRepository.findByIdIn(updateCompilationDto.getEvents());
        if (Objects.nonNull(updateCompilationDto.getEvents())) {
            compilation.setEvents(events);
        }
        if (Objects.nonNull(updateCompilationDto.getPinned())) {
            compilation.setPinned(updateCompilationDto.getPinned());
        }
        if (Objects.nonNull(updateCompilationDto.getTitle())) {
            compilation.setTitle(updateCompilationDto.getTitle());
        }
        log.info("Обновление данных подборки с id = {}.", compId);
        return compilationMapper.mapToCompilationDto(compilation, events.stream()
                .map(eventMapper::mapToEventShortDto)
                .toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationResponseDto> getAllCompilations(Boolean pinned, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        List<Compilation> compilationList;
        if (Objects.nonNull(pinned)) {
            compilationList = compilationRepository.findByPinned(pinned, pageRequest);
        } else {
            compilationList = compilationRepository.findAll(pageRequest).toList();
        }
        if (compilationList.isEmpty()) {
            log.info("Подборок событий еще нет.");
            return new ArrayList<>();
        }
        log.info("Получение списка подборок событий.");
        return compilationList.stream()
                .map(c -> compilationMapper.mapToCompilationDto(c, c.getEvents().stream()
                        .map(eventMapper::mapToEventShortDto).toList()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationResponseDto getCompilationById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборки с id = {} не существует." + compId));
        log.info("Получение данных подборки с id = {}.", compId);
        return compilationMapper.mapToCompilationDto(compilation,
                compilation.getEvents().stream()
                        .map(eventMapper::mapToEventShortDto)
                        .toList());
    }
}