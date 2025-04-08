package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.requestDto;
import ru.practicum.entity.Stat;

@Mapper
public interface StatsMapper {
    StatsMapper INSTANCE = Mappers.getMapper(StatsMapper.class);

    Stat toStats(requestDto statRequestDto);

    requestDto toStatsDtoInput(Stat stat);
}