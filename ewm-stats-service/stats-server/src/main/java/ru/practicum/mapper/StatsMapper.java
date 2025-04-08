package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.RequestDto;
import ru.practicum.entity.Stat;

@Mapper
public interface StatsMapper {
    StatsMapper INSTANCE = Mappers.getMapper(StatsMapper.class);

    Stat mapToStats(RequestDto statRequestDto);

    RequestDto mapToStatsRequestDto(Stat stat);
}