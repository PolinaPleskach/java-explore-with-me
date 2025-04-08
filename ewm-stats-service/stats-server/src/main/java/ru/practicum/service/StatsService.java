package ru.practicum.service;

import ru.practicum.requestDto;
import ru.practicum.responseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    requestDto createStat(requestDto statRequestDto);

    List<responseDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}