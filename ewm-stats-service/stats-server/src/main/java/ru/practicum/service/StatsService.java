package ru.practicum.service;

import ru.practicum.RequestDto;
import ru.practicum.ResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    RequestDto createStat(RequestDto statRequestDto);

    List<ResponseDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}