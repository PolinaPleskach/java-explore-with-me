package ru.practicum.event.mapper;

import ru.practicum.event.dto.EventLocationDto;
import ru.practicum.event.entity.Location;

public class LocationMapper {

    public Location mapToLocation(EventLocationDto dto) {
        return new Location(
                null,
                dto.getLat(),
                dto.getLon()
        );
    }

    public EventLocationDto mapToLocationDto(Location location) {
        return new EventLocationDto(
                location.getLat(),
                location.getLon()
        );
    }
}